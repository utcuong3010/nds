package com.mbv.airtime2.epay.actors;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mbv.airtime2.epay.EpayConfig;
import com.mbv.airtime2.epay.Stub.QueryBalanceResult;
import com.mbv.airtime2.epay.domain.AtTransaction;
import com.mbv.airtime2.epay.domain.Mapper;
import com.mbv.airtime2.epay.message.CheckPendingMessage;
import com.mbv.airtime2.epay.message.QueryBalanceMessage;
import com.mbv.airtime2.epay.message.RequestMessage;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.RoundRobinRouter;
import akka.util.Duration;

public class EpayMaster extends UntypedActor {
	private int numberOfRequestMessages = 0;

	private EpayConfig config;
	private Mapper mapper;
	ActorRef router;
	private final int MILITOHOURCONVERSION = 3600 * 1000; 
	private static Logger LOGGER = Logger.getLogger(EpayMaster.class);

	public EpayMaster(EpayConfig config, Mapper mapper) {
		this.config = config;
		this.mapper = mapper;
	}

	@Override
	public void preStart() {
		/*
		 * Create a router with numberOfChildren children , use RoundRobin
		*/
		router = getContext().actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = 1L;

			public Actor create() throws Exception {
				// TODO Auto-generated method stub
				return new EpayActor(config);
			}
		}).withRouter(new RoundRobinRouter(config.getNumberOfChildren())));
		
		
		//Update balance at start up
		router.tell(new QueryBalanceMessage(),getSelf());
		
		//Search for all the pending transaction and send to actor once when master starts
		List<AtTransaction> transactions = mapper
				.searchTransactionByStatusAndConnType("PENDING",
						"EPAY");
		for (AtTransaction txn : transactions){
			router.tell(new RequestMessage(txn.getAt_txn_id().toString(), txn.getTelco_id(), txn.getMsisdn(), txn.getAmount()),getSelf());
		}

		getContext().system().scheduler().schedule(Duration.Zero(), Duration.create(config.getPendingTransactionCheckingInterval(), TimeUnit.MILLISECONDS), getSelf(), new CheckPendingMessage());
		getContext().system().scheduler().schedule(Duration.Zero(), Duration.create(config.getPendingTransactionCheckingInterval(), TimeUnit.MILLISECONDS), router, new QueryBalanceMessage());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		try {
			// handle children
			/**
			 * Get RequestMessage, send RequestMessage to slave actors After 5
			 * consecutive requests, check CDV balance of account
			 */
			if (message instanceof RequestMessage) {
				router.tell(message, getSelf());
				++numberOfRequestMessages;
				if (numberOfRequestMessages >= config
						.getNumberOfConsicutiveRequests()) {

					router.tell(new QueryBalanceMessage(), getSelf());
					numberOfRequestMessages = 0;
				}
			}
			/**
			 * Receive the CheckingPending Request. Get all the pending EPAY
			 * transactions and send it to slaves for confirmation.
			 */
			else if (message instanceof CheckPendingMessage) {
				List<AtTransaction> pendingTransactions = mapper
						.searchTransactionByStatusAndConnType("UNKNOWN",
								"EPAY");
				Date now = new Date();
				for (AtTransaction transaction : pendingTransactions) {
					long diff = now.getTime() - transaction.getUpdated_date().getTime();
					
					if (diff < MILITOHOURCONVERSION){
						router.tell(transaction, getSelf());
					}else{
						transaction.setTxn_status("SUCCESS");
						getSelf().tell(transaction);
					}
				}
			}

			/**
			 * Handle the Query Balance result. Update new balance in database
			 */
			else if (message instanceof QueryBalanceResult) {
				QueryBalanceResult result = (QueryBalanceResult) message;
				mapper.UpdateBalance(result);
			}
			/**
			 * Update the transactions to database
			 */
			else if (message instanceof AtTransaction) {
				AtTransaction transaction = (AtTransaction) message;
				transaction.setUpdated_by("EPAY_SYS");
				transaction.setUpdated_date(new Date());
				if (transaction.getError_code() == null){
					transaction.setError_code("");
				}
				mapper.UpdateTxn(transaction);
			}
			/**
			 * Kill self and all the children
			 */			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
