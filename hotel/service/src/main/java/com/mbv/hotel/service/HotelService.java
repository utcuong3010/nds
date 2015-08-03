package com.mbv.hotel.service;

import com.mbv.hotel.form.CreateBookingForm;
import com.mbv.hotel.info.HotelBookingInfo;

public interface HotelService {
	HotelBookingInfo createBooking(CreateBookingForm createForm);
	HotelBookingInfo getBooking(String bookingId);
	long countHotel();
	

}

