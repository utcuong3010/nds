package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class AddPNRRemarkField extends BaseCommand {

	private String cmd;

	// [5H-MBV]
	public AddPNRRemarkField(String text) {
		this.cmd = "5h-" + text;
	}

	@Override
	public Result execute(Terminal terminal) {
		Result retval = new Result(Result.Code.SUCCESS);
		try {
			logger.info("Send command Add PNR Remark Field!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal).trim();
			logger.info("AddPNRRemarkField RESPONSE: " + resp);
			if (!"*".equals(resp)) {
				retval.setStatus(Code.ERROR);
				retval.setDescription("Remark Field Error");
			}
		} catch (TerminalException e) {
			retval.setStatus(Code.TERMINAL_ERROR);
			retval.setDescription("AddPNRRemarkField Error");
			logger.error("AddPNRRemarkField Error", e);
		}
		return retval;
	}

}
