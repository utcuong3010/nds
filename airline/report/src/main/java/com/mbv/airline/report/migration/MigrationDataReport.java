package com.mbv.airline.report.migration;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.airline.common.info.AirBookingInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.report.AirTicketReport;
import com.mbv.airline.common.support.AirItineraryReport;
import com.mbv.airline.common.support.AirItineraryRepository;

public class MigrationDataReport {
	private AirItineraryRepository itineraryRepository;
	final Log logger = LogFactory.getLog(MigrationDataReport.class);

	public void setItineraryRepository(AirItineraryRepository itineraryRepository) {
		this.itineraryRepository = itineraryRepository;
	}

	private AirItineraryReport airItineraryReport;

	public void setAirItineraryReport(AirItineraryReport airItineraryReport) {
		this.airItineraryReport = airItineraryReport;
	}

	public void migrateData(String status) throws Exception {
		logger.info("call migrateData");
		List<AirItinerary> itineraryList = itineraryRepository.findByStatus(status);
		for (AirItinerary itinerary : itineraryList) {
			insertMongoReport(itinerary);
		}

	}

	private void insertMongoReport(AirItinerary itinerary) {
		String vendor = itinerary.getFareInfos().get(0).getVendor();
		if (vendor.equals("VJ"))
			vendor = "Vietjet";
		else if (vendor.equals("BL"))
			vendor = "Jetstar";
		else if (vendor.equals("VN"))
			vendor = "VN_Sabre";
		AirTicketReport ticketReport = airItineraryReport.findById(itinerary.getId());
		if (ticketReport == null) {
			AirBookingInfo bookingInfo = new AirBookingInfo();
			bookingInfo.setAgentInfo(itinerary.getAgentInfo());
			bookingInfo.setContactInfo(itinerary.getContactInfo());
			bookingInfo.setExtraServices(itinerary.getExtraServices());
			bookingInfo.setFareInfos(itinerary.getFareInfos());
			bookingInfo.setPassengerInfos(itinerary.getPassengerInfos());
			AirTicketReport insertReport = new AirTicketReport(itinerary.getId(), itinerary.getTicketingInfo()
					.getReservationCode(), vendor, itinerary.getTicketingInfo().getAmount(), bookingInfo);
			insertReport.setCheckedDate(itinerary.getTicketingInfo().getUpdatedDate());
			try {
				logger.info("Insert mongo db for migrate data: " + insertReport.toString());
				airItineraryReport.add(insertReport);
			} catch (Exception ex) {
				logger.info("Migrate data: Insert mongo error: " + insertReport.toString());
			}
		} else
			logger.info("Data migrate already inserted: " + ticketReport.toString());

	}

}
