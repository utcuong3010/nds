package com.mbv.airtime2.vngmobile;

import org.apache.log4j.Logger;

import com.mbv.airtime2.vngmobile.actor.Master;
import com.mbv.airtime2.vngmobile.domain.Mapper;
import com.mbv.airtime2.vngmobile.domain.ServiceStop;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;


public class Service {
	private static Logger LOGGER = Logger.getLogger(Service.class);
	
	private ActorSystem actorSystem;
	private ActorRef master;	
	private Config config;
	private boolean isTerminated = true;
	private Mapper mapper;
	
	
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public void start(){
		if(!isTerminated) return;
		LOGGER.info(config.getServiceId() + " : Starting");	
		isTerminated = false;
	
		actorSystem = ActorSystem.create(config.getServiceId());
		
		Props MobifoneRouterProps = new Props(new UntypedActorFactory() {			
			private static final long serialVersionUID = 1L;
			public Actor create() throws Exception {
				return new Master(config, mapper);
			}
		});
		
		master = actorSystem.actorOf(MobifoneRouterProps, "Master");
	}
	
	public void stop(){
		if(isTerminated) return;
		isTerminated = true;
		LOGGER.info(config.getServiceId() + " : Stopping ");		
		master.tell(new ServiceStop());
		while(!master.isTerminated()){			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}	
		}		
		actorSystem.shutdown();
	}
	
	public void process(Object request) throws Exception{
		if(isTerminated) throw new Exception(config.getServiceId() + " is terminated");
		master.tell(Utils.transformPayload(request));
	}
}
