package com.mbv.airline.common;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;


/**
 * 
 * @author cuongtv
 *
 */
public class JsonUtils {
	
	

	/**
	 * to string in json format
	 * @param object
	 * @return
	 * @throws Exception
	 */
	
	public static String toString(Object object) throws Exception{
		
		ObjectWriter writer = new ObjectMapper().writer();
		
		return writer.writeValueAsString(object);
		
	}
}
