package com.mbv.airline.common.support;

import java.util.Date;
import java.util.List;

import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;

public interface AirItineraryRepository {

    public void add(AirItinerary itinerary) throws Exception;
    
    public void delete(AirItinerary itinerary) throws Exception;

    public AirItinerary findById(String id);

    public void update(AirItinerary itinerary) throws Exception;
    
    public void updateBookingInfo(AirItinerary itinerary) throws Exception;

    public List<AirItinerary> findByFare(AirFareInfo fareInfo);
    
    public List<AirItinerary> findByStatus(String status);
    
    public AirItinerary findByReservationCode(AirTicketingInfo ticketingInfo);
    
    public List<AirItinerary> findByDate(Date dateStart,Date dateEnd) throws Exception;
}
