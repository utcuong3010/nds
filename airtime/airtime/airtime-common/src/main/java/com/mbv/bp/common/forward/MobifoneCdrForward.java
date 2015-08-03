package com.mbv.bp.common.forward;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.airtime.CdrSyncDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.NotificationSettings.TEMPLATE_TYPE;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.vo.airtime.CdrSync;

public class MobifoneCdrForward implements IForward {
	private static Log log=LogFactory.getLog(MobifoneCdrForward.class);
	private Map<Long, CdrSync> errMap;
	private AtomicBoolean errSeqNotiSent=new AtomicBoolean(false);
	private Map<Long, CdrSync> errSeqMap;
	private Object lock =new Object();
	
	
	private static class MobifoneCdrForwardHolder{
		private static MobifoneCdrForward instance=new MobifoneCdrForward();
	}

	private MobifoneCdrForward() {
		errMap=new ConcurrentHashMap<Long, CdrSync>();
		errSeqMap=new ConcurrentHashMap<Long, CdrSync>();
	}
	
	public static MobifoneCdrForward getInstance(){
		return MobifoneCdrForwardHolder.instance;
	}
	
	public boolean execute(ContextBase context){
			return mobifoneBuyForwarding(context);
	}
	
	public boolean process(ContextBase context){
		boolean result =false;
		if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equals(context.getString(Attributes.ATTR_CONN_TYPE))){
			return mobifoneCdrProcess(context);
		}else{
			log.info("No process for provider found!| context:"+context);
		}
		return result;
	}
	
	private boolean mobifoneBuyForwarding(ContextBase context) {
		boolean status=false;
		ContextBase ctxRequest=new ContextBase();
		try{
			ctxRequest.putAll(context);
			ctxRequest.put(Attributes.ATTR_CONN_TYPE, AppConstants.MOBIFONE_SETTINGS.getConnectionType());
			ctxRequest.put(Attributes.ATTR_QUEUE_ID, AppConstants.MOBIFONE_SETTINGS.getCdrMobifoneProcessorQueueId());
			ctxRequest.put(Attributes.ATTR_DEST_WFP, AppConstants.MOBIFONE_SETTINGS.getCdrMobifoneProcessorWfp());
//				backup txn id
			ctxRequest.put(Attributes.ATTR_ORG_TRANSACTION_ID,ctxRequest.get(Attributes.ATTR_TRANSACTION_ID));
			String seq=IdGeneratorFactory.getInstance().getAirTimeIdGenentor().generateId();
			ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, AppUtils.getAtTxnId(ctxRequest.getDate(Attributes.ATTR_TRANSACTION_DATE), seq));
			IExecutor<ContextBase> executor=  ExecutorFactory.getInstance().getExecutor(ExecutorFactory.QUEUE_EXECUTOR);
			executor.process(ctxRequest);
			status=true;
		}catch (Exception e) {
			log.error("Error on process for Mobifone Cdr.|ctxRequest-" +ctxRequest+"|Error message is:" + e.getMessage(),e);
			status=false;
		}
		return status;
	}
	
	private boolean mobifoneCdrProcess(ContextBase context) {
		boolean result=false;
		try{
			CdrSyncDao dataDao=new CdrSyncDao();
			CdrSync cdrSync=getMobiCrdData(context);
			if (cdrSync.getAtTxnId()!=null){
				if (ErrorCode.DELIVERY_RESPONSE_ERROR.equals(cdrSync.getErrorCode())){
//		send no response notification
					AppConstants.NOTIFICATION_SETTINGS.sendNotification(TEMPLATE_TYPE.UNKNOW_TXN_STATUS_TEMPLATE, cdrSync);
					dataDao.insert(cdrSync);
				}else{
//		normal buy error.					
					dataDao.insert(cdrSync);
					if ("0".equalsIgnoreCase(cdrSync.getErrorCode())){
						errSeqMap.clear();
						errSeqNotiSent.set(false); 
					}else{
						errSeqMap.put(cdrSync.getAtTxnId(), cdrSync);
						errMap.put(cdrSync.getAtTxnId(), cdrSync);
						synchronized (lock){
							checkForAlert(context);
						}
					}
					result=true;
				}
			
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
		if ( errSeqMap.size()>= AppConstants.MOBIFONE_SETTINGS.getMaxSeqError()){
			if (errSeqNotiSent.compareAndSet(false, true))
				AppConstants.NOTIFICATION_SETTINGS.sendNotification(TEMPLATE_TYPE.MOBI_SEQ_ERROR_TEMPLATE,errSeqMap);
			errSeqMap.clear();
		}
		
		if ( errMap.size()>= AppConstants.MOBIFONE_SETTINGS.getMaxError()){
			AppConstants.NOTIFICATION_SETTINGS.sendNotification(TEMPLATE_TYPE.MOBI_ERROR_TEMPLATE,errMap);
			errMap.clear();
		}
		
	}

	private CdrSync getMobiCrdData(ContextBase context){
		
		CdrSync cdrSync=new CdrSync();
		cdrSync.setMessageId(context.getString(Attributes.ATTR_MESSAGE_ID));
		cdrSync.setTxnDate(context.getDate(Attributes.ATTR_TRANSACTION_DATE));
		cdrSync.setProviderId(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
		if (context.getInt(Attributes.ATTR_RESPONSE_CODE)==0){
			cdrSync.setStatus(AppConstants.TXN_STATUS_SUCCESS);
		}else{
			cdrSync.setStatus(AppConstants.TXN_STATUS_ERROR);
		}
		cdrSync.setMsisdn(context.getString(Attributes.ATTR_MSISDN));
		cdrSync.setAmount(context.getInt(Attributes.ATTR_AMOUNT));
		cdrSync.setErrorCode(context.getString(Attributes.ATTR_RESPONSE_CODE));
		cdrSync.setpTxnId("");
		cdrSync.setpStatus("");
//		using back up txn id
		cdrSync.setAtTxnId(context.getLong(Attributes.ATTR_ORG_TRANSACTION_ID));
		return cdrSync;
	}
}
