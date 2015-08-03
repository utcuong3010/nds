package com.mbv.airtime.gateway.services;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.mbv.airtime.gateway.AirTimeServicesSkeletonInterface;
import com.mbv.airtime.gateway.ArrayOfTxnInfo;
import com.mbv.airtime.gateway.AtTxnInfo;
import com.mbv.airtime.gateway.CreateLockAccount;
import com.mbv.airtime.gateway.CreateTxnId;
import com.mbv.airtime.gateway.CreateTxnIdResponse;
import com.mbv.airtime.gateway.CreditLockAccount;
import com.mbv.airtime.gateway.DebitLockAccount;
import com.mbv.airtime.gateway.Inquiry;
import com.mbv.airtime.gateway.InquiryResponse;
import com.mbv.airtime.gateway.KeyValuePair;
import com.mbv.airtime.gateway.LockAccountInfo;
import com.mbv.airtime.gateway.LockAccountQuery;
import com.mbv.airtime.gateway.LockAccountQueryResponse;
import com.mbv.airtime.gateway.LockAccountResponse;
import com.mbv.airtime.gateway.LockAccountTxnQuery;
import com.mbv.airtime.gateway.LockAccountTxnQueryResponse;
import com.mbv.airtime.gateway.LockTxnInfo;
import com.mbv.airtime.gateway.LockTxnResponse;
import com.mbv.airtime.gateway.ReservedTopup;
import com.mbv.airtime.gateway.Topup;
import com.mbv.airtime.gateway.TopupResponse;
import com.mbv.airtime.gateway.TxnRequest;
import com.mbv.airtime.gateway.TxnResponse;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.constants.GatewayConstants;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.model.GwErrConverter;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.google.gson.reflect.TypeToken;


public class AirTimeServices implements AirTimeServicesSkeletonInterface {
	private Log log = LogFactory.getLog(AirTimeServices.class);
	private static final String sysError="sys-error";
	private static final String txnError="txn-error";
	
	@Override
	public InquiryResponse inquiry(Inquiry inquiry) {
		String method="INQUIRY";
		String errorCode=ErrorCode.SUCCESS;
		InquiryResponse result = new InquiryResponse();
		ContextBase context = new ContextBase();
		
		if (StringUtils.isBlank(inquiry.getAtTxnIds())){
			errorCode=ErrorCode.INVALID_REQUEST;
			result.setErrorCode(getErrorConversion(method,sysError,errorCode));
			result.setErrorMessage("txnIds can not be blank");
			return result;
		}
		
		context.put(Attributes.ATTR_REQUEST_TXN_ID_LIST, inquiry.getAtTxnIds());
		context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.INQUIRY_AIRTIME_TXN_WFP);

		if (log.isDebugEnabled())
			log.debug("context before call:-" + context);
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(method,
					ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			errorCode=ErrorCode.SYS_INTERNAL_ERROR;
			result.setErrorCode(getErrorConversion(method,sysError,errorCode));
			result.setErrorMessage("Internal error");
			log.error("Fail to process request.| errorCode-"
					+ context.getErrorCode() + "| context-" + context, e);
			return result;
		}
		if (log.isDebugEnabled())
			log.debug("context after call:-" + context);
		// Process response
		errorCode=context.get(Attributes.ATTR_ERROR_CODE);
		try{
			if (ErrorCode.SUCCESS.equalsIgnoreCase(errorCode)){
				result.setTxnErrorList(context.get(Attributes.ATTR_ERROR_LIST));
				String strResult=context.get(Attributes.ATTR_AIRTIME_TXN_INQUIRY_RESULT);
				if (StringUtils.isNotBlank(strResult)){
					Gson gson = new Gson();
					Type type=new TypeToken<List<AtTransaction>>(){}.getType();
					List<AtTransaction> listResult = gson.fromJson(strResult,type);
					ArrayOfTxnInfo arrayOfTxnInfo=new ArrayOfTxnInfo();
					for(AtTransaction atResult:listResult){
						AtTxnInfo txnInfo=new AtTxnInfo();
						txnInfo.setAtTxnId(String.valueOf(atResult.getAtTxnId()));
						txnInfo.setProviderId(atResult.getConnType());
						txnInfo.setTxnStatus(atResult.getTxnStatus());
						txnInfo.setTxnErrorCode(getErrorConversion(method,txnError,atResult.getErrorCode()));
						if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(atResult.getTxnStatus())){
								txnInfo.setTxnErrorCode(null);
							
						}else if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(atResult.getTxnStatus())){
							txnInfo.setTxnStatus(AppConstants.TXN_STATUS_DELIVERING);
						}
						arrayOfTxnInfo.addTxnInfo(txnInfo);
					}
					result.setTxnInfos(arrayOfTxnInfo);
				}
				result.setErrorCode(getErrorConversion(method,sysError,errorCode));
				
			}else{
				result.setErrorCode(getErrorConversion(method,sysError,errorCode));
			}
			
		}catch (Exception e) {
			errorCode=ErrorCode.SYS_INTERNAL_ERROR;
			result.setErrorCode(getErrorConversion(method,sysError,errorCode));
			result.setErrorMessage("Internal error");
			log.error("Fail to process request.| errorCode-"
					+ context.getErrorCode() + "| context-" + context, e);
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode())){
				result.setTxnErrorList(null);
				result.setTxnInfos(null);
			}
			return result;
		}
		
		
		return result;
	}
	
	@Override
	public TopupResponse topup(Topup topup) {
		
		String method="TOPUP";
		String channelId = topup.getChannelId();
		int timeOut=topup.getTimeOut();
		String msisdn = topup.getMsisdn();
		String telcoId=topup.getTelcoId();
		int amount = topup.getAmount();
		String txnId = topup.getTxnId();
		TopupResponse result = new TopupResponse();
		result.setErrorCode(ErrorCode.SUCCESS);
		
		TelcoProvider provider=AppConstants.TELCO_PROVIDER.get(telcoId);
		if (provider==null){
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid telcoId");
			return result;
		}
		
		if (amount<=0){
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid amount");
			return result;
		}
		
		if (StringUtils.isBlank(channelId)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid channelId");
			return result;
		}

		if (StringUtils.isBlank(txnId)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid txnId");
			return result;
		}

		if (StringUtils.isBlank(msisdn)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid msisdn");
			return result;
		}
		
		if (msisdn.startsWith("84"))
			msisdn="0"+msisdn.substring(2);
		
		if (msisdn.startsWith("+84"))
			msisdn="0"+msisdn.substring(3);
		
		if (msisdn.startsWith("0084"))
			msisdn="0"+msisdn.substring(4);
		
		if (amount <= 0) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid amount");
			return result;
		}

		if (timeOut <= 0) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid timeOut");
			return result;
		}

		if (StringUtils.isBlank(telcoId)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid telco");
			return result;
		}
		 
		ContextBase context = new ContextBase();
		context.put(Attributes.ATTR_TXN_TYPE,AppConstants.MOBILE_TOPUP);
		context.put(Attributes.ATTR_REQUEST_TXN_ID, txnId);
		context.put(Attributes.ATTR_CHANNEL_ID, channelId);
		context.put(Attributes.ATTR_MSISDN, msisdn);
		context.putLong(Attributes.ATTR_AMOUNT, amount);
		context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
		context.put(Attributes.ATTR_TELCO_ID, telcoId);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.TOPUP_AIRTIME_WFP);
		context.put(Attributes.ATTR_MESSAGE_TYPE,AppConstants.TOPUP_REQUEST);
		context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);

		if (log.isDebugEnabled())
			log.debug("context before call:-" + context);
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
			AtTxnInfo atTxnInfo=new AtTxnInfo();  
			atTxnInfo.setAtTxnId(context.get(Attributes.ATTR_TRANSACTION_ID));
			atTxnInfo.setProviderId(context.get(Attributes.ATTR_CONN_TYPE));
//			atTxnInfo.setTxnStatus();
			atTxnInfo.setTxnErrorCode(getErrorConversion(method,txnError,context.get(Attributes.ATTR_TXN_ERROR_CODE)));
			
			if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
				atTxnInfo.setTxnErrorCode(null);
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_SUCCESS);
			}else if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_DELIVERING);
			}else if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){ 
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_ERROR);
			}else if (AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){ 
				atTxnInfo.setTxnErrorCode(null);
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_PENDING);
			}
			
			result.setTxnInfo(atTxnInfo);
			result.setErrorCode(getErrorConversion(method,sysError,context.get(Attributes.ATTR_ERROR_CODE)));
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode())){
				result.setTxnInfo(null);
			}
			return result;
		} catch (IntegrationException e) {
			result.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			result.setErrorMessage("Internal error");
			log.error("Fail to process request.| errorCode-"
					+ context.getErrorCode() + "| context-" + context, e);
			result.setErrorCode(getErrorConversion(method,sysError,result.getErrorCode()));
			result.setTxnInfo(null);
			return result;
		}
		
	}
	private String getErrorConversion(String method,String type,String errorCode){

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
/*
	@Override
	public LockAmountResponse lockAmount(LockAmount lockAmount) {
		String method="LOCK";
		String type="default";
		LockAmountResponse response=new LockAmountResponse();
		LockAmountInfo lockAmountInfo=lockAmount.getLockAmountInfo();
		ContextBase context=new ContextBase();
		response.setErrorCode(ErrorCode.SUCCESS);
		context.setErrorCode(ErrorCode.SUCCESS);
		
		if (StringUtils.isBlank(lockAmountInfo.getSystemId())){
			response.setErrorCode(getErrorConversion(method,type,ErrorCode.INVALID_REQUEST));
			return response;
		}
		
		context.put(Attributes.ATTR_CHANNEL_ID, lockAmountInfo.getSystemId());
		
		if (StringUtils.isBlank(lockAmountInfo.getLockId())){
			response.setErrorCode(getErrorConversion(method,type,ErrorCode.INVALID_REQUEST));
			return response;
		}
		
		context.put(Attributes.ATTR_REQUEST_TXN_ID, lockAmountInfo.getLockId());
		
		
		if (lockAmountInfo.getAmount()<=0){
			response.setErrorCode(getErrorConversion(method,type,ErrorCode.INVALID_REQUEST));
			return response;
		}
		context.putLong(Attributes.ATTR_AMOUNT, lockAmountInfo.getAmount());
		
		if (StringUtils.isNotBlank(lockAmountInfo.getTelcoIds())){
			String[] telcoIds=lockAmountInfo.getTelcoIds().split(",");
			boolean isValid=true;
			TelcoProvider provider;
			for(String telcoId:telcoIds){
				provider=AppConstants.TELCO_PROVIDER.get(telcoId);
				if (provider==null){
					isValid=false;
					break;
				}
			}
			if (!isValid){
				response.setErrorCode(getErrorConversion(method,type,ErrorCode.INVALID_REQUEST));
				return response;
			}
		}
		
		context.put(Attributes.ATTR_TELCO_IDS, lockAmountInfo.getTelcoIds());
		context.put(Attributes.ATTR_DESCRIPTION, lockAmountInfo.getDescription());
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.LOCK_AMOUNT_AIRTIME_WFP);
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(getErrorConversion(method,type,ErrorCode.SYS_INTERNAL_ERROR));
			return response;
		}
		
		response.setErrorCode(getErrorConversion(method,type,context.getErrorCode()));
		if (ErrorCode.ALREDY_EXISTED.equalsIgnoreCase(context.getErrorCode())){
			LockAmountInfo amountInfo=new LockAmountInfo();
			amountInfo.setAmount(context.getLong(Attributes.ATTR_AMOUNT));
			amountInfo.setLockId(context.get(Attributes.ATTR_REQUEST_TXN_ID));
			amountInfo.setSystemId(context.get(Attributes.ATTR_CHANNEL_ID));
			amountInfo.setTelcoIds(context.get(Attributes.ATTR_TELCO_IDS));
			response.setLockAmountInfo(amountInfo);
		}
		
		return response;
	}
*/
	@Override
	public TopupResponse reservedTopup(ReservedTopup reservedTopup) {
		
		String method="TOPUP";
		TopupResponse result=new TopupResponse();
		result.setErrorCode(ErrorCode.SUCCESS);
		String channelId = reservedTopup.getChannelId();
		int timeOut=reservedTopup.getTimeOut();
		String msisdn = reservedTopup.getMsisdn();
		String telcoId=reservedTopup.getTelcoId();
		int amount = reservedTopup.getAmount();
		String txnId = reservedTopup.getTxnId();
		String lockId=reservedTopup.getLockId();
		String description=reservedTopup.getDescription();
		
		if (amount<=0){
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid amount");
			return result;
		}
		
		if (StringUtils.isBlank(lockId)){
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid lockId");
			return result;
		}	
		
		TelcoProvider provider=AppConstants.TELCO_PROVIDER.get(telcoId);
		if (provider==null){
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid telcoId");
			return result;
		}
		if (StringUtils.isBlank(channelId)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid channelId");
			return result;
		}

		if (StringUtils.isBlank(txnId)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid txnId");
			return result;
		}

		if (StringUtils.isBlank(msisdn)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid msisdn");
			return result;
		}
		
		if (msisdn.startsWith("84"))
			msisdn="0"+msisdn.substring(2);
		
		if (msisdn.startsWith("+84"))
			msisdn="0"+msisdn.substring(3);
		
		if (msisdn.startsWith("0084"))
			msisdn="0"+msisdn.substring(4);
		
		if (amount <= 0) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid amount");
			return result;
		}

		if (timeOut <= 0) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid timeOut");
			return result;
		}

		if (StringUtils.isBlank(telcoId)) {
			result.setErrorCode(ErrorCode.INVALID_REQUEST);
			result.setErrorMessage("invalid telco");
			return result;
		}
		 
		ContextBase context = new ContextBase();
		context.put(Attributes.ATTR_TXN_TYPE,AppConstants.MOBILE_TOPUP);
		context.put(Attributes.ATTR_OPERATION, "RESERVED_TOPUP");
		context.put(Attributes.ATTR_REQUEST_TXN_ID, txnId);
		context.put(Attributes.ATTR_RESERVED_ID, lockId);
		context.put(Attributes.ATTR_DESCRIPTION, description);
		context.put(Attributes.ATTR_CHANNEL_ID, channelId);
		context.put(Attributes.ATTR_MSISDN, msisdn);
		context.putLong(Attributes.ATTR_AMOUNT, amount);
		context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
		context.put(Attributes.ATTR_TELCO_ID, telcoId);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.RESERVE_TOPUP_AIRTIME_WFP);
		context.put(Attributes.ATTR_MESSAGE_TYPE,AppConstants.TOPUP_REQUEST);
		context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
		
		if (log.isDebugEnabled())
			log.debug("context before call : " + context);
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
			
			AtTxnInfo atTxnInfo=new AtTxnInfo();  
			atTxnInfo.setAtTxnId(context.get(Attributes.ATTR_TRANSACTION_ID));
			atTxnInfo.setProviderId(context.get(Attributes.ATTR_CONN_TYPE));
			atTxnInfo.setTxnErrorCode(getErrorConversion(method,txnError,context.get(Attributes.ATTR_TXN_ERROR_CODE)));
			
			if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
				atTxnInfo.setTxnErrorCode(null);
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_SUCCESS);
			}else if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_DELIVERING);
			}else if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){ 
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_ERROR);
			}else if (AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){ 
				atTxnInfo.setTxnErrorCode(null);
				atTxnInfo.setTxnStatus(AppConstants.TXN_STATUS_PENDING);
			}
			
			result.setTxnInfo(atTxnInfo);
			result.setErrorCode(getErrorConversion(method,sysError,context.get(Attributes.ATTR_ERROR_CODE)));
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode())){
				result.setTxnInfo(null);
			}
			return result;

		} catch (IntegrationException e) {
			result.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			result.setErrorMessage("Internal error");
			log.error("Fail to process request.| errorCode-"
					+ context.getErrorCode() + "| context-" + context, e);
			result.setErrorCode(getErrorConversion(method,sysError,result.getErrorCode()));
			result.setTxnInfo(null);
			return result;
		}
		
	}

	@Override
	public LockAccountResponse createLockAccount(
			CreateLockAccount createLockAccount) {
		String method="CREATE_LOCK_ACCOUNT";
		String type="default";
		LockAccountResponse response=new LockAccountResponse();

		ContextBase context=new ContextBase();
		response.setErrorCode(ErrorCode.SUCCESS);
		context.setErrorCode(ErrorCode.SUCCESS);
		
		if (StringUtils.isBlank(createLockAccount.getSystemId())){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		context.put(Attributes.ATTR_CHANNEL_ID, createLockAccount.getSystemId());
		
		if (StringUtils.isBlank(createLockAccount.getAccountId())){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		context.put(Attributes.ATTR_REQUEST_TXN_ID, createLockAccount.getAccountId());
		
		
		if (createLockAccount.getAmount()<=0){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		context.putLong(Attributes.ATTR_AMOUNT, createLockAccount.getAmount());
		String[] telcoIdTemp=null;
		if (StringUtils.isNotBlank(createLockAccount.getTelcoIds())){
			telcoIdTemp=createLockAccount.getTelcoIds().split(",");
			boolean isValid=true;
			int telcoLength=telcoIdTemp.length;
			TelcoProvider provider;
			for(int i=0;i<telcoLength;i++){
				telcoIdTemp[i]=telcoIdTemp[i].trim();
				provider=AppConstants.TELCO_PROVIDER.get(telcoIdTemp[i]);
				if (provider==null){
					isValid=false;
					break;
				}
			}
			if (!isValid){
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
		}else{
			telcoIdTemp=AppConstants.TELCO_PROVIDER.keySet().toArray(new String[ AppConstants.TELCO_PROVIDER.keySet().size()] );
		}
		if (telcoIdTemp==null){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		String strTelcoId="";
		boolean isFirst=true;
		TelcoProvider telcoProvider;
		for (String telco:telcoIdTemp){
			telcoProvider=AppConstants.TELCO_PROVIDER.get(telco);
			if (AppConstants.TELCO_GROUP_CODE.equals(telcoProvider.getGroup())){
				if(isFirst){
					strTelcoId=strTelcoId+telco;
					isFirst=false;
				}else
					strTelcoId=strTelcoId+","+telco;
			}
		}
		if (StringUtils.isBlank(strTelcoId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		context.put(Attributes.ATTR_TELCO_IDS, strTelcoId);
		context.put(Attributes.ATTR_OPERATION, method);
		context.put(Attributes.ATTR_DESCRIPTION, createLockAccount.getDescription());
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.CREATE_LOCK_ACCOUNT_AIRTIME_WFP);
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			return response;
		}
		response.setErrorCode(context.getErrorCode());
				
		return response;
	}

	@Override
	public CreateTxnIdResponse createTxnId(CreateTxnId createTxnId) {
		String method="CREATE_TXN_ID";
		String type="default";
		CreateTxnIdResponse response=new CreateTxnIdResponse();
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_OPERATION, method);
		context.put(Attributes.ATTR_CHANNEL_ID, createTxnId.getSystemId());
		context.setErrorCode(ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.CREATE_TXN_ID_WFP);
		
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			return response;
		}
		
		if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode()))
			response.setTxnId(context.get(Attributes.ATTR_TRANSACTION_ID));
		response.setErrorCode(context.getErrorCode());
		
		return response;
		
	}

	@Override
	public LockTxnResponse creditLockAccount(CreditLockAccount creditLockAccount) {
		LockTxnResponse response=new LockTxnResponse();
		String method="CREDIT_LOCK_AMOUNT";
		String type="default";
		
		String systemId=creditLockAccount.getLockTxnInfo().getSystemId();
		String txnId=creditLockAccount.getLockTxnInfo().getTxnId();
		String referenceId=creditLockAccount.getLockTxnInfo().getReferenceId();
		String accountId=creditLockAccount.getLockTxnInfo().getAccountId();
		long amount=creditLockAccount.getLockTxnInfo().getAmount();
		String description=creditLockAccount.getLockTxnInfo().getDescription();
		
		if (StringUtils.isBlank(systemId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		if (StringUtils.isBlank(txnId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		
		if (amount<0){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		if (StringUtils.isBlank(accountId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_CHANNEL_ID, systemId);
		context.put(Attributes.ATTR_REQUEST_TXN_ID, txnId);
		context.put(Attributes.ATTR_RESERVED_ID, accountId);
		context.putLong(Attributes.ATTR_AMOUNT, amount);
		context.put(Attributes.ATTR_REFERENCE_ID, referenceId);
		context.put(Attributes.ATTR_DESCRIPTION, description);
		context.put(Attributes.ATTR_OPERATION, method);
		context.setErrorCode(ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.CREDIT_LOCK_ACCOUNT_WFP);
		
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			return response;
		}
		
		response.setErrorCode(context.getErrorCode());
		
		return response;
	}

	@Override
	public LockTxnResponse debitLockAccount(DebitLockAccount debitLockAccount) {
		String method="DEBIT_LOCK_AMOUNT";
		String type="default";
		
		LockTxnResponse response=new LockTxnResponse();
		String systemId=debitLockAccount.getLockTxnInfo().getSystemId();
		String txnId=debitLockAccount.getLockTxnInfo().getTxnId();
		String referenceId=debitLockAccount.getLockTxnInfo().getReferenceId();
		String accountId=debitLockAccount.getLockTxnInfo().getAccountId();
		long amount=debitLockAccount.getLockTxnInfo().getAmount();
		String description=debitLockAccount.getLockTxnInfo().getDescription();
		
		if (StringUtils.isBlank(systemId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		if (StringUtils.isBlank(txnId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		if (amount<=0){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		if (StringUtils.isBlank(accountId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_CHANNEL_ID, systemId);
		context.put(Attributes.ATTR_REQUEST_TXN_ID, txnId);
		context.put(Attributes.ATTR_RESERVED_ID, accountId);
		context.putLong(Attributes.ATTR_AMOUNT, amount);
		context.put(Attributes.ATTR_REFERENCE_ID, referenceId);
		context.put(Attributes.ATTR_DESCRIPTION, description);
		context.put(Attributes.ATTR_OPERATION, method);
		context.setErrorCode(ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.DEBIT_LOCK_ACCOUNT_WFP);
		
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			return response;
		}
		
		response.setErrorCode(context.getErrorCode());
		return response;
	}

	@Override
	public LockAccountQueryResponse lockAccountQuery(
			LockAccountQuery lockAccountQuery) {
		String method="LOCK_ACCOUNT_QUERY";
		String type="default";
		
		LockAccountQueryResponse response=new LockAccountQueryResponse();
		String txnId=lockAccountQuery.getAccountId();
		
		if (StringUtils.isBlank(txnId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_REQUEST_TXN_ID, txnId);
		context.put(Attributes.ATTR_OPERATION, method);
		context.setErrorCode(ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.LOCK_ACCOUNT_QUERY_WFP);
		
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			return response;
		}
		response.setErrorCode(context.getErrorCode());
		
		if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode())){
			response.setTxnStatus(context.get(Attributes.ATTR_TXN_STATUS));
			if (ErrorCode.SUCCESS.equalsIgnoreCase(response.getTxnStatus())){
				LockAccountInfo lockAccountInfo=new LockAccountInfo();
				lockAccountInfo.setAccountId(context.get(Attributes.ATTR_RESERVED_ID));
				lockAccountInfo.setSystemId(context.get(Attributes.ATTR_CHANNEL_ID));
				lockAccountInfo.setTelcoIds(context.get(Attributes.ATTR_TELCO_IDS));
				lockAccountInfo.setTotalCredit(context.getLong(Attributes.ATTR_CREDIT_AMOUNT,0));
				lockAccountInfo.setTotalDebit(context.getLong(Attributes.ATTR_DEBIT_AMOUNT,0));
				lockAccountInfo.setDescription(context.get(Attributes.ATTR_DESCRIPTION));
				response.setLockAccountInfo(lockAccountInfo);
			}
			
		}
		return response;
	}

	@Override
	public LockAccountTxnQueryResponse lockAccountTxnQuery(LockAccountTxnQuery lockAccountTxnQuery) {
		String method="LOCK_ACCOUNT_TXN_QUERY";
		String type="default";
		
		LockAccountTxnQueryResponse response=new LockAccountTxnQueryResponse();
		String txnId=lockAccountTxnQuery.getTxnId();
		
		if (StringUtils.isBlank(txnId)){
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			return response;
		}
		
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_REQUEST_TXN_ID, txnId);
		context.put(Attributes.ATTR_OPERATION, method);
		context.setErrorCode(ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.LOCK_ACCOUNT_TXN_QUERY_WFP);
		
		try {
			IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
			context = executor.process(context);
		} catch (IntegrationException e) {
			response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			return response;
		}
		response.setErrorCode(context.getErrorCode());
		
		if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode())){
			response.setTxnStatus(context.get(Attributes.ATTR_TXN_STATUS));
			if (ErrorCode.SUCCESS.equalsIgnoreCase(response.getTxnStatus())){
				LockTxnInfo lockTxnInfo=new LockTxnInfo();
				lockTxnInfo.setAccountId(context.get(Attributes.ATTR_RESERVED_ID));
				lockTxnInfo.setSystemId(context.get(Attributes.ATTR_CHANNEL_ID));
				lockTxnInfo.setAmount(context.getLong(Attributes.ATTR_AMOUNT,0));
				lockTxnInfo.setReferenceId(context.get(Attributes.ATTR_REFERENCE_ID));
				lockTxnInfo.setTxnId(context.get(Attributes.ATTR_TRANSACTION_ID));
				lockTxnInfo.setOperation(context.get(Attributes.ATTR_OPERATION));
				lockTxnInfo.setDescription(context.get(Attributes.ATTR_DESCRIPTION));
				response.setLockTxnInfo(lockTxnInfo);
			}
		}
		return response;
	}

	@Override
	public TxnResponse txnRequest(TxnRequest txnRequest) {
		String method="TOPUP";
		TxnResponse response=new TxnResponse();
		response.setErrorCode(ErrorCode.SUCCESS);
		ContextBase context=new ContextBase();
		
		if (txnRequest.getKeyValuePairs()==null){
			context.setErrorCode(ErrorCode.INVALID_REQUEST);
			response.setErrorCode(context.getErrorCode());
			return response;
		}
		
		if (txnRequest.getKeyValuePairs().getKeyValuePair()==null){
			context.setErrorCode(ErrorCode.INVALID_REQUEST);
			response.setErrorCode(context.getErrorCode());
			return response;
		}
		
		if (txnRequest.getKeyValuePairs().getKeyValuePair().length==0){
			context.setErrorCode(ErrorCode.INVALID_REQUEST);
			response.setErrorCode(context.getErrorCode());
			return response;
		}
		
		for (KeyValuePair keyValuePair:txnRequest.getKeyValuePairs().getKeyValuePair())
			if (StringUtils.isNotBlank(keyValuePair.getValue()))
				context.put(keyValuePair.getKey(), keyValuePair.getValue());
		
		if (StringUtils.isBlank(context.get(Attributes.ATTR_TXN_TYPE))) {
			context.setErrorCode(ErrorCode.INVALID_REQUEST);
			response.setErrorCode(ErrorCode.INVALID_REQUEST);
			response.setErrorMessage("invalid txn_type");
			return response;
		}
		
		if (AppConstants.GAME_TOPUP.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_TYPE))){
			method="TOPUP";
			/*
			 * Attributes.ATTR_TXN_TYPE
			 * Attributes.ATTR_REQUEST_TXN_ID
			 * Attributes.ATTR_CHANNEL_ID
			 * Attributes.ATTR_ACCOUNT_ID --> Attributes.ATTR_MSISDN
			 * Attributes.ATTR_AMOUNT
			 * Attributes.ATTR_TIME_OUT
			 * Attributes.ATTR_TELCO_ID
			 * Attributes.ATTR_WFP_NAME->GatewayConstants.TOPUP_AIRTIME_WFP
			 * Attributes.ATTR_MESSAGE_TYPE-->AppConstants.TOPUP_REQUEST
			 * Attributes.ATTR_ERROR_CODE--> ErrorCode.SUCCESS
			 * Attributes.ATTR_SERVER_ID
			 * Attributes.ATTR_SERVER_NAME
			 */
			
			
		
			if (StringUtils.isBlank(context.get(Attributes.ATTR_CHANNEL_ID))) {
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				response.setErrorMessage("invalid channelId");
				return response;
			}
			
			
			
			if (StringUtils.isBlank(context.get(Attributes.ATTR_REQUEST_TXN_ID))) {
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				response.setErrorMessage("invalid txnId");
				return response;
			}
			
			if (StringUtils.isBlank(context.get(Attributes.ATTR_AMOUNT))) {
				response.setErrorMessage("invalid amount");
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			if (!NumberUtils.isNumber(context.get(Attributes.ATTR_AMOUNT))) {
				response.setErrorMessage("invalid amount");
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			if (StringUtils.isBlank(context.get(Attributes.ATTR_ACCOUNT_ID))) {
				response.setErrorMessage("invalid account_id");
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			context.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_ACCOUNT_ID));
			context.put(Attributes.ATTR_ACCOUNT_ID,null);
			
			
			if (StringUtils.isBlank(context.get(Attributes.ATTR_TIME_OUT))) {
				response.setErrorMessage("invalid time_out");
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			if (!NumberUtils.isNumber(context.get(Attributes.ATTR_TIME_OUT))) {
				response.setErrorMessage("invalid time_out");
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			if (StringUtils.isBlank(context.get(Attributes.ATTR_GAME_ID))) {
				response.setErrorMessage("invalid game_id");
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			TelcoProvider provider=AppConstants.TELCO_PROVIDER.get(context.get(Attributes.ATTR_GAME_ID));
			if (provider==null){
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			context.put(Attributes.ATTR_TELCO_ID, context.get(Attributes.ATTR_GAME_ID));
			context.put(Attributes.ATTR_GAME_ID,null);
			context.put(Attributes.ATTR_MESSAGE_TYPE,AppConstants.TOPUP_REQUEST);
			context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.TOPUP_AIRTIME_WFP);

			try {
				IExecutor executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR);
				context = executor.process(context);
				
				List<KeyValuePair> keyValuePairList=new ArrayList<KeyValuePair>();
				KeyValuePair keyValuePair=new KeyValuePair();
				keyValuePair.setKey(Attributes.ATTR_TRANSACTION_ID);
				keyValuePair.setValue(context.get(Attributes.ATTR_TRANSACTION_ID));
				keyValuePairList.add(keyValuePair);
				
				keyValuePair=new KeyValuePair();
				keyValuePair.setKey(Attributes.ATTR_PROVIDER_ID);
				keyValuePair.setValue(context.get(Attributes.ATTR_CONN_TYPE));
				keyValuePairList.add(keyValuePair);
				
				if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
					keyValuePair=new KeyValuePair();
					keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
					keyValuePair.setValue(AppConstants.TXN_STATUS_SUCCESS);
					keyValuePairList.add(keyValuePair);
				}else if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
					keyValuePair=new KeyValuePair();
					keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
					keyValuePair.setValue(AppConstants.TXN_STATUS_DELIVERING);
					keyValuePairList.add(keyValuePair);
				}else if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){
					
					keyValuePair=new KeyValuePair();
					keyValuePair.setKey(Attributes.ATTR_TXN_ERROR_CODE);
					keyValuePair.setValue(getErrorConversion(method,txnError,context.get(Attributes.ATTR_TXN_ERROR_CODE)));
					keyValuePairList.add(keyValuePair);
					
					keyValuePair=new KeyValuePair();
					keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
					keyValuePair.setValue(AppConstants.TXN_STATUS_ERROR);
					keyValuePairList.add(keyValuePair);
					
				}else if (AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_STATUS))){ 
					keyValuePair=new KeyValuePair();
					keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
					keyValuePair.setValue(AppConstants.TXN_STATUS_PENDING);
					keyValuePairList.add(keyValuePair);
				}
				
				response.setKeyValuePairs(keyValuePairList.toArray(new KeyValuePair[keyValuePairList.size()]));
				
				response.setErrorCode(getErrorConversion(method,sysError,context.get(Attributes.ATTR_ERROR_CODE)));
				if (!ErrorCode.SUCCESS.equalsIgnoreCase(context.getErrorCode())){
					response.setKeyValuePairs(null);
				}
				return response;

			} catch (IntegrationException e) {
				response.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
				response.setErrorMessage("Internal error");
				log.error("Fail to process request.| errorCode-"
						+ context.getErrorCode() + "| context-" + context, e);
				response.setErrorCode(getErrorConversion(method,sysError,response.getErrorCode()));
				response.setKeyValuePairs(null);
				return response;
			}
		}else if (AppConstants.INQUIRY_GAME_TOPUP.equalsIgnoreCase(context.get(Attributes.ATTR_TXN_TYPE))){
			method="INQUIRY";
			String errorCode=ErrorCode.SUCCESS;
			if (StringUtils.isBlank(context.get(Attributes.ATTR_REQUEST_TXN_ID))) {
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			if (!StringUtils.isAlphanumeric(context.get(Attributes.ATTR_REQUEST_TXN_ID))) {
				response.setErrorCode(ErrorCode.INVALID_REQUEST);
				return response;
			}
			
			context.put(Attributes.ATTR_REQUEST_TXN_ID_LIST, context.get(Attributes.ATTR_REQUEST_TXN_ID));
			context.put(Attributes.ATTR_REQUEST_TXN_ID, null);
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
			context.put(Attributes.ATTR_WFP_NAME,GatewayConstants.INQUIRY_AIRTIME_TXN_WFP);
			
			try {
				IExecutor executor = ExecutorFactory.getInstance().getExecutor(method,
						ExecutorFactory.WFP_EXECUTOR);
				context = executor.process(context);
			} catch (IntegrationException e) {
				errorCode=ErrorCode.SYS_INTERNAL_ERROR;
				response.setErrorCode(getErrorConversion(method,sysError,errorCode));
				response.setErrorMessage("Internal error");
				log.error("Fail to process request.| errorCode-"
						+ context.getErrorCode() + "| context-" + context, e);
				return response;
			}
			if (log.isDebugEnabled())
				log.debug("context after call:-" + context);
			
			errorCode=context.get(Attributes.ATTR_ERROR_CODE);
			try{
				if (ErrorCode.SUCCESS.equalsIgnoreCase(errorCode)){
					String strResult=context.get(Attributes.ATTR_AIRTIME_TXN_INQUIRY_RESULT);
					if (StringUtils.isNotBlank(strResult)){
						Gson gson = new Gson();
						Type type=new TypeToken<List<AtTransaction>>(){}.getType();
						List<AtTransaction> listResult = gson.fromJson(strResult,type);
						ArrayOfTxnInfo arrayOfTxnInfo=new ArrayOfTxnInfo();
						for(AtTransaction atResult:listResult){
							List<KeyValuePair> keyValuePairList=new ArrayList<KeyValuePair>();
							KeyValuePair keyValuePair=new KeyValuePair();
							keyValuePair.setKey(Attributes.ATTR_TRANSACTION_ID);
							keyValuePair.setValue(String.valueOf(atResult.getAtTxnId()));
							keyValuePairList.add(keyValuePair);
							
							keyValuePair=new KeyValuePair();
							keyValuePair.setKey(Attributes.ATTR_PROVIDER_ID);
							keyValuePair.setValue(atResult.getConnType());
							keyValuePairList.add(keyValuePair);
							
							if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(atResult.getTxnStatus())){
								keyValuePair=new KeyValuePair();
								keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
								keyValuePair.setValue(AppConstants.TXN_STATUS_SUCCESS);
								keyValuePairList.add(keyValuePair);
							}else if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(atResult.getTxnStatus())){
								keyValuePair=new KeyValuePair();
								keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
								keyValuePair.setValue(AppConstants.TXN_STATUS_DELIVERING);
								keyValuePairList.add(keyValuePair);
							}else if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(atResult.getTxnStatus())){
								
								keyValuePair=new KeyValuePair();
								keyValuePair.setKey(Attributes.ATTR_TXN_ERROR_CODE);
								keyValuePair.setValue(getErrorConversion(method,txnError,atResult.getErrorCode()));
								keyValuePairList.add(keyValuePair);
								
								keyValuePair=new KeyValuePair();
								keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
								keyValuePair.setValue(AppConstants.TXN_STATUS_ERROR);
								keyValuePairList.add(keyValuePair);
								
							}else if (AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(atResult.getTxnStatus())){ 
								keyValuePair=new KeyValuePair();
								keyValuePair.setKey(Attributes.ATTR_TXN_STATUS);
								keyValuePair.setValue(AppConstants.TXN_STATUS_PENDING);
								keyValuePairList.add(keyValuePair);
							}
							response.setKeyValuePairs(keyValuePairList.toArray(new KeyValuePair[keyValuePairList.size()]));
						}
						
					}
					response.setErrorCode(getErrorConversion(method,sysError,errorCode));
					
				}else{
					response.setErrorCode(getErrorConversion(method,sysError,errorCode));
				}
				return response;
			}catch (Exception e) {
				errorCode=ErrorCode.SYS_INTERNAL_ERROR;
				response.setErrorCode(getErrorConversion(method,sysError,errorCode));
				response.setErrorMessage("Internal error");
				log.error("Fail to process request.| errorCode-"
						+ context.getErrorCode() + "| context-" + context, e);
				if (!ErrorCode.SUCCESS.equalsIgnoreCase(response.getErrorCode())){
					response.setKeyValuePairs(null);
				}
				return response;
			}
			
		}else{
			context.setErrorCode(ErrorCode.INVALID_REQUEST);
			response.setErrorCode(ErrorCode.UNSUPPORTED_OPERATION);
			return response;
		}
		
	}

	

}
