package com.mbv.frontend.model;

public class AnypaySmsModel {
	
	private String fromDate;
	
	private String toDate;

    private String page;
    
    private String messageId;

    private String processed;

    private String msgDate;

    private String txtContent;

    private String orgContent;

    private String sender;

    private String txnDate;

    private String txnType;

    private String txnAmount;

    private String txnStatus;

    private String smsAmount;

    private String totalPart;

    private String partNo;

    private String partId;

    private String fraudStatus;

    private String msisdn;
    
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

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public String getTxtContent() {
		return txtContent;
	}

	public void setTxtContent(String txtContent) {
		this.txtContent = txtContent;
	}

	public String getOrgContent() {
		return orgContent;
	}

	public void setOrgContent(String orgContent) {
		this.orgContent = orgContent;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getSmsAmount() {
		return smsAmount;
	}

	public void setSmsAmount(String smsAmount) {
		this.smsAmount = smsAmount;
	}

	public String getTotalPart() {
		return totalPart;
	}

	public void setTotalPart(String totalPart) {
		this.totalPart = totalPart;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getFraudStatus() {
		return fraudStatus;
	}

	public void setFraudStatus(String fraudStatus) {
		this.fraudStatus = fraudStatus;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	@Override
	public String toString() {
		return "AnypaySmsModel [fraudStatus=" + fraudStatus + ", fromDate="
				+ fromDate + ", messageId=" + messageId + ", msgDate="
				+ msgDate + ", msisdn=" + msisdn + ", orgContent=" + orgContent
				+ ", page=" + page + ", partId=" + partId + ", partNo="
				+ partNo + ", processed=" + processed + ", sender=" + sender
				+ ", simId=" + simId + ", smsAmount=" + smsAmount + ", toDate="
				+ toDate + ", totalPart=" + totalPart + ", txnAmount="
				+ txnAmount + ", txnDate=" + txnDate + ", txnStatus="
				+ txnStatus + ", txnType=" + txnType + ", txtContent="
				+ txtContent + "]";
	}

	
    
}
