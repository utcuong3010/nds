package com.mbv.frontend.model;



public class AtSummaryModel{
	private String totalAmount;

    private String totalInputAmount;

    private String usedAmount;

    private String totalTxn;
	
    private String providerId;

    private String txnDate;

    private String beginAmount;
	private String endAmount;
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getTotalInputAmount() {
		return totalInputAmount;
	}
	public void setTotalInputAmount(String totalInputAmount) {
		this.totalInputAmount = totalInputAmount;
	}
	public String getUsedAmount() {
		return usedAmount;
	}
	public void setUsedAmount(String usedAmount) {
		this.usedAmount = usedAmount;
	}
	public String getTotalTxn() {
		return totalTxn;
	}
	public void setTotalTxn(String totalTxn) {
		this.totalTxn = totalTxn;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}
	public String getBeginAmount() {
		return beginAmount;
	}
	public void setBeginAmount(String beginAmount) {
		this.beginAmount = beginAmount;
	}
	public String getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(String endAmount) {
		this.endAmount = endAmount;
	}
	
	
}
