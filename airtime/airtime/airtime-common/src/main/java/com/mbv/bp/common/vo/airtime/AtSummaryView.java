package com.mbv.bp.common.vo.airtime;


import java.util.Date;

public class AtSummaryView {
	public static final String SEARCH_BY_FILTER = "AtSummaryView_SearchByFilter";
	public static final String SEARCH_BY_FILTER_COUNT = "AtSummaryView_SearchByFilterCount";
	public static final String SELECT_MAX_DATE_BY_PROVIDER_ID="AtSummaryView_SelectEndDateByProviderId";
	public static final String SELECT_CUR_PROVIDERS_AMOUNT="AtSummaryView_SearchProviderAccountInfo";
	public static final String SELECT_CUR_PROVIDERS_AMOUNT_BY_PROVIDER_ID="AtSummaryView_SearchProviderAccountInfoByProviderId";
	private Long beginAmount;

    private Date txnDate;

    private String providerId;

    private Long usedAmount;

    private Long totalTxn;

    private Long totalAmount; 

    private Long totalInputAmount;

    private Long endAmount;

    public Long getBeginAmount() {
        return beginAmount;
    }

    public void setBeginAmount(Long beginAmount) {
        this.beginAmount = beginAmount;
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

    public Long getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Long usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Long getTotalTxn() {
        return totalTxn;
    }

    public void setTotalTxn(Long totalTxn) {
        this.totalTxn = totalTxn;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTotalInputAmount() {
        return totalInputAmount;
    }

    public void setTotalInputAmount(Long totalInputAmount) {
        this.totalInputAmount = totalInputAmount;
    }

    public Long getEndAmount() {
        return endAmount;
    }

    public void setEndAmount(Long endAmount) {
        this.endAmount = endAmount;
    }

	@Override
	public String toString() {
		return "AtSummaryView [beginAmount=" + beginAmount + ", endAmount="
				+ endAmount + ", providerId=" + providerId + ", totalAmount="
				+ totalAmount + ", totalInputAmount=" + totalInputAmount
				+ ", totalTxn=" + totalTxn + ", txnDate=" + txnDate
				+ ", usedAmount=" + usedAmount + "]";
	}
    
}