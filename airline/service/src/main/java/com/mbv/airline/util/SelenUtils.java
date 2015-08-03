package com.mbv.airline.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelenUtils {

	/***
	 * logger
	 */
	final static Logger logger = Logger.getLogger(SelenUtils.class);

	/**
	 * get source page
	 * @param driver
	 * @return
	 */
	public String getHtmlContent(WebDriver driver){
		return driver.getPageSource();
	}

	/**
	 * delete all cookies
	 * @param driver
	 */
	public static void deleteAllCookies(WebDriver driver){
		driver.manage().deleteAllCookies();
		//driver.quit();
	}

	/**
	 * wait page load
	 * @param driver
	 * @param timout
	 */
	public static void waitElementLoad(WebDriver driver,long timout){
		driver.manage().timeouts().pageLoadTimeout(timout, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(timout, TimeUnit.SECONDS);			
	}

	/**
	 * click element button use javascript
	 * @param driver
	 * @param by
	 * @param element
	 * @param timeout
	 */
	public static void clickElementByJS(final WebDriver driver, final By by,String element,long timeout){
		try {
			JavascriptExecutor executor = (JavascriptExecutor)driver;
			WebElement button = driver.findElement(by);		
			executor.executeScript("$('"+ element +"').trigger('click');", button);
		} catch (Exception exeption) {
			logger.error("Can not find element with [" + by + "] " + exeption.getMessage());
		}
	}



	/**
	 * awating element display
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public static WebElement waitForElementExist(final WebDriver driver,final By by) throws Exception {
		try {	
			
			return driver.findElement(by);
			
		} catch(Exception exeption) {
			logger.error("Can not find element with [" + by + "] " + exeption);
			throw exeption;
		}
	}

	/***
	 * 
	 * @param driver
	 * @param element
	 */
	public static void removeInputReadOnly(final WebDriver driver, final WebElement element){
		try {		

			((JavascriptExecutor) driver).executeScript("arguments[0].removeAttribute('readonly','')",element);

		} catch (Exception exeption) {
			logger.error("Can not find element with [" + element + "] " + exeption.getMessage());
		}	
	}

	/**
	 * get text
	 * @param driver
	 * @param by
	 * @param timeout
	 * @return
	 */
	public static String getText(final SearchContext context, final By by) throws Exception{
		try {
			WebElement element = context.findElement(by);	
			logger.info("Find Element [" + by + "] by Id");
			return element.getText();
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}
	
	public static WebElement getElement(final SearchContext context, final By by) throws Exception{
		try {
			WebElement element = context.findElement(by);	
			logger.info("Find Element [" + by + "] by Id"); 
			return element;
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}

	/** 
	 * 
	 * @param context
	 * @param by
	 * @return
	 */
	public static WebElement findElement(final SearchContext context, final By by) throws Exception{
		try {
			WebElement ele = context.findElement(by);

			logger.info("Find Element with [" + by + "]");
			return ele;

		} catch (Exception exeption) {
			logger.error("Can not find element with [" + by + "] " + exeption.getMessage());			
			throw exeption;
		}
	}

	/***
	 * find element in context
	 * @param context
	 * @param by
	 * @param value
	 * @throws Exception
	 */
	public static void fillInputElement(final SearchContext context, By by, String value) throws Exception {
		try {
			//get element from by
			WebElement element = context.findElement(by);
			element.clear();
			element.sendKeys(value);	
			logger.info("Fill Element[" + by + " ] value=" + value);
		} catch(NoSuchElementException exception) {
			logger.error("Can not fill Element[" + by + " ] with the error:" + exception + "");
			throw exception;
		} 
	}

	/** 
	 * 
	 * @param context
	 * @param by
	 * @return
	 */
	public static void clickElement(final SearchContext context, final By by) throws Exception{
		try {
			WebElement element = context.findElement(by);
			element.click();	
			logger.info("Click Element:[" + by + " ] ");
		} catch (Exception exeption) {
			throw new Exception("Can not find element with [" + by + "] " + exeption.getMessage());			
		}
	}

	/** 
	 * 
	 * @param context
	 * @param by
	 * @return
	 */
	public static void doubleClickElement(final SearchContext context, final By by) throws Exception{
		try {
			WebElement element = context.findElement(by);
			element.click();	
			element.click();
			logger.info("Double Click Element:[" + by + " ] ");
		} catch (Exception exception) {
			throw new Exception("Can not find element with [" + by + "] " + exception.getMessage());	
		}
	}
	/**
	 * select drop down by value
	 * @param value
	 * @param element
	 */
	public static void selectElementByValue(final SearchContext context, final By by,String value) throws Exception{
		try {
			Select dropdown = new Select(context.findElement(by));	
			dropdown.selectByValue(value);
			logger.info("Select Element:[" + by + "] By value =" + value);
		} catch (Exception exeption) {
			logger.error("Can not find element with [" + by + "] " + exeption); 
			throw exeption;
		}
	}


	/**
	 * select value dropdow by text
	 * @param driver
	 * @param by
	 * @param text
	 * @param timeout
	 */
	public static void selectElementByText(final SearchContext context, final By by,String text) throws Exception{
		try {
			Select dropdown = new Select(context.findElement(by));	
			dropdown.selectByVisibleText(text);
			logger.info("Select Element:[" + by + "] By Text");
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}

	/***
	 * 
	 * @param context
	 * @param by
	 * @param searchText
	 * @throws Exception
	 */
//	public static void selectElementContainText(final SearchContext context, final By by,String searchText) throws Exception{
//		try {			
//			WebElement select = context.findElement(by);
//			List<WebElement> options = select.findElements(By.tagName("option"));
//			for(WebElement option : options){
//				if(StringUtils.contains(option.getText(), searchText)) {			
//					option.click();
//					break;
//				}
//			}
//			logger.info("Select Element:[" + by + "] By Contain Text[" + searchText + "]");
//		} catch (Exception exception) {
//			logger.error("Can not find element with [" + by + "] " + exception);
//			throw exception;
//		}
//	}
	
	/***
	 * 
	 * @param context
	 * @param by
	 * @param searchText
	 * @throws Exception
	 */
	public static void selectElementContainText(final WebDriver driver, final By by,String searchText) throws Exception{
		try {
			WebElement ele = driver.findElement(by);
			Select select = new Select(ele);			
			String value = "";		
			List<WebElement> options = select.getOptions();
			for(WebElement option : options){
				if(StringUtils.contains(option.getText(), searchText)) {			
					value = option.getAttribute("value");					
					break;
				}
			}							
			select.selectByValue(value);	
			logger.info("Select Element:[" + by + "] By Contain Text[" + searchText + "] after changed[" + select.getFirstSelectedOption().getText() +"]");
			
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}
	
	
	/***
	 * 
	 * @param context
	 * @param by
	 * @param index dropdown
	 * @throws Exception
	 */
	public static void selectElementByIndex(final SearchContext context, final By by,int index) throws Exception{
		try {									
			WebElement element = context.findElement(by);	//must using driver find				
			Select dropdown = new Select(element);
			dropdown.selectByIndex(index);	
			logger.info("Select Element:[" + by + "] By Index [ " + index +"] contain Text[" + dropdown.getFirstSelectedOption().getText() + "]");
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}


	/**
	 * fill value to input by javascript
	 * @param elementInput
	 * @param datetime
	 */
	public static void fillInputElementByJS(final WebDriver driver, final By by,String value) throws Exception{
		try {

			WebElement element = driver.findElement(by);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value = '"+value+"';", element);				
			logger.info("Fill Element:[" + by + "] By JS");
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}			
	}

	/***
	 * 
	 * @param context
	 * @param by
	 * @param isCheck
	 * @throws Exception
	 */
	public static void checkBoxElement(final SearchContext context, final By by,boolean isCheck) throws Exception{
		try {			
			WebElement element = context.findElement(by);
			if(isCheck) {
				if(!element.isSelected()) {
					element.click();
				}		
			} else {//uncheck
				if(element.isSelected()) {
					element.click();
				}
			}
			logger.info("CheckBox Element:[" + by + "]");
		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}

	public static void clickElementContainValue(final SearchContext context, final By by,String keyword) throws Exception{
		try {			
			WebElement element = null;
			List<WebElement> list = context.findElements(by);
			for(WebElement ele :list) {
				String value = ele.getAttribute("value");				
				if(value.contains(keyword)) {
					element = ele;
					break;
				}
			}
			logger.info("Find Element:[" + by + "] contain value["+ keyword +"]");

			//click
			element.click();

		} catch (Exception exception) {
			logger.error("Can not find element with [" + by + "] " + exception);
			throw exception;
		}
	}
	
	
	
	/***
	 * 
	 * @param driver
	 * @param element
	 */
	public static void waitUntilVisible(WebDriver driver, WebElement element){
		WebDriverWait waiting = new WebDriverWait(driver, 10);
	    waiting.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * 
	 * @param driver
	 * @param locator
	 */
	public static void waitUntilClickable(WebDriver driver, By locator){
		WebDriverWait waiting = new WebDriverWait(driver, 10);
	    waiting.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	
	public static void hrefClickTest(final WebDriver driver, final By locator) {
		Actions builder = new Actions(driver);
		WebElement link = driver.findElement(locator);
		waitUntilVisible(driver, link);
		builder.moveToElement(link).build().perform();		
		
		waitUntilClickable(driver, locator);
		
		link.click();
		
		System.err.println("Link:" + link);
	}	

}
