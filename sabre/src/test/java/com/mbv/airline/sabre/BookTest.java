package com.mbv.airline.sabre;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mbv.airline.common.email.SenderManager;
import com.mbv.airline.common.info.AgentInfo;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.ContactInfo;
import com.mbv.airline.common.info.Gender;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.CreatePNR;
import com.mbv.airline.sabre.commands.DesignatePrinters;
import com.mbv.airline.sabre.commands.result.PNR;
import com.mbv.airline.sabre.commands.Pricing;
import com.mbv.airline.sabre.commands.result.PricingResult;
import com.mbv.airline.sabre.commands.result.Result;
import com.mbv.airline.sabre.commands.SignIn;
import com.mbv.airline.sabre.commands.SignOut;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class BookTest {
	private static SenderManager senderManager;
    public static void main(String[] args) throws ParseException{
        TerminalConfig config = new TerminalConfig();
        config.setUsername("6274");
        config.setPassword("MTLIVI10");
        config.setAirlineCode("AUF");
        config.setPseudoCityCode("opk");
        config.setHardcopyPrinter("4CB4B6");
        config.setTicketPrinter("4CB4B7");
        config.setPrintRoutine("GF");
        config.setStationNumber("37983061");
        Terminal terminal = new Terminal(config);

        if (terminal.open() == false) {
            System.out.println("Connection Error");
            return;
        }
        Result result = (new SignIn(senderManager)).execute(terminal);
        if (result.getStatus() != Code.SUCCESS) {
            System.out.println("Sign-in Error");
            return;
        }

        result = (new DesignatePrinters()).execute(terminal);
        if (result.getStatus() != Code.SUCCESS) {
            System.out.println("Printer Error");
            return;
        }

        AirItinerary itinerary = new AirItinerary();

        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        final List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();
        fareInfos.add(new AirFareInfo() {
            {
                setVendor("VN");
                setClassCode("M");
                setFlightCode("VN237");
                setOriginCode("HAN");
                setDestinationCode("SGN");
                setDepartureDate(dateFormat.parse("2015-05-01T08:30:00.000"));
                setArrivalDate(dateFormat.parse("2015-05-08T10:30:00.000"));
                setReference("FARE1");
            }
        });
//		fareInfos.add(new AirFareInfo() {
//			{
//				setVendor("VN");
//				setClassCode("J");
//				setFlightCode("VN232");
//				setOriginCode("SGN");
//				setDestinationCode("HAN");
//				setDepartureDate(dateFormat.parse("2014-04-04T08:30:00.000"));
//				setArrivalDate(dateFormat.parse("2014-02-08T10:30:00.000"));
//				setReference("FARE2");
//			}
//		});
        List<AirPassengerInfo> passengerInfos = new ArrayList<AirPassengerInfo>();
//		passengerInfos.add(new AirPassengerInfo() {
//			{
//				setPassengerType(AirPassengerType.ADT);
//				setFirstName("Nguyen");
//				setLastName("Minh Tuan");
//				setGender(Gender.MALE);
//				setReference("PAX1");
//			}
//		});
//		passengerInfos.add(new AirPassengerInfo() {
//			{
//				setPassengerType(AirPassengerType.INF);
//				setFirstName("Nguyen");
//				setLastName("Huyen Anh");
//				setGender(Gender.FEMALE);
//				setAccompaniedBy("PAX1");
//				setBirthDate(dateFormat.parse("2013-09-04T10:30:00.000"));
//			}
//		});

        passengerInfos.add(new AirPassengerInfo() {
            {
                setPassengerType(AirPassengerType.ADT);
                setFirstName("Cuong");
                setLastName("Truong");
                setGender(Gender.MALE);
                setReference("PAX1");
            }
        });

        ContactInfo contactInfo = new ContactInfo(){
            {
                setAddress("35 Kham Thien");
                setCity("Ha Noi");
                setMobile("12365984");
            }
        };

        AgentInfo agentInfo = new AgentInfo(){
            {
                setAgentId("myAgentId2");
                setUserId("tuan");
            }
        };

        itinerary.setContactInfo(contactInfo);
        itinerary.setFareInfos(fareInfos);
        itinerary.setPassengerInfos(passengerInfos);
        itinerary.setAgentInfo(agentInfo);
        Command cmd = new CreatePNR(itinerary);


        PNR pnr = (PNR) cmd.execute(terminal);
        System.out.println(pnr);

//        cmd = new Pricing(null, null);
//        PricingResult pricing = (PricingResult) cmd.execute(terminal);
//        System.out.println(pricing);
//        new SignOut().execute(terminal);
        terminal.close();
    }
}
