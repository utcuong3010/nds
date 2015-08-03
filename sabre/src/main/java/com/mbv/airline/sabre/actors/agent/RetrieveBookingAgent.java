package com.mbv.airline.sabre.actors.agent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.info.AgentInfo;
import com.mbv.airline.common.info.AirExtraService;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.common.info.ContactInfo;
import com.mbv.airline.common.support.AirItineraryRepository;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.Pricing;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PricingResult;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.utils.PricingClassUtil;
import com.mbv.airline.sabre.utils.Utils;

/**
 * Created by phuongvt on 1/21/15.
 */
public class RetrieveBookingAgent extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AirItinerary itinerary;
	private AirItineraryRepository repo;
	private PricingClassUtil pcUtil;

	public RetrieveBookingAgent(AirItinerary itinerary,
			AirItineraryRepository repo, PricingClassUtil pcUtil) {
		this.itinerary = itinerary;
		this.repo = repo;
		this.pcUtil = pcUtil;
	}

	public void preStart() {
		logger.debug(self().path() + " started");

		Command cmd = new Pricing(itinerary.getTicketingInfo()
				.getReservationCode(), pcUtil);
		context().actorSelection("/user/SabreTerminals").tell(cmd, getSelf());
		context().setReceiveTimeout(Duration.create(60, TimeUnit.SECONDS));
	}

	public void postRestart() {
		context().stop(getSelf());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		try {
			StringBuilder msgLog = new StringBuilder();
			msgLog.append("[" + self().path() + "] ");
			msgLog.append("[" + getSender().path() + "] ");
			msgLog.append("[Message : " + message.getClass().getName() + "]"
					+ "\n");
			msgLog.append(message.toString());

			logger.info(msgLog.toString());
			if (message instanceof PricingResult) {
				PricingResult result = (PricingResult) message;

				if (result.getStatus() == Code.SUCCESS) {
					ContactInfo contactInfo = itinerary.getContactInfo();
					if (contactInfo == null) {
						contactInfo = new ContactInfo();
					}
					if (result.getPNR().getPhone() != null)
						contactInfo.setMobile(result.getPNR().getPhone());

					AgentInfo agentInfo = itinerary.getAgentInfo();
					if (agentInfo == null) {
						agentInfo = new AgentInfo();
					}
					if (result.getPNR().getAgentId() != null)
						agentInfo.setAgentId(result.getPNR().getAgentId());
					if (result.getPNR().getReceived() != null)
						agentInfo.setUserId(result.getPNR().getReceived());

					AirTicketingInfo ticketInfo = itinerary.getTicketingInfo();
					if (ticketInfo == null)
						ticketInfo = new AirTicketingInfo();
					
					if(result.getPNR().getTotalAmount()!=null && result.getTotalAmount()==0){
						result.setTotalAmount(result.getPNR().getTotalAmount());
					}

					if (result.getTotalAmount() != null)
						ticketInfo.setAmount(result.getTotalAmount());

					if (result.getPNR().getDateCreate() != null)
						ticketInfo.setCreatedDate(result.getPNR()
								.getDateCreate().plusHours(12).toDate());

					if (result.getPNR().getDateUpdate() != null)
						ticketInfo.setUpdatedDate(result.getPNR()
								.getDateUpdate().plusHours(12).toDate());
					else
						ticketInfo.setUpdatedDate(new Date());

					if (result.getTimeLimit() != null)
						ticketInfo.setExpiredDate(result.getTimeLimit()
								.toDate());
					else if (ticketInfo.getCreatedDate() != null)
						ticketInfo.setExpiredDate(DateUtils.addDays(
								ticketInfo.getCreatedDate(), 1));

					ArrayList<String> ticketNumbers = result.getPNR()
							.getTicketNumbers();
					ticketInfo.setTicketNumbers(ticketNumbers);

					if (ticketInfo.getAmount() > 0)
						ticketInfo.setStatus(BookingStatus.BOOK_SUCCESS);

					if (ticketInfo.getExpiredDate() != null
							&& ticketInfo.getExpiredDate().getTime() < new Date()
									.getTime())
						ticketInfo.setStatus(BookingStatus.BOOK_EXPIRED);

					else if (ticketInfo.getCreatedDate() == null)
						ticketInfo.setStatus(BookingStatus.BOOKING_NOT_FOUND);

					if (ticketNumbers.size() > 0) {
						if (ticketInfo.getAmount() > 0)
							ticketInfo.setStatus(BookingStatus.BUY_SUCCESS);
					}

					if (ticketInfo.getAmount() <= 0)
						ticketInfo.setStatus(BookingStatus.BOOK_CANCELED);

					List<AirPassengerInfo> passengerInfos = new ArrayList<AirPassengerInfo>();
					for (PNR.Pax pax : result.getPNR().getPaxs()) {
						if (pax != null) {
							AirPassengerInfo passengerInfo = new AirPassengerInfo();
							passengerInfo.setFirstName(pax.getFirstName());
							passengerInfo.setLastName(pax.getLastName());
							passengerInfo.setPassengerType(Utils
									.getPassengerType(pax.getType()));
							passengerInfo.setGender(Utils.getGenderType(pax
									.getGender()));
							passengerInfos.add(passengerInfo);
						}
					}

					List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();
					for (PNR.Segment itineraryInfo : result.getPNR()
							.getSegments()) {
						if (itineraryInfo != null) {
							AirFareInfo fareInfo = new AirFareInfo();
							fareInfo.setVendor(itineraryInfo
									.getAirlineDesignator());
							fareInfo.setFlightCode(itineraryInfo
									.getAirlineDesignator()
									+ itineraryInfo.getFlightNumber());
							if (itineraryInfo.getDepartureDate() != null)
								fareInfo.setDepartureDate(itineraryInfo
										.getDepartureDate().toDate());
							if (itineraryInfo.getArrivalDate() != null)
								fareInfo.setArrivalDate(itineraryInfo
										.getArrivalDate().toDate());
							fareInfo.setDestinationCode(itineraryInfo.getFrom());
							fareInfo.setOriginCode(itineraryInfo.getTo());
							fareInfo.setClassCode(itineraryInfo
									.getClassOfService());
							fareInfos.add(fareInfo);
						}
					}

					// List<AirExtraService> extraServices = new
					// ArrayList<AirExtraService>();

					itinerary.setTicketingInfo(ticketInfo);
					itinerary.setContactInfo(contactInfo);
					itinerary.setPassengerInfos(passengerInfos);
					itinerary.setFareInfos(fareInfos);
				} else {
					itinerary.getTicketingInfo().setStatus(
							BookingStatus.BOOKING_NOT_FOUND);
					itinerary.getTicketingInfo().setDescription(result.getDescription());
				}
				// itinerary.setExtraServices(extraServices);
				// if (result.getStatus() != null
				// && result.getStatus() == Result.Code.SUCCESS) {
				//
				// } else {
				// logger.error("Retrieve booking error: "
				// + result.getDescription());
				// itinerary.getTicketingInfo().setStatus(
				// AirTicketingStatus.BOOK_ERROR);
				// itinerary.getTicketingInfo().setDescription(
				// result.getDescription());
				// }

				repo.updateBookingInfo(itinerary);
				context().stop(getSelf());
			} else if (message instanceof ReceiveTimeout) {
				context().stop(getSelf());
			}

		} catch (Exception e) {
			logger.error("RetrieveBooking Agent Error : ", e);
		}
	}
}
