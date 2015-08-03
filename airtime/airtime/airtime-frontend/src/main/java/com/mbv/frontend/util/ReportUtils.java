package com.mbv.frontend.util;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;

import javax.xml.rpc.holders.StringHolder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.vo.airtime.AtSummaryFilter;
import com.mbv.bp.common.vo.airtime.AtTransactionFilter;
import com.mbv.bp.common.vo.airtime.ProviderAccountFilter;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.report.service.ReportserviceLocator;
import com.mbv.frontend.report.service.ReportserviceStub;

public class ReportUtils {
	private static Log log=LogFactory.getLog(ReportUtils.class);
	private static String inputDateTimeFormat="yyyy-MM-dd HH:mm:ss";
	public static String createReport(String reportRequest,String inputParameters){
		try{		
	        ReportserviceStub binding;
	        binding = (ReportserviceStub)new ReportserviceLocator().getreportservice();
			return binding.createReport("AUTH_TYPE_SIGNATURE","AUTH_INFO", reportRequest, inputParameters);
		}catch (Exception e) {
			log.error("Error in createReport| reportRequest-"+reportRequest+"| inputParameters-"+inputParameters,e);
			return "ERROR";
		}
    }
	
	public static String retrieveReport(String reportId,String fileName){
		String status="";
		try{
	        ReportserviceStub binding;
	        binding = (ReportserviceStub)new ReportserviceLocator().getreportservice();
			javax.xml.rpc.holders.StringHolder reportStatus = new StringHolder("");
			javax.xml.rpc.holders.StringHolder reportData = new StringHolder("");
			javax.xml.rpc.holders.StringHolder reportID = new StringHolder(reportId);
			binding.retrieveReport("AUTH_TYPE_SIGNATURE", "AUTH_INFO", reportID, 5, reportStatus, reportData);
//			System.out.println("reportID :"+reportID.value);
//			System.out.println("reportStatus :"+reportStatus.value);
//			System.out.println("reportData :"+reportData.value);
			if (reportStatus!=null)
				status=reportStatus.value;
			if ("DONE".equalsIgnoreCase(status)){
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(fileName));	        
		        byte[] decoded = Base64.decodeBase64(reportData.value.getBytes());	        
			    out.write(decoded);
			    out.close();
			}else{
				if (!"IN_PROGRESS".equalsIgnoreCase(status))
					status="ERROR";
			}
		}catch (Exception e) {
			log.error("Error in retrieveReport| reportId-"+reportId+"| fileName-"+fileName,e);
			status="ERROR";
		}
	    return status;
    }
	
	
	public static String createReportHeader(String reportId,String reportName,String creator,String lifeTime) {
       String reportRequest =  "<reportRequest>"; 
	    	   reportRequest += 	"<type>"+reportId+"</type>"; 
	    	   reportRequest += 	"<name>"+reportName+"."+DateUtils.convertDatetoString(new Date(), "dd.MM.HH.mm.ss")+".xls"+"</name>"; 
	    	   reportRequest += 	"<creator>"+creator+"</creator>"; 
	    	   reportRequest += 	"<dataDomain>"+FeConstant.REPORT_DOMAIN+"</dataDomain>"; 
	    	   reportRequest += 	"<lifeTime>"+lifeTime+"</lifeTime>"; 
	    	   reportRequest += "</reportRequest>"; 
    	return reportRequest;
    	
    }
	
	public static String providerAmountRptParams(ProviderAccountFilter filter){
		String inputParameters = "<para>"; 
		if (StringUtils.isBlank(filter.getProviderId()))
			inputParameters+="<providerId></providerId>"; 
		else
			inputParameters+="<providerId>"+filter.getProviderId()+"</providerId>"; 
		
		if (StringUtils.isBlank(filter.getTxnId()))
			inputParameters+="<txnId></txnId>"; 
		else
			inputParameters+="<txnId>"+filter.getTxnId()+"</txnId>";
		
		if (StringUtils.isBlank(filter.getEmployee()))
			inputParameters +="<employee></employee>";
		else 
			inputParameters +="<employee>"+filter.getEmployee()+"</employee>";
		
    	if (filter.getFromDate()!=null)
    		inputParameters +="<date.from>"+DateUtils.convertDatetoString(filter.getFromDate(),inputDateTimeFormat)+"</date.from>";
    	else 
    		inputParameters +="<date.from></date.from>";
    	
    	if (filter.getToDate()!=null)
    		inputParameters +="<date.to>"+DateUtils.convertDatetoString(filter.getToDate(),inputDateTimeFormat)+"</date.to>";
    	else
    		inputParameters +="<date.to></date.to>";
    	
    	inputParameters +="</para>";
    	return inputParameters;
	}

	public static String providerAccountSummaryRptParams(AtSummaryFilter filter) {
		String inputParameters = "<para>"; 
		if (StringUtils.isBlank(filter.getProviderId()))
			inputParameters+="<providerId></providerId>"; 
		else
			inputParameters+="<providerId>"+filter.getProviderId()+"</providerId>"; 
		
		if (filter.getFromDate()!=null)
    		inputParameters +="<date.from>"+DateUtils.convertDatetoString(filter.getFromDate(),inputDateTimeFormat)+"</date.from>";
    	else 
    		inputParameters +="<date.from></date.from>";
    	
    	if (filter.getToDate()!=null)
    		inputParameters +="<date.to>"+DateUtils.convertDatetoString(filter.getToDate(),inputDateTimeFormat)+"</date.to>";
    	else
    		inputParameters +="<date.to></date.to>";
    	
    	inputParameters +="</para>";
    	return inputParameters;
	}

	public static String providerTxnSummaryRptParams(AtSummaryFilter filter) {
		String inputParameters = "<para>"; 
		if (StringUtils.isBlank(filter.getProviderId()))
			inputParameters+="<providerId></providerId>"; 
		else
			inputParameters+="<providerId>"+filter.getProviderId()+"</providerId>"; 
		
		if (filter.getFromDate()!=null)
    		inputParameters +="<date.from>"+DateUtils.convertDatetoString(filter.getFromDate(),inputDateTimeFormat)+"</date.from>";
    	else 
    		inputParameters +="<date.from></date.from>";
    	
    	if (filter.getToDate()!=null)
    		inputParameters +="<date.to>"+DateUtils.convertDatetoString(filter.getToDate(),inputDateTimeFormat)+"</date.to>";
    	else
    		inputParameters +="<date.to></date.to>";
    	
    	inputParameters +="</para>";
    	return inputParameters;
	}

	public static String txnInfoRptParams(AtTransactionFilter filter) {
		String inputParameters = "<para>"; 
		if (StringUtils.isBlank(filter.getConnType()))
			inputParameters+="<providerId></providerId>"; 
		else
			inputParameters+="<providerId>"+filter.getConnType()+"</providerId>"; 
		
		
		if (StringUtils.isBlank(filter.getMessageId()))
			inputParameters+="<messageId></messageId>"; 
		else
			inputParameters+="<messageId>"+filter.getMessageId()+"</messageId>"; 
		
		
		if (StringUtils.isBlank(filter.getChannelId()))
			inputParameters+="<channelId></channelId>"; 
		else
			inputParameters+="<channelId>"+filter.getChannelId()+"</channelId>"; 
		
		if (StringUtils.isBlank(filter.getTxnId()))
			inputParameters+="<txnId></txnId>"; 
		else
			inputParameters+="<txnId>"+filter.getTxnId()+"</txnId>"; 
		
		if (StringUtils.isBlank(filter.getTelcoId()))
			inputParameters+="<telcoId></telcoId>"; 
		else
			inputParameters+="<telcoId>"+filter.getTelcoId()+"</telcoId>"; 
		
		if (filter.getAtTxnId()==null)
			inputParameters+="<atTxnId></atTxnId>"; 
		else
			inputParameters+="<atTxnId>"+filter.getAtTxnId()+"</atTxnId>"; 
		
		if (StringUtils.isBlank(filter.getMsisdn()))
			inputParameters+="<msisdn></msisdn>"; 
		else
			inputParameters+="<msisdn>"+filter.getMsisdn()+"</msisdn>"; 
		
		if (filter.getAmount()==null)
			inputParameters+="<amount></amount>"; 
		else
			inputParameters+="<amount>"+filter.getAmount()+"</amount>";
		
		if (StringUtils.isBlank(filter.getTxnStatus()))
			inputParameters+="<txnStatus></txnStatus>"; 
		else
			inputParameters+="<txnStatus>"+filter.getTxnStatus()+"</txnStatus>";
		
    	if (filter.getFromDate()!=null)
    		inputParameters +="<date.from>"+DateUtils.convertDatetoString(filter.getFromDate(),inputDateTimeFormat)+"</date.from>";
    	else 
    		inputParameters +="<date.from></date.from>";
    	
    	if (filter.getToDate()!=null)
    		inputParameters +="<date.to>"+DateUtils.convertDatetoString(filter.getToDate(),inputDateTimeFormat)+"</date.to>";
    	else
    		inputParameters +="<date.to></date.to>";
    	
    	inputParameters +="</para>";
    	return inputParameters;
	}
}
