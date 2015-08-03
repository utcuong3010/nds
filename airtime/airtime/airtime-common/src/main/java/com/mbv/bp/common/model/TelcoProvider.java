package com.mbv.bp.common.model;

import java.util.Arrays;

public class TelcoProvider {
	String providerId;
	String providerName;
	String group;
	String[] connectionIds;
	String msisdnPrefix;
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getMsisdnPrefix() {
		return msisdnPrefix;
	}
	public void setMsisdnPrefix(String msisdnPrefix) {
		this.msisdnPrefix = msisdnPrefix;
	}
	
	public String[] getConnectionIds() {
		return connectionIds;
	}
	public void setConnectionIds(String[] connectionIds) {
		this.connectionIds = connectionIds;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}
