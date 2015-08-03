package com.mbv.hotel.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mbv.hotel.domain.HotelBooking;

@Repository
public interface BookingReposity extends CrudRepository<HotelBooking, String>{

}
