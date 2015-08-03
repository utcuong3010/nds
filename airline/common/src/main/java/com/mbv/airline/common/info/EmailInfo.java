package com.mbv.airline.common.info;

public class EmailInfo {	
	private String systemId;
	private String appName;
	private String fromMailAddress;
	private String toMailAddress;
	private String queue;
	private String ipAdress;
	
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getFromMailAddress() {
		return fromMailAddress;
	}
	public void setFromMailAddress(String fromMailAddress) {
		this.fromMailAddress = fromMailAddress;
	}
	public String getToMailAddress() {
		return toMailAddress;
	}
	public void setToMailAddress(String toMailAddress) {
		this.toMailAddress = toMailAddress;
	}
	public String getQueue() {
		return queue;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public String getIpAdress() {
		return ipAdress;
	}
	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}
	@Override
	public String toString() {
		return "EmailInfo [systemId=" + systemId + ", appName=" + appName
				+ ", fromMailAddress=" + fromMailAddress + ", toMailAddress="
				+ toMailAddress + ", queue=" + queue + ", ipAdress=" + ipAdress
				+ "]";
	}
}
