package com.mbv.airline.actors.vietjet;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class VietJetConfig {
	
	@Value("${airline.vietjet.workers}" )
	private Integer numWorkers ;
	
	@Value("${airline.vietjet.worker.name}")
	private String workerName;
	
	
	@Value("${airline.vietjet.username}")
    private String username;
	
	@Value("${airline.vietjet.password}")
    private String password;
	
	@Value("${airline.vietjet.book.reservationCode}")
    private String reservationCode;


	@Value("${airline.vietjet.session.expire.minus}")
	private int sessionExpire;
	
	@Value("${airline.vietjet.login.url}")
	private String loginUrl;
	
	
	@Value("${airline.vietjet.search.url}")
	private String searchUrl;
	
	
	@Value("${airline.vietjet.pay.url}")
	private String payUrl;
	
	@Value("${airline.vietjet.book.testing}")
	private boolean isBook4Testing;
	
	
	@Value("${airline.vietjet.pay.testing}")
	private boolean isPay4Testing;
	
	@Value("${airline.vietjet.session.expire.urls}")
	private String[] sessionExpiredUrls;
	
	@Value("${airline.vietjet.action.retry}")
	private int actionRetry;
	

	public Integer getNumWorkers() {
		return numWorkers;
	}

	public String getWorkerName() {
		return workerName;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public int getSessionExpire() {
		return sessionExpire;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public boolean isBook4Testing() {
		return isBook4Testing;
	}

	public boolean isPay4Testing() {
		return isPay4Testing;
	}

	public String[] getSessionExpiredUrls() {
		return sessionExpiredUrls;
	}

	public int getActionRetry() {
		return actionRetry;
	}

	

	public void setNumWorkers(Integer numWorkers) {
		this.numWorkers = numWorkers;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public void setSessionExpire(int sessionExpire) {
		this.sessionExpire = sessionExpire;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public void setSearchUrl(String searchUrl) {
		this.searchUrl = searchUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public void setBook4Testing(boolean isBook4Testing) {
		this.isBook4Testing = isBook4Testing;
	}

	public void setPay4Testing(boolean isPay4Testing) {
		this.isPay4Testing = isPay4Testing;
	}

	public void setSessionExpiredUrls(String[] sessionExpiredUrls) {
		this.sessionExpiredUrls = sessionExpiredUrls;
	}

	public void setActionRetry(int actionRetry) {
		this.actionRetry = actionRetry;
	}


	@Override
	public String toString() {
		return "VietJetConfig [numWorkers=" + numWorkers + ", workerName="
				+ workerName + ", username=" + username + ""
				+ ", reservationCode=" + reservationCode
				+ ", sessionExpire=" + sessionExpire + ", loginUrl=" + loginUrl
				+ ", searchUrl=" + searchUrl + ", payUrl=" + payUrl
				+ ", isBook4Testing=" + isBook4Testing + ", isPay4Testing="
				+ isPay4Testing + ", sessionExpiredUrls="
				+ Arrays.toString(sessionExpiredUrls) + ", actionRetry="
				+ actionRetry + "]";
	}
}
