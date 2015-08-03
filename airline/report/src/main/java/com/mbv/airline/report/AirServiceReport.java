package com.mbv.airline.report;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;

public class AirServiceReport {
	
	
	protected final Logger logger = Logger.getLogger(AirServiceReport.class);

	protected ActorSystem actorSystem;
	private ActorRef master;
	private Props masterProps;
	private Integer timeout;

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Props getMasterProps() {
		return masterProps;
	}

	public void setMasterProps(Props masterProps) {
		this.masterProps = masterProps;
	}

	public void start() {
		if (actorSystem == null) {
			actorSystem = ActorSystem.create("AirReport");
		}
		logger.info("Airline Checking will run in duration: " + timeout + " MINUTES");
		master = actorSystem.actorOf(masterProps, "Master");
		FiniteDuration interval = Duration.create(timeout, TimeUnit.MINUTES);
		actorSystem.scheduler().schedule(interval, interval, new Runnable() {
			public void run() {
				master.tell("Check", null);
			}
		}, actorSystem.dispatcher());

	}

	public void stop() {
		master.tell(PoisonPill.getInstance(), null);
	}
}
