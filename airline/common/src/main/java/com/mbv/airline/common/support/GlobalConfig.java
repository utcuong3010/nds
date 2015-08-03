package com.mbv.airline.common.support;

import com.mbv.airline.common.info.EmailInfo;


public class GlobalConfig {
	private int timeCheckSession;
	public EmailInfo emailInfo;
	
	public int getTimeCheckSession() {
		return timeCheckSession;
	}
	public void setTimeCheckSession(int timeCheckSession) {
		this.timeCheckSession = timeCheckSession;
	}
	public EmailInfo getEmailInfo() {
		return emailInfo;
	}
	public void setEmailInfo(EmailInfo emailInfo) {
		this.emailInfo = emailInfo;
	}
}
