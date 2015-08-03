package com.mbv.hotel.common;

public class AgodaResult {
	
	private String reservationCode;
	private String description;
	
	public String getReservationCode() {
		return reservationCode;
	}
	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AgodaResult [reservationCode=" + reservationCode
				+ ", description=" + description + "]";
	}
	
	
	

}
