package com.mbv.hotel.util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 
 * @author cuongtv
 *
 */
public class JsonUtils {
	
	private final static Logger logger = Logger.getLogger(JsonUtils.class);
	
	/***
	 * return json string
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
			
		}catch (IOException e) {
			logger.error("Convert json " + e);
		}
		return null;
		
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJsonUTF8(Object obj) {
		try {
			
			String contentJson = JsonUtils.toJson(obj);
			
			return new String(contentJson.getBytes("ISO-8859-1"),"UTF-8");
			
		}catch (IOException e) {
			logger.error("Convert json " + e);
		}
		return null;
		
	}
	/***
	 * covert json to object
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> valueType) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.readValue(json,valueType);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
