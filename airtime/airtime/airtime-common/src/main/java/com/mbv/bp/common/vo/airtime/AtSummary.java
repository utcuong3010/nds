package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class AtSummary {
	
	public static final String SEARCH_BY_FILTER = "AtSummary_SearchByFilter";
	public static final String SEARCH_BY_FILTER_COUNT = "AtSummary_SearchByFilterCount";
	public static final String SELECT="AtSummary_Select";
	public static final String INSERT="AtSummary_Insert";
	public static final String UPDATE="AtSummary_Update";
	public static final String SELECT_MAX_DATE_BY_PROVIDER_ID="AtSummary_SelectEndDateByProviderId";
	public static final String SELECT_BEGIN_AMOUNT="AtSummary_BeginAmount";
	
    private Long totalAmount;

    private Long totalInputAmount;

    private Long usedAmount;

    private Integer totalTxn;
	
    private String providerId;

    private Date txnDate;

    private Long beginAmount;
	private Long endAmount;
	public Long getBeginAmount() {
		return beginAmount;
	}
	public void setBeginAmount(Long beginAmount) {
		this.beginAmount = beginAmount;
	}
	public Long getEndAmount() {
		return endAmount;
	}
	public void setEndAmount(Long endAmount) {
		this.endAmount = endAmount;
	}
    
    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId == null ? null : providerId.trim();
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
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

    public Long getUsedAmount() {
        return usedAmount;
    }

    public void setUsedAmount(Long usedAmount) {
        this.usedAmount = usedAmount;
    }

    public Integer getTotalTxn() {
        return totalTxn;
    }

    public void setTotalTxn(Integer totalTxn) {
        this.totalTxn = totalTxn;
    }
	@Override
	public String toString() {
		return "AtSummary [beginAmount=" + beginAmount + ", endAmount="
				+ endAmount + ", providerId=" + providerId + ", totalAmount="
				+ totalAmount + ", totalInputAmount=" + totalInputAmount
				+ ", totalTxn=" + totalTxn + ", txnDate=" + txnDate
				+ ", usedAmount=" + usedAmount + "]";
	}

	
}