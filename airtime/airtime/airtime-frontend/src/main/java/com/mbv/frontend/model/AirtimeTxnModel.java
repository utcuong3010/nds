package com.mbv.frontend.model;

import java.util.Date;

public class AirtimeTxnModel {
	
	private String txnDate;
	
	private String fromDate;
	
	private String toDate;

    private String atTxnId;

    private String deliveryDate;

    private String amount;

    private String messageId;

    private String msgType;

    private String msisdn;

    private String telcoId;

    private String connType;

    private String timeOut;

    private String networkCode;

    private String status;

    private String errorCode;

    private String createdBy;

    private String createdDate;

    private String updatedBy;

    private String updatedDate;

    private String channelId;

    private String txnId;

    private String page;
    
    private String currentStatus;
    
	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

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

	public String getAtTxnId() {
		return atTxnId;
	}

	public void setAtTxnId(String atTxnId) {
		this.atTxnId = atTxnId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTelcoId() {
		return telcoId;
	}

	public void setTelcoId(String telcoId) {
		this.telcoId = telcoId;
	}

	public String getConnType() {
		return connType;
	}

	public void setConnType(String connType) {
		this.connType = connType;
	}

	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	@Override
	public String toString() {
		return "AirtimeTxnModel [amount=" + amount + ", atTxnId=" + atTxnId
				+ ", channelId=" + channelId + ", connType=" + connType
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate
				+ ", deliveryDate=" + deliveryDate + ", errorCode=" + errorCode
				+ ", fromDate=" + fromDate + ", messageId=" + messageId
				+ ", msgType=" + msgType + ", msisdn=" + msisdn
				+ ", networkCode=" + networkCode + ", status=" + status
				+ ", telcoId=" + telcoId + ", timeOut=" + timeOut + ", toDate="
				+ toDate + ", txnDate=" + txnDate + ", txnId=" + txnId
				+ ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ "]";
	}
    
    
}
