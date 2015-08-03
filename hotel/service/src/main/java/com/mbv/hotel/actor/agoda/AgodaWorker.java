package com.mbv.hotel.actor.agoda;

import org.apache.log4j.Logger;

import com.mbv.hotel.actor.AbstractHotelWorker;
import com.mbv.hotel.cmd.PayCmd;
import com.mbv.hotel.cmd.WorkerCmd;
import com.mbv.hotel.common.AgodaResult;
import com.mbv.hotel.info.CardInfo;

public class AgodaWorker extends AbstractHotelWorker{
	
	
	final static Logger logger = Logger.getLogger(AgodaWorker.class);
	
	
	private AgodaStub stub;

	
	public AgodaWorker(CardInfo cardInfo){
		this.stub = new AgodaStub(cardInfo);
		setAvailableMessage(new WorkerCmd("Agoda"));
	}
	
	@Override
	protected void doPay(PayCmd payCmd) {
		//go to pay and update status of payment
		AgodaResult result = null;
		try {
			result = stub.doPay(payCmd);
//				ticketingInfo.setReservationCode(result.getReservationCode());
//				ticketingInfo.setAmount(result.getAmount());
//				ticketingInfo.setStatus(TicketStatus.PAY_SUCCESS);
//				ticketingInfo.setDescription("PAY_SUCCESS");
		}catch (Exception ex) {
//				ticketingInfo.setStatus(TicketStatus.PAY_ERROR);
//				ticketingInfo.setDescription(ex.getMessage());
			logger.error("PAYMENT_PROCESSING WITH ERROR:" + ex.getMessage()); 
		}
		
	}
	

}
