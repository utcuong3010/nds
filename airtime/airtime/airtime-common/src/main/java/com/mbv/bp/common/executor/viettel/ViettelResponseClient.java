package com.mbv.bp.common.executor.viettel;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.model.Field;
import com.mbv.bp.common.model.ViettelRequest;
import com.mbv.bp.common.util.ViettelUtils;

public class ViettelResponseClient extends Thread {
	private static Log log = LogFactory.getLog(ViettelResponseClient.class);
	private AtomicBoolean shutdownFlag=new AtomicBoolean(false);
	private ViettelClient client;
	private long lastRun=0;
	private static long printTimeInterval=30000;
	public ViettelResponseClient(ViettelClient client) {
		shutdownFlag.set(false);
		this.client=client;
	}
	@Override
	public void run() {
		long currentTime=0;
		String message;
		String errorCode;
		String responseCode;
		String messageId;
		while (true){
			try{
				
				if (shutdownFlag.get() && client.getCurPendingResponseSize()<=0){
					log.info("System is shuting down.");
					break;
				}
				client.checkPendingResponseState();
				
				message=client.getResponse();
				errorCode=ErrorCode.SUCCESS;
				responseCode=null;
				messageId=null;
				
				currentTime=System.currentTimeMillis();
				if (currentTime-lastRun>printTimeInterval){
					log.info("Pending Response Size: "+client.getCurPendingResponseSize());
					lastRun=currentTime;
				}
				
				if (message==null) {
					continue;
				}
				
				
				try {
					log.info("Recieved message:" +message);
					Map<Integer, Field> resultMap= ViettelUtils.deserializeMessage(message);
					responseCode= resultMap.get(39).getValue();
					log.info("Message content:"+resultMap);
					messageId=null;
					if (resultMap.get(11)!=null)
						messageId=resultMap.get(11).getValue();
					
					try{
						
						if (StringUtils.isNotBlank(message)){
							ViettelClientManager.getInstance().setSuspend(false);
						}
						
						if (StringUtils.isNotBlank(messageId) && AppConstants.NETWORK_RESPONSE.equals(message.substring(4,8)))
							messageId=StringUtils.EMPTY;
						}catch (Exception e) {
							log.error("unexpected error while check for network response.",e);
						}
					
					if (StringUtils.isBlank(messageId)){
						log.warn("Not found messageId in response.| Response_code: "+responseCode+"| responseMessage: "+message);
						continue;
					}
					
					if (AppConstants.VIETTEL_SETTINGS.isTxnInProgress(responseCode)){
						client.updateResponseInprogress(messageId,responseCode);
						log.info("Request "+messageId+" is in progress state. Going to retry response in next response check state. | responseCode: "+responseCode);
						continue;
					}
					
				} catch (Exception e) {
					errorCode=ErrorCode.INVALID_RESPONSE;
					log.error("Fail to process response from Viettel. Going to reset connection| error_code: "+errorCode,e);
					client.setUsable(false);
					continue;
				}
				/*
				 * Next step to process normal response.
				 */
				if (errorCode.equals(ErrorCode.SUCCESS)){
					ViettelRequest request=null;
					request=client.getPendingResponseRequest(messageId);
					ContextBase context=new ContextBase();
					
					if (request!=null){
						context.putString(Attributes.ATTR_MSISDN,request.getMsisdn());
						context.putString(Attributes.ATTR_AMOUNT,request.getAmount());
						context.putDate(Attributes.ATTR_TRANSACTION_DATE,request.getTxnDate());
						Date deliveryDate=null;
						
						if (request.getFirstDelivery()!=null){
							try{
								deliveryDate=new Date(request.getFirstDelivery());
							}catch (Exception e) {
								log.error("Exception while process delivery date",e);
							}
						}
						
						context.putDate(Attributes.ATTR_DELIVERY_DATE, deliveryDate);
						context.putDate(Attributes.ATTR_RESPONSE_DATE, new Date());
						context.putString(Attributes.ATTR_MESSAGE_TYPE,request.getRequestType());
						context.putString(Attributes.ATTR_TRANSACTION_ID,request.getTxnId());
						context.putString(Attributes.ATTR_AMOUNT,request.getAmount());
						
					}else{
						log.info("Not found viettel request in pending response for messageId| Request already processed. |messageId: "+messageId+"| responseCode: "+responseCode+"| responseMsg-"+message);
						continue;
					}
					
					context.putString(Attributes.ATTR_MESSAGE_ID, messageId);
					context.putString(Attributes.ATTR_RESPONSE_CODE, responseCode);
					context.putString(Attributes.ATTR_OPERATION_TYPE, AppConstants.VIETTEL_SETTINGS.getResponseOperation());
					context.put(Attributes.ATTR_DEST_WFP, AppConstants.VIETTEL_SETTINGS.getDnProcessorWfp());
					context.put(Attributes.ATTR_QUEUE_ID, AppConstants.VIETTEL_SETTINGS.getDnProcessorQueueId());
					context.putString(Attributes.ATTR_CONN_TYPE, AppConstants.VIETTEL_SETTINGS.getConnectionType());
					
					try {
						IExecutor<ContextBase> executor;
						executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.QUEUE_EXECUTOR);
						ContextBase result=executor.process(context);
						if (log.isDebugEnabled())
							log.debug("Push Viettel delivery request result to queue completed. | context: "+result);
						client.removePendingResponse(messageId);
					} catch (IntegrationException e) {
						log.error("Fail to process Viettel request result| context: "+context,e);
					}

				}
			}catch (Exception e) {
				log.error("Unexpected error in process.",e);
			}
		}
	}
	
	public void shutdown(){
		if (shutdownFlag.compareAndSet(false, true)){
			log.info("Going to shutdown response poller.");
		}else
			log.info("Shutdown response poller in progress.");
	}
}
