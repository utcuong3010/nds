package com.mbv.airtime2.mobifone.actors;

import org.apache.log4j.Logger;

import com.mbv.airtime2.mobifone.domain.BalanceResponse;
import com.mbv.airtime2.mobifone.domain.Mapper;
import com.mbv.airtime2.mobifone.domain.TopupResponse;


import akka.actor.UntypedActor;

public class PersistenceActor extends UntypedActor{
	private static Logger LOGGER = Logger.getLogger(PersistenceActor.class);
	
	private Mapper mapper;
	
	public PersistenceActor(Mapper mapper){
		this.mapper = mapper;
	}
	
	public void preStart() {		
		LOGGER.info(getSelf() + " : Starting");	
	}

	public void postStop() {
		LOGGER.info(getSelf() + " : Stoping");
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		LOGGER.info(getSelf() + " : Receives from " + getSender() + " message " + message);
		try{
			if(message instanceof TopupResponse){
				mapper.UpdateTxn((TopupResponse)message);
			}else 
			if(message instanceof BalanceResponse){
				mapper.UpdateBalance((BalanceResponse)message);
			}else{
				throw new Exception("Unhandled message type " + message.getClass().toString());
			}
		}catch(Exception ex){
			LOGGER.error(getSelf(), ex);
		}
	}
}
