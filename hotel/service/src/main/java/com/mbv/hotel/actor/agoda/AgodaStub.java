package com.mbv.hotel.actor.agoda;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mbv.hotel.cmd.PayCmd;
import com.mbv.hotel.common.AgodaResult;
import com.mbv.hotel.info.CardInfo;
import com.mbv.hotel.info.GuestInfo;
import com.mbv.hotel.info.HotelBookingInfo;
import com.mbv.hotel.util.SelenUtils;



public class AgodaStub {
	
	
	private final long TIMEOUT = 180;
	
	/**
	 * logger for agoda
	 */
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/***
	 * The Web driver
	 */
	private WebDriver driver;
	
	
	/***
	 * card info 
	 */
	private CardInfo cardInfo;
	
	

	public AgodaStub(CardInfo cardInfo) {
		
		this.cardInfo = cardInfo;
		
		//init driver
		initialize();
	}

	private void initialize() {
		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0";
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("general.useragent.override", userAgent);
		profile.setPreference("permissions.default.stylesheet", 2);
		profile.setPreference("permissions.default.image", 2);
		profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so","false");
		profile.setPreference("javascript.enabled", true);
		
		DesiredCapabilities cap = DesiredCapabilities.firefox();	
		cap.setCapability(FirefoxDriver.PROFILE, profile);
//		cap.setVersion("firefox");
//		cap.setJavascriptEnabled(true); 	

		driver = new HtmlUnitDriver(cap);
//		driver = new FirefoxDriver(cap);
	}
	
	/** 
	 * go to the page with url
	 * @param url
	 */
	private void go2Page(String url) {
		driver.get(url);
	}
	
	/***
	 * return result 
	 * @param hotelBookingInfo
	 * @return
	 * @throws Exception 
	 */
	public AgodaResult doPay(PayCmd payCmd) throws Exception {
		AgodaResult result = null;
		try {
			//goto url's payment
//			go2Page(payCmd.getUrl());
//			
//			logger.info("[Begining]-select room");
//			//step 1
//			selectRoom(payCmd.getBookingInfo());
//			logger.info("[End]-select room");
//			
//			logger.info("[Begining]-fill contact detail");
//			//step 2
//			GuestInfo guestInfo = payCmd.getBookingInfo().getGuests().get(0);
//			fillContactInfo(guestInfo);
//			logger.info("[End]-fill contact detail");
//			
//			logger.info("[Begining]-fill payment detail");
//			//step 3
//			fillPaymentInfo(cardInfo);
//			
			logger.info("[End]-fill payment detail");
			
			//final step
		} catch(Exception exception) {
			logger.error("pay with error:" + exception);
			
			throw exception;
		}
		return result;
	}
	
	/***
	 * 
	 * from this step
	 * select room
	 * @param bookingInfo
	 * @throws Exception 
	 */
	private void selectRoom(HotelBookingInfo bookingInfo) throws Exception {
		
		if(selectRoom1(bookingInfo) == 0) {
			try {
				selectRoom2(bookingInfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	/** 
	 * 
	 * @param bookingInfo
	 * @throws Exception 
	 */
	private void selectRoom2(HotelBookingInfo bookingInfo) throws Exception {
		
		List<WebElement> bookBtns = SelenUtils.waitForElementsExist(driver,By.cssSelector("a[id^=button_room]"), TIMEOUT);
		for(WebElement bookBtn: bookBtns) {
			if(StringUtils.equals(bookBtn.getAttribute("roomtypeid"), bookingInfo.getRoomId())) {
				
				SelenUtils.selectDropdownByValue(driver.findElement(By.id(bookBtn.getAttribute("validateddlid"))), 
						String.valueOf(bookingInfo.getQuantity()));
				
				bookBtn.click();
				
				break;
			}
		}
		
		
		
	}
	
	/***
	 * select room with the structure html 
	 * @param bookingInfo
	 * @return
	 * @throws Exception 
	 */
	private int selectRoom1(HotelBookingInfo bookingInfo) throws Exception {
		//select all item rows		
		List<WebElement> rows = SelenUtils.waitForElementsExist(driver, By.cssSelector("table#rgt-roomitem"), TIMEOUT);
		WebElement bookButton = null;		
		for(WebElement row : rows) {			
			List<WebElement> hrefElements = row.findElements(By.cssSelector("a"));
			for(WebElement hrefElement: hrefElements) {
				String hrefContent = hrefElement.getAttribute("href");
				if(hrefContent.contains(bookingInfo.getRoomId()) && 
						hrefContent.contains(bookingInfo.getHotelId())) {
					//select room		
					SelenUtils.selectDropdownByValue(row.findElement(By.cssSelector("select[id^=rgt-select-room]")), 
							String.valueOf(bookingInfo.getQuantity()));
					//here room
					bookButton = row.findElement(By.cssSelector(".td-room-book a[id^=book]"));
					
					break;
				}
			}
			if(bookButton != null) break;
		}
		if(bookButton != null) {
			bookButton.click();
		}
		
		return rows.size();
	}
	
	/**
	 * fill contact info
	 * @param guestInfo
	 * @throws Exception 
	 */
	private void fillContactInfo(GuestInfo guestInfo) throws Exception {
		//fill form
		SelenUtils.fillElement(driver,By.id("txtCntFirstName"), guestInfo.getFirstName(),TIMEOUT);
		SelenUtils.fillElement(driver,By.id("txtCntLastName"), guestInfo.getLastName(),TIMEOUT);
		SelenUtils.fillElement(driver, By.id("txtEmail"), guestInfo.getEmail(),TIMEOUT);
		SelenUtils.fillElement(driver, By.id("txtConfirmEmail"), guestInfo.getEmail(),TIMEOUT);
		SelenUtils.fillElement(driver, By.id("txtPhoneNo"), guestInfo.getMobile(),TIMEOUT);
		//select country
		SelenUtils.selectDropdownByValue(driver.findElement(By.id("ddlCntctCountry")), guestInfo.getNationalId());
		
		WebElement continueBtn = SelenUtils.waitForElementExist(driver, By.id("btnContinue"), TIMEOUT);
		continueBtn.click();
	}
	
	
	/**
	 * fill date for payment
	 * @param cardInfo
	 * @throws Exception 
	 */
	private void fillPaymentInfo(CardInfo cardInfo) throws Exception {
		//select pay now
		WebElement payOptionBtn = SelenUtils.waitForElementExist(driver, By.id("payOpt1"), TIMEOUT);
//		payOptionBtn.click();
		//method payment
		SelenUtils.selectDropdownByText(driver.findElement(By.id("ddlPaymentMethod")), cardInfo.getTypeCard());
		//number card
		SelenUtils.fillElement(driver, By.id("txtCard_0"), cardInfo.getCardNumber().substring(0, 4),TIMEOUT);
		SelenUtils.fillElement(driver, By.id("txtCard_1"), cardInfo.getCardNumber().substring(5, 9),TIMEOUT);
		SelenUtils.fillElement(driver, By.id("txtCard_2"), cardInfo.getCardNumber().substring(10, 14),TIMEOUT);
		SelenUtils.fillElement(driver, By.id("txtCard_3"), cardInfo.getCardNumber().substring(15),TIMEOUT);
		//hotel name
		SelenUtils.fillElement(driver, By.id("txtHolderName"), cardInfo.getCardHotelName(),TIMEOUT);
		//set expire date
		SelenUtils.selectDropdownByValue(driver.findElement(By.id("ddlExpiryMonth")), cardInfo.getExpiryDate().substring(0,2));
		SelenUtils.selectDropdownByValue(driver.findElement(By.id("ddlExpiryYear")), cardInfo.getExpiryDate().substring(3));
		//cvc
		SelenUtils.fillElement(driver, By.id("txtCvc"), cardInfo.getCvcCode(),TIMEOUT);
		
		//set country
		SelenUtils.selectDropdownByText(driver.findElement(By.id("ddlIssueBankCountry")),cardInfo.getCountryOfIssuingBank());

		//bank
		SelenUtils.fillElement(driver, By.id("txtIssueBank"), cardInfo.getBankGaveCard(),TIMEOUT);
		

		WebElement submitBtn = SelenUtils.waitForElementExist(driver, By.id("btnSubmit"), TIMEOUT);
		submitBtn.click();
	

	}
	
	public static void main(String[] args) {
		
//		HotelPaymentMsg msg = new HotelPaymentMsg();
//		
////		String url = "http://www.agoda.com/vi-vn/325-giang-vo-hotel/hotel/hanoi-vn.html?asq=vl3HLt1awvZNTL1CSbbBipIcNhWzohZynP4SPAvD4GKUgeLSiaJW2H76xKCP%2fX6mg7jgTmTYnOXQFFRLMYwIQcMFr2lGvPZJ8UHfTL3XT4MjLjSBOAwZyS6JAcM42xMrhQvut7KNiQCQzsGUkHx0Ulimd%2fxfiiqWV6tXdnk6GdpFwtAKSdjLp%2b0Z4YCJLeD1qJS%2fcoZ7kbM%2fitCryUv9Fg%3d%3d&setcookienew=1";
//		
//		String url = "http://www.agoda.vn/pages/agoda/default/WaitPage.aspx?asq=vl3HLt1awvZNTL1CSbbBipIcNhWzohZynP4SPAvD4GKUgeLSiaJW2H76xKCP%2fX6mg7jgTmTYnOXQFFRLMYwIQcMFr2lGvPZJ8UHfTL3XT4Pi9gFJ3zoRUUxA1bXicT8i&header=&footer=&providerID=332,29000&HotelID=568700&cid=1606990&waitpage=hotel";
//		msg.setUrl(url);//set url
//		
//		
//		//booking info
//		HotelBookingInfo info = new HotelBookingInfo();
//		
//		info.setBookingId("55111a0afa5d802443000003");
//		info.setCheckIn("2015-04-28");
//		info.setCheckOut("2015-04-28");
//		info.setTotal(100000);
//		info.setQuantity(2);
//		info.setExtrabeds(0);
//		info.setHotelId("568700");
//		info.setRoomId("2666464");
//		info.setOccupancy(2);
//		info.setCityName("Hanoi");
//		
//		//guest
//		GuestInfo guestInfo = new GuestInfo();
//		guestInfo.setFirstName("Tuyen Pham");
//		guestInfo.setLastName("Pham");
//		guestInfo.setEmail("tuyen.pham@mobivi.vn");
//		guestInfo.setMobile("0987125444");
//		guestInfo.setNationalId("38");
//		
//		info.getGuests().add(guestInfo);
//		
//		
//		msg.setBookingInfo(info);
//		//cardInfo
//		CardInfo cardInfo = new CardInfo();
//		cardInfo.setTypeCard("MasterCard");
//		cardInfo.setCardNumber("1111-2222-3333-1111");
//		cardInfo.setCardHotelName("Card MOBIVI Name");
//		cardInfo.setBankGaveCard("VietComBank");
//		cardInfo.setCountryOfIssuingBank("Việt Nam");
//		cardInfo.setCvcCode("123");
//		cardInfo.setExpiryDate("01-2020");
//		
//		AgodaStub stub = new AgodaStub(cardInfo);
//		try {
//			stub.doPay(msg);
//		
//		} catch(Exception exception) {
//			
//		}
		
		
		

	}

}
