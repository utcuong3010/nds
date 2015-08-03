package com.mbv.bp.common.executor.client;

import java.rmi.RemoteException;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.executor.sim.services.SimServicesStub;
import com.mbv.bp.common.executor.sim.services.SimServicesStub.KeyValuePair;
import com.mbv.bp.common.executor.sim.services.SimServicesStub.KeyValuePairs;
import com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnRequest;
import com.mbv.bp.common.executor.sim.services.SimServicesStub.TxnResponse;
import com.mbv.bp.common.integration.ContextBase;

public class SimConnection {
	private static Log log = LogFactory.getLog(SimConnection.class);
	private SimServicesStub stub;
	private String address;
	
	public SimConnection() {
		this.address=AppConstants.ANYPAY_SETTINGS.getSimServiceUrl();
		connect();
	}
	
	public void connect(){
		try{
			if (stub==null){ 
				stub=new SimServicesStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.ANYPAY_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to SimService",e);
		}
	}
	
	
	public void checkConnection(){
		try{
			if (stub==null ){ 
				stub=new SimServicesStub(address);
				stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(AppConstants.ANYPAY_SETTINGS.getResponseTimeOut());
			}
		}catch (Exception e) {
			stub=null;
			log.error("Fail to connect to SimService",e);
		}
	}
	
	public ContextBase process(ContextBase context) throws Exception{
		ContextBase result=new ContextBase();
		checkConnection();
		if (stub==null) {
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.CONNECTION_FAILED);
			return context;
		}
		
		TxnRequest request=new TxnRequest();
		KeyValuePairs keyValuePairs=new KeyValuePairs(); 
		KeyValuePair keyValuePair;
		for(Entry<String, String> entry: context.entrySet()){
			keyValuePair=new KeyValuePair();
			keyValuePair.setKey(entry.getKey());
			keyValuePair.setValue(entry.getValue());
			keyValuePairs.addKeyValuePair(keyValuePair);
		}
		request.setKeyValuePairs(keyValuePairs);
		try{
			TxnResponse response=stub.txnRequest(request);
		
		result.setErrorCode(response.getErrorCode());
		
		if (response.getKeyValuePairs()!=null)
			for (KeyValuePair valuePair:response.getKeyValuePairs())
				result.put(valuePair.getKey(), valuePair.getValue());
		}catch (RemoteException e) {
			throw new Exception("Fail to invoke SimService",e);
		}
		return result;
	}

	
	
	}