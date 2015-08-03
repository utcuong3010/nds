package com.mbv.airtime2.mobifone.actors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.mbv.airtime2.mobifone.MobifoneConfig;
import com.mbv.airtime2.mobifone.domain.BalanceRequest;
import com.mbv.airtime2.mobifone.domain.HeartBeat;
import com.mbv.airtime2.mobifone.domain.Mapper;
import com.mbv.airtime2.mobifone.domain.Stop;
import com.mbv.airtime2.mobifone.domain.TopupRequest;
import com.mbv.airtime2.mobifone.domain.TopupResponse;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

public class MobifoneMaster extends UntypedActor {
	private static Logger LOGGER = Logger.getLogger(MobifoneMaster.class);
	
	private MobifoneConfig config;
	private Mapper mapper;
	private ActorRef resultHandler;
	private LinkedList<Object> pendingMessages = new LinkedList<Object>();			
	private LinkedList<ActorRef> freeWorkers = new LinkedList<ActorRef>();
	private HashSet<ActorRef> activeWorkers = new HashSet<ActorRef>();
	private int maxQueueCapacity = 0;
	
	public MobifoneMaster(MobifoneConfig config, Mapper mapper){
		this.config = config;			
		this.mapper = mapper;
	}
	
	public void preStart(){
		LOGGER.info(getSelf() + " : Starting");		

		//create persistence actor		
		resultHandler = getContext().actorOf(
				new Props(new UntypedActorFactory() {
					private static final long serialVersionUID = 1L;
					public Actor create() throws Exception {
						return new PersistenceActor(mapper);
					}
				}), 
				"MobifoneResultHandler");
		
		//Create worker actors
		Props MobifoneActorProps = new Props(new UntypedActorFactory() {			
			private static final long serialVersionUID = 1L;
			public Actor create() throws Exception {
				return new MobifoneActor(config, resultHandler);
			}
		});
		
		for(int i = 0; i < config.getQueueWorkers(); i++){
			getContext().actorOf(MobifoneActorProps);
		}
	}
	
	@Override
	public void onReceive(Object message) throws Exception {	
		LOGGER.info(getSelf() + " : Receives from " + getSender() + " message " + message);
		if(message instanceof TopupRequest){
			DispatchRequest(message);
		}else if (message instanceof BalanceRequest){
			DispatchRequest(message);		
		}else if (message instanceof HeartBeat){
			DispatchWorker(getSender(), (HeartBeat) message);			
		} else if (message instanceof Stop){
			Stop();
		}else{
			LOGGER.error("Unhandled message type " + message.getClass().toString());
		}
	}	
	
	public void DiscardMessage(Object message){
		if(message instanceof TopupRequest){
			TopupResponse response = new TopupResponse(
					((TopupRequest) message).getAtTxnId(), 
					TopupResponse.STATUS_ERROR, 
					TopupResponse.ERROR_NONE, 
					null);
			SendMessage(resultHandler, response);
		}else{
			LOGGER.info(getSelf() + " : Discard message " + message);
		}
	}
	
	public void Stop() {
		while(!pendingMessages.isEmpty()){
			DiscardMessage(pendingMessages.poll());
		}
		
		//stopping workers
		if(!activeWorkers.isEmpty()){
			ArrayList<ActorRef> workers = new ArrayList<ActorRef>(activeWorkers); 		
			for(ActorRef worker : workers){
				worker.tell(PoisonPill.getInstance());
			}
		
			boolean allTerminated = false;
			while(!allTerminated){
				allTerminated = true;
				for(ActorRef worker : workers){
					allTerminated = allTerminated && worker.isTerminated();
				}
			}
		}
		
		getContext().stop(getSelf());
				
	}
	
	private void SendMessage(ActorRef receiver, Object msg){
		LOGGER.info(getSelf() + " : Dispatches to " + receiver + " message " + msg);
		receiver.tell(msg, getSelf());
	}
	
	private void DispatchRequest(Object message){
		if(freeWorkers.isEmpty()){
			if(pendingMessages.size() < maxQueueCapacity){
				LOGGER.info(getSelf() + " : Enqueue message " + message);
				pendingMessages.add(message);
			}else{
				DiscardMessage(message);
			}
		} else {
			SendMessage(freeWorkers.poll(), message);
		}		
	}
	
	private void DispatchWorker(ActorRef worker, HeartBeat heartBeat){
		if(heartBeat.getStatus() == HeartBeat.Status.READY){			
			activeWorkers.add(worker);
			if(pendingMessages.isEmpty()){
				freeWorkers.add(worker);
			}else{
				SendMessage(worker, pendingMessages.poll());				
			}
		} else {
			activeWorkers.remove(worker);
		}
			
		maxQueueCapacity = activeWorkers.size() * config.getQueueFactor();
		while(pendingMessages.size() > maxQueueCapacity){
			DiscardMessage(pendingMessages.poll());				
		}
				
		LOGGER.info(getSelf() + " : Active workers = " + activeWorkers.size() +", Free workers = " + freeWorkers.size() +", Queue Size = " + pendingMessages.size());
	}
}
