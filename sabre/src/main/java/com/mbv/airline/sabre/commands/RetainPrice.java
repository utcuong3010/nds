package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;

public class RetainPrice extends BaseCommand {

	@Override
	public Result execute(Terminal terminal) {
		try {
			this.send("PQ", terminal);
			String resp = this.receive(terminal);
			return new Result(Result.Code.SUCCESS);
		} catch (TerminalException e) {
			return new Result(Result.Code.TERMINAL_ERROR);
		}
	}

}
