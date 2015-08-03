package com.mbv.hotel.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mbv.hotel.domain.City;

@Repository
public interface CityRepository extends CrudRepository<City, String>{

}
