package com.mbv.helpers;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;

public class FormatHelper {
	
	public static String formatDateTime(Date value, int secondOffset) {
		if (value==null) return null;
						
		return formatDateTimeSTD(new Date(value.getTime() + secondOffset*1000));
	}

    public static String formatDateTimeSTD(Date value) {
        return formatDateTime(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDateTime(Date value, String format) {
        if(value==null)
            return null;
        
        DateFormat df = new SimpleDateFormat(format);
        return df.format(value);
    }
	
	public static String formatDate(java.sql.Date value) {
		if (value==null) return null;
		return DateFormat.getDateInstance().format(value);
	}

    public static Date cvtToGmt(Date date) {
        TimeZone tz = TimeZone.getDefault();
        Date ret = new Date(date.getTime() - tz.getRawOffset());

        // if we are now in DST, back off by the delta.  Note that we are checking the GMT date, this is the KEY.
        if (tz.inDaylightTime(ret)) {
            Date dstDate = new Date(ret.getTime() - tz.getDSTSavings());

            // check to make sure we have not crossed back into standard time
            // this happens when we are on the cusp of DST (7pm the day before the change for PDT)
            if (tz.inDaylightTime(dstDate)) {
                ret = dstDate;
            }
        }
        return ret;
    }

    public static Date getCurrentGMT0Date(){
    	return cvtToGmt(new Date(System.currentTimeMillis()));
    }
    
    public static Date getCurrentDate(){
    	return new Date(System.currentTimeMillis());
    }
    
    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
    	GregorianCalendar car = new GregorianCalendar(year, month-1, day, hour, minute, second);
    	car.setTimeZone(TimeZone.getTimeZone("GMT"));
    	return car.getTime();
    }
    
    public static Date modifyDatetime(Date date, int field, int value){
    	if (date==null) return null;
    	GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(field,value);
    	return cal.getTime();
    }
    
    public static Date modifyDate(Date date, int field, int newValue){
    	if (date==null) return null;
    	GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(field,newValue);
    	return cal.getTime();
    }
    
    public static Date getNowDate() {
        return cvtToGmt(new Date());
    }

    public static Date getLastYearDate() {
        return getDaysBeforeDate(365);
    }

    public static Date getDaysBeforeDate(int daysBefore) {
        Date value = new Date();
        GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(value);
		cal.add(Calendar.DATE, -1*daysBefore);
		return cvtToGmt(cal.getTime());
    }

    public static Date getDateFrom(int month, int day, int year, int hour, int minute, int second) {
        String sDate = String.format("%2d/%2d/%4d %2d:%2d:%2d", month, day, year, hour, minute, second);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date dateFrom = null;
        try {
            dateFrom = df.parse(sDate);
            dateFrom = cvtToGmt(dateFrom);
        }
        catch (ParseException e)
        {
            return getNowDate();
        }
        return dateFrom;
    }

	public static Date parseDateTime(String value, int secondOffset) {
		if (value==null) return null;
		//Added by Tin Nguyen
		if (value.length()==10) {
			StringBuffer temp = new StringBuffer(value);
			temp.append(" 00:00:00");
			value = temp.toString();
		}
		//
		GregorianCalendar cal = new GregorianCalendar(
				Integer.parseInt(value.substring(0, 4)),
				Integer.parseInt(value.substring(5, 7))-1,
				Integer.parseInt(value.substring(8, 10)),
				Integer.parseInt(value.substring(11, 13)),
				Integer.parseInt(value.substring(14, 16)),
				Integer.parseInt(value.substring(17, 19))
		);
		cal.add(Calendar.SECOND, -secondOffset);
		return cal.getTime();
	}

    public static Date parseDateTime(String sDate, String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date value = null;
        try {
            value = df.parse(sDate);
        } catch (ParseException ex) {
            return null;
        }
        return value;
    }
	
	public static String formatRegex(Matcher matcher, String matchStr, String formatStr)
	{	
		if (!matcher.reset().find()) return formatStr;
		int nMatchCount = matcher.groupCount();
		int nMatch = 0;
		
		while (++nMatch<=nMatchCount) {
			if (formatStr.contains("$" + nMatch)) {
				formatStr = formatStr.replace("$" + nMatch, matchStr.substring(matcher.start(nMatch), matcher.end(nMatch)));
			}
		} 
		return formatStr;
	}

	public static Number convertStrToNum(Locale l, String strnum) throws ParseException{
        return NumberFormat.getNumberInstance(l).parse(strnum);
	}
}
