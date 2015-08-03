package com.mbv.bp.common.forward;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ForwardConstants;
import com.mbv.bp.common.constants.GatewayConstants;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.model.AirtimeResultFwd;
import com.mbv.bp.common.model.AirtimeTxnInfo;
import com.mbv.bp.common.model.ForwardModel;
import com.mbv.bp.common.model.GwErrConverter;
import com.mbv.bp.common.model.PropertyModel;
import com.mbv.bp.common.vo.airtime.AtTransaction;

public class ActiveMqForwarding {
	private static Log log=LogFactory.getLog(ActiveMqForwarding.class);
	private PooledConnectionFactory connectionFactory = null;
	private JmsTemplate template = null;
	private AtomicBoolean runningFag=new AtomicBoolean(false);
	
	private static class ActiveMqForwardingHolder{
		public static ActiveMqForwarding instance=new ActiveMqForwarding();
	}
	
	public static ActiveMqForwarding getInstance(){
		return ActiveMqForwardingHolder.instance;
	}
	private ActiveMqForwarding(){
		
	}
	
	public void init(){
		if (runningFag.compareAndSet(false, true)){
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ForwardConstants.ACTIVE_MQ_URL);
			connectionFactory = new PooledConnectionFactory(activeMQConnectionFactory);
			connectionFactory.setMaxConnections(ForwardConstants.ACTIVE_MQ_POOLSIZE);
			template = new JmsTemplate(connectionFactory);
		}
	}
	
	public boolean forward(Long atTxnId) throws Exception{
		if (!runningFag.get()) init();
		
		AtTransactionDao atTransactionDao=new AtTransactionDao();
		AtTransaction atTxn=new AtTransaction();
		atTxn.setAtTxnId(atTxnId);
		atTransactionDao.selectByAtTxnId(atTxn);
		
		ForwardModel forwardModel=ForwardConstants.RESULT_FORWARD_CHANNEL_MAP.get(atTxn.getChannelId());
		
		if (forwardModel==null){
			log.info("No activemq config for this channel.|channelId : "+atTxn.getChannelId());
			return false;
		}
			
		Destination destination=null;
		
		if (StringUtils.isNotBlank(forwardModel.getQueueId()))
			 destination = new ActiveMQQueue(forwardModel.getQueueId());
		else if (StringUtils.isNotBlank(forwardModel.getTopicId()))
			 destination = new ActiveMQTopic(forwardModel.getTopicId());
		
		if (destination==null) throw new Exception("Destination is null. Please check config");
		
		send(buildAtResultFromTxn(atTxn),destination);
		return true;
	}
	
	private void send(String message,Destination destination) throws Exception {
		final String text = message;
		if (log.isDebugEnabled())
			log.debug("Sending message to activemq | message : "+message);
		template.send(destination, new MessageCreator() {
			public Message createMessage(Session session)
					throws JMSException {
				TextMessage message = session.createTextMessage(text);
				message.setStringProperty("content-type", "text/airtime-result-xml");
				return message;
			}
		});
	}
	
	private String buildAtResultFromTxn(AtTransaction atTxn) throws Exception{
		AirtimeResultFwd resultFwd=new AirtimeResultFwd();
		resultFwd.add(new PropertyModel(Attributes.ATTR_TRANSACTION_ID,String.valueOf(atTxn.getAtTxnId())));
		resultFwd.add(new PropertyModel(Attributes.ATTR_CONN_TYPE,atTxn.getConnType()));
		resultFwd.add(new PropertyModel(Attributes.ATTR_TXN_STATUS,atTxn.getTxnStatus()));
		resultFwd.add(new PropertyModel(Attributes.ATTR_ERROR_CODE,getErrorConvertion("INQUIRY","txn-error",atTxn.getErrorCode())));
		return AirtimeResultFwd.marshal(resultFwd);
	}
	
	public void destroy(){
		if (runningFag.compareAndSet(true, false)){
			connectionFactory.stop();
		}
	}
	
	private String getErrorConvertion(String method,String type,String errorCode){
	   if(StringUtils.isBlank(errorCode)) return errorCode;
	   try{
		GwErrConverter gwErrConvertor= GatewayConstants.GW_ERROR_CONVERT_MAP.get(method).get(type);
		if (gwErrConvertor.getBypassList().contains(errorCode.toUpperCase()))
			return errorCode;
		else return gwErrConvertor.getDefaultError();
	   }catch (Exception e) {
		 log.error("Unable to Convert Error for response. return source errorcode-"+errorCode+"| method-"+method+"|type-"+type+"|errorCode-"+errorCode,e);
		 return errorCode;
	   }
	}
}
