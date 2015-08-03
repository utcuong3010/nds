package com.mbv.airtime2.xpay.domain;


public class TopupRequest {
	

	private final String reqtype;
	private final String atTxnId;
	private final String product_type;
	private final String msisdn;
	private final long amount;
	
	public TopupRequest(String reqtype,String atTxnId,String product_type, String msisdn, long amount){
		this.reqtype = reqtype;
		this.atTxnId = atTxnId;
		this.product_type= product_type;
		this.msisdn = msisdn;
		this.amount = amount;
	}

	public String getAtTxnId() {
		return atTxnId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public long getAmount() {
		return amount;
	}

	public String getProduct_type() {
		return product_type;
	}

	public String getReqtype() {
		return reqtype;
	}

	@Override
	public String toString() {
		return "TopupRequest [reqtype=" + reqtype + ", atTxnId=" + atTxnId
				+ ", product_type=" + product_type + ", msisdn=" + msisdn
				+ ", amount=" + amount + "]";
	}
	
}
