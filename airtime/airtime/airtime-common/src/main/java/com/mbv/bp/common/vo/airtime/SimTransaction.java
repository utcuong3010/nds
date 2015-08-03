package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class SimTransaction {
    public static final String SELECT_BY_TXN_ID = "SimTransaction_SelectByTxnId";

	public static final String INSERT = "SimTransaction_Insert";

	public static final String UPDATE = "SimTransaction_UpdateByTxnId";

	public static final String SELECT_PD_STATUS_BY_MSISDN_SIMID_TXNTYPE = "SimTransaction_SelectPDStatusByMsisdnSimIdTxnType";
	
	public static final String SELECT_PENDING_STATUS_BY_SIMID_TXNTYPE ="SimTransaction_SelectPendingStatusBySimIdTxnType";
	
	public static final String SEARCH_BY_FILTER_COUNT = "SimTransaction_SearchByFilterCount";
	
	public static final String SEARCH_BY_FILTER = "SimTransaction_SearchByFilter";

	private Long txnId;

    private String msisdn;

    private Long amount;

    private String txnType;

    private String txnStatus;

    private Date txnDate;

    private String errorCode;

    private String messageId;
    
    private Long simAmount;
    
    private Long lockAmount;
    
    private String billing;
    
    private String simId;
    
    public Long getTxnId() {
        return txnId;
    }

    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType == null ? null : txnType.trim();
    }

    public String getTxnStatus() {
        return txnStatus;
    }

    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus == null ? null : txnStatus.trim();
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

	public Long getSimAmount() {
		return simAmount;
	}

	public void setSimAmount(Long simAmount) {
		this.simAmount = simAmount;
	}

	public Long getLockAmount() {
		return lockAmount;
	}

	public void setLockAmount(Long lockAmount) {
		this.lockAmount = lockAmount;
	}

	public String getBilling() {
		return billing;
	}

	public void setBilling(String billing) {
		this.billing = billing == null ? null : billing.trim();
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId == null ? null : simId.trim();
	}

	@Override
	public String toString() {
		return "SimTransaction [amount=" + amount + ", billing=" + billing
				+ ", errorCode=" + errorCode + ", lockAmount=" + lockAmount
				+ ", messageId=" + messageId + ", msisdn=" + msisdn
				+ ", simAmount=" + simAmount + ", simId=" + simId
				+ ", txnDate=" + txnDate + ", txnId=" + txnId + ", txnStatus="
				+ txnStatus + ", txnType=" + txnType + "]";
	}
	
}