package com.mbv.airline.sabre.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PNR.TicketInfo;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class VoidTickets extends BaseCommand {

	private String reservationCode;
	private List<String> ticketNumbers;

	public VoidTickets(String reservationCode, List<String> ticketNumbers) {
		this.reservationCode = reservationCode == null ? "" : reservationCode
				.trim();
		this.ticketNumbers = ticketNumbers == null ? new ArrayList<String>()
				: ticketNumbers;
	}

	@Override
	public Result execute(Terminal terminal) {
		Result result = new Result();

		if (terminal == null || "".equals(reservationCode)
				|| ticketNumbers.size() == 0) {
			result.setStatus(Code.ERROR);
			return result;
		}

		PNR pnr = new RetrievePNR(reservationCode).execute(terminal);

		if (pnr.getStatus() != Code.SUCCESS) {
			result.setStatus(Code.ERROR);
			return result;
		}

		Iterator<String> iterator = this.ticketNumbers.iterator();
		while (iterator.hasNext() && result.getStatus() == Code.SUCCESS) {
			String tkNumber = iterator.next().trim();
			if ("".equals(tkNumber)) {
				result.setStatus(Code.ERROR);
				continue;
			}
			int lineNumber = -1;

			for (TicketInfo tkInfo : pnr.getTickets()) {
				if (tkInfo.getItem() != null
						&& tkInfo.getItem().contains(tkNumber)) {
					try {
						lineNumber = Integer.parseInt(tkInfo.getNumber());
					} catch (Exception ex) {

					}
					break;
				}
			}

			if (lineNumber == -1) {
				result.setStatus(Code.ERROR);
			} else {
				try {
					logger.info("Send command void ticket!!!");
					this.send("WV" + lineNumber, terminal);
					this.receive(terminal);
					this.send("WV" + lineNumber, terminal);
					this.receive(terminal);
					this.send("ER", terminal);
					this.receive(terminal);
				} catch (TerminalException e) {
					result.setStatus(Code.TERMINAL_ERROR);
					logger.error("Void Tickets error: ", e);
				}
			}
		}

		return result;
	}
}
