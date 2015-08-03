package com.mbv.airline.sabre.actors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.cmd.CreateBookingCmd;
import com.mbv.airline.common.cmd.PayBookingCmd;
import com.mbv.airline.common.cmd.RetrieveBookingCmd;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.common.support.AirFarePriceCache;
import com.mbv.airline.common.support.AirItineraryRepository;
import com.mbv.airline.common.support.ItineraryFilter;
import com.mbv.airline.common.support.MongoDbAirItineraryReport;
import com.mbv.airline.sabre.SpringExt;
import com.mbv.airline.sabre.actors.agent.BookAgent;
import com.mbv.airline.sabre.actors.agent.CheckItineraryAgent;
import com.mbv.airline.sabre.actors.agent.PayAgent;
import com.mbv.airline.sabre.actors.agent.RetrieveBookingAgent;
import com.mbv.airline.sabre.actors.agent.SearchAgent;
import com.mbv.airline.sabre.utils.PricingClassUtil;

public class CommandHandler extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private ApplicationContext appCtx;
	private ItineraryFilter filter;
	private PricingClassUtil pcUtil;
	private AirFarePriceCache cache;
	private AirItineraryRepository repo;
	// private MongoAirItineraryReport repoReport;
	private MongoDbAirItineraryReport report;

	/*
	 * (non-Javadoc)
	 * 
	 * @see akka.actor.UntypedActor#preStart()
	 */
	public void preStart() {
		logger.debug(self().path() + " started");

		appCtx = SpringExt.Extention.get(context().system()).getAppCtx();
		pcUtil = (PricingClassUtil) appCtx.getBean("pricingClassUtil");
		cache = (AirFarePriceCache) appCtx.getBean("airFarePriceCache");
		repo = (AirItineraryRepository) appCtx
				.getBean("airItineraryRepository");
		report = (MongoDbAirItineraryReport) appCtx
				.getBean("airItineraryReport");
		// repoReport = (MongoAirItineraryReport) appCtx
		// .getBean("reportAirItineraryRepository");

		filter = new ItineraryFilter(repo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see akka.actor.UntypedActor#postStop()
	 */
	public void postStop() {
		logger.debug(self().path() + " stopped");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		StringBuilder msgLog = new StringBuilder();
		msgLog.append("[" + self().path() + "] ");
		msgLog.append("[" + getSender().path() + "] ");
		msgLog.append("[Message : " + message.getClass().getName() + "]" + "\n");
		msgLog.append(message.toString());

		logger.debug(msgLog.toString());

		// TODO Auto-generated method stub
		if (message instanceof SearchItineraryCmd) {
			ActorRef child = this.context().actorOf(
					Props.create(SearchAgent.class, message, cache));

		} else if (message instanceof CreateBookingCmd) {
			AirItinerary itinerary = repo
					.findById(((CreateBookingCmd) message).getId());
			if (itinerary != null) {
				AirTicketingInfo info = itinerary.getTicketingInfo();
				try {
					if (itinerary.getTicketingInfo().getStatus() == BookingStatus.BOOK_PENDING) {
						info.setStatus(BookingStatus.BOOK_PROCESSING);
						repo.update(itinerary);
					}
					ActorRef child = this.context().actorOf(
							Props.create(BookAgent.class, itinerary));
				} catch (Exception e) {
					logger.error("Book itinerary " + itinerary.getId()
							+ "failed: ", e.getMessage());
				}
			}
		} else if (message instanceof RetrieveBookingCmd) {
			AirItinerary itinerary = repo
					.findById(((RetrieveBookingCmd) message).getId());
			if (itinerary != null) {
				try {
					if (itinerary.getTicketingInfo().getStatus() == BookingStatus.BOOK_PENDING) {
						itinerary.getTicketingInfo().setStatus(
								BookingStatus.BOOK_PROCESSING);
						repo.update(itinerary);
					}
					ActorRef child = this.context().actorOf(
							Props.create(RetrieveBookingAgent.class, itinerary,
									repo, pcUtil));
				} catch (Exception e) {
					logger.error("Retrieve itinerary " + itinerary.getId()
							+ "failed: ", e.getMessage());
				}
			}
		} else if (message instanceof PayBookingCmd) {
			AirItinerary itinerary = repo
					.findById(((PayBookingCmd) message).getId());

			if (itinerary != null) {
				if (itinerary.getTicketingInfo().getStatus() == BookingStatus.BUY_PENDING)
					itinerary.getTicketingInfo().setStatus(
							BookingStatus.BUY_PROCESSING);
				try {
					repo.update(itinerary);

					ActorRef child = this.context().actorOf(
							Props.create(PayAgent.class, itinerary, pcUtil,
									false));

					// repoReport.add(itinerary);

				} catch (Exception e) {
					logger.error("Pay itinerary " + itinerary.getId()
							+ ". Reservation code:"
							+ itinerary.getTicketingInfo().getReservationCode()
							+ "failed: ", e.getMessage());
				}
			}

		} else if (message instanceof VerifyPaymentCmd) {
			VerifyPaymentCmd reportCommand = (VerifyPaymentCmd) message;
			try {
				ActorRef child = this.context().actorOf(
						Props.create(CheckItineraryAgent.class, message, pcUtil));
			} catch (Exception e) {
				logger.error("Check itinerary " + reportCommand.getId()
						+ "failed: ", e.getMessage());
			}
		}

//		else if (message instanceof ReportPayItinerary) {
//			ReportPayItinerary payItinerary = (ReportPayItinerary) message;
//			try {
//				ActorRef child = this.context().actorOf(
//						Props.create(CheckAirItinerary.class, message, pcUtil));
//			} catch (Exception e) {
//				logger.error("Check itinerary " + payItinerary.getId()
//						+ "failed: ", e.getMessage());
//			}
//		} 
		
		else if (message instanceof AirItinerary) {
			AirItinerary airItinerary = (AirItinerary) message;
			try {
				repo.update((AirItinerary) message);
				if (BookingStatus.BUY_PROCESSING.equals(airItinerary
						.getTicketingInfo().getStatus())) {
					try {
						ActorRef child = this.context().actorOf(
								Props.create(PayAgent.class, airItinerary,
										pcUtil, true));
					} catch (Exception e) {
						logger.error("Pay itinerary "
								+ airItinerary.getId()
								+ ". Reservation code:"
								+ airItinerary.getTicketingInfo()
										.getReservationCode() + "failed: ",
								e.getMessage());
					}
				}
			} catch (Exception e) {
				logger.error("Update air itinerary " + airItinerary.getId()
						+ ". Reservation code: "
						+ airItinerary.getTicketingInfo().getReservationCode()
						+ "failed: ", e.getMessage());
			}

		}
	}
}
