package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class AddPNREmailField extends BaseCommand {
	private String cmd;

	public AddPNREmailField(String email) {
		this.cmd = "PE'" + email + "'";
	}

	@Override
	public Result execute(Terminal terminal) {
		Result retval = new Result(Result.Code.SUCCESS);
		try {
			logger.info("Send command Add PNR Email Field!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal).trim();
			logger.info("AddPNREmailField RESPONSE: " + resp);
			if (!"*".equals(resp)) {
				retval.setStatus(Code.ERROR);
				retval.setDescription("Email Field Error");
			}
		} catch (TerminalException e) {
			retval.setStatus(Code.TERMINAL_ERROR);
			retval.setDescription("AddPNREmailField Error");
			logger.error("AddPNREmailField Error", e);
		}
		return retval;
	}

}
