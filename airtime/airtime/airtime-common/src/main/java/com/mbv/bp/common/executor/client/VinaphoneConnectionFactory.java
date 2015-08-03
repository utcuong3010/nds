package com.mbv.bp.common.executor.client;

public class VinaphoneConnectionFactory {
	
	private static class VinaphoneConnectionFactoryHolder{
		private static VinaphoneConnection instance = new VinaphoneConnection();
	} 
	public static VinaphoneConnection getInstance(){
		return VinaphoneConnectionFactoryHolder.instance;
	}
}
