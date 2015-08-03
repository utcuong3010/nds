package com.mbv.bp.common.integration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ContextBase extends HashMap<String, String> implements Serializable {
	private static final long serialVersionUID = -7180680748604441146L;

	private static Log log = LogFactory.getLog(ContextBase.class); 

    private static String GLOBAL_DATE_FORMAT = "yyyyMMddHHmmssS";  
  
    public ContextBase() {
        super();
    }

    public void putInt(String key, int value) {
            put(key, Integer.toString(value));
    }


    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        int result = defaultValue;
        String keyValue = getString(key);
        if (NumberUtils.isNumber(keyValue))
            result = Integer.parseInt(keyValue);
        else
            log.info("return default for key-"+key+"| value-"+keyValue);
        return result;
    }

   public void putString(String key, String value) {
        if (StringUtils.isNotBlank(value)) {
            put(key, value);
        } else {
            put(key, "");
        }
    }


    public String getString(String key) {
        String value = "";
        value = get(key);
        if (value != null)
            return value;
        else
            return "";
    }

    public void putLong(String key, long value) {
         put(key, Long.toString(value));
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }


    public long getLong(String key, long defaultValue) {
        long result = defaultValue;
        String keyValue = getString(key);
        if (NumberUtils.isNumber(keyValue)) {
        	try {
        		result = Long.parseLong(keyValue);
        	}catch (Exception  e) {
        		log.warn("Fail to get long value",e);
        	}
        }else {
        	log.info("return default for key-"+key+"| value-"+keyValue);

        }
        return result;
    }
    
    public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
    	BigDecimal result = defaultValue;
        String keyValue = getString(key);
        if (NumberUtils.isNumber(keyValue)) {
        	try {
        		result = BigDecimal.valueOf(getDouble(key));
        	}catch (Exception  e) {
        		log.warn("Fail to get BigDecimal value",e);
        	}
        }else {
        	log.info("return default for key-"+key+"| value-"+keyValue);
        }
        return result;
    }

   public void putDouble(String key, double value) {
            put(key, Double.toString(value));
    }

   public double getDouble(String key) {
        return getDouble(key, 0);
    }


   public double getDouble(String key, double defaultValue) {
        double result = defaultValue;
        String keyValue = getString(key);
        if (NumberUtils.isNumber(keyValue) && StringUtils.isNotBlank(keyValue))
            result = Double.parseDouble(keyValue);
        else {
        	log.info("return default for key-"+key+"| value-"+keyValue);
        }
        return result;
    }

   
    public void putDate(String key, Date value) {
        if (value != null) {
            DateFormat df = new SimpleDateFormat(GLOBAL_DATE_FORMAT);
            put(key, df.format(value));
        } else {
        	log.info("Fail to put date-"+key+"| value-"+value);
        }
    }

    public Date getDate(String key) {
        String keyValue = getString(key);
        if (StringUtils.isNotEmpty(keyValue)) {
            DateFormat dt = new SimpleDateFormat(GLOBAL_DATE_FORMAT);
            try {
                return dt.parse(keyValue);
            } catch (ParseException ex) {
                log.warn(new StringBuilder("key:").append(key).append(",value: ").append(keyValue).append(" is not date").toString());
                return null;
            }
        } else {
            if (log.isDebugEnabled())
                log.debug("key/value is empty");
        }
        return null;
    }

    public String toFilterString(List<String> filterKeyList) {
        Map<String, String> filterMap = new HashMap<String, String>();
        for (String key : filterKeyList) {
            filterMap.put(key, getString(key));
        }
        return filterMap.toString();
    }

    public void setErrorCode(String errorCode){
    	put(Attributes.ATTR_ERROR_CODE,errorCode);
    }
    
    public String getErrorCode(){
    	return get(Attributes.ATTR_ERROR_CODE);
    }
}
