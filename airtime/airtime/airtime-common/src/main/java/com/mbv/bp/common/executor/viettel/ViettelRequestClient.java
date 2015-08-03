package com.mbv.bp.common.executor.viettel;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.exception.RequestException;
import com.mbv.bp.common.exception.ShuttingDownException;
import com.mbv.bp.common.exception.TimeOutException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.model.ViettelRequest;

public class ViettelRequestClient extends Thread {
	private static Log log = LogFactory.getLog(ViettelResponseClient.class);
	private AtomicBoolean shutdownFlag =new AtomicBoolean(false);
	ViettelClient client;
	private long lastRun=0;
	private static long printTimeInterval=30000;
	public ViettelRequestClient(ViettelClient client) {
		shutdownFlag.set(false);
		this.client=client;
	}
	
	@Override
	public void run() {
		String errorCode;
		long currentTime=0;
		while (true){
			try{
				errorCode=ErrorCode.SUCCESS;
				if (shutdownFlag.get() && (client.getCurPendingResponseSize()<=0) && (client.getCurReqQueue()<=0)){
					log.info("System is shuting down.");
					break;
				}
				
				ViettelRequest request=client.getPollViettelRequest();
				currentTime=System.currentTimeMillis();
				if (currentTime-lastRun>printTimeInterval){
					log.info("Pending Request Size: "+client.getCurReqQueue());
					lastRun=currentTime;
				}
				
				
				if (request==null){
					client.keepAliveConnection();
					continue;
				}
				
				String requestType=request.getRequestType();
		
				try {
					if (shutdownFlag.get() && (AppConstants.TOPUP_REQUEST.equals(requestType)))
						throw new ShuttingDownException("System is going to shutdown.");
					client.sendRequest(request);
				}catch (TimeOutException e) {
					errorCode=ErrorCode.REQUEST_TIME_OUT;
				}catch (RequestException e) {
					errorCode=ErrorCode.DELIVERY_REQUEST_ERROR;
					log.info("Fail to send request| error_code: "+ errorCode,e);
					client.setUsable(false);
				} catch (ShuttingDownException e) {
					errorCode=ErrorCode.SYSTEM_SHUTINGDOWN;
					log.info("Fail to send request| error_code: "+ errorCode,e);
				}
				
				/*
				 * if request is topup and send request error, going to update txn status
				 */
				if (!errorCode.equals(ErrorCode.SUCCESS) && (AppConstants.TOPUP_REQUEST.equals(requestType))){
					ContextBase context=new ContextBase();
					context.putString(Attributes.ATTR_TRANSACTION_ID, request.getTxnId());
					context.putString(Attributes.ATTR_MESSAGE_ID, request.getTrace());
					context.putString(Attributes.ATTR_RESPONSE_CODE, errorCode);
					context.putString(Attributes.ATTR_REQUEST_MESSAGE, request.getRequestMessage());
					context.putString(Attributes.ATTR_OPERATION_TYPE, AppConstants.VIETTEL_SETTINGS.getRequestOperation());
					context.put(Attributes.ATTR_DEST_WFP, AppConstants.VIETTEL_SETTINGS.getDnProcessorWfp());
					context.put(Attributes.ATTR_QUEUE_ID, AppConstants.VIETTEL_SETTINGS.getDnProcessorQueueId());
					context.put(Attributes.ATTR_QUEUE_REQUEST_ID,context.get(Attributes.ATTR_TRANSACTION_ID));
					try {
						IExecutor<ContextBase> executor;
						executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.QUEUE_EXECUTOR);
						ContextBase result=executor.process(context);
						if (log.isDebugEnabled())
							log.debug("Push Viettel delivery request result to queue completed. | context: "+result);
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
			log.info("Going to shutdown request poller.");
		}else
			log.info("Shutdown request poller in progress.");
	}
}
