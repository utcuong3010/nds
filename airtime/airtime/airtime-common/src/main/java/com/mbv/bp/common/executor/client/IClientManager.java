package com.mbv.bp.common.executor.client;

import com.mbv.bp.common.integration.ContextBase;

public interface IClientManager {
	void init(String ip,int port);
	boolean reconnect(String ip, int port);
	void shutdown();
	public ContextBase process(ContextBase context);
	public boolean isSuspend();
	public void setSuspend(boolean newValue);
}
