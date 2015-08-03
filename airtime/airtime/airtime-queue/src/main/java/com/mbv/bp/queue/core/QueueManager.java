package com.mbv.bp.queue.core;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.constants.QueueConstants;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.QueueConfig;
import com.mbv.bp.common.vo.airtime.QueueRequest;

public class QueueManager {
	
	private static final Log log = LogFactory.getLog( QueueManager.class );
	private Map<String, QueueServer> queues 	= new ConcurrentHashMap<String, QueueServer>();
	private AtomicBoolean shutdownFlag = new AtomicBoolean(false);
	
	private static class QueueManagerHolder{
		private static QueueManager instance = new QueueManager();
	} 
	
	private QueueManager() {
	}
	
	public static QueueManager getInstance(){
		return QueueManagerHolder.instance;
	}
	
	public void startUpQueueManager(){
		QueueServer qServer=null;
		for ( QueueConfig bpQueue : QueueConstants.QUEUE_CONFIGS ) {
			qServer	= new QueueServer(bpQueue);
			qServer.startQueueServer();
			queues.put(bpQueue.getQueueId(), qServer);
		}
	}
	
	public ContextBase process(ContextBase context){

		if (log.isDebugEnabled()) {
			log.debug( "Receive request. |context-" + context.toString());
		}

		String errCode				= null;
		QueueRequest request=null; 
		ContextBase result 	= new ContextBase();
		if (StringUtils.isEmpty(context.get(Attributes.ATTR_ERROR_CODE)))context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
		
		
		try {

			if( shutdownFlag.get()) {
				log.warn( "Queue Manager is shuting down." );
				result.put( Attributes.ATTR_ERROR_CODE, ErrorCode.QUEUE_SHUTINGDOWN );
				return result;
			}

			errCode = validate( context );
			context.put(Attributes.ATTR_QUEUE_REQUEST_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
			
			if( !ErrorCode.SUCCESS.equals( errCode ) ) {
				result.put( Attributes.ATTR_ERROR_CODE, errCode );
				return result;
			}
			
			request = RequestDbFactory.getInstance().insert(context );

			if( request == null ) {
				errCode = ErrorCode.DATABASE_EXCEPTION;
				result.put( Attributes.ATTR_ERROR_CODE, errCode );
				log.error( "Fail to insert context to queue db. | ErrorCode-" +	errCode + " | context-" + context);
				return result;
			}

			QueueServer qServer= queues.get(request.getQueueId());	
			if (qServer==null)
				result.setErrorCode(ErrorCode.QUEUE_NOT_EXISTED);
			else
				result=qServer.process(request);
			
			if (!ErrorCode.SUCCESS.equals(result.getErrorCode())){
				RequestDbFactory.getInstance().delete(request);
				log.error("Process put request to Queue failed. | result-"+result);
			}else{
				log.info("Process put request to Queue successful. | result-"+result);
			} 
			
			return result;
		} catch (Exception e) {
			log.error( "Exception while processing received context. | context-" + context.toString() + " | ErrorMessage-" + e.getMessage(), e );
			result.put( Attributes.ATTR_ERROR_CODE, ErrorCode.SYS_INTERNAL_ERROR );
			return result;
		}
	}
	
	public void shutdownQueueManager(){
		log.info("Going to shutdown queue manager.");
		for ( Entry<String, QueueServer> entry :queues.entrySet() ) {
			entry.getValue().shutdownQueueServer();
		}
	}
	
	protected String validate( Map<String, String> context ) {
		String errCode 	= ErrorCode.SUCCESS;
		String destWfp 	= context.get( Attributes.ATTR_DEST_WFP);
		String queueId 	= context.get( Attributes.ATTR_QUEUE_ID );
		if ( StringUtils.isBlank( destWfp ) ) {
			errCode = ErrorCode.MISSING_DEST_WFP;
		} else if ( StringUtils.isBlank( queueId ) ) {
			errCode = ErrorCode.MISSING_QUEUE_ID;
		}
		return errCode;
	}
}
