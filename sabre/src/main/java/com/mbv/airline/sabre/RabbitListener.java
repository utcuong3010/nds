package com.mbv.airline.sabre;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.support.converter.JsonMessageConverter;

import akka.actor.ActorSystem;


public class RabbitListener implements MessageListener {

	private ActorSystem actorSystem;

	private JsonMessageConverter jsonConverter;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void setJsonConverter(JsonMessageConverter jsonConverter) {
		this.jsonConverter = jsonConverter;
	}

	public void setActorSystem(ActorSystem actorSystem) {
		this.actorSystem = actorSystem;
	}

	@Override
	public void onMessage(Message message) {
		Object command ;
		try{
			command = jsonConverter.fromMessage(message);
			actorSystem.actorSelection("/user/CommandHandler").tell(
					command, null);
		}catch(Exception ex){
			logger.error("Receive unexpected command: "+ex.getCause());
		}

	}

}
