package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.Result;

public class Ignore extends BaseCommand{

	public Result execute(Terminal terminal) {
		try {
			this.send("I", terminal);
			String resp = this.receive(terminal).trim();
			if("IGD".equalsIgnoreCase(resp)){
				return new Result(Result.Code.SUCCESS);
			}else{
				return new Result(Result.Code.ERROR);
			}
		} catch (TerminalException e) {
			return new Result(Result.Code.TERMINAL_ERROR);
		}		
	}

}
