package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class AddPNRTicketingField extends BaseCommand {

	@Override
	public Result execute(Terminal terminal) {
		Result retval = new Result(Result.Code.SUCCESS);
		try {
			logger.info("Send command add PNR ticket field!!!");
			this.send("8tl30", terminal);
			String resp = this.receive(terminal).trim();
			logger.info("AddPNRTicketingField RESPONSE: " + resp);
			if (!"*".equals(resp)) {
				retval.setStatus(Code.ERROR);
				retval.setDescription("Ticketing Field Error");
			}
		} catch (TerminalException e) {
			retval.setStatus(Code.TERMINAL_ERROR);
			retval.setDescription("AddPNRTicketingField Error");
			logger.error("AddPNRTicketingField Error", e);
		}
		return retval;
	}

}
