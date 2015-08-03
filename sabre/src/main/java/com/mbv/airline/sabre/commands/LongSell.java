package com.mbv.airline.sabre.commands;

import java.util.Date;

import com.mbv.airline.sabre.commands.result.Result;
import org.joda.time.DateTime;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;

public class LongSell extends BaseCommand{

	private String cmd;
	//0<carrier code><flight number><class of service><date><city pair>NN<number of seats>
	public LongSell(String flightCode, String classCode, Date date, String cityPair, int numSeats){
		this.cmd = "0" + flightCode + classCode + new DateTime(date).toString("ddMMM") + cityPair + "NN" + numSeats;
	}

	public Result execute(Terminal terminal) {
		try {
            logger.info("Send command long sell!!!");
			this.send(this.cmd, terminal);
			String resp = this.receive(terminal);	
            logger.info("LONG SELL RESPONSE: " +  resp);		
			return new Result(Result.Code.SUCCESS);			
		} catch (TerminalException e) {
			return new Result(Result.Code.TERMINAL_ERROR);
		}	
	}
	
}
