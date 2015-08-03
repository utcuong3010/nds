package com.mbv.bp.common.executor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.executor.mobifone.MobifoneConnectionProperty;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;

public class MobifoneConnectionFactory implements IObjectFactory<IExecutable>{
	private static Log logger = LogFactory.getLog(MobifoneConnectionFactory.class);
	String address;
	public MobifoneConnectionFactory(String address) {
		this.address=address;
	} 
	@Override
	public IExecutable createObject() {
		MobifoneConnectionProperty.getInstance().setProperties(address);
		IExecutable executable=new MobifoneConnection(); 
		try{
			executable.connect();
			executable.setUsable(true);
		}catch (Exception e) {
			executable.setUsable(false);
			logger.error("Fail to Connect to Mobifone Server", e);
		}
		return executable;
	}

}
