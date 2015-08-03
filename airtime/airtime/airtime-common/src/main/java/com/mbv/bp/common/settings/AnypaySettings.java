package com.mbv.bp.common.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.model.PduSms;
import com.mbv.bp.common.model.SmsField;
import com.mbv.bp.common.model.SmsProfile;
import com.mbv.bp.common.vo.airtime.SmsContent;

public class AnypaySettings implements ISettings{
	private static Log log = LogFactory.getLog(AnypaySettings.class);
	private List<String> topupSuccessErrorList;
	public static final String TOPUP_TXN="TOPUP";
	public static final String TRANSFER_TXN="TRANSFER";
	public static final String UNKNOWN_TXN="UNKNOWN";
	public static final String GET_SMS = "GET_SMS";
	public static final String DEL_SMS = "DEL_SMS";
	private int baudRate=115200;
	private static String simServiceUrl;
	public List<SmsProfile> smsProfiles;
	
	
	public String getConnectionType() {
		return "ANYPAY";
	}
	@Override
	public void load(CompositeConfiguration config) {
		topupSuccessErrorList=new ArrayList<String>();
		topupSuccessErrorList.add("SUCCESS");
		topupSuccessErrorList.add("DELIVERY_RESPONSE_ERROR");
		
		List<SmsField> smsFields;
		SmsField smsField;
		Object p=config.getProperty("anypay.sms-profile.profile-id[@name]");
		smsProfiles=new ArrayList<SmsProfile>();
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	SmsProfile smsProfile=new SmsProfile();
            	smsProfile.setProfileId(config.getString("anypay.sms-profile.profile-id("+i+")[@name]"));
            	smsProfile.setStartWith(config.getString("anypay.sms-profile.profile-id("+i+").start-with"));
            	smsProfile.setSender(config.getString("anypay.sms-profile.profile-id("+i+").sender"));
            	smsProfile.setTxnType(config.getString("anypay.sms-profile.profile-id("+i+")[@TxnType]"));
            	smsProfile.setTxnStatus(config.getString("anypay.sms-profile.profile-id("+i+")[@TxnStatus]"));
            	smsFields=new ArrayList<SmsField>();
            	if (config.containsKey("anypay.sms-profile.profile-id("+i+").fields.field-id[@name]")){
            		Object p1=config.getProperty("anypay.sms-profile.profile-id("+i+").fields.field-id[@name]");
	            	if (p1 instanceof Collection) {
	            		 Collection c2 = (Collection) p1;
	                     for (int j = 0; j < c2.size(); j++) {
	                    	 smsField=new SmsField();
	                    	 smsField.setFieldId(config.getString("anypay.sms-profile.profile-id("+i+").fields.field-id("+j+")[@name]"));
	                    	 smsField.setStartWith(config.getString("anypay.sms-profile.profile-id("+i+").fields.field-id("+j+").start-with"));
	                    	 smsField.setEndWith(config.getString("anypay.sms-profile.profile-id("+i+").fields.field-id("+j+").end-with"));
	                    	 smsFields.add(smsField);
	                     }
	            	}else{
	            		 smsField=new SmsField();
	                   	 smsField.setFieldId(config.getString("anypay.sms-profile.profile-id("+i+").fields.field-id[@name]"));
	                   	 smsField.setStartWith(config.getString("anypay.sms-profile.profile-id("+i+").fields.field-id.start-with"));
	                   	 smsField.setEndWith(config.getString("anypay.sms-profile.profile-id("+i+").fields.field-id.end-with"));
	                   	 smsFields.add(smsField);
	            	}
	            }
            	smsProfile.setSmsFields(smsFields);
            	smsProfiles.add(smsProfile);
            }
        }else{
        	SmsProfile smsProfile=new SmsProfile();
        	smsProfile.setProfileId(config.getString("anypay.sms-profile.profile-id[@name]"));
        	smsProfile.setStartWith(config.getString("anypay.sms-profile.profile-id.start-with"));
        	smsProfile.setSender(config.getString("anypay.sms-profile.profile-id.sender"));
        	smsProfile.setTxnType(config.getString("anypay.sms-profile.profile-id[@TxnType]"));
        	smsProfile.setTxnStatus(config.getString("anypay.sms-profile.profile-id[@TxnStatus]"));
        	smsFields=new ArrayList<SmsField>();
        	if (config.containsKey("anypay.sms-profile.profile-id.fields.field-id[@name]")){
        		Object p1=config.getProperty("anypay.sms-profile.profile-id.fields.field-id[@name]");
            	if (p1 instanceof Collection) {
            		 Collection c2 = (Collection) p1;
                     for (int j = 0; j < c2.size(); j++) {
                    	 smsField=new SmsField();
                    	 smsField.setFieldId(config.getString("anypay.sms-profile.profile-id.fields.field-id("+j+")[@name]"));
                    	 smsField.setStartWith(config.getString("anypay.sms-profile.profile-id.fields.field-id("+j+").start-with"));
                    	 smsField.setEndWith(config.getString("anypay.sms-profile.profile-id.fields.field-id("+j+").end-with"));
                    	 smsFields.add(smsField);
                     }
            	}else{
            		 smsField=new SmsField();
                   	 smsField.setFieldId(config.getString("anypay.sms-profile.profile-id.fields.field-id[@name]"));
                   	 smsField.setStartWith(config.getString("anypay.sms-profile.profile-id.fields.field-id.start-with"));
                   	 smsField.setEndWith(config.getString("anypay.sms-profile.profile-id.fields.field-id.end-with"));
                   	 smsFields.add(smsField);
            	}
        	}
        	smsProfile.setSmsFields(smsFields);
        	smsProfiles.add(smsProfile);
        }
		simServiceUrl=config.getString("setting.anypay.anypay-gateway.url");
		if (config.containsKey("setting.anypay.baud-rate"))
			baudRate=config.getInt("setting.anypay.baud-rate");
		log.info(baudRate);
		log.info(simServiceUrl);
		log.info(smsProfiles);
		log.info("Load ok.");
	}

	public boolean isTopupSuccess(String errorCode) {
		return false;
	}

	public boolean isTopupDelivered(String errorCode) {
		if (StringUtils.isBlank(errorCode)) return false;
		return topupSuccessErrorList.contains(errorCode);
	}

	public int getResponseTimeOut() {
		return 30000;
	}

	public String getSimServiceUrl() {
		return simServiceUrl;
	}

	public SmsContent paser(PduSms pduSms) {
		
		SmsContent smsContent=new SmsContent();
		
		if (pduSms.getMsgDate()!=null)
			smsContent.setMsgDate(DateUtils.addHours(pduSms.getMsgDate(), -7));
		else
			smsContent.setMsgDate(new Date());
		
		smsContent.setOrgContent(pduSms.getPduMsg());
		smsContent.setTxtContent(pduSms.getTxtMsg());
		smsContent.setPartId(pduSms.getSeqId());
		smsContent.setPartNo(pduSms.getPartId());
		smsContent.setTotalPart(pduSms.getTotalPart());
		smsContent.setProcessed(AppConstants.NO_FLAG);
		smsContent.setSender(pduSms.getSenderAddress());
		smsContent.setFraudStatus(AppConstants.NO_FLAG);
		
		for(SmsProfile profile:smsProfiles){
			if (pduSms.getTxtMsg().startsWith(profile.getStartWith())){
				if (!profile.getSender().replace("+", "").equalsIgnoreCase(pduSms.getSenderAddress().replace("+", "")))
					smsContent.setFraudStatus(AppConstants.YES_FLAG);
				smsContent.setTxnType(profile.getTxnType());
				smsContent.setTxnStatus(profile.getTxnStatus());
				for (SmsField field:profile.getSmsFields()){
					if ("Msisdn".equalsIgnoreCase(field.getFieldId())){
						if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isNotEmpty(field.getEndWith())){
							smsContent.setMsisdn(StringUtils.substringBetween(pduSms.getTxtMsg(),field.getStartWith(), field.getEndWith()));
						}else if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isEmpty(field.getEndWith())){
							smsContent.setMsisdn(StringUtils.substringAfterLast(pduSms.getTxtMsg(),field.getStartWith()));
						}
						if (StringUtils.isNotBlank(smsContent.getMsisdn())){
							smsContent.setMsisdn("0"+smsContent.getMsisdn());
						}
					}else if ("TxnAmount".equalsIgnoreCase(field.getFieldId())){
						try{
							if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isNotEmpty(field.getEndWith())){
								smsContent.setTxnAmount(StringUtils.substringBetween(pduSms.getTxtMsg(),field.getStartWith(), field.getEndWith()).trim().replace(".", "").replace(",",""));
							}else if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isEmpty(field.getEndWith())){
								smsContent.setTxnAmount(StringUtils.substringAfterLast(pduSms.getTxtMsg(),field.getStartWith()).trim().replace(".", "").replace(",",""));
							}
						}catch (Exception e) {
							log.info("Fail to process amount from sms content");
						}
					}else if ("SimAmount".equalsIgnoreCase(field.getFieldId())){
						try{
							if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isNotEmpty(field.getEndWith())){
								smsContent.setSmsAmount(StringUtils.substringBetween(pduSms.getTxtMsg(),field.getStartWith(), field.getEndWith()).trim().replace(".", "").replace(",",""));
							}else if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isEmpty(field.getEndWith())){
								smsContent.setSmsAmount(StringUtils.substringAfterLast(pduSms.getTxtMsg(),field.getStartWith()).trim().replace(".", "").replace(",",""));
							}
						}catch (Exception e) {
							log.info("Fail to process amount from sms content");
						}
					}else if ("MessageId".equalsIgnoreCase(field.getFieldId())){
						try{
							if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isNotEmpty(field.getEndWith())){
								smsContent.setMessageId(StringUtils.substringBetween(pduSms.getTxtMsg(),field.getStartWith(), field.getEndWith()));
							}else if (StringUtils.isNotEmpty(field.getStartWith()) && StringUtils.isEmpty(field.getEndWith())){
								smsContent.setMessageId(StringUtils.substringAfterLast(pduSms.getTxtMsg(),field.getStartWith()));
							}
						}catch (Exception e) {
							log.info("Fail to process amount from sms content");
						}
					}
					
				}
			}
//			smsContent.setMessageId(mesageId);
//			smsContent.setFraudStatus(fraudStatus);
//			smsContent.setMsisdn(msisdn);
//			smsContent.setSmsAmount(smsAmount);
//			smsContent.setTxnAmount(txnAmount);
//			smsContent.setTxnStatus(txnStatus);
//			smsContent.setTxnType(txnType);
		}
		if (StringUtils.isBlank(smsContent.getTxnType()))
			smsContent.setTxnType(UNKNOWN_TXN);
		return smsContent;
	}
	public String getDeliveryQueueId() {
		return "ANYPAY";
	}
	public long getProcessSmsMaxTime() {
		
		return 20000;
	}
	public int getBaudRate() {
		// TODO Auto-generated method stub
		return baudRate;
	}
	
	
	
}
