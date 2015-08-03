package com.mbv.airtime2.vngmobile.domain;

public class BalanceInfo {
	private final String serviceId;
	private final long balance;
	
	public BalanceInfo(String serviceId, long balance){
		this.serviceId = serviceId;
		this.balance = balance;
	}
	
	public long getBalance() {
		return balance;
	}
	public String getProviderId() {
		return serviceId;
	}
}
