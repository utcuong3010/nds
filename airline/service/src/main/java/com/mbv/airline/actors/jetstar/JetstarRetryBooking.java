package com.mbv.airline.actors.jetstar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.info.AirExtraService;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.Gender;

/**
 * 
 * @author phamtuyen
 *
 */
public class JetstarRetryBooking extends JetstarPay {
	
	/**
	 * logger
	 */
	final Logger logger = Logger.getLogger(JetstarRetryBooking.class);
	
	/**
	 * account info
	 * @param jetstarConfig
	 */
	public JetstarRetryBooking(JetstarConfig jetstarConfig) {
		super(jetstarConfig);
	}
	
	/**
	 * do: get fare info with booking
	 * @param itinerary
	 * @param session
	 * @throws Exception
	 */
	public void doRetrieveBooking(AirItinerary itinerary,String session) throws Exception {
		try {
			logger.info("STEP 1: SEARCH RESERVATION CODE");
			Document doc = searchReservationCode(itinerary,session);
			logger.info("STEP 2: EXTRACT FARE INFO");				
			extractFareInfo(itinerary,doc);
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * do: extract data(fare info)
	 * @param itinerary
	 * @param doc
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private void extractFareInfo(AirItinerary itinerary, Document doc) throws Exception {
		try {
			Elements bookingSummary = doc.select("div[id=booking-summary]");
			// get passenger info (adt + inf)
			List<AirPassengerInfo> passengerInfos = new ArrayList<AirPassengerInfo>();
			Elements dataContainer = bookingSummary.select("div[id=data-container]");
			Elements passInfoElements = dataContainer.select("dl > ul > li");		
			for(Element passInfoElement:passInfoElements) {
				AirPassengerInfo passengerInfo = new AirPassengerInfo();
				// get gender
				String [] passengerInfoString = passInfoElement.text().substring(2).replace(".", "-").split("-");
				passengerInfo.setPassengerType(AirPassengerType.ADT);
				passengerInfo.setGender(Gender.FEMALE);
				if(passengerInfoString[0].replaceAll("\\s","").equals("Mr"))
					passengerInfo.setGender(Gender.MALE);
				// get name
				passengerInfo.setLastName(getLastName(passengerInfoString[1]));
				String [] firstName = getFirstName(passengerInfoString[1]).split(",");
				passengerInfo.setFirstName(firstName[0]);
				passengerInfos.add(passengerInfo);
				// get inf info
				if(firstName.length == 2) {
					passengerInfo = new AirPassengerInfo();
					passengerInfo.setPassengerType(AirPassengerType.INF);
					String [] infInfo = firstName[1].replace(":", "-").split("-");
					passengerInfo.setLastName(getLastName(infInfo[1]).replaceAll("\\s",""));	
					passengerInfo.setFirstName(getFirstName(infInfo[1]).replaceAll("\\s",""));
					passengerInfos.add(passengerInfo);
				}
			}
			// get ticketInfo
			Elements ticketInfoElement = bookingSummary.select("div[id=confirmation]");
			Elements totalPriceElement = ticketInfoElement.select("dl[class=total-price]");
			String amountString = totalPriceElement.select("dd > span").text().replaceAll("[^\\d]","");
			String dateString = doc.select("#booking-on-hold.alert-message.warning.clearfix div.message p strong").text();		
			if(!dateString.isEmpty()) {
				itinerary.getTicketingInfo().setExpiredDate(convertDate(dateString));					
				itinerary.getTicketingInfo().setStatus(BookingStatus.BOOK_SUCCESS);			
			}
			// if booking paid
			String bookingPaidElement = doc.select("#payments-table tbody tr.amt-paid td.detail-title.detail-emphasis.amt-paid").toString();
			if(!bookingPaidElement.isEmpty())
				itinerary.getTicketingInfo().setStatus(BookingStatus.BUY_SUCCESS);	
			
			itinerary.getTicketingInfo().setAmount(Long.valueOf(amountString).longValue());
			itinerary.getTicketingInfo().setUpdatedDate(new Date());

			// get contact info
			Elements contactElement = ticketInfoElement.select("dl[class=contact-details]");
			String mobile  =  contactElement.select("dd > dl > dd.summary-contact-mobile").text();		
			String email = contactElement.select("dd > dl > dd.summary-contact-pc-email").text();
			itinerary.getContactInfo().setMobile(mobile);
			itinerary.getContactInfo().setEmail(email);
			// get bookingJson		
			Elements scriptJson = doc.getElementsByTag("script");
			Elements bookingJson = scriptJson.tagName("bookingJson");
			String [] arrayString = bookingJson.get(1).toString().split("var bookingJson =")[1].split(";");		
			String bookingString = arrayString[0].replaceAll("(\\r|\\n)", "").replaceAll("\"", "'").replaceAll("'", "\"");			
			// get an array from the JSON object
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(bookingString);
			JSONArray bookingContent = (JSONArray) jsonObject.get("journeys");
			JSONObject passengerObject = null;
			JSONArray onewayObject = null;
			JSONArray twowayObject = null;
			
			int index = 0;
			Iterator i = bookingContent.iterator();
			while (i.hasNext()) {
				JSONObject json = (JSONObject) i.next();
				passengerObject = (JSONObject) json.get("payment"); // passenger buy ticket
				if(index == 0)
					onewayObject = (JSONArray) json.get("legs");
				else
					twowayObject = (JSONArray) json.get("legs");
				index++; 
			}		
			// get totalCHD
			int totalCHD = countCHD(passengerObject);
			itinerary.setPassengerInfos(getPassengerInfos(passengerInfos, totalCHD));
			// get fareInfo
			List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();
			fareInfos.add(getFareInfo(itinerary,onewayObject));			
			if(bookingContent.size() == 2)
				fareInfos.add(getFareInfo(itinerary,twowayObject));
			itinerary.setFareInfos(fareInfos);
			// get extra service
			List<AirExtraService> onewayService = getExtraService(onewayObject,"0"); // haveReturn = 0
			List<AirExtraService> twowayService = new ArrayList<AirExtraService>();
			if(bookingContent.size() == 2)		
				twowayService = getExtraService(twowayObject,"1"); // haveReturn = 1 	
			itinerary.setExtraServices(mergeExtraService(onewayService, twowayService));
		} catch (Exception ex) {
			logger.error("PARSER ERROR: " + ex);
			throw ex;
		}
	}
	
	/**
	 * do: get passenger info
	 * @param passengerInfos
	 * @param totalCHD
	 * @return
	 */
	private List<AirPassengerInfo> getPassengerInfos(List<AirPassengerInfo> passengerInfos, int totalCHD) {
		for(int i = passengerInfos.size() -1 ;i > 0 && totalCHD > 0;i--) {
			passengerInfos.get(i).setPassengerType(AirPassengerType.CHD);
			totalCHD--;
		}
		swapPassengerInfos(passengerInfos,"INF","ADT",0);
		return swapPassengerInfos(passengerInfos,"INF","CHD",1);
	}
	
	/**
	 * swap index of passenger info
	 * @param passengerInfos
	 * @param typePassenger1
	 * @param typePassenger2
	 * @param flag
	 * @return
	 */
	public List<AirPassengerInfo> swapPassengerInfos (List<AirPassengerInfo> passengerInfos,String typePassenger1,String typePassenger2,int flag){
		int begin = 0;
		int end = passengerInfos.size() -1;
		while(begin < end){
			AirPassengerInfo adtInfo = passengerInfos.get(begin);
			AirPassengerInfo infInfo = passengerInfos.get(end);
			if(adtInfo.getPassengerType().toString().equals(typePassenger1) && infInfo.getPassengerType().toString().equals(typePassenger2)){
				passengerInfos.set(begin, infInfo);
				passengerInfos.set(end, adtInfo);
				begin++;
				if(flag == 1)
					end--;
			}
			if(adtInfo.getPassengerType().toString().equals("ADT"))
				begin++;
			if(flag == 0)
				end--;
		}
		return 	passengerInfos;
	}

	/**
	 * do: Merge extra service of onewayService + twowayService
	 * @param onewayService
	 * @param twowayService
	 * @return
	 */
	private List<AirExtraService> mergeExtraService(List<AirExtraService> onewayService, List<AirExtraService> twowayService) {
		List<AirExtraService> extraServices = new ArrayList<AirExtraService>();
		extraServices.addAll(onewayService);
		extraServices.addAll(twowayService);
		return extraServices;
	}

	/**
	 * do: get last name
	 * @param string
	 * @return
	 */
	private String getLastName(String string) {	
		return string.split(" ")[1];
	}

	/**
	 * do: get first name
	 * @param string
	 * @return
	 */
	private String getFirstName(String string) {
		String [] array = string.split(" ");	
		String nameString = "";	
		for(int i=2; i < array.length; i++) {
			if(i < array.length-1)
				nameString += array[i] + " ";
			else 
				nameString += array[i];
		}	
		return nameString;
	}

	/**
	 * do: convert string to date
	 * @param dateString
	 * @return
	 * @throws Exception
	 */
	private Date convertDate(String dateString) throws Exception{ 
		try {
			int yearCurrent = Calendar.getInstance().get(Calendar.YEAR);
			int monthCurrent = Calendar.getInstance().get(Calendar.MONTH) + 1; // month start from 0 to 11
			String []partDateExpired = dateString.split(yearCurrent+"");
			String expireDay = partDateExpired[0].replaceAll("[^0-9]", "").trim();	
			dateString = expireDay + " " + monthCurrent + " " + yearCurrent + partDateExpired[1];
			return JetstarBook.getexpiredDate(dateString);
		} catch (Exception ex) {
			logger.error("PARSE DATE EXPIRED FALSE: " + ex);
			throw ex;
		}	
	}

	/**
	 * do: count children
	 * @param paymentObject
	 * @return
	 */
	private int countCHD(JSONObject paymentObject) throws Exception {		
		try {
			JSONObject paxCount = (JSONObject) paymentObject.get("paxCount");
			if(paxCount.containsKey("CHD"))
				return Integer.parseInt(paxCount.get("CHD").toString());
			return 0;
		} catch (Exception ex) {
			logger.error("GET CHILDREN NULL: " + ex);
			throw ex;
		}	
	}

	/**
	 * get fare info from jsonObject
	 * @param jsonArray
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public AirFareInfo getFareInfo(AirItinerary itinerary,JSONArray jsonArray) throws Exception {
		try {
			AirFareInfo fareInfo = new AirFareInfo();
			Iterator index = jsonArray.iterator();		
			while (index.hasNext()) {	
				JSONObject jsonObject = (JSONObject) index.next();
				String flightCode = ((String) jsonObject.get("flightCode")).replaceAll("\\s","");
				String deptTime = (String) jsonObject.get("deptTime");
				String arrvTime = (String) jsonObject.get("arrvTime");			
				fareInfo.setFlightCode(flightCode);
				fareInfo.setVendor(itinerary.getFareInfos().get(0).getVendor());
				fareInfo.setDepartureDate(DateUtils.parse(deptTime.replaceAll(",", "T").replaceAll("\\s",""), "HH:mm'T'dd/MM/yyyy"));
				fareInfo.setArrivalDate(DateUtils.parse(arrvTime.replaceAll(",", "T").replaceAll("\\s",""),"HH:mm'T'dd/MM/yyyy"));
				fareInfo.setDestinationCode(getFlightInfo((String) jsonObject.get("arrvAirport"),flightLocations));
				fareInfo.setOriginCode(getFlightInfo((String) jsonObject.get("deptAirport"),flightLocations));
			}
			return fareInfo;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * hash map flight locations
	 */
	@SuppressWarnings("serial")
	public HashMap<String, String> flightLocations = new HashMap<String, String>() {
		{		
			put("Buôn Ma Thuột", "BMV");
			put("Đà Nẵng", "DAD");
			put("Đồng Hới", "VDH");
			put("Hà Nội", "HAN");
			put("Hải Phòng", "HPH");
			put("Huế", "HUI");
			put("Nha Trang", "CXR");
			put("Phú Quốc", "PQC");
			put("Quy Nhơn", "UIH");
			put("Thanh Hóa", "THD");
			put("Thành phố Hồ Chí Minh", "SGN");
			put("Tuy Hoa", "TBB");
			put("Vinh", "VII");						
		}
	};

	/**
	 * do: get 
	 * @param keySearch
	 * @param flightInfo
	 * @return
	 */
	public String getFlightInfo(String keySearch,HashMap<String, String> flightInfo) {		
		Iterator<String> keySetIterator = flightInfo.keySet().iterator();
		while(keySetIterator.hasNext()) {
			String key = keySetIterator.next();
			if(key.contains(keySearch))
				return flightInfo.get(key);		
		}
		return  null;
	}

	/**
	 * do: get extra service from json
	 * @param jsonArray
	 * @param typeTrips (oneway or twoway)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes" )
	public List<AirExtraService> getExtraService(JSONArray jsonArray,String typeTrips) throws Exception {
		try {
			List<AirExtraService> extraServices = new ArrayList<AirExtraService>();	
			Iterator index = jsonArray.iterator();
			while (index.hasNext()) {		
				JSONObject jsonObject = (JSONObject) index.next();
				String flightCode = ((String)jsonObject.get("flightCode")).replaceAll("\\s","");
				String bags = ((String) jsonObject.get("bags"));
				if(!bags.isEmpty()){		
					String baggages = ((String) jsonObject.get("bags")).split(":")[1].replaceAll("\\s","");				
					String [] arrayBaggage = baggages.split(",");
					for(int i = 0;i < arrayBaggage.length; i++){
						AirExtraService extraService = new AirExtraService();
						extraService.setServiceCode(getFlightInfo(arrayBaggage[i],hashMapBaggages));
						extraService.setHaveReturn(typeTrips);
						extraService.setTypeFlightCode(flightCode);	
						extraServices.add(extraService);
					}
				}				
			}
			return extraServices;	
		} catch (Exception ex) {
			logger.error("GET EXTRA SERVICE FAILED [ERROR] = " + ex);
			throw ex;
		}	
	}

	/**
	 * do: hash map convert baggage
	 */
	@SuppressWarnings("serial")
	public HashMap<String, String> hashMapBaggages = new HashMap<String, String>() {
		{
			put("15kg", "BG15");
			put("20kg", "BG20");
			put("25kg", "BG25");
			put("30kg", "BG30");
			put("35kg", "BG35");
			put("40kg", "BG40");
		}
	};	
}