package com.mbv.bp.common.executor.client;

import java.rmi.RemoteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.executor.vtc.services.GoodsPaygateStub;
import com.mbv.bp.common.executor.vtc.services.GoodsPaygateStub.RequestTransaction;
import com.mbv.bp.common.executor.vtc.services.GoodsPaygateStub.RequestTransactionResponse;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.util.DateUtils;

public class VtcConnection implements IExecutable// , IIntegration
{
	
	private static Log log = LogFactory.getLog(VtcConnection.class);
	private boolean status=false;
	private GoodsPaygateStub stub;
	private String address;
	
	public VtcConnection(String address) {
		this.address=address;
	}

	@Override
	public void connect() throws Exception {
		try{
			if (stub==null){ 
				stub=new GoodsPaygateStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.VTC_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VTC",e);
		}
	}
	
	
	public void checkConnection(){
		try{
			if (stub==null ){ 
				stub=new GoodsPaygateStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.VTC_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VTC",e);
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
		
		
		if(AppConstants.VTC_SETTINGS.getTopupGameOperation().equalsIgnoreCase(operationType)){
			try{
				RequestTransactionResponse response=topupGame(AppConstants.VTC_SETTINGS.getVcoinServiceCode(), 
																context.get(Attributes.ATTR_MSISDN), 
																context.get(Attributes.ATTR_AMOUNT), 
																DateUtils.convertDate2String(context.getDate(Attributes.ATTR_TRANSACTION_DATE),"GMT+7","yyyyMMddmmss"), 
																context.get(Attributes.ATTR_TRANSACTION_ID));
				context.put(Attributes.ATTR_RESULT_INFO,response.getRequestTransactionResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from VTC",e);
			}
		}else if(AppConstants.VTC_SETTINGS.getCheckAccountOperation().equalsIgnoreCase(operationType)){
			try{
				RequestTransactionResponse response=checkAccount(AppConstants.VTC_SETTINGS.getVcoinServiceCode(), 
																context.get(Attributes.ATTR_MSISDN)
																);
				context.put(Attributes.ATTR_RESULT_INFO,response.getRequestTransactionResult());
			}catch (Exception e) {
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from VTC",e);
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
	
	public RequestTransactionResponse topupGame(String serviceCode,String account,String amount,String txnDate,String atTxnId) throws Exception{
		GoodsPaygateStub.RequestTransaction topupWs=new GoodsPaygateStub.RequestTransaction();
		RequestTransaction request=new RequestTransaction();
		request.setCommandType("TopupPartner");
		request.setPartnerCode(AppConstants.VTC_SETTINGS.getPartnerCode());
		request.setVersion("1.0");
		String data="<?xml version=\"1.0\" encoding=\"utf-16\"?>"+
					"<RequestData xmlnssi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlnssd=\"http://www.w3.org/2001/XMLSchema\">"+
					"<ServiceCode>"+serviceCode+"</ServiceCode>"+
					"<Account>"+account+"</Account>"+
					"<Amount>"+amount+"</Amount>"+
					"<Quantity>0</Quantity>"+
					"<TransDate>"+txnDate+"</TransDate>"+
					"<OrgTransID>"+atTxnId+"</OrgTransID>"+
					"<DataSign>"+AppConstants.VTC_SETTINGS.getSignature(serviceCode+"-"+account+"-"+amount+"-"+AppConstants.VTC_SETTINGS.getPartnerCode()+"-"+txnDate+"-"+atTxnId)+"</DataSign>"+
					"</RequestData>";
		request.setRequesData(data);
		return stub.requestTransaction(request);
	}
	
	public RequestTransactionResponse checkAccount(String serviceCode,String account) throws RemoteException,Exception{
		GoodsPaygateStub.RequestTransaction topupWs=new GoodsPaygateStub.RequestTransaction();
		RequestTransaction request=new RequestTransaction();
		request.setCommandType("CheckAccount");
		request.setPartnerCode(AppConstants.VTC_SETTINGS.getPartnerCode());
		request.setVersion("1.0");
		
		String data="<?xml version=\"1.0\" encoding=\"utf-16\"?>"+
					"<RequestData xmlnssi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlnssd=\"http://www.w3.org/2001/XMLSchema\">"+
					"<ServiceCode>"+serviceCode+"</ServiceCode>"+
					"<Account>"+account+"</Account>"+
					"<Quantity>0</Quantity>"+
					"<DataSign>"+AppConstants.VTC_SETTINGS.getSignature(serviceCode+"-"+account+"-"+AppConstants.VTC_SETTINGS.getPartnerCode())+"</DataSign>"+
					"</RequestData>";
		request.setRequesData(data);
		return stub.requestTransaction(request);
		
	}
	 
	public RequestTransactionResponse getBalance(String serviceCode) throws RemoteException,Exception{
		GoodsPaygateStub.RequestTransaction topupWs=new GoodsPaygateStub.RequestTransaction();
		RequestTransaction request=new RequestTransaction();
		request.setCommandType("GetBalance");
		request.setPartnerCode(AppConstants.VTC_SETTINGS.getPartnerCode());
		request.setVersion("1.0");
		String data="<?xml version=\"1.0\" encoding=\"utf-16\"?>"+
					"<RequestData xmlnssi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlnssd=\"http://www.w3.org/2001/XMLSchema\">"+
					"<ServiceCode>"+serviceCode+"</ServiceCode>"+
					"<DataSign>"+AppConstants.VTC_SETTINGS.getSignature(AppConstants.VTC_SETTINGS.getPartnerCode())+"</DataSign>"+
					"</RequestData>";
		request.setRequesData(data);
		return stub.requestTransaction(request);
		
	}	
}