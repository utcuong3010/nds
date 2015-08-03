package com.mbv.hotel.actor.agoda;

import com.mbv.hotel.actor.AbstractHotelWorker;
import com.mbv.hotel.actor.AbstractHotelWorkerFactory;
import com.mbv.hotel.info.CardInfo;

public class AgodaWorkerFactory extends AbstractHotelWorkerFactory {
	
	private static final long serialVersionUID = 151545103964581058L;
	

	private CardInfo cardInfo;
	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}
	@Override
	protected AbstractHotelWorker doCreate(){
		return new AgodaWorker(cardInfo);
	}
}
