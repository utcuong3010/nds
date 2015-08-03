package com.mbv.bp.common.settings;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;

public class MobifoneSettings implements ISettings, AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(MobifoneSettings.class);
	private String connectionType="MOBI";
	private String deliveryQueueId="MOBI_DELIVERY";
	private String deliveryWfp="wfpMobiFoneTopupRequestAfterQueue";
	private String dnProcessorQueueId;
	private String dnProcessorWfp;
	private String balQueryDeliveryWfp="wfpMobiFoneBalanceRequestAfterQueue";
	private String changePwdDeliveryTaskWfp="wfpMobiFoneChangePwdRequestAfterQueue";
	private String userName;
	private String password;
	private int sessionInvalidTime=30000;
	private int timeOut=30000;
	private int buyType=2;
	private int processFinalStatusTime=10000;
	private String buyPrePaidTarget="airtime";
	private String buyPostPaidTarget="postpaid";
	private String txnInquiryOperation="TXN_INQUIRY";
	private  String buyOperation="BUY";
	private  String nextIdOperation="NEXT_ID";
	private  String balanceInquiryOpeation="BALANCE_INQUIRY";
	private  String transferOperation="TRANSFER";
	private  String resetSessionOperation="RESET_SESSION";
	private  String changePasswordOperation="PWD_CHANGE";
	private  int nextIdMaxRetry=5;
	private  int inquiryMaxRetry=3; 
	private  int maxSeqError;
	private  int maxError;
	private String localMobiviPath;
	private String localBackupPath;
	private String tmpFileExtension;
	private String filePrefix;
	private String syncFileExtension;
	private int mobiviFtpPort;
	private String mobiviFtpPassword;
	private String mobiviFtpUserName;
	private String mobiviFtpHost;
	private String remoteMobiviPath;
	private String mobifoneFtpHost;
	private String mobifoneFtpUserName;
	private String mobifoneFtpPassword;
	private int mobifoneFtpPort;
	private String remoteMobifonePath;
	private boolean mobifoneFtpPassiveMode;
	private String localMobifonePath;
	private String sessionId;
	private boolean sessionVerified;
	private String cdrMobifoneProcessorWfp;
	private String cdrMobifoneProcessorQueueId;
	private int fromLastDateToCurDate;
	private boolean amountValidate;
	public static final String MODULE="mobifone";
	public static final String TYPE="config"; 
	public static final String USERNAME_KEY="settings.mobifone.username";
	public static final String PASSWORD_KEY="settings.mobifone.password";

	public String getEncryptedPin(String sessionId) throws Exception{
		String result=getUserName().toLowerCase()+getPassword();
		result=DigestUtils.shaHex(result.getBytes()).toLowerCase();
		result=sessionId+result;
		result=DigestUtils.shaHex(result.getBytes()).toUpperCase();
		return result;
	}
	
	public String getEncryptedPin(String username, String password,String sessionId){
		String result=username.toLowerCase()+password;
		result=DigestUtils.shaHex(result.getBytes()).toLowerCase();
		result=sessionId+result;
		result=DigestUtils.shaHex(result.getBytes()).toUpperCase();
		return result;
	}
	
	public String getUserName() throws Exception {
		if (StringUtils.isNotBlank(userName)) return userName;
		AirtimeConfig airtimeConfig=new AirtimeConfig();
		AirtimeConfigDao configDao=new AirtimeConfigDao();
		airtimeConfig.setModule(MODULE);
		airtimeConfig.setType(TYPE);
		airtimeConfig.setParamKey(USERNAME_KEY);
		try{
			if (!configDao.select(airtimeConfig)){
				airtimeConfig.setParamValue(userName);
				configDao.insert(airtimeConfig);
			}else{
				userName=airtimeConfig.getParamValue();
			}
			
			if (StringUtils.isBlank(userName)) throw new Exception("Fail to get user from database"); 
		}catch (Exception e) {
			log.error("Fail to get user from database| userName-"+userName);
			throw e;
		}
		
		return userName;
	}

	public String getPassword() throws Exception {
		AirtimeConfig airtimeConfig=new AirtimeConfig();
		AirtimeConfigDao configDao=new AirtimeConfigDao();
		airtimeConfig.setModule(MODULE);
		airtimeConfig.setType(TYPE);
		airtimeConfig.setParamKey(PASSWORD_KEY);
		try{
			if (!configDao.select(airtimeConfig)){
				airtimeConfig.setParamValue(password);
				configDao.insert(airtimeConfig);
				password=null;
			}else{
				password=airtimeConfig.getParamValue();
			}
		}catch (Exception e) {
			log.error("Fail to get password from database");
		}
		if (StringUtils.isBlank(password)) 
			throw new Exception("Fail to get password from database| params:"+airtimeConfig); 
		
		return password;
	}
    
	

	@Override
	public void load(CompositeConfiguration config) {
		timeOut=config.getInt("settings.mobifone.time-out"); 
		buyType=config.getInt("settings.mobifone.buy-type");
		processFinalStatusTime=config.getInt("settings.mobifone.process-result-time");
		nextIdMaxRetry=config.getInt("settings.mobifone.next-id-max-retry");
		inquiryMaxRetry=config.getInt("settings.mobifone.inquiry-max-retry");
		sessionInvalidTime=config.getInt("settings.mobifone.invalid-unloged-session-time");
		sessionId=config.getString("settings.mobifone.session-id");
		sessionVerified=config.getBoolean("settings.mobifone.session-verified");
		
		localMobiviPath=config.getString("settings.mobifone.local-mobivi-path");
		localBackupPath=config.getString("settings.mobifone.local-backup-path");
		tmpFileExtension=config.getString("settings.mobifone.temp-file-extension");
		filePrefix=config.getString("settings.mobifone.file-prefix");
		syncFileExtension=config.getString("settings.mobifone.sync-file-extension");
		mobiviFtpPort=config.getInt("settings.mobifone.mobivi-ftp-port");
		mobiviFtpPassword=config.getString("settings.mobifone.mobivi-ftp-password");
		mobiviFtpUserName=config.getString("settings.mobifone.mobivi-ftp-username");
		mobiviFtpHost=config.getString("settings.mobifone.mobivi-ftp-host");
		remoteMobiviPath=config.getString("settings.mobifone.remote-mobivi-path");
		mobifoneFtpHost=config.getString("settings.mobifone.mobifone-ftp-host");
		mobifoneFtpUserName=config.getString("settings.mobifone.mobifone-ftp-username");
		mobifoneFtpPassword=config.getString("settings.mobifone.mobifone-ftp-password");
		mobifoneFtpPort=config.getInt("settings.mobifone.mobifone-ftp-port");
		remoteMobifonePath=config.getString("settings.mobifone.remote-mobifone-path");
		mobifoneFtpPassiveMode=config.getBoolean("settings.mobifone.mobifone-ftp-passive-mode",true);
		localMobifonePath=config.getString("settings.mobifone.local-mobifone-path");
		amountValidate=config.getBoolean("settings.mobifone.amount-validate",false);
		maxSeqError=config.getInt("settings.mobifone.max-seq-error");
		maxError=config.getInt("settings.mobifone.max-error");
		fromLastDateToCurDate=config.getInt("settings.mobifone.from-lastdate-to-current-date");
		cdrMobifoneProcessorWfp=config.getString("settings.mobifone.cdr-processor-wfp","cdrProcessorWfp");
		cdrMobifoneProcessorQueueId=config.getString("settings.mobifone.cdr-processor-queue-id","CDR_PROCESSSOR");
		log.info(customer2String());
	}
	
	

	public String customer2String() {
		return "MobifoneSettings [balQueryDeliveryWfp=" + balQueryDeliveryWfp
		+ "\n"+"balanceInquiryOpeation=" + balanceInquiryOpeation
		+ "\n"+"buyOperation=" + buyOperation + "\n"+"buyPostPaidTarget="
		+ buyPostPaidTarget + "\n"+"buyPrePaidTarget=" + buyPrePaidTarget
		+ "\n"+"buyType=" + buyType + "\n"+"cdrMobifoneProcessorQueueId="
		+ cdrMobifoneProcessorQueueId + "\n"+"cdrMobifoneProcessorWfp="
		+ cdrMobifoneProcessorWfp + "\n"+"changePasswordOperation="
		+ changePasswordOperation + "\n"+"changePwdDeliveryTaskWfp="
		+ changePwdDeliveryTaskWfp + "\n"+"connectionType="
		+ connectionType + "\n"+"deliveryQueueId=" + deliveryQueueId
		+ "\n"+"deliveryWfp=" + deliveryWfp + "\n"+"dnProcessorQueueId="
		+ dnProcessorQueueId + "\n"+"dnProcessorWfp=" + dnProcessorWfp
		+ "\n"+"filePrefix=" + filePrefix + "\n"+"fromLastDateToCurDate="
		+ fromLastDateToCurDate + "\n"+"inquiryMaxRetry="
		+ inquiryMaxRetry + "\n"+"localMobifonePath=" + localMobifonePath
		+ "\n"+"localMobiviPath=" + localMobiviPath + "\n"+"maxError="
		+ maxError + "\n"+"maxSeqError=" + maxSeqError
		+ "\n"+"mobifoneFtpHost=" + mobifoneFtpHost
		+ "\n"+"mobifoneFtpPassiveMode=" + mobifoneFtpPassiveMode
		+ "\n"+"mobifoneFtpPassword=" + mobifoneFtpPassword
		+ "\n"+"mobifoneFtpPort=" + mobifoneFtpPort
		+ "\n"+"mobifoneFtpUserName=" + mobifoneFtpUserName
		+ "\n"+"mobiviFtpHost=" + mobiviFtpHost + "\n"+"mobiviFtpPassword="
		+ mobiviFtpPassword + "\n"+"mobiviFtpPort=" + mobiviFtpPort
		+ "\n"+"mobiviFtpUserName=" + mobiviFtpUserName
		+ "\n"+"nextIdMaxRetry=" + nextIdMaxRetry + "\n"+"nextIdOperation="
		+ nextIdOperation + "\n"+"password=" + password
		+ "\n"+"processFinalStatusTime=" + processFinalStatusTime
		+ "\n"+"remoteMobifonePath=" + remoteMobifonePath
		+ "\n"+"remoteMobiviPath=" + remoteMobiviPath
		+ "\n"+"resetSessionOperation=" + resetSessionOperation
		+ "\n"+"sessionId=" + sessionId + "\n"+"sessionInvalidTime="
		+ sessionInvalidTime + "\n"+"sessionVerified=" + sessionVerified
		+ "\n"+"syncFileExtension=" + syncFileExtension + "\n"+"timeOut="
		+ timeOut + "\n"+"tmpFileExtension=" + tmpFileExtension
		+ "\n"+"transferOperation=" + transferOperation
		+ "\n"+"txnInquiryOperation=" + txnInquiryOperation
		+ "\n"+"amountValidate=" + amountValidate
		+ "\n"+"userName=" + userName + "]";
	}

	@Override
	public String getConnectionType() {
		// TODO Auto-generated method stub
		return connectionType;
	}

	@Override
	public String getDeliveryQueueId() {
		// TODO Auto-generated method stub
		return deliveryQueueId;
	}

	@Override
	public String getDeliveryWfp() {
		// TODO Auto-generated method stub
		return deliveryWfp;
	}

	@Override
	public String getDnProcessorQueueId() {
		// TODO Auto-generated method stub
		return dnProcessorQueueId;
	}

	@Override
	public String getDnProcessorWfp() {
		// TODO Auto-generated method stub
		return dnProcessorWfp;
	}

	public int getBuyType() {
		return buyType;
	}

	public String getBuyPrePaidTarget() {
		return buyPrePaidTarget;
	}

	public String getBuyPostPaidTarget() {
		return buyPostPaidTarget;
	}
	public String getTxnInquiryOperation() {
		return txnInquiryOperation;
	}

	public String getBuyOperation() {
		return buyOperation;
	}

	public String getNextIdOperation() {
		return nextIdOperation;
	}

	public String getBalanceInquiryOpeation() {
		return balanceInquiryOpeation;
	}

	public String getTransferOperation() {
		return transferOperation;
	}

	public String getResetSessionOperation() {
		return resetSessionOperation;
	}
	public String getChangePasswordOperation(){
		return changePasswordOperation;
	}

	public int getProcessFinalStatusTime() {
		
		return processFinalStatusTime;
	}

	public int getNextIdMaxRetry() {
		return nextIdMaxRetry;
	}
	public int getInquiryMaxRetry() {
		return inquiryMaxRetry;
	}
	public boolean isPostPaidErrror(ContextBase context) {
		if (StringUtils.isNotBlank(context.get(Attributes.ATTR_RESPONSE_CODE))&& 
				AppConstants.MOBIFONE_SETTINGS.getBuyPrePaidTarget().equalsIgnoreCase(context.get(Attributes.ATTR_MOBI_BUY_TARGET))){
			int result=context.getInt(Attributes.ATTR_RESPONSE_CODE);
			if (result==42) return true;
		}
		return false;
	}
	public String getBalQueryDeliveryWfp(){
		return balQueryDeliveryWfp;
	}
	public String getChangePwdDeliveryTaskWfp(){
		return changePwdDeliveryTaskWfp;
	}
	
	public int getSessionInvalidTime() {
		return sessionInvalidTime;
	}

	
	public String getSessionId() {
		return sessionId;
	}

	public boolean isSessionVerified() {
		return sessionVerified;
	}

	public static void main(String[] args) {
		MobifoneSettings mobifoneSettings=new MobifoneSettings();
		System.out.println(mobifoneSettings.getEncryptedPin("mobivi","20110914","QBZQPUBTC3RGAFCIKI4F"));
	}

	@Override
	public int getResponseTimeOut() {
		return timeOut;
	}

	public String getLocalMobiviPath() {
		return localMobiviPath;
	}
	public String getTmpFileExtension() {
		return tmpFileExtension;
	}

	public String getFilePrefix() {
		return filePrefix;//"mobivi_buy_trans";
	}

	public String getSyncFileExtension() {
		return syncFileExtension;//".log";
	}

	public int getMobiviFtpPort() {
		return mobiviFtpPort;
	}

	public String getMobiviFtpPassword() {
		return mobiviFtpPassword;
	}

	public String getMobiviFtpUserName() {
		return mobiviFtpUserName;
	}

	public String getMobiviFtpHost() {
		return mobiviFtpHost;
	}

	public String getRemoteMobiviPath() {
		return remoteMobiviPath;
	}

	public String getMobifoneFtpHost() {
		return mobifoneFtpHost;
	}

	public String getMobifoneFtpUserName() {
		return mobifoneFtpUserName;
	}

	public String getMobifoneFtpPassword() {
		return mobifoneFtpPassword;
	}

	public int getMobifoneFtpPort() {
		return mobifoneFtpPort;
	}
// make sure that end with /
	public String getRemoteMobifonePath() {
		return remoteMobifonePath;
	}

	public boolean getMobifoneFtpPassiveMode() {
		return mobifoneFtpPassiveMode;
	}
	// make sure that end with /
	public String getLocalMobifonePath() {
		return localMobifonePath;
	}

	public int getMaxSeqError() {
		return maxSeqError;
	}

	public int getMaxError() {
		return maxError;
	}
	
	public String getCdrMobifoneProcessorWfp(){
		return cdrMobifoneProcessorWfp;
	}
	
	public String getCdrMobifoneProcessorQueueId(){
		return cdrMobifoneProcessorQueueId;
	}

	public boolean isTxnFound(String resultCode) {
		boolean result=true;
		if (StringUtils.isNotBlank(resultCode)){
			if ("415".equalsIgnoreCase(resultCode.trim()))
					return false;
		}
		return result;
	}

	public long getFromLastDateToCurDate() {
		return fromLastDateToCurDate;
	}

	public boolean isAmountValidate() {
		return amountValidate;
	}

	public String getBackupMobifonePath() {
		return localBackupPath;
	}
}
