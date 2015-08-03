package com.mbv.airline.sabre;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.sabre.commands.*;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.Result;
import com.sabre.gds.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;

/**
 * Created by phuongvt on 1/20/15.
 */
public class PayBookingIDTest extends BaseCommand {
    private String reservationCode;
    private static SenderManager senderManager;

    public PayBookingIDTest(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public static void main(String[] args) throws ParseException {
        TerminalConfig config = new TerminalConfig();
        config.setUsername("6274");
        config.setPassword("MTLIVI10");
        config.setAirlineCode("AUF");
        config.setStationNumber("37983061");
        Terminal terminal = new Terminal(config);

        if (terminal.open() == false) {
            System.out.println("Connection Error");
            return;
        }

        Result result = (new SignIn(senderManager)).execute(terminal);
        if (result.getStatus() != Result.Code.SUCCESS) {
            System.out.println("Sign-in Error");
            return;
        }
        

		DateTime expirationDate = DateTimeFormat.forPattern("MMyyyy")
				.parseDateTime("12" + DateTime.now().getYear());
		
		System.out.println("_____" + expirationDate.toString("MMyy"));

		PayBookingIDTest retrieveBookingID = new PayBookingIDTest("KRLMYF");
        retrieveBookingID.execute(terminal);


        new SignOut().execute(terminal);
        terminal.close();
    }

    public PNR execute(Terminal terminal) {
        String command;
        String resp;

        if (this.reservationCode != null) {
            command = "I";
            try {
                this.send(command, terminal);
                resp = this.receive(terminal); // IGD
                System.out.println(resp);
                command = "*" + this.reservationCode;
                this.send(command, terminal);
                System.out.println("______________________");
                resp = this.receive(terminal);

//                command = "WP";
//                this.send(command, terminal);
//                System.out.println("______________________");
//                resp = this.receive(terminal);
//                parse(resp);

                command = "+++A++QIK00001^PN37+JX PNR";
                try {
                    this.send(command, terminal);
                    resp = this.receive(terminal);
//                    System.out.println(resp);
                    parse(resp);
                } catch (Terminal.TerminalException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    PNR pnr = new PNR();
                    pnr.setStatus(Result.Code.TERMINAL_ERROR);
                }

                command = "+++A++QIK00001+WPP2ADT";
                command = "+++A++QIK00001+*PQS";
//                command = "*PQS";
                this.send(command, terminal);
                resp = this.receive(terminal);
                parse(resp);
//
//                command = "WP";
//                this.send(command, terminal);
//                resp = this.receive(terminal);
//                parse(resp);

//                System.out.println("______________________");
//                command = "*PE";
//                this.send(command, terminal);
//                resp = this.receive(terminal);
//
//                System.out.println("______________________");
//                command = "*PQS";
//                this.send(command, terminal);
//                resp = this.receive(terminal);
//
//                System.out.println("______________________");
//                command = "WP";
//                this.send(command, terminal);
//                resp = this.receive(terminal);
//                System.out.println("______________________");
//                command = "WP*BAG";
//                this.send(command, terminal);
//                resp = this.receive(terminal);

//                resp = "";
//                System.out.println("______________________");
//                command = "+++A++QIK00001^PN37+JX PNR";
//                this.send(command, terminal);
//                resp += this.receive(terminal);
//                parse(resp);

//                command = "*PQS";
//                this.send(command, terminal);
//                resp = this.receive(terminal);
//                if (resp.contains("PRICE QUOTE RECORD - SUMMARY BY NAME NUMBER")) {
//                    parsePricing1(resp);
//                } else {
//                    command = "WP";
//                    this.send(command, terminal);
//                    resp = this.receive(terminal);
//                    parse(resp);
//                }


            } catch (Terminal.TerminalException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }

        return null;
    }

    private void parsePricing1(String resp) {
        Long total = 0L;
        String rows[] = resp.split("\n");
        for (String row : rows) {
            if (row.contains("VND")) {
                System.out.println(row);
                String eles[] = row.split(" ");
                for (String ele : eles) {
                    if (ele.contains("VND")) {
                        System.out.println(ele);
                        total += Long.parseLong(ele.replaceAll("VND", "").trim());
                    }
                }
            }
        }

    }

    private PNR parse(String data) {
//        String rows[] = data.split("\n");
//        for (String row : rows) {
//            if (row.contains("VND") && row.contains("ADT")) {
//                String eles[] = row.split(" ");
//                for (String ele : eles) {
//                    if (ele.contains("VND")) {
//                        System.out.println(ele);
//                    } else if (ele.contains("XT")) {
//                        System.out.println(ele);
//                    } else if (ele.contains("VND") && ele.contains("ADT")) {
//                        System.out.println(ele);
//                    }
//                }
//            } else if (row.contains("TTL")) {
//                String eles[] = row.split(" ");
//                for (String ele : eles) {
//                    if (ele.contains("TTL")) {
//                        System.out.println(ele);
//                    }
//                }
//            } else if (row.contains("VND")) {
//                System.out.println(row);
//            }
//        }

        PNR pnr = new PNR();
        GDSDecoder decoder = new GDSDecoder();

        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMMyyyy");

        int code = decoder.allocMsg(data);
        if (code != GDSError.GDS_OK)
        {
            return pnr;
        }
        System.out.println("________" + data);

        GDSSegValue gdsSeg;
        GDSElemValue gdsElem;

        PNR.PriceQuote priceQuote = null;

        while ((gdsSeg = decoder.getNextSeg()) != null) {
            // Get Received
            System.out.println(gdsSeg.id + "____");

            while ((gdsElem = decoder.getNextElem()) != null) {
                System.out.println(gdsElem.id + "....." + gdsElem.value);
            }
//            if ("GEN000".equals(gdsSeg.id)) {
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("01PR".equals(gdsElem.id)) {
//                        pnr.setReceived(gdsElem.value);
//                    } else if ("001B".equals(gdsElem.id)) {
//                        pnr.setReservationCode(gdsElem.value);
//                    }
//                }
//            }
//            // Get Phone
//            else if ("PHN000".equals(gdsSeg.id)) {
//                String phone = "";
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    phone += gdsElem.value + " ";
//                }
//
//                pnr.setPhone(phone.trim());
//            }
//            // Get Ticketing Info
//            else if ("TRM000".equals(gdsSeg.id)) {
//                PNR.TicketInfo ticket = new PNR.TicketInfo();
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("00TC".equals(gdsElem.id)) {
//                        ticket.setNumber(gdsElem.value);
//                    } else if ("01QB".equals(gdsElem.id)) {
//                        ticket.setTimeLimit(gdsElem.value);
//                    } else if ("01PQ".equals(gdsElem.id)) {
//                        ticket.setItem(gdsElem.value);
//                    }
//                }
//
//                pnr.addTicket(ticket);
//            }
//            // Get Remark
//            else if ("HRM000".equals(gdsSeg.id)) {
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("0002".equals(gdsElem.id)) {
//                        pnr.setRemark(gdsElem.value);
//                    }
//                }
//            }
//            // Get Name
//            else if ("PAX000".equals(gdsSeg.id)) {
//                PNR.Pax pax = new PNR.Pax();
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("00TF".equals(gdsElem.id)) {
//                        pax.setNameNumber(gdsElem.value);
//                    } else if ("00LZ".equals(gdsElem.id)) {
//                        pax.setInfantIndicator(gdsElem.value);
//                    } else if ("000N".equals(gdsElem.id)) {
//                        pax.setLastName(gdsElem.value);
//                    } else if ("000O".equals(gdsElem.id)) {
//                        String text = gdsElem.value;
//                        pax.setFirstName(text.substring(0,
//                                text.lastIndexOf(' ')).trim());
//
//                        String title = text.substring(text.lastIndexOf(' '))
//                                .trim();
//                        String gender = "";
//                        String type = "";
//
//                        if ("MR".equals(title)) {
//                            gender = "M";
//                            type = "ADT";
//                        } else if ("MS".equals(title)) {
//                            gender = "F";
//                            type = "ADT";
//                        } else if ("MSTR".equals(title)) {
//                            gender = "M";
//                            if ("I".equals(pax.getInfantIndicator())) {
//                                type = "INF";
//                            } else {
//                                type = "CHD";
//                            }
//                        } else if ("MISS".equals(title)) {
//                            gender = "F";
//                            if ("I".equals(pax.getInfantIndicator())) {
//                                type = "INF";
//                            } else {
//                                type = "CHD";
//                            }
//                        }
//
//                        pax.setGender(gender);
//                        pax.setType(type);
//                    }
//                }
//
//                pnr.addPax(pax);
//            } else if ("AFX000".equals(gdsSeg.id)) {
//                PNR.Fact fact = new PNR.Fact();
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("00VF".equals(gdsElem.id)) {
//                        fact.setItemNumber(gdsElem.value);
//                    } else if ("00Y0".equals(gdsElem.id)) {
//                        fact.setIndicator(gdsElem.value);
//                    } else if ("005M".equals(gdsElem.id)) {
//                        fact.setRequest(gdsElem.value);
//                    } else if ("038D".equals(gdsElem.id)) {
//                        fact.setText(gdsElem.value);
//                    }
//                }
//
//                pnr.addFact(fact);
//            }
//            // Get Itinerary
//            else if ("AIR000".equals(gdsSeg.id)) {
//                PNR.Segment segment = null;
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("001N".equals(gdsElem.id)) {
//                        segment = pnr.getSegment(gdsElem.value);
//                        if (segment == null)
//                            segment = new PNR.Segment(gdsElem.value);
//                    } else if ("00SJ".equals(gdsElem.id)) {
//                        segment.setStatusCode(gdsElem.value);
//                    } else if ("0182".equals(gdsElem.id)) {
//                        try {
//                            int num = Integer.parseInt(gdsElem.value);
//                            segment.setNumberOfSeats(num);
//                        } catch (Exception ex) {
//                            segment.setNumberOfSeats(0);
//                        }
//                    } else if ("000B".equals(gdsElem.id)) {
//                        segment.setAirlineDesignator(gdsElem.value);
//                    } else if ("007V".equals(gdsElem.id)) {
//                        segment.setClassOfService(gdsElem.value);
//                    } else if ("000I".equals(gdsElem.id)) {
//                        segment.setFlightNumber(gdsElem.value.replaceAll("^0+", ""));
//                    } else if ("00CA".equals(gdsElem.id)) {
//                        DateTime tmpDatetime = formatter
//                                .parseDateTime(gdsElem.value
//                                        + DateTime.now().getYear());
//                        if (tmpDatetime.isBeforeNow())
//                            tmpDatetime = tmpDatetime.plusYears(1);
//                        segment.setDepartureDate(tmpDatetime);
//                    }
//                }
//
//                pnr.addSegment(segment);
//            } else if ("CPA000".equals(gdsSeg.id)) {
//                PNR.Segment segment = null;
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("001N".equals(gdsElem.id)) {
//                        segment = pnr.getSegment(gdsElem.value);
//                    } else if ("00G3".equals(gdsElem.id)) {
//                        segment.setFrom(gdsElem.value);
//                    } else if ("00GJ".equals(gdsElem.id)) {
//                        segment.setTo(gdsElem.value);
//                    }
//                }
//
//                pnr.updateSegment(segment);
//            } else if ("WSR003".equals(gdsSeg.id)) {
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("001P".equals(gdsElem.id)) {
//                        pnr.setBaseFare(Long.parseLong(gdsElem.value));
//                    } else if ("001Q".equals(gdsElem.id)) {
//                        pnr.setTaxAmount(Long.parseLong(gdsElem.value));
//                    } else if ("01NX".equals(gdsElem.id)) {
//                        pnr.setTotalAmount(Long.parseLong(gdsElem.value));
//                    }
//                }
//            } else if ("WSR002".equals(gdsSeg.id)) {
//                priceQuote = new PNR.PriceQuote();
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("01SO".equals(gdsElem.id)) {
//                        priceQuote.setCount(Integer.parseInt(gdsElem.value));
//                    } else if ("00UG".equals(gdsElem.id)) {
//                        priceQuote.setPaxType(gdsElem.value);
//                    } else if ("001P".equals(gdsElem.id)) {
//                        priceQuote.setBaseFare(Long.parseLong(gdsElem.value));
//                    } else if ("001Q".equals(gdsElem.id)) {
//                        priceQuote.setTaxAmount(Long.parseLong(gdsElem.value));
//                    } else if ("01NX".equals(gdsElem.id)) {
//                        priceQuote
//                                .setTotalAmount(Long.parseLong(gdsElem.value));
//                    }
//                }
//            } else if ("WSR005".equals(gdsSeg.id)) {
//                PNR.QuoteSegment segment = new PNR.QuoteSegment();
//                String datetimeString = "";
//
//                while ((gdsElem = decoder.getNextElem()) != null) {
//                    if ("001N".equals(gdsElem.id)) {
//                        segment.setSegmentNumber(gdsElem.value);
//                    } else if ("00G3".equals(gdsElem.id)) {
//                        segment.setFrom(gdsElem.value);
//                    } else if ("00EX".equals(gdsElem.id)) {
//                        segment.setAirlineDesignator(gdsElem.value);
//                    } else if ("000I".equals(gdsElem.id)) {
//                        segment.setFlightCode(gdsElem.value);
//                    } else if ("007V".equals(gdsElem.id)) {
//                        segment.setServiceClass(gdsElem.value);
//                    } else if ("01AL".equals(gdsElem.id)) {
//                        segment.setStatusCode(gdsElem.value);
//                    } else if ("00Z1".equals(gdsElem.id)) {
//                        segment.setFareBasisCode(gdsElem.value);
//                    } else if ("001Z".equals(gdsElem.id)) {
//                        segment.setBaggageAllowance(gdsElem.value);
//                    } else if ("00CA".equals(gdsElem.id)) {
//                        datetimeString = gdsElem.value
//                                + DateTime.now().getYear();
//                    } else if ("007X".equals(gdsElem.id)) {
//                        datetimeString += gdsElem.value;
//                        DateTime tmpDatetime = DateTimeFormat.forPattern(
//                                "ddMMMyyyyHHmm").parseDateTime(datetimeString);
//                        if (tmpDatetime.isBeforeNow())
//                            tmpDatetime = tmpDatetime.plusYears(1);
//                        segment.setDepartureDate(tmpDatetime);
//                    }
//                }
//
//                if (segment.getSegmentNumber() != null) {
//                    priceQuote.addSegment(segment);
//                } else {
//                    pnr.addPriceQuote(priceQuote);
//                }
//            }
        }

//        System.out.println(pnr.toString());
        return pnr;
    }

}
