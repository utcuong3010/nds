package com.mbv.airline.actors.vietjet;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mbv.airline.common.BookResult;
import com.mbv.airline.common.PayResult;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.util.SelenUtils;

public class VietjetStub {

	/***
	 * logger
	 */
	final static Logger logger = Logger.getLogger(VietjetStub.class);

	/**
	 * driver web
	 */
	private HtmlUnitDriver driver;

	/**
	 * account info
	 */
	private VietJetConfig vietjetConfig;

	/**
	 * time out for retrieves elements
	 */
	protected final long TIMEOUT = 300;//5*60minus 

	/**
	 * search fare info
	 */
	private VietjetSearch vietjetSearch = null;

	/**
	 * book itinerary
	 */
	private VietjetBook vietjetBook = null;

	/**
	 * pay itinerary
	 */
	private VietjetPay vietjetPay = null;

	/**
	 * retries booking
	 */
	private VietjetRetryBooking retryBooking = null;


	/**
	 * flag: check thread working
	 */
	private  AtomicBoolean isBusyFlag = new AtomicBoolean(false);

	/**
	 * get account Info
	 * 
	 * @param config
	 */
	public VietjetStub(final VietJetConfig vietjetConfig) {

		this.vietjetConfig = vietjetConfig;		
		//init implements
		vietjetSearch = new VietjetSearch(vietjetConfig);
		vietjetBook = new VietjetBook(vietjetConfig);
		vietjetPay = new VietjetPay(vietjetConfig);
		retryBooking = new VietjetRetryBooking(vietjetConfig);

		init();	//init application		
		scheduleKeepSession();//keep session
	}

	/***
	 * init webdriver and go to application at search page
	 */
	public void init() {
		
		isBusyFlag.set(true);

		initWebDriver();//init web driver
		try {
			doLogin();//dologin
		} catch (LoginException e) {

		}		
		isBusyFlag.set(false);
	}

	/***
	 * close driver
	 */
	public void detroyDriver() {
		if(driver != null) {
			driver.manage().deleteAllCookies();
			driver.quit();
			driver = null;
		}
	}

	/** 
	 * init webdriver for firefox
	 */
	private void initWebDriver() {	
		//destroy
		detroyDriver();
		// set user agent
		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0";
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("general.useragent.override", userAgent);
		DesiredCapabilities cap = DesiredCapabilities.firefox();	
		cap.setCapability(FirefoxDriver.PROFILE, profile);
		cap.setJavascriptEnabled(true);
		// new htmlunit driver
		driver  = new HtmlUnitDriver(cap);	
		//set implicied timeout
		driver.manage().timeouts().pageLoadTimeout(TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);

	}

	/**
	 * login page
	 * 
	 * @throws Exception
	 */
	protected void doLogin() throws LoginException {
		try {
			logger.info("[Login]-Starting...");
			
			driver.get(vietjetConfig.getLoginUrl());//goto login page then fillform
			//get form
			WebElement loginForm = SelenUtils.findElement(driver, By.id("SiteLogin"));

			SelenUtils.fillInputElement(loginForm, By.id("txtAgentID"), vietjetConfig.getUsername());
			SelenUtils.fillInputElement(loginForm, By.id("txtAgentPswd"), vietjetConfig.getPassword());

			SelenUtils.clickElement(loginForm, By.cssSelector("a.button"));//submit form

			SelenUtils.findElement(driver, By.id("AgentOptions"));//find next page
						
			//got to search page
			driver.get(vietjetConfig.getSearchUrl());

			logger.info("[Login]-End...");;

		}catch (Exception ex){
			logger.error("Login with error:" + ex);
			throw new LoginException("Login with error:"+ ex);
		}
	}

	/***
	 * search fare info
	 * 
	 * @param request
	 * @return
	 */
	public AirFarePriceInfos doSearch(SearchItineraryCmd request) throws Exception {
		AirFarePriceInfos result = new AirFarePriceInfos();
		isBusyFlag.set(true);
		try {
			result = vietjetSearch.doSearch(request, driver);
			//go back search page to ready next search
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}
			isBusyFlag.set(false);
		} catch (Exception ex) {
			//go back search page to ready next search
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}
			isBusyFlag.set(false);		
		} 
		return result;
	}

	/***
	 * 
	 * @return
	 */
	private boolean checkExpireSession() {
		boolean isExpired = false;
		for (String keyword: vietjetConfig.getSessionExpiredUrls()) {
			isExpired = driver.getCurrentUrl().contains(keyword);
			if(isExpired) break;
		}
		return isExpired;
	}

	/**
	 * process get reservation code
	 * 
	 * @param itinerary
	 * @return
	 */
	public BookResult doBook(AirItinerary itinerary) throws Exception{
		BookResult result = null;
		isBusyFlag.set(true);
		try {
			
			result = vietjetBook.doBook(itinerary, driver);
			//new driver
			isBusyFlag.set(false);

		} catch (Exception ex) {
			
			
			throw ex;

		} 
		return result;
	}

	/**
	 * process pay reservation code
	 * 
	 * @param itinerary
	 * @return
	 */
	public PayResult doPay(AirItinerary itinerary) throws Exception {
		PayResult result = null;
		isBusyFlag.set(true);
		try {
			result = vietjetPay.doPay(itinerary, driver);
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}		
			isBusyFlag.set(false);

		} catch (Exception ex) {
			
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}		
			isBusyFlag.set(false);
			
			throw ex;

		}
		return result;
	}


	/**
	 * verify payment info reservation code
	 * @param itinerary
	 * @return
	 */
	public boolean doVerifyPayment(String reservationCode) throws Exception{
		boolean result = false;
		isBusyFlag.set(true);
		try {

			result = vietjetPay.doVerifyPayment(reservationCode, driver);
			
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}		
			isBusyFlag.set(false);

		} catch (Exception ex) {
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}		
			isBusyFlag.set(false);
			
			
			throw ex;

		}	
		return result;
	}

	/**
	 * process retry bookingId
	 * 
	 * @param itinerary
	 * @return
	 */
	public void doRetrieveBooking(AirItinerary itinerary) throws Exception{
		isBusyFlag.set(true);
		try {
			
			retryBooking.doRetrieveBooking(itinerary, driver);	
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}		
			isBusyFlag.set(false);

		}catch (Exception ex) {
			if(checkExpireSession()) {
				doLogin();
			} else {
				driver.get(vietjetConfig.getSearchUrl());
			}		
			isBusyFlag.set(false);
			
			throw ex;
		}
	}

	/**
	 * request in time for keeping session
	 */
	private void scheduleKeepSession(){			
		Timer time = new Timer();
		ScheduledTask st = new ScheduledTask();		
		time.schedule(st, vietjetConfig.getSessionExpire()*60000, vietjetConfig.getSessionExpire()*60000);	//10mins
	}
	public class ScheduledTask extends TimerTask {
		Date now;
		public void run() {
			now = new Date();	
			logger.info("[Refresh]-Start Refreshing page with flag: " + isBusyFlag.get());
			if(isBusyFlag.get() == false){
				try {
					driver.navigate().refresh();
					logger.info("Current URL after refresh"+ driver.getCurrentUrl());
					if(checkExpireSession()) {
						doLogin();	
					}
				} catch (Exception ex) {
					logger.error("Refresh page with error:" + ex.getMessage());
				}
			}
			logger.info("[Refresh]-End Refreshing page with flag: " + isBusyFlag.get());
		}
	}

}