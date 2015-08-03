package com.mbv.airline.sabre.utils;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class PricingClassUtil {
	LinkedHashMap<String, String> pricingClassMap;

	public void setPricingClassMap(LinkedHashMap<String, String> pricingClassMap) {
		this.pricingClassMap = pricingClassMap;
	}
	
	public String getGroupofClass(String className) {
		String result = null;
		
		for (Entry<String, String> entry : pricingClassMap.entrySet()) {
			if (className.matches(entry.getKey())) {
				result = entry.getValue();
				break;
			}
		}
		
		return result;
	}
	
}
