package com.mbv.bp.common.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Ported from DateUtil.java
 * <p/>
 * Author: Toan Tran
 * Date: August 26, 2008
 * Time: 4:29:58 PM
 */
public class DateUtils {
	
	public static final String GLOBAL_FORMAT = "yyMMddHHmmssS";

    //------ Log object
    protected static Log log = LogFactory.getLog(DateUtils.class);

    // --------- Public enum type --------------
    public enum Type {
        BY_DAY, BY_HOUR, BY_MINUTE, BY_SECOND, BY_MILLISECOND
    }

    public enum Operation {
        PLUS, MINUS
    }

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
    public static Date convertStringToDate(String date, String format) {
        try {
        	SimpleDateFormat reqformat = new SimpleDateFormat(format);
            Date dateResult=reqformat.parse(date);
            if (date.equalsIgnoreCase(convertDatetoString(dateResult, format)))
            return dateResult;
            else return null;
        } catch (Exception e) {
            log.error("DateUtil.convertStringToDate error| ErrMsg:" + e.getMessage(), e);
        }
        return null;
    }


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
    
    
    public static Date monthMinus( Date date, int addValue) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.MONTH , addValue*-1);
    	return cal.getTime();
    	
    	
    }
    

    @SuppressWarnings("deprecation")
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

        return -1;
    }
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
    