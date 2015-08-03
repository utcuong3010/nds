package com.mbv.airline.common.info;

import java.util.Date;

import com.mbv.airline.common.BookingStatus;

/**
 * Created by phuongvt on 3/30/15.
 */
public class ReportPayItinerary {
	private String id;
	private String vendor;
	private boolean isChecked;
	private int retryCounter;
	private boolean isWorking;
	private Date dateCreated;
	private Date dateUpdated;
	private BookingStatus status;
	private String reservationCode;
	private String description;

	public static ReportPayItinerary create(AirItinerary itinerary) {
		ReportPayItinerary payItinerary = new ReportPayItinerary();
		payItinerary.setId(itinerary.getId());
		payItinerary.setVendor(itinerary.getFareInfos().get(0).getVendor());
		payItinerary.setStatus(itinerary.getTicketingInfo().getStatus());
		payItinerary.setReservationCode(itinerary.getTicketingInfo()
				.getReservationCode());
		payItinerary.setRetryCounter(0);
		payItinerary.setChecked(false);
		payItinerary.setStatusWorking(false);
		payItinerary.setDescription("");
		return payItinerary;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setRetryCounter(int retryCounter) {
		this.retryCounter = retryCounter;
	}

	public void increaseRetryCounter() {
		this.retryCounter++;
	}

	public int getRetryCounter() {
		return retryCounter;
	}

	public void setChecked(boolean checked) {
		this.isChecked = checked;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setStatusWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVendor() {
		return vendor;
	}

	public void setDateUpdated(Date date) {
		this.dateUpdated = date;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateCreated(Date date) {
		this.dateCreated = date;
	}

	public Date getDateCreated() {
		return dateCreated;
	}
}
