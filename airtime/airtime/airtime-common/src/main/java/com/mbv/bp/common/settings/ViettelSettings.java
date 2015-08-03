package com.mbv.bp.common.settings;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.model.ErrorConversion;

public class ViettelSettings implements ISettings, AirtimeProviderSettings{
	private static Log log = LogFactory.getLog(ViettelSettings.class);
	private List<String> successRcList;
	private List<String> waitRcList;
	private String 	clientId;
	private String 	publicKey;
	private String 	privateKey;
	private String 	connectionType;
	private String 	deliveryQueueId;
	private String 	deliveryWfp;
	private String 	dnProcessorQueueId;
	private String 	dnProcessorWfp;
	private int 	processFinalStatusTime;
	private int  	responseTimeOut;
	private int		maxPendingRequest;	
	private int 	maxPendingResponse;
	private int 	requestPollerInterval;
	private String  requestOperation;
	private String 	responseOperation;
	private int  	txnTimeOut;
	private int 	retryResponseInterval;
	private int 	maxConnsToVtelServer;
	private Map<String, ErrorConversion> responseErrors;
	private String localMobiviPath;
	private String localViettelPath;
	private String remoteMobiviPath;
	private String remoteViettelPath;
	private String ftpHost;
	private int ftpPort;
	private String ftpUserName;
	private String ftpPassword;
	private String syncFileExtension;
	private String tmpFileExtension;
	private int syncTimeIntervalMins;
	private long sendNetworkCheckTimeInterval;
	private int maxConnectionRetry;
	private String cdrProcessorWfp;
	private String cdrProcessorQueueId;
	private int maxSeqError;
	private int maxUnknownTxnSize;
	private String syncMidFileName;
	private boolean amountValidate;
	private String fileEndWith;
	
	@Override
	public void load(CompositeConfiguration config) {
		clientId=config.getString("settings.viettel.client-id");
		String[] temps;
		temps=config.getStringArray("settings.viettel.success.response-codes");
		successRcList=new ArrayList<String>();
		for (String rc:temps)
			successRcList.add(rc.trim());
		
		temps=config.getStringArray("settings.viettel.waiting.response-codes");
		waitRcList=new ArrayList<String>();
		for (String rc:temps)
			waitRcList.add(rc.trim());
		
		privateKey=config.getString("settings.viettel.private-key");
		publicKey=config.getString("settings.viettel.public-key", "");
		connectionType=config.getString("settings.viettel.connection-type");
		deliveryQueueId=config.getString("settings.viettel.delivery-queue-id");
		deliveryWfp=config.getString("settings.viettel.delivery-wfp");
		dnProcessorQueueId=config.getString("settings.viettel.dn-processor-queue-id");
		dnProcessorWfp=config.getString("settings.viettel.dn-processor-wfp");
		processFinalStatusTime=config.getInt("settings.viettel.process-final-status-time");
		responseTimeOut=config.getInt("settings.viettel.response-time-out");
		maxPendingRequest=config.getInt("settings.viettel.max-pending-request");
		maxPendingResponse=config.getInt("settings.viettel.max-pending-response");
		requestPollerInterval=config.getInt("settings.viettel.request-poller-interval");
		requestOperation=config.getString("settings.viettel.request-operation-type");
		responseOperation=config.getString("settings.viettel.response-operation-type");
		txnTimeOut=config.getInt("settings.viettel.txn-time-out");
		retryResponseInterval=config.getInt("settings.viettel.retry-response-interval");
		maxConnsToVtelServer=config.getInt("settings.viettel.max-vtel-connection");
		
		localMobiviPath=config.getString("settings.viettel.local-mobivi-path");
		localViettelPath=config.getString("settings.viettel.local-viettel-path");
		remoteMobiviPath=config.getString("settings.viettel.remote-mobivi-path");
		remoteViettelPath=config.getString("settings.viettel.remote-viettel-path");
		ftpHost=config.getString("settings.viettel.ftp-host");
		ftpPort=config.getInt("settings.viettel.ftp-port",21);
		ftpUserName=config.getString("settings.viettel.ftp-username");
		ftpPassword=config.getString("settings.viettel.ftp-password");
		syncFileExtension=config.getString("settings.viettel.sync-file-extension");
		tmpFileExtension=config.getString("settings.viettel.tmp-file-extension");
		syncTimeIntervalMins=config.getInt("settings.viettel.sync-time-interval-mins",16);
		sendNetworkCheckTimeInterval=config.getInt("settings.viettel.send-network-check-time-interval",120000);
		maxConnectionRetry=config.getInt("settings.viettel.max-connection-retry",1);
		syncMidFileName= config.getString("settings.viettel.sync-mid-filename");
		
		cdrProcessorWfp=config.getString("settings.viettel.cdr-processor-wfp","cdrProcessorWfp");
		cdrProcessorQueueId=config.getString("settings.viettel.cdr-processor-queue-id","CDR_PROCESSSOR");
		maxSeqError=config.getInt("settings.viettel.max-seq-error");
		maxUnknownTxnSize=config.getInt("settings.viettel.max-unknown-txn-id");
		
		amountValidate=config.getBoolean("settings.viettel.amount-validate",false);
		fileEndWith=config.getString("settings.viettel.viettel-cdr-end-with",".topup");
		
		Object p;
		responseErrors=new ConcurrentHashMap<String, ErrorConversion>();
		p=config.getProperty("settings.viettel.response-codes.response-code");
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	ErrorConversion errorConvertion=new ErrorConversion();
            	errorConvertion.setOrgError(config.getString("settings.viettel.response-codes.response-code("+i+")[@src]"));
            	errorConvertion.setDestError(config.getString("settings.viettel.response-codes.response-code("+i+")[@dest]"));
            	errorConvertion.setOrgMessage(config.getString("settings.viettel.response-codes.response-code("+i+")"));
            	responseErrors.put(errorConvertion.getOrgError(), errorConvertion);
            }
		}else{
			ErrorConversion errorConvertion=new ErrorConversion();
        	errorConvertion.setOrgError(config.getString("settings.viettel.response-codes.response-code[@src]"));
        	errorConvertion.setDestError(config.getString("settings.viettel.response-codes.response-code[@dest]"));
        	errorConvertion.setOrgMessage(config.getString("settings.viettel.response-codes.response-code"));
        	responseErrors.put(errorConvertion.getOrgError(), errorConvertion);
		}
		
		
		
		log.info("Viettel settings Loaded!"+"\n" +customize2String());
	}

	public boolean isTxnInProgress(String responseCode) {
		if (StringUtils.isBlank(responseCode)) return false;
		return waitRcList.contains(responseCode);
	}

	public boolean isSuccessRc(String responseCode) {
		responseCode=StringUtils.isBlank(responseCode)?"":responseCode.trim();
		if (successRcList.contains(responseCode)) 
			return true;
		else 
			return false;
	}

	public String getClientId() {
		return clientId;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public String getDeliveryQueueId() {
		return deliveryQueueId;
	}

	public String getDeliveryWfp() {
		return deliveryWfp;
	}

	public String getDnProcessorQueueId() {
		return dnProcessorQueueId;
	}

	public String getDnProcessorWfp() {
		return dnProcessorWfp;
	}

	public int getProcessFinalStatusTime() {
		return processFinalStatusTime;
	}

	public int getResponseTimeOut() {
		return responseTimeOut;
	}

	public int getMaxPendingRequest() {
		return maxPendingRequest;
	}

	public int getMaxPendingResponse() {
		return maxPendingResponse;
	}

	public int getRequestPollerInterval() {
		return requestPollerInterval;
	}

	public String getRequestOperation() {
		return requestOperation;
	}

	public String getResponseOperation() {
		return responseOperation;
	}

	public int getTxnTimeOut() {
		return txnTimeOut;
	}

	public int getRetryResponseInterval() {
		return retryResponseInterval;
	}

	public int getMaxConnsToVtelServer() {
		return maxConnsToVtelServer;
	}

	public String customize2String() {
		return "ViettelSettings [cdrProcessorQueueId=" + cdrProcessorQueueId
		+ "\n"+"cdrProcessorWfp=" + cdrProcessorWfp + "\n"+"clientId="
		+ clientId + "\n"+"connectionType=" + connectionType
		+ "\n"+"deliveryQueueId=" + deliveryQueueId + "\n"+"deliveryWfp="
		+ deliveryWfp + "\n"+"dnProcessorQueueId=" + dnProcessorQueueId
		+ "\n"+"dnProcessorWfp=" + dnProcessorWfp + "\n"+"ftpHost=" + ftpHost
		+ "\n"+"ftpPassword=" + ftpPassword + "\n"+"ftpPort=" + ftpPort
		+ "\n"+"ftpUserName=" + ftpUserName + "\n"+"localMobiviPath="
		+ localMobiviPath + "\n"+"localViettelPath=" + localViettelPath
		+ "\n"+"maxConnectionRetry=" + maxConnectionRetry
		+ "\n"+"maxConnsToVtelServer=" + maxConnsToVtelServer
		+ "\n"+"maxPendingRequest=" + maxPendingRequest
		+ "\n"+"maxPendingResponse=" + maxPendingResponse
		+ "\n"+"maxSeqError=" + maxSeqError + "\n"+"maxUnknownTxnSize="
		+ maxUnknownTxnSize + "\n"+"privateKey=" + privateKey
		+ "\n"+"processFinalStatusTime=" + processFinalStatusTime
		+ "\n"+"publicKey=" + publicKey + "\n"+"remoteMobiviPath="
		+ remoteMobiviPath + "\n"+"remoteViettelPath=" + remoteViettelPath
		+ "\n"+"requestOperation=" + requestOperation
		+ "\n"+"requestPollerInterval=" + requestPollerInterval
		+ "\n"+"responseErrors=" + responseErrors + "\n"+"responseOperation="
		+ responseOperation + "\n"+"responseTimeOut=" + responseTimeOut
		+ "\n"+"retryResponseInterval=" + retryResponseInterval
		+ "\n"+"sendNetworkCheckTimeInterval="
		+ sendNetworkCheckTimeInterval + "\n"+"successRcList="
		+ successRcList + "\n"+"syncFileExtension=" + syncFileExtension
		+ "\n"+"syncMidFileName=" + syncMidFileName
		+ "\n"+"syncTimeIntervalMins=" + syncTimeIntervalMins
		+ "\n"+"tmpFileExtension=" + tmpFileExtension
		+ "\n"+"txnTimeOut="	+ txnTimeOut 
		+ "\n"+"amountValidate=" + amountValidate
		+ "\n"+"waitRcList=" + waitRcList + "]";
	}
	
	
	public String getLocalMobiviPath() {
		return localMobiviPath;
	}

	public String getLocalViettelPath() {
		return localViettelPath;
	}

	public String getRemoteMobiviPath() {
		return remoteMobiviPath;
	}

	public String getRemoteViettelPath() {
		return remoteViettelPath;
	}

	public String getFtpHost() {
		return ftpHost;
	}

	public int getFtpPort() {
		return ftpPort;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public String getSyncFileExtension() {
		return syncFileExtension;
	}

	public String getTmpFileExtension() {
		return tmpFileExtension;
	}

	public int getSyncTimeIntervalMins() {
		return syncTimeIntervalMins;
	}

	public long getSendNetworkCheckTimeInterval() {
		return sendNetworkCheckTimeInterval;
	}

	public int getMaxConnectionRetry() {
		return maxConnectionRetry;
	}


	public String getSyncMidFileName() {
		return syncMidFileName;
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


	public int getMaxUnknownTxnSize() {
		return maxUnknownTxnSize;
	}

	public boolean isAmountValidate() {
		return amountValidate;
	}

	public String getFileEndWith() {
		return fileEndWith;
	}
	
	
}
