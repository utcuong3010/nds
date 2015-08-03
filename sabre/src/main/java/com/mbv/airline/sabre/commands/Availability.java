package com.mbv.airline.sabre.commands;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import com.mbv.airline.sabre.commands.result.AvailabilityResult;
import com.mbv.airline.sabre.commands.result.FlightSegment;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.sabre.gds.GDSDecoder;
import com.sabre.gds.GDSElemValue;
import com.sabre.gds.GDSError;
import com.sabre.gds.GDSSegValue;

public class Availability extends BaseCommand {
	private String from;
	private String to;
	private DateTime departureDate;

	public Availability(String from, String to, DateTime departureDate) {
		this.from = from.toUpperCase();
		this.to = to.toUpperCase();
		this.departureDate = departureDate;
	}

	public DateTime getDepartureDate() {
		return departureDate;
	}

	// +++A++QIK00001^AV06+115FEBHANSGN
	public AvailabilityResult execute(Terminal terminal) {
		AvailabilityResult retval = new AvailabilityResult(this.from, this.to);

		String command = "+++A++QIK00001^AV06+1"
				+ departureDate.toString("ddMMM") + this.from + this.to;
		boolean end;
		HashSet<String> segmentSet = new HashSet<String>();
		try {
			do {
				String data;
				try {
					this.send(command, terminal);
					data = this.receive(terminal);
				} catch (TerminalException e) {
					retval.setStatus(Code.TERMINAL_ERROR);
					retval.setDescription("Availability error: "
							+ e.getMessage());
					logger.error("Availability Error: " + e.getMessage());
					return retval;
				}

				AvailabilityResult partialResult = this.parse(data);

				end = false;
				Iterator<FlightSegment> iterator = partialResult.iterator();
				while (iterator.hasNext() && !end) {
					FlightSegment segment = iterator.next();

					String code = segment.getCarrierCode()
							+ segment.getFlightCode();
					if (segmentSet.contains(code)) {
						end = true;
					} else {
						segmentSet.add(code);
						retval.add(segment);
					}
				}
				end = end || (partialResult.size() == 0)
						|| (data.indexOf("NO MORE") != -1);
				if (!end)
					command = "+++A++QIK00001^AV06+1*";
			} while (!end);
		} catch (Exception e) {
			logger.error("Availability error: " + e);
		}
		return retval;
	}

	private AvailabilityResult parse(String data) {
		AvailabilityResult retval = new AvailabilityResult(this.from, this.to);
		GDSDecoder decoder = new GDSDecoder();
		int code = decoder.allocMsg(data);
		if (code != GDSError.GDS_OK) {
			logger.error("Alloc msg error: " + GDSError.getErrorMsg(code));
			retval.setStatus(Code.ERROR);
			retval.setDescription("Alloc msg error: "
					+ GDSError.getErrorMsg(code));
			return retval;
		}

		GDSSegValue gdsSeg;
		GDSElemValue gdsElem;

		String dateString = "";
		DateTimeFormatter formatter = DateTimeFormat
				.forPattern("ddMMMyyyyHHmm");
		DateTime tmpDatetime;
		while ((gdsSeg = decoder.getNextSeg()) != null) {
			// System.out.println(" Seg Id: " + segV.id);
			if ("AVHDR0".equals(gdsSeg.id)) {
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("012U".equals(gdsElem.id)) {
						dateString = gdsElem.value + DateTime.now().getYear();
					} else if ("0145".equals(gdsElem.id)) {
						// retval.setFrom(gdsElem.value);
					} else if ("018B".equals(gdsElem.id)) {
						// retval.setTo(gdsElem.value);
					}
				}
			} else if ("AVLIN0".equals(gdsSeg.id)) {
				FlightSegment segment = new FlightSegment();
				LinkedList<String> classCodes = new LinkedList<String>();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00B1".equals(gdsElem.id)) {
						// segment.setLineNumber(gdsElem.value);
					} else if ("012G".equals(gdsElem.id)) {
						segment.setCarrierCode(gdsElem.value);
					} else if ("016P".equals(gdsElem.id)) {
						segment.setFlightCode(gdsElem.value);
					} else if ("0145".equals(gdsElem.id)) {
						segment.setDepartureCode(gdsElem.value);
					} else if ("018B".equals(gdsElem.id)) {
						segment.setArrivalCode(gdsElem.value);
					} else if ("01L9".equals(gdsElem.id)) {
						tmpDatetime = formatter.parseDateTime(dateString
								+ gdsElem.value);
						if (tmpDatetime.isBeforeNow())
							tmpDatetime = tmpDatetime.plusYears(1);
						segment.setDepartureTime(tmpDatetime);
					} else if ("01MK".equals(gdsElem.id)) {
						tmpDatetime = formatter.parseDateTime(dateString
								+ gdsElem.value);
						if (tmpDatetime.isBefore(segment.getDepartureTime()))
							tmpDatetime = tmpDatetime.plusYears(1);
						segment.setArrivalTime(tmpDatetime);
					} else if ("007V".equals(gdsElem.id)) {
						classCodes.add(gdsElem.value);
					} else if ("01ZT".equals(gdsElem.id)) {
						int seatCount = 0;
						try {
							seatCount = Integer.parseInt(gdsElem.value);
						} catch (NumberFormatException ex) {

						}
						String seatClass = classCodes.pop();
						if (seatCount > 0 && seatClass != null)
							segment.getClasses().put(seatClass, seatCount);
					}
				}
				retval.add(segment);
			}
		}
		return retval;
	}

	public String toString() {
		return this.from + "/" + this.to + " "
				+ this.departureDate.toString("yyyyMMdd");
	}
}
