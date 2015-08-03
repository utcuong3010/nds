package com.mbv.airline.actors.jetstar;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mbv.airline.common.BookResult;
import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirExtraService;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.util.JsoupExecute;

/**
 * 
 * @author phamtuyen
 *
 */

public class JetstarBook extends JetstarSearch {

	/**
	 * logger
	 */
	final static Logger logger = Logger.getLogger(JetstarBook.class);

	/**
	 * init account
	 * @param jetstarConfig
	 */
	public JetstarBook(JetstarConfig jetstarConfig) {
		super(jetstarConfig);
	}

	/**
	 * total of adult and children
	 */
	private int totalAdtChd;

	/**
	 * do: create hash map to change baggage
	 */
	@SuppressWarnings("serial")
	private HashMap<String, String> hashMapBaggage = new HashMap<String, String>(){
		{
			put("none", "none");
			put("BG15", "none");
			put("BG20", "none");
			put("BG25", "BG05");
			put("BG30", "BG10");
			put("BG35", "BG15");
			put("BG40", "BG20");
		}
	};

	/**
	 * do: book itinerary
	 * @param itinerary
	 * @param session
	 * @param viewstate
	 * @return
	 * @throws Exception
	 */
	public BookResult doBook(AirItinerary itinerary,String session,String viewstate) throws Exception {
		try {			
			logger.info("STEP 1: FILL FORM SEARCH");
			fillFormSearch(SearchItineraryCmd.create(itinerary), session, viewstate);

			logger.info("STEP 2: SELECT FARE INFO");
			selectFareInfo(itinerary,session);

			logger.info("STEP 3: FILL FORM PASSENGER DETAIL");
			Connection.Response response = fillPassengerForm(itinerary, session);

			if(jetstarConfig.isBook4Testing()){
				logger.info("STEP 4: GET RESERVATION CODE 4 TESTTING");
				return getReservationCode4Testing(response, session);
			}

			logger.info("STEP 5: CHOOSE METHOD PAYMENT");
			choosePaymentMethod(session);

			logger.info("STEP 6: GET RESERVATION CODE");
			return getReservationCode(session);

		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * do: select reference and baggage
	 * @param itinerary
	 * @param session
	 * @throws Exception
	 */
	private void selectFareInfo(AirItinerary itinerary,String session) throws Exception {
		try {
			// doGet
			String url = jetstarConfig.getBaseUrl() + "AgentSelect.aspx";	
			Connection.Response  response = JsoupExecute.doGet(url, session);
			URL urlResult = response.url();
			if(!urlResult.toString().contains("AgentSelect.aspx"))
				throw new Exception("GET [URL] = " + url + " FAILED");

			// get viewState
			VIEWSTATE = JsoupExecute.getViewState(response);			
			// doPost				
			response = JsoupExecute.doPost(url,createParamsSelectFareInfo(itinerary), session);
			urlResult = response.url();
			if(!urlResult.toString().contains("AgentPassenger.aspx"))
				throw new Exception("SELECT FARE INFO FAILED WITH [PARAMS] = " + createParamsSelectFareInfo(itinerary));

		} catch (Exception ex) {
			logger.error("SELECT FARE INFO FAILED [ERROR] = "+ ex);
			throw ex;
		}
	}

	/**
	 * do: create hash map for select fare info
	 * @return
	 */
	private HashMap<String, String> createParamsSelectFareInfo(AirItinerary itinerary) throws Exception{
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			// select reference
			params.put("__VIEWSTATE", VIEWSTATE);
			params.put("__EVENTTARGET", "ControlGroupAgentSelectView$ButtonSubmit");			
			params.put("ControlGroupAgentSelectView$AvailabilityInputAgentSelectView$market1", itinerary.getFareInfos().get(0).getReference());		
			params.put("marketstructure", "OneWay");

			if(itinerary.getFareInfos().size()==2){
				params.put("ControlGroupAgentSelectView$AvailabilityInputAgentSelectView$market2", itinerary.getFareInfos().get(1).getReference());
				params.put("marketstructure", "RoundTrip");
			}
			// select baggage
			createParamsSelectBaggage(itinerary,params);
			return params;

		} catch (Exception ex) {
			logger.error("CREATE PARAMS FOR SELECT FARE INFO FAILED [ERROR] = " + ex); 
			throw ex;
		}	
	}

	/**
	 * do: select baggage 
	 * type of trips: typeOfTrips = 0 (Oneway), typeOfTrips = 1 (Twoway) 
	 * @param itinerary
	 * @param params
	 * @return
	 */
	private void createParamsSelectBaggage(AirItinerary itinerary,HashMap<String, String> params) throws Exception {
		try {
			// get total adult + children
			SearchItineraryCmd searchItineraryCmd = SearchItineraryCmd.create(itinerary);
			int adt = searchItineraryCmd.getPassengerQuantity(AirPassengerType.ADT);
			int chd = searchItineraryCmd.getPassengerQuantity(AirPassengerType.CHD);
			totalAdtChd = adt + chd;

			// select baggage			
			selectBaggage(itinerary,params,0); // typeOfTrips = 0;
			if(itinerary.getFareInfos().size() == 2)
				selectBaggage(itinerary,params,1); // typeOfTrips = 1;			

		} catch (Exception ex) {
			logger.error("CREATE PARAMS SELECT BAGGAGE FAILED [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: select baggage for fare info
	 * @param itinerary
	 * @param params
	 * @return
	 */
	private void selectBaggage(AirItinerary itinerary, HashMap<String, String> params, int typeOfTrips) throws Exception {
		try {
			boolean isStarMaxFlag = false;
			// get Reference
			String reference = itinerary.getFareInfos().get(0).getReference();
			if(typeOfTrips == 1)
				reference = itinerary.getFareInfos().get(1).getReference();
			// Reference is Starter max
			if(reference.contains("RY")) {
				isStarMaxFlag = true;
				params.put("AgentAdditionalBaggagePassengerView$AdditionalBaggageDropDownListJourney" + typeOfTrips, "none");
			}
			// Reference is Starter or Starter plus
			else {
				String firstBaggage = getFirstBaggage(itinerary.getExtraServices(), typeOfTrips + ""); 
				params.put("AgentAdditionalBaggagePassengerView$AdditionalBaggageDropDownListJourney" + typeOfTrips, firstBaggage);	
			}

			// select baggage
			if(typeOfTrips == 0){ // oneway
				for(int i = 0; i < totalAdtChd; i++) {
					String baggage = getBaggage(itinerary.getExtraServices(), typeOfTrips + "", i,isStarMaxFlag);
					params.put("AgentAdditionalBaggagePassengerView$AdditionalBaggageDropDownListJourney" + typeOfTrips + "Pax" + i, baggage);				
				}	
			}
			else{ // twoway
				int index = getFirstIndexBaggage(itinerary.getExtraServices());		
				String baggage = "none";
				for(int i = 0; i < totalAdtChd; i++) {				
					if(index != -1)
						baggage = getBaggage(itinerary.getExtraServices(), typeOfTrips + "", index,isStarMaxFlag);
					params.put("AgentAdditionalBaggagePassengerView$AdditionalBaggageDropDownListJourney" + typeOfTrips + "Pax" + i, baggage);	
					index++;
				}	
			}		
			params.put("baggage-selection-toggler", "on");	

		} catch (Exception ex) {
			logger.error("SELECT BAGGAGE FAILED [ERROR] = " + ex);
			throw ex;
		}
	}

	private int getFirstIndexBaggage(List<AirExtraService> extraServices) { 

		for(int i = 0;i < extraServices.size(); i++){
			AirExtraService extraService = extraServices.get(i);
			if(extraService.getHaveReturn().equals("1"))
				return i;
		}		
		return -1;
	}

	/**
	 * do: get first baggage of trips
	 * @param extraServices
	 * @param typeOfTrips
	 * @return
	 */
	private String getFirstBaggage(List<AirExtraService> extraServices, String typeOfTrips) throws Exception {
		try {
			String firstBaggage = "none";
			if(extraServices == null || extraServices.size() == 0 )
				return firstBaggage;

			for(AirExtraService extraService : extraServices){
				if(extraService.equals(typeOfTrips)){
					firstBaggage = extraService.getServiceCode();
					break;
				}			
			}
			return firstBaggage;

		} catch (Exception ex) {
			logger.error("GET FIRST BAGGAGE FAILED [ERROR] = " + ex);
			throw ex;
		}
	}

	/**
	 * do: get baggage with type of trip(one way || two way) and index of service list
	 * @param extraServices
	 * @param typeOfTrips
	 * @param indexOfServices
	 * @return
	 */
	private String getBaggage(List<AirExtraService> extraServices, String typeOfTrips, int indexOfServices, boolean isStarMaxFlag) throws Exception {
		try {
			String baggage = "none";
			if(extraServices == null || extraServices.size() == 0)
				return baggage;	

			for(int i = 0; i < extraServices.size(); i++){				
				if(i == indexOfServices && extraServices.get(i).getHaveReturn().equals(typeOfTrips)){
					baggage = extraServices.get(i).getServiceCode();
					break;
				}
			}		
			// if (reference is starter max)
			if(isStarMaxFlag)
				return changeBaggage(baggage);		
			return baggage;
		} catch (Exception ex) {
			logger.error("GET BAGGAGE FAILED [ERROR] = " + ex);
			throw ex;
		}		
	}

	/**
	 * do: change baggage when reference is starter max
	 * @param baggage
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String changeBaggage(String baggage) throws Exception {
		try {
			String tmpBag = "none";
			Iterator iterator = hashMapBaggage.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				if(mapEntry.getKey().equals(baggage)){
					tmpBag = mapEntry.getValue().toString();
					break;
				}
			}
			return tmpBag;
		} catch (Exception ex) {
			logger.error("CHANGE BAGGAGE FAILED [ERROR] = " + ex);
			throw ex;
		}		
	}

	/**
	 * do: fill passenger info to form
	 * @param itinerary
	 * @throws Exception
	 */
	private Connection.Response fillPassengerForm(AirItinerary itinerary,String session) throws Exception {
		try {
			String url = jetstarConfig.getBaseUrl() + "AgentPassenger.aspx";
			Connection.Response response = JsoupExecute.doGet(url, session);
			URL urlResult = response.url();
			if(!urlResult.toString().contains("AgentPassenger.aspx"))
				throw new Exception("GET [URL] = " + url + " FAILED");

			// doPost		
			VIEWSTATE = JsoupExecute.getViewState(response);
			response = JsoupExecute.doPost(url, createParamsPassengerForm(itinerary), session); 
			urlResult = response.url();
			if(!urlResult.toString().contains("AgentPay.aspx")) {
				// get error 
				Document doc = Jsoup.parse(response.body());
				String error = doc.select("#error > p:nth-child(2)").text();
				if(error.isEmpty())
					error = "CAN NOT POST TO [URL] = " + url + " WITH [PARAMS] = " + createParamsPassengerForm(itinerary);				
				throw new Exception(error);	
			}		
			return response;

		} catch (Exception ex) {
			logger.error("FILL FORM PASSENGER FAILED [ERROR] = " + ex);
			throw ex;
		}
	}

	/**
	 * do: create params fill passenger info form
	 * @param itinerary
	 * @return
	 */
	private HashMap<String, String> createParamsPassengerForm(AirItinerary itinerary) throws Exception {
		try {
			HashMap<String, String> params = new HashMap<String, String>();
			createParamsContactInfo(itinerary,params);
			createParamsPassengerInfo(itinerary,params);		
			return params;

		} catch (Exception ex) {
			logger.error("CREATE PARAMS PASSENGER FORM FAILED [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: create params (fill contact info to form)
	 * @param itinerary
	 * @param params
	 */
	private void createParamsContactInfo(AirItinerary itinerary, HashMap<String, String> params) throws Exception {
		try {
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$DropDownListTitle", "MR");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxFirstName", itinerary.getAgentInfo().getUserId()); // TODO: CHANGE getUserId 
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxLastName", itinerary.getAgentInfo().getAgentId()); // TODO: CHANGE getAgentId 
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$DropDownListWorkPhoneCountryCode", "VN");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxWorkPhone", itinerary.getContactInfo().getMobile().substring(1)); // TODO: CHANGE getAgentInfo 
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxJahEmailAddress", itinerary.getContactInfo().getEmail()); // TODO: CHANGE getAgentInfo
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxEmailAddress", itinerary.getContactInfo().getEmail()); // TODO: CHANGE getAgentInfo
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxPostalCode", "084");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxCity", "4");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxAddressLine1", "Suite 3.1, H3 Tower, 384 Hoang Dieu, Ward 6, Distric");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxAddressLine2", "");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$TextBoxCompanyName", "Viet Fast Services JST Company");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$DropDownListCountry", "VN");
			params.put("AgentControlGroupPassengerView$AgentContactInputViewPassengerView$DropDownListCountryCode", "VN");			
		} catch (Exception ex) {
			logger.error("CREATE PARAMS CONTACT INFO FAILED [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: create params (fill passenger info to form)
	 * @param itinerary
	 * @param params
	 */
	private void createParamsPassengerInfo(AirItinerary itinerary, HashMap<String, String> params) throws Exception {
		try {
			// params: fill name label
			for(int i = 1; i <= itinerary.getPassengerInfos().size(); i++) {
				params.put("default-value-firstname-" + i, "Tên");
				params.put("default-value-lastname-" + i, "Họ và tên đệm");
			}

			// params: fill nameInfo for adt + chd
			int indexField = 1;
			int totalInf = itinerary.getPassengerInfos().size() - totalAdtChd;

			for(int i = 0; i < totalAdtChd; i++) {
				AirPassengerInfo passengerInfo = itinerary.getPassengerInfos().get(i);
				String gender = getGender(passengerInfo);				
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$DropDownListTitle_" + indexField, gender);
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$TextBoxFirstName_" + indexField, passengerInfo.getFirstName());
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$TextBoxLastName_" + indexField, passengerInfo.getLastName());				
				indexField++;
				if(totalInf != 0 && i < totalInf)
					indexField++;
			}

			// params: fill nameInfo for inf
			indexField = 2;
			int indexAssign = 1; // Assign inf for adt
			for(int i = totalAdtChd ; i < itinerary.getPassengerInfos().size(); i++) {
				AirPassengerInfo passengerInfo = itinerary.getPassengerInfos().get(i);
				String gender = getGender(passengerInfo);	
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$TextBoxFirstName_" + indexField, passengerInfo.getFirstName());
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$TextBoxLastName_" + indexField, passengerInfo.getLastName());
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$DropDownListAssign_" + indexField, indexAssign + "");
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$DropDownListBirthDateDay_" + indexField, DateUtils.getTypeOfDate(passengerInfo.getBirthDate(), "day") + "");
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$DropDownListBirthDateMonth_" + indexField, DateUtils.getTypeOfDate(passengerInfo.getBirthDate(), "month") + "");
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$DropDownListBirthDateYear_" + indexField, DateUtils.getTypeOfDate(passengerInfo.getBirthDate(), "year") + "");
				params.put("AgentControlGroupPassengerView$AgentPassengerInputViewPassengerView$DropDownListGender_" + indexField, gender);				
				indexField += 2;
				indexAssign ++;
			}

			// submit form
			params.put("__VIEWSTATE", VIEWSTATE);
			params.put("__EVENTTARGET", "AgentControlGroupPassengerView$ButtonSubmit");	
		} catch (Exception ex) {
			logger.error("CREATE PARAMS PASSENGER INFO FAILED [ERROR] = " + ex); 
			throw ex;
		}
	}

	/**
	 * do: get gender
	 * @param passengerInfo
	 * @return
	 */
	private String getGender(AirPassengerInfo passengerInfo) throws Exception {
		String gender = "MR";
		switch (passengerInfo.getGender()) {
		case MALE:
			if(passengerInfo.getPassengerType().equals(AirPassengerType.CHD))
				gender = "MSTR";
			if(passengerInfo.getPassengerType().equals(AirPassengerType.INF))
				gender = "1";
			break;
		case FEMALE:
			if(passengerInfo.getPassengerType().equals(AirPassengerType.ADT))
				gender = "MRS";
			else if(passengerInfo.getPassengerType().equals(AirPassengerType.CHD))
				gender = "MISS";
			else
				gender = "2";
			break;		
		}	
		return gender;
	}

	/**
	 * do: choose payment method
	 * @throws Exception
	 */
	private void choosePaymentMethod(String session) throws Exception { 	
		try {
			// doGet
			String url = jetstarConfig.getBaseUrl() + "AgentPay.aspx";
			Connection.Response response = JsoupExecute.doGet(url, session);
			URL urlResult = response.url();
			if(!urlResult.toString().contains("AgentPay.aspx"))
				throw new Exception("GET [URL] = " + url + " FAILED");

			// doPost
			VIEWSTATE = JsoupExecute.getViewState(response); // get viewState		
			response = JsoupExecute.doPost(url,createParamsPaymentMethod(), session);
			urlResult = response.url();

			// doGet
			logger.info("[URL AFTER POST PAYMENT] = " + urlResult.toString());
			int retry = 0;
			while(retry < 5 && !urlResult.toString().contains("htl2-Itinerary.aspx")) {
				response = JsoupExecute.doGet(urlResult.toString(), session);
				urlResult = response.url();
				retry++;
				logger.info("[URL AFTER POST PAYMENT] = " + urlResult.toString());
			}
			logger.info("[URL AFTER POST PAYMENT] = " + urlResult.toString());

		} catch (Exception ex) {
			logger.error("CHOOSE PAYMENT METHOD FAILED [ERROR] = " + ex);
			throw ex;
		}
	}

	/**
	 * do: create params contains payment method
	 * @return
	 * @throws Exception
	 */
	private HashMap<String, String> createParamsPaymentMethod() throws Exception {
		try {
			// create date expired of card
			Date date = new Date();
			date =  DateUtils.dateChange(date, 10); // increment 10 days vs current day
			HashMap<String, String> params = new HashMap<String, String>();								
			params.put("__VIEWSTATE", VIEWSTATE);
			params.put("ControlGroupAgentPayView$PaymentSectionAgentPayView$UpdatePanelAgentPayView$PaymentInputAgentPayView$PaymentMethodDropDown", "ExternalAccount-HOLD");
			params.put("ControlGroupAgentPayView$PaymentSectionAgentPayView$UpdatePanelAgentPayView$PaymentInputAgentPayView$DropDownListEXPDAT_Month", DateUtils.getTypeOfDate(date, "month") + "");
			params.put("ControlGroupAgentPayView$PaymentSectionAgentPayView$UpdatePanelAgentPayView$PaymentInputAgentPayView$DropDownListEXPDAT_Year", DateUtils.getTypeOfDate(date, "year") + "");
			params.put("inlineDCCAjaxSucceeded", "false");
			params.put("ControlGroupAgentPayView$AgreementInputAgentPayView$CheckBoxAgreement", "on");
			params.put("summary-amount-total", "NaN");
			params.put("ControlGroupAgentPayView$ButtonSubmit", "");			
			return params;

		} catch (Exception ex) {
			logger.error("CREATE PARAMS PAYMENT METHOD FAILED [ERROR] = " + ex);
			throw ex;
		}

	}

	/**
	 * do: get reservation code
	 * @return
	 * @throws Exception
	 */
	private BookResult getReservationCode(String session) throws Exception {
		try {
			BookResult result = null;
			String url = jetstarConfig.getBaseUrl() + "htl2-Itinerary.aspx";
			Connection.Response response = JsoupExecute.doGet(url, session);
			URL urlResult = response.url();
			if(!urlResult.toString().contains("htl2-Itinerary.aspx"))
				throw new Exception("CAN NOT GET [URL] = " + url);	

			Document doc = Jsoup.parse(response.body());
			String reservationCode = doc.select("div#datalayer-itinerary-data").attr("data-transactionpnr").toString();
			String amount = doc.select("div#datalayer-itinerary-data").attr("data-bookingtotalprice").toString().replace(".", ",").split(",")[0];
			String dateExpire = doc.select("#booking-data > booking").attr("holddate").toString();

			if(!reservationCode.isEmpty()){
				result = new BookResult();
				result.setReservationCode(reservationCode);
				result.setAmount(Long.parseLong(amount));
				result.setExpiredDate(getexpiredDate(dateExpire));
			}
			return result;

		} catch (Exception ex) {
			logger.error("GET RESERVATION CODE FAILED: [ERROR] = " + ex);
			throw ex;
		}
	}

	/**
	 * convert element expired date
	 * @param dateString
	 * @return
	 * @throws Exception 
	 */
	protected static Date getexpiredDate(String dateString) throws Exception {		
		try {
			DateFormat datetimeFormat = new SimpleDateFormat("dd MM yyyy hh:mm a z");
			TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");	
			datetimeFormat.setTimeZone(gmtTimeZone);	
			
			return datetimeFormat.parse(dateString.replace("Thg", "").replace(",", "").trim());		
			
		} catch (Exception ex) {
			logger.error("PARSE DATE-EXPIRED FAILED: " + ex);
			throw ex;
		}
	}

	/**
	 * do: test (get reservation code)
	 * plan: for the test environment
	 * @return
	 * @throws Exception 
	 */
	private BookResult getReservationCode4Testing(Connection.Response response, String session) throws Exception { 
		try {
			// get reservation code
			Document doc = Jsoup.parse(response.body());
			String amount = doc.select("#summary-amount-total > span > span").text().replaceAll("[^0-9]", "");
			logger.info("[Amount] = " + Long.parseLong(amount));
			BookResult result = null;
			if(jetstarConfig.getReservationCode() != null && !amount.isEmpty()){
				result = new BookResult();
				result.setReservationCode(jetstarConfig.getReservationCode()); 
				result.setAmount(Long.parseLong(amount));
				result.setExpiredDate(DateUtils.dateChange(new Date(), 1)); // increment 1 day vs current day
			}			
			return result;

		} catch (Exception ex) {
			logger.error("GET RESERVATION CODE 4 TESTTING FAILED [ERROR] = " + ex);
			throw ex;
		}	
	}
}
