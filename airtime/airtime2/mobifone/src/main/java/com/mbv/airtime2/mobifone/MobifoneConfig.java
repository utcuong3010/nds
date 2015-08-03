package com.mbv.airtime2.mobifone;

import java.util.HashSet;
import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class MobifoneConfig {
	public final int RESULT_OK = 0;
	private final int RESULT_SESSION_TIMEOUT = 9;
	private final int RESULT_INVALID_SESSION = 10;
	public final String TARGET_PREPAID = "airtime";
	public final String TARGET_POSTPAID = "postpaid";
	public final int BUY_TYPE = 2;
	public final int BALANCE_TYPE = 2;
	
	
	private String url = "";
	private String login = "";
	private String password = "";
	private int queueWorkers = 1;
	private int queueFactor = 1;
	private HashSet<Long> prepaidAmounts = new HashSet<Long>();
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getQueueWorkers() {
		return queueWorkers;
	}

	public void setQueueWorkers(int queueWorkers) {
		this.queueWorkers = queueWorkers;
	}

	public int getQueueFactor() {
		return queueFactor;
	}

	public void setQueueFactor(int queueFactor) {
		this.queueFactor = queueFactor;
	}

	
	public void setPrepaidAmounts(String prepaidAmounts) {		
		List<String> amounts = Lists.newArrayList(Splitter.on(";").trimResults().omitEmptyStrings().split(prepaidAmounts));
		for(String amount : amounts){
			try{
				this.prepaidAmounts.add(Long.parseLong(amount));
			}catch(Exception ex){				
			}
		}
	}

	public boolean isPrepaidAmount(long amount){
		return (prepaidAmounts.isEmpty() || prepaidAmounts.contains(amount));		
	}
	
	public boolean isSessionError(int result){
		return (result == RESULT_SESSION_TIMEOUT || result == RESULT_INVALID_SESSION);
	}	
}
