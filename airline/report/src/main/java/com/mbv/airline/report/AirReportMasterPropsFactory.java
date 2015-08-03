package com.mbv.airline.report;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import akka.actor.Actor;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;

import com.mbv.airline.common.support.AirItineraryReport;

public class AirReportMasterPropsFactory {

	@SuppressWarnings("serial")
	public static Props create(final RabbitTemplate rabbitTemplate,
			final String queue, final AirItineraryReport airItineraryReport) {
		Props props = new Props(new UntypedActorFactory() {
			public Actor create() throws Exception {
				return new ReportActor(rabbitTemplate, queue,
						airItineraryReport);
			}
		});
		return props;
	}
}
