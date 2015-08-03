package com.mbv.airline.sabre.commands.result;

import java.util.ArrayList;

import org.joda.time.DateTime;

public class PNR extends Result {
	// GEN000
	private DateTime dateCreate;// 001H
	private DateTime dateUpdate;
	private String agentId; // 00WB
	private String received; // 01PR
	private String reservationCode;
	private int numIti; // 00DW

	private String phone;
	private String remark;
	private Long baseFare;
	private Long taxAmount;
	private Long totalAmount;

	private ArrayList<Segment> segments;
	private ArrayList<Pax> paxs;
	private ArrayList<PriceQuote> priceQuotes;

	private ArrayList<Fact> facts;
	private ArrayList<TicketInfo> tickets;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public DateTime getDateCreate() {
		return dateCreate;
	}

	public DateTime getDateUpdate() {
		return dateUpdate;
	}

	public void setDateCreate(DateTime date) {
		dateCreate = date;
	}

	public void setDateUpdate(DateTime date) {
		dateUpdate = date;
	}

	public int getNumIti() {
		return numIti;
	}

	public void setNumIti(int numIti) {
		this.numIti = numIti;
	}

	public ArrayList<Fact> getFacts() {
		return facts;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public ArrayList<Pax> getPaxs() {
		return paxs;
	}

	public void setPaxs(ArrayList<Pax> paxs) {
		this.paxs = paxs;
	}

	public Long getBaseFare() {
		return baseFare;
	}

	public void setBaseFare(Long baseFare) {
		this.baseFare = baseFare;
	}

	public Long getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public ArrayList<TicketInfo> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<TicketInfo> tickets) {
		this.tickets = tickets;
	}

	/*
	 * 
	 */
	public PNR() {
		this.segments = new ArrayList<Segment>();
		this.paxs = new ArrayList<Pax>();
		this.facts = new ArrayList<Fact>();
		this.priceQuotes = new ArrayList<PriceQuote>();
		this.tickets = new ArrayList<TicketInfo>();
	}

	/*
	 * 
	 */
	public void addPax(Pax pax) {
		this.paxs.add(pax);
	}

	/*
	 * 
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}

	/*
	 * 
	 */
	public boolean addSegment(Segment segment) {
		if (segment.getSegmentNumber() == null
				|| this.getSegment(segment.getSegmentNumber()) != null)
			return false;
		this.segments.add(segment);
		return true;
	}

	/*
	 * 
	 */
	public void updateSegment(Segment newSegment) {
		int index = 0;
		for (Segment segment : this.segments) {
			if (newSegment.getSegmentNumber().equalsIgnoreCase(
					segment.getSegmentNumber()))
				break;
			index++;
		}
		if (index < this.segments.size())
			this.segments.set(index, newSegment);
		else
			this.segments.add(newSegment);
	}

	/*
	 * 
	 */
	public Segment getSegment(String segmentNumber) {
		for (Segment segment : this.segments) {
			if (segment.getSegmentNumber().equalsIgnoreCase(segmentNumber))
				return segment;
		}
		return null;
	}

	public ArrayList<PriceQuote> getPriceQuotes() {
		return priceQuotes;
	}

	public void setPriceQuotes(ArrayList<PriceQuote> priceQuotes) {
		this.priceQuotes = priceQuotes;
	}

	/*
	 * 
	 */
	public void addPriceQuote(PriceQuote pq) {
		this.priceQuotes.add(pq);
	}

	public void addFact(Fact fact) {
		this.facts.add(fact);
	}

	public void addTicket(TicketInfo ticket) {
		this.tickets.add(ticket);
	}

	public ArrayList<String> getTicketNumbers() {
		int numTickets = 0;
		for (PriceQuote quote : this.getPriceQuotes()) {
			numTickets += quote.getCount();
		}

		ArrayList<String> ticketNumbers = new ArrayList<String>();
		for (PNR.TicketInfo ticket : this.getTickets()) {
			if (ticket.getItem() != null && ticket.getItem().contains("TE ")) {
				String[] item = ticket.getItem().split(" ");
				ticketNumbers.add(item[1]);
			}
		}

		if (ticketNumbers.size() == numTickets && numTickets > 0)
			return ticketNumbers;

		logger.error("ticketNumbers mismatch " + ticketNumbers.size() + " : "
				+ numTickets);

		return new ArrayList<String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getStatus() + "\t" + this.getDescription() + "\n");
		sb.append("Reservation Code: " + this.reservationCode + "\n");
		sb.append("Name:\n");
		for (Pax pax : this.paxs) {
			sb.append(pax.toString() + "\n");
		}

		sb.append("Itinerary:\n");
		for (Segment segment : this.segments) {
			sb.append(segment.toString() + "\n");
		}

		sb.append("Fact:\n");
		for (Fact fact : this.facts) {
			sb.append(fact.toString() + "\n");
		}

		sb.append("Phone: " + this.phone + "\n");
		sb.append("Received: " + this.received + "\n");
		sb.append("Remark: " + this.remark + "\n");

		sb.append("Price Quote: " + this.baseFare + " " + this.taxAmount + " "
				+ this.totalAmount + "\n");
		for (PriceQuote pq : this.priceQuotes) {
			sb.append(pq.toString());
		}

		sb.append("Ticket Info:\n");
		for (TicketInfo ticket : this.tickets) {
			sb.append(ticket.toString() + "\n");
		}

		return sb.toString();
	}

	/**
	 * 
	 * @author hieu
	 * 
	 */
	public static class Segment {
		// AIR000Â†0Â†0001Â†SSÂ†01Â†VNÂ†MÂ†0233Â†00Â†Â†NÂ†1000Â†1200Â†Â†EÂ†
		// Â†Â†Â†Â†01MARÂ†01MARÂ†Â†Â†Â†Â†Â†Â†Â†Â†Â†Â†Â†Â‰
		// AIR000Â†0Â†0002Â†SSÂ†01Â†VNÂ†MÂ†0232Â†00Â†Â†NÂ†0720Â†0920Â†Â†EÂ†
		// Â†Â†Â†Â†03MARÂ†03MARÂ†Â†Â†Â†Â†Â†Â†Â†Â†Â†Â†Â†Â‰
		private String segmentNumber; // 001N
		private String statusCode; // 00SJ
		private int numberOfSeats; // 0182
		private String airlineDesignator; // 000B
		private String classOfService; // 007V
		private String flightNumber; // 000I
		private DateTime departureDate;
		private DateTime arrivalDate;
		private String from;
		private String to;

		public Segment(String segmentNumber) {
			this.segmentNumber = segmentNumber;
		}

		public String getSegmentNumber() {
			return segmentNumber;
		}

		public void setSegmentNumber(String segmentNumber) {
			this.segmentNumber = segmentNumber;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public int getNumberOfSeats() {
			return numberOfSeats;
		}

		public void setNumberOfSeats(int numberOfSeats) {
			this.numberOfSeats = numberOfSeats;
		}

		public String getAirlineDesignator() {
			return airlineDesignator;
		}

		public void setAirlineDesignator(String airlineDesignator) {
			this.airlineDesignator = airlineDesignator;
		}

		public String getClassOfService() {
			return classOfService;
		}

		public void setClassOfService(String classOfService) {
			this.classOfService = classOfService;
		}

		public String getFlightNumber() {
			return flightNumber;
		}

		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}

		public DateTime getDepartureDate() {
			return departureDate;
		}

		public void setDepartureDate(DateTime departureDate) {
			this.departureDate = departureDate;
		}

		public DateTime getArrivalDate() {
			return arrivalDate;
		}

		public void setArrivalDate(DateTime arrivalDate) {
			this.arrivalDate = arrivalDate;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public String toString() {
			/*
			 * private String segmentNumber; // 001N private String statusCode;
			 * // 00SJ private String numberOfSeats; // 0182 private String
			 * airlineDesignator; // 000B private String classOfService; // 007V
			 * private String flightNumber; // 000I private DateTime
			 * departureDate; private String from; private String to;
			 */
			StringBuilder sb = new StringBuilder();
			sb.append(this.segmentNumber + " ");
			sb.append(this.from + "/" + this.to + " ");
			sb.append(this.airlineDesignator + " " + this.flightNumber + " "
					+ this.classOfService + " "
					+ this.departureDate.toString("ddMMM") + " ");
			sb.append(this.statusCode + " " + this.numberOfSeats);
			return sb.toString();
		}
	}

	/*
	 * PAX000Â†0Â†01.01Â†Â†Â†NGUYENÂ†MINH TUAN MRÂ†Â†Â†Â†Â†1Â†Â†ÂˆÂˆÂ‰
	 * PAX000Â†0Â†02.01Â†Â†IÂ†NGUYENÂ†QUANG HUY MSTRÂ†Â†Â†Â†Â†1Â†Â†ÂˆÂˆÂ‰
	 */
	public static class Pax { //
		private String nameNumber; // 00TF
		private String infantIndicator; // 00LZ
		private String lastName; // 000N
		private String firstName; // 000O
		private String gender;
		private String type;

		public String getNameNumber() {
			return nameNumber;
		}

		public void setNameNumber(String nameNumber) {
			this.nameNumber = nameNumber;
		}

		public String getInfantIndicator() {
			return infantIndicator;
		}

		public void setInfantIndicator(String infantIndicator) {
			this.infantIndicator = infantIndicator;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.nameNumber + " ");
			sb.append(this.infantIndicator == null ? "  "
					: this.infantIndicator + " ");
			sb.append(this.lastName + "/" + this.firstName + " " + type + " "
					+ gender);
			return sb.toString();
		}
	}

	/**
	 * 
	 * @author hieu
	 * 
	 */
	public static class Fact {
		private String itemNumber;
		private String indicator;
		private String request;
		private String text;

		public String getItemNumber() {
			return itemNumber;
		}

		public void setItemNumber(String itemNumber) {
			this.itemNumber = itemNumber;
		}

		public String getIndicator() {
			return indicator;
		}

		public void setIndicator(String indicator) {
			this.indicator = indicator;
		}

		public String getRequest() {
			return request;
		}

		public void setRequest(String request) {
			this.request = request;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.itemNumber + " " + this.indicator + " "
					+ this.request + " " + this.text);
			return sb.toString();
		}
	}

	/*
	 * 
	 */
	public static class PriceQuote {
		private String paxType;
		private int count;
		private Long baseFare;
		private Long taxAmount;
		private Long totalAmount;
		private ArrayList<QuoteSegment> segments;

		public ArrayList<QuoteSegment> getSegments() {
			return segments;
		}

		public void setSegments(ArrayList<QuoteSegment> segments) {
			this.segments = segments;
		}

		public PriceQuote() {
			segments = new ArrayList<PNR.QuoteSegment>();
		}

		public String getPaxType() {
			return paxType;
		}

		public void setPaxType(String paxType) {
			this.paxType = paxType;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		public Long getBaseFare() {
			return baseFare;
		}

		public void setBaseFare(Long baseFare) {
			this.baseFare = baseFare;
		}

		public Long getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(Long taxAmount) {
			this.taxAmount = taxAmount;
		}

		public Long getTotalAmount() {
			return totalAmount;
		}

		public void setTotalAmount(Long totalAmount) {
			this.totalAmount = totalAmount;
		}

		public void addSegment(QuoteSegment segment) {
			this.segments.add(segment);
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.paxType + "/" + this.count + " " + this.baseFare
					+ " " + this.taxAmount + " " + this.totalAmount + "\n");
			for (QuoteSegment segment : this.segments) {
				sb.append("\t" + segment.toString());
			}
			return sb.toString();
		}
	}

	public static class QuoteSegment {
		private String segmentNumber;
		private String from;
		private String airlineDesignator;
		private String flightCode;
		private DateTime departureDate;
		private String serviceClass;
		private String fareBasisCode;
		private String statusCode;
		private String baggageAllowance;

		public String getSegmentNumber() {
			return segmentNumber;
		}

		public void setSegmentNumber(String segmentNumber) {
			this.segmentNumber = segmentNumber;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getAirlineDesignator() {
			return airlineDesignator;
		}

		public void setAirlineDesignator(String airlineDesignator) {
			this.airlineDesignator = airlineDesignator;
		}

		public String getFlightCode() {
			return flightCode;
		}

		public void setFlightCode(String flightCode) {
			this.flightCode = flightCode;
		}

		public DateTime getDepartureDate() {
			return departureDate;
		}

		public void setDepartureDate(DateTime departureDate) {
			this.departureDate = departureDate;
		}

		public String getServiceClass() {
			return serviceClass;
		}

		public void setServiceClass(String serviceClass) {
			this.serviceClass = serviceClass;
		}

		public String getFareBasisCode() {
			return fareBasisCode;
		}

		public void setFareBasisCode(String fareBasisCode) {
			this.fareBasisCode = fareBasisCode;
		}

		public String getStatusCode() {
			return statusCode;
		}

		public void setStatusCode(String statusCode) {
			this.statusCode = statusCode;
		}

		public String getBaggageAllowance() {
			return baggageAllowance;
		}

		public void setBaggageAllowance(String baggageAllowance) {
			this.baggageAllowance = baggageAllowance;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.from + " " + this.airlineDesignator + " "
					+ this.flightCode + " " + this.serviceClass + "/"
					+ this.fareBasisCode + " ");
			sb.append(this.departureDate.toString("ddMMMM") + " "
					+ this.statusCode + "\n");
			return sb.toString();
		}
	}

	public static class TicketInfo {
		private String number;
		private String timeLimit;
		private String item;

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getTimeLimit() {
			return timeLimit;
		}

		public void setTimeLimit(String timeLimit) {
			this.timeLimit = timeLimit;
		}

		public String getItem() {
			return item;
		}

		public void setItem(String item) {
			this.item = item;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.number + " " + this.timeLimit + " " + this.item);
			return sb.toString();
		}
	}
}