package com.mbv.bp.common.executor.client;

import com.mbv.bp.common.executor.viettel.ViettelClientManager;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;

public class ViettelConnection implements IExecutable//, IIntegration
{
  private String ip="127.0.0.1";	
  private int port=10002;

public ViettelConnection(String address, int port) {
	this.ip=address;
	this.port=port;
	
}

@Override
public void connect() throws Exception {
	ViettelClientManager.getInstance().init(ip, port);
}

@Override
public void destroy() {
	ViettelClientManager.getInstance().shutdown();
}

@Override
public boolean isUsable() {
	return true;
}

@Override
public ContextBase process(ContextBase context) {
	ContextBase result=ViettelClientManager.getInstance().process(context);
	return result;
}

@Override
public void reset() throws Exception {
	
}

@Override
public void setUsable(boolean status) {
	
}
 

}