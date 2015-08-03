package com.mbv.airline.sabre.commands.result;

import java.util.ArrayList;
import java.util.Iterator;

public class AvailabilityResult extends Result{

	private String flightFrom;
	
	private String flightTo;
	
	private ArrayList<FlightSegment> segments;
	
	public String getFlightFrom() {
		return flightFrom;
	}

	public void setFlightFrom(String flightFrom) {
		this.flightFrom = flightFrom;
	}

	public String getFlightTo() {
		return flightTo;
	}

	public void setFlightTo(String flightTo) {
		this.flightTo = flightTo;
	}

	public AvailabilityResult(String from, String to){
		super();
		this.flightFrom = from;
		this.flightTo = to;
		this.segments = new ArrayList<FlightSegment>();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		Iterator<FlightSegment> iterator = this.segments.iterator();
		while(iterator.hasNext()){
			sb.append(iterator.next().toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public Iterator<FlightSegment> iterator(){
		return this.segments.iterator();
	}
	
	public void add(FlightSegment segment){
		this.segments.add(segment);
	}
	
	public int size(){
		return this.segments.size();
	}
}
