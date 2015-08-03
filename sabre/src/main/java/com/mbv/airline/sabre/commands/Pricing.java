package com.mbv.airline.sabre.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.PricingResult;
import com.mbv.airline.sabre.commands.result.Result;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.PNR.Pax;
import com.mbv.airline.sabre.commands.result.PricingResult.PaxPricing;
import com.mbv.airline.sabre.commands.result.PricingResult.SegPricing;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.utils.PricingClassUtil;
import com.sabre.gds.GDSDecoder;
import com.sabre.gds.GDSElemValue;
import com.sabre.gds.GDSError;
import com.sabre.gds.GDSSegValue;

public class Pricing extends BaseCommand {

	private String reservationCode;
	private PricingClassUtil pcUtil;

	private Pricing() {

	}

	public Pricing(String reservationCode, PricingClassUtil pcUtil) {
		this.reservationCode = reservationCode;
		this.pcUtil = pcUtil;
	}

	@Override
	public Result execute(Terminal terminal) {
		PricingResult result = null;

		// Retrieve PNR
		PNR pnr = (PNR) new RetrievePNR(reservationCode).execute(terminal);
		if(pnr.getStatus()!=Code.SUCCESS){
			result = new PricingResult();
			result.setStatus(Code.ERROR);
			result.setDescription("RetrievePNR error: Invalid reservationCode");
			return result;
		}

		int numADT = 0;
		int numCNN = 0;
		int numINF = 0;

		for (Pax pax : pnr.getPaxs()) {
			if ("ADT".equals(pax.getType())) {
				numADT++;
			} else if ("CHD".equals(pax.getType())) {
				numCNN++;
			} else if ("INF".equals(pax.getType())) {
				numINF++;
			}
		}

		// Compose command
		String prefix = "+++A++QIK00001+WP";

		String detail = "P" + numADT + "ADT";
		if (numCNN > 0)
			detail = detail + "/" + numCNN + "CNN";
		if (numINF > 0)
			detail = detail + "/" + numINF + "INF";

		// Execute pricing command separately
		result = new PricingResult();
		result.setStatus(Code.SUCCESS);
		result.setTotalAmount(0L);
		result.setPNR(pnr);

		for (int i = 1; i <= pnr.getSegments().size()
				&& result.getStatus() == Code.SUCCESS; i++) {
			PricingResult tmpResult;
			try {
				this.send(prefix + "s" + i + "\'" + detail, terminal);
				tmpResult = parse(this.receive(terminal));
				tmpResult.setPNR(pnr);
				if (tmpResult.getStatus() != Code.SUCCESS) {
					result.setStatus(Code.ERROR);
				} else {
					// Total Amount
					result.setTotalAmount(result.getTotalAmount()
							+ tmpResult.getTotalAmount());
					// Time Limit
					if (result.getTimeLimit() == null) {
						result.setTimeLimit(tmpResult.getTimeLimit());
					} else {
						if (tmpResult.getTimeLimit() != null) {
							if (tmpResult.getTimeLimit().isBefore(
									result.getTimeLimit()))
								result.setTimeLimit(tmpResult.getTimeLimit());
						}
					}
					// Pax Pricing
					result.getPrInfos().addAll(tmpResult.getPrInfos());
				}
			} catch (Exception e) {
				logger.error("Send Pricing command error", e);
				result.setStatus(Code.ERROR);
				result.setDescription("Pricing Error");
			}
		}

		if (pcUtil == null || result.getStatus() != Code.SUCCESS)
			return result;

		// Check whether segments can be combined
		boolean canCombineSegments = true;
		int numOfSegment = pnr.getSegments().size();
		if (numOfSegment > 1) {
			Set<String> pricingGroups = new HashSet<String>();

			List<SegPricing> segPricingList = ((PricingResult) result)
					.getSegPricingList("ADT");

			for (SegPricing segPricing : segPricingList) {
				String classGroup = pcUtil.getGroupofClass(segPricing
						.getFareBasisCode());
				if (classGroup == null) {
					canCombineSegments = false;
				} else {
					pricingGroups.add(classGroup);
				}
			}

			canCombineSegments = canCombineSegments
					&& (pricingGroups.size() == 1);
		}

		// Execute final pricing
		if (canCombineSegments) {
			try {
				this.send(prefix + detail, terminal);
				String resp = this.receive(terminal);
				result = parse(resp);
				result.setPNR(pnr);

				if (result.getStatus() != Code.SUCCESS) {
					result.setStatus(Code.ERROR);
				} else {
					this.send("pq", terminal);
					this.receive(terminal);
				}

			} catch (TerminalException e) {
				logger.error("Send Group Pricing command error: ", e);
				result = new PricingResult(Code.ERROR);
				result.setPNR(pnr);
				result.setDescription("Group Pricing Error");
			}
		} else {
			result = new PricingResult();
			result.setStatus(Code.SUCCESS);
			result.setTotalAmount(0L);
			result.setPNR(pnr);
			for (int i = 1; i <= numOfSegment
					&& result.getStatus() == Code.SUCCESS; i++) {
				PricingResult tmpResult;
				try {
					this.send(prefix + "s" + i + "\'" + detail, terminal);
					String resp = this.receive(terminal);
					tmpResult = parse(resp);
					tmpResult.setPNR(pnr);
					if (tmpResult.getStatus() != Code.SUCCESS) {
						result.setStatus(Code.ERROR);
					} else {
						this.send("pq", terminal);
						this.receive(terminal);
						result.setTotalAmount(result.getTotalAmount()
								+ tmpResult.getTotalAmount());
						if (result.getTimeLimit() == null) {
							result.setTimeLimit(tmpResult.getTimeLimit());
						} else {
							if (tmpResult.getTimeLimit() != null) {
								if (tmpResult.getTimeLimit().isBefore(
										result.getTimeLimit()))
									result.setTimeLimit(tmpResult
											.getTimeLimit());
							}
						}
						result.getPrInfos().addAll(tmpResult.getPrInfos());
					}
				} catch (Exception ex) {
					logger.error("Send Check Pricing command error ", ex);
					result.setStatus(Code.ERROR);
					result.setDescription("Send Check Pricing command error");
				}
			}
		}

		try {
			this.send("er", terminal);
			this.receive(terminal);
			this.send("er", terminal);
			this.receive(terminal);
		} catch (TerminalException e) {
			logger.error("Pricing command check error ", e);
			result.setStatus(Code.ERROR);
			result.setDescription("Pricing command check error");
		}

		result.setPNR(pnr);
		return result;
	}

	private PricingResult parse(String data) {
		PricingResult result = new PricingResult();

		GDSDecoder decoder = new GDSDecoder();

		int code = decoder.allocMsg(data);

		if (code != GDSError.GDS_OK) {
			logger.error("Alloc msg error: " + GDSError.getErrorMsg(code));
			result.setStatus(Code.ERROR);
			result.setDescription("Alloc msg error: "
					+ GDSError.getErrorMsg(code));
			return result;
		}

		GDSSegValue gdsSeg;
		GDSElemValue gdsElem;

		PaxPricing paxPricing = null;
		SegPricing segPricing = null;

		while ((gdsSeg = decoder.getNextSeg()) != null) {
			// Sum of amount
			if ("WPSUMT".equals(gdsSeg.id)) {
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00UJ".equals(gdsElem.id)) {
						result.setTotalAmount(Long.parseLong(gdsElem.value));
					}
				}
			}
			// Last date to purchase
			else if ("PRDATA".equals(gdsSeg.id)) {
				String lastDate = "";
				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00OZ".equals(gdsElem.id)) {
						lastDate += gdsElem.value;
					} else if ("003X".equals(gdsElem.id)) {
						lastDate += gdsElem.value;
					}
				}
				if (!lastDate.trim().isEmpty()) {
					try {
						DateTimeFormatter formatter = DateTimeFormat
								.forPattern("ddMMMyyyyHHmm");
						result.setTimeLimit(formatter.parseDateTime(lastDate));
					} catch (Exception ex) {
						logger.error("Pricing Parse Time Limit error "
								+ lastDate, ex);
					}
				}
			}
			// Pricing Info
			else if ("WPPAXT".equals(gdsSeg.id)) {
				paxPricing = new PaxPricing();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("00J8".equals(gdsElem.id)) {
						paxPricing.setCount(Integer.parseInt(gdsElem.value));
					} else if ("00UG".equals(gdsElem.id)) {
						paxPricing.setType(gdsElem.value);
					}
				}

				result.addPaxPricing(paxPricing);

			} else if ("WPCALC".equals(gdsSeg.id)) {
				segPricing = new SegPricing();

				while ((gdsElem = decoder.getNextElem()) != null) {
					if ("003Q".equals(gdsElem.id)) {
						segPricing.setFrom(gdsElem.value);
					} else if ("00GV".equals(gdsElem.id)) {
						segPricing.setTo(gdsElem.value);
					} else if ("00Z1".equals(gdsElem.id)) {
						segPricing.setFareBasisCode(gdsElem.value);
					} else if ("00UI".equals(gdsElem.id)) {
						segPricing.setFareAmount(Long.parseLong(gdsElem.value));
					}
				}

				result.addSegPricing(paxPricing.getType(), segPricing);
			}
		}

		return result;

	}

}
