package com.mbv.airline.common.info;

import java.util.Date;
import java.util.List;

import com.mbv.airline.common.BookingStatus;

public class AirTicketingInfo {
    private Date createdDate;
    private Date updatedDate;
    private Date expiredDate;
    private BookingStatus status;
    private String description;
    private String reservationCode;
    private String refContent;
    private long amount;
    private List<String> ticketNumbers;
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReservationCode() {
		return reservationCode;
	}
	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}
	public String getRefContent() {
		return refContent;
	}
	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public List<String> getTicketNumbers() {
		return ticketNumbers;
	}
	public void setTicketNumbers(List<String> ticketNumbers) {
		this.ticketNumbers = ticketNumbers;
	}
	@Override
	public String toString() {
		return "AirTicketingInfo [createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", expiredDate="
				+ expiredDate + ", status=" + status + ", description="
				+ description + ", reservationCode=" + reservationCode
				+ ", refContent=" + refContent + ", amount=" + amount
				+ ", ticketNumbers=" + ticketNumbers + "]";
	}
}
