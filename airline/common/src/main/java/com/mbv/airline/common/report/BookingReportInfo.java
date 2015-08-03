package com.mbv.airline.common.report;

import java.util.Date;

import com.mbv.airline.common.BookingStatus;

public class BookingReportInfo {
	
	private String vendor;
	
	private String reservationCode;
	
	private long money;
	
	private BookingStatus bookingStatus;
	
	private Date createDate;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "BookingReportInfo [vendor=" + vendor + ", reservationCode="
				+ reservationCode + ", money=" + money + ", bookingStatus="
				+ bookingStatus + ", createDate=" + createDate + "]";
	}
}
