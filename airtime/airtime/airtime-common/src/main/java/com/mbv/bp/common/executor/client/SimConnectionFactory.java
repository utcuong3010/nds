package com.mbv.bp.common.executor.client;

public class SimConnectionFactory {
	
	private static class SimConnectionFactoryHolder{
//		private static SimConnection instance = new SimConnection();
	} 
	public static SimConnection getInstance(){
//		return SimConnectionFactoryHolder.instance;
		return new SimConnection();
	}
}
