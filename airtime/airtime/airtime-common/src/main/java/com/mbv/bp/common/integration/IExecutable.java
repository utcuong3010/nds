package com.mbv.bp.common.integration;

public interface IExecutable {
	
	public ContextBase process(ContextBase context);
	void destroy();
	void connect() throws Exception;
	void reset() throws Exception;
	boolean isUsable();
	void setUsable(boolean status);

}
