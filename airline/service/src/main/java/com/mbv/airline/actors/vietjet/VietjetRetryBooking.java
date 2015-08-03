package com.mbv.airline.actors.vietjet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.info.AirExtraService;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.Gender;
import com.mbv.airline.util.SelenUtils;

/**
 * 
 * @author phamtuyen
 *
 */
public class VietjetRetryBooking extends VietjetPay {

	/**
	 * logger
	 */
	final static Logger logger = Logger.getLogger(VietjetRetryBooking.class);

	/**
	 * inti account info
	 * @param config
	 */
	public VietjetRetryBooking(VietJetConfig config) {
		super(config);
	}

	public void doRetrieveBooking(AirItinerary itinerary,HtmlUnitDriver driver) throws Exception {
		try {
			
			logger.info("STEP 1:  FIND RESERVATION CODE");
			searchReservationCode(itinerary.getTicketingInfo().getReservationCode(), driver);			
			logger.info("STEP 2: VIEW ITINERARY");
			viewItinerary(driver,itinerary);
			logger.info("STEP 3: EXTRACT FARE INFO");
			extractFareInfo(itinerary,driver);

		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * do: choose button view itinerary
	 * @param driver
	 * @param itinerary
	 */
	private void viewItinerary(HtmlUnitDriver driver, AirItinerary itinerary) throws Exception {
		try {

			SelenUtils.waitForElementExist(driver, By.id("grdLegOmegaEdit"));
			Document doc = Jsoup.parse(driver.getPageSource().toString());
			// update ticketInfo
			String totalChargeLabel = doc.select("#Edit > table:nth-child(27) tr:nth-child(1) td.grdChrInfo").text();
			String totalPaymentLabel = doc.select("#Edit > table:nth-child(27) tr:nth-child(2) td.grdChrInfo").text();
			if (Long.parseLong(totalChargeLabel.replaceAll("[^0-9]", "").trim()) > 0
					&& Long.parseLong(totalPaymentLabel.replaceAll("[^0-9]", "").trim()) > 0)
				itinerary.getTicketingInfo().setStatus(BookingStatus.BUY_SUCCESS);
			else if (Long.parseLong(totalChargeLabel.replaceAll("[^0-9]", "").trim()) == 0) {
				itinerary.getTicketingInfo().setStatus(BookingStatus.BOOK_EXPIRED);
				throw new Exception("BOOKING EXPIRED");
			}			
			else 
				itinerary.getTicketingInfo().setStatus(BookingStatus.BOOK_SUCCESS);
			
			itinerary.getTicketingInfo().setAmount(Long.parseLong(totalChargeLabel.replaceAll("[^0-9]", "").trim()));
			
			// get passenger info
			Elements passInfosTable = doc.select("table[class=hdrTable900]");
			Elements passInfoElements = passInfosTable.select("tr.GridPaxEven");		
			List<AirPassengerInfo> passengerInfos = extractPassengerInfo(passInfoElements);
			passInfoElements = passInfosTable.select("tr.GridPaxOdd");	
			mergePassengerInfo(passengerInfos, extractPassengerInfo(passInfoElements));

			// get fare info
			DateFormat dateFormatFull = new SimpleDateFormat("dd/MM/yyyy EEE HH:mm", Locale.US);
			List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();	
			Elements fareInfoElements = doc.select("table[id=grdLegOmegaEdit]");
			Elements fareInfoElement = fareInfoElements.select("table[id=grdLegGridEdit]");
			Elements flightInfoElements = fareInfoElement.select("table[id=grdLegGridEditMstr]");

			for(Element flightInfoElement:flightInfoElements){
				AirFareInfo fareInfo = new AirFareInfo();
				String flightDate = flightInfoElement.select("td").first().text();
				String departureTime = flightInfoElement.select("td").get(2).text().substring(0, 5);
				String arrivalTime = flightInfoElement.select("td").get(3).text().substring(0, 5);

				fareInfo.setDepartureDate(dateFormatFull.parse(flightDate + " " + departureTime.substring(0, 5)));
				fareInfo.setArrivalDate(dateFormatFull.parse(flightDate + " " + arrivalTime.substring(0, 5)));
				fareInfo.setFlightCode(flightInfoElement.select("td").get(1).text());	
				fareInfo.setOriginCode(flightInfoElement.select("td").get(2).text().substring(6, 9));
				fareInfo.setDestinationCode(flightInfoElement.select("td").get(3).text().substring(6, 9));		

				fareInfos.add(fareInfo);
			}
			
			// update passengerInfos && fareInfos							
			itinerary.setPassengerInfos(passengerInfos);
			itinerary.setFareInfos(fareInfos);

			SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=newitinerary]")); // click button viewItinerary

		} catch (Exception ex) {
			throw new Exception("CLICK BUTTON VIEW ITINERARY [ERROR] = " + ex);
		}
	}

	/**
	 * do: extract passenger info
	 * @param elements
	 * @return
	 */
	public List<AirPassengerInfo> extractPassengerInfo(Elements elements) {
		List<AirPassengerInfo> passengerInfos = new ArrayList<AirPassengerInfo>();
		List<AirPassengerInfo> chdInfos = new ArrayList<AirPassengerInfo>();

		for(Element element : elements){
			AirPassengerInfo passengerInfo = new AirPassengerInfo();
			String []fullName = element.select("td").get(1).text().split(",");
			passengerInfo.setFirstName(fullName[0]);
			passengerInfo.setLastName(fullName[1]);

			if(element.select("td").get(2).text().equals("M") || (element.select("td").get(2).text().equals("F"))){
				passengerInfo.setGender(Gender.MALE);
				passengerInfo.setPassengerType(AirPassengerType.ADT);
				if((element.select("td").get(2).text().equals("F")))
					passengerInfo.setGender(Gender.FEMALE);
			}				

			if(element.select("td").get(2).text().equals("C")){
				passengerInfo.setPassengerType(AirPassengerType.CHD);
				chdInfos.add(passengerInfo);
			}				
			else			
				passengerInfos.add(passengerInfo);	
		}

		for(AirPassengerInfo passengerInfo : chdInfos){
			passengerInfos.add(passengerInfo);
		}

		return passengerInfos;
	}

	/**
	 * do: megre passenger info(adt + chd + inf)
	 * @param passengerInfos
	 * @param chdInfos
	 * @return
	 */
	public List<AirPassengerInfo> mergePassengerInfo(List<AirPassengerInfo> passengerInfos,List<AirPassengerInfo> chdInfos){
		for(AirPassengerInfo passengerInfo : chdInfos)
			passengerInfos.add(passengerInfo);
		return passengerInfos;
	}

	/**
	 * do: extract fare info
	 * @param itinerary
	 * @return
	 */
	private void extractFareInfo(AirItinerary itinerary, HtmlUnitDriver driver) throws Exception {
		try {
			SelenUtils.waitForElementExist(driver, By.cssSelector("#EditItinerary > div:nth-child(11) > div:nth-child(2) > div:nth-child(2) > table:nth-child(6) > tbody > tr:nth-child(5) > td"));
			Document doc = Jsoup.parse(driver.getPageSource());
			// get infInfo && extraServices
			extractFareInfo(doc,itinerary);
			
			// get contactInfo
			String mobile = doc.select("#EditItinerary div div.content div:nth-child(2) table:nth-child(6) tbody tr:nth-child(5) td").text().split(" ")[0];
			String email = doc.select("#EditItinerary div div.content div:nth-child(2) table:nth-child(6) tbody tr:nth-child(6) td").text();
			itinerary.getContactInfo().setMobile(mobile);
			itinerary.getContactInfo().setEmail(email);
			
			// update ticketInfo
			itinerary.getTicketingInfo().setUpdatedDate(new Date());
			if(itinerary.getTicketingInfo().getStatus().equals(BookingStatus.BOOK_SUCCESS)){
				String dateString = doc.select("#EditItinerary > div:nth-child(11) > div:nth-child(2) > div:nth-child(2) > table:nth-child(6) > tbody > tr:nth-child(1) > td > span").text();
				String expireDate = dateString.replaceAll("[^0-9,:,/,\\s]","").trim();		
				itinerary.getTicketingInfo().setExpiredDate(DateUtils.parse(expireDate, DateUtils.HH_MM_DD_MM_YYYY));
			}
			
		} catch (Exception ex) {
			throw new Exception("PARSE [ERROR] = " + ex);
		}
	}

	/**
	 * do: extract infInfo && extraServices
	 * @param doc
	 * @param itinerary
	 * @throws Exception
	 */
	private void extractFareInfo(Document doc, AirItinerary itinerary) throws Exception {
		try {

			sortPassengerInfo(itinerary.getPassengerInfos());		
			// get infInfo
			Element itineraryElement = doc.select("#EditItinerary div div.content div table tbody").get(1);
			Elements rowElements = itineraryElement.select("> tr > td");
			int index = 0; // index of row passenger
			String fullName = "";
			for(Element element : rowElements) {
				if(index % 2 == 0) {						
					fullName = element.textNodes().toString().replaceAll("\\[","").replaceAll("\\]","").replaceAll(", ","").trim();
					if(!fullName.isEmpty()) {
						AirPassengerInfo passengerInfo = new AirPassengerInfo();
						String []splitName = fullName.split("\\s");
						String firstName = "";
						for(int i = 1;i < splitName.length - 1;i++) {
							firstName += splitName[i] + " ";
						}		

						passengerInfo.setFirstName(firstName + splitName[splitName.length - 1]);
						passengerInfo.setLastName(splitName[0]);
						passengerInfo.setPassengerType(AirPassengerType.INF);
						// add passenger to list
						itinerary.getPassengerInfos().add(passengerInfo);					
					}
				}
				index++;
			}

			// get extra service
			List<AirExtraService> extraServices = new ArrayList<AirExtraService>();
			Elements extraElements = doc.select("#EditItinerary div div.content div table tbody tr td div table tr");

			for(Element extraElement : extraElements) {
				Elements tdElements = extraElement.getElementsByTag("td");	

				for(Element tdElement : tdElements) {
					if(tdElement.text().contains("Add Ons")){
						AirExtraService extraService = new AirExtraService();
						if(tdElements.get(0).text().equals("1"))
							extraService.setHaveReturn("0");
						else
							extraService.setHaveReturn("1");

						extraService.setServiceCode(tdElements.get(2).text().split("-")[1].replaceAll("[^0-9]","")); 						
						extraServices.add(extraService);
					}			
				}

			}				
			itinerary.setExtraServices(extraServices); 

		} catch (Exception ex) {
			throw new Exception("PARSE [ERROR] = " + ex);
		}
	}

	/**
	 * do: sort passengerInfo
	 * @param passengerInfos
	 * @return
	 */
	public List<AirPassengerInfo> sortPassengerInfo (List<AirPassengerInfo> passengerInfos) {		
		for(int i = 0;i <= passengerInfos.size()-2;i++) {		
			for(int j = i+1;j <= passengerInfos.size()-1;j++) {			
				AirPassengerInfo passengerInfoI = passengerInfos.get(i);
				AirPassengerInfo passengerInfoJ = passengerInfos.get(j);
				if((passengerInfos.get(i).getPassengerType().toString().equals("CHD") && passengerInfos.get(j).getPassengerType().toString().equals("ADT"))){
					passengerInfos.set(i, passengerInfoJ);
					passengerInfos.set(j, passengerInfoI);				
				}
			}
		}
		return 	passengerInfos;
	}

}
