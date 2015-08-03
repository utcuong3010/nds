package com.mbv.airtime2.vngmobile.actor;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.mbv.airtime2.vngmobile.Config;
import com.mbv.airtime2.vngmobile.domain.BalanceInfo;
import com.mbv.airtime2.vngmobile.domain.Mapper;
import com.mbv.airtime2.vngmobile.domain.ServiceStop;
import com.mbv.airtime2.vngmobile.domain.TopupRequest;
import com.mbv.airtime2.vngmobile.domain.TopupResponse;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;


public class Master extends UntypedActor{
	private static Logger LOGGER = Logger.getLogger(Master.class);	
	private ActorRef worker;
	private Config config;
	private Mapper mapper;
	private LinkedList<Object> pendingMessages = new LinkedList<Object>();		
	
	public Master(Config config, Mapper mapper){
		this.config = config;
		this.mapper = mapper;
	}
	
	public void preStart(){
		LOGGER.info(getSelf() + " : Starting");
		
		//Create worker actors
		Props WorkerProps = new Props(new UntypedActorFactory() {			
			private static final long serialVersionUID = 1L;
			public Actor create() throws Exception {
				return new Worker(config);
			}
		});		
		worker = getContext().actorOf(WorkerProps, "Worker");		
	}
	
	public void postStop() {
		LOGGER.info(getSelf() + " : Stoping");		
	}
	
	private boolean isWorkerFree = true;
	@Override
	public void onReceive(Object message) throws Exception {
		LOGGER.info(getSelf() + " : Receives from " + getSender() + " message " + message);
		if(message instanceof TopupRequest){
			if(pendingMessages.size() >= 100){
				TopupResponse response = new TopupResponse(
						((TopupRequest)message).getAtTxnId(), 
						TopupResponse.STATUS_ERROR, 
						TopupResponse.ERROR_NONE, 
						null);
				UpdateTransaction(response);
			}else{
				if(isWorkerFree) {
					worker.tell(message, getSelf());
					isWorkerFree = false;
				} else {
					pendingMessages.add(message);
				}
			}
		} else if(message instanceof TopupResponse){
			UpdateTransaction((TopupResponse)message);
			Object tmpMsg = pendingMessages.poll();
			if(tmpMsg == null){
				isWorkerFree = true;
			}else{
				worker.tell(tmpMsg, getSelf());
			}
		} else if(message instanceof ServiceStop){
			worker.tell(PoisonPill.getInstance());
			while(!pendingMessages.isEmpty()){
				Object tmpMsg = pendingMessages.poll();
				TopupResponse response = new TopupResponse(
						((TopupRequest)tmpMsg).getAtTxnId(), 
						TopupResponse.STATUS_ERROR, 
						TopupResponse.ERROR_NONE, 
						null);
				UpdateTransaction(response);
			}
			getContext().stop(getSelf());
		} else {
			LOGGER.error("Unhandled message type " + message.getClass().toString());
		}
		LOGGER.info(getSelf() + " : Pending messages " + pendingMessages.size());
	}
	

	
	private void UpdateTransaction(TopupResponse response){
		if(response.getAtTxnId()==null) return;
		try{			
			mapper.UpdateTxn(response);
			if(response.getBalance()!=null){
				mapper.UpdateBalance(new BalanceInfo(config.getServiceId(), response.getBalance()));			
			}
		}catch(Exception ex){
			LOGGER.error(getSelf(), ex);
		}
	}
}
