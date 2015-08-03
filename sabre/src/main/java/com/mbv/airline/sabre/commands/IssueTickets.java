package com.mbv.airline.sabre.commands;

import java.util.HashSet;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PNR.Pax;
import com.mbv.airline.sabre.commands.result.PNR.PriceQuote;
import com.mbv.airline.sabre.commands.result.PNR.QuoteSegment;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class IssueTickets extends BaseCommand {

	private String reservationCode;

	public IssueTickets(String reservationCode) {
		this.reservationCode = reservationCode == null ? "" : reservationCode
				.trim();
	}

	@Override
	public PNR execute(Terminal terminal) {
		PNR pnr = new PNR();

		logger.info("Start Issue Tickets VNA");

		try {
			// Check terminal
			if (terminal == null) {
				pnr = new PNR();
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("No Terminal");
				return pnr;
			}
			// Check reservation code
			if ("".equals(this.reservationCode)) {
				pnr = new PNR();
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("No Reservation Code");
				return pnr;
			}
			// Check printer
			Result checkPrinter = (new DesignatePrinters()).execute(terminal);
			if (checkPrinter.getStatus() != Code.SUCCESS) {
				pnr = new PNR();
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("No Printer");
				return pnr;
			}
			// Check PNR
			Command cmd = new RetrievePNR(this.reservationCode);
			pnr = (PNR) cmd.execute(terminal);

			if (pnr.getStatus() != Code.SUCCESS) {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Cannot Retrieve PNR");
				return pnr;
			}

			// check
			int numADT = 0;
			int numCNN = 0;
			int numINF = 0;
			for (Pax pax : pnr.getPaxs()) {
				if ("ADT".equals(pax.getType())) {
					numADT++;
				} else if ("CHD".equals(pax.getType())) {
					numCNN++;
				} else if ("INF".equals(pax.getType())) {
					numINF++;
				}
			}

			int numQuotes = 0;
			HashSet<String> ADTSegments = new HashSet<String>();
			HashSet<String> CNNSegments = new HashSet<String>();
			HashSet<String> INFSegments = new HashSet<String>();

			for (PriceQuote quote : pnr.getPriceQuotes()) {
				numQuotes++;
				int paxCount = -1;
				HashSet<String> set = null;

				if ("ADT".equals(quote.getPaxType())) {
					paxCount = numADT;
					set = ADTSegments;
				} else if ("CNN".equals(quote.getPaxType())) {
					paxCount = numCNN;
					set = CNNSegments;
				} else if ("INF".equals(quote.getPaxType())) {
					paxCount = numINF;
					set = INFSegments;
				}

				if (set != null) {
					for (QuoteSegment pSegment : quote.getSegments()) {
						if (set.contains(pSegment.toString())) {
							pnr.setStatus(Code.ERROR);
							pnr.setDescription("Price Quote Error");
							return pnr;
						} else {
							set.add(pSegment.toString());
						}
					}
				}

				if (paxCount != quote.getCount() || set == null) {
					pnr.setStatus(Code.ERROR);
					pnr.setDescription("Price Quote Error");
					return pnr;
				}
			}

			int numSegments = pnr.getSegments().size();
			if (ADTSegments.size() != numSegments) {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Price Quote Error ADT");
				return pnr;
			}
			if (numCNN > 0 && CNNSegments.size() != numSegments) {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Price Quote Error CNN");
				return pnr;
			}
			if (numINF > 0 && INFSegments.size() != numSegments) {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Price Quote Error INF");
				return pnr;
			}

			// issue ticket
			DateTime expirationDate = DateTimeFormat.forPattern("MMyyyy")
					.parseDateTime("12" + DateTime.now().getYear());
			String stationNumber = terminal.getConfig().getStationNumber();
			for (int i = 1; i <= numQuotes; i++) {
				this.send("W'VCR'NP'F*BT87382105" + stationNumber + "/"
						+ expirationDate.toString("MMyy") + "'PQ" + i
						+ "'EDNONENDS", terminal);
				this.receive(terminal);
			}

			cmd = new EndAndRetrievePNR();
			pnr = (PNR) cmd.execute(terminal);
			if (pnr.getStatus() != Code.SUCCESS) {
				pnr.setStatus(Code.UNKNOWN);
				pnr.setDescription("IssueTickets Error: "
						+ pnr.getDescription());
			}

		} catch (TerminalException e) {
			logger.error("Issue Tickets Error", e);
			pnr.setStatus(Code.UNKNOWN);
			pnr.setDescription("Send Command IssueTickets Error");
			return pnr;
		}
		return pnr;
	}

}
