package com.mbv.hotel.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
public class SelenUtils {
	
	/***
	 * 
	 */
	private static final Logger logger = Logger.getLogger(SelenUtils.class);
	
	/***
	 * retry for fill form
	 */
	private static int retry = 10;
	
	/**
	 * time for sleeping in mili-seconds
	 */
	private static int TIME_SLEEP = 5000;
	
	/***
	 * get element within timeout
	 * @param driver
	 * @param By by
	 * @param timeout
	 * @return
	 * @throws Exception 
	 */
	public static WebElement waitForElementExist(final WebDriver driver,final By by , long timeout) throws Exception {
		try {	
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			WebElement element = wait.until(new Function<WebDriver, WebElement>(){
				 public WebElement apply(WebDriver driver) {					 	
			            return driver.findElement(by);
			        }
			});
			return element;
		} catch(Exception exeption) {
			logger.error("Can not find element with [" + by + "] " + exeption);
			throw exeption;
		}
		
		
		
	}
	
	/***
	 * list elements
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return
	 * @throws Exception 
	 */
	public static List<WebElement> waitForElementsExist(final WebDriver driver,final By by ,long timeout) throws Exception {
		
		try {	
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			List<WebElement> elements = wait.until(new Function<WebDriver, List<WebElement>>(){
				 public List<WebElement> apply(WebDriver driver) {
			            return driver.findElements(by);
			        }
			});
			return elements;
		} catch(Exception exeption) {
			logger.error("Can not find elements with [" + by + "] " + exeption);
			throw exeption;
		}
		
	}
	
	/***
	 * 
	 * @param element
	 * @param by
	 * @param value
	 */
	public static void selectDropdownByValue(final WebElement element, final String value) {
		try {
//			boolean filled = false;
//			int countRetry = 0;
//			while(!filled) {
				//clear 
//				element.sendKeys(Keys.SPACE);
				
//				Select selectElement = new Select(element);
//				selectElement.selectByValue(value);
//				//check filled or retry
//				filled = StringUtils.equalsIgnoreCase(selectElement.getFirstSelectedOption().getAttribute("value"), value);
//				countRetry++;
//				if(countRetry == retry) {
//					filled = true;
//				} else if(!filled){
//					Thread.sleep(TIME_SLEEP);	
//				}
//				
//				filled = true;
				Select selectElement = new Select(element);
				selectElement.selectByValue(value);
//			}
		}catch(NoSuchElementException ex) {
			logger.error("Can not find Element[" + element + " ] with the error:" + ex + "");
			throw ex;
		} 
//		catch (InterruptedException e) {
//			// TODO: handle exception
//			logger.error("Error with "+ e);
//		}
	}
	/**
	 * 
	 * @param element
	 * @param value
	 */
	public static void selectDropdownByText(final WebElement element, final String value) {
		try {
//			boolean filled = false;
//			int countRetry = 0;
//			while(!filled) {
//				element.sendKeys(Keys.SPACE);
				Select selectElement = new Select(element);
				selectElement.selectByVisibleText(value);
//				//check filled or retry
//				filled = StringUtils.equalsIgnoreCase(selectElement.getFirstSelectedOption().getText(), value);
//				countRetry++;
//				if(countRetry == retry) {
//					filled = true;
//				} else if(!filled){
//					Thread.sleep(TIME_SLEEP);	
//				}
//			}
			
		}catch(UnexpectedTagNameException ex) {
			logger.error("Can not find Element[" + element + " ] with the error:" + ex + "");
			throw ex;
		} 
		
	}
	/****
	 * 
	 * @param form
	 * @param by
	 * @param value
	 * @throws Exception 
	 */
	public static void fillElement(final WebDriver driver, By by, String value, long timeout) throws Exception {
		try {
			boolean filled = false;
			int countRetry = 0;
			while(!filled) {
				//get element from by
				WebElement element = SelenUtils.waitForElementExist(driver, by, timeout);
				element.click();
				element.clear();
				element.sendKeys(value);
				//get value
				filled = StringUtils.equalsIgnoreCase(element.getAttribute("value"), value) ;
				countRetry++;
				if(countRetry == retry) {
					filled = true;
				} else if(!filled){
					Thread.sleep(TIME_SLEEP);	
				}
				
			}
		} catch(NoSuchElementException exception) {
			logger.error("Can not fill Element[" + by + " ] with the error:" + exception + "");
			throw exception;
		} catch (InterruptedException e) {
			logger.error("Error with "+ e);
		}
	}
}
