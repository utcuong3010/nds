package com.mbv.airtime2.mobifone.domain;

import com.google.common.base.Objects;

public class BalanceResponse extends Response{
	private final String mobiTxnId;	
	private final long availableAmount;
	private final long currentAmount;
	private final long pendingAmount;
	
	public BalanceResponse(
			String status,
			String errorCode,
			String mobiTxnId,
			long availableAmount,
			long currentAmount,
			long pendingAmount){
		super(status, errorCode);
		this.mobiTxnId = mobiTxnId;
		this.availableAmount = availableAmount;
		this.currentAmount = currentAmount;
		this.pendingAmount = pendingAmount;
	}

	public String getMobiTxnId() {
		return mobiTxnId;
	}

	public long getAvailableAmount() {
		return availableAmount;
	}

	public long getCurrentAmount() {
		return currentAmount;
	}

	public long getPendingAmount() {
		return pendingAmount;
	}	
	
	public String toString(){
		return Objects.toStringHelper(this)
	       .add("status", this.status)
	       .add("errorCode", this.errorCode)
	       .add("mobiTxnId", this.mobiTxnId)
	       .add("availableAmount", this.availableAmount)
	       .add("currentAmount", this.currentAmount)
	       .add("pendingAmount", this.pendingAmount)
	       .toString();
	}
}
