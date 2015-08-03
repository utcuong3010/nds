package com.mbv.hotel.actor;

import akka.actor.Actor;
import akka.actor.UntypedActorFactory;

import com.mbv.hotel.cmd.WorkerCmd;


public abstract class AbstractHotelWorkerFactory implements UntypedActorFactory {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String workerName;
	private int numWorkers;
	
	public String getWorkerName() {
		return workerName;
	}
	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}
	public int getNumWorkers() {
		return numWorkers;
	}
	public void setNumWorkers(int numWorkers) {
		this.numWorkers = numWorkers;
	}
	
	
	public Actor create(){
		AbstractHotelWorker worker = doCreate();
		worker.setAvailableMessage(new WorkerCmd(getWorkerName()));
		return (Actor) worker;
	}
	
	protected abstract AbstractHotelWorker doCreate();
}








