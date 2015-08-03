package com.mbv.airtime2.mobifone.actors;

import java.math.BigDecimal;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.util.Duration;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Objects;
import com.mbv.airtime2.mobifone.MobifoneConfig;
import com.mbv.airtime2.mobifone.Stub.BalanceRequestType;
import com.mbv.airtime2.mobifone.Stub.BalanceResponseType;
import com.mbv.airtime2.mobifone.Stub.BuyRequestType;
import com.mbv.airtime2.mobifone.Stub.CreatesessionResponseType;
import com.mbv.airtime2.mobifone.Stub.LoginRequestType;
import com.mbv.airtime2.mobifone.Stub.StandardBizResponse;
import com.mbv.airtime2.mobifone.Stub.UMarketSCImpl;
import com.mbv.airtime2.mobifone.Stub.UMarketSCImplServiceLocator;
import com.mbv.airtime2.mobifone.domain.BalanceRequest;
import com.mbv.airtime2.mobifone.domain.BalanceResponse;
import com.mbv.airtime2.mobifone.domain.HeartBeat;
import com.mbv.airtime2.mobifone.domain.TopupRequest;
import com.mbv.airtime2.mobifone.domain.TopupResponse;

public class MobifoneActor extends UntypedActor {

	private static Logger LOGGER = Logger.getLogger(MobifoneActor.class);
	
	private final static Duration HearBeatInterval = Duration.create(300, "seconds");
	
	

	private UMarketSCImpl umarketService;
	private MobifoneConfig config;
	private String sessionId = "";
	private ActorRef forwarder;
	
	public MobifoneActor(MobifoneConfig config, ActorRef forwarder) {
		this.config = config;
		this.forwarder = forwarder;
	}

	public void preStart() {		
		LOGGER.info(getSelf() + " : Starting");				
		SendMessage(getSelf(),new HeartBeat(HeartBeat.Status.ERROR));
	}

	public void postStop() {
		LOGGER.info(getSelf() + " : Stoping");		
	}

	private void SendMessage(ActorRef receiver, Object msg){
		LOGGER.info(getSelf() + " : Dispatches to " + receiver + " message " + msg);
		receiver.tell(msg, getSelf());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {		
		LOGGER.info(getSelf() + " : Receives from " + getSender() + " message " + message);
		try{
			if (message instanceof TopupRequest) {
				TopupResponse topupResponse = Topup((TopupRequest) message);
				SendMessage(forwarder, topupResponse);
			} else if (message instanceof BalanceRequest) {
				BalanceResponse balanceResponse = UpdateBalance((BalanceRequest) message);
				if (balanceResponse == null) {
					throw new Exception("Balance update error");
				} else {
					SendMessage(forwarder, balanceResponse);
				}								
			} else if(message instanceof HeartBeat){
				if("".equals(sessionId)) Connect();				
			} else
			{
				throw new Exception("Unhandled message type " + message.getClass().toString());
			}
			
		}catch(Exception ex){
			LOGGER.error(getSelf(), ex);
		}
		
		HeartBeat heartBeat;
		if("".equals(sessionId)){
			heartBeat = new HeartBeat(HeartBeat.Status.ERROR);			
			getContext().system().scheduler().scheduleOnce(HearBeatInterval, getSelf(), heartBeat);			
		}else{
			heartBeat = new HeartBeat(HeartBeat.Status.READY);			
		}
		SendMessage(getContext().parent(), heartBeat);		
	}
	
	private void Connect() {
		LOGGER.info(getSelf() + " : Connecting");
		try {
			if(umarketService == null){
				UMarketSCImplServiceLocator locator = new UMarketSCImplServiceLocator();
				locator.setUMarketSCEndpointAddress(config.getUrl());
				umarketService = locator.getUMarketSC();
			}
			CreatesessionResponseType sessRes = umarketService.createsession();
			LOGGER.info(getSelf() + " : Create Session Response " + getLogString(sessRes));
			if (config.RESULT_OK != sessRes.getResult())
				throw new Exception(sessRes.getResult_namespace().toUpperCase() + sessRes.getResult());
			sessionId = sessRes.getSessionid();
								 
			String pin = (config.getLogin() + config.getPassword()).toLowerCase();
			pin = DigestUtils.sha1Hex(pin.getBytes()).toLowerCase();
			pin = sessionId + pin;
			pin = DigestUtils.sha1Hex(pin.getBytes()).toUpperCase();

			LoginRequestType lreq = new LoginRequestType();
			lreq.setSessionid(sessionId);
			lreq.setInitiator(config.getLogin());
			lreq.setPin(pin);
			LOGGER.info(getSelf() + " : Login Request " + getLogString(lreq));
			StandardBizResponse bizRes = umarketService.login(lreq);
			LOGGER.info(getSelf() + " : Login Response " + getLogString(bizRes));
						    
			if (config.RESULT_OK != bizRes.getResult())
				throw new Exception(bizRes.getResult_namespace().toUpperCase() + bizRes.getResult());
		} catch (Exception ex) {
			sessionId = "";
			LOGGER.error(getSelf(), ex);
		}
	}

	private TopupResponse Topup(TopupRequest request) {

		// isPrepaidAmount 	-> BuyPrepaid 	-> Exception 	-> return
		// 									-> Ok 			-> return
		// 									-> Error 		-> BuyPostpaid
		// 					-> BuyPostpaid 	-> Exception 	-> return
		// 									-> Ok 			-> return
		// 									-> Error 		-> return
		BuyRequestType buyRequest = new BuyRequestType();
		buyRequest.setRecipient(request.getMsisdn());
		buyRequest.setAmount(BigDecimal.valueOf(request.getAmount()));
		buyRequest.setReference1(request.getAtTxnId());
		buyRequest.setType(config.BUY_TYPE);
		if (config.isPrepaidAmount(request.getAmount())) {
			buyRequest.setTarget(config.TARGET_PREPAID);
		} else {
			buyRequest.setTarget(config.TARGET_POSTPAID);
		}

		TopupResponse response = null;
		StandardBizResponse buyResponse;
		do {
			buyResponse = Buy(buyRequest);
			if (buyResponse == null) {
				if("".equals(sessionId)){
					response = new TopupResponse(
							request.getAtTxnId(),
							TopupResponse.STATUS_ERROR,
							TopupResponse.ERROR_CONNECTION, null);
				}else{
					response = new TopupResponse(
							request.getAtTxnId(),
							TopupResponse.STATUS_SUCCESS,
							TopupResponse.ERROR_DELIVERY, null);
				}
			} else {
				if (config.RESULT_OK == buyResponse.getResult()) {
					response = new TopupResponse(
							request.getAtTxnId(),
							TopupResponse.STATUS_SUCCESS,
							TopupResponse.ERROR_NONE, buyResponse.getTransid().toString());
				} else {
					if (config.TARGET_PREPAID == buyRequest.getTarget()) {
						response = null;
						buyRequest.setTarget(config.TARGET_POSTPAID);
					} else {
						// error
						response = new TopupResponse(
								request.getAtTxnId(),
								TopupResponse.STATUS_ERROR,
								(buyResponse.getResult_namespace() + buyResponse.getResult()).toUpperCase(), 
								buyResponse.getTransid().toString());
					}
				}
			}
		} while (response == null);
		return response;
	}

	private StandardBizResponse Buy(BuyRequestType request) {
		StandardBizResponse bizRes = null;
		boolean isReconnected = false;
		do {
			if ("".equals(sessionId)) {
				isReconnected = true;
				Connect();
			} 
			try {
				request.setSessionid(sessionId);
					
				LOGGER.info(getSelf() + " : Buy Request " + getLogString(request));
				bizRes = umarketService.buy(request);
				LOGGER.info(getSelf() + " : Buy Response " + getLogString(bizRes));
										
				if (config.isSessionError(bizRes.getResult())) {
					sessionId = "";
					bizRes = null;
				}
			} catch (Exception ex) {
				LOGGER.error(getSelf(), ex);
				return null;
			}			
		} while (bizRes == null && !isReconnected);
		return bizRes;
	}

	private BalanceResponse UpdateBalance(BalanceRequest request) {
		if ("".equals(sessionId)) {
			Connect();
		}

		BalanceRequestType balanceRequest = new BalanceRequestType();
		balanceRequest.setSessionid(sessionId);
		balanceRequest.setType(config.BALANCE_TYPE);

		BalanceResponse response = null;
		try {
			LOGGER.info(getSelf() + " : Balance Request " + getLogString(balanceRequest));
			BalanceResponseType balanceResponse = umarketService.balance(balanceRequest);
			LOGGER.info(getSelf() + " : Balance Response " + getLogString(balanceResponse));
			if (config.RESULT_OK == balanceResponse.getResult()) {
				response = new BalanceResponse(
						BalanceResponse.STATUS_SUCCESS, 
						null, 
						balanceResponse.getTransid().toString(), 
						(balanceResponse.getAvail() == null) ? 0 : balanceResponse.getAvail().longValue(),
						(balanceResponse.getCurrent() == null) ? 0 : balanceResponse.getCurrent().longValue(),
						(balanceResponse.getPending() == null) ? 0 : balanceResponse.getPending().longValue());				
			} else {
				throw new Exception((balanceResponse.getResult_namespace() + balanceResponse.getResult()).toUpperCase());
			}
		} catch (Exception ex) {
			LOGGER.error(getSelf(), ex);
			response = null;
		}
		return response;
	}
	
	private String getLogString(LoginRequestType object){
		return Objects.toStringHelper(object)
				.add("initiator", object.getInitiator())
				.add("pin", object.getPin())
				.add("sessionid", object.getSessionid()).toString();
	}
	
	private String getLogString(BalanceRequestType object){
		return Objects.toStringHelper(object)
				.add("type", object.getType())
				.add("sessionid", object.getSessionid()).toString();		
	}
	
	private String getLogString(BuyRequestType object){
		return Objects.toStringHelper(object)
				.add("type", object.getType())
				.add("target", object.getTarget())
				.add("recipient", object.getRecipient())
				.add("amount", object.getAmount())
				.add("reference1", object.getReference1())
				.add("sessionid", object.getSessionid()).toString();		
	}
	
	private String getLogString(StandardBizResponse object){
		return Objects.toStringHelper(object)
				.add("result", object.getResult())
				.add("result_namespace", object.getResult_namespace())
				.add("transid", object.getTransid()).toString();	
	}
	
	private String getLogString(CreatesessionResponseType object){		
		return Objects.toStringHelper(object)				
				.add("result", object.getResult())
				.add("result_namespace", object.getResult_namespace())
				.add("sessionid", object.getSessionid()).toString();
	}
	
	private String getLogString(BalanceResponseType object){
		return Objects.toStringHelper(object)
				.add("result", object.getResult())
				.add("result_namespace", object.getResult_namespace())
				.add("transid", object.getTransid())
				.add("avail", object.getAvail())
				.add("avail_1", object.getAvail_1())
				.add("avail_2", object.getAvail_2())
				.add("avail_3", object.getAvail_3())
				.add("current", object.getCurrent())
				.add("current_1", object.getCurrent_1())
				.add("current_2", object.getCurrent_2())
				.add("current_3", object.getCurrent_3())
				.add("pending", object.getPending())
				.add("pending_1", object.getPending_1())
				.add("pending_2", object.getPending_2())
				.add("pending_3", object.getPending_3()).toString();
	}
}