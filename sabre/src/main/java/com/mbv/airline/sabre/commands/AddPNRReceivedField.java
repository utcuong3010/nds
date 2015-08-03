package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class AddPNRReceivedField extends BaseCommand {

	private String cmd;

	public AddPNRReceivedField(String text) {
		this.cmd = "6" + text;
	}

	@Override
	public Result execute(Terminal terminal) {
		Result retval = new Result(Result.Code.SUCCESS);
		try {
            logger.info("Send command Add PNR Received Field!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal).trim();
            logger.info("AddPNRReceivedField RESPONSE: " +  resp);
			if (!"*".equals(resp)) {
				retval.setStatus(Code.ERROR);
				retval.setDescription("Received Field Error");
			}
		} catch (TerminalException e) {
			retval.setStatus(Code.TERMINAL_ERROR);
			retval.setDescription("AddPNRReceivedField Error");
			logger.error("AddPNRReceivedField Error", e);
		}
		return retval;
	}

}
