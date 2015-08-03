package com.mbv.airline.common.email;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbv.ewallet.mail.Content;
import com.mbv.ewallet.mail.MailMessage;
import com.mbv.message.Message;
import com.mbv.sms.SmsMessage;


@Component
public class MessageSender implements InitializingBean, DisposableBean {
	
	@Autowired
	private ServerConfig config;
	
	private final static Logger logger = Logger.getLogger(MessageSender.class);
	
	private ActiveMQConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Session session = null;
	private Destination destination = null;
	private MessageProducer producer = null;
	private AtomicBoolean isRunning = new AtomicBoolean(false);


	/**
	 * 
	 * @param toList
	 * @param subject
	 * @param msg
	 * @throws Exception
	 */
	public void sendMailMessage(final List<String> toList,final String subject,final String msg, String typeStatus) throws Exception {
		try{			
			MailMessage message=new MailMessage();
			message.setSubject(subject);
			message.setFrom(config.getFrom());
			Content content=new Content(new Object(),null);
			content.addHeader("X-MBV-System",config.getSystemId());
			content.addHeader("X-Mobivi-AppName", config.getAppName());
			content.addHeader("X-MBV-Sensitive",String.valueOf(true));
			content.setData(msg);
			content.setType("text/html");
			
			String preToList = config.getTo();
			if(typeStatus.equals("DIFFERENCE"))
				preToList += "-helpdesk@mobivi.com";
			
			message.setToRecipents(toList==null?Arrays.asList(preToList.split("-")):toList);
			message.addBodyPart(content);
			//send mail
			if(isRunning.get() == true) {
				sendMailMessage(message);
			} else {
				afterPropertiesSet();
				sendMailMessage(message);//resend
			}
		}catch(Exception ex){

			throw ex;
		}
	}

	/***
	 * send
	 * @param sendSmg
	 * @throws JMSException
	 */
	private void sendMailMessage(final MailMessage sendSmg) throws Exception {
		String mailcontent = "";
		try {
			mailcontent = MailMessage.marshal(sendSmg);
		} catch (Exception e) {
			logger.error("failed to serialize mailMsg" + e.getMessage()); 
			throw e;
		}
		if (logger.isInfoEnabled())
			logger.info("sending Mail message: " + mailcontent);
		try{
			Message mailMessage = new Message();
			mailMessage.setContentType("text/email-xml");
			mailMessage.setContentText(mailcontent);
			
			sendMessage(mailMessage);
			
		}catch(Exception ex){
			logger.error("\nCannot send mail message\n" + ex.getMessage());
			throw ex;
		}
	}
	
	/**
	 * 
	 * @param mobile
	 * @param message
	 * @param sensitive
	 * @throws Exception
	 */
	public void sendSMSMessage(final String mobile, final String message,
			final boolean sensitive) throws Exception {
		try {
			String mobileNumber = mobile;
			if (mobileNumber != null && !mobileNumber.isEmpty()) {
				mobileNumber = mobileNumber.trim().replaceAll("[ ,\\.]", "");
				if (!mobileNumber.startsWith("84")) {
					if (!mobileNumber.startsWith("0"))
						mobileNumber = "84" + mobileNumber;
					else
						mobileNumber = "84" + mobileNumber.substring(1);
				}
			}
			SmsMessage sms = new SmsMessage();
			sms.setToAddress(mobileNumber);
			sms.setHeader("X-MBV-System", config.getSystemId());
			sms.setHeader("X-Mobivi-AppName", config.getAppName());
			sms.appendHeader("X-MBV-Sensitive", String.valueOf(sensitive));
			sms.setContentType("text/plain");
			sms.setContentText(message);
			sms.setContentId(UUID.randomUUID().toString());

			String content;
			try {
				content = SmsMessage.marshal(sms);
			} catch (IOException e) {
				logger.error("failed to serialize sms" + e.getMessage()); 
				throw new Exception("failed to serialize sms", e);
			}
			if (logger.isDebugEnabled())
				logger.debug("sending SMS message: " + content);

			Message smsMessage = new Message();
			smsMessage.setContentType("text/sms-response-xml");
			smsMessage.setContentText(content);
			
			sendMessage(smsMessage);
		} catch (Exception ex) {
			logger.error("Cannot send message\n" + ex.getMessage());
		}
	}
	
	private void sendMessage(Message msg) throws JMSException {
		TextMessage text = session.createTextMessage();
		text.setStringProperty("content-type", msg.getContentType());
		text.setText(msg.getContentText());
		if (logger.isDebugEnabled())
			logger.debug("sending " + msg.getContentType() + " " + msg.getContentText() + " to ActiveMQ " + destination);
		producer.send(destination, text);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			if (logger.isInfoEnabled())
				logger.info("Initializing ActiveMQ....");
			connectionFactory = new ActiveMQConnectionFactory("tcp://"+ config.getServerAddress()+"");
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destination = session.createQueue(config.getQueueName());
			producer = session.createProducer(destination);	
			
			//set running
			isRunning.set(true);
			
		} catch (JMSException ex) {
			logger.error("\nCannot Initializing ActiveMQ: " +ex.getMessage());		
			isRunning.set(false);
		}
	}
	
	@Override
	public void destroy() throws Exception {
		try {
			
			if(producer != null) producer.close();
			if(session != null) session.close();
			if(connection != null) connection.close();	
								
		} catch (JMSException ex) {
			logger.error("\nCannot send mail message: " +ex.getMessage());			
		}
		
	}
}
