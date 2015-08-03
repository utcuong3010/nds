package com.mbv.airline.sabre.utils;

import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.Gender;

/**
 * Created by phuongvt on 3/17/15.
 */
public class Utils {
	
 	private static final Character CROSS_LORRAINE_CHAR = new Character('\u0081'); //'
    private static final Character GDS_ITEM_DELIM_CHAR = new Character('\u0086'); //+
    private static final Character FIELD_CHAR = new Character('\u0083'); //^
    private static final Character END_OF_MESSAGE_CHAR = new Character('\u0089');

	public static int getPaxCountByType(AirItinerary itinerary,
			AirPassengerType type) {
		int count = 0;
		for (AirPassengerInfo pax : itinerary.getPassengerInfos()) {
			if (pax.getPassengerType().name().equals(type.name()))
				count++;
		}
		return count;
	}

	public static AirPassengerType getPassengerType(String type) {
		if (type != null) {
			if (type.equals("ADT"))
				return AirPassengerType.ADT;
			else if (type.equals("CHD") || type.equals("CNN"))
				return AirPassengerType.CHD;
			else if (type.equals("INF"))
				return AirPassengerType.INF;
		}
		return null;
	}

	public static Gender getGenderType(String gender) {
		if (gender != null) {
			if (gender.equals("M"))
				return Gender.MALE;
			else if (gender.equals("F"))
				return Gender.FEMALE;
		}
		return null;
	}
	
	public static String removeNonASCII(String inputString){
		String resultString = inputString.replaceAll("[^\\x00-\\x7F]", "");
		return resultString;
	}
}
