package com.mbv.airline.report;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ActiveObjectCounter;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;

import akka.actor.UntypedActor;

import com.mbv.airline.common.AirlineProvider;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirBookingInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.report.AirTicketReport;
import com.mbv.airline.common.support.AirItineraryReport;

public class ReportActor extends UntypedActor {
	private RabbitTemplate template;
	private String queue;
	private BlockingQueueConsumer consumer;
	private AirItineraryReport airItineraryReport;
	private String vendor;
	protected final Log logger = LogFactory.getLog(AirServiceReport.class);

	public ReportActor(RabbitTemplate template, String queue, AirItineraryReport airItineraryReport) {
		this.template = template;
		this.queue = queue;
		this.airItineraryReport = airItineraryReport;
		this.vendor = null;
		this.startReport();

	}

	public void startReport() {
		consumer = new BlockingQueueConsumer(template.getConnectionFactory(), new DefaultMessagePropertiesConverter(),
				new ActiveObjectCounter<BlockingQueueConsumer>(), AcknowledgeMode.AUTO, false, 1, queue);
		consumer.start();
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String && "Check".equals((String) message)) {
			Message object = consumer.nextMessage(1000L);
			while (object != null) {
				consumer.commitIfNecessary(false);
				AirItinerary command = (AirItinerary) template.getMessageConverter().fromMessage(object);
				vendor = command.getFareInfos().get(0).getVendor();
				if (vendor.equals("VJ"))
					vendor = AirlineProvider.Vietjet.toString();
				else if (vendor.equals("BL"))
					vendor = AirlineProvider.Jetstar.toString();
				else if (vendor.equals("VN"))
					vendor = AirlineProvider.VN_Sabre.toString();
				insertMongoReport(command);
				template.convertAndSend("AirService", command.getFareInfos().get(0).getVendor(),
						new VerifyPaymentCmd(command.getId(), command.getTicketingInfo().getReservationCode(),
								command.getTicketingInfo().getExpiredDate()));
				logger.info("Send PaidReportCommand: "
						+ new VerifyPaymentCmd(command.getId(), command.getTicketingInfo().getReservationCode(),
								command.getTicketingInfo().getExpiredDate()) + "to vendor: " + vendor);
				object = consumer.nextMessage(1000L);
			}

		}

	}

	private void insertMongoReport(AirItinerary command) {
		AirTicketReport ticketReport = airItineraryReport.findById(command.getId());
		if (ticketReport == null) {
			AirBookingInfo bookingInfo = new AirBookingInfo();
			bookingInfo.setAgentInfo(command.getAgentInfo());
			bookingInfo.setContactInfo(command.getContactInfo());
			bookingInfo.setExtraServices(command.getExtraServices());
			bookingInfo.setFareInfos(command.getFareInfos());
			bookingInfo.setPassengerInfos(command.getPassengerInfos());
			try {
				airItineraryReport.add(new AirTicketReport(command.getId(), command.getTicketingInfo()
						.getReservationCode(), vendor, command.getTicketingInfo().getAmount(), bookingInfo));
			} catch (Exception ex) {
				logger.info("Insert mongo error");
				ex.printStackTrace();
			}
		}
	}
}
