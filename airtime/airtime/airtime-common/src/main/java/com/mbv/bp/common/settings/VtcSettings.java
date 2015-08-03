package com.mbv.bp.common.settings;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;


public class VtcSettings implements ISettings, AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(VtcSettings.class);
	private String deliveryQueueId="VTC_DELIVERY";
	private String deliveryWfp="wfpVtcTopupGameRequestAfterQueue";
	private String partnerCode="MOBIVI2012";
	private int responseTimeOut=30000;	
	private String mobiviPrivateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL3hHTS4XDZOAszHhECut84zgxOuSCwN9ZOBXMegEdqPIOlUHdPGe3AxoLDZpjvp12wAlbKBsrP98s++FQI6yX2SAjUQdf7OZwlZJCqykHFTbt/mf9ihs2k9s5HkZMk7HHZHOT8wOIMmAtz2ZvdTIzo6KcQ/xyLKbvxYY4LJYWWNAgMBAAECgYBANC479Vq4wggQViZR+MIN5z0bGoMs4kt3ZPaKSYW/8UWfn+G2ChcTCLTdF7st5xQAYPI9Ob3DPssrk4pnBpm6VaJXY10rCo+gmGQhvc4LKVG37fCg+uATRIsNLDVwu2JxTFgo1Psyha6GY03DU0siAFQML6oUmsx4MtepV8hpwQJBAPqM08GFRqjSKznG/HM9Jwr0qd01YEv1pBlkg7fcB7tUrzQ6UEaFaLmtsFb0xDpv/qv8GEm2Ta+1+K9DRUSzgKsCQQDCAnIQCvwT5hE62aTkBKFw/iuweFJ+w1YvmnMC6mKq/uXFcIxtcTbgxK0/pvyOpPBhAhcfxEKoHGwaEDQZFWKnAkEAi4GcarWV2WxkqyAT8uqK8bu3VTdiLglRXN4txVMbbwBBKdiKWCnyXOjMNi7FkDBJ4mNU9r4uVXcCSDwxtoYoTwJAQDfJA7BvIjMMTvuNzgAOZDVtxrr9K4KC+7zXBwcIY+t9qO4JPYy1Co9vfVtLy/eiramgd95h5f2KdtIYPJlMjwJBAI0j+IIm8PFCYN13PpiUkTDjADEUN449Z87uvLO/XMKPqnlJCBk8p4P6mCDOeKZewBDQGwKg8osJVxgcJRQhB/Y="; 
	private String vtcPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfa12iA1BZDCehCDraOvRlEGgrkYXKLQZDmDP28k74nvdOpK6bp410BtGVrZaf1TXPQEgVibuyULSQ7ZYH+CjMJLu/IpQAYjdYjkckzHtDJpvIfaaJcDtysxPYKUkUvZejVKTWbij+ruW8BUGKjmFHj7W3wlgh7bsFwTTo1KoQNQIDAQAB";
	private String vcoinServiceCode="VTC0115";
	private String cdrProcessorWfp="cdrProcessorWfp";
	private String cdrProcessorQueueId="CDR_PROCESSSOR";
	private int maxSeqError=5;
	private List<String> errorList;
	private List<String> unknownList;
	private List<String> successList;
	@Override
	public void load(CompositeConfiguration config) {
		deliveryQueueId=config.getString("settings.vtc.delivery-queue-id");;
		deliveryWfp=config.getString("settings.vtc.delivery-wfp");
		partnerCode=config.getString("settings.vtc.partner-code");
		responseTimeOut=config.getInt("settings.vtc.response-time-out"); 
		mobiviPrivateKey=config.getString("settings.vtc.mobivi-private-key");
		vtcPublicKey=config.getString("settings.vtc.vtc-public-key");
		vcoinServiceCode=config.getString("settings.vtc.vcoin-service-code");
		cdrProcessorWfp=config.getString("settings.vtc.cdr-processor-wfp","cdrProcessorWfp");
		cdrProcessorQueueId=config.getString("settings.vtc.cdr-processor-queue-id","CDR_PROCESSSOR");
		maxSeqError=config.getInt("settings.vtc.max-seq-error");
		errorList=Arrays.asList(config.getStringArray("settings.vtc.error-errorcode"));
		unknownList=Arrays.asList(config.getStringArray("settings.vtc.unknown-errorcode"));
		successList=Arrays.asList(config.getStringArray("settings.vtc.success-errorcode"));
		log.info("Load VTC settings completed!");
		log.info(customer2String());
	}

	
	public String customer2String() {
		return "VtcSettings [\n" +
				"cdrProcessorQueueId=" + cdrProcessorQueueId
				+ ",\n cdrProcessorWfp=" + cdrProcessorWfp + ", deliveryQueueId="
				+ deliveryQueueId + ",\n deliveryWfp=" + deliveryWfp
				+ ",\n errorList=" + errorList + ",\n maxSeqError=" + maxSeqError
				+ ",\n mobiviPrivateKey=" + mobiviPrivateKey + ",\n partnerCode="
				+ partnerCode + ",\n responseTimeOut=" + responseTimeOut
				+ ",\n successList=" + successList + ",\n unknownList="
				+ unknownList + ",\n vcoinServiceCode=" + vcoinServiceCode
				+ ",\n vtcPublicKey=" + vtcPublicKey + "]";
	}

	@Override
	public String getConnectionType() {
		return "VTC";
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
		return responseTimeOut;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public String getTopupGameOperation() {
		return "VTC_GAME_TOPUP";
	}

	public String getSignature(String data) throws Exception {
		
		PKCS8EncodedKeySpec specPriv = new PKCS8EncodedKeySpec(Base64.decodeBase64(AppConstants.VTC_SETTINGS.getMobiviPrivateKey().getBytes()));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey= kf.generatePrivate(specPriv);
		Signature instance = Signature.getInstance("SHA1withRSA");
		instance.initSign(privateKey);
		instance.update((data).getBytes());
		byte[] signature = instance.sign();
		return new String(Base64.encodeBase64(signature));
	}
	
	public boolean verifySignature(String data,String dataSign) throws Exception {
		
		if (log.isDebugEnabled()){
			log.debug("data: "+data);
			log.debug("dataSign: "+dataSign);
		}
		X509EncodedKeySpec specPub = new X509EncodedKeySpec(Base64.decodeBase64(AppConstants.VTC_SETTINGS.getVtcPublicKey().getBytes()));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PublicKey publicKey = kf.generatePublic(specPub);
		Signature instance = Signature.getInstance("SHA1withRSA");
		instance.initVerify(publicKey);
		instance.update((data).getBytes());
		return instance.verify(Base64.decodeBase64(dataSign.getBytes()));
	}
	
	public boolean verifyResult(String result){
		try{
			String[] results=StringUtils.split(result,"|");
			String data="";
			for(int i=0;i<results.length-1;i++)
				data=data+results[i]+"-";
			data=data.substring(0,data.length()-1);
			return verifySignature(data, results[results.length-1]);
		}catch (Exception e) {
			log.error("Fail to verify signature.",e);
			return false; 
			
		}
	}
	
	private String getVtcPublicKey() {
		return vtcPublicKey;

	}

	private String getMobiviPrivateKey() {
		return mobiviPrivateKey;

	}

	public boolean isSuccessResponse(String responseCode) {
		if (StringUtils.isBlank(responseCode)) return false;
		
		if (successList.contains(responseCode.trim())) return true; 
		
		if (NumberUtils.isNumber(responseCode.trim())){
			Long errorCode=null;
			try{
				errorCode=Long.parseLong(responseCode);
				return errorCode>0?true:false; 
			}catch (Exception e) {
				return false;
			}
		}else{
			return false;
		}
	}

	public boolean isUnknownResponse(String responseCode) {
		
		if (StringUtils.isBlank(responseCode)) return false;
		
		if (unknownList.contains(responseCode.trim())) return true; 
		
		if (NumberUtils.isNumber(responseCode.trim())){
			Long errorCode=null;
			try{
				errorCode=Long.parseLong(responseCode);
				if (errorCode>0) 
					return false;
				else
					return errorList.contains(responseCode.trim())==true? false:true;
			}catch (Exception e) {
				return false;
			}
		}else{
			return false;
		}

	}
	
	public boolean isErrorResponse(String responseCode) {
		if (StringUtils.isBlank(responseCode)) return false;
		
		
		if (NumberUtils.isNumber(responseCode.trim())){
			Long errorCode=null;
			try{
				errorCode=Long.parseLong(responseCode);
				if (errorCode>0) 
					return false;
				else
					return errorList.contains(responseCode.trim())==true? true:false;
			}catch (Exception e) {
				return false;
			}
		}else{
			if (successList.contains(responseCode.trim()) || unknownList.contains(responseCode.trim())) 
				return false;
			else
				return true;
		}
	}
	
	public String getVcoinServiceCode() {
		return vcoinServiceCode;
	}

	public String getCheckAccountOperation() {
		return "VTC_CHECK_ACCOUNT";
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
}
