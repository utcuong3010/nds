package com.mbv.frontend.model;

public class AnypayTxnModel {
	
	private String fromDate;
	
	private String toDate;

    private String page;
    
    private String txnId;

    private String msisdn;

    private String amount;

    private String txnType;

    private String txnStatus;

    private String txnDate;

    private String errorCode;

    private String messageId;
    
    private String simAmount;
    
    private String lockAmount;
    
    private String billing;
    
    private String simId;
    
    public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSimAmount() {
		return simAmount;
	}

	public void setSimAmount(String simAmount) {
		this.simAmount = simAmount;
	}

	public String getLockAmount() {
		return lockAmount;
	}

	public void setLockAmount(String lockAmount) {
		this.lockAmount = lockAmount;
	}

	public String getBilling() {
		return billing;
	}

	public void setBilling(String billing) {
		this.billing = billing;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	@Override
	public String toString() {
		return "AnypayTxnModel [amount=" + amount + ", billing=" + billing
				+", errorCode="
				+ errorCode + ", fromDate=" + fromDate + ", lockAmount="
				+ lockAmount + ", messageId=" + messageId + ", msisdn="
				+ msisdn + ", page=" + page + ", simAmount=" + simAmount
				+ ", simId=" + simId + ", toDate=" + toDate + ", txnDate="
				+ txnDate + ", txnId=" + txnId + ", txnStatus=" + txnStatus
				+ ", txnType=" + txnType + "]";
	}
    
    
}
