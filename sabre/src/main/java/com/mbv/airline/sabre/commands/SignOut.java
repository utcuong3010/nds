package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class SignOut extends BaseCommand {

	public Result execute(Terminal terminal) {
		try {
			logger.info("Send command sign out!!!");
			this.send("SO*", terminal);
			String resp = this.receive(terminal);
			logger.info("SIGN OUT RESPONSE: " + resp);
			return new Result();
		} catch (TerminalException e) {
			logger.error("SIGN OUT ERROR: ", e);
			return new Result(Code.TERMINAL_ERROR, "SIGN OUT ERROR: "
					+ e.getMessage());
		}
	}
}
