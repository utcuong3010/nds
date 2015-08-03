package com.mbv.airtime.report.launcher;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.mbv.airtime.report.IReport;
import com.mbv.airtime.report.ProviderAccountLoadMoneyReport;
import com.mbv.airtime.report.ProviderAccountSummaryReport;
import com.mbv.airtime.report.ProviderTxnSummaryReport;
import com.mbv.airtime.report.TxnInfoReport;
import com.mbv.airtime.report.constant.ReportConstant;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;

public class ReportLauncher {
	private static Log log=LogFactory.getLog(ReportLauncher.class); 
public static void main(String[] args){
	try{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		IReport report=null;
		Map<String, String> sysEnvs = System.getenv();  
		for(Entry<String, String> entry:sysEnvs.entrySet()){
			log.info(entry.getKey() +" " +entry.getValue());
		}
		String url="jdbc:mysql://"+sysEnvs.get("AIRTIME_DB_URL").substring(5)+"/"+sysEnvs.get("AIRTIME_DB_DBASE");  
		String user=sysEnvs.get("AIRTIME_DB_USER");
		String password=sysEnvs.get("AIRTIME_DB_PASSWORD");
		String basePath=System.getProperty("user.dir");
		
		Map<String, String> propertyMap=new HashMap<String, String>(); 
		propertyMap.put(ReportConstant.ATTR_DBASE_URL,url);
		propertyMap.put(ReportConstant.ATTR_DBASE_USER,user);
		propertyMap.put(ReportConstant.ATTR_DBASE_PWD,password); 
		propertyMap.put(ReportConstant.ATTR_QUERY_RECORD_SIZE,"100");
		propertyMap.put(ReportConstant.ATTR_REPORT_TEMP_DIR,basePath+"/excel_temp/");
		propertyMap.put(ReportConstant.ATTR_REPORT_DIR,"/tmp/");
		propertyMap.put(ReportConstant.ATTR_REPORT_FILE_NAME,sysEnvs.get("REPORT_NAME"));
		propertyMap.put(ReportConstant.ATTR_INPUT_PARA,sysEnvs.get("INPUT_PARA"));
		  Properties properties = new Properties();
		  properties.put("driver", "com.mysql.jdbc.Driver");
		  properties.put("jdbc.url.airtime",propertyMap.get(ReportConstant.ATTR_DBASE_URL) );
		  properties.put("username.airtime",propertyMap.get(ReportConstant.ATTR_DBASE_USER));
		  properties.put("password.airtime", propertyMap.get(ReportConstant.ATTR_DBASE_PWD));
		  properties.put("db.airtime.connection.initialSize", "5");
		  properties.put("db.airtime.connection.maxActive", "10");
		  properties.put("db.airtime.connection.minIdle", "5");
		  properties.put("db.airtime.connection.maxIdle", "5");
		  properties.put("db.airtime.connection.maxWait", "6000");
		  properties.put("db.airtime.connection.poolPreparedStatements", "true");
		  properties.put("db.airtime.connection.validationQuery", "select 0");
		  properties.put("db.airtime.connection.testOnBorrow", "true");
		  SqlMapClient sqlMapClient;
		  Reader reader = Resources.getResourceAsReader(AppConstants.AT_SQL_MAP_CONFIG_FILE);
		  sqlMapClient=SqlMapClientBuilder.buildSqlMapClient(reader, properties);
		  SqlConfig.initAtSqlMapInstance(sqlMapClient);
		if ("report.airtime.provider.account.loadmoney".equalsIgnoreCase(sysEnvs.get("REPORT_ID"))){
			propertyMap.put(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME,"Provider_Load_Money_Temp.xls");
			report=new ProviderAccountLoadMoneyReport();
		}else if ("report.airtime.provider.account.summary".equalsIgnoreCase(sysEnvs.get("REPORT_ID"))){
			report=new ProviderAccountSummaryReport();
			propertyMap.put(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME,"Provider_Accout_Summary_Temp.xls");
		}else if ("report.airtime.provider.txn.summary".equalsIgnoreCase(sysEnvs.get("REPORT_ID"))){
			report=new ProviderTxnSummaryReport();
			propertyMap.put(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME,"Provider_Txn_Summary_Temp.xls");
		}else if ("report.airtime.provider.txn.info".equalsIgnoreCase(sysEnvs.get("REPORT_ID"))){
			propertyMap.put(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME,"Txn_Info_Temp.xls");
			report=new TxnInfoReport();
		}else
			throw new Exception("Invalid request|errMsg-missing reportId");
		log.info("property - "+propertyMap);
		report.init(propertyMap, sqlMapClient);
		report.createReport();
		
	}catch (Exception e) {
		log.info("Fail to create report",e);
	}
}

}
