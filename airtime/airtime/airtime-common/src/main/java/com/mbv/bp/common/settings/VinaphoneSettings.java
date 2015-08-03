package com.mbv.bp.common.settings;

import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VinaphoneSettings implements ISettings, AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(VinaphoneSettings.class);
	private String connectionType="VINA";
	private String deliveryQueueId;
	private String deliveryWfp;
	private String cdrProcessorWfp;
	private String cdrProcessorQueueId;
	private int    maxSeqError;
	private List<String> successTxnList;
	private List<String> unknownTxnList;
	private String userName;
	private String password="123456";
	private String agentMsisdn;
	private int timeOut;
	private String topupOperation="TOPUP_OPERATION";
	private String loginOperation="LOGIN_OPERATION";
	private String logoutOperation="LOGOUT_OPERATION";
	private String serviceUrl="http://203.162.35.90/topup/qttu/index.php/vina/eload?ws=1";
	
	public String getShaPassword() throws Exception{
		if (StringUtils.isBlank(password)) throw new Exception("Password not found.");
		return new String(Base64.encodeBase64(DigestUtils.sha(password)));
	}
	
	public String getSignature(String sessionId) throws Exception{
		byte[] data=password.getBytes("UTF-8");
		byte iv[] = {0, 0, 0, 0, 0, 0, 0, 0};
		byte padByte = -1;
		 SecretKeySpec keySpec = null;
	        Cipher cipher = null;
	        byte [] encryptedValue = null;
	        String fullAlgorithm="DESede/CBC/NoPadding";
	        try {
	            cipher = Cipher.getInstance(fullAlgorithm);
	            cipher.init(Cipher.ENCRYPT_MODE, SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec(Hex.decodeHex(sessionId.toCharArray()))), new IvParameterSpec(iv));
	            if(fullAlgorithm.toLowerCase().indexOf("nopadding") != -1)
				{
					int len = data.length;
					int bs = cipher.getBlockSize();
					if(len % bs != 0)
					{
						len = data.length + (bs - data.length % bs);
						data = rightPad(data, padByte, len);
					}
				}
	            encryptedValue = cipher.doFinal(data);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        return new String(Hex.encodeHex(encryptedValue));
	}
	
	private byte[] rightPad(byte data[], byte padByte, int padWidth)
	{
		int len = data.length;
		if(len < padWidth)
		{
			byte nb[] = new byte[padWidth];
			System.arraycopy(data, 0, nb, 0, len);
			for(int i = len; i < padWidth; i++)
				nb[i] = padByte;

			data = nb;
		}
		return data;
	}
	
	public String getUserName() throws Exception {
		return userName;
	}
	
	
	@Override
	public void load(CompositeConfiguration config) {
		serviceUrl=config.getString("settings.vinaphone.serviceUrl");
		timeOut=config.getInt("settings.vinaphone.time-out"); 
		userName=config.getString("settings.vinaphone.user-name");
		password=config.getString("settings.vinaphone.password");
		deliveryQueueId=config.getString("settings.vinaphone.delivery-queue-id");;
		deliveryWfp=config.getString("settings.vinaphone.delivery-wfp");
		cdrProcessorWfp=config.getString("settings.vinaphone.cdr-processor-wfp","cdrProcessorWfp");
		cdrProcessorQueueId=config.getString("settings.vinaphone.cdr-processor-queue-id","CDR_PROCESSSOR");
		maxSeqError=config.getInt("settings.vinaphone.max-seq-error");
		successTxnList=Arrays.asList(config.getStringArray("settings.vinaphone.success-errorcode"));
		unknownTxnList=Arrays.asList(config.getStringArray("settings.vinaphone.unknown-errorcode"));
		agentMsisdn=config.getString("settings.vinaphone.agent-msisdn");
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
		VinaphoneSettings settings=new VinaphoneSettings();
		System.out.println(settings.getSignature("2F20BF8CDFC8F44954C7F4DCBF587C4FC15D262A85F110C8"));
	}

	public String getServiceUrl() {
		return serviceUrl;
	}

	public String getTopupOperation() {
		return topupOperation;
	}

	public String getLoginOperation() {
		return loginOperation;
	}

	public String getLogoutOperation() {
		return logoutOperation;
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
		return "VinaphoneSettings [cdrProcessorQueueId=" + cdrProcessorQueueId
				+ "\n cdrProcessorWfp=" + cdrProcessorWfp + "\n connectionType="
				+ connectionType +  "\n agentMsisdn="
				+ agentMsisdn + "\n deliveryQueueId=" + deliveryQueueId
				+ "\n deliveryWfp=" + deliveryWfp + "\n maxSeqError="
				+ maxSeqError + "\n serviceUrl=" + serviceUrl
				+ "\n successTxnList=" + successTxnList + "\n timeOut=" + timeOut
				+ "\n unknownTxnList=" + unknownTxnList + "\n userName="
				+ userName + "]";
	}

	public String getAgentMsisdn() {
		return agentMsisdn;
	}
	
}
