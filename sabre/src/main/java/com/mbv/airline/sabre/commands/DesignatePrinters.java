package com.mbv.airline.sabre.commands;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.TerminalConfig;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class DesignatePrinters extends BaseCommand {

	@Override
	public Result execute(Terminal terminal) {
		// TODO Auto-generated method stub

		// *S*P = NO PRINTERS

		try {
			TerminalConfig config = terminal.getConfig();

			logger.info("Send command Designate Printers!!!");

			this.send("*s*p", terminal);
			String response = this.receive(terminal);
			if (response.contains("NO PRINTERS")) {
				this.send("YQRY " + config.getHardcopyPrinter(), terminal);
				response = this.receive(terminal);
				this.send(
						"W*" + config.getPrintRoutine()
								+ config.getTicketPrinter()
								+ config.getStationNumber(), terminal);
				response = this.receive(terminal);
				this.send("PTR/" + config.getHardcopyPrinter() + "*", terminal);
				response = this.receive(terminal);
				this.send("*s*p", terminal);
				response = this.receive(terminal);
			}

			if (response.contains(config.getHardcopyPrinter())
					&& response.contains(config.getTicketPrinter())) {
				return new Result(Code.SUCCESS);
			} else {
				logger.info("Designate Printers not found: " + response);
				return new Result(Code.ERROR, "Designate Printers not found "
						+ response);
			}
		} catch (Exception ex) {
			logger.error("Designate Printers error: ", ex);
			return new Result(Code.ERROR, "Designate Printers error "
					+ ex.getMessage());
		}
	}

}
