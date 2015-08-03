package com.mbv.airtime.report;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.airtime.report.constant.ReportConstant;
import com.mbv.airtime.report.utils.ExcelWriter;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;


public abstract class BaseReport implements IReport {
	protected Map<String, String> propertyMap;
	protected Log log=LogFactory.getLog(ProviderAccountLoadMoneyReport.class); 
	protected SqlMapClient sqlMapClient;
	public void addproperty(String key, String value){
		if (propertyMap==null) propertyMap=new HashMap<String, String>();
			propertyMap.put(key, value);
	}
	@Override
	public void init(Map<String, String> propertyMap, SqlMapClient sqlMapClient) {
		this.propertyMap=propertyMap;
		this.sqlMapClient=sqlMapClient;
		
	}
	
	public abstract void processInputParams();
	public abstract void process();
	@Override
	public String createReport() {
		processInputParams();
		process();
		processError();
		return propertyMap.get(ReportConstant.ATTR_REPORT_FILE_NAME);
	}
	
	public void processError() {
		if (!ReportConstant.STATUS_SUCCESS.equalsIgnoreCase(propertyMap.get(ReportConstant.ATTR_ERROR_CODE))){
			 ExcelWriter excelWriter=new ExcelWriter();
			 try {
				 File file;
			 	 file=new File(propertyMap.get(ReportConstant.ATTR_REPORT_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_FILE_NAME));
				 if (file.exists()) file.delete();
				 String reportTemp=propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME);
				 String reportFile=propertyMap.get(ReportConstant.ATTR_REPORT_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_FILE_NAME);
				 excelWriter.open(reportTemp,reportFile, null);
				 int startRow=4;
				 int startCol=0;
				 excelWriter.WriteString(startCol,++startRow,propertyMap.get(ReportConstant.ATTR_INPUT_PARA),excelWriter.formatDashDot(false,10));
				 excelWriter.WriteString(startCol,++startRow,"report error!|ErrMsg-"+propertyMap.get(ReportConstant.ATTR_ERROR_MSG),excelWriter.formatDashDot(false,10));
				 excelWriter.close();
				}catch (Exception ex){
			    	log.error(ex.getMessage(),ex);
			    }finally{
			    	excelWriter.close();
			    }
			
		}
	}
}
