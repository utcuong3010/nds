package com.mbv.bp.queue.core;

public class RequestDbFactory {
	private static class RequestDbFactoryHolder{
		private static RequestDb instance=new RequestDb();
	}
	
	public static RequestDb getInstance(){
		return RequestDbFactoryHolder.instance;
	}
}
