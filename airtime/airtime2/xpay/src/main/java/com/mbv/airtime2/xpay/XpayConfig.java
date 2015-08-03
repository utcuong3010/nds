package com.mbv.airtime2.xpay;

public class XpayConfig {
	private String webservice_url = "http://test.simso888.com:2024/PartnerServices/PartnerService.asmx";
	private String partner_code = "9040a955c699df1bb894e41fb18a0a9d";
	private String username = "simpay";
	private String password = "12345";
	private String version = "3.0.1.5";
	private String appid = "partner";
	private String successCodes;
	private String pendingCodes;
	private String unknownCodes;
	private int numberOfChildren = 3;
	private int numberOfConsicutiveRequests = 10;
	private long pendingTransactionCheckingInterval = 30; // seconds
	private long balanceCheckingInterval = 60; // seconds
	private String postpaidSuffix = "P2";

	@Override
	public String toString() {
		return "XpayConfig [webservice_url=" + webservice_url + ", partner_code=" + partner_code
				+ ", username=" + username + ", password=" + password + ", version=" + version
				+ ", successCodes=" + successCodes + ", pendingCodes=" + pendingCodes
				+ ", unknownCodes=" + unknownCodes + ", numberOfChildren=" + numberOfChildren
				+ ", numberOfConsicutiveRequests=" + numberOfConsicutiveRequests
				+ ", pendingTransactionCheckingInterval=" + pendingTransactionCheckingInterval
				+ ", balanceCheckingInterval=" + balanceCheckingInterval + ", postpaidSuffix="
				+ postpaidSuffix + "]";
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

	public void setPendingTransactionCheckingInterval(long pendingTransactionCheckingInterval) {
		this.pendingTransactionCheckingInterval = pendingTransactionCheckingInterval;
	}

	public String getPartner_code() {
		return partner_code;
	}

	public void setPartner_code(String partner_code) {
		this.partner_code = partner_code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getWebservice_url() {
		return webservice_url;
	}

	public void setWebservice_url(String webservice_url) {
		this.webservice_url = webservice_url;
	}

	public long getBalanceCheckingInterval() {
		return balanceCheckingInterval;
	}

	public void setBalanceCheckingInterval(long balanceCheckingInterval) {
		this.balanceCheckingInterval = balanceCheckingInterval;
	}

	public String getPostpaidSuffix() {
		return postpaidSuffix;
	}

	public void setPostpaidSuffix(String postpaidSuffix) {
		this.postpaidSuffix = postpaidSuffix;
	}

	public String getUnknownCodes() {
		return unknownCodes;
	}

	public void setUnknownCodes(String unknownCodes) {
		this.unknownCodes = unknownCodes;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

}
