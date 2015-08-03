package com.mbv.bp.queue.core;

import java.util.concurrent.Callable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.QueueRequest;

public class RequestProcessor implements Callable<Boolean>{
	private static Log log=LogFactory.getLog(RequestProcessor.class);
	private QueueRequest request;
	private QueueServer qserver;
	
	public RequestProcessor(QueueServer qserver, QueueRequest request) {
		this.request = request;
		this.qserver = qserver;
	}
	@Override
	public Boolean call() throws Exception {
		boolean result=false;
		long startTime 	= System.currentTimeMillis();
		ContextBase ctxResult=new ContextBase();
		try{
			ContextBase context = request.getContext();
			context.put(Attributes.ATTR_WFP_NAME, context.get(Attributes.ATTR_DEST_WFP));
			ctxResult = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			result=true;
		}catch (IntegrationException e) {
			ctxResult.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
			result=false;
		}finally{
			RequestDbFactory.getInstance().delete(request);
			if (ErrorCode.SUCCESS.equals(ctxResult.getErrorCode()))
				log.info(request.getQueueId()+" process request "+request.getRequestId()+"| status-SUCCESS| errorCode-"+ctxResult.getErrorCode()+"| processed time(ms):"+(System.currentTimeMillis()-startTime)+"| queueSize-"+qserver.getCurrentQueueSize());
			else
				log.error(request.getQueueId()+" process request "+request.getRequestId()+"| status-FAIL| errorCode-"+ctxResult.getErrorCode()+"| processed time(ms):"+(System.currentTimeMillis()-startTime)+"| queueSize-"+qserver.getCurrentQueueSize());
		}
		return result;
	}

}
