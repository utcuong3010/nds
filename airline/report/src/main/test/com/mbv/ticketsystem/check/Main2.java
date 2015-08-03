package com.mbv.ticketsystem.check;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.mbv.ticketsystem.common.airline.AirFareInfo;
import com.mbv.ticketsystem.common.airline.AirPassengerType;
import com.mbv.ticketsystem.common.airline.AirPassengerTypeQuantity;
import com.mbv.ticketsystem.common.airline.UpdateFarePriceCommand;

public class Main2 {

	public static void main(String[] args) throws ParseException, JsonGenerationException, JsonMappingException, IOException {
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

		AirFareInfo fareInfo = new AirFareInfo();
		fareInfo.setVendor("VJ");
		fareInfo.setOriginCode("SGN");
		fareInfo.setDestinationCode("HAN");
		fareInfo.setDepartureDate(dateFormat.parse("2015-08-15T09:27:22.364Z"));
		fareInfo.setArrivalDate(dateFormat.parse("2015-108-15T09:27:22.364Z"));

		List<AirFareInfo> fareInfoList = new ArrayList<AirFareInfo>();	
		fareInfoList.add(fareInfo);

		// Passenger
		List<AirPassengerTypeQuantity> passList = new ArrayList<AirPassengerTypeQuantity>();
		passList.add(new AirPassengerTypeQuantity(AirPassengerType.ADT, 1));
		passList.add(new AirPassengerTypeQuantity(AirPassengerType.CHD, 0));
		passList.add(new AirPassengerTypeQuantity(AirPassengerType.INF, 0));

		UpdateFarePriceCommand request = new UpdateFarePriceCommand();
		request.setOriginDestinationInfos(fareInfoList);
		request.setPassengerInfos(passList);

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(request);
		System.out.println(json);
	}

}
