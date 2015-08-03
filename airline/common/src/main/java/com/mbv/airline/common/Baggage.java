package com.mbv.airline.common;



public enum Baggage {
	BG15,BG20,BG25,BG30,BG35,BG40;


	public static String getText (String value) {
		String result = "thanks";
		if("".equals(value) || value == null) return result;
		Baggage baggage = Baggage.valueOf(value);
		switch (baggage) {
		case BG15:
			result = "15kgs";
			break;
		case BG20:
			result = "20kgs";
			break;
		case BG25:
			result = "25kgs";
			break;
		case BG30:
			result = "30kgs";
			break;
		case BG35:
			result = "35kgs";
			break;
		case BG40:
			result = "40kgs";
			break;

		default:
			break;
		}
		return result;		
	};
	
	
	public static int getIndexInSelect (String value) {
		int index = 0;
		if("".equals(value) || value == null) return index;
		Baggage baggage = Baggage.valueOf(value);
		switch (baggage) {
		case BG15:
			index = 1;
			break;
		case BG20:
			index = 2;
			break;
		case BG25:
			index = 3;
			break;
		case BG30:
			index = 4;
			break;
		case BG35:
			index = 5;
			break;
		case BG40:
			index = 6;
			break;

		default:
			break;
		}
		return index;		
	};
	
	
	

}
