package com.mbv.airline.common.info;

import java.util.Date;

public class TravelDateInfo {
	protected Date departureDate;
	protected Date arrivalDate;

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Override
	public String toString() {
		return "TravelDateInfo [departureDate=" + departureDate
				+ ", arrivalDate=" + arrivalDate + "]";
	}

}
