package com.mbv.anypay.queue.core;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mbv.anypay.queue.utils.SimIdFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.dao.airtime.SimInfoDao;
import com.mbv.bp.common.dao.airtime.SimTransactionDao;
import com.mbv.bp.common.dao.airtime.SmsContentDao;
import com.mbv.bp.common.executor.client.SimConnectionFactory;
import com.mbv.bp.common.forward.ActiveMqForwarding;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.PduSms;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.QueueRequest;
import com.mbv.bp.common.vo.airtime.SimInfo;
import com.mbv.bp.common.vo.airtime.SimTransaction;
import com.mbv.bp.common.vo.airtime.SmsContent;


public class RequestProcessor implements Callable<Boolean>{
	private static Log log=LogFactory.getLog(RequestProcessor.class);
	private QueueRequest request;
	private QueueServer qserver;
	
	public RequestProcessor(QueueServer qserver, QueueRequest request) {
		this.request = request;
		this.qserver = qserver;
	}
	@Override
	public Boolean call() throws Exception {
		
		boolean result=false;
		String errorCheck="";
		long startTime 	= System.currentTimeMillis();
		ContextBase context=request.getContext();
		String simMsisdn=context.get(Attributes.ATTR_COM_ID);
		try{
			RequestDbFactory.getInstance().delete(request);
			
			if (AppConstants.MOBILE_TOPUP.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_TYPE))){
				
				validateTxnStatus(context);
				
				if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode()))
					if (AppUtils.isVaildTimeOut(context, true))
						topup(context);
	
				if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode())){
					if (AppConstants.TXN_STATUS_DELIVERING.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS)) ||
							AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
						processSmsResponse(context);
					}
				}
				
				
				if (!ErrorCode.ALREDY_EXISTED.equalsIgnoreCase(context.getErrorCode())){
					if (!ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode()) || AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
						String txnErrorCode=StringUtils.isBlank(context.get(Attributes.ATTR_TXN_ERROR_CODE))?context.getErrorCode():context.get(Attributes.ATTR_TXN_ERROR_CODE);
						errorCheck=txnErrorCode;
						updateAtTxn(context,AppConstants.TXN_STATUS_ERROR,txnErrorCode);
					}else {
						String txnErrorCode=StringUtils.isBlank(context.get(Attributes.ATTR_TXN_ERROR_CODE))?context.getErrorCode():context.get(Attributes.ATTR_TXN_ERROR_CODE);
						updateAtTxn(context,AppConstants.TXN_STATUS_SUCCESS,txnErrorCode);
						errorCheck=txnErrorCode;
					}
				}
			}
		}catch (Exception e) {
			log.info("unexpected error found.",e);
		}
		
		if (StringUtils.isNotBlank(simMsisdn)){
			if (ErrorCode.CONNECTION_FAILED.equalsIgnoreCase(errorCheck)|| AppConstants.YES_FLAG.equalsIgnoreCase(context.get("no-response"))){
				try{
					log.info("Sleep 60s for restart simcard");
					Thread.sleep(60000L);
				}catch (Exception e) {
				}
			}
			SimConnectionManager.getInstance().checkInSim(simMsisdn);
		}
		log.info(request.getQueueId()+" process request "+request.getRequestId()+" errorCode-"+context.getErrorCode()+"| processed time(ms):"+(System.currentTimeMillis()-startTime)+"| queueSize-"+qserver.getCurrentQueueSize());
		return result;
	}
	
	public void updateAtTxn(ContextBase context, String txnStatus, String txnErrorCode){
		try{
			AtTransaction atTxn=new AtTransaction();
			atTxn.setAtTxnId(context.getLong(Attributes.ATTR_TRANSACTION_ID));	
			atTxn.setTxnStatus(txnStatus);
			atTxn.setErrorCode(txnErrorCode);
			atTxn.setUpdatedDate(new Date());
			atTxn.setUpdatedBy(AppConstants.APP_ID);
			AtTransactionDao dao=new AtTransactionDao(); 
			dao.updateByAtTxnId(atTxn);
			
			if (!AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(txnStatus)){
				BillingErrorHandler billingErrorHandler=new BillingErrorHandler();
				if (billingErrorHandler.handle(context, null)){
					log.error("**** Fail to process billingErrorHandler... *****");
				}
			}
			
			try{
				ActiveMqForwarding.getInstance().forward(context.getLong(Attributes.ATTR_TRANSACTION_ID));
			}catch (Exception e) {
				log.error("Fail to forward result to activemq",e);
			}
			
		}catch (Exception e) {
			log.error("Fail to refund for error status of at_transaction| context:"+context,e);
		}
	}
	
	public void validateTxnStatus(ContextBase context){
		boolean isValidAmount=true;
		boolean isNotExistTxn=true;
		try{
			String comId=context.get(Attributes.ATTR_COM_ID);
			SimTransaction simTransaction=new SimTransaction();
			String msisdn=context.get(Attributes.ATTR_MSISDN);
//			long amount=context.getLong(Attributes.ATTR_AMOUNT);
			long txnId=context.getLong(Attributes.ATTR_TRANSACTION_ID);
			simTransaction.setMsisdn(msisdn);
			simTransaction.setSimId(comId);
			simTransaction.setTxnType(AppConstants.ANYPAY_SETTINGS.TOPUP_TXN);
			SimTransactionDao simTransactionDao = new SimTransactionDao();
			List<SimTransaction> listSimTxn = simTransactionDao.selectPSStatusByMsisdnSimIdTxnType(simTransaction);
			if (listSimTxn!=null)
				for (SimTransaction simTxn:listSimTxn){
					if (simTxn.getTxnId()==txnId){
						isNotExistTxn=false;
						break;
					}else{ 
						isValidAmount=false;
					}
					
				}
			
		}catch (Exception e) {
			context.setErrorCode(ErrorCode.DATABASE_EXCEPTION);
			log.error("Fail to validate txn from database.",e);
		}
		
		if (!isNotExistTxn){
			context.setErrorCode(ErrorCode.ALREDY_EXISTED);
		}else if (!isValidAmount){
			context.setErrorCode(ErrorCode.IN_PROGRESS_EXCEPTION);
		}
	}  
	
	public boolean processSmsResponse(ContextBase context){
		boolean isHasResult=false;
		SmsContentDao smsContentDao=new SmsContentDao();
		SimTransactionDao simTransactionDao=new SimTransactionDao(); 
		SimTransaction simTransaction;
		long startTime=System.currentTimeMillis();
		List<String> msgIndexs=new ArrayList<String>();
		while (startTime+AppConstants.ANYPAY_SETTINGS.getProcessSmsMaxTime()>System.currentTimeMillis()){
			try{
				ContextBase ctxRequest=new ContextBase();
				ContextBase response=new ContextBase();
				ctxRequest.put(Attributes.ATTR_TXN_TYPE, AppConstants.ANYPAY_SETTINGS.GET_SMS);
				ctxRequest.put(Attributes.ATTR_COM_ID, context.get(Attributes.ATTR_COM_ID));
				try {
					response=SimConnectionFactory.getInstance().process(ctxRequest);
				} catch (Exception e1) {
					response=null;
				}
				if 	(response==null) continue;
				if (StringUtils.isBlank(response.get(Attributes.ATTR_RESULT_INFO))) continue; 
				Gson gson = new Gson();
				Type type=new TypeToken<List<PduSms>>(){}.getType();
				List<PduSms> listResult = gson.fromJson(response.get(Attributes.ATTR_RESULT_INFO),type);
				SmsContent smsContent;
				msgIndexs=new ArrayList<String>();
				for (PduSms pduSms:listResult){
					try{
						smsContent=AppConstants.ANYPAY_SETTINGS.paser(pduSms);
						smsContent.setSimId(context.get(Attributes.ATTR_COM_ID));
						msgIndexs.add(""+pduSms.getIndex());
						if (smsContent.getMessageId()==null){
							smsContent.setMessageId(SimIdFactory.getInstance().getSimIdGenerator().generateId());
						}
	//					check for fraud
						if (AppConstants.ANYPAY_SETTINGS.TOPUP_TXN.equalsIgnoreCase(smsContent.getTxnType()) && 
								AppConstants.NO_FLAG.equalsIgnoreCase(smsContent.getFraudStatus())){
							if (AppConstants.TXN_STATUS_ERROR.equals(smsContent.getTxnStatus())){
								if (context.getString(Attributes.ATTR_MSISDN).equalsIgnoreCase(smsContent.getMsisdn())){
									smsContent.setProcessed("Y");
									smsContentDao.insert(smsContent);
									processSimTxn(
											context.get(Attributes.ATTR_COM_ID),
											smsContent.getMessageId(),
											context.getLong(Attributes.ATTR_TRANSACTION_ID),
											AppConstants.TXN_STATUS_ERROR,
											context.getLong(Attributes.ATTR_AMOUNT)
											);
									context.put(Attributes.ATTR_TXN_STATUS, AppConstants.TXN_STATUS_ERROR);
									context.put(Attributes.ATTR_TXN_ERROR_CODE, ErrorCode.TRANSACTION_FAILED);
									isHasResult=true;
								}else{
									simTransaction=new SimTransaction();
									simTransaction.setMsisdn(smsContent.getMsisdn());
									simTransaction.setSimId(context.getString(Attributes.ATTR_COM_ID));
									simTransaction.setTxnType(AppConstants.ANYPAY_SETTINGS.TOPUP_TXN);
									List<SimTransaction> simTxns=simTransactionDao.selectPSStatusByMsisdnSimIdTxnType(simTransaction);
									if (simTxns!=null && simTxns.size()>0){
	
										smsContent.setProcessed("Y");
										smsContentDao.insert(smsContent);
										
										processSimTxn(
												context.get(Attributes.ATTR_COM_ID),
												smsContent.getMessageId(),
												simTxns.get(0).getTxnId(),
												AppConstants.TXN_STATUS_ERROR,
												simTxns.get(0).getAmount()
												);
									}else{
										smsContent.setProcessed("N");
										smsContentDao.insert(smsContent);
									}
								}
							}else{
								if (context.getString(Attributes.ATTR_MSISDN).equalsIgnoreCase(smsContent.getMsisdn())){
									
									smsContent.setProcessed("Y");
									smsContentDao.insert(smsContent);
									
									processSimTxn(
											context.get(Attributes.ATTR_COM_ID),
											smsContent.getMessageId(),
											context.getLong(Attributes.ATTR_TRANSACTION_ID),
											AppConstants.TXN_STATUS_SUCCESS,
											context.getLong(Attributes.ATTR_AMOUNT)
											);
									
									context.put(Attributes.ATTR_TXN_STATUS, AppConstants.TXN_STATUS_SUCCESS);
									context.put(Attributes.ATTR_TXN_ERROR_CODE, ErrorCode.SUCCESS);
									
									isHasResult=true;
									
								}else{
									simTransaction=new SimTransaction();
									simTransaction.setMsisdn(smsContent.getMsisdn());
									simTransaction.setSimId(context.getString(Attributes.ATTR_COM_ID));
									simTransaction.setTxnType(AppConstants.ANYPAY_SETTINGS.TOPUP_TXN);
									List<SimTransaction> simTxns=simTransactionDao.selectPSStatusByMsisdnSimIdTxnType(simTransaction);
									if (simTxns!=null && simTxns.size()>0){
	
										smsContent.setProcessed("Y");
										smsContentDao.insert(smsContent);
										
										processSimTxn(
												context.get(Attributes.ATTR_COM_ID),
												smsContent.getMessageId(),
												simTxns.get(0).getTxnId(),
												AppConstants.TXN_STATUS_SUCCESS,
												simTxns.get(0).getAmount()
												);
									}else{
										smsContent.setProcessed("N");
										smsContentDao.insert(smsContent);
									}
								}
							}
						}else{
							smsContent.setProcessed("N");
							smsContentDao.insert(smsContent);
						}
					}catch (Exception e) {
						log.error("Fai to process sms response:"+pduSms,e);
					}
				}
//				delete sms msgIndexs
				if (msgIndexs.size()>0)
					deleteSms(context.get(Attributes.ATTR_COM_ID), msgIndexs);
			}catch (Exception e) {
				log.error("Fai to process sms response.",e);
			}
			if (isHasResult) break;
		}
		if (!isHasResult){
			context.put(Attributes.ATTR_TXN_STATUS, AppConstants.TXN_STATUS_SUCCESS);
			context.put(Attributes.ATTR_TXN_ERROR_CODE, ErrorCode.DELIVERY_RESPONSE_ERROR);
		}
		return isHasResult;
	}
	
	public void topup(ContextBase context ){
		try {
			
			ContextBase ctxRequest=new ContextBase();
			ctxRequest.put(Attributes.ATTR_TXN_TYPE, AppConstants.ANYPAY_SETTINGS.TOPUP_TXN);
			ctxRequest.put(Attributes.ATTR_COM_ID, context.get(Attributes.ATTR_COM_ID));
			ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
			ctxRequest.put(Attributes.ATTR_TRANSACTION_DATE, context.get(Attributes.ATTR_TRANSACTION_DATE));
			ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
			ctxRequest.put(Attributes.ATTR_AMOUNT, context.get(Attributes.ATTR_AMOUNT));
			ctxRequest.put(Attributes.ATTR_SIM_AMOUNT, context.get(Attributes.ATTR_SIM_AMOUNT));
			ctxRequest.put(Attributes.ATTR_SIM_LOCK_AMOUNT, context.get(Attributes.ATTR_SIM_LOCK_AMOUNT));
			ContextBase response=SimConnectionFactory.getInstance().process(ctxRequest);
			context.put(Attributes.ATTR_TXN_STATUS, response.getString(Attributes.ATTR_TXN_STATUS));
			context.put(Attributes.ATTR_TXN_ERROR_CODE, response.getString(Attributes.ATTR_TXN_ERROR_CODE));
			context.put(ErrorCode.SUCCESS, response.getString(ErrorCode.SUCCESS));
		} catch (Exception e) {
			try{
				SimTransactionDao simTransactionDao=new SimTransactionDao();
				SimTransaction simTransaction=new SimTransaction();
				simTransaction.setTxnId(context.getLong(Attributes.ATTR_TRANSACTION_ID));
				if (simTransactionDao.select(simTransaction)){
					context.put(Attributes.ATTR_TXN_STATUS, simTransaction.getTxnStatus());
					context.put(Attributes.ATTR_TXN_ERROR_CODE, simTransaction.getErrorCode());
					context.setErrorCode(ErrorCode.SUCCESS);
					context.put("no-response", AppConstants.YES_FLAG);
				}else{
					context.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
				}
			}catch (Exception e1) {
				context.setErrorCode(ErrorCode.DATABASE_EXCEPTION);
			}
		}
	}
	
	public void processSimTxn(String simId,String messageId,long txnId,String txnStatus,long amount) throws DaoException{
		SimTransactionDao simTransactionDao=new SimTransactionDao();
		SimInfoDao simInfoDao=new SimInfoDao();
		SimInfo simInfo=new SimInfo();
		
		SimTransaction simTransaction=new SimTransaction();
		simTransaction.setMessageId(messageId);
		simTransaction.setTxnStatus(txnStatus);
		simTransaction.setTxnId(txnId);
		
		simInfo.setMsisdn(simId);
		
		if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(txnStatus)){
			simInfo.setLockAmount(-1*amount); //plus value
			simInfo.setCurrentAmount(-1*amount); 
		}else{
			simInfo.setLockAmount(-1*amount); //plus value
			simInfo.setCurrentAmount(0L); 
			simTransaction.setBilling(AppConstants.NO_FLAG);
		}
		
		simInfoDao.updateCurrentLockAmountByMsisdn(simInfo);
		simTransactionDao.update(simTransaction);
	}
	public void deleteSms(String comId, List<String> msgIndexs){
		try {
			ContextBase ctxRequest=new ContextBase();
			ctxRequest.put(Attributes.ATTR_TXN_TYPE, AppConstants.ANYPAY_SETTINGS.DEL_SMS);
			ctxRequest.put(Attributes.ATTR_COM_ID, comId);
			ctxRequest.put(Attributes.ATTR_MESSAGE_INDEXS, StringUtils.join(msgIndexs.toArray(), ","));
			ContextBase response=SimConnectionFactory.getInstance().process(ctxRequest);
			log.info("delete Sms response:"+response);
		} catch (Exception e) {
			log.error("Fail to delete SMS.| simNumber:"+comId+" | smsList:"+msgIndexs);
		}
	}
}
