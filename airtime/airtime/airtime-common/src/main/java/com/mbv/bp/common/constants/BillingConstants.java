package com.mbv.bp.common.constants;

import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import com.mbv.bp.common.dao.DaoException;

public class BillingConstants extends AppConfig {
	public static Map<String, List<String>> TELCO_PROVIDER_SUPPORTED_MAP;
	static {
	    try {
			load();
		} catch (ConfigurationException e) {
			log.error("Fail to load config. ",e);
		} catch (DaoException e) {
			log.error("Fail to load config| Database exception. ",e);
		}
	} 
	
	public static void load() throws ConfigurationException, DaoException {
		loadConfig(getConfig("gateway","config"));
	}

	@SuppressWarnings("unchecked")
	private static void loadConfig(Configuration config) {
	}
	
}