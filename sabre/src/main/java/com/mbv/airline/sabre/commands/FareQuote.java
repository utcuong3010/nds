package com.mbv.airline.sabre.commands;

import org.joda.time.DateTime;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.commands.result.FareQuoteResult;
import com.mbv.airline.sabre.commands.result.QuoteInfo;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.sabre.gds.GDSDecoder;
import com.sabre.gds.GDSElemValue;
import com.sabre.gds.GDSError;
import com.sabre.gds.GDSSegValue;

public class FareQuote extends BaseCommand {

	// +++A++QIK00001^FQ07+FQHANSGN15FEB¥R15FEB¥FL
	// REQ/LOADSDSPRCEFARETAX01

	private String cityPair;
	private DateTime date;
	private String paxType = "A";

	public FareQuote(String cityPair, DateTime date) {
		this(cityPair, date, false, false);
	}

	public FareQuote(String cityPair, DateTime date, boolean includeChd,
			boolean includeInf) {
		this.cityPair = cityPair;
		this.date = date;
		if (includeChd)
			this.paxType = this.paxType + "C";
		if (includeInf)
			this.paxType = this.paxType + "I";
	}

	public FareQuoteResult execute(Terminal terminal) {
		FareQuoteResult result;
		String command = "+++A++QIK00001^FQ07+FQ" + cityPair
				+ date.toString("ddMMM") + "," + paxType + "'FL";

		try {
			this.send(command, terminal);
			String tmp = this.receive(terminal);
			result = parse(tmp);
		} catch (Exception e) {
			logger.error("FareQuote", e.getMessage());
			result = new FareQuoteResult();
			result.setStatus(Code.TERMINAL_ERROR);
		}
		return result;
	}

	public FareQuoteResult parse(String data) {
		FareQuoteResult retval = new FareQuoteResult();

		GDSDecoder decoder = new GDSDecoder();
		int code = decoder.allocMsg(data);
		if (code == GDSError.GDS_OK) {
			GDSSegValue gdsSeg;
			GDSElemValue gdsElem;
			QuoteInfo quote = null;

			while ((gdsSeg = decoder.getNextSeg()) != null) {
				if ("FQFBR0".equals(gdsSeg.id)) {
					quote = new QuoteInfo();
					while ((gdsElem = decoder.getNextElem()) != null) {
						if ("00Z1".equals(gdsElem.id)) {
							quote.setFareBasisCode(gdsElem.value);
						} else if ("00UG".equals(gdsElem.id)) {
							quote.setPaxType(gdsElem.value);
						}
					}
				} else if ("FQDATA".equals(gdsSeg.id) && quote != null) {
					while ((gdsElem = decoder.getNextElem()) != null) {
						if ("01DH".equals(gdsElem.id)) {
							// private String carrier;
							quote.setCarrier(gdsElem.value);
						} else if ("0142".equals(gdsElem.id)) {
							// private String bookingCode;
							quote.setBookingCode(gdsElem.value);
						} else if ("04C1".equals(gdsElem.id)) {
							// private long onewayTaxAmount;
							long amount = 0;
							try {
								amount = Long.parseLong(gdsElem.value);
							} catch (NumberFormatException ex) {

							}
							quote.setOnewayTaxAmount(amount);
						} else if ("04C2".equals(gdsElem.id)) {
							// private long onewayFareAmount;
							long amount = 0;
							try {
								amount = Long.parseLong(gdsElem.value);
							} catch (NumberFormatException ex) {

							}
							quote.setOnewayFareAmount(amount);
						} else if ("04C3".equals(gdsElem.id)) {
							// private long rountripTaxAmount;
							long amount = 0;
							try {
								amount = Long.parseLong(gdsElem.value);
							} catch (NumberFormatException ex) {

							}
							quote.setRountripTaxAmount(amount);
						} else if ("04C4".equals(gdsElem.id)) {
							// private long roundtripFareAmount;
							long amount = 0;
							try {
								amount = Long.parseLong(gdsElem.value);
							} catch (NumberFormatException ex) {

							}
							quote.setRoundtripFareAmount(amount);
						}
					}
					retval.addQuote(quote);
					quote = null;
				}
			}

		} else {
			logger.error("Alloc msg error: " + GDSError.getErrorMsg(code));
		}
		return retval;
	}

}
