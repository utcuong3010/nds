package com.mbv.airline.actors.vietjet;

import java.util.ArrayList;
import java.util.List;

import com.mbv.airline.actors.AbstractWorker;
import com.mbv.airline.common.BookResult;
import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.PayResult;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;


public class VietjetWorker extends AbstractWorker{

	private VietjetStub stub;	   

	/***
	 * retry for search
	 */
	private final int RETRY;



	public VietjetWorker(final VietJetConfig config) {

		this.stub = new VietjetStub(config);

		//set retry time
		RETRY = config.getActionRetry();
	}


	@Override
	protected AirFarePriceInfos doSearch(SearchItineraryCmd searchItineraryCmd) throws Exception{

		AirFarePriceInfos result = new AirFarePriceInfos();
	
		try {

			result = stub.doSearch(searchItineraryCmd);

		}catch (Exception ex) {
			throw ex;
		}
		return result;
	}

	@Override
	protected void doBook(AirItinerary itinerary) throws Exception {
		AirTicketingInfo info = itinerary.getTicketingInfo();
		BookResult result = null;
		
		try {
			result = stub.doBook(itinerary);
			if (result != null && result.getReservationCode() != null && result.getExpiredDate() != null) {
				info.setAmount(result.getAmount());
				info.setReservationCode(result.getReservationCode());
				info.setExpiredDate(result.getExpiredDate());
				info.setStatus(BookingStatus.BOOK_SUCCESS);
			}
		} catch (Exception ex) {
			throw ex;
		}
	}

	@Override
	protected void doPay(AirItinerary itinerary)throws Exception  {
		AirTicketingInfo info = itinerary.getTicketingInfo();
		PayResult result = null;		
		try {		
			result = stub.doPay(itinerary);
			if (result != null && result.getReceiptNumber() != null) {
				info.setStatus(BookingStatus.BUY_SUCCESS);
				List<String> ticketNumbers = new ArrayList<String>();
				ticketNumbers.add(result.getReceiptNumber());
				info.setTicketNumbers(ticketNumbers);
			} else {
				//throw exception
				throw new Exception("Pay with error");
			}
		} catch (Exception ex) {
			throw ex;
		}		
	}

	@Override
	protected void doRetrieveBooking(AirItinerary itinerary) throws Exception {		
		try {
			
			stub.doRetrieveBooking(itinerary);
			
		} catch (Exception ex) {
			throw ex;			
		}
	}

	@Override
	protected boolean doVerifyPayment(VerifyPaymentCmd command) throws Exception{
		boolean result = false;
		for (int i = 0; i <= 1 && result == false; i++) {
			try {
				result = stub.doVerifyPayment(command.getReservationCode());
			} catch (Exception ex) {
				throw ex;
			}
		}
		return result;
	}
	
	@Override
	protected void refreshActor() {
		
		stub.init();//reinit
	}

}
