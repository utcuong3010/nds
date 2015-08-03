package com.mbv.hotel.util;

import java.util.Date;


/***
 * including all util methods on date
 * @author cuongtv
 *
 */
public class DateUtils {
	
	
	/*** constants ***/
	public final static String FORMAT_DATE_YYY_DD_MM = "yyyMMdd";
	
	
	/***
	 * 
	 * @param expiredDate
	 * @return
	 */
	public static boolean isExpiredDate(Date expiredDate){
		Date currentDate = new Date();	
		long datetime = expiredDate.getTime() - currentDate.getTime();		
		if(datetime > 60000*5)
			return true;
		return false;
	}
}
