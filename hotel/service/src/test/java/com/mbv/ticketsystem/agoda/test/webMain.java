package com.mbv.ticketsystem.agoda.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class webMain {
	final static Logger logger = Logger.getLogger(webMain.class);
	public static void main(String[] args) throws ParseException{  
		DateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy'T'HH:mm:ss");
		//		String expiredDate =  document.select("html body div#wrapper div#wrapper-2 div#container div#main form#Itinerary h1:nth-child(12)").text();
		//		String getExpiredDate = "Nếu không thanh toán, đến 26/03/2015 Thu 13:57 (GMT+7), đặt chỗ của bạn sẽ tự động bị hủy.";
		//		String [] parseExpired = getExpiredDate.split(",");
		//		String expiredDate = parseExpired[1].replace("đến", "").replace("(GMT+7)", "").trim().replaceAll("[^0-9,:,/]","T").replace("TTTTT", "T")+":00";
		//		System.out.println(datetimeFormat.parse(expiredDate)); 

		//26 Thg3, 2015 08:20 AM GMT
//		String getExpiredDate = "26 Thg3, 2015 08:20 AM GMT";
//		System.out.println(datetimeFormat.parse(getexpiredDate(getExpiredDate)));
		
		Date expiredDate = datetimeFormat.parse("25/03/2015T16:37:00");
		Date datetimeCurrent = new Date();	
		System.err.println(datetimeCurrent);
		long datetime = expiredDate.getTime() - datetimeCurrent.getTime();
		System.out.println(datetime); 
		if(datetime > 60000*3)
			System.out.println("okie");
		else 
			System.out.println("expired");
	}
	
	private static String getexpiredDate(String getExpiredDate){
		String expiredSplit = getExpiredDate.replaceAll("AM GMT","").replace("Thg", "").replace(",", "").trim();
		String []parseExpiredDate = expiredSplit.split(" ");
		String expiredDate = "";
		for(int i=0;i<parseExpiredDate.length;i++){
			if(i==2)
				expiredDate += parseExpiredDate[i] + "T";	
			else{
				expiredDate += parseExpiredDate[i];
				if(i<2)
					expiredDate += "/";
			}
		}
		return expiredDate+":00";
	}

}
