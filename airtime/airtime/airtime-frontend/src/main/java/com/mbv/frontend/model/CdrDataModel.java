package com.mbv.frontend.model;

import java.util.Date;

public class CdrDataModel {
	
	private String txnDate;
	
	private String fromDate;
	
	private String toDate;

    private String atTxnId;

    private String amount;

    private String messageId;

    private String msisdn;

    private String providerId;
    
    private String price;

    private String status;
    
    private String pstatus;
    
    private String ptxnId;

    private String errorCode;
    
    private String perrorCode;

    private String createdBy;

    private String page;

    private String result;
    
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
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

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPstatus() {
		return pstatus;
	}

	public void setPstatus(String pstatus) {
		this.pstatus = pstatus;
	}

	public String getPtxnId() {
		return ptxnId;
	}

	public void setPtxnId(String ptxnId) {
		this.ptxnId = ptxnId;
	}

	public String getPerrorCode() {
		return perrorCode;
	}

	public void setPerrorCode(String perrorCode) {
		this.perrorCode = perrorCode;
	}
	
}
