package com.mbv.airline.sabre;

public class TerminalConfig {
	private String username;	
	private String password;
	private String airlineCode;
	private String pseudoCityCode;
	private String hardcopyPrinter;
	private String ticketPrinter;
	private String printRoutine;
	private String stationNumber;
	
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
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getPseudoCityCode() {
		return pseudoCityCode;
	}
	public void setPseudoCityCode(String pseudoCityCode) {
		this.pseudoCityCode = pseudoCityCode;
	}
	public String getHardcopyPrinter() {
		return hardcopyPrinter;
	}
	public void setHardcopyPrinter(String hardcopyPrinter) {
		this.hardcopyPrinter = hardcopyPrinter;
	}
	public String getTicketPrinter() {
		return ticketPrinter;
	}
	public void setTicketPrinter(String ticketPrinter) {
		this.ticketPrinter = ticketPrinter;
	}
	public String getPrintRoutine() {
		return printRoutine;
	}
	public void setPrintRoutine(String printRoutine) {
		this.printRoutine = printRoutine;
	}
	public String getStationNumber() {
		return stationNumber;
	}
	public void setStationNumber(String stationNumber) {
		this.stationNumber = stationNumber;
	}	
}
