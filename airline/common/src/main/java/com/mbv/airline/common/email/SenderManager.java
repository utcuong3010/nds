package com.mbv.airline.common.email;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbv.airline.common.info.AirItinerary;


/***
 * 
 * @author cuongtv
 *
 */
@Component
public class SenderManager{

	final static Logger logger = Logger.getLogger(SenderManager.class);
	
	@Autowired
	private MessageSender mailSender;	
	
	/***
	 * send message to queue
	 * @param itinerary
	 */
	public void sendMessage(String subject,AirItinerary itinerary) {
		try {
			String routingKey = itinerary.getFareInfos().get(0).getVendor();
			if (routingKey.equals("BL"))
				routingKey = "Jetstar";
			else if (routingKey.equals("VJ"))
				routingKey = "Vietjet";
			else
				routingKey = "VN_Sabre";
		
			String typeStatus = "SIMILAR";
			mailSender.sendMailMessage(null, routingKey + " - " + subject, "itinerary: " + itinerary.toString(),typeStatus);
			
			
		} catch (Exception ex) {
			logger.error("Send Message to Mail Server with Error:" + ": ");
		}
	}
	
	/**
	 * do: send mail for helpdesk
	 * @param subject
	 * @param itinerary
	 * @param typeStatus
	 */
	public void sendMessage(String subject,AirItinerary itinerary, String typeStatus) {
		try {
			String routingKey = itinerary.getFareInfos().get(0).getVendor();
			if (routingKey.equals("BL"))
				routingKey = "Jetstar";
			else if (routingKey.equals("VJ"))
				routingKey = "Vietjet";
			else
				routingKey = "Vietnam Airlines";
			
			String content = "Dear HelpDesk," + "\n" + "Please help team NDS check booking = "+ itinerary.getTicketingInfo().getReservationCode() +
					" (paid)." + "\n" + "NDS Team";
			// sendmail
			mailSender.sendMailMessage(null, subject + " - " + routingKey, content, typeStatus);
		} catch (Exception ex) {
			logger.error("Send Message to Mail Server with Error:" + ": ");
		}
	}
	
	
	public void sendMailMessage(List<String> toList,String subject, String msg){
		try{
			String typeStatus = "SIMILAR";
			mailSender.sendMailMessage(toList, subject, msg, typeStatus);
		} catch (Exception ex) {
			logger.error("Send Mail Message to Server with Error:" + ": ");
		}
	}
	
	public void sendSmsMessage(String mobile, String message,
			 boolean sensitive) {
		try{
			mailSender.sendSMSMessage(mobile, message, sensitive);
		} catch (Exception ex) {
			logger.error("Send SMS Message to Server with Error:" + ": ");
		}
	}

}
