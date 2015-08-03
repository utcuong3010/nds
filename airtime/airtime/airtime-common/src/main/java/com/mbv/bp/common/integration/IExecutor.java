package com.mbv.bp.common.integration;

import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorCfg;

public interface IExecutor<T extends ContextBase> {
	public void init(ExecutorCfg executorCfg, IObjectFactory<IExecutable> objectFactory) throws IntegrationException;
	public T process(T context) throws IntegrationException;
	public void destroy();
}
