package com.mbv.airline.common;

/**
 * 
 * @author phamtuyen
 *
 */
public class StringUtils {

	/**
	 * do: part String 
	 * @param prefixStr
	 * @param regex
	 * @param indexOfArray
	 * @return
	 * @throws Exception
	 */
	public static String splitString(String prefixStr ,String regex,int indexOfArray) throws Exception{
		String []partString = prefixStr.split(regex);	
		return partString[indexOfArray];
	}
}
