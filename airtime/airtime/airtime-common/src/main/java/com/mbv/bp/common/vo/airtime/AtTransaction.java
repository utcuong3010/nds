package com.mbv.bp.common.vo.airtime;

import java.util.Date;
import java.util.List;

public class AtTransaction {

    public static final String INSERT = "AtTransaction_Insert";
    public static final String UPDATE = "AtTransaction_Update";
    public static final String SELECT = "AtTransaction_Select";
    public static final String SELECT_BY_AT_TXN_IDS = "AtTransaction_SelectByAtTnxIds";
    public static final String SELECT_BY_AT_TXN_IDS_HISTORY = "AtTransactionHistory_SelectByAtTnxIds";
    public static final String SELECT_BY_AT_TXN_ID = "AtTransaction_SelectByAtTnxId";
    public static final String SEARCH_BY_FILTER = "AtTransaction_SearchByFilter";
    public static final String SEARCH_FOR_COMPARE_BY_FILTER = "AtTransaction_SearchForCompare";
	public static final String SEARCH_BY_FILTER_COUNT="AtTransaction_SearchByFilterCount";
	public static final String UPDATE_FOR_DELETE="AtTransaction_UpdateTerminate";
	public static final String SELECT_MAX_TXN_DATE="AtTransaction_SelectMaxTxnDate";
	public static final String UPDATE_BY_AT_TXN_ID = "AtTransaction_UpdateByAtTxnId";
    
    private Date txnDate;

    private Long atTxnId;

    private Date deliveryDate;

    private Integer amount;

    private String messageId;

    private String msgType;

    private String msisdn;

    private String telcoId;

    private String connType;

    private Integer timeOut;

    private String status;

    private String errorCode;

    private String createdBy;

    private Date createdDate;

    private String updatedBy;

    private Date updatedDate;

    private String channelId;

    private String txnId;
    
    private Long summaryAmount;
    
    private Integer txnCount;
    
    private Long deleted;
    
    private String serverId;
    
    private String txnType;
    
    private List<Long>  atTxnIdList;

    private String txnStatus;
    
    private Date responseDate;
    
    public List<Long> getAtTxnIdList() {
		return atTxnIdList;
	}

	public void setAtTxnIdList(List<Long> atTxnIdList) {
		this.atTxnIdList = atTxnIdList;
	}

	public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId == null ? null : channelId.trim();
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

    public Long getAtTxnId() {
        return atTxnId;
    }

    public void setAtTxnId(Long atTxnId) {
        this.atTxnId = atTxnId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getTelcoId() {
        return telcoId;
    }

    public void setTelcoId(String telcoId) {
        this.telcoId = telcoId == null ? null : telcoId.trim();
    }

    public String getConnType() {
        return connType;
    }

    public void setConnType(String connType) {
        this.connType = connType == null ? null : connType.trim();
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
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

	public Long getSummaryAmount() {
		return summaryAmount;
	}

	public void setSummaryAmount(Long summaryAmount) {
		this.summaryAmount = summaryAmount;
	}

	public Integer getTxnCount() {
		return txnCount;
	}

	public void setTxnCount(Integer txnCount) {
		this.txnCount = txnCount;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	
	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		 this.serverId = serverId == null ? null : serverId.trim();
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		 this.txnType = txnType == null ? null : txnType.trim();
	}

	@Override
	public String toString() {
		return "AtTransaction [amount=" + amount + ", atTxnId=" + atTxnId
				+ ", atTxnIdList=" + atTxnIdList + ", channelId=" + channelId
				+ ", connType=" + connType + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", deleted=" + deleted
				+ ", deliveryDate=" + deliveryDate + ", errorCode=" + errorCode
				+ ", messageId=" + messageId + ", msgType=" + msgType
				+ ", msisdn=" + msisdn + ", responseDate=" + responseDate
				+ ", serverId=" + serverId + ", status=" + status
				+ ", summaryAmount=" + summaryAmount + ", telcoId=" + telcoId
				+ ", timeOut=" + timeOut + ", txnCount=" + txnCount
				+ ", txnDate=" + txnDate + ", txnId=" + txnId + ", txnStatus="
				+ txnStatus + ", txnType=" + txnType + ", updatedBy="
				+ updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	
}