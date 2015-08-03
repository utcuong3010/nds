package com.mbv.bp.common.executor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;

public class VnpayConnectionFactory implements IObjectFactory<IExecutable>{
	private static Log logger = LogFactory.getLog(VnpayConnectionFactory.class);
	String address;
	int port;
	public VnpayConnectionFactory(String address,int port) {
		this.address=address;
		this.port=port;
	} 
	@Override
	public IExecutable createObject() {
		IExecutable executable=new VnPayConnection(address, port); 
		try{
			executable.connect();
			executable.setUsable(true);
		}catch (Exception e) {
			executable.setUsable(false);
			logger.error("Fail to Connect to VnPay Server", e);
		}
		return executable;
	}

}
