package com.mbv.airline.webapp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.report.AirTicketReport;
import com.mbv.airline.common.support.AirItineraryReport;
import com.mbv.airline.common.support.AirItineraryRepository;

@Controller("ReportController")
@RequestMapping("/Report")
public class ReportController {

	final static Logger LOG = Logger.getLogger(ReportController.class);
	private RabbitTemplate rabbitTemplate;

	@Autowired
	@Resource(name = "rabbitTemplate")
	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	private AirItineraryRepository itineraryRepository;

	@Autowired
	@Resource(name = "airItineraryRepository")
	public void setItineraryRepository(AirItineraryRepository itineraryRepository) {
		this.itineraryRepository = itineraryRepository;
	}

	private AirItineraryReport airItineraryReport;

	@Autowired
	@Resource(name = "airItineraryReport")
	public void setAirItineraryReport(AirItineraryReport airItineraryReport) {
		this.airItineraryReport = airItineraryReport;
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	@ResponseBody
	public String Test() throws Exception {
		return "Maka Faka";
	}

	/**
	 * get list report info
	 *
	 * @param fromdate
	 *            , todate
	 * @return list of AirTicketReport
	 * @throws DataNotFoundException
	 *             ,ParseException
	 */
	@RequestMapping(value = "/checking/{query}", method = RequestMethod.GET)
	@ResponseBody
	public List<AirTicketReport> getReportData(@PathVariable String query) throws DataNotFoundException, ParseException {
		String[] parts = query.split("-");
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date start_date = sdf.parse(parts[0] + " 00:00:00");
		Date end_date = sdf.parse(parts[1] + " 23:59:59");
		List<AirTicketReport> result = null;
		try {
			result = airItineraryReport.findByDate(start_date, end_date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null)
			throw new DataNotFoundException();
		for (AirTicketReport ticketReport : result)
			LOG.info("report data: " + ticketReport.toString());
		return result;
	}

	/**
	 * send a payment id to check
	 *
	 * @param Id
	 * @return checked id
	 * @throws
	 */
	@RequestMapping(value = "/checking/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String verifyPayment(@PathVariable String id) throws Exception {
		airItineraryReport.updatePayCheckStatus(id, "checking");
		AirItinerary itinerary = itineraryRepository.findById(id);
		String rountingKey = itinerary.getFareInfos().get(0).getVendor();
		rabbitTemplate.convertAndSend("AirService", rountingKey, new VerifyPaymentCmd(id, itinerary
				.getTicketingInfo().getReservationCode(), itinerary.getTicketingInfo().getExpiredDate()));
		LOG.info("RabbitMQ send success verify payment request: "
				+ new VerifyPaymentCmd(id, itinerary.getTicketingInfo().getReservationCode(), itinerary
						.getTicketingInfo().getExpiredDate()).toString());
		return id;

	}

	/**
	 * get a report
	 *
	 * @param Id
	 * @return report info
	 * @throws
	 */
	@RequestMapping(value = "/payment/{id}", method = RequestMethod.GET)
	@ResponseBody
	public AirTicketReport getVerifyPayment(@PathVariable String id) throws Exception {
		AirTicketReport ticketReport = airItineraryReport.findById(id);
		LOG.info("Result ticketReport: " + ticketReport.toString());
		return ticketReport;
	}

	@SuppressWarnings("serial")
	private static class DataNotFoundException extends Exception {
	}

	@ExceptionHandler(DataNotFoundException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void DataNotFound(DataNotFoundException ex) {
	}

}
