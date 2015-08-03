package com.mbv.airline.actors.vietjet;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.mbv.airline.common.Baggage;
import com.mbv.airline.common.BookResult;
import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirExtraService;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.ContactInfo;
import com.mbv.airline.common.info.Gender;
import com.mbv.airline.util.SelenUtils;


/***
 * @category VietJet
 * @author cuongtv
 *
 */
public class VietjetBook extends VietjetSearch {

	/***
	 * logger
	 */
	final static Logger logger = Logger.getLogger(VietjetBook.class);

	/**
	 * contructor for viet jet
	 * @param config
	 */
	public VietjetBook(VietJetConfig config) {

		super(config);
	}

	/**
	 * do booking
	 * @param itinerary
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	public BookResult doBook(AirItinerary itinerary,HtmlUnitDriver driver) throws Exception {		
		BookResult result = null;
		try {
			SearchItineraryCmd searchItinaryCmd = SearchItineraryCmd.getSearchItinaryCmd(itinerary);
			
			logger.info("Step 1:SEARCH FLIGHT");
			fillSearchForm(searchItinaryCmd,driver);

			logger.info("Setp 2:CHOOSE FLIGHT");	
			chooseFlight(itinerary,driver);	

			logger.info("Step 3:FILL PASSENGER INFO");	
			fillPassengerForm(itinerary,driver);

			logger.info("Step 4:CHOOSE ADD-ON (LAGGUGE)");
			chooseAddOn(itinerary,driver);

			logger.info("Step 5:CHOOSE PAYMENT METHOD");

			choosePaymentMethod(driver);

			if(config.isBook4Testing()) {
				logger.info("Step 6:The Booking is running on testing enviroment:" + config.isBook4Testing());
				result = getResult4Testing(driver);
			} else {

				logger.info("Step 6:CONFIRM AIR INFO");
				confirmAirFareInfo(driver);

				logger.info("Step 7:GET RESERVATION CODE");

				result = getReservationCode(driver); 	
			}

			return result;
		} catch (Exception ex) {

			throw ex;
		}
	}
	/***
	 * 
	 * @param itinerary
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	private void chooseFlight(AirItinerary itinerary,HtmlUnitDriver driver) throws Exception { 	
		try {
			String reference = itinerary.getFareInfos().get(0).getReference();
			//choose flight
			SelenUtils.clickElement(driver, By.xpath("//input[@id='gridTravelOptDep'][@value='" + reference +"']"));
			if(itinerary.getFareInfos().size() == 2){//2 ways
				reference = itinerary.getFareInfos().get(1).getReference();	
				SelenUtils.clickElement(driver, By.xpath("//input[@id='gridTravelOptRet'][@value='" + reference +"']"));
			}
			SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=continue]"));//go to next step

		} catch (Exception ex) {
			throw new Exception("Choose the flight with error:" + ex);
		}

	}
	/**
	 * step 3: fill passenger info to form detail 
	 * @param itinerary
	 * @param driver
	 * @throws Exception
	 */
	private void fillPassengerForm(AirItinerary itinerary,HtmlUnitDriver driver) throws Exception {	
		try {

			WebElement form = SelenUtils.findElement(driver, By.id("Details"));
			//get passenger info
			List<AirPassengerInfo>passengerInfos = itinerary.getPassengerInfos();
			//fill-data
			int index = 0;
			int infant = 0;
			for (AirPassengerInfo passInfo: passengerInfos) {
				index++;
				//fill date for adult and child
				if(passInfo.getPassengerType().equals(AirPassengerType.ADT) ||
						passInfo.getPassengerType().equals(AirPassengerType.CHD)) {
					//gender M or F for only ADUT
					if(passInfo.getPassengerType().equals(AirPassengerType.ADT)) {
						if(passInfo.getGender().equals(Gender.FEMALE)) {
							SelenUtils.selectElementByValue(form, By.id("txtPax"+ index +  "_Gender"), "F");
						} else {
							SelenUtils.selectElementByValue(form, By.id("txtPax"+ index + "_Gender"), "M");
						}				
					}
					//last name and first name
					SelenUtils.fillInputElement(form, By.id("txtPax" + index + "_LName"), passInfo.getLastName());
					SelenUtils.fillInputElement(form, By.id("txtPax"+ index + "_FName"), passInfo.getFirstName());					
					//city and country,email ,phone only one don;t fill again for other passengers
					if(index == 1) {					
						ContactInfo contactInfo = itinerary.getContactInfo();
						SelenUtils.fillInputElement(form, By.id("txtPax"+ index + "_Addr1"), contactInfo.getAddress());	//address
						SelenUtils.fillInputElement(form, By.id("txtPax"+ index + "_City"), contactInfo.getCity());//city
						SelenUtils.selectElementByText(driver, By.id("txtPax"+ index + "_Ctry"), "Vietnam");												
						SelenUtils.fillInputElement(form, By.id("txtPax"+ index + "_EMail"), contactInfo.getEmail());//email
						String mobile = contactInfo.getMobile();						
						if(mobile.length() == 11)
							mobile = mobile.substring(1);
						mobile = mobile.substring(0, 3) + "-" + mobile.substring(3,6) + "-" + mobile.substring(6, mobile.length());
						SelenUtils.fillInputElement(form, By.id("txtPax"+ index + "_Phone2"), mobile); //mobile							
					}
				} else {
					//fill date for infrant
					infant ++;
					SelenUtils.checkBoxElement(form, By.id("chkPax" + infant + "_Infant"), true);

					SelenUtils.fillInputElement(form, By.id("txtPax" + infant + "_Infant_Name"),
							passInfo.getLastName() + " " + passInfo.getFirstName());
				}			
			}
			//go next
			SelenUtils.clickElement(form, By.cssSelector("a.button.rightbutton[href*=continue]"));//go to next step

		} catch (Exception ex) {
			throw new Exception("Fill Passsenger form with error:" + ex);
		}
	}

	/**
	 * addOn
	 * choose baggage
	 * choose seats
	 * @param itinerary
	 * @param driver
	 * @return
	 */
	private void chooseAddOn(AirItinerary itinerary, HtmlUnitDriver driver) throws Exception {
		try {						
			//click to tab full infoaddOnForm
			SelenUtils.clickElement(driver, By.cssSelector("#editBtn1 a.scrollNext.clearSeats"));			
			// get list baggage
			List<AirExtraService> extrasDep = getExtraServices(itinerary.getExtraServices(),"0");  	// typeTrips = 0 is extra of oneway
			List<AirExtraService> extrasReturn = getExtraServices(itinerary.getExtraServices(),"1"); // typeTrips = 1 is extra of twoway
			
			Thread.sleep(2000);//waiting init data on select	
			//for each passenager
			int index = 0;
			for(AirPassengerInfo passInfo : itinerary.getPassengerInfos()) {
				//ONLY ADT
				if(passInfo.getPassengerType().equals(AirPassengerType.ADT) || passInfo.getPassengerType().equals(AirPassengerType.CHD)) {				

					AirExtraService extraInfo = getExtraInfo(extrasDep, 
							passInfo.getPassengerType() + String.valueOf(index));//passengerCode = passengerType+index

					//fill data
					String selectOptionDep = "select[id^='lstPaxItem:-" + (index+1) + ":1']";					
					SelenUtils.selectElementContainText(driver, By.cssSelector(selectOptionDep), Baggage.getText(extraInfo.getServiceCode()));

					if(itinerary.getFareInfos().size() == 2) {//2 way							
						extraInfo = getExtraInfo(extrasReturn, 
								passInfo.getPassengerType() + String.valueOf(index));//passengerCode = passengerType+index
						String selectOptionReturn = "select[id^='lstPaxItem:-" + (index+1) + ":2']";
						SelenUtils.selectElementContainText(driver, By.cssSelector(selectOptionReturn),  Baggage.getText(extraInfo.getServiceCode()));			
					}
					index++;	
				}
			}
			// insuranceCheckbox
			SelenUtils.clickElement(driver, By.cssSelector("#chkInsuranceNo"));
			//submit href
			SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=continue]"));
			
		} catch (Exception e) {
			throw new Exception("Choosing AddOn Step with error:" + e);
		}
	}

	/**
	 * get list extra service with type trips(oneway: typeTrips =0  || two way: typeTrips = 1)
	 * @param extraServices
	 * @param typeTrips
	 * @return
	 */
	private List<AirExtraService> getExtraServices(List<AirExtraService> extraServices, String typeTrips) {		
		List<AirExtraService> listServices = new ArrayList<AirExtraService>();
		for(AirExtraService service : extraServices)
			if(service.getHaveReturn().equals(typeTrips))
				listServices.add(service);	
		return listServices;				
	}

	/**
	 * choose payment method
	 * @param driver
	 */
	private void choosePaymentMethod(HtmlUnitDriver driver) throws Exception {	
		try {
			WebElement form = driver.findElement(By.id("Payment"));
			SelenUtils.clickElementContainValue(form, By.cssSelector("input#lstPmtType"),"PL");//send payment later
			SelenUtils.clickElement(form, By.cssSelector("a.button.leftbutton"));

		}catch(Exception exception) {
			throw new Exception("Choose Payment method with error:" + exception);
		}
	}

	/**
	 * confirm booking
	 * @param driver
	 */
	private void confirmAirFareInfo(HtmlUnitDriver driver) throws Exception {	

		try {
			SelenUtils.checkBoxElement(driver, By.id("chkIAgree"),true);		
			SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=continue]"));//go to next step
		}catch(Exception exception) {
			throw new Exception("Confirm booking with error:" + exception);
		}
	}

	/**
	 * get reservation code => done
	 * @param driver
	 * @return
	 */
	private BookResult getReservationCode(HtmlUnitDriver driver) throws Exception{		
		BookResult result = null;
		try {			 		
			WebElement form = SelenUtils.findElement(driver, By.id("Itinerary"));
			String reservationCode = SelenUtils.getText(form, By.cssSelector("#itin .ResNumber"));
			String amount = SelenUtils.getText(driver, By.cssSelector(".ChargesTotal > td:nth-child(2) > strong"));		
			String expiredDateText = SelenUtils.getText(driver, By.cssSelector("form#Itinerary h1:nth-child(12)"));
			String expiredDate = getExpiredDate(expiredDateText);	
			if(reservationCode != null){
				result = new BookResult();
				result.setReservationCode(reservationCode.replaceAll("[^0-9]", "").trim());
				result.setAmount(Long.valueOf(amount.replace(".", "-").split("-")[0].replaceAll("[^0-9]", "").trim()).longValue());
				result.setExpiredDate(DateUtils.parse(expiredDate,DateUtils.DD_MM_YYYY_HH_MM_SS));
			} 
		} catch (Exception ex){
			throw new Exception("Get reservation code with error:"+ ex.getMessage());
		}	
		return result;
	}

	/***
	 * get extras info for each passenger
	 * @param extras
	 * @param passengerCode
	 * @return
	 */
	private AirExtraService getExtraInfo(List<AirExtraService>extras, String passengerCode) {

		AirExtraService extraInfo = new AirExtraService();
		//for each extras service to fill baggage info			
		for (AirExtraService extra: extras) {
			if(StringUtils.equals(extra.getPassengerCode(), passengerCode)) return extra;

		}		
		return extraInfo;
	}	

	/**
	 * step 9: parse date time
	 * @param expiredDateText
	 * @return
	 */
	private String getExpiredDate(String expiredDateText){
		String [] parseExpired = expiredDateText.split(",");
		String expiredtemp = parseExpired[1].trim();
		String datetime= expiredtemp.split("Time")[0].trim().replaceAll("[^0-9,:,/]","T").replace("TTT", "T").substring(1).replace("TTT", "T");
		String [] expired = datetime.split("T");
		return expired[0] + "T" + expired[1]+":00";
	}


	/**
	 * TEST TEST TEST
	 * get reservation code in file config.properties
	 * @throws ParseException 
	 */

	private BookResult getResult4Testing(HtmlUnitDriver driver) throws Exception{
		BookResult result = new BookResult();
		String amount = SelenUtils.getText(driver, By.cssSelector("form#Confirm .ChargesTotal"));
		result.setAmount(Long.valueOf(amount.replace(".", "-").split("-")[0].replaceAll("[^0-9]", "").trim()));		
		logger.info("[Amount] = " + Long.valueOf(amount.replace(".", "-").split("-")[0].replaceAll("[^0-9]", "").trim()));		
		
		//generate revervation code
		result.setReservationCode(config.getReservationCode());
		//set next date
		result.setExpiredDate(DateUtils.nextDay(new Date(), 1)); 
		return result;
	}

}
