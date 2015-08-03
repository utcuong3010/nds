package com.mbv.airline.sabre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirPassengerTypeQuantity;

public class RequestFactory {
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

	public static String createSearch() throws ParseException{
		SearchItineraryCmd searchRequest = new SearchItineraryCmd();
		
		final List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();

		AirFareInfo fareInfo = new AirFareInfo(); 
				
					fareInfo.setVendor("VN");
					fareInfo.setOriginCode("SGN");
					fareInfo.setDestinationCode("HUI");
					fareInfo.setDepartureDate(dateFormat.parse("204-04-01T08:30:00.000"));
					fareInfos.add(fareInfo);
		
		
		searchRequest.setOriginDestinationInfos(fareInfos);
		
		final List<AirPassengerTypeQuantity> passengerInfos = new ArrayList<AirPassengerTypeQuantity>();
		passengerInfos.add(new AirPassengerTypeQuantity(AirPassengerType.ADT, 1));
				// add(new AirPassengerTypeQuantity(AirPassengerType.CHD, 1));
				// add(new AirPassengerTypeQuantity(AirPassengerType.INF, 1));
			
		
		
		searchRequest.setPassengerInfos(passengerInfos);
		return searchRequest.toHashString();
	}
	
	public static void main(String[] args) throws ParseException{
		System.out.println(createSearch());
	}
}
