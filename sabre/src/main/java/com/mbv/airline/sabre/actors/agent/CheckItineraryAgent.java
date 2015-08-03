package com.mbv.airline.sabre.actors.agent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import scala.concurrent.duration.Duration;
import akka.actor.ReceiveTimeout;
import akka.actor.UntypedActor;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.support.AirItineraryRepository;
import com.mbv.airline.common.support.MongoDbAirItineraryReport;
import com.mbv.airline.sabre.SpringExt;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.Pricing;
import com.mbv.airline.sabre.commands.result.PricingResult;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.common.ConfigInfo;
import com.mbv.airline.sabre.utils.PricingClassUtil;

public class CheckItineraryAgent extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private VerifyPaymentCmd reportCommand;
	private MongoDbAirItineraryReport report;
	private AirItineraryRepository repoItinerary;
	private PricingClassUtil pcUtil;

	private ConfigInfo configInfo;

//	private JMSSending jmsSending;
	private SenderManager senderManager;
	
	private String Email_Content = "%s booking %s";
	private String Email_Subject = "VNA_Payment error_%s";

	public CheckItineraryAgent(VerifyPaymentCmd reportCommand,
			PricingClassUtil pcUtil) {
		this.reportCommand = reportCommand;
		this.pcUtil = pcUtil;
	}

	public void preStart() {
		logger.debug(self().path() + " started");

		ApplicationContext appCtx = SpringExt.Extention.get(context().system())
				.getAppCtx();
//		jmsSending = (JMSSending) appCtx.getBean("sending");
		senderManager = (SenderManager) appCtx.getBean(SenderManager.class);
		
		configInfo = (ConfigInfo) appCtx.getBean("configInfo");
		report = (MongoDbAirItineraryReport) appCtx
				.getBean("airItineraryReport");
		repoItinerary = (AirItineraryRepository) appCtx
				.getBean("airItineraryRepository");

		Command cmd = new Pricing(reportCommand.getReservationCode(), pcUtil);

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

			BookingStatus status = BookingStatus.BUY_ERROR;

			if (message instanceof PricingResult) {
				PricingResult result = (PricingResult) message;
				if (result.getStatus() != null) {
					if (result.getStatus() == Result.Code.SUCCESS) {

						ArrayList<String> ticketNumbers = result.getPNR()
								.getTicketNumbers();

						if (ticketNumbers.size() > 0)
							status = BookingStatus.BUY_SUCCESS;
					}
				}
				
				AirItinerary checkError = repoItinerary.findById(reportCommand.getId());;
				String oldStatus = checkError.getTicketingInfo().getStatus().toString();	

				if (BookingStatus.BUY_ERROR.equals(status)) {
					sendEmail(repoItinerary.findById(reportCommand.getId()));
				}
				// send mail to helpdesk
				if(!oldStatus.equalsIgnoreCase(status.name()))
				senderManager.sendMessage("VERIFY PAYMENT", repoItinerary.findById(reportCommand.getId()), "DIFFERENCE");

				report.updatePayCheckInfo(reportCommand.getId(), status.name());
				context().stop(getSelf());
			} else if (message instanceof ReceiveTimeout) {
				context().stop(getSelf());
			}

		} catch (Exception e) {
			logger.error("RetrieveBooking Agent Error : ", e);
		}
	}

	private void sendEmail(AirItinerary airItinerary) {
		try {
			String msg;
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"));

			Calendar today = Calendar.getInstance();
			String date = new SimpleDateFormat("yyyy/MM/dd").format(today
					.getTime());

			msg = String.format(Email_Content, airItinerary.getTicketingInfo()
					.getStatus().name(),
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