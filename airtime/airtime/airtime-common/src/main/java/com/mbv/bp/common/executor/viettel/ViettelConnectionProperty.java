package com.mbv.bp.common.executor.viettel;
import java.util.concurrent.atomic.AtomicInteger;

public class ViettelConnectionProperty {
	private String ip;
	private int port;
	private Object lock=new Object();
	private AtomicInteger syncId=new AtomicInteger(0);
	
	private static class VietelConnectionPropertyHolder{
		private static ViettelConnectionProperty instance=new ViettelConnectionProperty();
	}
	
	public static ViettelConnectionProperty getInstance(){
		return VietelConnectionPropertyHolder.instance;
	}
	private ViettelConnectionProperty() {
		ip="";
		port=0;
	}
	
	public void setProperties(String ip, int port){
		synchronized (lock) {
			this.ip=ip;
			this.port=port;
			int id=syncId.incrementAndGet();
			if (id>10000)
				syncId.set(0);
		}
		
	}
	
	public String getIp(){
		return ip;
	} 
	
	public int getPort(){
		return port;
	}
	public int getSyncId() {
		return syncId.get();
	}
}
