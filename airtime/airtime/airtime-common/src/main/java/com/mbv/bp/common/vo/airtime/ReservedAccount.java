package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class ReservedAccount {
	
    public static final String INSERT = "ReservedAccount_Insert";

	public static final String SELECT_LOCK = "ReservedAccount_Select_Lock";

	public static final String UPDATE_CREDIT_AMOUNT = "ReservedAccount_Update_Credit_Amount";

	public static final String SELECT = "ReservedAccount_Select";

	public static final String UPDATE_DEBIT_AMOUNT = "ReservedAccount_Update_Debit_Amount";
	
	public static final String SEARCH_BY_FILTER = "ReservedAccount_SearchByFilter";
	
	public static final String SEARCH_BY_FILTER_COUNT = "ReservedAccount_SearchByFilterCount";
	
    private String accountId;

    private Long totalDebit;

    private Long totalCredit;

    private String systemId;

    private String telcoIds;

    private String description;

    private Date createdDate;

    private Date updatedDate;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public Long getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Long totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Long getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Long totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getTelcoIds() {
        return telcoIds;
    }

    public void setTelcoIds(String telcoIds) {
        this.telcoIds = telcoIds == null ? null : telcoIds.trim();
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}