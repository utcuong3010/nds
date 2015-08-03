package com.mbv.airline.actors;


import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;


public class AirlineServiceApplicationContext {
	
	final static Logger logger = Logger.getLogger(AirlineServiceApplicationContext.class);

    protected ActorSystem actorSystem;
    private ActorRef master;
    private Props masterProps;

    public Props getMasterProps() {
        return masterProps;
    }

    public void setMasterProps(Props masterProps) {
        this.masterProps = masterProps;
    }

    public void start() {
        if (actorSystem == null) {
            actorSystem = ActorSystem.create("AirlineService");
        }
        master = actorSystem.actorOf(masterProps, "Master");
    }

    public void stop() {
        master.tell(PoisonPill.getInstance(), null);
    }
 
}
