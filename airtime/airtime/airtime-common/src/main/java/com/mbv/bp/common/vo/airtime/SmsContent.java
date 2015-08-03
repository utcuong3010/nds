package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class SmsContent {
    public static final String INSERT = "SmsContent_Insert";
    
    public static final String SEARCH_BY_FILTER_COUNT = "SmsContent_SearchByFilterCount";
	
	public static final String SEARCH_BY_FILTER = "SmsContent_SearchByFilter";

    private String messageId;

    private String processed;

    private Date msgDate;

    private String txtContent;

    private String orgContent;

    private String sender;

    private Date txnDate;

    private String txnType;

    private String txnAmount;

    private String txnStatus;

    private String smsAmount;

    private Integer totalPart;

    private Integer partNo;

    private String partId;

    private String fraudStatus;

    private String msisdn;

    private String simId;
    
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
    	this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed == null ? null : processed.trim();
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent == null ? null : txtContent.trim();
    }

    public String getOrgContent() {
        return orgContent;
    }

    public void setOrgContent(String orgContent) {
        this.orgContent = orgContent == null ? null : orgContent.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType == null ? null : txnType.trim();
    }

    public String getTxnAmount() {
        return txnAmount;
    }

    public void setTxnAmount(String txnAmount) {
        this.txnAmount = txnAmount == null ? null : txnAmount.trim();
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus == null ? null : txnStatus.trim();
    }

    public String getSmsAmount() {
        return smsAmount;
    }

    public void setSmsAmount(String smsAmount) {
        this.smsAmount = smsAmount == null ? null : smsAmount.trim();
    }

    public Integer getTotalPart() {
        return totalPart;
    }

    public void setTotalPart(Integer totalPart) {
        this.totalPart = totalPart;
    }

    public Integer getPartNo() {
        return partNo;
    }

    public void setPartNo(Integer partNo) {
        this.partNo = partNo;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId == null ? null : partId.trim();
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    public void setFraudStatus(String fraudStatus) {
        this.fraudStatus = fraudStatus == null ? null : fraudStatus.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId == null ? null : simId.trim();
	}

	@Override
	public String toString() {
		return "SmsContent [fraudStatus=" + fraudStatus + ", messageId="
				+ messageId + ", msgDate=" + msgDate + ", msisdn=" + msisdn
				+ ", orgContent=" + orgContent + ", partId=" + partId
				+ ", partNo=" + partNo + ", processed=" + processed
				+ ", sender=" + sender + ", simId=" + simId + ", smsAmount="
				+ smsAmount + ", totalPart=" + totalPart + ", txnAmount="
				+ txnAmount + ", txnDate=" + txnDate + ", txnStatus="
				+ txnStatus + ", txnType=" + txnType + ", txtContent="
				+ txtContent + "]";
	}
    
}