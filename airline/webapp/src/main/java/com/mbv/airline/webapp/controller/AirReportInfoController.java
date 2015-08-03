package com.mbv.airline.webapp.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.StringUtils;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.report.AirReportForm;
import com.mbv.airline.common.support.AirItineraryRepository;
import com.mbv.airline.report.AirReportInfoFilter;

/**
 * 
 * @author phamtuyen
 *
 */

@Controller("airReportInfoController")
@RequestMapping("/AirReportInfo")
public class AirReportInfoController {
	
	/**
	 * logger
	 */
	final Logger logger = Logger.getLogger(AirReportInfoController.class);
	
	/**
	 * query schema itinerary in mongodb
	 */
	private AirItineraryRepository itineraryRepository;
	
	@Autowired
	@Resource(name = "airItineraryRepository")
	public void setItineraryRepository(AirItineraryRepository itineraryRepository) {
		this.itineraryRepository = itineraryRepository;
	}
	
	/**
	 * do: Statistical booking Airline
	 * @param start
	 * @param end
	 * @param pageSize
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{start}&{end}&{pageSize}&{page}", method = RequestMethod.GET)
	@ResponseBody
	public AirReportForm getReportInfo(@PathVariable String start,@PathVariable String end,@PathVariable String pageSize,@PathVariable String page) throws Exception {
		try {			
			logger.info("Data Recieved:AirReportInfo/" + start + "&" + end + "&" + pageSize + "&" + page);
			
			start = StringUtils.splitString(start, "=", 1);			
			end = StringUtils.splitString(end, "=", 1);
			
			Date dateStart = DateUtils.parse(start, "dd-MM-yyyy");
			Date dateEnd = DateUtils.dateChange(DateUtils.parse(end, "dd-MM-yyyy"), 1);			
			List<AirItinerary> itineraries = itineraryRepository.findByDate(dateStart, dateEnd);
			if(itineraries == null)
				return null;	
			// sort itineraries
			Collections.reverse(itineraries);
			// paging
			pageSize = StringUtils.splitString(pageSize, "=", 1);
			page = StringUtils.splitString(page, "=", 1);			
			return AirReportInfoFilter.getAirReportForm(itineraries,Integer.parseInt(pageSize),Integer.parseInt(page));				
		} catch (Exception ex) {
			logger.info("CAN NOT GET AIRREPORTFROM: " + ex);
			throw ex;
		}
	}
}
