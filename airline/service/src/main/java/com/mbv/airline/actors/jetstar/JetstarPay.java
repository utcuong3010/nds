package com.mbv.airline.actors.jetstar;

import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.PayResult;
import com.mbv.airline.common.cmd.VerifyPaymentCmd;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.util.JsoupExecute;

/**
 * 
 * @author phamtuyen
 *
 */
public class JetstarPay {

	/**
	 * logger
	 */
	final Logger logger = Logger.getLogger(JetstarPay.class);

	/**
	 * account info
	 */
	private final JetstarConfig jetstarConfig;

	/**
	 * init account
	 * @param jetstarConfig
	 */
	public JetstarPay(final JetstarConfig jetstarConfig) {
		this.jetstarConfig = jetstarConfig;
	}

	/**
	 * keep view state
	 */
	private String VIEWSTATE;

	/**
	 * do: pay reservation code
	 * @param itinerary
	 * @param session
	 * @return
	 * @throws Exception
	 */
	public PayResult doPay(AirItinerary itinerary, String session) throws Exception {
		try {

			logger.info("STEP 1: SEARCH RESERVATION CODE");
			searchReservationCode(itinerary,session);

			if(jetstarConfig.isPay4Testing()) {
				logger.info("STEP 2: GET RESULRS 4 TESTTING");
				return getResults4Testing(itinerary,session);
			}

			logger.info("STEP 2: COMFORM FARE INFO");
			confirmFareInfo(session);

			logger.info("STEP 3: PAYMENT");
			payment(session);

			logger.info("STEP 4: AWATING RESULTS(PAYMENT) FROM JETSTAR");
			Document htmlBody = awaitingResults(session);

			logger.info("STEP 5: GET RESULTS");
			return getResults(htmlBody);

		} catch (Exception ex) {
			throw ex;
		}
	}

	private PayResult getResults4Testing(AirItinerary itinerary, String session) throws Exception {
		try {

			String url = jetstarConfig.getBaseUrl() + "C3Payment.aspx";
			Connection.Response response = JsoupExecute.doGet(url, session);	
			URL urlResult = response.url();			
			if(!urlResult.toString().contains("C3Payment.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + url);

			PayResult result = new PayResult();
			result.setReceiptNumber("1234567");
			return result;		

		} catch (Exception ex) {
			throw ex;
		}	
	}

	/**
	 * do: search reservation code
	 * @param itinerary
	 * @param session
	 * @return 
	 */
	protected  Document searchReservationCode(AirItinerary itinerary, String session) throws Exception {	
		try {

			String dateString = DateUtils.format(itinerary.getTicketingInfo().getExpiredDate(), "dd/MM/yyyy");		
			String url = jetstarConfig.getBaseUrl() + "AgentBookingDetails.aspx?RecordLocator="+itinerary.getTicketingInfo().getReservationCode()
					+ "&status=status-hold&expirationDate="+dateString+"&currentTab=#paymentDues";
			Connection.Response response = JsoupExecute.doGet(url, session);	
			URL urlResult = response.url();			
			if(!urlResult.toString().contains("AgentBookingDetails.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + url);

			Document doc = Jsoup.parse(response.body(),"UTF-8");
			String reservationCode = doc.select("#confirmation.booking-summary-col.col-totals.col-dockable.col-transparent.clearfix h4.booking-reference span").text();
			if(!reservationCode.equals(itinerary.getTicketingInfo().getReservationCode()))
				throw new Exception("CAN NOT GET [URL] = " + url + " [ERROR] = " + reservationCode);

			return doc;
		} catch (Exception ex) {
			logger.error("SEARCH RESERVATION CODE FAILED [ERROR] = " + ex); 
			throw ex;
		}	
	}

	/**
	 * do: confirm fare info
	 * @param session
	 * @throws Exception
	 */
	private void confirmFareInfo(String session) throws Exception {		
		try {

			String url = jetstarConfig.getBaseUrl() + "C3Payment.aspx";
			Connection.Response response = JsoupExecute.doGet(url, session);	
			URL urlResult = response.url();			
			if(!urlResult.toString().contains("C3Payment.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + url);

			VIEWSTATE = JsoupExecute.getViewState(response);

		} catch (Exception ex) {
			logger.error("CONFIRM FARE INFO FAILED [ERROR] = " + ex); 
			throw ex;
		}
	}

	private void payment(String session) throws Exception {	
		try {
			// doPost
			String url = jetstarConfig.getBaseUrl() + "C3Payment.aspx";
			Connection.Response response = JsoupExecute.doPost(url,createParamsPayment(), session);		
			URL urlResult = response.url();								
			logger.info("[URL AFTER POST PAYMENT] = " + urlResult.toString());
			// doGet
			int retry = 0;
			while(retry < 7 && !urlResult.toString().contains("C3Itinerary.aspx")) {
				response = JsoupExecute.doGet(urlResult.toString(), session);	
				urlResult = response.url();
				retry++;
				logger.info("[URL CONTINUE AFTER DOPOST PAYMENT] = " + urlResult.toString());
			}
			logger.info("[URL AFTER POST PAYMENT] = " + urlResult.toString());
			
		} catch (Exception ex) {
			logger.error("PAYMENT FAILED [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: create params payment
	 * @return
	 */
	private static final String elementPaymentParams = "ControlGroupPaymentView%24PaymentSectionPaymentView%24UpdatePanelPaymentView%24PaymentInputPaymentView%24";
	private HashMap<String, String> createParamsPayment() throws Exception {
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			// fill hidden
			params.put("__EVENTTARGET", "");
			params.put("__EVENTARGUMENT", "");
			params.put("pageToken", "");
			params.put("__VIEWSTATE", VIEWSTATE);
			// fill cardInfo
			params.put("card_number1", "");
			params.put("card_number2", "");
			params.put("card_number3", "");
			params.put("card_number4", "");		
			params.put(elementPaymentParams + "TextBoxCC__AccountHolderName", "");
			params.put(elementPaymentParams + "DropDownListEXPDAT_Month", "12");
			params.put(elementPaymentParams + "DropDownListEXPDAT_Year", "2016");
			// fill voucherInfo
			params.put(elementPaymentParams + "TextBoxCC__VerificationCode", "");
			params.put(elementPaymentParams + "TextBoxACCTNO", "");
			params.put("inlineDCCAjaxSucceeded", "false");
			params.put(elementPaymentParams + "TextBoxVoucherAccount_VO_ACCTNO", "");
			// submit
			params.put(elementPaymentParams + "PaymentMethodDropDown", "AgencyAccount-AG");
			params.put("ControlGroupPaymentView$AgreementInputPaymentView$CheckBoxAgreement", "on");
			params.put("ControlGroupPaymentView$ButtonSubmit", "");
			return params;

		} catch (Exception ex) {
			logger.error("CREATE PARAMS PAYMENT FAILED [ERROR] = " + ex);
			throw ex;
		}
	}

	/**
	 * do: awaiting results form jetstar
	 * @param session
	 * @throws Exception
	 */
	private Document awaitingResults(String session) throws Exception {	
		try {	
			// doGet
			String url = jetstarConfig.getBaseUrl() + "C3Itinerary.aspx";
			Connection.Response response = JsoupExecute.doGet(url, session);	
			URL urlResult = response.url();
			// doGet
			response = JsoupExecute.doGet(url, session);	
			urlResult = response.url();	
			if(!urlResult.toString().contains("C3Itinerary.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + url + " SECOND TIME");
			
			return Jsoup.parse(response.body());

		} catch (Exception ex) {
			logger.error("FAILED RESULTS" + " [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: get pay for resevation code form jetstar
	 * @param session
	 * @return
	 * @throws Exception
	 */
	private PayResult getResults(Document htmlBody) throws Exception {	
		try {		
			String totalAmountPaid = htmlBody.select("#paymentDisplayTable.paymentCol tbody tr:nth-child(5) td:nth-child(2)").text().replaceAll("[^0-9]", "").trim();;
			logger.info("TOTAL AMOUNT OF PAYMENT SELECT CSS THE SECOND TIME" + totalAmountPaid);
			
			PayResult result = null;
			if(Long.parseLong(totalAmountPaid) > 0) {
				result = new PayResult();
				result.setReceiptNumber(totalAmountPaid);
			}		
			return result;
			
		} catch (Exception ex) {
			logger.error("PARSER " + " [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: verify pay for reservation
	 * @param verifyPaymentCmd
	 * @param session
	 * @return
	 * @throws Exception
	 */
	protected boolean doVerifyPayment(VerifyPaymentCmd verifyPaymentCmd, String session) throws Exception {
		try {

			logger.info("STEP 1: SEARCH RESERVATION CODE");
			findReservationCode(verifyPaymentCmd, session); 

			logger.info("STEP 2: CONFIRM FARE INFO");
			String htmlBody = confirmFareInfo(verifyPaymentCmd, session);

			logger.info("STEP 3: CHECK STATUS RESERVATION CODE");			
			return checkStatusResCode(htmlBody);

		} catch (Exception ex) {
			throw ex;
		}		
	}

	/**
	 * process verify payment
	 * do: find reservation code
	 * @param command
	 * @throws Exception
	 */
	private void findReservationCode(VerifyPaymentCmd command, String session) throws Exception {
		try {

			String url = jetstarConfig.getBaseUrl()  + "AgentBookingList.aspx#findBooking?keyword="+command.getReservationCode();	
			Connection.Response response = JsoupExecute.doGet(url, session);		
			URL urlResult = response.url();
			if(!urlResult.toString().contains("AgentBookingList.aspx"))			
				throw new Exception("NOT FOUNT [RESERVATION CODE] = " + command.getReservationCode());			

		} catch (Exception ex) {
			logger.error("NOT FOUND [RESERVATION CODE] = "+ command.getReservationCode() + " [ERROR] = " + ex);
			throw ex;
		}
	}

	/**
	 * confirm fare info
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private String confirmFareInfo(VerifyPaymentCmd command, String session) throws Exception{		
		try {
			String expiredDate = DateUtils.format(command.getExpiredDate(), "dd/MM/yyyy");
			String url = jetstarConfig.getBaseUrl() + "AgentBookingDetails.aspx?RecordLocator="+command.getReservationCode()+"&status=status-hold&expirationDate="+expiredDate+"&currentTab=#paymentDues";
			Connection.Response response = JsoupExecute.doGet(url, session);		
			URL urlResult = response.url();
			if(!urlResult.toString().contains("AgentBookingDetails.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + url);			

			return response.body();

		} catch (Exception ex) {
			logger.error("NOT FOUND PASSENGER INFO WITH RESERVATION CODE" + ex);
			throw ex;
		}		
	}

	/**
	 * check status reservation code => done
	 * @param htmlBody
	 * @return
	 * @throws Exception 
	 */
	private boolean checkStatusResCode(String htmlBody) throws Exception {			
		try {
			
			Document doc = Jsoup.parse(htmlBody);	
			String mountString = doc.select("#payments-table tbody tr.amt-paid td.detail-title.detail-emphasis.amt-paid").toString();
			if(mountString.isEmpty())
				return false;
			return true;
			
		} catch (Exception ex) {
			logger.error("NOT FOUND ELEMENT AMOUNT WHEN CHECK STATUS RESERVATION CODE [ERROR] = " + ex.getMessage());				
			throw ex;
		}		
	}
}