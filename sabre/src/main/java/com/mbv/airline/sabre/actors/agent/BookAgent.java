package com.mbv.airline.sabre.actors.agent;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.CreatePNR;
import com.mbv.airline.sabre.commands.Pricing;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PricingResult;
import com.mbv.airline.sabre.commands.result.PricingResult.PaxPricing;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.utils.Utils;

public class BookAgent extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AirItinerary itinerary;

	public BookAgent(AirItinerary itinerary) {
		this.itinerary = itinerary;
	}

	public void preStart() {
		logger.debug(self().path() + " started");

		Command cmd = new CreatePNR(this.itinerary);
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

			// TODO Auto-generated method stub
			if (message instanceof PNR) {
				PNR pnr = (PNR) message;
				logger.info("Booking Receive PNR from VNA");
				if (pnr.getStatus() == Code.SUCCESS) {
					itinerary.getTicketingInfo().setReservationCode(
							pnr.getReservationCode());
					Command cmd = new Pricing(pnr.getReservationCode(), null);
					context().actorSelection("/user/SabreTerminals").tell(cmd,
							getSelf());
				} else {
					itinerary.getTicketingInfo().setStatus(
							BookingStatus.BOOK_ERROR);
					itinerary.getTicketingInfo().setDescription(
							"Create PNR Error: " + pnr.getDescription());
					context().parent().tell(itinerary, getSelf());
					context().stop(getSelf());
				}
			} else if (message instanceof PricingResult) {
				PricingResult pricing = (PricingResult) message;
				logger.info("Receive Booking Result from VNA");
				if (pricing.getStatus() == Code.SUCCESS) {
					AirTicketingInfo ticketingInfo = this.itinerary
							.getTicketingInfo();

					int numSegments = itinerary.getFareInfos().size();
					HashMap<String, Integer> paxCounts = new HashMap<String, Integer>();
					paxCounts.put("ADT", Utils.getPaxCountByType(itinerary,
							AirPassengerType.ADT));
					paxCounts.put("CNN", Utils.getPaxCountByType(itinerary,
							AirPassengerType.CHD));
					paxCounts.put("INF", Utils.getPaxCountByType(itinerary,
							AirPassengerType.INF));

					for (Entry<String, Integer> entry : paxCounts.entrySet()) {
						if (entry.getValue() > 0
								&& ticketingInfo.getStatus() != BookingStatus.BOOK_ERROR) {
							PaxPricing paxPricing = pricing.getPaxPricing(entry
									.getKey());

							if (paxPricing == null
									|| paxPricing.getCount() != entry
											.getValue()
									|| pricing
											.getSegPricingList(entry.getKey())
											.size() != numSegments) {

								ticketingInfo
										.setStatus(BookingStatus.BOOK_ERROR);
								ticketingInfo.setDescription("Pricing Error: "
										+ entry.getValue() + " "
										+ entry.getKey());

							}
						}

					}

					if (pricing.getTotalAmount() == null
							|| pricing.getTotalAmount() <= 0) {
						ticketingInfo.setStatus(BookingStatus.BOOK_ERROR);
						ticketingInfo.setDescription("Pricing Error");
					}

					if (ticketingInfo.getStatus() != BookingStatus.BOOK_ERROR) {
						ticketingInfo
								.setStatus(BookingStatus.BOOK_SUCCESS);
						ticketingInfo.setAmount(pricing.getTotalAmount());
						// ticketingInfo.setUpdatedDate(new Date());
						if (pricing.getTimeLimit() != null) {
							ticketingInfo.setExpiredDate(pricing.getTimeLimit()
									.toDate());
							// ticketingInfo.setDescription(pricing.getTimeLimit()
							// .toString("yyyy-MM-dd'T'HH:mm:ssZ"));
						} else {
							ticketingInfo.setExpiredDate(DateUtils.addDays(
									ticketingInfo.getCreatedDate(), 1));
						}
					}

					context().parent().tell(itinerary, getSelf());
					context().stop(getSelf());
				} else {
					itinerary.getTicketingInfo().setStatus(
							BookingStatus.BOOK_ERROR);
					itinerary.getTicketingInfo()
							.setDescription("Pricing Error");
					context().parent().tell(itinerary, getSelf());
					context().stop(getSelf());
				}
			} else if (message instanceof ReceiveTimeout) {
				logger.info("Sabre Book Receive Timeout");
				context().stop(getSelf());
			}

		} catch (Exception e) {
			logger.error("Book Agent Error : ", e);
		}

	}
}
