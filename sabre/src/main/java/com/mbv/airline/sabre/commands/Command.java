package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.commands.result.Result;

public interface Command {
	public Result execute(Terminal terminal);
}
