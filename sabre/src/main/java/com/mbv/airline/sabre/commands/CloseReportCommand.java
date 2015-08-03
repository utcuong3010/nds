package com.mbv.airline.sabre.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.TerminalConfig;
import com.mbv.airline.sabre.commands.result.Result;
import com.sabre.jsapi.Message;

/**
 * Created by phuongvt on 12/15/14.
 */
public class CloseReportCommand extends BaseCommand {
	private TerminalConfig config;

	public CloseReportCommand(TerminalConfig config) {
		this.config = config;
	}

	public Result execute(Terminal terminal) {
		Result result = new Result();

		// get current date time
		Date date = new Date();
		logger.info("Close report "
				+ new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss").format(date));

		String command = String.format("WY*AGT'STA%s",
				config.getStationNumber());
		// String command = String.format("WY*AGT'D%s%s%s'STA%s",day, month,
		// year, config.getStationNumber());
		try {
			// list child account
			this.send(command, terminal);
			String resp = this.receive(terminal);
			boolean masterOpened = false;
			// command = "+++A+JX PNR";

			if (resp.contains("GRAND TOTAL STATION REPORT")) {
				// close child account
				ArrayList<ChildrenAccInfo> listChild = extractChildrenInfo(resp);
				for (ChildrenAccInfo childrenInfo : listChild) {
					if (childrenInfo.getAccStatus().equals(
							ChildrenAccStatus.OPENING)) {
						if (!childrenInfo.getEmployerNum().equals(
								config.getUsername())) {
							logger.info("Send command close children account "
									+ childrenInfo.getEmployerNum());

							command = String.format("WY%cAC'STA%s'EMP%s",
									Message.CHANGE_CHAR,
									config.getStationNumber(),
									childrenInfo.getEmployerNum());
							this.send(command, terminal);
							resp = this.receive(terminal);
							if (resp.contains("ACCOUNTING RPT CLOSED")) {
								childrenInfo.setAccStatus("CL");
							}
							// else if (!resp.contains("NO OPEN REPORT EXISTS"))
							// {
							// result.setStatus(Result.Code.ERROR);
							// result.setDescription(resp);
							// return result;
							// }
						} else
							masterOpened = true;
					}
				}
				if (masterOpened) {
					logger.info("Send command close children account "
							+ config.getUsername());

					command = String.format("WY%cAC'STA%s'EMP%s",
							Message.CHANGE_CHAR, config.getStationNumber(),
							config.getUsername());
					this.send(command, terminal);
					resp = this.receive(terminal);
				}

				// close master
				logger.info("Send command sell report !!!!");
				command = String
						.format("WY*T'STA%s", config.getStationNumber());
				this.send(command, terminal);
				resp = this.receive(terminal);

				logger.info("Send command close master account "
						+ config.getUsername());
				if (resp.contains("GRAND TOTAL STATION REPORT")
						&& resp.contains("STATUS: OPEN")) {
					command = String.format("WY%cTC", Message.CHANGE_CHAR);
					this.send(command, terminal);
					resp += ("\n" + this.receive(terminal));
					if (!resp.contains("OK-STATION SUMMARY REPORT CLOSED")) {
						result.setStatus(Result.Code.ERROR);
						result.setDescription(resp);
						return result;
					}
				}
				result.setStatus(Result.Code.SUCCESS);
				result.setDescription(resp);
			} else {
				result.setStatus(Result.Code.ERROR);
				result.setDescription(resp);
			}
			
		} catch (Exception e) {
			result.setStatus(Result.Code.TERMINAL_ERROR);
			result.setDescription("Close Report error " + e.getMessage());
			logger.error("Close Report error ", e);
		}

		return result;
	}

	private ArrayList<ChildrenAccInfo> extractChildrenInfo(String resp) {
		ArrayList<ChildrenAccInfo> childrenInfos = new ArrayList<ChildrenAccInfo>();

		String lines[] = resp.split("\n");
		boolean start = false;
		for (String line : lines) {
			if (line.startsWith("END OF REPORT"))
				start = false;
			if (start == true) {
				String ele[] = line.split("\\s+");
				ChildrenAccInfo childrenInfo = new ChildrenAccInfo();
				childrenInfo.setSequence(ele[0]);
				childrenInfo.setEmployerNum(ele[1]);
				childrenInfo.setAgent(ele[2]);
				childrenInfo.setAccStatus(ele[3]);

				childrenInfos.add(childrenInfo);
			}
			if (line.startsWith("SEQ"))
				start = true;
		}
		return childrenInfos;
	}

	public class ChildrenAccInfo {
		private String sequence;
		private String employerNum;
		private String nbr;
		private String agent;
		private String credit;
		private String cur;
		private String cash;
		private String count;
		private ChildrenAccStatus status;

		public String getSequence() {
			return sequence;
		}

		public void setSequence(String seq) {
			this.sequence = seq;
		}

		public String getEmployerNum() {
			return employerNum;
		}

		public void setEmployerNum(String emp) {
			this.employerNum = emp;
		}

		public String getNBR() {
			return nbr;
		}

		public void setNBR(String nbr) {
			this.nbr = nbr;
		}

		public String getAgent() {
			return agent;
		}

		public void setAgent(String agent) {
			this.agent = agent;
		}

		public ChildrenAccStatus getAccStatus() {
			return status;
		}

		public void setAccStatus(String status) {
			if (status.equals("OP"))
				this.status = ChildrenAccStatus.OPENING;
			if (status.equals("CL"))
				this.status = ChildrenAccStatus.CLOSED;
		}

		public String getCredit() {
			return credit;
		}

		public void setCredit(String credit) {
			this.credit = credit;
		}

		public String getCur() {
			return cur;
		}

		public void setCur(String cur) {
			this.cur = cur;
		}

		public String geCash() {
			return cash;
		}

		public void setCash(String cash) {
			this.cash = cash;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}
	}

	public enum ChildrenAccStatus {
		OPENING, CLOSED;
	}
}