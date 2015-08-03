package com.mbv.ticketsystem.agoda.test;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class payAgoda {
	// http://54.151.151.183:8181/hotelservice/itinerary
	final static Logger logger = Logger.getLogger(payAgoda.class);
//	private static HtmlUnitDriver driver = null;
	private static HtmlUnitDriver driver = null;
	private static DateFormat dateFormat_ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
	private static HashMap<String, String> location = new HashMap<String, String>();
	private static HashMap<String, String> cardInfo = new HashMap<String, String>();
	public static void main(String[] args) throws ParseException, InterruptedException, IOException {
//		HotelBooking bookingInfo = create("Hanoi Fantasea Hotel","945325"); 
//		Date now = new Date();
//		logger.info("begin :" + now); // Display current time
//		logger.info("initWebdriver");
//		initWebdriver();
//		logger.info("searchInfo");
//		searchInfo(bookingInfo);
//		logger.info("chooseRoom");
//		chooseRoom(bookingInfo);
//		logger.info("bookingInfo");
//		bookingInfo(bookingInfo); 		
//		logger.info("get agoda.com again");
//		now = new Date();
//		logger.info("complete :" + now); // Display current time
////		getAgodaAgain();
		
//		logger.info("paymentDetails");
//		paymentDetails(bookingInfo);
//		logger.info("getBookingInfo");
//		getBookingInfo();
	}

	private static void getAgodaAgain() throws ParseException, InterruptedException {	
//		HotelBooking bookingInfo = create("Hanoi 3B Hotel","686209"); 
//		getAgoda();
//		Thread.sleep(30000); 
//		Date now = new Date();
//		logger.info("begin :" + now); // Display current time
//		logger.info("searchInfo");
//		searchInfo(bookingInfo);
//		logger.info("chooseRoom");
//		chooseRoom(bookingInfo);
//		logger.info("bookingInfo");
//		bookingInfo(bookingInfo); 
//		now = new Date();
//		logger.info("complete :" + now); // Display current time
	}

	private static void getBookingInfo() {
//		deleteAllCookies();
	}

//	private static void paymentDetails(HotelBooking bookingInfo) {
//		waitElementLoad();	
//		selectElement(getMethodPayment(),"//*[@id='ddlPaymentMethod']");		
//		fillDataToInput("1111","//*[@id='txtCard_0']");
//		fillDataToInput("1111","//*[@id='txtCard_1']");
//		fillDataToInput("1111","//*[@id='txtCard_2']");
//		fillDataToInput("1111","//*[@id='txtCard_3']");
//		fillDataToInput("Hotel VN","//*[@id='txtHolderName']");
//		selectElement("10","//*[@id='ddlExpiryMonth']");
//		selectElement("2020","//*[@id='ddlExpiryYear']");
//		selectElement("1111","//*[@id='txtCvc']");				
//		fillDataToInput("Vietcom Bank","//*[@id='txtIssueBank']");
//		String continueXpath = "//*[@id='btnSubmit']";
//		String idSubmit = "btnSubmit";
//		clickButtonContinue(idSubmit,continueXpath); 
//	}

	private static void selectElement(String value,String xpathElement){
		Select methodPayment = new Select(driver.findElement(By.xpath(xpathElement)));	
		methodPayment.selectByValue(value);
	}

	private static void fillDataToInput(String data,String xpathElement){
		WebDriverWait wait = new WebDriverWait(driver, 180);
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathElement)));	
		element.clear();
		element.sendKeys(data);		
		new Actions(driver).moveToElement(element).perform();
	}

	@SuppressWarnings("rawtypes")
	private static String getMethodPayment(){
		List<WebElement> allMethod = driver.findElements(By.xpath("//*[@id='ddlPaymentMethod']/option"));
		for(WebElement method :  allMethod){
			String key = method.getText();
			String value = method.getAttribute("value");
			if(value != "0")
				cardInfo.put(key, value);				
		}
		String method = "1";
		Iterator iterator = cardInfo.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) iterator.next();
			if(mapEntry.getKey().equals("MasterCard")){
				method = mapEntry.getValue().toString();
				break;
			}				
		}	
		return method;
	}

//	private static void bookingInfo(HotelBooking bookingInfo) throws InterruptedException {
//		waitElementLoad();
//		Thread.sleep(5000);	
//		System.out.println("Booking Info");		
//		fillDataToInput(bookingInfo.getContactInfo().getFirstName(), "//*[@id='txtCntFirstName']"); 
//		fillDataToInput(bookingInfo.getContactInfo().getLastName(), "//*[@id='txtCntLastName']");
//		executeJavascript(bookingInfo.getContactInfo().getEmail(), "//*[@id='txtEmail']", "txtEmail"); 
//		executeJavascript(bookingInfo.getContactInfo().getEmail(), "//*[@id='txtConfirmEmail']", "txtConfirmEmail"); 
//		fillDataToInput(bookingInfo.getContactInfo().getMobile(), "//*[@id='txtPhoneNo']");
//		if(bookingInfo.getContactInfo().getCountryOfPassport().equals("Vietnam"))
//			selectElement("38", "//*[@id='ddlCntctCountry']"); 
//		else
//			selectElement(getContryInformation(bookingInfo), "//*[@id='ddlCntctCountry']"); 		
//		checkElementBookingInfo();
//		checkElementBookingInfo(bookingInfo);		
//		String continueXpath = "//*[@id='btnContinue']";
//		String idSubmit = "btnContinue";
//		clickButtonContinue(idSubmit,continueXpath); 
//		Thread.sleep(1500);
//		checkElementBookingInfo(); 
//	}
//
//	private static void executeJavascript(String value,String xpathElement,String element){
//		WebDriverWait wait = new WebDriverWait(driver, 180);
//		JavascriptExecutor executor = (JavascriptExecutor)driver;
//		WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathElement)));
//		email.clear();
//		executor.executeScript("document.getElementById('"+ element +"').value = '"+ value +"';$('#txtEmail').trigger('blur');", email);
//		new Actions(driver).moveToElement(email).perform();
//	}
//
//	private static void clickButtonContinue(String idSubmit,String continueXpath){
//		JavascriptExecutor executor = (JavascriptExecutor)driver;
//		WebElement buttonContinue = driver.findElement(By.xpath(continueXpath));		
//		executor.executeScript("$('#"+ idSubmit +"').trigger('click');", buttonContinue);
//	}
//
//	private static void checkElementBookingInfo(HotelBooking bookingInfo){	
//		String email = driver.findElement(By.xpath("//*[@id='txtEmail']")).getAttribute("class"); 
//		if(chechElement(email) == false)
//			fillAgain(bookingInfo.getContactInfo().getEmail(),"//*[@id='txtEmail']");
//
//		String comfirmEmail = driver.findElement(By.xpath("//*[@id='txtConfirmEmail']")).getAttribute("class"); 
//		if(chechElement(comfirmEmail) == false)
//			fillAgain(bookingInfo.getContactInfo().getEmail(),"//*[@id='txtConfirmEmail']");
//	}
//
//	private static boolean chechElement(String infoString){
//		return infoString.contains("valid"); 
//	}
//
//	private static void fillAgain(String field,String elementXpath){
//		WebDriverWait wait = new WebDriverWait(driver, 120);
//		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
//		element.clear();
//		element.sendKeys(field);		
//		new Actions(driver).moveToElement(element).perform();	
//	}
//
//	@SuppressWarnings("rawtypes")
//	private static String getContryInformation(HotelBooking bookingInfo){
//		List<WebElement> allCountry = driver.findElements(By.xpath("//*[@id='ddlCntctCountry']/option"));
//		for(WebElement country :  allCountry){
//			String key = country.getAttribute("name");
//			String value = country.getAttribute("value");
//			if(value != "0")
//				location.put(key, value);				
//		}
//		String valueCountry = "38";
//		Iterator iterator = location.entrySet().iterator();
//		while (iterator.hasNext()) {
//			Map.Entry mapEntry = (Map.Entry) iterator.next();
//			if(mapEntry.getKey().equals(bookingInfo.getContactInfo().getCountryOfPassport())){
//				valueCountry = mapEntry.getValue().toString();
//				break;
//			}				
//		}
//		return valueCountry;
//	}
//
//	private static void checkElementBookingInfo() {
//		String fn = driver.findElement(By.xpath("//*[@id='txtCntFirstName']")).getAttribute("class"); 
//		logger.info("fName: " +fn);
//		String ln = driver.findElement(By.xpath("//*[@id='txtCntLastName']")).getAttribute("class"); 
//		logger.info("fName: " +ln);
//		String mail = driver.findElement(By.xpath("//*[@id='txtEmail']")).getAttribute("class"); 
//		logger.info("Mail: " + mail);
//		String cmail = driver.findElement(By.xpath("//*[@id='txtConfirmEmail']")).getAttribute("class"); 
//		logger.info("Cmail: " + cmail);
//		String phone = driver.findElement(By.xpath("//*[@id='txtPhoneNo']")).getAttribute("class"); 
//		logger.info("Phone: " + phone);
//		String country = driver.findElement(By.id("ddlCntctCountry")).getAttribute("class");
//		logger.info("Country: " + country);
//
//		String thirdstep = driver.findElement(By.id("secondstep")).getAttribute("class");	
//		logger.info("secondstep: " + thirdstep);  
//	}
//	
//	private static void getAgoda(){
//		deleteAllCookies();
//		driver.get("http://www.agoda.com");		
//		initCookies();
//	}
//
//	private static void initWebdriver() throws IOException{		
//		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0";
//		FirefoxProfile profile = new FirefoxProfile();
////		profile.setPreference("webdriver.load.strategy", "unstable");
////		profile.setPreference("webdriver.reap_profile", "false");
//		profile.setPreference("general.useragent.override", userAgent);
//		DesiredCapabilities cap = DesiredCapabilities.firefox();	
//		cap.setCapability(FirefoxDriver.PROFILE, profile);		
//		cap.setVersion("firefox");
//		cap.setBrowserName("htmlunit");
//		cap.setJavascriptEnabled(true);
//		driver = new HtmlUnitDriver(cap);	
//		
////		driver = new FirefoxDriver(profile);		
////		driver = new FirefoxDriver();
//		waitElementLoad();	
//		logger.info("get agoda");
//		driver.get("http://www.agoda.com");
//		logger.info("get finish agoda");
//		initCookies();
//	}
//
//	private static void initCookies(){
//		Cookie cookie = new Cookie("key", "value");
//		driver.manage().addCookie(cookie);
//	}
//
//	private static void deleteAllCookies(){ 
//		driver.manage().deleteAllCookies();
////		driver.quit(); // all 
////		driver.close(); // one
//	}
//
//	private static void waitElementLoad(){
//		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
//		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);	
//		driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
//	}
//
//
//	private static void searchInfo(HotelBooking bookingInfo) {
//		fillDataToInput(bookingInfo.getTravelInfo().getNameHotel() + " " + bookingInfo.getTravelInfo().getDestinationName(),"//*[@id='SearchInput']");
//		selectElement(""+bookingInfo.getTravelInfo().getOccupancies(), "//*[@id='NightCount']"); 
//		logger.info("finish: 1");
//		String arrivalDate = dateFormat_ddMMyyyy.format(bookingInfo.getTravelInfo().getArrivalDate());
//		selectElement(arrivalDate.substring(3), "//*[@id='CheckInMonthYear']"); 
//		logger.info("finish: 2");
//		selectElement(arrivalDate.substring(0, 2), "//*[@id='CheckInDay']"); 
//		logger.info("finish: 3");
////		JavascriptExecutor executor = (JavascriptExecutor)driver;
////		List<WebElement> elements = driver.findElements(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/div/form/fieldset/dl"));
////		int indexdl = 5;
////		if(elements.size() > 5)
////			indexdl = 9;
////		WebElement buttonSearch = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[1]/div[2]/div/div/form/fieldset/dl["+indexdl+"]/dd/input"));
////		executor.executeScript("$('form.search-form dl.form-actions input.submit').click();", buttonSearch);
////		System.out.println("finish: 4");
//		
//		WebElement buttonSearch = driver.findElement(By.cssSelector("html body.home.geo div.wrapper.home-page div.body.body-home div.bg-home-panel div.rotator-wrapper.rotator-text div.search-panel-wrapper div.search-panel form.search-form.calendar-form fieldset dl.form-actions dd input.submit.submit-primary"));
//		logger.info("finish: 4");
//		buttonSearch.click();
//		logger.info("finish: 5");
////		executor.executeScript("$('html body.home.geo div.wrapper.home-page div.body.body-home div.bg-home-panel div.rotator-wrapper.rotator-text div.search-panel-wrapper div.search-panel form.search-form.calendar-form fieldset dl.form-actions dd input.submit.submit-primary').click();", buttonSearch);
////		System.out.println("finish: 5");
////		driver.findElement(By.cssSelector("html body.home.geo div.wrapper.home-page div.body.body-home div.bg-home-panel div.rotator-wrapper.rotator-text div.search-panel-wrapper div.search-panel form.search-form.calendar-form fieldset dl.form-actions dd input.submit.submit-primary")).click();
////		System.out.println("finish: 5");
//	}
//
//	private static void chooseRoom(HotelBooking bookingInfo) {
//		logger.info("Choose Room");
//		waitElementLoad();
//		List<WebElement> allRows = driver.findElements(By.xpath("//*[@id='ctl00_ctl00_MainContent_ContentMain_RoomTypesListGrid_AB1771_upRoomTypes']/div[1]/table/tbody/tr"));
//		int numberOfHotel = allRows.size()-1;
//		if(numberOfHotel > 3)
//			driver.findElement(By.xpath("//*[@id='ctl00_ctl00_MainContent_ContentMain_RoomTypesListGrid_AB1771_lbMoreRoomPlus']")).click();
//		System.out.println("finish: 5");
//		int chooseRoom = 0;
//		for(int i=0;i < numberOfHotel;i++){
//			String roomId = driver.findElement(By.xpath("//*[@id='button_room"+ i +"']")).getAttribute("roomtypeid");
//			if(roomId.equals(bookingInfo.getRoomInfo().getRoomId())){
//				chooseRoom = i;
//				break; 
//			}			
//		}
//		logger.info("ChooseRoom: " + chooseRoom); 
//		if(bookingInfo.getRoomInfo().isExtrabed()){
//			driver.findElement(By.xpath("//*[@id='lnkExtrabed"+ chooseRoom +"']")).click();
//			WebElement elementToClick = driver.findElement(By.xpath("//*[@id='chkExtrabed"+ chooseRoom +"-0']"));
//			elementToClick.click();
//		}		
//		logger.info("finish: 6");
//		selectElement(""+bookingInfo.getRoomInfo().getNumberOfRooms(), "//*[@id='room"+ chooseRoom +"']"); 
//		driver.findElement(By.xpath("//*[@id='button_room"+ chooseRoom +"']")).click();
//		logger.info("finish: 7");
//	}
//
//	private static HotelBooking create(String hotelName,String roomId) throws ParseException{
//		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
//		HotelBooking bookingInfo = new HotelBooking();
//		HotelTravelInfo travelInfo = new HotelTravelInfo();
//		travelInfo.setNameHotel(hotelName); // 
//		travelInfo.setDestinationName("Hanoi"); 
//		travelInfo.setArrivalDate(dateFormat.parse("2015-03-18T00:00:00.000Z"));
//		travelInfo.setDepartureDate(dateFormat.parse("2015-03-19T00:00:00.000Z")); 
//		travelInfo.setOccupancies(1);  
//		travelInfo.setVendor("AG");
//
//		HotelRoomInfo roomInfo = new HotelRoomInfo();
//		roomInfo.setRoomId(roomId); 
//		roomInfo.setExtrabed(false);
//		roomInfo.setNumberOfRooms(1);
//
//		HotelContactInfo contactInfo = new HotelContactInfo();
//		contactInfo.setFirstName("Truong");
//		contactInfo.setLastName("Cuong");
//		contactInfo.setEmail("cuong.truong@mobivi.vn"); 
//		contactInfo.setMobile("01264143081");
//		contactInfo.setCountryOfPassport("Vietnam");
//
//		bookingInfo.setTravelInfo(travelInfo);
//		bookingInfo.setRoomInfo(roomInfo); 
//		bookingInfo.setContactInfo(contactInfo);
//		return bookingInfo;
//	}

}
