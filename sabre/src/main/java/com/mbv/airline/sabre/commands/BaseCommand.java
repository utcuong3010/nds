package com.mbv.airline.sabre.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;

public abstract class BaseCommand implements Command {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	protected void send(String cmd, Terminal terminal) throws TerminalException {
		if (terminal == null)
			throw new TerminalException("SEND COMMAND ERROR",
					"terminal not found");
		terminal.send(cmd);
	}

	protected String receive(Terminal terminal) throws TerminalException {
		if (terminal == null)
			throw new TerminalException("RECEIVE COMMAND RESULT ERROR",
					"terminal not found");
		return terminal.receive();
	}
}
