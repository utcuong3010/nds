package com.mbv.airline.actors.vietjet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.mbv.airline.common.PayResult;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.util.SelenUtils;

public class VietjetPay {

	/**
	 * logger
	 */
	final static Logger logger = Logger.getLogger(VietjetPay.class);

	/***
	 * contain all config properties
	 */
	protected VietJetConfig config;
	
	/***
	 * contructor for viet jet
	 * @param config
	 */
	public VietjetPay(VietJetConfig config) {
		this.config = config;
	}
	

	/**
	 * do pay reservation code
	 */
	public PayResult doPay(AirItinerary itinerary, HtmlUnitDriver driver) throws Exception {
		try {		
			PayResult result = null;
			logger.info("Step1: Search reservation code");
			searchReservationCode(itinerary.getTicketingInfo().getReservationCode(), driver);
			
			if(config.isPay4Testing()) {
				
				logger.info("Step2:The Paying is running on production:" + config.isPay4Testing());
				result = getReceiptForTesting(driver);				
			} else {						
				SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=addpayment]"));			
				logger.info("Step3:Choose PAYMENT Method");
				addPayment(driver);
				logger.info("Step4: Get RECEIPT Number");
				result = getReceiptNumber(driver);
			}
			return result;
			
		} catch(Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * step1:search reservation code(payment)
	 * 
	 * @param itinerary
	 * @param driver
	 * @throws Exception
	 */
	protected void searchReservationCode(String reservationCode, HtmlUnitDriver driver) throws Exception {
		try {
			//goto pay page
			logger.info("goto search reservation code page");
			driver.get(config.getPayUrl());
			
			SelenUtils.fillInputElement(driver, By.id("txtSearchResNum"), reservationCode);			
			//submit form
			SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=search]"));
			SelenUtils.clickElement(driver, By.cssSelector("a.button[href*=continue]"));//go next page
			
		} catch(Exception exception) {
			throw new Exception("Search reservation code with error:" + exception);
		}
	}
	
	/**
	 * step3: process payment method
	 * 
	 * @param driver
	 */
	private void addPayment(HtmlUnitDriver driver) throws Exception {		
		try {
			
			SelenUtils.clickElementContainValue(driver, By.cssSelector("input#lstPmtType"), "AG");//send payment later
			SelenUtils.clickElement(driver, By.cssSelector("form#AddPayment #buttonDisplay a.button"));
			
		} catch(Exception exception) {
			throw new Exception("Choose Payment method error:" + exception);
		}
	}

	/**
	 * get receipt number
	 * 
	 * @param driver
	 * @return
	 */
	private PayResult getReceiptNumber(HtmlUnitDriver driver) throws Exception {		
		try {
			SelenUtils.waitForElementExist(driver, By.cssSelector("form#Result div#results p.SuccessCaption"));		
			PayResult result = null;
			String receiptNumber = SelenUtils.getText(driver, By.cssSelector("form#Result div#results p.SuccessCaption"));
			String[] partReceipt = receiptNumber.split(":");
			if (!receiptNumber.isEmpty()) {
				result = new PayResult();
				result.setReceiptNumber(partReceipt[1].replaceAll("[^0-9]", "").trim());
			}		
			return result;
			
		} catch (Exception ex) {
			throw new Exception("Get Receipt Number error:" + ex);
		}	
	}
	
	/**
	 * do TEST :write htmlContent for pay
	 * @param driver
	 * @throws Exception
	 */
	private void writeFile(HtmlUnitDriver driver) throws Exception {
		try {
			String content = driver.getPageSource();		
			File file = new File("/home/phamtuyen/parse.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();	
			
		} catch (Exception ex) {
			throw ex;
		}
		
	}
	
	/***
	 * for TESTING TESTING TESTING
	 * @param driver
	 * @return
	 * @throws Exception
	 */
	private PayResult getReceiptForTesting(HtmlUnitDriver driver) throws Exception{
		PayResult result = null;
		try {
			SelenUtils.waitForElementExist(driver, By.cssSelector("#Edit > table:nth-child(27)"));
			String lbTotalChar = SelenUtils.getText(driver, By.cssSelector("#Edit > table:nth-child(27) tr:nth-child(1) td.grdChrInfo"));
			String lbBalanceDue = SelenUtils.getText(driver, By.cssSelector("#Edit > table:nth-child(27) tr:nth-child(3) td.grdChrInfo"));			
			if (Long.parseLong(lbTotalChar.replaceAll("[^0-9]", "").trim()) > 0
					&& Long.parseLong(lbBalanceDue.replaceAll("[^0-9]", "").trim()) > 0) {
				result = new PayResult();
				result.setReceiptNumber(lbTotalChar.replaceAll("[^0-9]", "").trim());
			}
			
			logger.info("Receipt for Testing: " + result.getReceiptNumber());
		} catch (Exception e) {
			throw e;
		}

		return result;
	}
	

	/**
	 * verify payment info reservation code
	 */
	public boolean doVerifyPayment(String reservationCode, HtmlUnitDriver driver) throws Exception {
		boolean result = false;
		try {
			logger.info("Step1: Search reservation code");
			searchReservationCode(reservationCode, driver);
			logger.info("Step2: Check Payment or not");
			result = checkResStatus(driver, reservationCode);
		} catch (Exception ex) {
			throw ex;
		}
		return result;
	}

	/**
	 * verify payment info reservation code
	 * step 4: check booking status
	 * 
	 * @param driver
	 * @return
	 */
	private boolean checkResStatus(HtmlUnitDriver driver,String reservationCode) throws Exception {
		boolean result = false;
		try {
			SelenUtils.waitForElementExist(driver, By.cssSelector("#Edit > table:nth-child(27)"));
			String totalChargeLabel = SelenUtils.getText(driver, By.cssSelector("#Edit > table:nth-child(27) tr:nth-child(1) td.grdChrInfo"));
			String totalPaymentLabel = SelenUtils.getText(driver, By.cssSelector("#Edit > table:nth-child(27) tr:nth-child(2) td.grdChrInfo"));			
			if (Long.parseLong(totalChargeLabel.replaceAll("[^0-9]", "").trim()) > 0
					&& Long.parseLong(totalPaymentLabel.replaceAll("[^0-9]", "").trim()) > 0)
				result = true;
			
			logger.info("Status verify payment [reservationCode = ]" + reservationCode +  " [STATUS] = " + result); 
			
		} catch (Exception ex) {
			throw ex;
		}
		return result;
	}

}
