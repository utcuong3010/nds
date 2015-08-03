package com.mbv.bp.common.integration;


public interface IWorkflow<T> {
	public T process(T context) throws Exception;
}
