package com.mbv.bp.common.executor.client;

import java.rmi.RemoteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.executor.vasc.services.VinaControllerServiceStub;
import com.mbv.bp.common.executor.vasc.services.VinaControllerServiceStub.Eload;
import com.mbv.bp.common.executor.vasc.services.VinaControllerServiceStub.EloadResponse;
import com.mbv.bp.common.executor.vasc.services.VinaControllerServiceStub.Params;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;

public class VascConnection {
	private static Log log = LogFactory.getLog(VascConnection.class);
	private VinaControllerServiceStub stub;
	private String address;
	
	public VascConnection() {
		this.address=AppConstants.VASC_SETTINGS.getServiceUrl();
		connect();
	}
	
	public void connect(){
		try{
			if (stub==null){ 
				stub=new VinaControllerServiceStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.VASC_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VascService",e);
		}
	}
	
	
	public void checkConnection(){
		try{
			if (stub==null ){ 
				stub=new VinaControllerServiceStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.VASC_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VascService",e);
		}
	}
	
	public ContextBase process(ContextBase context) throws Exception{
		ContextBase result=new ContextBase();
		result.setErrorCode(ErrorCode.SUCCESS);
		
		checkConnection();
		
		if (stub==null) {
			result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
			return result;
		}
		
		String operationType=context.get(Attributes.ATTR_OPERATION_TYPE);
		
		if(AppConstants.VASC_SETTINGS.getTopupOperation().equalsIgnoreCase(operationType)){
			try{
				String phoneNumber=context.getString(Attributes.ATTR_MSISDN);
				if (phoneNumber.startsWith("0")) phoneNumber="84"+phoneNumber.substring(1); 
				Eload eload=new Eload();
				Params param =new Params();
				String traceId=IdGeneratorFactory.getInstance().getVascIdGenentor().generateId();
				param.setCounter(Integer.parseInt(traceId));
				param.setAmount(context.getString(Attributes.ATTR_AMOUNT));
				param.setOption("");
				param.setPhone_number(AppConstants.VASC_SETTINGS.encryptMsisdn(phoneNumber));
				param.setSignature(AppConstants.VASC_SETTINGS.getSignature(phoneNumber, context.getString(Attributes.ATTR_AMOUNT), traceId, ""));
				param.setUsername(AppConstants.VASC_SETTINGS.getUserName());
				eload.setObj(param);
				EloadResponse response=stub.eload(eload);
				result.putInt(Attributes.ATTR_STATUS_CODE,response.get_return().getStatus());
				result.putInt(Attributes.ATTR_RESPONSE_CODE,response.get_return().getMessage_code());
				result.putLong(Attributes.ATTR_BALANCE_AFTER_TXN,(long)response.get_return().getBalance());
				result.putInt(Attributes.ATTR_MESSAGE_ID,response.get_return().getTrace_id());
				result.putString(Attributes.ATTR_SIGNATURE,response.get_return().getSign());
				result.putLong(Attributes.ATTR_PRICE,(long)response.get_return().getAmount());
				
				if (!AppConstants.VASC_SETTINGS.verifyResult(
						response.get_return().getStatus(),
						(long)response.get_return().getAmount(),
						response.get_return().getTrace_id(),
						(long)response.get_return().getBalance(),
						response.get_return().getMessage_code(),
						response.get_return().getSign()
						)){
					throw new Exception("Fail to verify signature");
				}
				log.info("Vasc topup result:"+result);
			}catch (RemoteException e) {
				result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from VASC |context:"+result,e);
			}
		}else if(AppConstants.VASC_SETTINGS.getCheckConnectionOperation().equalsIgnoreCase(operationType)){
			try{
				String phoneNumber="84910000001";
				Eload eload=new Eload();
				Params param =new Params();
				String traceId="0";
				String amount="0";
				param.setCounter(Integer.parseInt(traceId));
				param.setAmount("0");
				param.setOption("");
				param.setPhone_number(AppConstants.VASC_SETTINGS.encryptMsisdn(phoneNumber));
				param.setSignature(AppConstants.VASC_SETTINGS.getSignature(phoneNumber, amount, traceId, ""));
				param.setUsername(AppConstants.VASC_SETTINGS.getUserName());
				eload.setObj(param);
				EloadResponse response=stub.eload(eload);
				result.setErrorCode(ErrorCode.SUCCESS);
			}catch (RemoteException e) {
				result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from VASC",e);
			}
		}else{
			result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.UNSUPPORTED_OPERATION);
		}
		return result;
	}

	
	
	}