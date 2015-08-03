package com.mbv.hotel.info;

import java.util.ArrayList;
import java.util.List;

public class HotelBookingInfo {
	
	
	private String bookingId;
	private String checkIn;
	private String checkOut;
	private double total;
	private int quantity;
	private int extrabeds;
	private String hotelId;
	private String roomId;
	private int occupancy;
	private String cityName;
	private List<GuestInfo> guests = new ArrayList();
	
	/**
	 * @return the bookingId
	 */
	public String getBookingId() {
		return bookingId;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	/**
	 * @return the checkIn
	 */
	public String getCheckIn() {
		return checkIn;
	}
	/**
	 * @param checkIn the checkIn to set
	 */
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	/**
	 * @return the checkOut
	 */
	public String getCheckOut() {
		return checkOut;
	}
	/**
	 * @param checkOut the checkOut to set
	 */
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the extrabeds
	 */
	public int getExtrabeds() {
		return extrabeds;
	}
	/**
	 * @param extrabeds the extrabeds to set
	 */
	public void setExtrabeds(int extrabeds) {
		this.extrabeds = extrabeds;
	}
	/**
	 * @return the hotelId
	 */
	public String getHotelId() {
		return hotelId;
	}
	/**
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	/**
	 * @return the roomId
	 */
	public String getRoomId() {
		return roomId;
	}
	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	/**
	 * @return the occupancy
	 */
	public int getOccupancy() {
		return occupancy;
	}
	/**
	 * @param occupancy the occupancy to set
	 */
	
	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}
	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	/**
	 * @return the guests
	 */
	public List<GuestInfo> getGuests() {
		return guests;
	}
	/**
	 * @param guests the guests to set
	 */
	public void setGuests(List<GuestInfo> guests) {
		this.guests = guests;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HotelBookingInfo [bookingId=" + bookingId + ", checkIn="
				+ checkIn + ", checkOut=" + checkOut + ", total=" + total
				+ ", quantity=" + quantity + ", extrabeds=" + extrabeds
				+ ", hotelId=" + hotelId + ", roomId=" + roomId
				+ ", occupancy=" + occupancy + ", cityName=" + cityName
				+ ", guests=" + guests + "]";
	}
	/**
	 * @param occupancy the occupancy to set
	 */
	public void setOccupancy(int occupancy) {
		this.occupancy = occupancy;
	}
	
	

}
