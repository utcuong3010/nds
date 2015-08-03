package com.mbv.bp.common.executor;

public class ExecutorCfg {
	private String type;
	private String host;
	private int port;
	private String module;
	private int poolSize;
	private boolean localEnable;
	private String clazz;
	private String clientConnType;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public boolean isLocalEnable() {
		return localEnable;
	}
	public void setLocalEnable(boolean localEnable) {
		this.localEnable = localEnable;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	public String getClientConnType() {
		return clientConnType;
	}
	public void setClientConnType(String clientConnType) {
		this.clientConnType = clientConnType;
	}
	@Override
	public String toString() {
		return "ExecutorCfg [clazz=" + clazz + ", clientConnType="
				+ clientConnType + ", host=" + host + ", localEnable="
				+ localEnable + ", module=" + module + ", poolSize=" + poolSize
				+ ", port=" + port + ", type=" + type + "]";
	}
	
	
	
}
