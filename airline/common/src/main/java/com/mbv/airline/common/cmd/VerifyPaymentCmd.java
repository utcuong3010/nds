package com.mbv.airline.common.cmd;

import java.util.Date;

public class VerifyPaymentCmd {

	private String id;

	private String reservationCode;
	private Date expiredDate;

	public VerifyPaymentCmd() {

	}

	public VerifyPaymentCmd(String id, String reservationCode, Date expiredDate) {
		this.id = id;
		this.reservationCode = reservationCode;
		this.expiredDate = expiredDate;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	@Override
	public String toString() {
		return "VerifyPaymentCommand [id=" + id + ", reservationCode=" + reservationCode + ", expiredDate="
				+ expiredDate + "]";
	}

}
