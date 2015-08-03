package com.mbv.airline.actors;

import org.springframework.beans.factory.annotation.Autowired;

import akka.actor.Actor;
import akka.actor.UntypedActorFactory;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.common.support.AirFarePriceCache;
import com.mbv.airline.common.support.AirItineraryReport;
import com.mbv.airline.common.support.AirItineraryRepository;


public abstract class AbstractWorkerFactory implements UntypedActorFactory {

	private String workerName;
	
	private int numWorkers;
	
	@Autowired
	private AirFarePriceCache farePriceCache;
	public AirFarePriceCache getFarePriceCache() {
		return farePriceCache;
	}
	
	@Autowired
	private AirItineraryRepository itineraryRepository;
	public AirItineraryRepository getItineraryRepository() {
		return itineraryRepository;
	}
	
	@Autowired
	private AirItineraryReport itineraryReport;
	public AirItineraryReport getItineraryReport() {
		return itineraryReport;
	}
		
	//email
	@Autowired
	private SenderManager senderManager;
	public SenderManager getSenderManager() {
		return senderManager;
	}

	public int getNumWorkers() {
		return numWorkers;
	}

	public void setNumWorkers(int numWorkers) {
		this.numWorkers = numWorkers;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public Actor create() throws Exception {
		AbstractWorker worker = doCreate();
		worker.setFarePriceCache(getFarePriceCache());
		worker.setItineraryRepository(getItineraryRepository());
		worker.setAvailableMessage(new WorkerAvailableMessage(getWorkerName()));
		worker.setItineraryReport(getItineraryReport());
		worker.setSenderManager(getSenderManager());
		return (Actor) worker;
	}

	protected abstract AbstractWorker doCreate();
}
