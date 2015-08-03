package com.mbv.airline.sabre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.sabre.commands.*;
import com.mbv.airline.sabre.commands.result.FlightSegment;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.result.Result;
import com.sabre.gds.GDSDecoder;
import com.sabre.gds.GDSElemValue;
import com.sabre.gds.GDSError;
import com.sabre.gds.GDSSegValue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import static akka.pattern.Patterns.ask;

public class SearchTest extends BaseCommand {
    private Date date;
    private String from;
    private String to;
    private static SenderManager senderManager;

    public SearchTest(int day, int month, int year, String from, String to) {
        this.date = new Date(year, month, day);
        this.from = from;
        this.to = to;
    }

    public static void main(String[] args) throws ParseException {
        TerminalConfig config = new TerminalConfig();
        config.setUsername("6274");
        config.setPassword("MTLIVI15");
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

        SearchTest retrieveBookingID = new SearchTest(7, 7, 2015, "SGN", "HAN");
        retrieveBookingID.execute(terminal);
    }

    public PNR execute(Terminal terminal) {
        String command;
        String resp;
        command = "I";
        SimpleDateFormat format = new SimpleDateFormat("ddMMM");

        try {
            this.send(command, terminal);
            resp = this.receive(terminal); // IGD
            System.out.println(resp);
            command = "+++A++QIK00001^AV06+1" + format.format(date) + this.from + this.to;

            command = "+++A++QIK00001^FQ07+FQ" + this.from + this.to
                    + format.format(date) + "," + "A" + "'FL";
            this.send(command, terminal);
            System.out.println("______________________");
            resp = this.receive(terminal);

            parse(resp);
//            command = "+++A++QIK00001^AV06+1*";
//            try {
//                this.send(command, terminal);
//                resp = this.receive(terminal);
//                parse(resp);
//            } catch (Terminal.TerminalException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                PNR pnr = new PNR();
//                pnr.setStatus(Result.Code.TERMINAL_ERROR);
//            }
        } catch (Terminal.TerminalException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        return null;
    }

    private PNR parse(String data) {
        PNR pnr = new PNR();
        GDSDecoder decoder = new GDSDecoder();

        int code = decoder.allocMsg(data);
        if (code != GDSError.GDS_OK)
            return pnr;

        GDSSegValue gdsSeg;
        GDSElemValue gdsElem;

        PNR.PriceQuote priceQuote = null;

        while ((gdsSeg = decoder.getNextSeg()) != null) {
            // Get Received
            System.out.println(gdsSeg.id + "____");

            while ((gdsElem = decoder.getNextElem()) != null) {
                System.out.println(gdsElem.id + "....." + gdsElem.value);
            }
        }

        String dateString = "";
        DateTimeFormatter formatter = DateTimeFormat.forPattern("ddMMMyyyyHHmm");
        DateTime tmpDatetime;
        while ((gdsSeg = decoder.getNextSeg()) != null) {
            //System.out.println(" Seg Id: " + segV.id);
            if ("AVHDR0".equals(gdsSeg.id)) {
                while ((gdsElem = decoder.getNextElem()) != null) {
                    if ("012U".equals(gdsElem.id)) {
                        dateString = gdsElem.value + DateTime.now().getYear();
                    } else if ("0145".equals(gdsElem.id)) {
                        //retval.setFrom(gdsElem.value);
                    } else if ("018B".equals(gdsElem.id)) {
                        //retval.setTo(gdsElem.value);
                    }
                }
            } else if ("AVLIN0".equals(gdsSeg.id)) {
                FlightSegment segment = new FlightSegment();
                LinkedList<String> classCodes = new LinkedList<String>();

                while ((gdsElem = decoder.getNextElem()) != null) {
                    if ("00B1".equals(gdsElem.id)) {
                        //segment.setLineNumber(gdsElem.value);
                    } else if ("012G".equals(gdsElem.id)) {
                        segment.setCarrierCode(gdsElem.value);
                    } else if ("016P".equals(gdsElem.id)) {
                        segment.setFlightCode(gdsElem.value);
                    } else if ("0145".equals(gdsElem.id)) {
                        segment.setDepartureCode(gdsElem.value);
                    } else if ("018B".equals(gdsElem.id)) {
                        segment.setArrivalCode(gdsElem.value);
                    } else if ("01L9".equals(gdsElem.id)) {
                        tmpDatetime = formatter.parseDateTime(dateString + gdsElem.value);
                        if (tmpDatetime.isBeforeNow()) tmpDatetime = tmpDatetime.plusYears(1);
                        segment.setDepartureTime(tmpDatetime);
                    } else if ("01MK".equals(gdsElem.id)) {
                        tmpDatetime = formatter.parseDateTime(dateString + gdsElem.value);
                        if (tmpDatetime.isBefore(segment.getDepartureTime())) tmpDatetime = tmpDatetime.plusYears(1);
                        segment.setArrivalTime(tmpDatetime);
                    } else if ("007V".equals(gdsElem.id)) {
                        classCodes.add(gdsElem.value);
                    } else if ("01ZT".equals(gdsElem.id)) {
                        int seatCount = 0;
                        try {
                            seatCount = Integer.parseInt(gdsElem.value);
                        } catch (NumberFormatException ex) {

                        }
                        String seatClass = classCodes.pop();
                        if (seatCount > 0 && seatClass != null) segment.getClasses().put(seatClass, seatCount);
                    }
                }
//                retval.add(segment);
            }
        }

//        System.out.println(pnr.toString());
        return pnr;
    }
}
