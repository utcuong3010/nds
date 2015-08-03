package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class AddPNRPhoneField extends BaseCommand {

	String cmd;

	public AddPNRPhoneField(String pcc, String number) {
		this.cmd = "9" + pcc + number + "-M";
	}

	@Override
	public Result execute(Terminal terminal) {
		Result retval = new Result(Result.Code.SUCCESS);
		try {
			logger.info("Send command Add PNR Phone Field!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal).trim();
			logger.info("AddPNRPhoneField RESPONSE: " + resp);
			if (!"*".equals(resp)) {
				retval.setStatus(Code.ERROR);
				retval.setDescription("Phone Field Error");
			}
		} catch (TerminalException e) {
			retval.setStatus(Code.TERMINAL_ERROR);
			retval.setDescription("AddPNRPhoneField Error");
			logger.error("AddPNRPhoneField Error", e);
		}
		return retval;
	}

}
