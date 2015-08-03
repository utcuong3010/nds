package com.mbv.airtime2.xpay;

import org.apache.log4j.Logger;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;

import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.xpay.actor.XpayMaster;
import com.mbv.airtime2.xpay.domain.Mapper;

public class XpayService {
	private static Logger LOGGER = Logger.getLogger(XpayService.class);

	private ActorSystem actorSystem;
	private ActorRef master;
	private XpayConfig config;
	private Mapper mapper;
	private boolean isTerminated = true;

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public void setConfig(XpayConfig config) {
		this.config = config;
	}

	public void process(IntegrationPayload payload) throws Exception {
		if (isTerminated)
			throw new Exception("XpayService is terminated");

		LOGGER.info("XpayService : Receives IntegrationPayload" + payload);
		try {
			Object msg = Utils.fromIntegrationPayload(config.getPostpaidSuffix(), payload);
			master.tell(msg);
		}
		catch (Exception ex) {
			LOGGER.error("XpayService : " + ex.getMessage() + " IntegrationPayload" + payload);
		}
	}

	public void start() {
		if (!isTerminated)
			return;

		LOGGER.info("XpayService : Starting");

		isTerminated = false;
		actorSystem = ActorSystem.create("XpayService");

		Props XPayRouteProps = new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;

			@Override
			public Actor create() throws Exception {
				return new XpayMaster(config, mapper);
			}
		});

		master = actorSystem.actorOf(XPayRouteProps, "XpayMaster");
	}

	public void stop() {
		if (isTerminated)
			return;
		LOGGER.info("XpayService : Stopping");
		isTerminated = true;
		master.tell(PoisonPill.getInstance());
		while (!master.isTerminated()) {
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
			}
		}
		actorSystem.shutdown();
	}

}
