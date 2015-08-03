package com.mbv.airline.sabre.commands;

import java.util.Date;

import com.mbv.airline.sabre.commands.result.Result;
import org.joda.time.DateTime;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;

public class AddPNRInfant extends BaseCommand {

	// [4INFT1/NGUYEN/QUANG HUY MSTR/15MAR13-1.1Σ4INFT2/NGUYEN/QUANG HUY
	// MSTR/15MAR13-1.1]

	private String cmd;

	public AddPNRInfant(String firstName, String lastName, String title,
			Date dob, String flightRef, String adtRef) {
		this.cmd = "4INFT" + flightRef + "/" + firstName + "/" + lastName + " "
				+ title + "/" + new DateTime(dob).toString("ddMMMyy") + "-"
				+ adtRef;
	}

	public Result execute(Terminal terminal) {
		try {
			logger.info("Send command Add PNR Infant!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal);
			logger.info("AddPNRInfant RESPONSE: " + resp);
			return new Result(Result.Code.SUCCESS);
		} catch (TerminalException e) {
			logger.error("AddPNRInfant Error", e);
			return new Result(Result.Code.TERMINAL_ERROR, "AddPNRInfant Error");
		}
	}
}
