package com.mbv.frontend.model;

public class SimInfoModel {
	private String comId;

    private String simType;

    private String currentAmount;

    private String lockAmount;

    private String ip;
    
    private String msisdn;

    private String simStatus;
    
    private String lastSentSms;

	public String getComId() {
		return comId;
	}

	public void setComId(String comId) {
		this.comId = comId;
	}

	public String getSimType() {
		return simType;
	}

	public void setSimType(String simType) {
		this.simType = simType;
	}

	public String getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getLockAmount() {
		return lockAmount;
	}

	public void setLockAmount(String lockAmount) {
		this.lockAmount = lockAmount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSimStatus() {
		return simStatus;
	}

	public void setSimStatus(String simStatus) {
		this.simStatus = simStatus;
	}
	
	public String getLastSentSms(){
		return lastSentSms;
	}
	
	public void setLastSentSms(String lastSentSms){
		this.lastSentSms = lastSentSms;
	}

	@Override
	public String toString() {
		return "SimInfoModel [comId=" + comId + ", currentAmount="
				+ currentAmount + ", ip=" + ip + ", lockAmount=" + lockAmount
				+ ", msisdn=" + msisdn + ", simStatus=" + simStatus
				+ ", simType=" + simType +", last sent "+ lastSentSms+ "]";
	}
    
    
}
