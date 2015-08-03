package com.mbv.airtime2.vngmobile.domain;

import com.google.common.base.Objects;

public class TopupResponse {
	public static final String STATUS_SUCCESS = "SUCCESS";
	public static final String STATUS_ERROR = "ERROR";
	public static final String ERROR_NONE = "";
	public static final String ERROR_DELIVERY = "DELIVERY_RESPONSE_ERROR";
	public static final String ERROR_CONNECTION = "CONNECTION_FAILED";
	
	
	protected final String status;
	protected final String errorCode;
	private final String atTxnId;	
	private final Long balance;
	
	public Long getBalance() {
		return balance;
	}

	public String getStatus() {
		return status;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public boolean isSuccess(){
		return STATUS_SUCCESS.equals(this.status);
	}
	
	public String getAtTxnId() {
		return atTxnId;
	}

	public TopupResponse(String atTxnId, String status, String errorCode, Long balance){
		this.atTxnId = atTxnId;
		this.status = status;
		this.errorCode = errorCode;
		this.balance = balance;
	}
	
	public String toString(){
		return Objects.toStringHelper(this)
	       .add("status", status)
	       .add("errorCode", errorCode)
	       .add("atTxnId", atTxnId)
	       .add("balance", balance)	       
	       .toString();
	}
}
