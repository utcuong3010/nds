package com.mbv.airline.actors.vietjet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirFarePriceInfo;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirFarePriceOption;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirPassengerTypePrice;
import com.mbv.airline.util.SelenUtils;

public class VietjetSearch {
	
	/***
	 * logger
	 */
	final static Logger logger = Logger.getLogger(VietjetSearch.class);
	
	
	protected final VietJetConfig config;
	
	
	public VietjetSearch(VietJetConfig vietJetConfig) {

		config = vietJetConfig;
	}
	
	/***
	 * do search fare info
	 */
	
	public AirFarePriceInfos doSearch(SearchItineraryCmd request,HtmlUnitDriver driver) throws Exception{	
		AirFarePriceInfos result = null;
		try{		
			logger.info("Step1: FILL FORM SEARCH....");
			fillSearchForm(request,driver);		
			logger.info("Step2: PROCESS EXTRACT HTML(AirFareInfo)");
			result = extractAirFareInfo(request,driver);
		}catch(Exception ex){
			throw ex;
		}			
		return result;
	}
	
	/***
	 * step 3: fill form search
	 * @param searchCommand
	 * @throws Exception
	 */
	protected void fillSearchForm(SearchItineraryCmd searchCommand,HtmlUnitDriver driver) throws Exception{
		
		try {
			//get form 
			WebElement searchForm = SelenUtils.findElement(driver,By.id("Flight"));
			
			if(searchCommand.getOriginDestinationInfos().size()==1) {
				SelenUtils.clickElement(searchForm, By.id("chkRoundTrip1"));
			} else {
				SelenUtils.clickElement(searchForm, By.id("chkRoundTrip0"));
			}			
			
			SelenUtils.selectElementByValue(searchForm,  By.id("lstOrigAP"), searchCommand.getOriginDestinationInfos().get(0).getOriginCode());
			SelenUtils.selectElementByValue(searchForm,  By.id("lstDestAP"), searchCommand.getOriginDestinationInfos().get(0).getDestinationCode());

			SelenUtils.removeInputReadOnly(driver, searchForm.findElement(By.id("departure1")));
			SelenUtils.fillInputElementByJS(driver, By.id("departure1"), 
							DateUtils.format(searchCommand.getOriginDestinationInfos().get(0).getDepartureDate(),
							DateUtils.DD_MM_YYYY)); 
					
			if(searchCommand.getOriginDestinationInfos().size()==2) {
				SelenUtils.removeInputReadOnly(driver, searchForm.findElement(By.id("departure2")));
				SelenUtils.fillInputElementByJS(driver, By.id("departure2"), 
								DateUtils.format(searchCommand.getOriginDestinationInfos().get(1).getDepartureDate(),
								DateUtils.DD_MM_YYYY));
			}
						
			//fill quantities
			SelenUtils.fillInputElement(searchForm, By.id("txtNumAdults"), String.valueOf(searchCommand.getPassengerQuantity(AirPassengerType.ADT))); 							
			SelenUtils.fillInputElement(searchForm, By.id("txtNumChildren"), String.valueOf(searchCommand.getPassengerQuantity(AirPassengerType.CHD)));		
			SelenUtils.fillInputElement(searchForm, By.id("txtNumInfants"), String.valueOf(searchCommand.getPassengerQuantity(AirPassengerType.INF)));
		
				
//			SelenUtils.clickHrefElement(searchForm,  By.cssSelector("a.button_sb.leftbutton"), driver.getCurrentUrl(), driver);
			SelenUtils.clickElement(searchForm,  By.cssSelector("a.button_sb.leftbutton"));
							
					
		} catch (Exception ex) {
			throw new Exception("FILL SEARCH FORM DATA WITH ERROR + "+ ex.getMessage());
		}
		
	}
	
	/**
	 *  step 4: extract Air fare info
	 * @param searchCommand
	 * @return : AirFarePriceInfos
	 * @throws Exception 
	 */

	public AirFarePriceInfos extractAirFareInfo(SearchItineraryCmd searchCommand,HtmlUnitDriver driver) throws Exception {
		// wait elements load
		SelenUtils.waitForElementExist(driver, By.id("TravelOptions"));
		SelenUtils.waitForElementExist(driver, By.cssSelector("#contentwsb div.title_operatedby span.title_operatedby_VJ"));
		
		String htmlContent = driver.getPageSource();		
		AirFarePriceInfos result = new AirFarePriceInfos();	
		try {
			if(htmlContent == null)
				logger.error("MESSAGE ERROR (SEARCH): HTMLCONTENT NULL"); 
			Document doc = Jsoup.parse(htmlContent, "UTF-8");
			Elements dataTable = doc.select("#contentwsb");
			Elements flightTable = dataTable.select("table.TrvOptGrid");
			for (Element incFlightTable : flightTable) {
				String siteDate = incFlightTable.select("b").get(0).html();					
				Elements exTickets = incFlightTable.select("tr[class^=gridFlight][id^=gridTravelOpt]");
				for (Element exTicket : exTickets) {
					try {						
						AirFareInfo fareInfo = new AirFareInfo();
						fareInfo.setVendor("VJ");					
						Elements partElements = exTicket.select("td.SegInfo");										
						// Thoi gian xuat phat (khoi~ hanh)
						Element segment = partElements.get(1);
						String data = segment.text();					
						
						fareInfo.setDepartureDate(DateUtils.parse(siteDate + " " + data.substring(0, 5),DateUtils.DD_MM_YYYY_EEE_HH_MM));
						fareInfo.setOriginCode(data.substring(6, 9));
						segment = partElements.get(2);
						data = segment.text();						
						fareInfo.setArrivalDate(DateUtils.parse((siteDate + " " + data.substring(0, 5)),DateUtils.DD_MM_YYYY_EEE_HH_MM));
						fareInfo.setDestinationCode(data.substring(6, 9));
						fareInfo.setFlightCode(partElements.get(3).text().substring(0, 5));
						
						// Initialize PriceOption List
						List<AirFarePriceOption> priceOptionList = new ArrayList<AirFarePriceOption>();
						Elements priceOptions = exTicket.select("table.FaresGrid");
						for (Element option : priceOptions) {
							Elements prices = option.select("td[id^=gridTravelOpt]");						
							for (Element price : prices) {
								AirFarePriceOption priceOption = new AirFarePriceOption();
								priceOption.setReference(price.select("input[id^=gridTravelOpt]").val());
								priceOption.setClassCode(priceOption.getReference().split("[,_]")[1]);
								priceOption.setClassName(priceOption.getReference().split("[,_]")[2]);								
								
								long fare = Long.parseLong(price.select("#fare").first().val().replace(".", "-").split("-")[0].replaceAll("[^0-9]", "").trim());
								long fare_taxes = Long.parseLong(price.select("#fare_taxes").first().val().replace(".", "-").split("-")[0].replaceAll("[^0-9]", "").trim());
								long charges = Long.parseLong(price.select("#charges").first().val().replace(".", "-").split("-")[0].replaceAll("[^0-9]", "").trim());
								
								int numADT = searchCommand.getPassengerQuantity(AirPassengerType.ADT);
								int numCHD = searchCommand.getPassengerQuantity(AirPassengerType.CHD);
								int numINF = searchCommand.getPassengerQuantity(AirPassengerType.INF);
								long zWhat = 10 * fare_taxes - fare;
								long axWhat = 2 * (charges - (numADT + numCHD) * zWhat) / (2 * numADT + numCHD);

								priceOption.add(new AirPassengerTypePrice(AirPassengerType.ADT, fare + fare_taxes + axWhat + zWhat));
								if (numCHD > 0) {
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.CHD, fare + fare_taxes + axWhat / 2 + zWhat));
								}
								if (numINF > 0) {
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.INF, 0));
								}
								priceOptionList.add(priceOption);
							}
						}						
						AirFarePriceInfo farePriceInfo = new AirFarePriceInfo();
						farePriceInfo.setFareInfo(fareInfo);
						farePriceInfo.setPriceOptions(priceOptionList);
						result.add(farePriceInfo);
					} catch (Exception ex) {						
						logger.error("PARSER RESULT FOR SEARCH WITH ERROR: " +ex.getMessage());
					}
				}
			}
		} catch (Exception ex) {
			throw new Exception("PARSER RESULT FOR SEARCH WITH ERROR: " + ex.getMessage());
			
		}		
		return result;
	}
}
