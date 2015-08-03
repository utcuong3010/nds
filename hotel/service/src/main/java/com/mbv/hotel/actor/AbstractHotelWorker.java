package com.mbv.hotel.actor;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;

import com.mbv.hotel.cmd.PayCmd;
import com.mbv.hotel.cmd.WorkerCmd;
import com.mbv.hotel.util.JsonUtils;

public abstract class AbstractHotelWorker extends UntypedActor{
	
	final static Logger logger = Logger.getLogger(AbstractHotelWorker.class);
	
	private WorkerCmd availableMessage;
	
	public void setAvailableMessage(WorkerCmd availableMessage) {
		this.availableMessage = availableMessage;
	}

	public void preStart(){
		logger.info("{} started " + getSelf());
		getContext().parent().tell(availableMessage, getSelf()); 
	}

	@Override
	public void onReceive(Object message) {	
		
		logger.info("Received msg:" + JsonUtils.toJson(message) + "");
		if(message instanceof PayCmd) {
			
			doPay((PayCmd)message);
		}
		//send to master know that i'm available
		getContext().parent().tell(availableMessage, getSelf());
	}

	protected abstract void doPay(PayCmd payCmd);
}















