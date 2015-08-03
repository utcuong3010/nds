package com.mbv.hotel.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mbv.hotel.domain.Country;

@Repository
public interface CountryRepository extends CrudRepository<Country,String>{

}
