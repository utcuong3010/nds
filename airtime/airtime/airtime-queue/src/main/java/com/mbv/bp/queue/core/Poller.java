package com.mbv.bp.queue.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.vo.airtime.QueueRequest;

public class Poller extends Thread{
	private static Log log=LogFactory.getLog(Poller.class); 
	private AtomicBoolean shutdownFlag =new AtomicBoolean(false);	
	private QueueServer queue;
	private ThreadPoolExecutor executor		= null;
	public Poller(QueueServer queue) {
		this.queue=queue;
		if (queue.getQueueCfg().getDequeue()>0){
			executor =(ThreadPoolExecutor)Executors.newFixedThreadPool(queue.getQueueCfg().getDequeue()); 
		}
	}
	@Override
	public void run() {
		QueueRequest request=null;
		while (true){
			try{
				if (shutdownFlag.get()){
					log.info("Poller is shutting down.");
					break;
				}
				if (executor!=null){
					if ( executor.getActiveCount() < executor.getCorePoolSize() ) {
						request = this.queue.getPollRequest();
						if( request == null ) {
							continue;
						}
						log.info("Going to process poll request.| requestId:"+request.getRequestId());
						executor.submit(new RequestProcessor(queue,request));	
					}
				}
			}catch (Exception e) {
				log.info("Unexpected error found.",e);
			}
		}
		
		try {
			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			log.error("Exception while waiting for shutdown poller.",e);
		}
		
	}
	
	public void shutdown(){
		if (shutdownFlag.compareAndSet(false, true)){
			log.info("Going to shutdown poller.");
		}else
			log.info("Shutdown poller in progress.");
	}

}
