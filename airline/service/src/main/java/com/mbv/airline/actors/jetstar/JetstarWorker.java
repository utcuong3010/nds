package com.mbv.airline.actors.jetstar;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mbv.airline.actors.AbstractWorker;
import com.mbv.airline.actors.WorkerAvailableMessage;
import com.mbv.airline.common.BookResult;
import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.PayResult;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;

public class JetstarWorker extends AbstractWorker {

	final static Logger logger = Logger.getLogger(JetstarWorker.class);

	private JetstarStub stub;
	public JetstarWorker(JetstarConfig jetStarConfig) {
		stub = new JetstarStub(jetStarConfig);
		setAvailableMessage(new WorkerAvailableMessage(jetStarConfig.getWorkerName()));
	}

	@Override
	protected AirFarePriceInfos doSearch(SearchItineraryCmd searchItineraryCmd) throws Exception {
		AirFarePriceInfos result = null;
		for (int retry = 0; retry <= 1 && result == null; retry++) {
			try {

				result = stub.doSearch(searchItineraryCmd);

			} catch (Exception ex) {				
				throw ex;
			}
		}
		return result;
	}

	@Override
	protected void doBook(AirItinerary itinerary) throws Exception {
		AirTicketingInfo info = itinerary.getTicketingInfo();
		BookResult result = null;
		for (int i = 0; i <= 1 && result == null; i++) {
			try {
				result = stub.doBook(itinerary);
				if (result != null && result.getExpiredDate() != null) {
					info.setAmount(result.getAmount());
					info.setExpiredDate(result.getExpiredDate());
					info.setStatus(BookingStatus.BOOK_SUCCESS);
					info.setReservationCode(result.getReservationCode());
				} else {
					info.setStatus(BookingStatus.BOOK_ERROR);					
				}
			} catch (Exception ex) {			
				throw ex;
			}
		}
	}

	@Override
	protected void doPay(AirItinerary itinerary) throws Exception {
		AirTicketingInfo info = itinerary.getTicketingInfo();
		try {
			PayResult result = stub.doPay(itinerary);
			if (result != null && result.getReceiptNumber() != null){
				info.setStatus(BookingStatus.BUY_SUCCESS);
				List<String> ticketNumbers = new ArrayList<String>();
				ticketNumbers.add(result.getReceiptNumber());
				info.setTicketNumbers(ticketNumbers);
			} else {
				info.setStatus(BookingStatus.BUY_ERROR);				
			}
		} catch (Exception ex) {			
			throw ex;
		}
	}

	@Override
	protected void doRetrieveBooking(AirItinerary itinerary) throws Exception{
		try {			

			stub.doRetrieveBooking(itinerary);

		} catch (Exception ex) {			
			throw ex;
		}	
	}

	@Override
	protected boolean doVerifyPayment(VerifyPaymentCmd verifyPaymentCmd) throws Exception {
		try {
			boolean result = false;
			for (int i = 0; i <= 1 && result == false; i++) {
				result =  stub.doVerifyPayment(verifyPaymentCmd);
			}
			return result;
		} catch (Exception ex) {
			throw ex;
		}
	}
}
