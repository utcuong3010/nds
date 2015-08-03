package com.mbv.airline.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/***
 * 
 * @author cuongtv
 *
 */
public class DateUtils {
	
	
	
	/**
	 *  convert date time
	 */
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	
	/***
	 * 
	 */
	public static final String DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy'T'HH:mm:s";
	
	public static final String DD_MM_YYYY_EEE_HH_MM = "dd/MM/yyyy EEE HH:mm";
	
	public static final String HH_MM_DD_MM_YYYY = "HH:mm dd/MM/yyyy";
	
	
	
	
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		DateFormat fomat = new SimpleDateFormat(pattern);
		return fomat.format(date);
	}
	
	/***
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws Exception
	 */
	public static Date parse(String dateStr, String pattern) throws Exception {
		DateFormat fomat = new SimpleDateFormat(pattern);
		return fomat.parse(dateStr);
	}
	
	/**
	 * increment or decrement day in date
	 * @param date
	 * @param days
	 * @return
	 * @throws Exception
	 */
	public static Date dateChange(Date date,int days) throws Exception{
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime (date);
			cal.add(Calendar.DATE,days);
			return cal.getTime();
		} catch (Exception ex) {			
			throw ex;
		}
				
	}
	
	/**
	 * get day,month,year from date
	 * node: support pay reservation code - if change function not working
	 * @param date
	 * @param typeDate
	 * @return
	 */
	public static int getTypeOfDate(Date date, String typeDate){		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);	    
		if(typeDate.equals("month"))
			return cal.get(Calendar.MONTH) + 1; // months start at 0, not 1.
		else if(typeDate.equals("year"))
			return cal.get(Calendar.YEAR);
		return cal.get(Calendar.DAY_OF_MONTH);
		
	}
	
	/**
	 * get time(hour:hh or HH , minute: mm, second: ss) from date
	 * @param date
	 * @param typeTime
	 * @return
	 * @throws Exception
	 */
	public static int getHourFromDate(Date date,String typeTime,String typeTimeZone) throws Exception{
		try {
			DateFormat dateFormat = new SimpleDateFormat(typeTime);
			TimeZone gmtTime = TimeZone.getTimeZone(typeTimeZone);
			dateFormat.setTimeZone(gmtTime); 
			String hour = dateFormat.format(date);
			return Integer.parseInt(hour);
		} catch (Exception ex) {			
			throw ex;
		}	
	}
	
	/***
	 * Tuyen Pham
	 * @param expiredDate
	 * @return
	 */
	public static boolean compareDate(Date expiredDate){
		Date datetimeCurrent = new Date();	
		long datetime = expiredDate.getTime() - datetimeCurrent.getTime();		
		if(datetime > 60000*5)
			return true;
		return false;
	}
	
	/**
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date nextDay(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE,days);
		return calendar.getTime();
	}

}
