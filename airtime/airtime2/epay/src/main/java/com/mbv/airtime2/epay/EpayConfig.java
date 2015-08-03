package com.mbv.airtime2.epay;

import java.util.List;
import java.util.Map;

public class EpayConfig {
	private String webservice_url = "";
	private String partnerName = "";
	private int serviceTimeOut;
	private int numberOfChildren;
	private int numberOfConsicutiveRequests;
	private long pendingTransactionCheckingInterval;
	private Map<String, List<String>> providerTypeMap;
	private Encrypt signer;
	private String successCodes;
	private String pendingCodes;

	public EpayConfig(){
	}
	
	

	public String getSuccessCodes() {
		return successCodes;
	}



	public void setSuccessCodes(String successCodes) {
		this.successCodes = successCodes;
	}



	public String getPendingCodes() {
		return pendingCodes;
	}



	public void setPendingCodes(String pendingCodes) {
		this.pendingCodes = pendingCodes;
	}



	public String getWebservice_url() {
		return webservice_url;
	}

	public void setWebservice_url(String webservice_url) {
		this.webservice_url = webservice_url;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public int getNumberOfChildren() {
		return numberOfChildren;
	}

	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}

	public int getNumberOfConsicutiveRequests() {
		return numberOfConsicutiveRequests;
	}

	public void setNumberOfConsicutiveRequests(int numberOfConsicutiveRequests) {
		this.numberOfConsicutiveRequests = numberOfConsicutiveRequests;
	}

	public long getPendingTransactionCheckingInterval() {
		return pendingTransactionCheckingInterval;
	}

	public void setPendingTransactionCheckingInterval(
			long pendingTransactionCheckingInterval) {
		this.pendingTransactionCheckingInterval = pendingTransactionCheckingInterval;
	}

	public Map<String, List<String>> getProviderTypeMap() {
		return providerTypeMap;
	}

	public void setProviderTypeMap(Map<String, List<String>> providerTypeMap) {
		this.providerTypeMap = providerTypeMap;
	}
	public Encrypt getSigner() {
		return signer;
	}
	public void setSigner(Encrypt signer) {
		this.signer = signer;
	}

	public int getServiceTimeOut() {
		return serviceTimeOut;
	}

	public void setServiceTimeOut(int serviceTimeOut) {
		this.serviceTimeOut = serviceTimeOut;
	}
}
