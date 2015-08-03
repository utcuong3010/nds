package com.mbv.bp.common.executor.mobifone;

import java.util.concurrent.atomic.AtomicInteger;

public class MobifoneConnectionProperty {
	
	private String url;
	private AtomicInteger syncId=new AtomicInteger(0);
	private Object lock=new Object();
	
	private static class MobifoneConnectionPropertyHolder{
		private static MobifoneConnectionProperty instance=new MobifoneConnectionProperty();
	}
	
	public static MobifoneConnectionProperty getInstance(){
		return MobifoneConnectionPropertyHolder.instance;
	}
	private MobifoneConnectionProperty() {
		url="";
	}
	
	public void setProperties(String newUrl){
		synchronized (lock) {
			this.url=newUrl;
			int id=syncId.incrementAndGet();
			if (id>10000)
				syncId.set(0);
		}
	}
	
	public String getUrl(){
		return url;
	} 
	
	public int getSyncId() {
		return syncId.get();
	}
	
}
