package com.mbv.airtime2.vngmobile.actor;

import java.util.Date;

import org.apache.log4j.Logger;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.mbv.airtime2.vngmobile.Config;
import com.mbv.airtime2.vngmobile.domain.TopupRequest;
import com.mbv.airtime2.vngmobile.domain.TopupResponse;
import com.mbv.airtime2.vngmobile.stub.Channel;
import com.mbv.airtime2.vngmobile.stub.ISO8583Message;

public class Worker extends UntypedActor{
	private static Logger LOGGER = Logger.getLogger(Worker.class);
	//private final static Duration HearBeatInterval = Duration.create(300, "seconds");
	
	private Channel channel;
	private String serviceCode;
	
	public Worker(Config config){
		channel = new Channel(config);
		this.serviceCode = config.getEvourcherServiceCode();
	}
	public void preStart() {		
		LOGGER.info(getSelf() + " : Starting");	
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
		TopupResponse response;
		try{
			if (message instanceof TopupRequest) {
				response = Buy((TopupRequest) message);				
			} else {
				throw new Exception("Unhandled message type " + message.getClass().toString());
			}
		}catch(Exception ex){
			LOGGER.error(getSelf(), ex);
			response = new TopupResponse(null, TopupResponse.STATUS_SUCCESS, TopupResponse.ERROR_NONE, null);
		}	
		SendMessage(getContext().parent(), response);
	}

	private TopupResponse Buy(TopupRequest request){
		if(!channel.isConnected()) channel.Connect();
		ISO8583Message msg = new ISO8583Message();
		msg.setType(ISO8583Message.TYPE_FINANCIAL_REQUEST);
		msg.setProcessingCode("111111");
		msg.setTransmissionDate(new Date()); 	
		msg.setRefEncryptedMpin(channel.getSessionToket());
		msg.setRefTransactionId(request.getAtTxnId());
		msg.setRefInfo(request.getMsisdn() + "|" + request.getAmount());
		msg.setDate(new Date());
		msg.setServiceIndicator(serviceCode);	
		
		TopupResponse result;
		boolean hasError = true;
		try{
			msg = channel.Send(msg);
			if(msg == null){
				result = new TopupResponse(request.getAtTxnId(),
						TopupResponse.STATUS_SUCCESS,TopupResponse.ERROR_DELIVERY, null);
			}else{
				if(msg.isSuccess()){
					Long balance;
					try{
						String[] tmp = Iterables.toArray(Splitter.on('|').split(msg.getRefInfo()), String.class);
						balance = Long.parseLong(tmp[4]);
					}catch(Exception ex){
						balance = null;
					}
					result = new TopupResponse(request.getAtTxnId(), 
							TopupResponse.STATUS_SUCCESS, TopupResponse.ERROR_NONE, balance);
					hasError = false;
				}else{
					result = new TopupResponse(request.getAtTxnId(), 
							TopupResponse.STATUS_ERROR, msg.getErrorCode(), null);
				}
			}
		}catch(Exception ex){
			LOGGER.error(getSelf(),ex);
			result = new TopupResponse(request.getAtTxnId(), 
					TopupResponse.STATUS_ERROR, TopupResponse.ERROR_CONNECTION, null);
		}		
		if(hasError) channel.Disconnect();
		return result;
	}
}
