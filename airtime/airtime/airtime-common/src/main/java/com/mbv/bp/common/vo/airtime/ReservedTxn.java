package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class ReservedTxn {
    public static final String INSERT = "ReservedTxn_Insert";

	public static final String SELECT = "ReservedTxn_Select";
	
	public static final String SEARCH_BY_FILTER = "ReservedTxn_SearchByFilter";
	
	public static final String SEARCH_BY_FILTER_COUNT = "ReservedTxn_SearchByFilterCount";

    private String txnId;

    private String accountId;

    private String systemId;

    private Long amount;

    private String referenceId;

    private String operation;

    private String description;

    private Date createdDate;

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId == null ? null : txnId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId == null ? null : referenceId.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}