package com.mbv.airline.actors;

import org.apache.log4j.Logger;

import akka.actor.UntypedActor;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.cmd.CreateBookingCmd;
import com.mbv.airline.common.cmd.PayBookingCmd;
import com.mbv.airline.common.cmd.RetrieveBookingCmd;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.common.support.AirFarePriceCache;
import com.mbv.airline.common.support.AirItineraryReport;
import com.mbv.airline.common.support.AirItineraryRepository;



public abstract class AbstractWorker extends UntypedActor {

	static Logger logger = Logger.getLogger(AbstractWorker.class);

	
	private AirItineraryRepository itineraryRepository;
	public void setItineraryRepository(
			AirItineraryRepository itineraryRepository) {
		this.itineraryRepository = itineraryRepository;
	}
	
	private AirFarePriceCache farePriceCache;
	public void setFarePriceCache(AirFarePriceCache farePriceCache) {
		this.farePriceCache = farePriceCache;
	}
	
	private AirItineraryReport itineraryReport;
	public void setItineraryReport(AirItineraryReport itineraryReport) {
		this.itineraryReport = itineraryReport;
	}
	
	public AbstractWorker() {
	
		super();
	}
	
	private WorkerAvailableMessage availableMessage;
	public void setAvailableMessage(WorkerAvailableMessage availableMessage) {
		this.availableMessage = availableMessage;
	}
	
	//send mail
	private SenderManager senderManager;
	public void setSenderManager(SenderManager senderManager) {
		this.senderManager = senderManager;
	}

	public void preStart() {
		logger.info("{} started " + getSelf());
		getContext().parent().tell(availableMessage, getSelf());
	}
	

	
	@Override
	public void onReceive(Object message) {
		try {
			//search command
			if (message instanceof SearchItineraryCmd) {		
				//search handler
				searchHandler((SearchItineraryCmd)message);
				
			} else if (message instanceof CreateBookingCmd) {//book command
					
				bookHandler((CreateBookingCmd)message);//book handler
	
			} else if (message instanceof PayBookingCmd) {//paycommand
				
				payHandler((PayBookingCmd)message);
				
			} else if (message instanceof RetrieveBookingCmd) {//retrieve command
				
				retrieveBookingHandler((RetrieveBookingCmd)message);
				
			} else if (message instanceof VerifyPaymentCmd) {//verify command
				
				verifyPaymentHandler((VerifyPaymentCmd) message);
			}
		}catch (Exception e) {
			logger.error("Actor " + getSelf() + " handle message " + message + " with error " + e);
		}

		getContext().parent().tell(availableMessage, getSelf());
	}
	
	/***
	 * 
	 * @param searchCmd
	 */
	private void searchHandler(SearchItineraryCmd searchCmd) {
		logger.info("Received[Search]: " + getSelf() + ": " + searchCmd.toString());
		AirFarePriceInfos result = new AirFarePriceInfos();
		try {
			result = doSearch(searchCmd);
			if (result.size() > 0) {
				farePriceCache.update(searchCmd.toHashString(), result);
			}
		} catch(Exception exception) {
			logger.error("Search error with exception:" + exception);
		}
		logger.info("Received[Search]: " + getSelf() + ":" + result);
	}
	
	/***
	 * 
	 * @param bookCmd
	 */
	private void bookHandler(CreateBookingCmd createBookingForm) throws Exception {
		
		AirItinerary itinerary = itineraryRepository.findById(createBookingForm.getId());
		if (itinerary != null) {		
			AirTicketingInfo info = itinerary.getTicketingInfo();
			try {
				info.setStatus(BookingStatus.BOOK_PROCESSING);
				itineraryRepository.update(itinerary);
				doBook(itinerary);
				itineraryRepository.update(itinerary);
				logger.info("Result[Book]-: " + getSelf() + ": " + itinerary.toString());
				
			} catch (Exception ex) {
				itinerary.getTicketingInfo().setDescription(ex.getMessage());
				itinerary.getTicketingInfo().setStatus(BookingStatus.BOOK_ERROR);
				itineraryRepository.update(itinerary);				
				logger.error("[Book] error with exception:" + ex);
			} finally {
				//refresh
				refreshActor();
			}
		}
	}
	/***
	 * 
	 * @param payCmd
	 */
	private void payHandler(PayBookingCmd payBookingForm) throws Exception{
		AirItinerary itinerary = itineraryRepository.findById(payBookingForm.getId());
		logger.info("Received[Pay]: " + getSelf() + ": " + itinerary.toString());
		if (itinerary != null) {
			AirTicketingInfo info = itinerary.getTicketingInfo();
			if (info.getStatus() == BookingStatus.BUY_PENDING) {
				info.setStatus(BookingStatus.BUY_PROCESSING);
				try {
					itineraryRepository.update(itinerary);
					doPay(itinerary);
					itineraryRepository.update(itinerary);
					logger.info("Received[Pay]: " + getSelf() + ": " + itinerary.toString());
				} catch (Exception ex) {
					itinerary.getTicketingInfo().setDescription(ex.getMessage());
					itinerary.getTicketingInfo().setStatus(BookingStatus.BUY_ERROR);
					itineraryRepository.update(itinerary);
					logger.error("Pay error with exception:" + ex);
				}
			}
		}
		//send Email
		senderManager.sendMessage("PAYMENT ", itinerary);
	}
	
	/**
	 * 
	 * @param verifyPaymentCmd
	 */
	private void verifyPaymentHandler(VerifyPaymentCmd verifyPaymentForm) throws Exception {
		logger.info("Received(VERIFY PAYMENT CMD): " + getSelf() + ": " + verifyPaymentForm.toString());
		AirItinerary checkError = itineraryRepository.findById(verifyPaymentForm.getId());
		String oldStatus = checkError.getTicketingInfo().getStatus().toString();	
		try {				
			boolean check = doVerifyPayment(verifyPaymentForm);
			String currentStatus = BookingStatus.BUY_SUCCESS.toString();
			checkError.getTicketingInfo().setStatus(BookingStatus.BUY_SUCCESS); 
			// pay error
			if (!check) {
				currentStatus = BookingStatus.BUY_ERROR.toString();	
				checkError.getTicketingInfo().setStatus(BookingStatus.BUY_ERROR); 
				senderManager.sendMessage("VERIFY PAYMENT ", checkError);
			}
			if(!oldStatus.equals(currentStatus)) {
				checkError.getTicketingInfo().setDescription("DIFFERENCE STATUS");
				String typeStatus = "DIFFERENCE";
				senderManager.sendMessage("VERIFY PAYMENT ", checkError,typeStatus);
			}
			// update mongodb
			itineraryReport.updatePayCheckInfo(verifyPaymentForm.getId(), currentStatus);
			
		} catch (Exception ex) {
			checkError.getTicketingInfo().setDescription(ex.getMessage());
			senderManager.sendMessage("VERIFY PAYMENT ", checkError);
			logger.error("VERIFY PAYMENT COMMAND ERROR WITH EXCEPTION:" + ex);
		}
		logger.info("Received(VERIFY PAYMENT CMD): " + getSelf() + ": DONE");
	}
	
	/**
	 * 
	 * @param retrieveCmd
	 * @throws Exception 
	 */
	private void retrieveBookingHandler(RetrieveBookingCmd retrieveCmd) throws Exception {
		logger.info("Received(RETRIEVE BOOKING): " + getSelf() + ": " + retrieveCmd.toString());
		AirItinerary itinerary = itineraryRepository.findById(retrieveCmd.getId());
		try {
			
			doRetrieveBooking(itinerary);
			itineraryRepository.updateBookingInfo(itinerary);
			
		} catch (Exception ex) {
			itinerary.getTicketingInfo().setStatus(BookingStatus.BOOKING_NOT_FOUND);
			itinerary.getTicketingInfo().setDescription(ex.getMessage());
			itineraryRepository.updateBookingInfo(itinerary);
			logger.error("RETRIEVE BOOKING COMMAND ERROR WITH EXCEPTION:" + ex);
		}
		
		logger.info("Received(RETRIEVE BOOKING): " + getSelf() + ": DONE");
		
	}

	//implement with provider outside
	protected abstract void doBook(AirItinerary itinerary) throws Exception;
	protected abstract void doPay(AirItinerary itinerary) throws Exception;
	protected abstract AirFarePriceInfos doSearch(SearchItineraryCmd searchCmd) throws Exception;
	protected abstract boolean doVerifyPayment(VerifyPaymentCmd verifyPaymentCmd) throws Exception;
	protected abstract void doRetrieveBooking(AirItinerary itinerary) throws Exception;
	protected void refreshActor(){};//VJ can not fill data 

}
