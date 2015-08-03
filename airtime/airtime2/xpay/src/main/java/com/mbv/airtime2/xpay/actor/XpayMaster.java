package com.mbv.airtime2.xpay.actor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.RoundRobinRouter;
import akka.util.Duration;

import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.xpay.Utils;
import com.mbv.airtime2.xpay.XpayConfig;
import com.mbv.airtime2.xpay.domain.AtTransaction;
import com.mbv.airtime2.xpay.domain.BalanceRequest;
import com.mbv.airtime2.xpay.domain.BalanceResponse;
import com.mbv.airtime2.xpay.domain.CheckUnknownRequest;
import com.mbv.airtime2.xpay.domain.Handshake;
import com.mbv.airtime2.xpay.domain.Login;
import com.mbv.airtime2.xpay.domain.Mapper;
import com.mbv.airtime2.xpay.domain.Stop;
import com.mbv.airtime2.xpay.domain.TopupRequest;

public class XpayMaster extends UntypedActor {
	private static Logger LOGGER = Logger.getLogger(XpayMaster.class);
	private int numberOfRequestMessages = 0;
	private XpayConfig config;
	private Mapper mapper;
	ActorRef router;
	private final int MILITOHOURCONVERSION = 3600 * 1000;
	private Duration checkPendingInteval = null;
	private Duration checkBalanceInteval = null;
	private final Duration HandshakeInterval = Duration.create(300, TimeUnit.SECONDS);

	public static String sessionId = "";

	public static String getSessionId() {
		return sessionId;
	}

	public XpayMaster(XpayConfig config, Mapper mapper) {
		this.config = config;
		this.mapper = mapper;
	}

	@Override
	public void preStart() {
		LOGGER.info(getSelf() + " : Starting");
		checkBalanceInteval = Duration
				.create(config.getBalanceCheckingInterval(), TimeUnit.SECONDS);
		checkPendingInteval = Duration.create(config.getPendingTransactionCheckingInterval(),
				TimeUnit.SECONDS);
		/*
		 * Create a router with numberOfChildren children , use RoundRobin
		 */
		router = getContext().actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;

			@Override
			public Actor create() throws Exception {
				return new XpayActor(config, getSelf());
			}
		}).withRouter(new RoundRobinRouter(config.getNumberOfChildren())));

		// login with Xpay at start up
		router.tell(new Login(), getSelf());

		// Search for all the pending transaction and send to actor once when
		// master starts
		List<AtTransaction> transactions = mapper.searchTransactionByStatusAndConnType("PENDING",
				"XPAY");
		for (AtTransaction txn : transactions) {
			IntegrationPayload payload = new IntegrationPayload();
			payload.put("telco_id", txn.getTelco_id());
			payload.put("transaction_id", txn.getAt_txn_id().toString());
			payload.put("msisdn", txn.getMsisdn());
			payload.put("amount", txn.getAmount() + "");
			try {
				router.tell(Utils.fromIntegrationPayload(config.getPostpaidSuffix(), payload),
						getSelf());
			}
			catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}

		getContext()
				.system()
				.scheduler()
				.schedule(Duration.create(15, TimeUnit.SECONDS), HandshakeInterval, router,
						new Handshake());
		getContext()
				.system()
				.scheduler()
				.schedule(Duration.create(30, TimeUnit.SECONDS), checkBalanceInteval, router,
						new BalanceRequest());

		getContext()
				.system()
				.scheduler()
				.schedule(Duration.create(30, TimeUnit.SECONDS), checkPendingInteval, getSelf(),
						new CheckUnknownRequest());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		LOGGER.info(getSelf() + " : Receives from " + getSender() + " message " + message);
		try {
			// handle children
			/**
			 * Get TopupRequest, send TopupRequest to slave actors After 5
			 * consecutive requests, check balance of account
			 */
			if (message instanceof TopupRequest) {
				router.tell(message, getSelf());
				++numberOfRequestMessages;
				if (numberOfRequestMessages >= config.getNumberOfConsicutiveRequests()) {

					router.tell(new BalanceRequest(), getSelf());
					numberOfRequestMessages = 0;
				}
			}
			/**
			 * Receive the CheckUnknownRequest. Get all the unknown XPAY
			 * transactions and send it to slaves for confirmation.
			 */
			else if (message instanceof CheckUnknownRequest) {
				List<AtTransaction> pendingTransactions = mapper
						.searchTransactionByStatusAndConnType("UNKNOWN", "XPAY");
				Date now = new Date();
				for (AtTransaction transaction : pendingTransactions) {
					long diff = now.getTime() - transaction.getUpdated_date().getTime();
					if (diff < 24 * MILITOHOURCONVERSION) {
						router.tell(transaction, getSelf());
					}
					else {
						transaction.setTxn_status("SUCCESS");
						getSelf().tell(transaction);
					}
				}
			}

			/**
			 * Handle the Query Balance result. Update new balance in database
			 */
			else if (message instanceof BalanceResponse) {
				BalanceResponse response = (BalanceResponse) message;
				mapper.UpdateBalance(response);
			}
			/**
			 * Update the transactions to database
			 */
			else if (message instanceof AtTransaction) {
				AtTransaction transaction = (AtTransaction) message;
				transaction.setUpdated_by("XPAY_SYS");
				transaction.setUpdated_date(new Date());
				if (transaction.getError_code() == null) {
					transaction.setError_code("");
				}
				mapper.UpdateTxn(transaction);
			}
			/**
			 * Kill self and all the children
			 */
			else if (message instanceof Stop) {
				router.tell(PoisonPill.getInstance());
				getContext().stop(getSelf());
			}
		}
		catch (Exception e) {
			LOGGER.error(getSelf(), e);
		}
	}

	@Override
	public void postStop() {
		LOGGER.warn(getSelf() + " stopped");
	}
}
