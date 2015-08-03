package com.mbv.airtime;

public class AirtimeConfiguration {

	private String host;
	private String port;
	
	
	public AirtimeConfiguration(String host, String port) {
		this.host = host;
		this.port = port;
	}
	
	public void print() {
		
		System.err.println("host=" + host + ":port=" +  port);
	}
}
