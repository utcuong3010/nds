package com.mbv.ticketsystem.agoda.test;

import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ClasspathExtension;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Test {
	final static Logger logger = Logger.getLogger(Test.class);
	private static WebDriver driver = null;
	public static void main(String[] args) throws InterruptedException {	
		initWebdriver();
		driver.get("https://ameliaweb3.intelisys.ca/VietJet/sitelogin.aspx?lang=vi");
		Thread.sleep(5000);
		Date now = new Date();
		logger.info("begin :" + now);
		initCookies();
		login();	
		driver.manage().deleteAllCookies();
		driver.quit();
		//		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		//		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);	
		//		driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
		//		driver.get("https://ameliaweb3.intelisys.ca/VietJet/sitelogin.aspx?lang=vi");	
		//		Thread.sleep(7000);
		//		now = new Date();
		//		logger.info("begin :" + now);
		//		initCookies();
		//		login();

	}

	private static void login() throws InterruptedException {
		WebElement element = driver.findElement(By.cssSelector("#txtAgentID"));
		element.clear();
		element.sendKeys("AG38197121");
		System.out.println("finish 1 ");
		element = driver.findElement(By.cssSelector("#txtAgentPswd"));
		element.clear();
		element.sendKeys("Mobivi123456");
		System.out.println("finish 2 ");
		driver.findElement(By.xpath("//*[@id='SiteLogin']/div[3]/table/tbody/tr[4]/td/a")).click();
		System.out.println("finish 3 ");	
		menu();

	}

	private static void menu() {
		driver.findElement(By.cssSelector("#AgentOptions > table > tbody > tr:nth-child(3) > td:nth-child(1) > a")).click();	
		System.out.println("finish 4 ");	
		WebElement element = driver.findElement(By.cssSelector("html body div#wrapper div#wrapper-2 div#container div#main div.mid_900 form#Search table.midTable tbody tr td input#txtSearchResNum"));
		element.clear();
		element.sendKeys("19925644");
		System.out.println("finish 5 ");	
		driver.findElement(By.cssSelector("#Search > table > tbody > tr:nth-child(6) > td > a")).click();
		System.out.println("finish 6 ");	
		driver.findElement(By.cssSelector("#Search > table > tbody > tr:nth-child(10) > td > a")).click();	
		System.out.println("finish 7 ");	
		driver.findElement(By.cssSelector("#Edit > table:nth-child(38) > tbody > tr > td:nth-child(3) > a")).click();
		System.out.println("finish 8 ");	
		Date now = new Date();
		logger.info("complete :" + now);
		driver.quit();		
	}

	private static void initCookies() {
		Cookie cookie = new Cookie("key", "value");
		driver.manage().addCookie(cookie);
	}

	private static void initWebdriver(){
		//		String userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:35.0) Gecko/20100101 Firefox/35.0";
		//		FirefoxProfile profile = new FirefoxProfile();
		//		profile.setPreference("general.useragent.override", userAgent);	
		//		profile.setPreference("", "phamtuyen webdriver-profile");	
		//		profile.setPreference("webdriver.reap_profile", true);
//				DesiredCapabilities cap = DesiredCapabilities.firefox();							
		//		cap.setCapability(FirefoxDriver.PROFILE, profile);
		//		cap.setVersion("firefox");
//		cap.setBrowserName("htmlunit");
//		cap.setPlatform(Platform.LINUX);
		
		//		driver = new HtmlUnitDriver(cap);	

		System.setProperty("java.io.tmpdir", "/home/phamtuyen/123");
		System.setProperty("webdriver.reap_profile", "false");
//		System.setProperty("webdriver.firefox.useExisting", "true");
	
//		File extensions = new File("/home/phamtuyen/123", "/extensions/fxdriver@googlecode.com");
//
//		if (extensions.exists() && extensions.isDirectory()) {
//			return;
//		}
////		
//		ClasspathExtension extension = new ClasspathExtension(FirefoxProfile.class,
//				"/" + FirefoxProfile.class.getPackage().getName().replace(".", "/") + "/webdriver.xpi");
//		System.out.println(extension);
//		FirefoxProfile profile = new FirefoxProfile();
//		profile.addExtension("webdriver", extension);

		driver = new FirefoxDriver();
		driver.manage().window().setSize(new Dimension(800, 540));	
		File sysTemp = new File(System.getProperty("java.io.tmpdir"));	
		System.out.println("tmpDir Path: " + sysTemp.getAbsolutePath());
		//		
		driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);	
		driver.manage().timeouts().setScriptTimeout(180, TimeUnit.SECONDS);
	}


}
