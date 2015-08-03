package com.mbv.airline.sabre.actors.agent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import scala.concurrent.duration.Duration;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.sabre.SpringExt;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.IssueTickets;
import com.mbv.airline.sabre.commands.Pricing;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PricingResult;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.common.ConfigInfo;
import com.mbv.airline.sabre.utils.PricingClassUtil;

public class PayAgent extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String Email_Content = "%s booking %s";
	private String Email_Content2 = "%s %s booking %s";
	private String Email_Subject = "VNA_PAYMENT %s";

	private ApplicationContext appCtx;
	private AirItinerary itinerary;
	private PricingClassUtil pcUtil;

	private ConfigInfo configInfo;

//	private JMSSending jmsSending;
	
	private SenderManager senderManager;

	private boolean isRetry;

	public PayAgent(AirItinerary itinerary, PricingClassUtil pcUtil,
			boolean isRetry) {
		this.itinerary = itinerary;
		this.pcUtil = pcUtil;
		this.isRetry = isRetry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see akka.actor.UntypedActor#preStart()
	 */
	public void preStart() {
		logger.debug(self().path() + " started");

		appCtx = SpringExt.Extention.get(context().system()).getAppCtx();
		senderManager = (SenderManager) appCtx.getBean(SenderManager.class);
//		jmsSending = (JMSSending) appCtx.getBean("sending");
		configInfo = (ConfigInfo) appCtx.getBean("configInfo");

		Command cmd = new Pricing(itinerary.getTicketingInfo()
				.getReservationCode(), pcUtil);
		context().actorSelection("/user/SabreTerminals").tell(cmd, getSelf());
		context().setReceiveTimeout(Duration.create(60, TimeUnit.SECONDS));
	}

	public void postRestart() {
		context().stop(getSelf());
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

				logger.info("Receive Pay Result from VNA");

				if (result.getStatus() == Code.SUCCESS) {
					if (result.getTotalAmount() == itinerary.getTicketingInfo()
							.getAmount()) {
						AirTicketingInfo ticketInfo = this.itinerary
								.getTicketingInfo();

						ArrayList<String> ticketNumbers = result.getPNR()
								.getTicketNumbers();
						ticketInfo.setTicketNumbers(ticketNumbers);

						if (ticketNumbers.size() > 0) {
							ticketInfo
									.setStatus(BookingStatus.BUY_SUCCESS);

							sendEmail(itinerary, null);

							context().parent().tell(itinerary, getSelf());
							context().stop(getSelf());
						} else {
							// Issue ticket
							IssueTickets cmd = new IssueTickets(itinerary
									.getTicketingInfo().getReservationCode());
							context().actorSelection("/user/SabreTerminals")
									.tell(cmd, getSelf());
						}
					} else {
						// return to parent
						logger.error("Pricing does not match: " + "nds price: "
								+ itinerary.getTicketingInfo().getAmount()
								+ "       vna price: "
								+ result.getTotalAmount());

						itinerary.getTicketingInfo().setStatus(
								BookingStatus.BUY_ERROR);
						itinerary.getTicketingInfo().setDescription(
								"Pricing does not match");

						sendEmail(itinerary, null);
						context().parent().tell(itinerary, getSelf());
						context().stop(getSelf());
					}
				} else {
					itinerary.getTicketingInfo().setStatus(
							BookingStatus.BUY_ERROR);
					itinerary.getTicketingInfo()
							.setDescription("Pricing Error");
					sendEmail(itinerary, null);
					context().parent().tell(itinerary, getSelf());
					context().stop(getSelf());
				}
			} else if (message instanceof PNR) {
				PNR pnr = (PNR) message;

				logger.info("Payment Receive PNR from VNA");

				itinerary.getTicketingInfo().setRefContent(
						getSender().path().name());

				if (pnr.getStatus() == Code.SUCCESS) {
					AirTicketingInfo ticketInfo = this.itinerary
							.getTicketingInfo();

					ArrayList<String> ticketNumbers = pnr.getTicketNumbers();
					ticketInfo.setTicketNumbers(ticketNumbers);

					if (ticketNumbers.size() > 0) {
						ticketInfo.setStatus(BookingStatus.BUY_SUCCESS);
					} else {
						logger.error("BUY ERROR: " + "ticketNumbers mismatch "
								+ ticketNumbers.size());
						ticketInfo.setDescription("BUY ERROR: "
								+ "ticketNumbers mismatch "
								+ ticketNumbers.size());
						if (isRetry) {
							ticketInfo.setStatus(BookingStatus.BUY_ERROR);
						}
					}

					sendEmail(itinerary, null);

					context().parent().tell(itinerary, getSelf());
					context().stop(getSelf());
				} else if (pnr.getStatus() == Code.ERROR
						|| pnr.getStatus() == Code.TERMINAL_ERROR) {
					itinerary.getTicketingInfo().setStatus(
							BookingStatus.BUY_ERROR);
					itinerary.getTicketingInfo().setDescription(
							"Issue Ticket Error: " + pnr.getDescription());
					sendEmail(itinerary, null);
					context().parent().tell(itinerary, getSelf());
					context().stop(getSelf());
				} else {
					context().stop(getSelf());
				}
			} else if (message instanceof ReceiveTimeout) {
				logger.info("Sabre Pay Receive Timeout");
				context().stop(getSelf());
			}

		} catch (Exception e) {
			logger.error("Pay Agent Error : ", e);
		}

	}

	private void sendEmail(AirItinerary airItinerary, String des) {
		try {
			String msg;
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));

			Calendar today = Calendar.getInstance();
			String date = new SimpleDateFormat("yyyy/MM/dd").format(today
					.getTime());

			if (des == null)
				msg = String.format(Email_Content, airItinerary
						.getTicketingInfo().getStatus().name(),
						mapper.writeValueAsString(airItinerary));
			else
				msg = String.format(Email_Content2, des, airItinerary
						.getTicketingInfo().getStatus().name(),
						mapper.writeValueAsString(airItinerary));

			senderManager.sendMailMessage(Arrays.asList(configInfo.getEmailTests()),String.format(Email_Subject, date),
					msg.replaceAll(";", "<br>"));
			
//			jmsSending.sendMailMessage(configInfo.getEmailTests(),
//					String.format(Email_Subject, date),
//					msg.replaceAll(";", "<br>"), true);
		} catch (Exception ex) {
			logger.error("Send email check BUY/BOOK air itinerary "
					+ airItinerary.getId() + ". Reservation code: "
					+ airItinerary.getTicketingInfo().getReservationCode()
					+ "failed: ", ex.getMessage());
		}
	}
}
