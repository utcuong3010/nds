package com.mbv.bp.common.integration;

public interface IPool<T> {
	
	public void setObjectFactory(IObjectFactory<T> objectFactory);
	public void setPoolSize(int poolSize);
	public void initialize();
	public T getObject()throws Exception;
	public void releaseObject(T t);
	public void destroy();
	
}
