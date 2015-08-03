package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;

public class EndAndRetrievePNR extends BaseCommand {

	@Override
	public Result execute(Terminal terminal) {
		try {
			logger.info("Send command End And Retrieve PNR!!!");
			this.send("ER", terminal);
			String resp = this.receive(terminal);
			logger.info("End And Retrieve PNR: " + resp);
			// return new Result(Result.Code.SUCCESS);
			return (new RetrievePNR()).execute(terminal);
		} catch (TerminalException e) {
			return new Result(Result.Code.TERMINAL_ERROR);
		}
	}

}
