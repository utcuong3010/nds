package com.mbv.airline.common.report;

import java.util.Date;

import com.mbv.airline.common.info.AirBookingInfo;

public class AirTicketReport {
	private String id;
	private String reservationCode;
	private String provider;
	private String status;
	private Date checkedDate;
	private String result;
	private long amount;
	private String content;

	public AirTicketReport() {
	}

	public AirTicketReport(String ID, String reservationCode, String provider, long amount,
			AirBookingInfo airBookingInfo) {
		this.setId(ID);
		this.setReservationCode(reservationCode);
		this.setProvider(provider);
		this.amount = amount;
		this.content = airBookingInfo.toString();
		this.status = "checking";
		this.checkedDate = new Date();
		this.result = null;
	}

	public Date getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(AirBookingInfo airBookingInfo) {
		this.content = airBookingInfo.toString();
	}

	@Override
	public String toString() {
		return "AirTicketReport [id=" + id + ", reservationCode=" + reservationCode + ", provider=" + provider
				+ ", status=" + status + ", checkedDate=" + checkedDate + ", result=" + result + ", amount=" + amount
				+ "]";
	}
}
