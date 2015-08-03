package com.mbv.bp.common.executor.client;

import java.rmi.RemoteException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.executor.vinaphone.services.BasicInput;
import com.mbv.bp.common.executor.vinaphone.services.BasicOutput;
import com.mbv.bp.common.executor.vinaphone.services.EloadFunctionServiceLocator;
import com.mbv.bp.common.executor.vinaphone.services.EloadFunctionSoapBindingStub;
import com.mbv.bp.common.executor.vinaphone.services.EnquiryOutput;
import com.mbv.bp.common.executor.vinaphone.services.LoginOutput;
import com.mbv.bp.common.executor.vinaphone.services.TransactionInput;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;

public class VinaphoneConnection {
	private static Log log = LogFactory.getLog(VinaphoneConnection.class);
	private EloadFunctionSoapBindingStub stub;
	
	public VinaphoneConnection() {
		connect();
	}
	
	public void connect(){
		try{
			if (stub==null){ 
				EloadFunctionServiceLocator locator=new EloadFunctionServiceLocator();
				locator.setEloadFunctionEndpointAddress(AppConstants.VINAPHONE_SETTINGS.getServiceUrl());
				stub=(EloadFunctionSoapBindingStub)locator.getEloadFunction();
				stub.setTimeout(AppConstants.VINAPHONE_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VinaphoneService",e);
		}
	}
	
	
	public void checkConnection(){
		try{
			if (stub==null ){ 
				EloadFunctionServiceLocator locator=new EloadFunctionServiceLocator();
				locator.setEloadFunctionEndpointAddress(AppConstants.VINAPHONE_SETTINGS.getServiceUrl());
				stub=(EloadFunctionSoapBindingStub)locator.getEloadFunction();
				stub.setTimeout(AppConstants.VINAPHONE_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to VinaphoneService",e);
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
		
		if(AppConstants.VINAPHONE_SETTINGS.getLoginOperation().equalsIgnoreCase(operationType)){
			try{
				
				BasicInput input=new BasicInput(
						AppConstants.VINAPHONE_SETTINGS.getUserName(),
						AppConstants.VINAPHONE_SETTINGS.getShaPassword(),
						AppConstants.VINAPHONE_SETTINGS.getAgentMsisdn());
				
				LoginOutput output=stub.login(input);
				result.put(Attributes.ATTR_SESSION_ID, output.getSessionid());
				result.put(Attributes.ATTR_RESPONSE_CODE, output.getStatus());
				result.put(Attributes.ATTR_DESCRIPTION, output.getMessage());
				log.info("LogIn Vinaphone| context:"+result);
			}catch (RemoteException e) {
				result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from Vinaphone |context:"+result,e);
			}
		}else if(AppConstants.VINAPHONE_SETTINGS.getLogoutOperation().equalsIgnoreCase(operationType)){
			try{
				BasicOutput response=stub.logout(context.getString(Attributes.ATTR_SESSION_ID));
				result.put(Attributes.ATTR_MESSAGE_ID, response.getTransId());
				result.put(Attributes.ATTR_RESPONSE_CODE, response.getStatus());
				result.put(Attributes.ATTR_DESCRIPTION, response.getMessage());
				log.info("Logout Vinaphone| context:"+result);
			}catch (RemoteException e) {
				result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from Vinaphone |context:"+result,e);
			}
		}else if(AppConstants.VINAPHONE_SETTINGS.getTopupOperation().equalsIgnoreCase(operationType)){
			try{
				
				TransactionInput input=new TransactionInput(
						AppConstants.VINAPHONE_SETTINGS.getUserName(),
						AppConstants.VINAPHONE_SETTINGS.getSignature(context.getString(Attributes.ATTR_SESSION_ID)),
						AppConstants.VINAPHONE_SETTINGS.getAgentMsisdn(),
						context.getString(Attributes.ATTR_MSISDN),
						context.getDouble(Attributes.ATTR_AMOUNT),
						Integer.parseInt(IdGeneratorFactory.getInstance().getVinaphoneIdGenentor().generateId())
						);
				
				EnquiryOutput response=stub.load(input);
				result.put(Attributes.ATTR_MESSAGE_ID,response.getTransId());
				result.putDouble(Attributes.ATTR_BALANCE_AFTER_TXN,response.getRemainAmount());
				result.put(Attributes.ATTR_RESPONSE_CODE,response.getStatus());
				result.putDouble(Attributes.ATTR_PRICE,response.getTotalAmount());
				result.put(Attributes.ATTR_DESCRIPTION,response.getMessage());
				log.info("Topup Vinaphone| context:"+result);
			}catch (RemoteException e) {
				result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
				log.error("Fail to call topup from Vinaphone |context:"+result,e);
			}
		}else{
			result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.UNSUPPORTED_OPERATION);
		}
		return result;
	}
	
	
	
	
	}