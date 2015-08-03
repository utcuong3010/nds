package com.mbv.bp.common.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.executor.ExecutorCfg;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.settings.NotificationSettings;
import com.mbv.bp.common.settings.AnypaySettings;
import com.mbv.bp.common.settings.VascSettings;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.settings.ViettelSettings;
import com.mbv.bp.common.settings.VinaphoneSettings;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.settings.VtcSettings;

public class AppConstants extends AppConfig {
	public static final String YES_FLAG = "Y";
	public static final String NO_FLAG = "N";
	public static final String REFUND_FLAG ="R";
	public static Map<String,List<ExecutorCfg>> MODULE_EXECUTOR_CONFIG_MAP;
	public static String GLOBAL_DATE_FORMAT = "yyyyMMddHHmmss";
	public static String[] AIRTIME_LAUNCHER_CONTEXT_LOADERS;
	public static AtomicBoolean SYSTEM_IS_SHUTDOWN=new AtomicBoolean(false);
	public static String APP_ID = "AT_SYS";
	public static long INQUIRY_TXN_MAX_TIME_OUT = 360000;
	public static final String TXN_STATUS_PENDING = "PENDING";
	public static final String TXN_STATUS_DELIVERED = "DELIVERED";
	public static final String TXN_STATUS_SUCCESS = "SUCCESS";
	public static final String TXN_STATUS_UNKNOWN = "UNKNOWN";
	public static final String TXN_STATUS_DELIVERING = "DELIVERING";
	public static final String TXN_STATUS_ERROR = "ERROR";
	public static final String TXN_STATUS_RETRY_RESP = "RETRY_RESP";
	public static final String TXN_STATUS_MATCHED = "MATCHED";
	public static final String TXN_STATUS_UNMATCHED = "UNMATCHED";
	public static final String TXN_STATUS_INVALID_RES = "INVALID_RES";
	public static final String TOPUP_REQUEST="0200";
	public static final String TOPUP_RESPONSE="0210";
	public static final String TXN_OPERATION_INQUIRY="QUERY";
	public static final String TXN_OPERATION_RETRY_RESPONSE="RETRY_RESPONSE";
	public static final String TOPUP_RESPONSE_ADD_INFO="0210-48";
	public static final String NETWORK_REQUEST="0800";
	public static final String NETWORK_RESPONSE="0810";
	public static final String module="airtime-common";
	public static VnPaySettings VNPAY_SETTINGS=null;
	public static MobifoneSettings MOBIFONE_SETTINGS=null;
	public static ViettelSettings VIETTEL_SETTINGS=null;
	public static VietPaySettings VIETPAY_SETTINGS=null;
	public static NotificationSettings NOTIFICATION_SETTINGS=null;
	public static Map<String, TelcoProvider> TELCO_PROVIDER;
	public static Map<String, List<String>> PROVIDER_TELCOS;
	public static Map<String, String> AIRTIME_PROVIDER;
	public static final String DEFAULT_CLIENT_CONN_TYPE="COMMON";
	public static final String MOBIVI = "MOBIVI";
	public static final String VIETTEL = "VIETTEL";
	public static final String MOBIFONE = "MOBIFONE";
	public static final String VNPAY = "VNPAY";
	public static final String MOBIFONE_CONN_PROP_RECACHE = "MOBIFONE_CONN_PROP_RECACHE";
	public static final String VIETTEL_CONN_PROP_RECACHE = "VIETTEL_CONN_PROP_RECACHE";
	public static final String TELCO_PROVIDER_RECACHE = "TELCO_PROVIDER_RECACHE";
	public static final String NOTIFICATION_TEMPLATE_RECACHE = "NOTIFICATION_TEMPLATE_RECACHE";
	public static final String CHANGE_TXN_STATUS_OPERATION="CHANGE_TXN_STATUS";	
	public static final String AT_TXN_OPERATION="AT_TXN";
	public static final String VIETPAY = "VIETPAY";	
	public static final String VTC = "VTC";
	public static final String MOBILE_TOPUP = "MOBILE_TOPUP";
	public static final String GAME_TOPUP = "GAME_TOPUP";
	public static final String INQUIRY_GAME_TOPUP = "INQUIRY_GAME_TOPUP";
	public static final String GAME_GROUP_CODE="GAME";
	public static final String TELCO_GROUP_CODE="TELCO";
	public static AnypaySettings ANYPAY_SETTINGS = null;
	public static VascSettings VASC_SETTINGS = null;
	public static VinaphoneSettings VINAPHONE_SETTINGS = null;

	public static VtcSettings VTC_SETTINGS = null;
	
	
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
		loadConfig(getConfig("airtime","config"));
	    if (VNPAY_SETTINGS==null) 
	    	VNPAY_SETTINGS=new VnPaySettings();
	    VNPAY_SETTINGS.load(getConfig("vnpay","config"));
	    if (MOBIFONE_SETTINGS==null) 
	    	MOBIFONE_SETTINGS=new MobifoneSettings();
	    MOBIFONE_SETTINGS.load(getConfig("mobifone","config"));
	    
	    if (VIETTEL_SETTINGS==null) 
	    	VIETTEL_SETTINGS=new ViettelSettings();
	    VIETTEL_SETTINGS.load(getConfig("viettel","config"));
	    
	    if (VIETPAY_SETTINGS==null) 
	    	VIETPAY_SETTINGS=new VietPaySettings();
	    VIETPAY_SETTINGS.load(getConfig("vietpay","config"));
	    
	    if (ANYPAY_SETTINGS==null) 
	    	ANYPAY_SETTINGS=new AnypaySettings();
	    ANYPAY_SETTINGS.load(getConfig("anypay","config"));
	    
	    if (VTC_SETTINGS==null) 
	    	VTC_SETTINGS=new VtcSettings();
	    VTC_SETTINGS.load(getConfig("vtc","config"));
	    
	    if (VASC_SETTINGS==null) 
	    	VASC_SETTINGS=new VascSettings();
	    VASC_SETTINGS.load(getConfig("vasc","config"));
	    
	    if (VINAPHONE_SETTINGS==null) 
	    	VINAPHONE_SETTINGS=new VinaphoneSettings();
	    VINAPHONE_SETTINGS.load(getConfig("vinaphone","config"));
	    
	    if (NOTIFICATION_SETTINGS==null) 
	    	NOTIFICATION_SETTINGS=new NotificationSettings();
	    NOTIFICATION_SETTINGS.load(getConfig("notification","config"));
	    
	    
	}
	
	@SuppressWarnings("unchecked")
	private static void loadConfig(Configuration config) {
		
		SYSTEM_IS_SHUTDOWN.set(false);
		AIRTIME_LAUNCHER_CONTEXT_LOADERS=config.getStringArray("app-context-loader.airtime-launcher");
		MODULE_EXECUTOR_CONFIG_MAP = new ConcurrentHashMap<String, List<ExecutorCfg>>();
		Object p = config.getProperty("integation.client.gateway.module");
	    if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
                String module = config.getString("integation.client.gateway("+i+").module");
                ExecutorCfg executorCfg = new ExecutorCfg();
                executorCfg.setModule(module);
                executorCfg.setType(config.getString("integation.client.gateway("+i+").name"));
                executorCfg.setClientConnType(config.getString("integation.client.gateway("+i+").client-connection-type"));
                executorCfg.setPoolSize(config.getInt("integation.client.gateway("+i+").pool-size"));
                executorCfg.setLocalEnable(config.getBoolean("integation.client.gateway("+i+").local-enable"));
                if (!executorCfg.isLocalEnable()){
                	executorCfg.setHost(config.getString("integation.client.gateway("+i+").host"));
	                executorCfg.setPort(config.getInt("integation.client.gateway("+i+").port"));
                }else
                	executorCfg.setClazz(config.getString("integation.client.gateway("+i+").class"));
                
                if (MODULE_EXECUTOR_CONFIG_MAP.containsKey(module)) {
                	MODULE_EXECUTOR_CONFIG_MAP.get(module).add(executorCfg);
                } else {
                    List<ExecutorCfg> configList = new ArrayList<ExecutorCfg>();
                    configList.add(executorCfg);
                    MODULE_EXECUTOR_CONFIG_MAP.put(module, configList);
                }
            }
        } else {
        	String module = config.getString("integation.client.gateway.module");
            ExecutorCfg executorCfg = new ExecutorCfg();
            executorCfg.setType(config.getString("integation.client.gateway.name"));
            executorCfg.setClientConnType(config.getString("integation.client.gateway.client-connection-type"));
            executorCfg.setPoolSize(config.getInt("integation.client.gateway.pool-size"));
            executorCfg.setLocalEnable(config.getBoolean("integation.client.gateway.local-enable"));
            if (!executorCfg.isLocalEnable()){
            	executorCfg.setHost(config.getString("integation.client.gateway.host"));
                executorCfg.setPort(config.getInt("integation.client.gateway.port"));
            }else
            	executorCfg.setClazz(config.getString("integation.client.gateway.class"));
                       
            
            if (MODULE_EXECUTOR_CONFIG_MAP.containsKey(module)) {
            	MODULE_EXECUTOR_CONFIG_MAP.get(module).add(executorCfg);
            } else {
                List<ExecutorCfg> configList = new ArrayList<ExecutorCfg>();
                configList.add(executorCfg);
                MODULE_EXECUTOR_CONFIG_MAP.put(module, configList);
            }
        }
	    
	    reload(config);
		
	    log.info(MODULE_EXECUTOR_CONFIG_MAP);
	}
	

	public static boolean reload() {
		try{
			return reload(getConfig("airtime","config"));
		}catch (Exception e) {
			log.error("Fail to reload AppConstants",e);
			return false;
		}
	}	
	public static boolean reloadNotifTemplate(){
		try{
			NotificationSettings notificationSettings=new NotificationSettings();
		    notificationSettings.load(getConfig("notification","config"));
		    NOTIFICATION_SETTINGS=notificationSettings;
		    return true;
		}catch (Exception e) {
			log.error("Fail to load notif template");
			 return false;
		}
	}
	
	public static boolean reload(Configuration config){
			Map<String, List<String>> providerTelcosMap=new HashMap<String, List<String>>();
		    long inquiryMaxTimeOut =config.getLong("airtime.constant.inquiry-max-timeout",360000);
		    Map<String, TelcoProvider> telcoProviders =new ConcurrentHashMap<String, TelcoProvider>();
			Object p=config.getProperty("airtime.constant.telco-provider.provider[@id]");
			if (p instanceof Collection) {
	            Collection c = (Collection) p;
	            for (int i = 0; i < c.size(); i++) {
	            	TelcoProvider provider=new TelcoProvider();
	            	provider.setProviderId(config.getString("airtime.constant.telco-provider.provider("+i+")[@id]").toUpperCase());
	            	provider.setProviderName(config.getString("airtime.constant.telco-provider.provider("+i+")[@name]"));
	            	provider.setGroup(config.getString("airtime.constant.telco-provider.provider("+i+")[@group]",""));
	            	provider.setConnectionIds(config.getStringArray("airtime.constant.telco-provider.provider("+i+").connection-id"));
	            	telcoProviders.put(provider.getProviderId(), provider);
	            	for (String providerId:provider.getConnectionIds()){
	            		if (providerTelcosMap.get(providerId)==null)
	            			providerTelcosMap.put(providerId, new ArrayList<String>());
	            		if (!providerTelcosMap.get(providerId).contains(provider.getProviderId()))
	            			providerTelcosMap.get(providerId).add(provider.getProviderId());
	            	}
	            }
	        }else{
	        	TelcoProvider provider=new TelcoProvider();
	        	provider.setProviderId(config.getString("airtime.constant.telco-provider.provider[@id]").toUpperCase());
	        	provider.setProviderName(config.getString("airtime.constant.telco-provider.provider[@name]"));
	        	provider.setGroup(config.getString("airtime.constant.telco-provider.provider[@group]",""));
	        	provider.setConnectionIds(config.getStringArray("airtime.constant.telco-provider.provider.connection-id"));
	        	telcoProviders.put(provider.getProviderId(), provider);
	        	for (String providerId:provider.getConnectionIds()){
            		if (providerTelcosMap.get(providerId)==null)
            			providerTelcosMap.put(providerId, new ArrayList<String>());
            		if (!providerTelcosMap.get(providerId).contains(provider.getProviderId()))
            			providerTelcosMap.get(providerId).add(provider.getProviderId());
            	}
	        }
			
			if (PROVIDER_TELCOS!=null)
				PROVIDER_TELCOS.clear();
			PROVIDER_TELCOS=providerTelcosMap;
			log.info(PROVIDER_TELCOS);
			
			INQUIRY_TXN_MAX_TIME_OUT =inquiryMaxTimeOut;
			
			if (TELCO_PROVIDER!=null)
				TELCO_PROVIDER.clear();
			TELCO_PROVIDER=telcoProviders;
			log.info(TELCO_PROVIDER);
			
			if (AIRTIME_PROVIDER==null)
				AIRTIME_PROVIDER =new ConcurrentHashMap<String, String>();
			AIRTIME_PROVIDER.clear();
			p=config.getProperty("airtime.constant.airtime-provider.provider[@id]");
			String key;
			String value;
			if (p instanceof Collection) {
	            Collection c = (Collection) p;
	            for (int i = 0; i < c.size(); i++) {
	            	key=config.getString("airtime.constant.airtime-provider.provider("+i+")[@id]").toUpperCase();
	            	value=config.getString("airtime.constant.airtime-provider.provider("+i+")[@name]");
	            	AIRTIME_PROVIDER.put(key, value);
	            }
	        }else{
	        	key=config.getString("airtime.constant.airtime-provider.provider[@id]").toUpperCase();
	        	value=config.getString("airtime.constant.airtime-provider.provider[@name]");
	        	AIRTIME_PROVIDER.put(key, value);
	        }
			log.info(AIRTIME_PROVIDER);
		return true;
		
	}
	

	public static void main(String[] args) {
		
	}


}