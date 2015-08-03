package com.mbv.bp.common.settings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.model.ErrorConversion;
import com.mbv.bp.common.model.Field;
import com.mbv.bp.common.model.MessageInfo;

public class VnPaySettings implements ISettings,AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(VnPaySettings.class);
	private String connectionType;
	private long initialValue;
	private long maxValue;
	private long updateIncerment;
	private String queueId;
	private String seqName; 
	private String deliveryWfp;
	private String dnProcessorQueueId;
	private String dnProcessorWfp;
	private String networkCheckWfp;
	private String networkCheckDeliveryWfp;
	private int responseTimeOut;
	private int inquiryTime;
	private String addInfoPrefix;
	private String networkLoginCode;
	private String networkLogOutCode;
	private String networkCheckingCode;
	private String merchantType;
	private String agentCode;
	private String processingCode;
	private String sceretKey;
	private List<String> successRcList;
	private List<String> noResponseList;
	private String cdrProcessorWfp;
	private String cdrProcessorQueueId;
	private int maxSeqError;
	private boolean amountValidate;
	private Map<String, ErrorConversion> responseErrors;
	private Map<String, MessageInfo> messageInfoMaps;

	@SuppressWarnings("unchecked")
	@Override
	public void load(CompositeConfiguration config) {
		
		deliveryWfp=config.getString("settings.vnpay.delivery-wfp");
		dnProcessorQueueId=config.getString("settings.vnpay.dn-processor-queue-id");
		dnProcessorWfp=config.getString("settings.vnpay.dn-processor-wfp");
		networkCheckWfp=config.getString("settings.vnpay.network-check-wfp");
		networkCheckDeliveryWfp=config.getString("settings.vnpay.network-check-delivery-wfp");
		
		
		connectionType=config.getString("settings.vnpay.connection-type"); 
		seqName=config.getString("settings.vnpay.id-generator.sequence-name");
		initialValue=config.getLong("settings.vnpay.id-generator.initial-value");
		maxValue=config.getLong("settings.vnpay.id-generator.max-value");
		updateIncerment=config.getLong("settings.vnpay.id-generator.update-increment");
		queueId=config.getString("settings.vnpay.queue-id");
		
		responseTimeOut=config.getInt("settings.vnpay.response-time-out"); 
		inquiryTime=config.getInt("settings.vnpay.inquiry-time"); 
		addInfoPrefix=config.getString("settings.vnpay.additional-info-prefix");
		networkLoginCode=config.getString("settings.vnpay.network-login-code");
		networkLogOutCode=config.getString("settings.vnpay.network-logout-code");
		networkCheckingCode=config.getString("settings.vnpay.network-check-code");
		merchantType=config.getString("settings.vnpay.merchant-type");
		agentCode=config.getString("settings.vnpay.agent-code");
		processingCode=config.getString("settings.vnpay.topup-processing-code");
		sceretKey=config.getString("settings.vnpay.secret-key");
		
		cdrProcessorWfp=config.getString("settings.vnpay.cdr-processor-wfp","cdrProcessorWfp");
		cdrProcessorQueueId=config.getString("settings.vnpay.cdr-processor-queue-id","CDR_PROCESSSOR");
		maxSeqError=config.getInt("settings.vnpay.max-seq-error");
		amountValidate=config.getBoolean("settings.vnpay.amount-validate",false);

		String[] temps;
		temps=config.getStringArray("settings.vnpay.success.response-codes");
		successRcList=new ArrayList<String>();
		for (String rc:temps)
			successRcList.add(rc.trim());
		
		temps=config.getStringArray("settings.vnpay.no-response.response-codes");
		
		noResponseList=new ArrayList<String>();
		for (String rc:temps)
			noResponseList.add(rc.trim());
		
		messageInfoMaps=new ConcurrentHashMap<String, MessageInfo>();
		Object p;
		p=config.getProperty("settings.vnpay.messages.message[@type]");
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	MessageInfo messageInfo=new MessageInfo();
            	messageInfo.setMessageType(config.getString("settings.vnpay.messages.message("+i+")[@type]"));
            	messageInfo.setFieldMaps(new ConcurrentHashMap<Integer,Field>());
            	Object p1=config.getProperty("settings.vnpay.messages.message("+i+").field[@num]");
            	if (p1 instanceof Collection) {
		            Collection c1 = (Collection) p1;
		            for (int j = 0; j < c1.size(); j++) {
		            	Field field=new Field();
		            	field.setNumber(config.getInt("settings.vnpay.messages.message("+i+").field("+j+")[@num]"));
		            	field.setLength(config.getInt("settings.vnpay.messages.message("+i+").field("+j+")[@length]"));
		            	field.setLengthIndicator(config.getInt("settings.vnpay.messages.message("+i+").field("+j+")[@lengthIndicator]"));
		            	field.setName(config.getString("settings.vnpay.messages.message("+i+").field("+j+")[@name]"));
		            	field.setValue(config.getString("settings.vnpay.messages.message("+i+").field("+j+")"));
		            	messageInfo.getFieldMaps().put(field.getNumber(), field);
		            }
            	}else{
            		Field field=new Field();
	            	field.setNumber(config.getInt("settings.vnpay.messages.message("+i+").field[@num]"));
	            	field.setLength(config.getInt("settings.vnpay.messages.message("+i+").field[@length]"));
	            	field.setLengthIndicator(config.getInt("settings.vnpay.messages.message("+i+").field[@lengthIndicator]"));
	            	field.setName(config.getString("settings.vnpay.messages.message("+i+").field[@name]"));
	            	field.setValue(config.getString("settings.vnpay.messages.message("+i+").field"));
	            	messageInfo.getFieldMaps().put(field.getNumber(), field);
            	}
            	messageInfoMaps.put(messageInfo.getMessageType(), messageInfo);
            }
		}else{
        	MessageInfo messageInfo=new MessageInfo();
        	messageInfo.setMessageType(config.getString("settings.vnpay.messages.message[@type]"));
        	messageInfo.setFieldMaps(new ConcurrentHashMap<Integer,Field>());
        	Object p1=config.getProperty("settings.vnpay.messages.message.field[@num]");
        	if (p1 instanceof Collection) {
	            Collection c1 = (Collection) p1;
	            for (int j = 0; j < c1.size(); j++) {
	            	Field field=new Field();
	            	field.setNumber(config.getInt("settings.vnpay.messages.message.field("+j+")[@num]"));
	            	field.setLength(config.getInt("settings.vnpay.messages.message.field("+j+")[@length]"));
	            	field.setLengthIndicator(config.getInt("settings.vnpay.messages.message.field("+j+")[@lengthIndicator]"));
	            	field.setName(config.getString("settings.vnpay.messages.message.field("+j+")[@name]"));
	            	field.setValue(config.getString("settings.vnpay.messages.message.field("+j+")"));
	            	messageInfo.getFieldMaps().put(field.getNumber(), field);
	            }
        	}else{
        		Field field=new Field();
            	field.setNumber(config.getInt("settings.vnpay.messages.message.field[@num]"));
            	field.setLength(config.getInt("settings.vnpay.messages.message.field[@length]"));
            	field.setLengthIndicator(config.getInt("settings.vnpay.messages.message.field[@lengthIndicator]"));
            	field.setName(config.getString("settings.vnpay.messages.message.field[@name]"));
            	field.setValue(config.getString("settings.vnpay.messages.message.field"));
            	messageInfo.getFieldMaps().put(field.getNumber(), field);
        	}
        	messageInfoMaps.put(messageInfo.getMessageType(), messageInfo);
        }
		
		responseErrors=new ConcurrentHashMap<String, ErrorConversion>();
		p=config.getProperty("settings.vnpay.response-codes.response-code");
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	ErrorConversion errorConvertion=new ErrorConversion();
            	errorConvertion.setOrgError(config.getString("settings.vnpay.response-codes.response-code("+i+")[@src]"));
            	errorConvertion.setDestError(config.getString("settings.vnpay.response-codes.response-code("+i+")[@dest]"));
            	errorConvertion.setOrgMessage(config.getString("settings.vnpay.response-codes.response-code("+i+")"));
            	responseErrors.put(errorConvertion.getOrgError(), errorConvertion);
            }
		}else{
			ErrorConversion errorConvertion=new ErrorConversion();
        	errorConvertion.setOrgError(config.getString("settings.vnpay.response-codes.response-code[@src]"));
        	errorConvertion.setDestError(config.getString("settings.vnpay.response-codes.response-code[@dest]"));
        	errorConvertion.setOrgMessage(config.getString("settings.vnpay.response-codes.response-code"));
        	responseErrors.put(errorConvertion.getOrgError(), errorConvertion);
		}
		log.info("VnPay Settings loaded.| "+customize2String());
	}

	public long getInitialValue() {
		return initialValue;
	}

	public long getMaxValue() {
		return maxValue;
	}

	public long getUpdateIncerment() {
		return updateIncerment;
	}

	public String getSeqName() {
		return seqName;
	}

	public int getResponseTimeOut() {
		return responseTimeOut;
	}

	public String getAddInfoPrefix() {
		return addInfoPrefix;
	}

	public String getNetworkLoginCode() {
		return networkLoginCode;
	}

	public String getNetworkLogOutCode() {
		return networkLogOutCode;
	}

	public String getNetworkCheckingCode() {
		return networkCheckingCode;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public String getAgentCode() {
		return agentCode;
	}
	
	public String getProcessingCode() {
		return processingCode;
	}

	public String getSceretKey() {
		return sceretKey;
	}

	public Map<String, ErrorConversion> getResponseErrors() {
		return responseErrors;
	}

	
	public int getInquiryTime() {
		return inquiryTime;
	}

	public MessageInfo getMessageInfo(String messageType) throws Exception {
		return (MessageInfo)BeanUtils.cloneBean(messageInfoMaps.get(messageType));
	}

	public String getConnectionType() {
		return connectionType;
	}

	@Override
	public String getDeliveryQueueId() {
		return queueId;
	}

	@Override
	public String getDeliveryWfp() {
		return deliveryWfp;
	}

	@Override
	public String getDnProcessorQueueId() {
		return dnProcessorQueueId;
	}

	@Override
	public String getDnProcessorWfp() {
		return dnProcessorWfp;
	}

	public String getNetworkCheckWfp() {
		return networkCheckWfp;
	}
	public String getNetworkDeliveryWfp() {
		return networkCheckDeliveryWfp;
	}

	
	public String customize2String() {
		return "VnPaySettings [addInfoPrefix=" + addInfoPrefix + "\n"+"agentCode="
		+ agentCode + "\n"+"cdrProcessorQueueId=" + cdrProcessorQueueId
		+ "\n"+"cdrProcessorWfp=" + cdrProcessorWfp + "\n"+"connectionType="
		+ connectionType + "\n"+"deliveryWfp=" + deliveryWfp
		+ "\n"+"dnProcessorQueueId=" + dnProcessorQueueId
		+ "\n"+"dnProcessorWfp=" + dnProcessorWfp + "\n"+"initialValue="
		+ initialValue + "\n"+"inquiryTime=" + inquiryTime
		+ "\n"+"maxSeqError=" + maxSeqError + "\n"+"maxValue=" + maxValue
		+ "\n"+"merchantType=" + merchantType + "\n"+"messageInfoMaps="
		+ messageInfoMaps + "\n"+"networkCheckDeliveryWfp="
		+ networkCheckDeliveryWfp + "\n"+"networkCheckWfp="
		+ networkCheckWfp + "\n"+"networkCheckingCode="
		+ networkCheckingCode + "\n"+"networkLogOutCode="
		+ networkLogOutCode + "\n"+"networkLoginCode=" + networkLoginCode
		+ "\n"+"noResponseList=" + noResponseList + "\n"+"processingCode="
		+ processingCode + "\n"+"queueId=" + queueId + "\n"+"responseErrors="
		+ responseErrors + "\n"+"responseTimeOut=" + responseTimeOut
		+ "\n"+"sceretKey=" + sceretKey + "\n"+"seqName=" + seqName
		+ "\n"+"successRcList=" + successRcList 
		+ "\n"+"amountValidate=" + amountValidate
		+ "\n"+"updateIncerment="
		+ updateIncerment + "]";
	}

	
	
	

	public String getCdrProcessorQueueId() {
		return cdrProcessorQueueId;
	}

	public String getCdrProcessorWfp() {
		return cdrProcessorWfp;
	}

	public int getMaxSeqError() {
		return maxSeqError;
	}

	public boolean isSuccessRc(String errorCode) {
		errorCode=StringUtils.isBlank(errorCode)?"":errorCode.trim();
		if (successRcList.contains(errorCode)) 
			return true;
		else 
			return false;
	}

	public boolean isNoResponse(String errorCode) {
		errorCode=StringUtils.isBlank(errorCode)?"":errorCode.trim();
		if (noResponseList.contains(errorCode)) 
			return true;
		else 
			return false;
	}

	public boolean isAmountValidate() {
		return amountValidate;
	}
	
	
}
