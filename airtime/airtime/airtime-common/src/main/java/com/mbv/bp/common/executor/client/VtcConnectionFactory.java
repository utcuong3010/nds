package com.mbv.bp.common.executor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;

public class VtcConnectionFactory implements IObjectFactory<IExecutable>{
	private static Log logger = LogFactory.getLog(VtcConnectionFactory.class);
	private String address;
	public VtcConnectionFactory(String address) {
		this.address=address;
	} 
	@Override
	public IExecutable createObject() {
		IExecutable executable=new VtcConnection(address); 
		try{
			executable.connect();
			executable.setUsable(true);
		}catch (Exception e) {
			executable.setUsable(false);
			logger.error("Fail to Connect to Vtc Server", e);
		}
		return executable;
	}

}
