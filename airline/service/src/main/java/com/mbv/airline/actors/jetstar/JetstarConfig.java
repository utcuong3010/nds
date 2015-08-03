package com.mbv.airline.actors.jetstar;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JetstarConfig {
	
	
	@Value("${airline.jetstar.workers}" )
	private Integer numWorkers ;
	
	@Value("${airline.jetstar.worker.name}")
	private String workerName;
	
	
	@Value("${airline.jetstar.username}")
    private String username;
	
	@Value("${airline.jetstar.password}")
    private String password;

	@Value("${airline.jetstar.base.url}")
    private String baseUrl;
	
	@Value("${airline.jetstar.session.expire.minus}")
	private int sessionExpire;
	
	@Value("${airline.jetstar.book.testing}")
	private boolean isBook4Testing;
	
	@Value("${airline.jetstar.pay.testing}")
	private boolean isPay4Testing;
	
	@Value("${airline.jetstar.book.reservationCode}")
	private String reservationCode;

	public Integer getNumWorkers() {
		return numWorkers;
	}

	public void setNumWorkers(Integer numWorkers) {
		this.numWorkers = numWorkers;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public int getSessionExpire() {
		return sessionExpire;
	}

	public void setSessionExpire(int sessionExpire) {
		this.sessionExpire = sessionExpire;
	}

	public boolean isBook4Testing() {
		return isBook4Testing;
	}

	public void setBook4Testing(boolean isBook4Testing) {
		this.isBook4Testing = isBook4Testing;
	}

	public boolean isPay4Testing() {
		return isPay4Testing;
	}

	public void setPay4Testing(boolean isPay4Testing) {
		this.isPay4Testing = isPay4Testing;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	@Override
	public String toString() {
		return "JetstarConfig [numWorkers=" + numWorkers + ", workerName="
				+ workerName + ", username=" + username + ", password="
				+ password + ", baseUrl=" + baseUrl + ", sessionExpire="
				+ sessionExpire + ", isBook4Testing=" + isBook4Testing
				+ ", isPay4Testing=" + isPay4Testing + ", reservationCode="
				+ reservationCode + "]";
	}
	
}
