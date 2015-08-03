package com.mbv.bp.common.settings;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.util.VascAesUtils;

public class VascSettings implements ISettings, AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(VascSettings.class);
	private String connectionType="VASC";
	private String deliveryQueueId;
	private String deliveryWfp;
	private String cdrProcessorWfp;
	private String cdrProcessorQueueId;
	private int    maxSeqError;
	private List<String> successTxnList;
	private List<String> unknownTxnList;
	private String userName;
	private String privateKey;
	private int timeOut;
	private String topupOperation="TOPUP_OPERATION";
	private String checkConnectionOperation="CHECK_CONN_OPERATION";
	private String serviceUrl="http://203.162.35.90/topup/qttu/index.php/vina/eload?ws=1";
	public String encryptMsisdn(String msisdn84prefix) throws Exception {
		return VascAesUtils.Rijndael_Encrypt(msisdn84prefix, privateKey);
	}
	
	public String getSignature(String msisdn84prefix,String amount,String txnId,String option){
		if (StringUtils.isBlank(option))
		  option="";
		return DigestUtils.md5Hex(userName + privateKey + msisdn84prefix + amount + txnId+option);
	}
	
	public String getUserName() throws Exception {
		return userName;
	}
	
	
	@Override
	public void load(CompositeConfiguration config) {
		serviceUrl=config.getString("settings.vasc.serviceUrl");
		timeOut=config.getInt("settings.vasc.time-out"); 
		userName=config.getString("settings.vasc.user-name");
		privateKey=config.getString("settings.vasc.secret-key");
		deliveryQueueId=config.getString("settings.vasc.delivery-queue-id");;
		deliveryWfp=config.getString("settings.vasc.delivery-wfp");
		cdrProcessorWfp=config.getString("settings.vasc.cdr-processor-wfp","cdrProcessorWfp");
		cdrProcessorQueueId=config.getString("settings.vasc.cdr-processor-queue-id","CDR_PROCESSSOR");
		maxSeqError=config.getInt("settings.vasc.max-seq-error");
		successTxnList=Arrays.asList(config.getStringArray("settings.vasc.success-errorcode"));
		unknownTxnList=Arrays.asList(config.getStringArray("settings.vasc.unknown-errorcode"));
		log.info(customer2String());
	}
	
	@Override
	public String getConnectionType() {
		return connectionType;
	}

	@Override
	public String getDeliveryQueueId() {
		return deliveryQueueId;
	}

	@Override
	public String getDeliveryWfp() {
		return deliveryWfp;
	}

	@Override
	public String getDnProcessorQueueId() {
		return null;
	}

	@Override
	public String getDnProcessorWfp() {
		return null;
	}

	@Override
	public int getResponseTimeOut() {
		return timeOut;
	}

	public static void main(String[] args) throws Exception {
		VascSettings settings=new VascSettings();
		System.out.println(DigestUtils.md5Hex("123456"+ "1" +"10000"+"1"+"0"+"5"));
		
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public String getTopupOperation() {
		return topupOperation;
	}

	public String getCheckConnectionOperation() {
		return checkConnectionOperation;
	}

	public boolean verifyResult(int status,float amount,int traceId, long balance, int messageCode,String signature) {
		String plainTxt=privateKey+""+status+""+(long)amount +""+balance+""+traceId+""+messageCode;
		String s=DigestUtils.md5Hex(plainTxt);
		log.info("signature: "+signature +" vs target: "+s);
		return s.equals(signature);
	}

	public boolean isSuccessResponse(String status) {
		return successTxnList.contains(status);
	}

	public boolean isUnknownResponse(String status) {
		return unknownTxnList.contains(status);
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

	
	public String customer2String() {
		return "VascSettings [cdrProcessorQueueId=" + cdrProcessorQueueId
				+ "\n cdrProcessorWfp=" + cdrProcessorWfp + "\n connectionType="
				+ connectionType + "\n deliveryQueueId=" + deliveryQueueId
				+ "\n deliveryWfp=" + deliveryWfp + "\n maxSeqError="
				+ maxSeqError + "\n serviceUrl=" + serviceUrl
				+ "\n successTxnList=" + successTxnList + "\n timeOut=" + timeOut
				+ "\n unknownTxnList=" + unknownTxnList + "\n userName="
				+ userName + "]";
	}
	
}
