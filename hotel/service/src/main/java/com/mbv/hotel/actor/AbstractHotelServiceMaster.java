package com.mbv.hotel.actor;

import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.mbv.hotel.cmd.WorkerCmd;

public abstract class AbstractHotelServiceMaster extends UntypedActor{
	
	
	private List<AbstractHotelWorkerFactory> workerFactories;

	public AbstractHotelServiceMaster(List<AbstractHotelWorkerFactory> workerFactories){
		this.workerFactories = workerFactories;
	}

	public void preStart(){
		for (AbstractHotelWorkerFactory factory : workerFactories) {
			Props props = new Props(factory);
			for (int num = 0; num < factory.getNumWorkers(); num++) {
				getContext().actorOf(props, factory.getWorkerName() + num);
			}
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof WorkerCmd) {
			dispatchWorker((WorkerCmd) message, getSender());
		}
	}

	protected abstract void dispatchWorker(WorkerCmd message, ActorRef worker);
}
