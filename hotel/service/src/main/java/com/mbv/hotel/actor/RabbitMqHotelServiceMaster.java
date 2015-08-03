package com.mbv.hotel.actor;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.ActiveObjectCounter;
import org.springframework.amqp.rabbit.listener.BlockingQueueConsumer;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;

import com.mbv.hotel.cmd.WorkerCmd;

public class RabbitMqHotelServiceMaster extends AbstractHotelServiceMaster{
	
	
	private static Logger logger = Logger.getLogger(RabbitMqHotelServiceMaster.class);

	private RabbitTemplate template;
	private HashMap<String, ActorRef> listeners;

	public RabbitMqHotelServiceMaster(List<AbstractHotelWorkerFactory> workerFactories, RabbitTemplate template){
		super(workerFactories);
		this.template = template;
		this.listeners = new HashMap<String, ActorRef>();
	}

	public void preStart() {
        super.preStart();
        logger.info("RabbitMQ Hotel Service Master pre started");
    }

    @Override
    protected void dispatchWorker(WorkerCmd message, ActorRef worker) {
        final String vendor = message.getVendor();
        ActorRef listener = listeners.get(vendor);
        if (listener == null) {
            listener = getContext().actorOf(new Props(new UntypedActorFactory() {
                public Actor create() throws Exception {
                    return new RabbitMqListenerActor(vendor, template);
                }
            }));
            listeners.put(vendor, listener);
        }
        listener.tell(getSender(), null);
    }

    private static class RabbitMqListenerActor extends UntypedActor {
    	
        private RabbitTemplate template;
        private BlockingQueueConsumer consumer;

        public RabbitMqListenerActor(String queue, RabbitTemplate template) {
            this.template = template;
            consumer = new BlockingQueueConsumer(template.getConnectionFactory(), new DefaultMessagePropertiesConverter(), new ActiveObjectCounter<BlockingQueueConsumer>(), AcknowledgeMode.AUTO, false, 1, queue);
            consumer.start();
        }

        @Override
        public void onReceive(Object object) throws Exception {
            if (object instanceof ActorRef) {
                ActorRef worker = (ActorRef) object;
                Message message = consumer.nextMessage(1000L);
                if (message == null) {
                    getSelf().tell(worker, null);
                } else {
                    consumer.commitIfNecessary(false);
                    logger.info("dispatchWorker: " + worker.path());
                    worker.tell(template.getMessageConverter().fromMessage(message), null);
                }
            }
        }
        public void postStop() {
            consumer.stop();
        }
    }

}
