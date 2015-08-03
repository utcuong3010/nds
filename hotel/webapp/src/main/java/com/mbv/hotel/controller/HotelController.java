package com.mbv.hotel.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mbv.hotel.service.HotelService;


@RestController
@RequestMapping("/hotel")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	
	/***
	 * logger 
	 */
	final Logger logger = Logger.getLogger(HotelController.class);


	@RequestMapping(value = "test", method = RequestMethod.GET)
	public String Test() throws Exception {
		return "Maka Faka";
	}
	
	
	
	
	
}














