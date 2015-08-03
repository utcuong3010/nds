package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class AnypayAccountTxn {
	
	public static final String SELECT="AnypayAccountTxn_Select";
	public static final String INSERT="AnypayAccountTxn_Insert";
	public static final String UPDATE="AnypayAccountTxn_Update";
	public static final String SEARCH_BY_FILTER="AnypayAccountTxn_SearchByFilter";
	public static final String SEARCH_BY_FILTER_COUNT="AnypayAccountTxn_SearchByFilterCount";
	public static final String UPDATE_DELETED="AnypayAccountTxn_UpdateDeleted";
    
	private Date txnDate;

    private String simId;

    private Long amount;

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

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId == null ? null : simId.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
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
		return "AnypayAccountTxn [amount=" + amount + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", deleted="
				+ deleted + ", description=" + description + ", employee="
				+ employee + ", simId=" + simId + ", txnDate=" + txnDate
				+ ", txnId=" + txnId + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + "]";
	}
    
    
}