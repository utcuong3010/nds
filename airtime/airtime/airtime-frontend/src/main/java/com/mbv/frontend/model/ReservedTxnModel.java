package com.mbv.frontend.model;

public class ReservedTxnModel {

	private String txnId;
    private String accountId;
    private String systemId;
    private String amount;
    private String referenceId;
    private String operation;
    private String description;
    private String createdDate;
	private String fromDate;
	private String toDate;
	private String page;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
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
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "ReservedTxnModel [accountId=" + accountId + ", amount="
				+ amount + ", createdDate=" + createdDate + ", description="
				+ description + ", fromDate=" + fromDate + ", operation="
				+ operation + ", page=" + page + ", referenceId=" + referenceId
				+ ", systemId=" + systemId + ", toDate=" + toDate + ", txnId="
				+ txnId + "]";
	}
	
	
}
