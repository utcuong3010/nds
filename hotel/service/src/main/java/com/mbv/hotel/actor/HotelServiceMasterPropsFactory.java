package com.mbv.hotel.actor;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;

public class HotelServiceMasterPropsFactory {
	
	public static Props create(final List<AbstractHotelWorkerFactory> factories, final RabbitTemplate rabbitTemplate) {
        
		Props props = new Props(new UntypedActorFactory() {
			public Actor create() throws Exception {
                return new RabbitMqHotelServiceMaster(factories, rabbitTemplate);
            }
        });
        return props;
    }
}
