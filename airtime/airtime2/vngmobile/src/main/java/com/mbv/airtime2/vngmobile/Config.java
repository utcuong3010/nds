package com.mbv.airtime2.vngmobile;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class Config {
	private String serviceId;	
	
	private String host;
	private int port;
	
	private String evourcherLogin;
	private String evourcherPassword;
	private String evourcherMpin;
	private String evourcherMsisdn;
	private String evourcherServiceCode;
	
	private String PgLogin;
    private String PgPassword;
	private String PgTokenKey;
	
	
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getEvourcherLogin() {
		return evourcherLogin;
	}
	public void setEvourcherLogin(String evourcherLogin) {
		this.evourcherLogin = evourcherLogin;
	}
	public String getEvourcherPassword() {
		return evourcherPassword;
	}
	public void setEvourcherPassword(String evourcherPassword) {
		this.evourcherPassword = Base64.encodeBase64String(DigestUtils.sha1(evourcherPassword));
	}
	public String getEvourcherMpin() {
		return evourcherMpin;
	}
	public void setEvourcherMpin(String evourcherMpin) {
		this.evourcherMpin = evourcherMpin;
	}
	public String getEvourcherMsisdn() {
		return evourcherMsisdn;
	}
	public void setEvourcherMsisdn(String evourcherMsisdn) {
		this.evourcherMsisdn = evourcherMsisdn;
	}
	public String getPgLogin() {
		return PgLogin;
	}
	public void setPgLogin(String pgLogin) {
		PgLogin = pgLogin;
	}
	public String getPgPassword() {
		return PgPassword;
	}
	public void setPgPassword(String pgPassword) {
		PgPassword = pgPassword;
	}
	public String getPgTokenKey() {
		return PgTokenKey;
	}
	public void setPgTokenKey(String pgTokenKey) {
		PgTokenKey = pgTokenKey;
	}
	public String getEvourcherServiceCode() {
		return evourcherServiceCode;
	}
	public void setEvourcherServiceCode(String evourcherServiceCode) {
		this.evourcherServiceCode = evourcherServiceCode;
	}

}
