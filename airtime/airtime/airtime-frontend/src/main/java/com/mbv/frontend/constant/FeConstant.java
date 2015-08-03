package com.mbv.frontend.constant;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.loader.LoaderListener;

public class FeConstant {
	private static Log log = LogFactory.getLog(LoaderListener.class);
	public static String MODULE="AT_FE";
	public static String REPORT_DOMAIN = "AIRTIME";
	public static String REPORT_TEMP_PATH = "rpt_temp";
	public static String CDR_TEMP_PATH = "cdr";
	public static String REPORT_TEMP_FULL_PATH = "rpt_temp";
	public static int MAX_RECORD=20;
	public static String REPORT_SERVICE_URL="http://localhost:16510"; 
	public static String REPORT_PROVIDER_ACCOUNT_FILENAME="NapTien_Rpt";
	public static String REPORT_PROVIDER_ACCOUNT_RPT_ID="report.wallet.balance";
//	
	public static String REPORT_PROVIDER_ACCOUNT_SUMMARY_FILENAME="SoDuTKTong_Rpt"; 
	public static String REPORT_PROVIDER_TXN_SUMMARY_FILENAME="GiaoDichTKTong_Rpt"; 
	public static String REPORT_TXN_INFO_FILENAME="GiaoDichHeThong_Rpt"; 
	public static String REPORT_PROVIDER_ACCOUNT_SUMMARY_RPT_ID="report.wallet.balance"; 
	public static String REPORT_PROVIDER_TXN_SUMMARY_RPT_ID="report.wallet.balance"; 
	public static String REPORT_TXN_INFO_RPT_ID="report.wallet.balance"; 
	public static String ACCESS_CONTROL_ACCESS_TARGET_URL="access.control.target.url";
	public static String ACCESS_CONTROL_ACCESS_TARGET_APP_ID="access.control.target.type";
	public static int LOCAL_GMT_VALUE=7;
	public static void load(Properties prop) {
		REPORT_DOMAIN=prop.getProperty("report.domain");
		log.info("REPORT_DOMAIN - "+REPORT_DOMAIN);
		REPORT_TEMP_PATH=prop.getProperty("report.path");
		log.info("REPORT_TEMP_PATH - "+REPORT_TEMP_PATH);
		REPORT_SERVICE_URL=prop.getProperty("report.service.url");
		log.info("REPORT_SERVICE_URL - "+REPORT_SERVICE_URL);
		MAX_RECORD=Integer.valueOf(prop.getProperty("max.record.perpage"));
		log.info("MAX_RECORD - "+MAX_RECORD);
		REPORT_PROVIDER_ACCOUNT_FILENAME=prop.getProperty("report.provider.account.filename");
		log.info("REPORT_PROVIDER_ACCOUNT_FILENAME - "+REPORT_PROVIDER_ACCOUNT_FILENAME);
		REPORT_PROVIDER_ACCOUNT_RPT_ID=prop.getProperty("report.provider.account.id");
		log.info("REPORT_PROVIDER_ACCOUNT_RPT_ID - "+REPORT_PROVIDER_ACCOUNT_RPT_ID);
		REPORT_PROVIDER_ACCOUNT_SUMMARY_FILENAME=prop.getProperty("report.provider.account.summary.filename");
		log.info("REPORT_PROVIDER_ACCOUNT_SUMMARY_FILENAME - "+REPORT_PROVIDER_ACCOUNT_SUMMARY_FILENAME);
		REPORT_PROVIDER_ACCOUNT_SUMMARY_RPT_ID=prop.getProperty("report.provider.account.summary.id");
		log.info("REPORT_PROVIDER_ACCOUNT_SUMMARY_RPT_ID - "+REPORT_PROVIDER_ACCOUNT_SUMMARY_RPT_ID);
		REPORT_PROVIDER_TXN_SUMMARY_FILENAME=prop.getProperty("report.provider.txn.summary.filename");
		log.info("REPORT_PROVIDER_TXN_SUMMARY_FILENAME - "+REPORT_PROVIDER_TXN_SUMMARY_FILENAME);
		REPORT_PROVIDER_TXN_SUMMARY_RPT_ID=prop.getProperty("report.provider.txn.summary.id");
		log.info("REPORT_PROVIDER_TXN_SUMMARY_RPT_ID - "+REPORT_PROVIDER_TXN_SUMMARY_RPT_ID);
		REPORT_TXN_INFO_FILENAME=prop.getProperty("report.txn.info.filename");
		log.info("REPORT_TXN_INFO_FILENAME - "+REPORT_TXN_INFO_FILENAME);
		REPORT_TXN_INFO_RPT_ID=prop.getProperty("report.txn.info.id");
		log.info("REPORT_TXN_INFO_RPT_ID - "+REPORT_TXN_INFO_RPT_ID);
		
		ACCESS_CONTROL_ACCESS_TARGET_URL=prop.getProperty("access.control.target.url");
		log.info("ACCESS_CONTROL_ACCESS_TARGET_URL - "+ACCESS_CONTROL_ACCESS_TARGET_URL);
		ACCESS_CONTROL_ACCESS_TARGET_APP_ID=prop.getProperty("access.control.target.type");
		log.info("ACCESS_CONTROL_ACCESS_TARGET_APP_ID - "+ACCESS_CONTROL_ACCESS_TARGET_APP_ID);
		
		
		
	}
}
