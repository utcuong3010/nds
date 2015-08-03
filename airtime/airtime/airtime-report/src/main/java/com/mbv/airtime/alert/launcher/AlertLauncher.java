package com.mbv.airtime.alert.launcher;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.mbv.airtime.alert.IAlert;
import com.mbv.airtime.alert.ProviderNotEnoughMoneyAlert;
import com.mbv.airtime.alert.UnknownTxnStatusAlert;
import com.mbv.airtime.alert.constant.AlertConstant;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;

public class AlertLauncher {
	private static Log log=LogFactory.getLog(AlertLauncher.class); 
	public static void main(String[] args){
		try{
			  Map<String, String> sysEnvs = System.getenv();  
			  /*
			  sysEnvs=new HashMap<String, String>();
			  sysEnvs.put("AIRTIME_DB_URL", "inet:127.0.0.1:3306");
			  sysEnvs.put("AIRTIME_DB_DBASE", "airtime");
			  sysEnvs.put("AIRTIME_DB_USER", "root");
			  sysEnvs.put("AIRTIME_DB_PASSWORD", "Aesx5099");
			  sysEnvs.put("ALERT_FILE_PATH", "d:/test.xml");
			  sysEnvs.put("ALERT_TEMPLATE_DIR", "");
			  sysEnvs.put("ALERT_TEMPLATE_FILE", "unknownTxnAlert.ftl");
			  sysEnvs.put("ALERT_MIN_ACCOUNTS_CFG", "VNPAY:10000");
			  sysEnvs.put("AIRTIME_ALERT_ID", "system.airtime.unknown.txn");
			  */
			  if (log.isDebugEnabled()){
				for(Entry<String, String> entry:sysEnvs.entrySet()){
					log.debug(entry.getKey() +" " +entry.getValue());
				}
			  }
			  String url="jdbc:mysql://"+sysEnvs.get("AIRTIME_DB_URL").substring(5)+"/"+sysEnvs.get("AIRTIME_DB_DBASE");  
			  String user=sysEnvs.get("AIRTIME_DB_USER");
			  String password=sysEnvs.get("AIRTIME_DB_PASSWORD");
			  Map<String, String> propertyMap=new HashMap<String, String>(); 
			  propertyMap.put(AlertConstant.ATTR_DBASE_URL,url);
			  propertyMap.put(AlertConstant.ATTR_DBASE_USER,user);
			  propertyMap.put(AlertConstant.ATTR_DBASE_PWD,password); 
			  propertyMap.put(AlertConstant.ATTR_ALERT_OUTPUT_FILENAME,sysEnvs.get("ALERT_FILE_PATH"));
			  propertyMap.put(AlertConstant.ATTR_ALERT_TEMP_DIR,sysEnvs.get("ALERT_TEMPLATE_DIR"));
			  propertyMap.put(AlertConstant.ATTR_ALERT_TEMP_FILE,sysEnvs.get("ALERT_TEMPLATE_FILE"));
			  propertyMap.put(AlertConstant.ATTR_ALERT_MIN_ACCOUNT_STR,sysEnvs.get("ALERT_MIN_ACCOUNTS_CFG"));
			  propertyMap.put(AlertConstant.ATTR_ALERT_ID,sysEnvs.get("AIRTIME_ALERT_ID"));
			  
			  Properties properties = new Properties();
			  properties.put("driver", "com.mysql.jdbc.Driver");
			  properties.put("jdbc.url.airtime",propertyMap.get(AlertConstant.ATTR_DBASE_URL) );
			  properties.put("username.airtime",propertyMap.get(AlertConstant.ATTR_DBASE_USER));
			  properties.put("password", propertyMap.get(AlertConstant.ATTR_DBASE_PWD));
			  properties.put("db.airtime.connection.initialSize", "5");
			  properties.put("db.airtime.connection.maxActive", "10");
			  properties.put("db.airtime.connection.minIdle", "5");
			  properties.put("db.airtime.connection.maxIdle", "5");
			  properties.put("db.airtime.connection.maxWait", "6000");
			  properties.put("db.airtime.connection.poolPreparedStatements", "true");
			  properties.put("db.airtime.connection.validationQuery", "select 0");
			  properties.put("db.airtime.connection.testOnBorrow", "false");
			  SqlMapClient sqlMapClient;
			  Reader reader = Resources.getResourceAsReader(AppConstants.AT_SQL_MAP_CONFIG_FILE);
			  sqlMapClient=SqlMapClientBuilder.buildSqlMapClient(reader, properties);
			  SqlConfig.initAtSqlMapInstance(sqlMapClient);
			  IAlert alert=null;
			  if (propertyMap.get(AlertConstant.ATTR_ALERT_ID).equalsIgnoreCase(AlertConstant.PROVIDER_NOT_ENOUGH_MONEY_ALERT_ID))
				  alert=new ProviderNotEnoughMoneyAlert();
			  else if(propertyMap.get(AlertConstant.ATTR_ALERT_ID).equalsIgnoreCase(AlertConstant.UNKNOWN_TXN_ALERT_ID))
				  alert=new UnknownTxnStatusAlert();
			  else{
				  log.error("Unsupported alertId-"+propertyMap.get(AlertConstant.ATTR_ALERT_ID));
				  return;
			  }
			  alert.execute(propertyMap);
		}catch (Exception e) {
			log.info("Fail to process alert",e);
		}
	}

}
