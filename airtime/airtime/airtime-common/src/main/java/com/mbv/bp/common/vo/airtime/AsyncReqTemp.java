package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class AsyncReqTemp {
	public static final String SELECT="AsyncReqTemp_Select";
	public static final String INSERT="AsyncReqTemp_Insert";
	public static final String UPDATE="AsyncReqTemp_Update";
	public static final String SELECT_BY_OPERATION_TYPE="AsyncReqTemp_SelectByOperationType";
	public static final String SEARCH_BY_FILTER="AsyncReqTemp_SearchByFilter";
	public static final String SEARCH_BY_FILTER_COUNT="AsyncReqTemp_SearchByFilterCount";
	
	
    private Long atTxnId;

    private String operatorType;

    private String value;

    private String status;

    private Date txnDate;

    private String errorCode;

    public Long getAtTxnId() {
        return atTxnId;
    }

    public void setAtTxnId(Long atTxnId) {
        this.atTxnId = atTxnId;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType == null ? null : operatorType.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
}