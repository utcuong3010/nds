package com.mbv.anypay.queue.core;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.anypay.queue.utils.Utils;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.constants.QueueConstants;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.QueueConfig;
import com.mbv.bp.common.util.JacksonUtils;
import com.mbv.bp.common.vo.airtime.QueueRequest;

public class QueueServer {
	private static Log log= LogFactory.getLog(QueueServer.class);
	private BlockingQueue<QueueRequest> queue	= null;
	private AtomicBoolean runningFlag 		= new AtomicBoolean(false);
	private AtomicBoolean shutdownFlag		= new AtomicBoolean(false);
	private Object lock 					= new Object();
	private QueueConfig queueCfg			= null;
	private AtomicInteger currentQueueSize	= new AtomicInteger(0);
	private Poller poller;
	private static final long REQUEST_POLLER_INTERVAL=1000;
	public QueueServer(QueueConfig queueCfg) {
		this.queueCfg=queueCfg;
	} 
	
	public boolean startQueueServer(){
		if (runningFlag.compareAndSet(false, true)){
			try{
				this.queue = new ArrayBlockingQueue<QueueRequest>(queueCfg.getMaxSize());
				poller = new Poller(this);
				poller.setName(queueCfg.getQueueId()+"_POLLER");
				poller.setDaemon(true);
				poller.setPriority(Thread.NORM_PRIORITY);
				poller.start();
				loadPendingRequest();
				return true;
			}catch (Exception e) {
				log.error("Fail to start Queue server | QueueCfg-"+queueCfg);
				runningFlag.set(false);
				return false;
			}
		}
		return false;
	}
	
	private void loadPendingRequest() {
		List<QueueRequest> requests=RequestDbFactory.getInstance().getPendingRequests(queueCfg.getQueueId(),QueueConstants.QUEUE_STATUS_NEW); 
		synchronized ( lock ) {
			QueueRequest request=null;
			for( int i=0;i<requests.size();i++) {
				try {
					request=requests.get(i);
					request.setContext(Utils.getContextBaseFromMap(JacksonUtils.getMapFromJsonString(request.getContent())));
					queue.put( request );
					currentQueueSize.incrementAndGet();
				} catch (Exception e) {
					log.error( this.getQueueCfg().getQueueId() + " | Exception while loading pending request| request-"+request,e);
				}
			}
		}
	}

	public ContextBase process(QueueRequest request){

		ContextBase result=new ContextBase();
		result.setErrorCode(ErrorCode.SUCCESS);
		
		if (shutdownFlag.get()){
			log.error("Queue is shuting down | queueId-"+queueCfg.getQueueId());
			result.setErrorCode(ErrorCode.QUEUE_SHUTINGDOWN);
			return result;
		}
			
		synchronized (lock) {
			int currentSize=currentQueueSize.get();
			if (currentSize>= queueCfg.getMaxSize()){
				log.error("Queue is full | queueId-"+queueCfg.getQueueId()+" | queueSize-"+currentSize);
				result.setErrorCode(ErrorCode.QUEUE_FULL);
				return result;
			}
			queue.add(request);
			currentQueueSize.incrementAndGet();
		}

		return result;
	} 
	
	public synchronized QueueRequest getPollRequest(){
		QueueRequest request=null;
		try {
			request = queue.poll(REQUEST_POLLER_INTERVAL,TimeUnit.MILLISECONDS);
			if (request!=null)
				currentQueueSize.decrementAndGet();
				
		} catch (InterruptedException e) {
			log.error("Exception while poll request from queue. |queueId-"+queueCfg.getQueueId());
			request=null;
		}
		
		return request;
	}
	
	public boolean shutdownQueueServer(){
		boolean result=false;
		log.info("Going to shutdown queue-"+queueCfg.getQueueId()+"| terminable-"+queueCfg.isTerminable());
		if (!queueCfg.isTerminable()){
			while (currentQueueSize.get()!=0){
				try {
					log.info("Waiting for "+queueCfg.getQueueId()+" process all request..|queueSize-"+currentQueueSize.get());
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					log.warn("Waiting to shutdown queue-"+queueCfg.getQueueId(),e);
				}
			}
		}
		
		if (shutdownFlag.compareAndSet(false, true)){
			poller.shutdown();
			try {
				poller.join();
				result=true;
				log.info( "Shutdown poller completed !" );
			} catch (InterruptedException e) {
				log.error( "Exception occur when waiting for poller to shutdown. | ErrMsg-" + e.getMessage(), e );
				result=false;
			}	
		}
		return result;
	}
	
	public QueueConfig getQueueCfg(){
		return queueCfg;
	}

	public int getCurrentQueueSize() {
		return currentQueueSize.get();
	}
}
