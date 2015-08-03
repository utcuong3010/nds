package com.mbv.airline.common.info;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.mbv.airline.common.BookingStatus;

public class AirItinerary extends AirBookingInfo {
	
	private String id;
	private AirTicketingInfo ticketingInfo;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private void generateId() {
		this.setId(UUID.randomUUID().toString().replace("-", ""));
	}

	/***
	 * create booking info
	 * @param bookingInfo
	 * @return
	 */
	public static AirItinerary create(AirBookingInfo bookingInfo) {
		AirItinerary itinerary = new AirItinerary();
		itinerary.setAgentInfo(bookingInfo.getAgentInfo());
		itinerary.setFareInfos(bookingInfo.getFareInfos());
		itinerary.setPassengerInfos(bookingInfo.getPassengerInfos());
		itinerary.setContactInfo(bookingInfo.getContactInfo());
		itinerary.setExtraServices(bookingInfo.getExtraServices());
		itinerary.generateId();
		AirTicketingInfo ticketingInfo = new AirTicketingInfo();
		ticketingInfo.setCreatedDate(new Date());
		ticketingInfo.setStatus(BookingStatus.BOOK_PENDING);
		itinerary.setTicketingInfo(ticketingInfo);
		return itinerary;
	}
	
	private static String toHashString(AirItinerary bookingInfo){
		StringBuilder tmp = new StringBuilder();								
		tmp.append(bookingInfo.getTicketingInfo().getReservationCode()).append(",").append(bookingInfo.getFareInfos().get(0).getVendor());
		HashFunction hashFunc = Hashing.md5();
		return hashFunc.newHasher().putBytes(tmp.toString().getBytes()).hash().toString();
	}

	public static AirItinerary create(AirItinerary bookingInfo) {
		String id = toHashString(bookingInfo);
		bookingInfo.setId(id); 	
		bookingInfo.getTicketingInfo().setCreatedDate(new Date());
		bookingInfo.getTicketingInfo().setUpdatedDate(new Date());
		bookingInfo.getTicketingInfo().setExpiredDate(new Date());
		return bookingInfo;
	}

	public static AirItinerary create(String bookingId, String verdor)
			throws ParseException {
		AirItinerary itinerary = new AirItinerary();

		itinerary.generateId();

		AgentInfo agentInfo = new AgentInfo();
		agentInfo.setAgentId("mca");
		agentInfo.setUserId("admin-mca");
		agentInfo.setAddress("104 Mai Thi Luu, Q1");
		agentInfo.setEmail("support@mobivi.com");
		agentInfo.setMobile("0965292512");
		agentInfo.setCity("TP Ho Chi Minh");

		AirTicketingInfo ticketingInfo = new AirTicketingInfo();
		ticketingInfo.setCreatedDate(new Date());
		ticketingInfo.setStatus(BookingStatus.BOOK_PENDING);
		ticketingInfo.setReservationCode(bookingId);

		List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();
		AirFareInfo fareInfo = new AirFareInfo();
		fareInfo.setVendor(verdor);
		fareInfos.add(fareInfo);

		itinerary.setAgentInfo(agentInfo);
		itinerary.setFareInfos(fareInfos);
		itinerary.setTicketingInfo(ticketingInfo);
		return itinerary;
	}

	public AirTicketingInfo getTicketingInfo() {
		return ticketingInfo;
	}

	public void setTicketingInfo(AirTicketingInfo ticketingInfo) {
		this.ticketingInfo = ticketingInfo;
	}

	@Override
	public String toString() {
		return "AirItinerary [id=" + id + ", ticketingInfo=" + ticketingInfo
				+ ", agentInfo=" + agentInfo + ", fareInfos=" + fareInfos
				+ ", passengerInfos=" + passengerInfos + ", contactInfo="
				+ contactInfo + ", extraServices=" + extraServices + "]";
	}

}
