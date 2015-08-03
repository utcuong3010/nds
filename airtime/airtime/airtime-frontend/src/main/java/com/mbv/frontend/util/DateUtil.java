package com.mbv.frontend.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Ported from DateUtil.java
 * <p/>
 * Author: Toan Tran
 * Date: August 26, 2008
 * Time: 4:29:58 PM
 */
public class DateUtil {
	
	public static final String GLOBAL_FORMAT = "yyMMddHHmmssS";

    //------ Log object
    protected static Log log = LogFactory.getLog(DateUtil.class);

    // --------- Public enum type --------------
    public enum Type {
        BY_DAY, BY_HOUR, BY_MINUTE, BY_SECOND, BY_MILLISECOND
    }

    public enum Operation {
        PLUS, MINUS
    }

    // --------- Public static methods ------------------

    /**
     * Returns a string date.
     *
     * @param date   - date to convert
     * @param format - date format
     * @return String date
     */
//    public static String convertDatetoString(int gmt,Date date, String format) {
//        try {
//        	Date convertDate=dateAdd(date, gmt, Type.BY_HOUR, Operation.PLUS);
//            String retVal = "";
//            if (date != null) {
//                Format formatter = new SimpleDateFormat(format);
//                retVal = formatter.format(convertDate);
//            }
//            return retVal;
//        } catch (Exception e) {
//            log.error("DateUtil.convertDatetoString error| ErrMsg:" + e.getMessage(), e);
//        }
//        return null;
//    }

    public static String convertDatetoString(Date date, String format) {
        try {
            String retVal = "";
            if (date != null) {
                Format formatter = new SimpleDateFormat(format);
                retVal = formatter.format(date);
            }
            return retVal;
        } catch (Exception e) {
            log.error("DateUtil.convertDatetoString error| ErrMsg:" + e.getMessage(), e);
        }
        return null;
    }
    /**
     * Returns a Date object from String date.
     *
     * @param date   - string to convert
     * @param format - date format
     * @return Date object. Return null on error
     */
//    public static Date convertStringToDate(int gmt,String date, String format) {
//        try {
//        	
//        	SimpleDateFormat reqformat = new SimpleDateFormat(format);
//            Date dateResult=reqformat.parse(date);
//            if (date.equalsIgnoreCase(convertDatetoString(0,dateResult, format)))
//            return dateAdd(dateResult, gmt, Type.BY_HOUR, Operation.MINUS);
//            else return null;
//        } catch (Exception e) {
//            log.error("DateUtil.convertStringToDate error| ErrMsg:" + e.getMessage(), e);
//        }
//        return null;
//    }

    /**
     * Add/subtract operation on a date
     *
     * @param date      Date object
     * @param addValue  Value to be added
     * @param unit      By millisecond, second, hour, day
     * @param operation Add or subtract
     * @return A new date
     */
    public static Date dateAdd(Date date, long addValue, Type unit, Operation operation) {
        if (date != null) {
            long timeInLong = date.getTime();
            int checkOperation = 0;
            if (operation == Operation.PLUS)
                checkOperation = 1;
            else
                checkOperation = -1;
            if (unit == Type.BY_DAY)
                timeInLong += checkOperation * addValue * 1000 * 60 * 60 * 24;
            else if (unit == Type.BY_HOUR)
                timeInLong += checkOperation * addValue * 1000 * 60 * 60;
            else if (unit == Type.BY_MINUTE)
                timeInLong += checkOperation * addValue * 1000 * 60;
            else if (unit == Type.BY_SECOND)
                timeInLong += checkOperation * addValue * 1000;
            else if (unit == Type.BY_MILLISECOND)
                timeInLong += checkOperation * addValue;
            return new Date(timeInLong);
        } else {
            log.error("DateUtil.dateAdd error | ErrMsg: input date is null ");
            return null;
        }
    }
 

    /**
     * Returns a long value.
     *
     * @param diffUom  - (Type.BY_DAY, Type.BY_HOUR, Type.BY_MINUTE,Type.BY_SECOND,DateUtil.BY_MILLIS )
     * @param fromDate - from date object
     * @param toDate   - to date object
     * @return long value
     */
    public static long dateDiffGMT2GMT7(Date fromDate, Date toDate) {
    	Date gmtFromDate=dateAdd(fromDate, 7, Type.BY_HOUR, Operation.PLUS);
    	Date gmtToDate=dateAdd(toDate, 7, Type.BY_HOUR, Operation.PLUS);
        Calendar cal = Calendar.getInstance();
       	cal.set(gmtFromDate.getYear()+1900, gmtFromDate.getMonth(), gmtFromDate.getDate(),0, 0, 0);
        long fromTime = cal.getTimeInMillis();
       	cal.set(gmtToDate.getYear()+1900, gmtToDate.getMonth(), gmtToDate.getDate(), 0, 0, 0);
        long endTime = cal.getTimeInMillis();
        long diffTimeInMillis = endTime - fromTime;
       
        return (diffTimeInMillis / 1000) / (60 * 60 * 24);
    }
    
public static long dateDiff(Type diffUom, Date fromDate, Date toDate) {
    	
        Calendar cal = Calendar.getInstance();
        
        if (diffUom == Type.BY_DAY) {
        	cal.set(fromDate.getYear()+1900, fromDate.getMonth(), fromDate.getDate(), 0, 0, 0);
        } else if (diffUom == Type.BY_HOUR) {
        	cal.set(fromDate.getYear()+1900, fromDate.getMonth(), fromDate.getDate(), fromDate.getHours(),0, 0);
        } else if (diffUom == Type.BY_MINUTE) {
        	cal.set(fromDate.getYear()+1900, fromDate.getMonth(), fromDate.getDate(), fromDate.getHours(), fromDate.getMinutes(), 0);
        } else if (diffUom == Type.BY_SECOND)
        	cal.set(fromDate.getYear()+1900, fromDate.getMonth(), fromDate.getDate(), fromDate.getHours(), fromDate.getMinutes(), fromDate.getSeconds());
         else if (diffUom == Type.BY_MILLISECOND)
            cal.setTime(fromDate);

        long fromTime = cal.getTimeInMillis();

        if (diffUom == Type.BY_DAY) {
        	cal.set(toDate.getYear()+1900, toDate.getMonth(), toDate.getDate(), 0, 0, 0);
        } else if (diffUom == Type.BY_HOUR) {
        	cal.set(toDate.getYear()+1900, toDate.getMonth(), toDate.getDate(), toDate.getHours(),0, 0);
        } else if (diffUom == Type.BY_MINUTE) {
        	cal.set(toDate.getYear()+1900, toDate.getMonth(), toDate.getDate(), toDate.getHours(), toDate.getMinutes(), 0);
        } else if (diffUom == Type.BY_SECOND)
        	cal.set(toDate.getYear()+1900, toDate.getMonth(), toDate.getDate(), toDate.getHours(), toDate.getMinutes(), toDate.getSeconds());
         else if (diffUom == Type.BY_MILLISECOND)
            cal.setTime(toDate);
        

        long endTime = cal.getTimeInMillis();

//        if (endTime < fromTime) {
//            log.error("DateUtil.dateDiff error | ErrMsg: endDate less than fromDate. From date " + fromDate + ", To date " + toDate);
//            return -1;
//        }

        long diffTimeInMillis = endTime - fromTime;
        if (diffUom == Type.BY_DAY){
        	
            return (diffTimeInMillis / 1000) / (60 * 60 * 24);
        }else if (diffUom == Type.BY_HOUR)
            return (diffTimeInMillis / 1000) / (60 * 60);
        else if (diffUom == Type.BY_MINUTE)
            return (diffTimeInMillis / 1000) / (60);
        else if (diffUom == Type.BY_SECOND)
            return (diffTimeInMillis / 1000);
        else if (diffUom == Type.BY_MILLISECOND)
            return diffTimeInMillis;
        // Not possible to come here 
        return -1;
    }
    
//    public static String stringGMTFromLocalDate(Date date){
//		String format="yyyyMMddHHmmssS";
//		final SimpleDateFormat sdf =new SimpleDateFormat(format);
//		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//		return sdf.format(date);
//	}	
//	
//	public static Date dateGMTFromStringDate(String strDate){
//		String format="yyyyMMddHHmmssS";
//		final SimpleDateFormat sdf =new SimpleDateFormat(format);
//		try {
//			return sdf.parse(strDate);
//		} catch (ParseException e) {
//			log.error(e);
//			return null;
//		}
//	}
//	
//	public static Date dateGMTDateFromLocalDate(Date date) {
//		return dateGMTFromStringDate(stringGMTFromLocalDate(date));
//	}
//	public static String stringDateFromDate(Date date){
//		String format="yyyyMMddHHmmssS";
//		final SimpleDateFormat sdf =new SimpleDateFormat(format);
//		return sdf.format(date);
//	}	
//	
//	public static Date dateLocalDateFromGMTDate(Date date){
//		return dateLocalDateFromStringGMTDate(stringDateFromDate(date));
//	}
//	
//	
//    public static Date dateLocalDateFromStringGMTDate(String strGMTDate){
//    	try{
//	    	String format="yyyyMMddHHmmssS";
//			final SimpleDateFormat sdf =new SimpleDateFormat(format);
//			sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
//			return sdf.parse(strGMTDate);
//    	}catch (Exception e) {
//    		log.error(e);
//    		return null;
//		}
//	}
    
    public static String convertDate2String(Date date, String strTimeZone,String format){
    	try{
	    	final SimpleDateFormat sdf =new SimpleDateFormat(format);
			sdf.setTimeZone(TimeZone.getTimeZone(strTimeZone));
			return sdf.format(date);
    	}catch (Exception e) {
    		log.error(e);
    		return null;
		}
	}
    public static Date convertString2Date(String strDate, String strTimeZone,String format){
    	try{
	    	final SimpleDateFormat sdf =new SimpleDateFormat(format);
			sdf.setTimeZone(TimeZone.getTimeZone(strTimeZone));
			return sdf.parse(strDate);
    	}catch (Exception e) {
    		log.error(e);
    		return null;
		}
	}
   
}
    