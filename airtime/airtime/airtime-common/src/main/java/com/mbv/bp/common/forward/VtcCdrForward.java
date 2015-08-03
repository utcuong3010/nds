package com.mbv.bp.common.forward;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.CdrSyncDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.NotificationSettings.TEMPLATE_TYPE;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.vo.airtime.CdrSync;

public class VtcCdrForward implements IForward {
	private static Log log=LogFactory.getLog(VtcCdrForward.class);
	private Map<Long, CdrSync> errSeqMap;
	private AtomicBoolean errSeqNotiSent=new AtomicBoolean(false);
	private Object lock =new Object();
	
	
	private static class  VnPayCdrForwardHolder{
		private static VtcCdrForward instance=new VtcCdrForward();
	}

	private VtcCdrForward() {
		errSeqMap=new ConcurrentHashMap<Long, CdrSync>();
	}
	
	public static VtcCdrForward getInstance(){
		return  VnPayCdrForwardHolder.instance;
	}
	
	public boolean execute(ContextBase context){
		boolean status=false;
		ContextBase ctxRequest=new ContextBase();
		try{
			ctxRequest.putAll(context);
			ctxRequest.put(Attributes.ATTR_QUEUE_ID, AppConstants.VTC_SETTINGS.getCdrProcessorQueueId());
			ctxRequest.put(Attributes.ATTR_DEST_WFP, AppConstants.VTC_SETTINGS.getCdrProcessorWfp());
//				backup txn id
			ctxRequest.put(Attributes.ATTR_ORG_TRANSACTION_ID,ctxRequest.get(Attributes.ATTR_TRANSACTION_ID));
			String seq=IdGeneratorFactory.getInstance().getAirTimeIdGenentor().generateId();
			ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, AppUtils.getAtTxnId(ctxRequest.getDate(Attributes.ATTR_TRANSACTION_DATE), seq));
			IExecutor<ContextBase> executor=  ExecutorFactory.getInstance().getExecutor(ExecutorFactory.QUEUE_EXECUTOR);
			executor.process(ctxRequest);
			status=true;
		}catch (Exception e) {
			log.error("Error on process for Vnpay Cdr.|ctxRequest-" +ctxRequest+"|Error message is:" + e.getMessage(),e);
			status=false;
		}
		return status;
	}
	
	public boolean process(ContextBase context){
		return cdrProcess(context);
	}
	
	
	private boolean cdrProcess(ContextBase context) {
		boolean result=false;
		try{
			CdrSyncDao dataDao=new CdrSyncDao();
			CdrSync cdrSync=getCrdData(context);
			if (cdrSync.getAtTxnId()!=null){
				if (AppConstants.TXN_STATUS_SUCCESS.equals(cdrSync.getStatus())){
					errSeqMap.clear();
					errSeqNotiSent.set(false);
				}else{
					synchronized (lock){
						errSeqMap.put(cdrSync.getAtTxnId(), cdrSync);
						checkForAlert(context);
						if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(cdrSync.getStatus())){
							AppConstants.NOTIFICATION_SETTINGS.sendNotification(TEMPLATE_TYPE.UNKNOW_TXN_STATUS_TEMPLATE, cdrSync);
							cdrSync.setStatus(AppConstants.TXN_STATUS_SUCCESS);
						}
					}
					
				}
//				dataDao.insert(cdrSync);
				result=true;
			
			}else{
				result=false;	
				log.error("Fail to process CdrSync");
			}
			
		}catch (Exception e) {
			log.error("Fail to insert record to CdrSync",e);
			result=true;
		}
		return result;
	}
	
	
	private void checkForAlert(ContextBase context) {
		if ( errSeqMap.size()>= AppConstants.VTC_SETTINGS.getMaxSeqError()){
			if (errSeqNotiSent.compareAndSet(false, true))
				AppConstants.NOTIFICATION_SETTINGS.sendNotification(TEMPLATE_TYPE.SEQ_ERROR_TEMPLATE, errSeqMap);
			errSeqMap.clear();
		}
	}

	private CdrSync getCrdData(ContextBase context){
		
		CdrSync cdrSync=new CdrSync();
		
		cdrSync.setAtTxnId(context.getLong(Attributes.ATTR_ORG_TRANSACTION_ID));
		cdrSync.setTxnDate(context.getDate(Attributes.ATTR_TRANSACTION_DATE));
		cdrSync.setProviderId(AppConstants.VTC_SETTINGS.getConnectionType());
		cdrSync.setMsisdn(context.getString(Attributes.ATTR_MSISDN));
		cdrSync.setAmount(context.getInt(Attributes.ATTR_AMOUNT));
		cdrSync.setMessageId(context.getString(Attributes.ATTR_MESSAGE_ID));
		cdrSync.setErrorCode(context.getString(Attributes.ATTR_RESPONSE_CODE));
//		cdrSync.setExt4(context.get(Attributes.ATTR_BALANCE_AFTER_TXN));
		if (AppConstants.VTC_SETTINGS.isSuccessResponse(cdrSync.getErrorCode()))
			cdrSync.setStatus(AppConstants.TXN_STATUS_SUCCESS);
		else if (AppConstants.VTC_SETTINGS.isUnknownResponse(cdrSync.getErrorCode()))
				cdrSync.setStatus(AppConstants.TXN_STATUS_UNKNOWN);	
		else if (AppConstants.VTC_SETTINGS.isErrorResponse(cdrSync.getErrorCode()))
				cdrSync.setStatus(AppConstants.TXN_STATUS_ERROR);
		else 
			cdrSync.setStatus(AppConstants.TXN_STATUS_UNKNOWN);	
		cdrSync.setpTxnId("");
		cdrSync.setpStatus("");
		return cdrSync;
	}
}
