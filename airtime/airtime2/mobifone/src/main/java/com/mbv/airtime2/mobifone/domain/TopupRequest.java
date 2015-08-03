package com.mbv.airtime2.mobifone.domain;

import com.google.common.base.Objects;

public class TopupRequest {

	private final String atTxnId;
	private final String msisdn;
	private final long amount;
	
	public TopupRequest(String atTxnId, String msisdn, long amount){
		this.atTxnId = atTxnId;
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
	
	public String toString(){
		return Objects.toStringHelper(this)
				.add("atTxnId", atTxnId)
				.add("msisdn", msisdn)
				.add("amount", amount).toString();
	}
}
