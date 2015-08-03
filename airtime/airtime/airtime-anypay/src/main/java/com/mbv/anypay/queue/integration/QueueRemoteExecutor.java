package com.mbv.anypay.queue.integration;

import com.mbv.anypay.queue.core.QueueManager;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.protoc.IntegrationProtocServiceImp;


public class QueueRemoteExecutor extends IntegrationProtocServiceImp {
	QueueManager qManager		= QueueManager.getInstance();

	public ContextBase process( ContextBase context){
		ContextBase reqContext=new ContextBase();
		reqContext.putAll(context);
		return qManager.process(reqContext);
	}

	protected void startUp() {
		try{
			QueueManager.getInstance().startUpQueueManager();
		}catch (Exception e) {
			log.info("Fail to init anypay queue.| Going to exit.",e);
			System.exit(-1);
		}
	}

	public void shutdown() throws Exception {
		QueueManager.getInstance().shutdownQueueManager();
	}
}
