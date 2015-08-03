package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class ProviderAccount {

	public static final String SELECT_ALL = "ProviderAccount_SelectAll";
	public static final String SELECT = "ProviderAccount_Select";
	public static final String INSERT = "ProviderAccount_Insert";
	public static final String DYNAMIC_UPDATE = "ProviderAccount_DynamicUpdate";
	public static final String UPDATE_DELETED = "ProviderAccount_UpdateDeleted";
	public static final String SEARCH_BY_FILTER = "ProviderAccount_SearchByFilter";
	public static final String SEARCH_BY_FILTER_COUNT="ProviderAccount_SearchByFilterCount";
	public static final String SEARCH_BY_MIN_DATE_BY_PROVIDER_ID="ProviderAccount_SelectStartDateByProviderId";
	public static final String PROVIDER_SUMMARY_BY_DATE="ProviderAccount_ProviderSummaryByDate";
	public static final String SEARCH_BY_MAX_DATE_BY_PROVIDER_ID = "ProviderAccount_SelectMaxDateByProviderId";
	public static final String SELECT_TOTAL_AMOUNT_BY_PROVIDER_ID="ProviderAccount_SelectTotalAmountByProviderId";
    private Date txnDate;

    private String providerId;

    private String discount;

    private Long totalAmount;

    private Long inputAmount;

    private String employee;

    private String description;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

	private Long deleted;

    private String txnId;

    public Long getDeleted() {
        return deleted;
    }

    public void setDeleted(Long deleted) {
        this.deleted = deleted;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId == null ? null : providerId.trim();
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount == null ? null : discount.trim();
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getInputAmount() {
        return inputAmount;
    }

    public void setInputAmount(Long inputAmount) {
        this.inputAmount = inputAmount;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee == null ? null : employee.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

	@Override
	public String toString() {
		return "ProviderAccount [createdBy=" + createdBy + ", createdDate="
				+ createdDate + ", deleted=" + deleted + ", description="
				+ description + ", discount=" + discount + ", employee="
				+ employee + ", inputAmount=" + inputAmount + ", providerId="
				+ providerId + ", totalAmount=" + totalAmount + ", txnDate="
				+ txnDate + ", txnId=" + txnId + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + "]";
	}
    
}