package com.mbv.airtime2.epay;

import org.apache.log4j.Logger;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;

import com.mbv.airtime2.epay.Stub.Interfaces;
import com.mbv.airtime2.epay.Stub.InterfacesServiceLocator;
import com.mbv.airtime2.epay.Stub.QueryBalanceResult;
import com.mbv.airtime2.epay.actors.EpayMaster;
import com.mbv.airtime2.epay.Utils;
import com.mbv.airtime2.epay.domain.Mapper;
//import com.mbv.airtime2.integration.IntegrationPayload;
import com.mbv.airtime2.integration.IntegrationPayload;

public class EpayService {

	private static Logger LOGGER = Logger.getLogger(EpayService.class);

	private ActorSystem actorSystem;
	private ActorRef master;
	private EpayConfig config;
	private boolean isTerminated = true;
	private Mapper mapper;

	public Mapper getMapper() {
		return mapper;
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public void setConfig(EpayConfig config) {
		this.config = config;
	}

	public void start() {
		if (!isTerminated)
			return;

		LOGGER.info("EpayService : Starting");
		isTerminated = false;
		actorSystem = ActorSystem.create("EpayService");

		master = actorSystem.actorOf(new Props(new UntypedActorFactory() {
			public Actor create() throws Exception {
				return new EpayMaster(config, mapper);
			}
		}), "masterActor");

	}

	public void stop() {
		if (isTerminated) return;
		LOGGER.info("Epay service : Stopping");
		isTerminated = true;
		master.tell(PoisonPill.getInstance());
		while(!master.isTerminated()){			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}	
		}		
		actorSystem.shutdown();
	}

	public void process(IntegrationPayload payload) throws Exception {
		if (isTerminated)
			throw new Exception("Epay Service is terminated");

		LOGGER.info("EpayService : Receives IntegrationPayload" + payload);
		try {
			Object msg = Utils.fromIntegrationPayloadWithConfig(payload);
			master.tell(msg);
		} catch (Exception ex) {

		}
	}

	public static void main(String[] args) throws Exception {
		
		String url = "http://naptien.thanhtoan247.vn:8082/CDV_Partner_Services_V1.0/services/Interfaces?wsdl";
		String partnerName = "cdvmobivi";
		String keyPath = "mobivi_epay_private_key.pem";
		
		if(args.length == 3){
			partnerName = args[0];
			url = args[1];
			keyPath = args[2];
		}
		Encrypt signer = new Encrypt(keyPath);
		InterfacesServiceLocator locator = new InterfacesServiceLocator();
		locator.setInterfacesEndpointAddress(url);
		Interfaces service = locator.getInterfaces();
		QueryBalanceResult result = service.queryBalance(partnerName, signer.sign(partnerName));
		System.out.println(result.getErrorCode());
		System.out.println(result.getMessage());
		System.out.println(result.getBalance_avaiable());
	}
}
