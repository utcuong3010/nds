package com.mbv.bp.common.executor.client;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.executor.vietpay.services.TopupServiceStub;
import com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.CheckTransactionResponse;
import com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.GetServerListResponse;
import com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerBalanceResponse;
import com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.PartnerInfo;
import com.mbv.bp.common.executor.vietpay.services.TopupServiceStub.TopupResponse;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;

public class VietPayConnection implements IExecutable// , IIntegration
{
	
	private static Log log = LogFactory.getLog(VietPayConnection.class);
	private boolean status=false;
	private TopupServiceStub stub;
	private String address;
	
	public VietPayConnection(String address) {
		this.address=address;
	}

	@Override
	public void connect() throws Exception {
		try{
			if (stub==null){ 
				stub=new TopupServiceStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.VIETPAY_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VietPay",e);
		}
	}
	
	
	public void checkConnection(){
		try{
			if (stub==null ){ 
				stub=new TopupServiceStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.VIETPAY_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VietPay",e);
		}
	}
	
	@Override
	public ContextBase process(ContextBase context) {
		String operationType=context.get(Attributes.ATTR_OPERATION_TYPE);
		context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
		
		checkConnection();
		
		if (stub==null) {
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
			return context;
		}
		
		
		if(AppConstants.VIETPAY_SETTINGS.getTopupOperation().equalsIgnoreCase(operationType)){
			try{
				TopupResponse response=topup(context.get(Attributes.ATTR_TRANSACTION_ID),
											AppConstants.VIETPAY_SETTINGS.getTopupCode(context.get(Attributes.ATTR_TELCO_ID)),
											context.get(Attributes.ATTR_SERVER_ID),
											context.get(Attributes.ATTR_MSISDN), 
											context.getLong(Attributes.ATTR_AMOUNT));
				context.put(Attributes.ATTR_RESULT_INFO,response.getTopupResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from VietPay",e);
			}
		}else if(AppConstants.VIETPAY_SETTINGS.getBalanceOperation().equalsIgnoreCase(operationType)){
			try{
				PartnerBalanceResponse response=getBalance();
				context.put(Attributes.ATTR_RESULT_INFO,response.getPartnerBalanceResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to inquiry Txn from VietPay",e);
			}
		}else if(AppConstants.VIETPAY_SETTINGS.getTxnInquiryOperation().equalsIgnoreCase(operationType)){
			try{
				CheckTransactionResponse response=txnInquiry(context.get(Attributes.ATTR_TRANSACTION_ID));
				context.put(Attributes.ATTR_RESULT_INFO,response.getCheckTransactionResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to inquiry Txn from VietPay",e);
			}
		}else if(AppConstants.VIETPAY_SETTINGS.getServerListOperation().equalsIgnoreCase(operationType)){
			try{
				GetServerListResponse response=getServerList(AppConstants.VIETPAY_SETTINGS.getTopupCode(context.get(Attributes.ATTR_TELCO_ID)));
				context.put(Attributes.ATTR_RESULT_INFO,response.getGetServerListResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to inquiry Txn from VietPay",e);
			}
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
		log.info("recover lost connection...");
	}

	@Override
	public boolean isUsable() {
		return status;
	}

	@Override
	public void setUsable(boolean status) {
		this.status = status;

	}
	
	private TopupResponse topup(String txnId,String topupCode, String serverId,String accountId, long amount) throws RemoteException{
		TopupServiceStub.Topup topupWs=new TopupServiceStub.Topup();
		topupWs.setTopupCode(topupCode);
		topupWs.setAccount(accountId);
		topupWs.setAmount(BigDecimal.valueOf(amount));
		topupWs.setTransRef(txnId);
		if (StringUtils.isNotBlank(serverId))
		topupWs.setServer(serverId);
		PartnerInfo partnerInfo=new PartnerInfo();
		partnerInfo.setPartnerCode(AppConstants.VIETPAY_SETTINGS.getPartnerCode());
		partnerInfo.setPassword(AppConstants.VIETPAY_SETTINGS.getPassword());
		partnerInfo.setSignature(AppConstants.VIETPAY_SETTINGS.getTopupSignature(txnId, topupCode, accountId, amount));
		topupWs.setPartnerInfo(partnerInfo);
		return stub.topup(topupWs);
	}
	
	private PartnerBalanceResponse getBalance() throws RemoteException{
		TopupServiceStub.PartnerBalance balanceWs=new TopupServiceStub.PartnerBalance();
		balanceWs.setPartnerCode(AppConstants.VIETPAY_SETTINGS.getPartnerCode());
		balanceWs.setPassword(AppConstants.VIETPAY_SETTINGS.getPassword());
		return stub.partnerBalance(balanceWs);
	}
	
	private CheckTransactionResponse txnInquiry(String txnId) throws RemoteException{
		TopupServiceStub.CheckTransaction checkTransactionWs=new TopupServiceStub.CheckTransaction();
		checkTransactionWs.setTransRef(txnId);
		PartnerInfo partnerInfo=new PartnerInfo();
		partnerInfo.setPartnerCode(AppConstants.VIETPAY_SETTINGS.getPartnerCode());
		partnerInfo.setPassword(AppConstants.VIETPAY_SETTINGS.getPassword());
		partnerInfo.setSignature(AppConstants.VIETPAY_SETTINGS.getInquiryTxnSignature(txnId));
		checkTransactionWs.setPartnerInfo(partnerInfo);
		return stub.checkTransaction(checkTransactionWs);
	}
	
	private GetServerListResponse getServerList(String topupCode) throws RemoteException{
		TopupServiceStub.GetServerList request=new TopupServiceStub.GetServerList();
		request.setTopupCode(topupCode);
		PartnerInfo partnerInfo=new PartnerInfo();
		partnerInfo.setPartnerCode(AppConstants.VIETPAY_SETTINGS.getPartnerCode());
		partnerInfo.setPassword(AppConstants.VIETPAY_SETTINGS.getPassword());
		partnerInfo.setSignature(AppConstants.VIETPAY_SETTINGS.getServerListSignature());
		request.setPartnerInfo(partnerInfo);
		return stub.getServerList(request);
	}
	
	}