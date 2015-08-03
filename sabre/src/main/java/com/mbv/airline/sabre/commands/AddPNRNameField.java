package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;

public class AddPNRNameField extends BaseCommand {

	// -<Family name>/<Given name or Initial> <Title>

	private String cmd;

	public AddPNRNameField(String firstName, String lastName, String title) {
		this.cmd = "-" + firstName + "/" + lastName + " " + title;
	}

	public Result execute(Terminal terminal) {
		try {
            logger.info("Send command Add PNR Name Field!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal);
            logger.info("AddPNRNameField RESPONSE: " +  resp);
			return new Result(Result.Code.SUCCESS);
		} catch (TerminalException e) {
			logger.error("AddPNRNameField Error", e);
			return new Result(Result.Code.TERMINAL_ERROR,
					"AddPNRNameField Error");
		}
	}

}
