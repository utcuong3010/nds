package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class CdrSync {
	
	public static final String SELECT_BY_AT_TXN_ID_AND_PROVIDER_ID = "CdrSync_selectByAtTxnIdAndProviderId";
	public static final String SELECT_BY_MESSAGE_ID_AND_PROVIDER_ID = "CdrSync_selectByMessageIdAndProviderId";
	public static final String SELECT_BY_AT_TXN_ID_AND_PROVIDER_ID_ERROR_CODE = "CdrSync_selectByAtTxnIdAndProviderIdAndErrorCode";
	public static final String SELECT_BY_P_TXN_ID_AND_PROVIDER_ID = "CdrSync_selectByPTxnIdAndProviderId";
	public static final String UPDATE_BY_AT_TXN_ID_AND_PROVIDER_ID_ERROR_CODE = "CdrSync_updateProviderDataByAtTxnIdAndProviderIdAndErrorCode";
	public static final String UPDATE_BY_AT_TXN_ID_AND_PROVIDER_ID = "CdrSync_updateProviderDataByAtTxnIdAndProviderId";
	public static final String UPDATE_BY_MESSAGE_ID_AND_PROVIDER_ID = "CdrSync_updateProviderDataByMessageIdAndProviderId";
	
	public static final String UPDATE_STATUS_BY_AT_TXN_ID_AND_PROVIDER_ID = "CdrSync_updateStatusByAtTxnIdAndProviderId";
	public static final String UPDATE_STATUS_BY_MESSAGE_ID_AND_PROVIDER_ID = "CdrSync_updateStatusByMessageIdAndProviderId";
	
	public static final String SEARCH_BY_FILTER = "CdrSync_SearchByFilter";
	public static final String SEARCH_BY_FILTER_COUNT = "CdrSync_SearchByFilterCount";
	
	public static final String INSERT = "CdrSync_insert";
	
	
    private String providerId;

    private Date txnDate;

    private String msisdn;

    private Integer amount;

    private String errorCode;

    private String status;

    private Integer pPrice;

    private String pStatus;

    private String pErrorCode;

    private String ext;

    private Long atTxnId;

    private String messageId;

    private String pTxnId;

    public Long getAtTxnId() {
        return atTxnId;
    }

    public void setAtTxnId(Long atTxnId) {
        this.atTxnId = atTxnId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public String getpTxnId() {
        return pTxnId;
    }

    public void setpTxnId(String pTxnId) {
        this.pTxnId = pTxnId == null ? null : pTxnId.trim();
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

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getpPrice() {
        return pPrice;
    }

    public void setpPrice(Integer pPrice) {
        this.pPrice = pPrice;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus == null ? null : pStatus.trim();
    }

    public String getpErrorCode() {
        return pErrorCode;
    }

    public void setpErrorCode(String pErrorCode) {
        this.pErrorCode = pErrorCode == null ? null : pErrorCode.trim();
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

}