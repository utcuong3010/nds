package com.mbv.airline.sabre;

import org.springframework.context.ApplicationContext;
import akka.actor.AbstractExtensionId;
import akka.actor.Actor;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.ExtensionIdProvider;
import akka.actor.IndirectActorProducer;
import akka.actor.Props;

public class SpringExt extends AbstractExtensionId<SpringExt.SpringExtImpl>
		implements ExtensionIdProvider {

	public final static SpringExt Extention = new SpringExt();

	public SpringExt lookup() {
		return SpringExt.Extention;
	}

	public SpringExtImpl createExtension(ExtendedActorSystem system) {
		return new SpringExtImpl();
	};

	public class SpringExtImpl implements Extension {
		private volatile ApplicationContext applicationContext;

		public void initialize(ApplicationContext applicationContext) {
			this.applicationContext = applicationContext;
		}
		
		public ApplicationContext getAppCtx(){
			return this.applicationContext;
		}

		public Props props(String actorBeanName) {
			return Props.create(SpringActorProducer.class, applicationContext,
					actorBeanName);
		}
	}

	public class SpringActorProducer implements IndirectActorProducer {

		final ApplicationContext applicationContext;
		final String actorBeanName;

		public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName) {
			this.applicationContext = applicationContext;
			this.actorBeanName = actorBeanName;
		}

		public Class<? extends Actor> actorClass() {
			return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
		}

		public Actor produce() {
			return (Actor) applicationContext.getBean(actorBeanName);
		}

	}
}
