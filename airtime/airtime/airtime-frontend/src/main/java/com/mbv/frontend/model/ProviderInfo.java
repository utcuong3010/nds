package com.mbv.frontend.model;

public class ProviderInfo {
	private String providerId;
	private String currentAmount;
	private String lockAmount;
	private String availAmount;
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	public String getLockAmount() {
		return lockAmount;
	}
	public void setLockAmount(String lockAmount) {
		this.lockAmount = lockAmount;
	}
	public String getAvailAmount() {
		return availAmount;
	}
	public void setAvailAmount(String availAmount) {
		this.availAmount = availAmount;
	}

}
