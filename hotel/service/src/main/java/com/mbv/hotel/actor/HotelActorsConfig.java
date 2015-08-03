package com.mbv.hotel.actor;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;


@Configuration
@ImportResource("classpath:actors-context.xml")
public class HotelActorsConfig {
	
	
	final static Logger logger = Logger.getLogger(HotelActorsConfig.class);

	protected ActorSystem actorSystem;
	private ActorRef master;
	private Props masterProps;

	public void setMasterProps(Props masterProps) {
		this.masterProps = masterProps;
	}

	public void start() {
		if (actorSystem == null) {
			actorSystem = ActorSystem.create("HotelService");
		}
		master = actorSystem.actorOf(masterProps, "Master");
	}

	public void stop() {
		master.tell(PoisonPill.getInstance(), null);
	}
}
