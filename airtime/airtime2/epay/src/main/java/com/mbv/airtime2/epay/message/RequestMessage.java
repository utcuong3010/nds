package com.mbv.airtime2.epay.message;

public class RequestMessage {
	private final String atTxnId;
	private final String telcoId;
	private final String account;
	private final long amount;

	
	public RequestMessage (String atTxnId, String telcoId,String account, long amount){
		this.atTxnId = atTxnId;
		this.telcoId = telcoId;
		this.account = account;
		this.amount = amount;
	}


	public String getAtTxnId() {
		return atTxnId;
	}

	public String getTelcoId() {
		return telcoId;
	}


	public String getAccount() {
		return account;
	}

	public long getAmount() {
		return amount;
	}

	
}
