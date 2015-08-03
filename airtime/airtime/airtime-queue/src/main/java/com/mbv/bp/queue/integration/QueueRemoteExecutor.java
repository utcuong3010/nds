package com.mbv.bp.queue.integration;

import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.protoc.IntegrationProtocServiceImp;
import com.mbv.bp.queue.core.QueueManager;


public class QueueRemoteExecutor extends IntegrationProtocServiceImp {
	QueueManager qManager		= QueueManager.getInstance();

	public ContextBase process( ContextBase context){
		ContextBase reqContext=new ContextBase();
		reqContext.putAll(context);
		return qManager.process(reqContext);
	}

	protected void startUp() {
		QueueManager.getInstance().startUpQueueManager();
	}

	public void shutdown() throws Exception {
		QueueManager.getInstance().shutdownQueueManager();
	}
}
