package com.mbv.airtime2.mobifone;

import org.apache.log4j.Logger;
import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.mobifone.actors.MobifoneMaster;
import com.mbv.airtime2.mobifone.domain.Mapper;
import com.mbv.airtime2.mobifone.domain.Stop;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;

public class MobifoneService {
	private static Logger LOGGER = Logger.getLogger(MobifoneService.class);
		
	private ActorSystem actorSystem;
	private ActorRef master;	
	private MobifoneConfig config;
	private Mapper mapper;
	private boolean isTerminated = true;
	
	
	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public void setConfig(MobifoneConfig config) {
		this.config = config;
	}
	
	public void start(){
		if(!isTerminated) return;
				
		LOGGER.info("MobifoneService : Starting");
		
		isTerminated = false;
		actorSystem = ActorSystem.create("MobifoneService");
		
		Props MobifoneRouterProps = new Props(new UntypedActorFactory() {			
			private static final long serialVersionUID = 1L;
			public Actor create() throws Exception {
				return new MobifoneMaster(config, mapper);
			}
		});
		
		master = actorSystem.actorOf(MobifoneRouterProps, "MobifoneMaster");
	}
	
	public void stop(){
		if(isTerminated) return;
		
		LOGGER.info("MobifoneService : Stopping ");
		isTerminated = true;
		master.tell(new Stop());
		while(!master.isTerminated()){			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}	
		}		
		actorSystem.shutdown();
	}
	
	
	public void process(IntegrationPayload payload) throws Exception{
		if(isTerminated) throw new Exception("Mobifone Service is terminated");
		
		LOGGER.info("MobifoneService : Receives IntegrationPayload" + payload);		
		try{
			Object msg = Utils.fromIntegrationPayload(payload);			
			master.tell(msg);
		}catch(Exception ex){
			LOGGER.error("MobifoneService : " + ex.getMessage() + " IntegrationPayload" + payload);
		}		
	}
}
