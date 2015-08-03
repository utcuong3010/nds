package com.mbv.airline.sabre.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.Gender;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.Result;

import org.joda.time.DateTime;

import com.mbv.airline.sabre.Terminal;
import com.mbv.airline.sabre.commands.result.PNR.Fact;
import com.mbv.airline.sabre.commands.result.PNR.Pax;
import com.mbv.airline.sabre.commands.result.PNR.Segment;
import com.mbv.airline.sabre.commands.result.Result.Code;
import com.mbv.airline.sabre.utils.Utils;

public class CreatePNR extends BaseCommand {

	private AirItinerary itinerary;

	public CreatePNR(AirItinerary itinerary) {
		this.itinerary = itinerary;
	}

	public PNR execute(Terminal terminal) {
		PNR retval = null;
		try {
			if (terminal == null) {
				retval = new PNR();
				retval.setStatus(Code.ERROR);
				retval.setDescription("No Terminal");
				return retval;
			}

			Result res = (new Ignore()).execute(terminal);
			if (res.getStatus() != Result.Code.SUCCESS) {
				retval = new PNR();
				retval.setStatus(Code.ERROR);
				retval.setDescription("Terminal Error");
				return retval;
			}

			Command cmd;
			Iterator<Command> iterator = createLongSellCmd().iterator();
			while (iterator.hasNext()) {
				cmd = iterator.next();
				cmd.execute(terminal);
			}

			iterator = createPaxCmd().iterator();
			while (iterator.hasNext()) {
				cmd = iterator.next();
				cmd.execute(terminal);
			}

			// validate
			cmd = new RetrievePNR();
			PNR pnr = (PNR) cmd.execute(terminal);

			validatePNR(pnr);
			if (pnr.getStatus() == Code.ERROR)
				return pnr;

			List<Command> cmds = new ArrayList<Command>();
			// Phone
			// 9<><Phone>-M
			String cityCode = terminal.getConfig().getPseudoCityCode();
			if (this.itinerary.getContactInfo().getMobile() != null) {
				cmd = new AddPNRPhoneField(cityCode, this.itinerary
						.getContactInfo().getMobile());
			} else {
				cmd = new AddPNRPhoneField(cityCode, "38269109");
			}
			cmds.add(cmd);

			// Email
			if (this.itinerary.getContactInfo().getEmail() != null) {
				cmd = new AddPNREmailField(this.itinerary.getContactInfo()
						.getEmail());
			} else {
				cmd = new AddPNREmailField("support@mobivi.com");
			}
			cmds.add(cmd);

			// Received:
			// [6QQ]
			/*
			 * if (this.itinerary.getAgentInfo().getUserId() != null) { cmd =
			 * new AddPNRReceivedField(this.itinerary.getAgentInfo()
			 * .getUserId()); } else { cmd = new AddPNRReceivedField("MBV"); }
			 */
			cmd = new AddPNRReceivedField("KIMMAI");
			cmds.add(cmd);

			// Ticketing:
			// 8TL30
			cmd = new AddPNRTicketingField();
			cmds.add(cmd);

			// Remarks:
			// [5H-MBV]
			if (this.itinerary.getAgentInfo().getAgentId() != null) {
				cmd = new AddPNRRemarkField(this.itinerary.getAgentInfo()
						.getAgentId());
			} else {
				cmd = new AddPNRRemarkField("MBV");
			}
			cmds.add(cmd);

			iterator = cmds.iterator();
			while (iterator.hasNext()) {
				cmd = iterator.next();
				Result result = cmd.execute(terminal);
				if (result.getStatus() != Code.SUCCESS) {
					pnr.setStatus(Code.ERROR);
					pnr.setDescription(result.getDescription());
				}
			}

			// end &
			// TODO

			/*
			 * 
			 * System.out.println(cmd.execute(terminal));
			 */
			cmd = new EndAndRetrievePNR();
			// cmd = new RetrievePNR();
			// System.out.println(cmd.execute(terminal));

			return (PNR) cmd.execute(terminal);
		} catch (Exception e) {
			logger.error("Create PNR Error", e.getMessage());
		}
		return retval;
	}

	private List<Command> createLongSellCmd() {
		ArrayList<Command> cmds = new ArrayList<Command>();
		int numberOfSeats = 0;
		for (AirPassengerInfo paxInfo : this.itinerary.getPassengerInfos()) {
			if (paxInfo.getPassengerType() != AirPassengerType.INF) {
				numberOfSeats++;
			}
		}

		// Itinerary
		for (AirFareInfo fareInfo : this.itinerary.getFareInfos()) {
			cmds.add(new LongSell(fareInfo.getFlightCode(), fareInfo
					.getClassCode(), fareInfo.getDepartureDate(), fareInfo
					.getOriginCode() + fareInfo.getDestinationCode(),
					numberOfSeats));
		}
		return cmds;
	}

	private List<Command> createPaxCmd() {
		ArrayList<Command> cmds = new ArrayList<Command>();

		HashMap<String, String> refMap = new HashMap<String, String>();
		LinkedList<AirPassengerInfo> infants = new LinkedList<AirPassengerInfo>();

		int i = 1;
		Command cmd;
		for (AirPassengerInfo paxInfo : this.itinerary.getPassengerInfos()) {
            logger.info(itinerary.getPassengerInfos().size()  + " _ " + paxInfo.getPassengerType() + " _ " + paxInfo.toString());
			switch (paxInfo.getPassengerType()) {
			case INF:
				infants.add(paxInfo);
				break;
			case CHD:
				if (paxInfo.getGender() == Gender.MALE) {
					cmd = new AddPNRNameField(paxInfo.getLastName(),
							paxInfo.getFirstName(), "MSTR");
				} else {
					cmd = new AddPNRNameField(paxInfo.getLastName(),
							paxInfo.getFirstName(), "MISS");
				}
				cmds.add(cmd);
				i++;
				break;
			case ADT:
				if (paxInfo.getGender() == Gender.MALE) {
					cmd = new AddPNRNameField(paxInfo.getLastName(),
							paxInfo.getFirstName(), "MR");
				} else {
					cmd = new AddPNRNameField(paxInfo.getLastName(),
							paxInfo.getFirstName(), "MS");
				}
				cmds.add(cmd);
				if (paxInfo.getReference() != null) {
					refMap.put(paxInfo.getReference(), i + ".1");
				}
				i++;
				break;
			}

		}

		for (AirPassengerInfo paxInfo : infants) {
			String adtRef = refMap.get(paxInfo.getAccompaniedBy());
			if (adtRef != null) {
				for (i = this.itinerary.getFareInfos().size(); i > 0; i--) {
					if (paxInfo.getGender() == Gender.MALE) {
						cmd = new AddPNRInfant(paxInfo.getLastName(),
								paxInfo.getFirstName(), "MSTR",
								paxInfo.getBirthDate(), Integer.toString(i),
								adtRef);
					} else {
						cmd = new AddPNRInfant(paxInfo.getLastName(),
								paxInfo.getFirstName(), "MISS",
								paxInfo.getBirthDate(), Integer.toString(i),
								adtRef);
					}
					cmds.add(cmd);
				}
			}
		}
		return cmds;
	}

	private void validatePNR(PNR pnr) {
		int numberOfINF = Utils.getPaxCountByType(itinerary,
				AirPassengerType.INF);
		int numberOfCHD = Utils.getPaxCountByType(itinerary,
				AirPassengerType.CHD);
		int numberOfADT = Utils.getPaxCountByType(itinerary,
				AirPassengerType.ADT);
		int numberOfSeats = numberOfADT + numberOfCHD;

		// segments
		HashSet<String> segments = new HashSet<String>();
		for (AirFareInfo fareInfo : this.itinerary.getFareInfos()) {
			String tmp = fareInfo.getFlightCode()
					+ fareInfo.getClassCode()
					+ new DateTime(fareInfo.getDepartureDate())
							.toString("ddMMM") + fareInfo.getOriginCode()
					+ fareInfo.getDestinationCode();
			if (segments.contains(tmp)) {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Duplicate Segments");
				return;
			} else {
				segments.add(tmp);
			}
		}

		for (Segment segment : pnr.getSegments()) {
			String tmp = segment.getAirlineDesignator()
					+ segment.getFlightNumber() + segment.getClassOfService()
					+ segment.getDepartureDate().toString("ddMMM")
					+ segment.getFrom() + segment.getTo();
			if (segments.contains(tmp)
					&& segment.getNumberOfSeats() == numberOfSeats
					&& "SS".equals(segment.getStatusCode())) {
				segments.remove(tmp);
			} else {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Extra Segments");
				return;
			}
		}

		if (!segments.isEmpty()) {
			pnr.setStatus(Code.ERROR);
			pnr.setDescription(segments.iterator().next());
			return;
		}

		int _numberOfINF = 0;
		int _numberOfCHD = 0;
		int _numberOfADT = 0;
		for (Pax pax : pnr.getPaxs()) {
			if ("ADT".equalsIgnoreCase(pax.getType())) {
				_numberOfADT++;
			} else if ("CHD".equalsIgnoreCase(pax.getType())) {
				_numberOfCHD++;
			} else if ("INF".equals(pax.getType())) {
				_numberOfINF++;
			} else {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Passenger type " + pax.getType() + " Error");
				return;
			}
		}

		System.out.println("numberOfADT " + numberOfADT + " : " + _numberOfADT
				+ "\n numberOfCHD " + numberOfCHD + " : " + _numberOfCHD
				+ "\n numberOfINF " + numberOfINF + " : " + _numberOfINF);

		if (numberOfADT != _numberOfADT || numberOfCHD != _numberOfCHD
				|| numberOfINF != _numberOfINF) {
			pnr.setStatus(Code.ERROR);
			pnr.setDescription("Pax Count Error");
			return;
		}

		if (numberOfINF > 0) {
			HashSet<String> infFacts = new HashSet<String>();
			for (Fact fact : pnr.getFacts()) {
				if ("SSR".equals(fact.getIndicator())
						&& "INFT".equals(fact.getRequest())) {
					infFacts.add(fact.getText());
				}
			}
			if (infFacts.size() != numberOfINF
					* this.itinerary.getFareInfos().size()) {
				pnr.setStatus(Code.ERROR);
				pnr.setDescription("Infant SSR Error");
				return;
			}
		}
		return;
	}
}
