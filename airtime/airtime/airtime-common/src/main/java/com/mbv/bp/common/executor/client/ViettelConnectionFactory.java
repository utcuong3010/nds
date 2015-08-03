package com.mbv.bp.common.executor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;

public class ViettelConnectionFactory implements IObjectFactory<IExecutable>{
	protected static final Log log = LogFactory.getLog(ViettelConnectionFactory.class);
	String address;
	int port;
	public ViettelConnectionFactory(String address,int port) {
		this.address=address;
		this.port=port;
	} 
	@Override
	public ViettelConnection createObject() {
		ViettelConnection viettelConnection=new ViettelConnection(address, port); 
		try{
			viettelConnection.connect();
		}catch (Exception e) {
			viettelConnection.setUsable(false);
			log.error("Fail to Connect to Viettel Server", e);
		}
		return viettelConnection;
	}

}
