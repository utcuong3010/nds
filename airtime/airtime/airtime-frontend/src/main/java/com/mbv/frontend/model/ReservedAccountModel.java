package com.mbv.frontend.model;

public class ReservedAccountModel {
	private String accountId;
    private String totalDebit;
    private String totalCredit;
    private String amount;
    private String systemId;
    private String telcoIds;
    private String description;
    private String createdDate;
    private String updatedDate;
	private String fromDate;
	private String toDate;
	private String page;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getTotalDebit() {
		return totalDebit;
	}
	public void setTotalDebit(String totalDebit) {
		this.totalDebit = totalDebit;
	}
	public String getTotalCredit() {
		return totalCredit;
	}
	public void setTotalCredit(String totalCredit) {
		this.totalCredit = totalCredit;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getTelcoIds() {
		return telcoIds;
	}
	public void setTelcoIds(String telcoIds) {
		this.telcoIds = telcoIds;
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
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
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
		return "ReservedAmountModel [accountId=" + accountId + ", amount="
				+ amount + ", createdDate=" + createdDate + ", description="
				+ description + ", fromDate=" + fromDate + ", page=" + page
				+ ", systemId=" + systemId + ", telcoIds=" + telcoIds
				+ ", toDate=" + toDate + ", totalCredit=" + totalCredit
				+ ", totalDebit=" + totalDebit + ", updatedDate=" + updatedDate
				+ "]";
	}
	
	
}
