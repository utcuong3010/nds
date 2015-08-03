package com.mbv.anypay.queue.core;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.SimInfoDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.QueueConfig;
import com.mbv.bp.common.vo.airtime.QueueRequest;
import com.mbv.bp.common.vo.airtime.SimInfo;

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
	
	public void startUpQueueManager() throws Exception{
		
		QueueServer qServer=null;
		
		QueueConfig bpQueue=new QueueConfig();
		bpQueue.setMaxRetry(0);
		bpQueue.setMaxSize(50);
		bpQueue.setQueueId("ANYPAY");
		bpQueue.setTerminable(false);
		log.info("ANYPAY Queue config:"+bpQueue);
		int maxdeQueue=0;
		try{
			SimInfoDao simInfoDao=new SimInfoDao();
			List<SimInfo> simInfos=simInfoDao.selectAll();
			
			if (simInfos!=null)
				maxdeQueue=simInfos.size();
			if (maxdeQueue==0)
				throw new Exception("No Sim info configured");
		}catch (Exception e) {
			throw e;
		}
		bpQueue.setDequeue(maxdeQueue);
		log.info("ANYPAY Queue config:"+bpQueue);
		qServer	= new QueueServer(bpQueue);
		qServer.startQueueServer();
		queues.put(bpQueue.getQueueId(), qServer);
	}
	
	public ContextBase process(ContextBase context){

		if (log.isDebugEnabled()) {
			log.debug( "Receive request. |context-" + context.toString());
		}

		String errCode				= null;
		QueueRequest request=null; 
		ContextBase result 	= new ContextBase();
		if (StringUtils.isEmpty(context.get(Attributes.ATTR_ERROR_CODE)))
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.SUCCESS);
		
		
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
		String queueId 	= context.get( Attributes.ATTR_QUEUE_ID );
		
		if ( StringUtils.isBlank( queueId ) ) {
			errCode = ErrorCode.MISSING_QUEUE_ID;
		}
		return errCode;
	}
}
