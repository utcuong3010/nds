package com.mbv.ticketsystem.check;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

public class Sender {
	final static Logger logger = Logger.getLogger(Sender.class);
	private ActiveMQConnectionFactory connectionFactory = null;
    private Connection connection = null;
    private Session session = null;
    private Destination destination = null;
    private MessageProducer producer = null;
    public Sender() {
    }
    public void sendMessage() {
        try {
        	connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("SAMPLEQUEUE");
            producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            message.setText("Hello ...This is a sample message..sending");
            producer.send(message);
            producer.close();
			session.close();
			connection.close();	
			logger.info("Sent sucsessfully: " + message.getText());
        } catch (JMSException ex) {
        	logger.info("Sent ERROR: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        Sender sender = new Sender();
        sender.sendMessage();
    }

}
