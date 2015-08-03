package com.mbv.bp.common.model;

import java.util.Date;

public class MobiPwdChangeInfo {
	private String userName;
	private String password;
	private Date txnDate;
	private String txnId;
	private String status;
	private String errorCode;
	private String nameSpace;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	@Override
	public String toString() {
		return "MobiUserInfo [errorCode=" + errorCode + ", nameSpace="
				+ nameSpace + ", password=" + password + ", status=" + status
				+ ", txnDate=" + txnDate + ", txnId=" + txnId + ", userName="
				+ userName + "]";
	}
}
