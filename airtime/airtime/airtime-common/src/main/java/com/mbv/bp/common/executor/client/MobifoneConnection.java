package com.mbv.bp.common.executor.client;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.Gson;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.executor.client.result.MobiTxnQueryResult;
import com.mbv.bp.common.executor.mobifone.MobifoneConnectionProperty;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BalanceRequestType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BalanceResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BalanceResponseType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BuyRequestType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.BuyResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatesessionResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.CreatesessionResponseType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LoginRequestType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.LoginResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.NextidRequestType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.NextidResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.PinRequestType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.PinResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.StandardBizResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransqueryRequestType;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransqueryResponse;
import com.mbv.bp.common.executor.mobifone.services.UMarketSCStub.TransqueryResponseType;
import com.mbv.bp.common.forward.MobifoneCdrForward;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.model.MobiBalanceInfo;
import com.mbv.bp.common.vo.airtime.AtSummaryView;

public class MobifoneConnection implements IExecutable// , IIntegration
{
	
	private static Log logger = LogFactory.getLog(MobifoneConnection.class);
	private boolean status=false;
	private UMarketSCStub stub = null;
	private String sessionId="E1K5QAFLS3KV1TNYYYH4";
	private boolean isSessionVerified=false;
	private long sessionCreateAt=0;
	private AtomicInteger syncId=new AtomicInteger(0);
	public MobifoneConnection() {
	}

	@Override
	public void connect() throws Exception {
		try{
			if (stub==null){ 
				stub = new UMarketSCStub(MobifoneConnectionProperty.getInstance().getUrl());
				syncId.set(MobifoneConnectionProperty.getInstance().getSyncId());
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.MOBIFONE_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			logger.error("Fail to connect to Mobifone");
		}
		try{
			if (AppConstants.MOBIFONE_SETTINGS.isSessionVerified()){
				sessionId=AppConstants.MOBIFONE_SETTINGS.getSessionId();
				isSessionVerified=AppConstants.MOBIFONE_SETTINGS.isSessionVerified();
			}
		}catch (Exception e) {
			
		}
	}
	
	
	public void checkConnectToMobifone(){
		try{
			int curSyncId=MobifoneConnectionProperty.getInstance().getSyncId();
			if (stub==null || (syncId.get()!=curSyncId)){ 
				stub = new UMarketSCStub(MobifoneConnectionProperty.getInstance().getUrl());
				syncId.set(curSyncId);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.MOBIFONE_SETTINGS.getResponseTimeOut());
				logger.info("Going to reconnect Mobifone server due to connection properties changed.");
			}
		}catch (Exception e) {
			stub=null;
			logger.error("Fail to connect to Mobifone");
		}
	}
	
	@Override
	public ContextBase process(ContextBase context) {
		String operationType=context.get(Attributes.ATTR_OPERATION_TYPE);
		context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
		
		checkConnectToMobifone();
		if (stub==null) {
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
			return context;
		}
		
		if (!AppConstants.MOBIFONE_SETTINGS.getResetSessionOperation().equalsIgnoreCase(operationType)){
			boolean isCreateSession=!isSessionVerified && (System.currentTimeMillis()-sessionCreateAt>AppConstants.MOBIFONE_SETTINGS.getSessionInvalidTime());
			try{
				if (sessionId==null || isCreateSession){
					sessionId=null;
					CreatesessionResponseType session=createSession();
					if (session.getResult()==0){
						sessionId=session.getSessionid();
						logger.info("**** Create new sessionId-"+sessionId);
						sessionCreateAt=System.currentTimeMillis();
					}else{
						context.put(Attributes.ATTR_RESULT_NAMESPACE,session.getResult_namespace());
						context.putInt(Attributes.ATTR_RESPONSE_CODE,session.getResult());
						context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.UNABLE_CREATE_SESSION);
						return context;
					}
				}
//				perform login
				if (!isSessionVerified){
					if (sessionId!=null){
						StandardBizResponse loginRes=login(sessionId);
						if (loginRes.getResult()==0){
							isSessionVerified=true;
							logger.info("**** SessionId-"+sessionId+" is verified !");
						}else{
							sessionId=null;
							isSessionVerified=false;
							context.put(Attributes.ATTR_RESULT_NAMESPACE,loginRes.getResult_namespace());
							context.putInt(Attributes.ATTR_RESPONSE_CODE,loginRes.getResult());
							context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.UNABLE_LOGIN_TO_MOBIFONE);
							return context;
						}
					}
				}
			}catch (RemoteException e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				logger.error(e.getMessage(),e);
				return context;
			}
		}
		
		if (AppConstants.MOBIFONE_SETTINGS.getTxnInquiryOperation().equalsIgnoreCase(operationType)){
			try{
				context.put(Attributes.ATTR_SESSION_ID, sessionId);
				MobiTxnQueryResult txnQueryResult=txnInquiry(sessionId, context.getInt(Attributes.ATTR_NEXT_MESSAGE_ID));
				Gson gson=new Gson();
				context.put(Attributes.ATTR_TXT_INQUIRY_RESULT, gson.toJson(txnQueryResult));
				context.put(Attributes.ATTR_RESULT_NAMESPACE,txnQueryResult.getResultNamespace());
				context.putInt(Attributes.ATTR_RESPONSE_CODE,txnQueryResult.getResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				logger.error("Fail to call transQuery method from Mobifone",e);
			}
		}else if(AppConstants.MOBIFONE_SETTINGS.getNextIdOperation().equalsIgnoreCase(operationType)){
			try{
				context.put(Attributes.ATTR_SESSION_ID, sessionId);
				StandardBizResponse nextIdRes=getNextId(sessionId);
				context.putInt(Attributes.ATTR_NEXT_MESSAGE_ID,nextIdRes.getTransid());
				context.put(Attributes.ATTR_RESULT_NAMESPACE,nextIdRes.getResult_namespace());
				context.putInt(Attributes.ATTR_RESPONSE_CODE,nextIdRes.getResult());
				/*
				 * Add check result here to reset session
				 */
			}catch (RemoteException e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				logger.error("Fail to call nextId method from Mobifone",e);
			}
			
		}else if(AppConstants.MOBIFONE_SETTINGS.getBuyOperation().equalsIgnoreCase(operationType)){
			try{
				context.put(Attributes.ATTR_SESSION_ID, sessionId);
				StandardBizResponse nextIdRes=buyAirtime(
						sessionId, 
						AppConstants.MOBIFONE_SETTINGS.getBuyType(), 
						context.getLong(Attributes.ATTR_AMOUNT), 
						context.get(Attributes.ATTR_MOBI_BUY_TARGET), 
						context.get(Attributes.ATTR_MSISDN),
						context.get(Attributes.ATTR_TRANSACTION_ID)
						);
				context.putInt(Attributes.ATTR_MESSAGE_ID,nextIdRes.getTransid());
				context.put(Attributes.ATTR_RESULT_NAMESPACE,nextIdRes.getResult_namespace());
				context.putInt(Attributes.ATTR_RESPONSE_CODE,nextIdRes.getResult());
				MobifoneCdrForward.getInstance().execute(context);
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				logger.error("Fail to call buy method from Mobifone",e);
			}
		}else if(AppConstants.MOBIFONE_SETTINGS.getChangePasswordOperation().equalsIgnoreCase(operationType)){
			try{
				context.put(Attributes.ATTR_SESSION_ID, sessionId);
				StandardBizResponse changePinRes=changePin(
						sessionId, 
						AppConstants.MOBIFONE_SETTINGS.getUserName(),
						AppConstants.MOBIFONE_SETTINGS.getEncryptedPin(sessionId),
						context.get(Attributes.ATTR_NEW_PASSWORD)
						);
				context.putInt(Attributes.ATTR_MESSAGE_ID,changePinRes.getTransid());
				context.put(Attributes.ATTR_RESULT_NAMESPACE,changePinRes.getResult_namespace());
				context.putInt(Attributes.ATTR_RESPONSE_CODE,changePinRes.getResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				logger.error("Fail to call pin method from Mobifone",e);
			}
		}else if(AppConstants.MOBIFONE_SETTINGS.getBalanceInquiryOpeation().equalsIgnoreCase(operationType)){
			try{
				context.put(Attributes.ATTR_SESSION_ID, sessionId);
				BalanceResponseType balanceResponseType=getBalance(sessionId);
				
				MobiBalanceInfo balanceInfo=new MobiBalanceInfo();
				balanceInfo.setAvail1(balanceResponseType.getAvail_1());
				balanceInfo.setCurrent1(balanceResponseType.getCurrent_1());
				balanceInfo.setPending1(balanceResponseType.getPending_1());
				
				balanceInfo.setAvail2(balanceResponseType.getAvail_2());
				balanceInfo.setCurrent2(balanceResponseType.getCurrent_2());
				balanceInfo.setPending2(balanceResponseType.getPending_2());
				
				balanceInfo.setAvail3(balanceResponseType.getAvail_3());
				balanceInfo.setCurrent3(balanceResponseType.getCurrent_3());
				balanceInfo.setPending3(balanceResponseType.getPending_3());
				balanceInfo.setNameSpace(balanceResponseType.getResult_namespace());
				balanceInfo.setErrorCode(String.valueOf(balanceResponseType.getResult()));
				
				balanceInfo.setTxnId(balanceResponseType.getTransid());
				balanceInfo.setTxnDate(context.getDate(Attributes.ATTR_TRANSACTION_DATE));
				
				try{
					ProviderAmountDao amountDao=new ProviderAmountDao();
					Long currentAmount=amountDao.selectAmount(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
					balanceInfo.setMobiviBalance(currentAmount);
				}catch (Exception e) {
					
				}
				Gson gson=new Gson();
				
				context.put(Attributes.ATTR_BALANCE_INQUIRY_RESULT, gson.toJson(balanceInfo));
				context.putInt(Attributes.ATTR_MESSAGE_ID,balanceResponseType.getTransid());
				context.put(Attributes.ATTR_RESULT_NAMESPACE,balanceResponseType.getResult_namespace());
				context.putInt(Attributes.ATTR_RESPONSE_CODE,balanceResponseType.getResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				logger.error("Fail to call pin method from Mobifone",e);
			}
		}else if(AppConstants.MOBIFONE_SETTINGS.getResetSessionOperation().equalsIgnoreCase(operationType)){
			isSessionVerified=false;
			sessionId=null;
		}else{
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.UNSUPPORTED_OPERATION);
		}
		return context;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void reset() throws Exception {
		logger.info("recover lost connection...");
	}

	@Override
	public boolean isUsable() {
		return status;
	}

	@Override
	public void setUsable(boolean status) {
		this.status = status;

	}

	public CreatesessionResponseType createSession() throws RemoteException {
		UMarketSCStub.Createsession createsessionReq = new UMarketSCStub.Createsession();
		CreatesessionResponse createsessionRes = stub.createsession(createsessionReq);
		
		return createsessionRes.getCreatesessionReturn();
	}

	public StandardBizResponse login(String sessionId) throws RemoteException{
		UMarketSCStub.Login login = new UMarketSCStub.Login();
		LoginRequestType loginRequestType = new LoginRequestType();
		try{
			loginRequestType.setInitiator(AppConstants.MOBIFONE_SETTINGS.getUserName());
			loginRequestType.setSessionid(sessionId);
			loginRequestType.setPin(AppConstants.MOBIFONE_SETTINGS.getEncryptedPin(sessionId));
		}catch (Exception e) {
			throw  new RemoteException(e.getMessage(),e);
		}
		login.setLoginRequest(loginRequestType);
		LoginResponse logRes = stub.login(login);
		return logRes.getLoginReturn();
		
	}

	public StandardBizResponse getNextId(String sessionId)
			throws RemoteException {
		UMarketSCStub.Nextid nextid=new UMarketSCStub.Nextid();
		NextidRequestType nextidRequestType=new NextidRequestType();
		nextidRequestType.setSessionid(sessionId);
		nextid.setNextidRequest(nextidRequestType);
		NextidResponse nextidRes= stub.nextid(nextid);
		return nextidRes.getNextidReturn();
	}

	public MobiTxnQueryResult txnInquiry(String sessionId, int txnId)
			throws RemoteException {
		UMarketSCStub.Transquery transqueryWs=new  UMarketSCStub.Transquery();
		TransqueryRequestType transqueryRequestType=new TransqueryRequestType();
		transqueryRequestType.setSessionid(sessionId);
		transqueryRequestType.setTransid(txnId);
		transqueryWs.setTransqueryRequest(transqueryRequestType);
		TransqueryResponse transqueryRes= stub.transquery(transqueryWs);
		TransqueryResponseType responseType=transqueryRes.getTransqueryReturn();
		MobiTxnQueryResult mobiTxnQueryResult=new MobiTxnQueryResult(); 
		mobiTxnQueryResult.setTransid(responseType.getTransid());
		mobiTxnQueryResult.setResult(responseType.getResult());
		mobiTxnQueryResult.setResultNamespace(responseType.getResult_namespace());
		mobiTxnQueryResult.setInitiator(responseType.getInitiator());
		mobiTxnQueryResult.setCreditor(responseType.getCreditor());
		mobiTxnQueryResult.setAmount(responseType.getAmount());
		mobiTxnQueryResult.setType(responseType.getType());
		mobiTxnQueryResult.setDate(responseType.getDate());
		mobiTxnQueryResult.setState(responseType.getState());
		mobiTxnQueryResult.setTransResult(responseType.getTrans_result());
		mobiTxnQueryResult.setTransResultNamespace(responseType.getTrans_result_namespace());
		return mobiTxnQueryResult;
	}

	public StandardBizResponse buyAirtime(String sessionId, int type,long amount, String target, String msisdn, String referenceId) throws RemoteException {
		UMarketSCStub.Buy buyWs= new UMarketSCStub.Buy();
		BuyRequestType buyRequestType=new BuyRequestType();
		buyRequestType.setAmount(BigDecimal.valueOf(amount));
		buyRequestType.setSessionid(sessionId);
		buyRequestType.setTarget(target); //airtime
		buyRequestType.setType(type); //2
		buyRequestType.setRecipient(msisdn); //2
		buyWs.setBuyRequest(buyRequestType);
		buyRequestType.setReference1(referenceId);
		BuyResponse buyResponse= stub.buy(buyWs);
		return buyResponse.getBuyReturn();
	}

	public void transferAirtime(String sessionId) throws RemoteException {

	}

	public BalanceResponseType getBalance(String sessionId) throws RemoteException {
		UMarketSCStub.Balance balanceWs=new UMarketSCStub.Balance();
		BalanceRequestType balanceRequestType=new BalanceRequestType();
		balanceRequestType.setSessionid(sessionId);
		balanceWs.setBalanceRequest(balanceRequestType);
		BalanceResponse balanceRes= stub.balance(balanceWs);
		return balanceRes.getBalanceReturn();
	}
	
	public StandardBizResponse changePin(String sessionId, String initiator, String oldPin, String newPwd) throws RemoteException {
		UMarketSCStub.Pin changePin=new UMarketSCStub.Pin();
		PinRequestType pinRequestType=new PinRequestType();
		pinRequestType.setSessionid(sessionId);
		pinRequestType.setInitiator(initiator);
		pinRequestType.setPin(oldPin);
		pinRequestType.setNew_pin(newPwd);
		changePin.setPinRequest(pinRequestType);
		PinResponse pinResponse= stub.pin(changePin);
		return pinResponse.getPinReturn();
	}

}