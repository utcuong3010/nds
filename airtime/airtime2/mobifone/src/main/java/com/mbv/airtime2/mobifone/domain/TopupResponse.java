package com.mbv.airtime2.mobifone.domain;

import com.google.common.base.Objects;

public class TopupResponse extends Response {
	public static final String ERROR_NONE = "";
	public static final String ERROR_DELIVERY = "DELIVERY_RESPONSE_ERROR";
	public static final String ERROR_CONNECTION = "CONNECTION_FAILED";
	
	private final String atTxnId;	
	private final String refTxnId;
	
	public String getAtTxnId() {
		return atTxnId;
	}

	public String getRefTxnId() {
		return refTxnId;
	}

	public TopupResponse(String atTxnId, String status, String errorCode, String refTxnId){
		super(status, errorCode);
		this.atTxnId = atTxnId;		
		this.refTxnId = refTxnId;
	}
	
	public String toString(){
		return Objects.toStringHelper(this)
	       .add("status", status)
	       .add("errorCode", errorCode)
	       .add("atTxnId", atTxnId)
	       .add("mobiTxnId", refTxnId)	       
	       .toString();
	}

	
}
