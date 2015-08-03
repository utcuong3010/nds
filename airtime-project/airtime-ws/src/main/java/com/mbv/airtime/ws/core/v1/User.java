package com.mbv.airtime.ws.core.v1;

import com.mbv.airtime.ws.DataObject;

public class User extends DataObject {
	
	
	private String userId;
	private String username;
	private String fullName;
	
	
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
	

}
