package com.mbv.airline.sabre.commands.result;

public class QuoteInfo {
	private String carrier;	
	private String paxType;
	private String fareBasisCode;
	private String bookingCode;
	private long onewayTaxAmount;
	private long onewayFareAmount;
	private long rountripTaxAmount;
	private long roundtripFareAmount;
	
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	
	public String getPaxType() {
		return paxType;
	}
	public void setPaxType(String paxType) {
		this.paxType = paxType;
	}
	public String getFareBasisCode() {
		return fareBasisCode;
	}
	public void setFareBasisCode(String fareBasisCode) {
		this.fareBasisCode = fareBasisCode;
	}
	public String getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}
	public long getOnewayTaxAmount() {
		return onewayTaxAmount;
	}
	public void setOnewayTaxAmount(long onewayTaxAmount) {
		this.onewayTaxAmount = onewayTaxAmount;
	}
	public long getOnewayFareAmount() {
		return onewayFareAmount;
	}
	public void setOnewayFareAmount(long onewayFareAmount) {
		this.onewayFareAmount = onewayFareAmount;
	}
	public long getRountripTaxAmount() {
		return rountripTaxAmount;
	}
	public void setRountripTaxAmount(long rountripTaxAmount) {
		this.rountripTaxAmount = rountripTaxAmount;
	}
	public long getRoundtripFareAmount() {
		return roundtripFareAmount;
	}
	public void setRoundtripFareAmount(long roundtripFareAmount) {
		this.roundtripFareAmount = roundtripFareAmount;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(carrier + " ");
		sb.append(paxType + " ");
		sb.append(bookingCode + "/" + fareBasisCode + " ");
		sb.append(onewayTaxAmount + "/" + onewayFareAmount + " ");
		sb.append(rountripTaxAmount + "/" + roundtripFareAmount);
		return sb.toString();
	}
}
