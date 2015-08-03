package com.mbv.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mbv.hotel.form.CreateBookingForm;
import com.mbv.hotel.info.HotelBookingInfo;
import com.mbv.hotel.repos.HotelRepository;


@Service
public class HotelServiceImpl implements HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;

	@Override
	public HotelBookingInfo createBooking(CreateBookingForm createForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HotelBookingInfo getBooking(String bookingId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long countHotel() {
		// TODO Auto-generated method stub
		return hotelRepository.count();
	}
	

}
