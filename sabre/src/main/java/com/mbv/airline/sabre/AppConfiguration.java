package com.mbv.airline.sabre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import akka.actor.ActorSystem;
import akka.actor.Props;

import com.mbv.airline.sabre.actors.CommandHandler;
import com.mbv.airline.sabre.actors.TerminalPool;


@Configuration
@ImportResource("classpath:appConfig.xml")
public class AppConfiguration {

	@Autowired 
	private ApplicationContext appContext;
	
	@Bean(destroyMethod = "shutdown")
	public ActorSystem actorSystem() {
		ActorSystem system = ActorSystem.create("SabreSystem");
		SpringExt.Extention.get(system).initialize(appContext);
		
		system.actorOf(Props.create(CommandHandler.class), "CommandHandler");
		system.actorOf(Props.create(TerminalPool.class), "SabreTerminals");
		
		return system;
	}
}
