package com.mbv.hotel.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mbv.hotel.domain.Hotel;

@Repository
public interface HotelRepository extends CrudRepository<Hotel, String>{

}
