package com.mbv.airline.sabre.commands;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PNR.Fact;
import com.mbv.airline.sabre.commands.result.PNR.Pax;
import com.mbv.airline.sabre.commands.result.PNR.PriceQuote;
import com.mbv.airline.sabre.commands.result.PNR.QuoteSegment;
import com.mbv.airline.sabre.commands.result.PNR.Segment;
import com.mbv.airline.sabre.commands.result.PNR.TicketInfo;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.sabre.gds.GDSDecoder;
import com.sabre.gds.GDSElemValue;
import com.sabre.gds.GDSError;
import com.sabre.gds.GDSSegValue;

public class RetrievePNR extends BaseCommand {

	private String reservationCode;

	public RetrievePNR() {

	}

	public RetrievePNR(String reservationCode) {
		this.reservationCode = reservationCode;
	}

	public PNR execute(Terminal terminal) {
		String command;
		String resp;

		if (this.reservationCode != null) {
			command = "I";
			try {
				this.send(command, terminal);
				resp = this.receive(terminal); // IGD

				command = "*" + this.reservationCode;
				this.send(command, terminal);
				resp = this.receive(terminal);
			} catch (Exception e) {
				logger.error("RetrievePNR Terminal Error", e.getMessage());
			}
		}
		command = "+++A++QIK00001^PN37+JX PNR";
		try {
			this.send(command, terminal);
			resp = this.receive(terminal);
			return parse(resp);
		} catch (Exception e) {
			logger.error("Retrieve PNR Error", e.getMessage());
			PNR pnr = new PNR();
			pnr.setStatus(Code.TERMINAL_ERROR);
			pnr.setDescription("RetrievePNR booking error");
			return pnr;
		}

		// return null;
	}

	private PNR parse(String data) {
		PNR pnr = new PNR();
		GDSDecoder decoder = new GDSDecoder();

		int code = decoder.allocMsg(data);
		if (code != GDSError.GDS_OK) {
			logger.error("Alloc msg error: " + GDSError.getErrorMsg(code));
			pnr.setStatus(Code.ERROR);
			pnr.setDescription("Alloc msg error: " + GDSError.getErrorMsg(code));
			return pnr;
		}

		GDSSegValue gdsSeg;
		GDSElemValue gdsElem;

		PriceQuote priceQuote = null;

		while ((gdsSeg = decoder.getNextSeg()) != null) {
			// Get Received
			if ("GEN000".equals(gdsSeg.id)) {
				String dateCreate = "";
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("001H".equals(gdsElem.id)) {
						dateCreate = gdsElem.value;
					} else if ("00VI".equals(gdsElem.id)) {
						dateCreate += gdsElem.value;
					} else if ("001B".equals(gdsElem.id)) {
						pnr.setReservationCode(gdsElem.value);
					} else if ("00WB".equals(gdsElem.id)) {
						pnr.setAgentId(gdsElem.value);
					} else if ("01PR".equals(gdsElem.id)) {
						pnr.setReceived(gdsElem.value);
					} else if ("00DW".equals(gdsElem.id)) {
						String numIti = gdsElem.value;
						while (numIti.startsWith("0"))
							numIti = numIti.replaceFirst("0", "");
						if (!numIti.trim().isEmpty())
							pnr.setNumIti(Integer.parseInt(numIti));
					}
				}

				if (!dateCreate.trim().isEmpty())
					try {
						pnr.setDateCreate(DateTimeFormat.forPattern(
								"ddMMMyyHHmm").parseDateTime(dateCreate));
					} catch (Exception e) {
						logger.error("RetrievePNR Parse Date Create error "
								+ dateCreate, e);
					}
			}
			// Get Phone
			else if ("PHN000".equals(gdsSeg.id)) {
				String phone = "";

				while ((gdsElem = decoder.getNextElem()) != null) {
					phone += gdsElem.value + " ";
				}

				pnr.setPhone(phone.trim());
			}
			// Get Ticketing Info
			else if ("TRM000".equals(gdsSeg.id)) {
				TicketInfo ticket = new TicketInfo();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00TC".equals(gdsElem.id)) {
						ticket.setNumber(gdsElem.value);
					} else if ("01QB".equals(gdsElem.id)) {
						ticket.setTimeLimit(gdsElem.value);
					} else if ("01PQ".equals(gdsElem.id)) {
						ticket.setItem(gdsElem.value);
					}
				}

				pnr.addTicket(ticket);
			}
			// Get Remark
			else if ("HRM000".equals(gdsSeg.id)) {
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("0002".equals(gdsElem.id)) {
						pnr.setRemark(gdsElem.value);
					}
				}
			}
			// Get Name
			else if ("PAX000".equals(gdsSeg.id)) {
				Pax pax = new Pax();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00TF".equals(gdsElem.id)) {
						pax.setNameNumber(gdsElem.value);
					} else if ("00LZ".equals(gdsElem.id)) {
						pax.setInfantIndicator(gdsElem.value);
					} else if ("000N".equals(gdsElem.id)) {
						pax.setLastName(gdsElem.value);
					} else if ("000O".equals(gdsElem.id)) {
						String text = gdsElem.value;
						pax.setFirstName(text.substring(0,
								text.lastIndexOf(' ')).trim());

						String title = text.substring(text.lastIndexOf(' '))
								.trim();
						String gender = "";
						String type = "";

						if ("MR".equals(title)) {
							gender = "M";
							type = "ADT";
						} else if ("MS".equals(title)) {
							gender = "F";
							type = "ADT";
						} else if ("MSTR".equals(title)) {
							gender = "M";
							if ("I".equals(pax.getInfantIndicator())) {
								type = "INF";
							} else {
								type = "CHD";
							}
						} else if ("MISS".equals(title)) {
							gender = "F";
							if ("I".equals(pax.getInfantIndicator())) {
								type = "INF";
							} else {
								type = "CHD";
							}
						}

						pax.setGender(gender);
						pax.setType(type);
					}
				}

				pnr.addPax(pax);
			} else if ("AFX000".equals(gdsSeg.id)) {
				Fact fact = new Fact();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00VF".equals(gdsElem.id)) {
						fact.setItemNumber(gdsElem.value);
					} else if ("00Y0".equals(gdsElem.id)) {
						fact.setIndicator(gdsElem.value);
					} else if ("005M".equals(gdsElem.id)) {
						fact.setRequest(gdsElem.value);
					} else if ("038D".equals(gdsElem.id)) {
						fact.setText(gdsElem.value);
					}
				}
				pnr.addFact(fact);

			}
			// Get Itinerary
			else if ("AIR000".equals(gdsSeg.id)) {
				Segment segment = null;
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("001N".equals(gdsElem.id)) {
						segment = pnr.getSegment(gdsElem.value);
						if (segment == null)
							segment = new Segment(gdsElem.value);
					} else if ("00SJ".equals(gdsElem.id)) {
						segment.setStatusCode(gdsElem.value);
					} else if ("0182".equals(gdsElem.id)) {
						try {
							int num = Integer.parseInt(gdsElem.value);
							segment.setNumberOfSeats(num);
						} catch (Exception ex) {
							segment.setNumberOfSeats(0);
						}
					} else if ("000B".equals(gdsElem.id)) {
						segment.setAirlineDesignator(gdsElem.value);
					} else if ("007V".equals(gdsElem.id)) {
						segment.setClassOfService(gdsElem.value);
					} else if ("000I".equals(gdsElem.id)) {
						segment.setFlightNumber(gdsElem.value.replaceAll("^0+",
								""));
					}
				}
				pnr.addSegment(segment);
			} else if ("CPA000".equals(gdsSeg.id)) {
				Segment segment = null;
				String departureDate = "", arrivalDate = "";
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("001N".equals(gdsElem.id)) {
						segment = pnr.getSegment(gdsElem.value);
					} else if ("00G3".equals(gdsElem.id)) {
						segment.setFrom(gdsElem.value);
					} else if ("00GJ".equals(gdsElem.id)) {
						segment.setTo(gdsElem.value);
					} else if ("00CA".equals(gdsElem.id)) {
						departureDate = gdsElem.value
								+ DateTime.now().getYear();
					} else if ("007X".equals(gdsElem.id)) {
						departureDate += gdsElem.value;
					} else if ("00CB".equals(gdsElem.id)) {
						arrivalDate = gdsElem.value + DateTime.now().getYear();
					} else if ("007Z".equals(gdsElem.id)) {
						arrivalDate += gdsElem.value;
					}
				}

				if (!departureDate.trim().isEmpty()) {
					try {
						DateTime tmpDatetime = DateTimeFormat.forPattern(
								"ddMMMyyyyHHmm").parseDateTime(departureDate);
						segment.setDepartureDate(tmpDatetime);
					} catch (Exception ex) {
						logger.error("RetrievePNR Parse departure Date error "
								+ departureDate, ex);
					}
				}
				if (!arrivalDate.trim().isEmpty()) {
					try {
						DateTime tmpDatetime = DateTimeFormat.forPattern(
								"ddMMMyyyyHHmm").parseDateTime(arrivalDate);
						segment.setArrivalDate(tmpDatetime);
					} catch (Exception ex) {
						logger.error("RetrievePNR Parse arrival Date error "
								+ arrivalDate, ex);
					}
				}

				pnr.updateSegment(segment);
			} else if ("WSR003".equals(gdsSeg.id)) {
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("001P".equals(gdsElem.id)) {
						pnr.setBaseFare(Long.parseLong(gdsElem.value));
					} else if ("001Q".equals(gdsElem.id)) {
						pnr.setTaxAmount(Long.parseLong(gdsElem.value));
					} else if ("01NX".equals(gdsElem.id)) {
						pnr.setTotalAmount(Long.parseLong(gdsElem.value));
					}
				}
			} else if ("WSR002".equals(gdsSeg.id)) {
				priceQuote = new PriceQuote();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("01SO".equals(gdsElem.id)) {
						priceQuote.setCount(Integer.parseInt(gdsElem.value));
					} else if ("00UG".equals(gdsElem.id)) {
						priceQuote.setPaxType(gdsElem.value);
					} else if ("001P".equals(gdsElem.id)) {
						priceQuote.setBaseFare(Long.parseLong(gdsElem.value));
					} else if ("001Q".equals(gdsElem.id)) {
						priceQuote.setTaxAmount(Long.parseLong(gdsElem.value));
					} else if ("01NX".equals(gdsElem.id)) {
						priceQuote
								.setTotalAmount(Long.parseLong(gdsElem.value));
					}
				}
			} else if ("WSR005".equals(gdsSeg.id)) {
				QuoteSegment segment = new QuoteSegment();
				String departureDate = "";
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("001N".equals(gdsElem.id)) {
						segment.setSegmentNumber(gdsElem.value);
					} else if ("00G3".equals(gdsElem.id)) {
						segment.setFrom(gdsElem.value);
					} else if ("00EX".equals(gdsElem.id)) {
						segment.setAirlineDesignator(gdsElem.value);
					} else if ("000I".equals(gdsElem.id)) {
						segment.setFlightCode(gdsElem.value);
					} else if ("007V".equals(gdsElem.id)) {
						segment.setServiceClass(gdsElem.value);
					} else if ("01AL".equals(gdsElem.id)) {
						segment.setStatusCode(gdsElem.value);
					} else if ("00Z1".equals(gdsElem.id)) {
						segment.setFareBasisCode(gdsElem.value);
					} else if ("001Z".equals(gdsElem.id)) {
						segment.setBaggageAllowance(gdsElem.value);
					} else if ("00CA".equals(gdsElem.id)) {
						departureDate = gdsElem.value
								+ DateTime.now().getYear();
					} else if ("007X".equals(gdsElem.id)) {
						departureDate += gdsElem.value;
					}
				}

				if (!departureDate.trim().isEmpty()) {
					try {
						DateTime tmpDatetime = DateTimeFormat.forPattern(
								"ddMMMyyyyHHmm").parseDateTime(departureDate);
						if (tmpDatetime.isBeforeNow())
							tmpDatetime = tmpDatetime.plusYears(1);
						segment.setDepartureDate(tmpDatetime);
					} catch (Exception ex) {
						logger.error("RetrievePNR Parse departure Date error "
								+ departureDate, ex);
					}
				}

				if (segment.getSegmentNumber() != null) {
					priceQuote.addSegment(segment);
				} else {
					pnr.addPriceQuote(priceQuote);
				}
			}
		}

		return pnr;
	}

}
