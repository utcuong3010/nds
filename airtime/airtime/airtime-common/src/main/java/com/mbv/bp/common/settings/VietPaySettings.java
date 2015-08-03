package com.mbv.bp.common.settings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class VietPaySettings implements ISettings, AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(VietPaySettings.class);
	private static String vietPayFtpHost = "222.255.28.184";
	private static String vietPayFtpUserName = "MBV";
	private static String vietPayFtpPassword = "5byNkZHF";
	private static int vietPayFtpPort = 21;
	private static String remoteVietPayPath = "";
	private static String localVietPayPath = "D:/ftp-simulator";
	private static String localBackupPath = "D:/ftp-simulator";
	private static Map<String, String> gameServerMap;
	private static boolean vietPayFtpPassiveMode = false;
	
	private String partnerCode="100133829";
	private String password="112233";
	private String secretKey="112233";
	private int responseTimeOut=30000;
	private String topupOperation="TOPUP";
	private String txnInquiryOperation="VIETPAY_TXN_INQUIRY";
	private String balanceOperation="VIETPAY_BALANCE_INQUIRY";
	private String serverListOperation="VIETPAY_SERVER_LIST";
	private String connectionType="VIETPAY";	
	private String cdrProcessorQueueId;
	private String deliveryQueueId;
	private String cdrProcessorWfp;
	private String deliveryWfp;
	private int maxSeqError;
	private List<String> successTxnList;
	private List<String> unknownTxnList;
	private Map<String, String> topupCodeMap;
	
	
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getTopupSignature(String transRef, String topupCode, String account, long amount ){
		return DigestUtils.md5Hex(transRef + topupCode + account + amount + partnerCode + password + secretKey);
	}
	
	public String getInquiryTxnSignature(String transRef){
		return DigestUtils.md5Hex(transRef + partnerCode + password + secretKey);
	}
	
	public String getCheckAccountSignature(String topupCode, String account){
		return DigestUtils.md5Hex(topupCode + account + partnerCode + password + secretKey);
	}
	
	public String getServerListSignature(){
		return DigestUtils.md5Hex(partnerCode + password + secretKey);
	}
	
	@Override
	public void load(CompositeConfiguration config) {
		partnerCode=config.getString("settings.vietpay.partner-code");
		password=config.getString("settings.vietpay.password");
		secretKey=config.getString("settings.vietpay.secret-key");
		deliveryQueueId=config.getString("settings.vietpay.delivery-queue-id");;
		deliveryWfp=config.getString("settings.vietpay.delivery-wfp");
		connectionType=config.getString("settings.vietpay.connection-type"); 
		responseTimeOut=config.getInt("settings.vietpay.response-time-out"); 
		cdrProcessorWfp=config.getString("settings.vietpay.cdr-processor-wfp","cdrProcessorWfp");
		cdrProcessorQueueId=config.getString("settings.vietpay.cdr-processor-queue-id","CDR_PROCESSSOR");
		maxSeqError=config.getInt("settings.vietpay.max-seq-error");
		successTxnList=Arrays.asList(config.getStringArray("settings.vietpay.success-errorcode"));
		unknownTxnList=Arrays.asList(config.getStringArray("settings.vietpay.unknown-errorcode"));
		Map<String, String> tempMap=new HashMap<String, String>(); 
		String[] topupCodes=config.getStringArray("settings.vietpay.topup-code");
		String[] temp; 
		
		vietPayFtpHost 			= config.getString("settings.vietpay.vietpay-ftp-host");
		vietPayFtpUserName 		= config.getString("settings.vietpay.vietpay-ftp-username");
		vietPayFtpPassword 		= config.getString("settings.vietpay.vietpay-ftp-password");
		vietPayFtpPort 			= config.getInt("settings.vietpay.vietpay-ftp-port",21);
		remoteVietPayPath 		= config.getString("settings.vietpay.vietpay-remote-path","");
		if (StringUtils.isBlank(remoteVietPayPath)) remoteVietPayPath="";
		localVietPayPath 		= config.getString("settings.vietpay.vietpay-local-path");
		localBackupPath 		= config.getString("settings.vietpay.vietpay-local-backup-path");
		vietPayFtpPassiveMode 	= config.getBoolean("settings.vietpay.vietpay-ftp-passive-mode");
		
		for (String topupCode:topupCodes){
			temp=topupCode.split(":");
			tempMap.put(temp[0], temp[1]);
		}
		topupCodeMap=tempMap;
		String[] gameServerInfos=config.getStringArray("settings.vietpay.game-servers");
		gameServerMap=new HashMap<String, String>();
		if (gameServerInfos!=null){
			for (String gameServerInfo:gameServerInfos){
				String[] gameInfo=gameServerInfo.split(":");
				if (gameInfo.length>=2){
					gameServerMap.put(gameInfo[0], gameInfo[1]);
				}
			}
		}
		log.info(gameServerMap);
		
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
		return responseTimeOut;
	}
	
	public String getTopupOperation() {
		return topupOperation;
	}

	public String getTxnInquiryOperation() {
		return txnInquiryOperation;
	}
	
	public String getServerListOperation() {
		return serverListOperation;
	}

	
	public String getBalanceOperation() {
		return balanceOperation;
	}

	public String getTopupCode(String telcoId) {
		return topupCodeMap.get(telcoId);
	}
	
	public boolean isUnknownResponse(String responseCode) {
		return unknownTxnList.contains(responseCode);
	}

	public boolean isSuccessResponse(String responseCode) {
		return successTxnList.contains(responseCode);
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

	public String getVietPayFtpHost() {
		return vietPayFtpHost;
	}

	public String getVietPayFtpUserName() {
		return vietPayFtpUserName;
	}

	public String getVietPayFtpPassword() {
		return vietPayFtpPassword;
	}

	public int getVietPayFtpPort() {
		return vietPayFtpPort;
	}

	public boolean getVietPayFtpPassiveMode() {
		return vietPayFtpPassiveMode;
	}

	public String getRemoteVietPayPath() {
		return remoteVietPayPath;
	}

	public String getLocalVietPayPath() {
		return localVietPayPath;
	}

	public String getTxnInquiryDeliveryWfp() {
		return "wfpVietPayTxnInquiryRequestAfterQueue";
	}

	public String getBackupVietPayPath() {
		return localBackupPath;
	}
	
	public String getGamseServerId(String mbvGameServerCode){
		return gameServerMap.get(mbvGameServerCode);
	}
}
