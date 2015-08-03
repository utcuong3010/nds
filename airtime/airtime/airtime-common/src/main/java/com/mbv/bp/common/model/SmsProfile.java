package com.mbv.bp.common.model;

import java.util.List;

public class SmsProfile {
	private String profileId;
	private String sender;
	private String startWith;
	private List<SmsField> smsFields;
	private String txnType;
	private String txnStatus;
	
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getStartWith() {
		return startWith;
	}
	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}
	public List<SmsField> getSmsFields() {
		return smsFields;
	}
	public void setSmsFields(List<SmsField> smsFields) {
		this.smsFields = smsFields;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	@Override
	public String toString() {
		return "SmsProfile [profileId=" + profileId + ", sender=" + sender
				+ ", smsFields=" + smsFields + ", startWith=" + startWith
				+ ", txnStatus=" + txnStatus + ", txnType=" + txnType + "]";
	}
}
