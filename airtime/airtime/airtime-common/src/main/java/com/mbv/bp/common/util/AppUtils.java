package com.mbv.bp.common.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.airtime.CdrSyncDao;
import com.mbv.bp.common.dao.airtime.SimTransactionDao;
import com.mbv.bp.common.dao.airtime.SmsContentDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.util.DateUtils.Operation;
import com.mbv.bp.common.util.DateUtils.Type;
import com.mbv.bp.common.vo.airtime.CdrSync;
import com.mbv.bp.common.vo.airtime.CdrSyncFilter;
import com.mbv.bp.common.vo.airtime.SimTransaction;
import com.mbv.bp.common.vo.airtime.SimTransactionFilter;
import com.mbv.bp.common.vo.airtime.SmsContent;
import com.mbv.bp.common.vo.airtime.SmsContentFilter;



public class AppUtils{
	
	private static Log log=LogFactory.getLog(AppUtils.class);
	
	public static String getAtTxnId(Date date, String seq){
		return DateUtils.convertDatetoString(date, "yyyyMMdd") +StringUtils.repeat("0",10-seq.length()) + seq;
	}
	
	public static String getViettelId(Date date, String seq){
		return DateUtils.convertDatetoString(date, "yyMMdd")+"0" +StringUtils.repeat("0",8-seq.length()) + seq;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Object create(String name) throws Exception {
        if (StringUtils.isBlank(name))
        	return null;
        ClassLoader clazzLoader = AppUtils.class.getClassLoader();
        try { 
        	Class clazz = clazzLoader.loadClass(name);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new Exception("Unable to create object: " + name, e);
        }
    }
	
	private static InternetAddress[] buildInternetAddressArray(String address)
	  throws AddressException
	  {
	    InternetAddress[] internetAddressArray = null;
	    try
	    {
	      internetAddressArray = InternetAddress.parse(address);
	    }
	    catch (AddressException ae)
	    {
	      // do logging here
	      throw ae;
	    }
	    return internetAddressArray;
	  }
	public static void sendEmail(String host, int port, String from,String to,String cc, String bcc, String subject, String content) throws Exception {
		
		if(AppConstants.NOTIFICATION_SETTINGS.isLogEmail()){
			log.info("********* Going to send notification email **********");
			log.info("smtp host: "+host+":"+port);
			log.info("from: "+from);
			log.info("to: "+to);
			log.info("cc: "+cc);
			log.info("bcc: "+bcc);
			log.info("subject: "+subject);
			log.info("content:"+content);
		}
		
		if (StringUtils.isBlank(from))
			throw new Exception("Not found from address in request.");
		
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "false");
	    props.put("mail.smtp.starttls.enable", "false");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", port); 
        Session mailSession = Session.getDefaultInstance(props, null);
        Transport transport = mailSession.getTransport("smtp");
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(from));
        message.setSubject(subject);
        message.setContent(content, "text/html; charset=UTF-8");
        if (StringUtils.isNotBlank(to)){
	       InternetAddress[] addressArray = buildInternetAddressArray(to);
	       if ((addressArray != null) && (addressArray.length > 0)){
	    	   message.setRecipients(RecipientType.TO, addressArray);
	       }else 
	    	   throw new Exception("Not found to address in request.");
        }else 
	    	   throw new Exception("Not found to address in request.");
       
        if (StringUtils.isNotBlank(cc)){
 	       InternetAddress[] addressArray = buildInternetAddressArray(cc);
 	       if ((addressArray != null) && (addressArray.length > 0)){
 	    	   message.setRecipients(RecipientType.CC, addressArray);
 	       }
        }
        
        if (StringUtils.isNotBlank(bcc)){
  	       InternetAddress[] addressArray = buildInternetAddressArray(bcc);
  	       if ((addressArray != null) && (addressArray.length > 0)){
  	    	   message.setRecipients(RecipientType.BCC, addressArray);
  	       }
        }
        
       transport.connect(host, port, null, null);
       transport.send(message);
       transport.close();
	}

	public static String prepareContentFromTemplate(String key, String value, String source) {
		return StringUtils.replace(source, "$"+key, value);
	}

	public static String prepareContentFromTemplate(ContextBase context,
			String source) {
		String content=source;
		if (context!=null)
			for (String key:context.keySet()){
				content=prepareContentFromTemplate(key, context.get(key), content);
			}
		return content;
	}
	
	public static boolean exportCdrFile(CdrSyncFilter filter, String fileName) throws Exception {
		boolean result =true;
		log.info("Going to generate Cdr file| Filter: " +filter+"| FileName:"+fileName);
		CdrSyncDao viewDao=new CdrSyncDao();
		List<CdrSync> txnList; 
		FileWriter outFile;
		PrintWriter out;
		boolean hasResult=false;
		try{
			outFile= new FileWriter(fileName);
			out = new PrintWriter(outFile);
		}catch (IOException e) {
			log.error("Error while generating Cdr Comparison file | Fail to open FileWriter. | FileName-"+fileName);
			throw e;
		}
		try{
			int startReccord=0;
			filter.setRecordSize(50);
			StringBuffer stringBuffer;
			stringBuffer=new StringBuffer("");
			stringBuffer.append("No").append(",")
			.append("Txn Date").append(",")
			.append("Mobivi TxnId").append(",")
			.append("Msisdn").append(",")
			.append("Amount").append(",")
			.append("Price").append(",")
			.append("Provider").append(",")
			.append("MobiVi ErrorCode").append(",")
			.append("MobiVi Status").append(",")
			.append("Provider TxnId").append(",")
			.append("Provider ErrorCode").append(",")
			.append("Provider Status").append(",")
			.append("Comparison Result");
			out.println(stringBuffer.toString());
			int index=0;
			String conpareResult="";
			do{
				filter.setStartRecord(startReccord);
				txnList= viewDao.search(filter);
				if (txnList==null) txnList=new ArrayList<CdrSync>();
				for(int i=0;i<txnList.size();i++){
					CdrSync cdrSync=txnList.get(i);
					index++;
					stringBuffer=new StringBuffer("");
					
					if (StringUtils.equals(cdrSync.getStatus(), cdrSync.getpStatus()))
						conpareResult=AppConstants.TXN_STATUS_MATCHED;
					else
						conpareResult=AppConstants.TXN_STATUS_UNMATCHED;
					
					stringBuffer.append(index).append(",")
									.append(DateUtils.convertDatetoString(DateUtils.dateAdd(cdrSync.getTxnDate(),7,Type.BY_HOUR,Operation.PLUS),"dd/MM/yyyy HH:mm:ss")).append(",")
									.append(cdrSync.getAtTxnId()==null?"":cdrSync.getAtTxnId()).append(",")
									.append(cdrSync.getMsisdn()==null?"":cdrSync.getMsisdn()).append(",")
									.append(cdrSync.getAmount()==null?"":cdrSync.getAmount()).append(",")
									.append(cdrSync.getpPrice()==null?"":cdrSync.getpPrice()).append(",")
									.append(cdrSync.getProviderId()==null?"":cdrSync.getProviderId()).append(",")
									.append(cdrSync.getErrorCode()==null?"":cdrSync.getErrorCode()).append(",")
									.append(cdrSync.getStatus()==null?"":cdrSync.getStatus()).append(",")
									.append(cdrSync.getpTxnId()==null?"":cdrSync.getpTxnId()).append(",")
									.append(cdrSync.getpErrorCode()==null?"":cdrSync.getpErrorCode()).append(",")
									.append(cdrSync.getpStatus()==null?"":cdrSync.getpStatus()).append(",")
									.append(conpareResult);
					out.println(stringBuffer.toString());
					hasResult=true;
					
				}
				startReccord+=txnList.size();
			}while (txnList.size()==filter.getRecordSize());
			out.close();
			out=null;
			log.info("File created. | Total record: "+startReccord+"| FileName: "+fileName);
		}catch (Exception e) {
			log.error("Error while generate Cdr file.",e);
			throw e;
		}finally{
			try{
				if (out!=null)
					out.close();
			}catch (Exception e) {
				log.error("Fail to close FileWriter",e);
			}
		}
		if (!hasResult){
			log.info("No transaction | Filter: "+filter);
			return false;
		}
		
		return result;
	}
	
	public static boolean exportAnypaySms(SmsContentFilter filter, String fileName) throws Exception {
		boolean result =true;
		log.info("Going to generate Cdr file| Filter: " +filter+"| FileName:"+fileName);
		SmsContentDao viewDao=new SmsContentDao();
		List<SmsContent> txnList; 
		FileWriter outFile;
		PrintWriter out;
		boolean hasResult=false;
		try{
			outFile= new FileWriter(fileName);
			out = new PrintWriter(outFile);
		}catch (IOException e) {
			log.error("Error while generating Cdr Comparison file | Fail to open FileWriter. | FileName-"+fileName);
			throw e;
		}
		try{
			int startReccord=0;
			filter.setRecordSize(50);
			StringBuffer stringBuffer;
			stringBuffer=new StringBuffer("");
			
			stringBuffer
			.append("No").append(",")
			.append("Message Date").append(",")
			.append("Message Id").append(",")
			.append("Msisdn").append(",")
			.append("Amount").append(",")
			.append("Txn Type").append(",")
			.append("Txn Status").append(",")
			.append("Sim No.").append(",")
			.append("Sim Amount").append(",")
			.append("PartId").append(",")
			.append("Total Part").append(",")
			.append("PartNo").append(",")
			.append("Process").append(",")
			.append("Sender").append(",")
			.append("Fraud Status").append(",")
			.append("Text").append(",")
			.append("PDU");
			out.println(stringBuffer.toString());
			
			int index=0;
			do{
				filter.setStartRecord(startReccord);
				txnList= viewDao.search(filter);
				if (txnList==null) txnList=new ArrayList<SmsContent>();
				for(int i=0;i<txnList.size();i++){
					SmsContent record=txnList.get(i);
					index++;
					stringBuffer=new StringBuffer("");
					
					stringBuffer.append(index).append(",")
									.append(DateUtils.convertDatetoString(DateUtils.dateAdd(record.getMsgDate(),7,Type.BY_HOUR,Operation.PLUS),"dd/MM/yyyy HH:mm:ss")).append(",")
									.append(record.getMessageId()==null?"":record.getMessageId()).append(",")
									.append(record.getMsisdn()==null?"":record.getMsisdn()).append(",")
									.append(record.getTxnAmount()==null?"":record.getTxnAmount()).append(",")
									.append(record.getTxnType()==null?"":record.getTxnType()).append(",")
									.append(record.getTxnStatus()==null?"":record.getTxnStatus()).append(",")
									.append(record.getSimId()==null?"":record.getSimId()).append(",")
									.append(record.getSmsAmount()==null?"":record.getSmsAmount()).append(",")
									.append(record.getPartId()==null?"":record.getPartId()).append(",")
									.append(record.getTotalPart()==null?"":record.getTotalPart()).append(",")
									.append(record.getPartNo()==null?"":record.getPartNo()).append(",")
									.append(record.getProcessed()==null?"":record.getProcessed()).append(",")
									.append(record.getSender()==null?"":record.getSender()).append(",")
									.append(record.getFraudStatus()==null?"":record.getFraudStatus()).append(",")
									.append(record.getTxtContent()==null?"":record.getTxtContent().replace(",", ".")).append(",")
									.append(record.getOrgContent());
					out.println(stringBuffer.toString());
					hasResult=true;
					
				}
				startReccord+=txnList.size();
			}while (txnList.size()==filter.getRecordSize());
			out.close();
			out=null;
			log.info("File created. | Total record: "+startReccord+"| FileName: "+fileName);
		}catch (Exception e) {
			log.error("Error while generate CSV file.",e);
			throw e;
		}finally{
			try{
				if (out!=null)
					out.close();
			}catch (Exception e) {
				log.error("Fail to close FileWriter",e);
			}
		}
		if (!hasResult){
			log.info("No transaction | Filter: "+filter);
			return false;
		}
		
		return result;
	}
	
	public static boolean exportAnypayTxn(SimTransactionFilter filter, String fileName) throws Exception {
		boolean result =true;
		log.info("Going to generate Cdr file| Filter: " +filter+"| FileName:"+fileName);
		SimTransactionDao viewDao=new SimTransactionDao();
		List<SimTransaction> txnList; 
		FileWriter outFile;
		PrintWriter out;
		boolean hasResult=false;
		try{
			outFile= new FileWriter(fileName);
			out = new PrintWriter(outFile);
		}catch (IOException e) {
			log.error("Error while generating Cdr Comparison file | Fail to open FileWriter. | FileName-"+fileName);
			throw e;
		}
		try{
			int startReccord=0;
			filter.setRecordSize(50);
			StringBuffer stringBuffer;
			stringBuffer=new StringBuffer("");
			stringBuffer.append("No").append(",")
			.append("Txn Date").append(",")
			.append("Txn Id").append(",")
			.append("Msisdn").append(",")
			.append("Amount").append(",")
			.append("Txn Type").append(",")
			.append("Error Code").append(",")
			.append("Message Id").append(",")
			.append("Sim No.").append(",")
			.append("Sim Amount").append(",")
			.append("Sim Pending Amount").append(",")
			.append("Billing Status").append(",")
			.append("Txn Status");
			out.println(stringBuffer.toString());
			int index=0;
			do{
				filter.setStartRecord(startReccord);
				txnList= viewDao.search(filter);
				if (txnList==null) txnList=new ArrayList<SimTransaction>();
				for(int i=0;i<txnList.size();i++){
					SimTransaction record=txnList.get(i);
					index++;
					stringBuffer=new StringBuffer("");
					
					stringBuffer.append(index).append(",")
									.append(DateUtils.convertDatetoString(DateUtils.dateAdd(record.getTxnDate(),7,Type.BY_HOUR,Operation.PLUS),"dd/MM/yyyy HH:mm:ss")).append(",")
									.append(record.getTxnId()==null?"":record.getTxnId()).append(",")
									.append(record.getMsisdn()==null?"":record.getMsisdn()).append(",")
									.append(record.getAmount()==null?"":record.getAmount()).append(",")
									.append(record.getTxnType()==null?"":record.getTxnType()).append(",")
									.append(record.getErrorCode()==null?"":record.getErrorCode()).append(",")
									.append(record.getMessageId()==null?"":record.getMessageId()).append(",")
									.append(record.getSimId()==null?"":record.getSimId()).append(",")
									.append(record.getSimAmount()==null?"":record.getSimAmount()).append(",")
									.append(record.getLockAmount()==null?"":record.getLockAmount()).append(",")
									.append(record.getBilling()==null?"":record.getBilling()).append(",")
									.append(record.getTxnStatus());
					out.println(stringBuffer.toString());
					hasResult=true;
					
				}
				startReccord+=txnList.size();
			}while (txnList.size()==filter.getRecordSize());
			out.close();
			out=null;
			log.info("File created. | Total record: "+startReccord+"| FileName: "+fileName);
		}catch (Exception e) {
			log.error("Error while generate CSV file.",e);
			throw e;
		}finally{
			try{
				if (out!=null)
					out.close();
			}catch (Exception e) {
				log.error("Fail to close FileWriter",e);
			}
		}
		if (!hasResult){
			log.info("No transaction | Filter: "+filter);
			return false;
		}
		
		return result;
	}
	
	
	public static boolean moveFile(String oldPath, String newPath){
		try{
			File f = new File(oldPath); // backup of this source file.
	 	    return f.renameTo(new File(newPath));
		}catch (Exception e) {
			log.error("fail to rename file",e);
			return false;
		}
	}
	
	public static boolean isVaildTimeOut(ContextBase context, boolean isCtxModifiable){
		int timeOut=context.getInt(Attributes.ATTR_TIME_OUT);
		if (timeOut<0){
			if (isCtxModifiable)
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.REQUEST_TIME_OUT);
			return false;
		}
		
		Date txnDate=context.getDate(Attributes.ATTR_TRANSACTION_DATE);
		Date timeOutDate=DateUtils.dateAdd(txnDate, timeOut, Type.BY_MILLISECOND, Operation.PLUS);
		if (timeOutDate.before(new Date())){
			if (isCtxModifiable)
				context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.REQUEST_TIME_OUT);
			return false;
		}
		return true;
	}
}
