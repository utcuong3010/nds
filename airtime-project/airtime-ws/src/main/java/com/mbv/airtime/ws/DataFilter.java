package com.mbv.airtime.ws;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class DataFilter implements Serializable {
	String query;
	
	final Map<String, Object> properties = new LinkedHashMap<String, Object>();
	
	int pageNumber = 1;
	int pageSize = 20;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}
	
	public void setProperties(Map<String, Object> props) {
		if (props!=null)
			properties.putAll(props);
		else
			properties.clear();
	}
	
	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * return a property value
	 * @param property
	 * @return
	 */
	public <T> T getValue(String property) {
		@SuppressWarnings("unchecked")
		T obj = (T) properties.get(property);
		return obj;
	}
	
	public void setValue(String name, Object value) {
		if (value==null)
			properties.remove(name);
		else
			properties.put(name, value);
	}
	
	/**
	 * Return an integer value of a property.
	 * @param property
	 * @return
	 */
	public int getIntValue(String property) {
		if (!properties.containsKey(property))
			return 0;
		Object value = properties.get(property);
		if (value==null)
			return 0;
		if (value instanceof Number)
			return ((Number)value).intValue();
		if (value instanceof String)
			return Integer.parseInt((String)value);
		throw new RuntimeException("Could not convert integer from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**
	 * Return a long value of a property.
	 * @param property
	 * @return
	 */
	public long getLongValue(String property) {
		if (!properties.containsKey(property))
			return 0;
		Object value = properties.get(property);
		if (value==null)
			return 0;
		if (value instanceof Number)
			return ((Number)value).longValue();
		if (value instanceof String)
			return Long.parseLong((String)value);
		throw new RuntimeException("Could not convert long from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**
	 * Return a float value of a property.
	 * @param property
	 * @return
	 */
	public float getFloatValue(String property) {
		if (!properties.containsKey(property))
			return 0;
		Object value = properties.get(property);
		if (value==null)
			return 0;
		if (value instanceof Number)
			return ((Number)value).floatValue();
		if (value instanceof String)
			return Float.parseFloat((String)value);
		throw new RuntimeException("Could not convert float from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**
	 * Return an double value of a property.
	 * @param property
	 * @return
	 */
	public double getDoubleValue(String property) {
		if (!properties.containsKey(property))
			return 0;
		Object value = properties.get(property);
		if (value==null)
			return 0;
		if (value instanceof Number)
			return ((Number)value).doubleValue();
		if (value instanceof String)
			return Double.parseDouble((String)value);
		throw new RuntimeException("Could not convert doule from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**
	 * Return an integer value of a property.
	 * @param property
	 * @return
	 */
	public Date getDateValue(String property) {
		if (!properties.containsKey(property))
			return null;
		Object value = properties.get(property);
		if (value==null)
			return null;
		if (value instanceof Date)
			return (Date)value;
		try {
			if (value instanceof String)
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((String)value);
		}
		catch (ParseException ex) {
			throw new RuntimeException("Unable to parse date from " + value, ex);
		}
		throw new RuntimeException("Could not convert date from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**
	 * get list value of a property
	 * @param property
	 * @return
	 */
	public List<?> getListValue(String property) {
		if (!properties.containsKey(property))
			return null;
		Object value = properties.get(property);
		if (value==null) return null;
		if (value instanceof List)
			return (List<?>)value;
		throw new RuntimeException("Could not convert list from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**get map value of a property
	 * 
	 * @param property
	 * @return
	 */
	public Map<?,?> getMapValue(String property) {
		if (!properties.containsKey(property))
			return null;
		Object value = properties.get(property);
		if (value==null) return null;
		if (value instanceof Map)
			return (Map<?,?>)value;
		throw new RuntimeException("Could not convert map from unknown type: " + value.getClass().getCanonicalName());
	}
	
	/**
	 * get a string value of a property
	 * @param property
	 * @return
	 */
	public String getStringValue(String property) {
		if (!properties.containsKey(property))
			return null;
		Object value = properties.get(property);
		if (value==null)
			return null;
		
		if (value instanceof String)
			return (String)value;
	
		return value.toString();
	}

	public boolean hasValue(String value) {
		return properties.containsKey(value);
	}
}
