package com.mbv.bp.common.constants;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;

import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.model.GwErrConverter;


public class GatewayConstants extends AppConfig {

	public static String CREATE_TXN_ID_WFP = "generateTxnIdWfp";
	public static String TOPUP_AIRTIME_WFP="wfpAirTimeRequestPushToQueue";
	public static String INQUIRY_AIRTIME_TXN_WFP="wfpAirTimeInquiryTransaction";
	public static String CREATE_LOCK_ACCOUNT_AIRTIME_WFP="createLockAccountWfp";
	public static String CREDIT_LOCK_ACCOUNT_WFP="creditLockAmountWfp";
	public static String DEBIT_LOCK_ACCOUNT_WFP ="debitLockAmountWfp";
	public static String LOCK_ACCOUNT_QUERY_WFP ="lockAccountQueryWfp";
	public static String LOCK_ACCOUNT_TXN_QUERY_WFP ="lockAccountTxnQueryWfp";
	
	public static String RESERVE_TOPUP_AIRTIME_WFP="wfpAirTimeRequestPushToQueue";
	public static Map<String, Map<String, GwErrConverter>> GW_ERROR_CONVERT_MAP;
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
		GW_ERROR_CONVERT_MAP=new ConcurrentHashMap<String, Map<String,GwErrConverter>>();
		Object p=config.getProperty("gateway.airtime.error-convert.method[@id]");
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	String method=config.getString("gateway.airtime.error-convert.method("+i+")[@id]");
            	Map<String,GwErrConverter> typeConverts=new HashMap<String, GwErrConverter>();
            	Object p1=config.getProperty("gateway.airtime.error-convert.method("+i+").type[@id]");
            	if (p1 instanceof Collection) {
                    Collection c1 = (Collection) p1;
                    for (int i1 = 0; i1 < c1.size(); i1++) {
                    	String type=config.getString("gateway.airtime.error-convert.method("+i+").type("+i1+")[@id]");
                    	GwErrConverter convertor=new GwErrConverter();
                    	convertor.setDefaultError(config.getString("gateway.airtime.error-convert.method("+i+").type("+i1+").default-error"));
                    	convertor.setBypassList(config.getList("gateway.airtime.error-convert.method("+i+").type("+i1+").bypass-error"));
                    	typeConverts.put(type, convertor);
                    }
            	}else{
            		String type=config.getString("gateway.airtime.error-convert.method("+i+").type[@id]");
                	GwErrConverter convertor=new GwErrConverter();
                	convertor.setDefaultError(config.getString("gateway.airtime.error-convert.method("+i+").type.default-error"));
                	convertor.setBypassList(config.getList("gateway.airtime.error-convert.method("+i+").type.bypass-error"));
                	typeConverts.put(type, convertor);
            	}
            	GW_ERROR_CONVERT_MAP.put(method, typeConverts);
            }
            
        }else{
           	String method=config.getString("gateway.airtime.error-convert.method[@id]");
        	Map<String,GwErrConverter> typeConverts=new HashMap<String, GwErrConverter>();
        	Object p1=config.getProperty("gateway.airtime.error-convert.method.type[@id]");
        	if (p1 instanceof Collection) {
                Collection c1 = (Collection) p1;
                for (int i1 = 0; i1 < c1.size(); i1++) {
                	String type=config.getString("gateway.airtime.error-convert.method.type("+i1+")[@id]");
                	GwErrConverter convertor=new GwErrConverter();
                	convertor.setDefaultError(config.getString("gateway.airtime.error-convert.method.type("+i1+").default-error"));
                	convertor.setBypassList(config.getList("gateway.airtime.error-convert.method.type("+i1+").bypass-error"));
                	typeConverts.put(type, convertor);
                }
        	}else{
        		String type=config.getString("gateway.airtime.error-convert.method.type[@id]");
            	GwErrConverter convertor=new GwErrConverter();
            	convertor.setDefaultError(config.getString("gateway.airtime.error-convert.method.type.default-error"));
            	convertor.setBypassList(config.getList("gateway.airtime.error-convert.method.type.bypass-error"));
            	typeConverts.put(type, convertor);
        	}
        	GW_ERROR_CONVERT_MAP.put(method, typeConverts);
        }
		
		log.info(GW_ERROR_CONVERT_MAP);
	}
	public static void main(String[] args) {
		
	}
}