package com.mbv.frontend.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.mbv.bp.common.util.DateUtil;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.ReportUtils;



public class ReportController extends ActionControllerBase {
	private Log log=LogFactory.getLog(ReportController.class);
	private static final long serialVersionUID = 1L;
	private String reportId;
	private String fileName;
	
	public String checkRpt(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		if (reportId==null) return "success";
		fileName=DateUtil.convertDatetoString(new Date(), "yyyyMMdd")+"_"+reportId+"_"+fileName+".zip";
		String status=ReportUtils.retrieveReport(reportId,request.getRealPath(FeConstant.REPORT_TEMP_PATH)+"/" +fileName);
		
		resultMap.put("error", status);
		resultMap.put("fileName", fileName);
		resultMap.put("reportId", reportId);
		System.out.println(resultMap);
		System.out.println(request.getRealPath(FeConstant.REPORT_TEMP_PATH));
		return AppUtils.builJsonResult(request, resultMap, "success");
		
	}

	public String getReportId() {
		return reportId;  
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
