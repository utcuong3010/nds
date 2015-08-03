package com.mbv.bp.common.executor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;

public class VietPayConnectionFactory implements IObjectFactory<IExecutable>{
	private static Log logger = LogFactory.getLog(VietPayConnectionFactory.class);
	private String address;
	public VietPayConnectionFactory(String address) {
		this.address=address;
	} 
	@Override
	public IExecutable createObject() {
		IExecutable executable=new VietPayConnection(address); 
		try{
			executable.connect();
			executable.setUsable(true);
		}catch (Exception e) {
			executable.setUsable(false);
			logger.error("Fail to Connect to VietPay Server", e);
		}
		return executable;
	}

}
