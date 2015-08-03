package com.mbv.airline.sabre.commands.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

public class FlightSegment {	
	private String carrierCode;
	private String flightCode;
	private String departureCode;
	private String arrivalCode;
	private DateTime departureTime;
	private DateTime arrivalTime;		
	private Map<String, Integer> classes;

	public FlightSegment(){
		this.classes = new HashMap<String, Integer>();
	}
	
	public FlightSegment(FlightSegment segment){
		this.carrierCode = segment.carrierCode;
		this.flightCode = segment.flightCode;
		this.departureCode = segment.departureCode;
		this.arrivalCode = segment.arrivalCode;
		this.departureTime = segment.departureTime;
		this.arrivalTime = segment.arrivalTime;
		this.classes = new HashMap<String, Integer>();
	}
	
	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getDepartureCode() {
		return departureCode;
	}

	public void setDepartureCode(String departureCode) {
		this.departureCode = departureCode;
	}

	public String getArrivalCode() {
		return arrivalCode;
	}

	public void setArrivalCode(String arrivalCode) {
		this.arrivalCode = arrivalCode;
	}

	public DateTime getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(DateTime departureTime) {
		this.departureTime = departureTime;
	}

	public DateTime getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(DateTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Map<String, Integer> getClasses() {
		return classes;
	}

	public void setClasses(Map<String, Integer> classes) {
		this.classes = classes;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.carrierCode + " ");
		sb.append(this.flightCode + " ");
		sb.append(this.departureCode + "/");
		sb.append(this.arrivalCode + " ");
		sb.append(this.departureTime.toString("yyyy-MM-dd HH:mm") + " / ");
		sb.append(this.arrivalTime.toString("yyyy-MM-dd HH:mm") + " ");
		for(Entry<String, Integer> cl : this.classes.entrySet()){
			sb.append(cl.getKey() + "/" + cl.getValue() + " ");
		}
		return sb.toString();
	}
}
