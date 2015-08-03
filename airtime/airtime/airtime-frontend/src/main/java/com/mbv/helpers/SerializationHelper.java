package com.mbv.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SerializationHelper {
	static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static {
        DATE_FORMATTER.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public static String serializeDateTime(Date value) {
    	if (value==null) return null;
    	return DATE_FORMATTER.format(value);
    }
    
    public static Date deserializeDateTime(String value) throws ParseException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return DATE_FORMATTER.parse(value);
    }
	
	public static java.sql.Date deserializeDate(String value) {
		if (value!=null)
			return java.sql.Date.valueOf(value);
		else 
			return null;
	}
	
	public static String serializeDate(java.sql.Date value) {
		if (value==null)
			return null;
		else 
			return value.toString();
	}

    public static String serializeInt(int value) {
        return new Integer(value).toString();
    }

    public static int deserializeInt(String value) {
        if(value==null || value.isEmpty())
            return 0;
        return Integer.parseInt(value.trim());
    }

    public static String serializePercents(float value) {
        return new Float(value).toString();
    }
    
    public static float deserializePercents(String value) {
        return Float.parseFloat(value.replace("%", "").trim());
    }
}
