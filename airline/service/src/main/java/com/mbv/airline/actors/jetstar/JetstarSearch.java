package com.mbv.airline.actors.jetstar;

import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirFarePriceInfo;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirFarePriceOption;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirPassengerTypePrice;
import com.mbv.airline.common.info.OriginDestinationInfo;
import com.mbv.airline.util.JsoupExecute;

/**
 * 
 * @author phamtuyen
 *
 */
public class JetstarSearch {

	/**
	 * logger
	 */
	final static Logger logger = Logger.getLogger(JetstarSearch.class);


	protected static String VIEWSTATE;

	/**
	 * account info
	 */
	protected final JetstarConfig jetstarConfig;

	/**
	 * init account
	 * @param jetstarConfig
	 */
	public JetstarSearch(final JetstarConfig jetstarConfig) {
		this.jetstarConfig = jetstarConfig;
	}

	/**
	 * do: search fare info
	 * @param searchItineraryCmd
	 * @return
	 * @throws Exception
	 */
	public AirFarePriceInfos doSearch(SearchItineraryCmd searchItineraryCmd,String session,String viewstate) throws Exception {
		try {

			logger.info("STEP 1: FILL FORM SEARCH");
			String htmlBody = fillFormSearch(searchItineraryCmd,session,viewstate);
			logger.info("STEP 2: EXTRACT DATA (FARE INFO)");
			return extractAirFareInfo(htmlBody);

		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * do: fill data to form search
	 * @param searchItineraryCmd
	 * @param session
	 * @return
	 * @throws Exception
	 */
	protected String fillFormSearch(SearchItineraryCmd searchItineraryCmd,String session,String viewstate) throws Exception{
		try {		
			String url =  jetstarConfig.getBaseUrl() + "TradeSalesHome.aspx";
			Connection.Response response = JsoupExecute.doPost(url, createParamsFormSearch(searchItineraryCmd, viewstate), session);
			URL urlResult = response.url();
			if(!urlResult.toString().contains("AgentSelect.aspx"))
				throw new Exception("CAN NOT POST TO [URL] = " + url + "WITH [PARAMS] = " + createParamsFormSearch(searchItineraryCmd, viewstate));
			
			return response.body();

		} catch (Exception ex) {
			logger.error("SEARCH FAILED [ERROR] = " + ex);
			throw ex;
		}

	}

	/**
	 * do: create hash map for form search
	 * @param searchItineraryCmd
	 * @param viewstate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("serial")
	private HashMap<String, String> createParamsFormSearch(SearchItineraryCmd searchItineraryCmd,final String viewstate) throws Exception {
		try {
			// choose type trips
			String travelType = "OneWay";
			if (searchItineraryCmd.getOriginDestinationInfos().size() == 2) 
				travelType = "RoundTrip";

			// get passenger info
			int adt = searchItineraryCmd.getPassengerQuantity(AirPassengerType.ADT);
			int chd = searchItineraryCmd.getPassengerQuantity(AirPassengerType.CHD);
			int inf = searchItineraryCmd.getPassengerQuantity(AirPassengerType.INF);

			// create hash map
			HashMap<String, String> params = new HashMap<String, String>() {
				{
					put("__VIEWSTATE", viewstate);
					put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$RadioButtonSearchBy", "");
					put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$numberTrips", "1");
					put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$ButtonSubmit", "");
				}
			};

			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$RadioButtonMarketStructure", travelType);
			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$DropDownListPassengerType_ADT", adt + "");
			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$DropDownListPassengerType_CHD", chd + "");
			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$DropDownListPassengerType_INFANT", inf + "");			

			OriginDestinationInfo tmpODI = searchItineraryCmd.getOriginDestinationInfos().get(0);
			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMarketOrigin1", tmpODI.getOriginCode());
			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMarketDestination1", tmpODI.getDestinationCode());
			params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextboxDepartureDate1", DateUtils.format(tmpODI.getDepartureDate(),DateUtils.DD_MM_YYYY));

			// two way
			if (travelType.equals("RoundTrip")) {
				OriginDestinationInfo tmpODI2 = searchItineraryCmd.getOriginDestinationInfos().get(1);
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextboxDestinationDate1", DateUtils.format(tmpODI2.getDepartureDate(), DateUtils.DD_MM_YYYY));
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMarketOrigin2", tmpODI2.getOriginCode());
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMarketDestination2", tmpODI2.getDestinationCode());
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextboxDepartureDate2", DateUtils.format(tmpODI2.getDepartureDate(),DateUtils.DD_MM_YYYY));
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextboxDestinationDate2", "");

				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMultiCityOrigin1", tmpODI.getOriginCode());
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMultiCityDestination1", tmpODI.getDestinationCode());
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextboxDepartureMultiDate1", DateUtils.format(tmpODI.getDepartureDate(),DateUtils.DD_MM_YYYY));

				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMultiCityOrigin2", tmpODI2.getOriginCode());
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextBoxMultiCityDestination2", tmpODI2.getDestinationCode());
				params.put("ControlGroupTradeSalesHomeView$AvailabilitySearchInputTradeSalesHomeView$TextboxDepartureMultiDate2", DateUtils.format(tmpODI2.getDepartureDate(),DateUtils.DD_MM_YYYY));
			}

			return params;

		} catch (Exception ex) {
			logger.error("CREATE PARAMS FORM SEARCH FAILED: " + ex);
			throw ex;
		}
	}

	/**
	 * do: extract data(fare info)
	 * @param htmlBody
	 * @return
	 * @throws Exception
	 */
	private AirFarePriceInfos extractAirFareInfo(String htmlBody) throws Exception {
		try {
			if(htmlBody == null)
				throw new Exception("HTML BODY NULL");

			AirFarePriceInfos result = new AirFarePriceInfos();				
			Document document = Jsoup.parse(htmlBody);
			
			int totalCHDs, totalINFs;
			try {
				Element person = document.select("div[id=current-search] dd[data-children]").first();
				totalCHDs = Integer.parseInt(person.attr("data-children"));
				totalINFs = Integer.parseInt(person.attr("data-infants"));
			} catch (Exception ex) {
				throw new Exception("NOT FOUND ELEMENT [-- div[id=current-search] dd[data-children] ---]");
			}

			Elements elements = document.getElementsByClass("full-matrix");
			for (Element element : elements) {
				try {
					Elements flightInfos = element.select("tr[flight-info-json]");
					for (Element flightInfo : flightInfos) {
						Element flightcode = flightInfo.select("td.flight-code").first();
						if (flightcode.text().replace(" ", "").length() > 5)
							continue;
						Element origincode = flightInfo.select("dt").first();
						Element destinationcode = flightInfo.select("dt").get(1);
						Element flightDetails = flightInfo.select("dd[class=details flight-summary-details]").first();
						Element datetime = flightDetails.getElementsByClass("date-time").first();
						AirFarePriceInfo priceInfo = new AirFarePriceInfo();
						AirFareInfo fareinfo = new AirFareInfo();
						fareinfo.setVendor("BL");
						fareinfo.setFlightCode(flightcode.text().replace(" ", ""));
						fareinfo.setOriginCode(origincode.text());
						fareinfo.setDestinationCode(destinationcode.text());
						fareinfo.setDepartureDate(DateUtils.parse(datetime.attr("data-date-dept"), "yyyy-MM-dd'T'HH:mm:ss")); 
						fareinfo.setArrivalDate(DateUtils.parse(datetime.attr("data-date-arrv"), "yyyy-MM-dd'T'HH:mm:ss")  );
						priceInfo.setFareInfo(fareinfo);

						AirFarePriceOption priceOption = null;
						Element starter = flightInfo.select("td[class=selection starter]").first();
						if (starter != null) {
							try {
								Element priceDetails = starter.select("input[class=radio]").first();
								String value = priceDetails.attr("value");
								String[] splitValues = value.split("[~\\|]");
								priceOption = new AirFarePriceOption();
								priceOption.setClassCode(splitValues[1]);
								priceOption.setClassName(starter.attr("bundletype"));
								priceOption.setReference(value);
								long price = (long) Double.parseDouble(priceDetails.attr("bundle-fare-adt"));
								long priceADTFees = (long) Double.parseDouble(priceDetails.attr("data-discfees-adt"));
								priceOption.add(new AirPassengerTypePrice(AirPassengerType.ADT, price + priceADTFees));
								if (totalCHDs > 0) {
									price = (long) Double.parseDouble(priceDetails.attr("bundle-fare-chd"));
									long priceCHDFees = (long) Double.parseDouble(priceDetails.attr("data-discfees-chd"));
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.CHD, price + priceCHDFees));
								}
								if (totalINFs > 0) {
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.INF, 0));
								}

								priceInfo.add(priceOption);
							} catch (Exception ex) {
								ex.printStackTrace();
								continue;
							}
						}

						Element plus = flightInfo.select("td[class=selection starter-plus]").first();
						if (plus != null) {
							try {
								Element priceDetails = plus.select("input[class=radio]").first();
								String value = priceDetails.attr("value");
								String[] splitValues = value.split("[~\\|]");
								priceOption = new AirFarePriceOption();
								priceOption.setClassCode(splitValues[1]);
								priceOption.setClassName(plus.attr("bundletype"));
								priceOption.setReference(value);
								long price = (long) Double.parseDouble(priceDetails.attr("bundle-fare-adt"));
								long priceADTFees = (long) Double.parseDouble(priceDetails.attr("data-discfees-adt"));
								priceOption.add(new AirPassengerTypePrice(AirPassengerType.ADT, price + priceADTFees));
								if (totalCHDs > 0) {
									price = (long) Double.parseDouble(priceDetails.attr("bundle-fare-chd"));
									long priceCHDFees = (long) Double.parseDouble(priceDetails.attr("data-discfees-chd"));
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.CHD, price + priceCHDFees));
								}
								if (totalINFs > 0) {
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.INF, 0));
								}
								priceInfo.add(priceOption);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}

						Element max = flightInfo.select("td[class=selection starter-max]").first();
						if (max != null) {
							try {
								Element priceDetails = max.select("input[class=radio]").first();
								String value = priceDetails.attr("value");
								String[] splitValues = value.split("[~\\|]");
								priceOption = new AirFarePriceOption();
								priceOption.setClassCode(splitValues[1]);
								priceOption.setClassName(max.attr("bundletype"));
								priceOption.setReference(value);
								long price = (long) Double.parseDouble(priceDetails.attr("bundle-fare-adt"));
								long priceADTFees = (long) Double.parseDouble(priceDetails.attr("data-discfees-adt"));
								priceOption.add(new AirPassengerTypePrice(AirPassengerType.ADT, price + priceADTFees));
								if (totalCHDs > 0) {
									price = (long) Double.parseDouble(priceDetails.attr("bundle-fare-chd"));
									long priceCHDFees = (long) Double.parseDouble(priceDetails.attr("data-discfees-chd"));
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.CHD, price + priceCHDFees));
								}
								if (totalINFs > 0) {
									priceOption.add(new AirPassengerTypePrice(AirPassengerType.INF, 0));
								}
								priceInfo.add(priceOption);
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}

						result.add(priceInfo);
					}
				} catch (Exception ex) {
					logger.error("PASSER FORM CHOOSE REFRENCE ERROR: " + ex.toString());
					throw ex;
				}
			}		

			return result;

		} catch (Exception ex) {
			throw ex;
		}
	}

}
