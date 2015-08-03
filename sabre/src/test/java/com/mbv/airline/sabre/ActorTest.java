package com.mbv.airline.sabre;

import java.io.BufferedInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AgentInfo;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirPassengerTypeQuantity;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.common.info.ContactInfo;
import com.mbv.airline.common.info.Gender;
import com.mbv.airline.sabre.actors.TerminalPool.Attach;
import com.mbv.airline.sabre.actors.agent.BookAgent;
import com.mbv.airline.sabre.actors.agent.SearchAgent;
import com.mbv.airline.sabre.commands.Availability;
import com.mbv.airline.sabre.commands.Command;

public class ActorTest {
	public static void main(String args[]) {		
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfiguration.class);
		
		ActorSystem system = ctx.getBean(ActorSystem.class);
		
		
		Attach config = new Attach();
		config.setUsername("4428");
		config.setPassword("mbvsc858");
		config.setAirlineCode("apk");
		config.setPseudoCityCode("opk");
		config.setHardcopyPrinter("4CB4B6");
		config.setTicketPrinter("4CB4B7");
		config.setPrintRoutine("GF");
		config.setStationNumber("37982254");
		system.actorSelection("/user/SabreTerminals").tell(config, null);
		
		Scanner scanner = new Scanner(new BufferedInputStream(System.in));
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();	
			
			if("exit".equalsIgnoreCase(line)) break;
			
			system.actorOf(Props.create(SearchAgentTest.class));
			//system.actorOf(Props.create(BookAgentTest.class));
		}
		ctx.close();
	}
	
	private static class BookAgentTest extends UntypedActor{
		private AirItinerary itinerary;
		
		public BookAgentTest() throws ParseException{
			itinerary = new AirItinerary();
			
			final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			final List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();
			fareInfos.add(new AirFareInfo() {
				{			
					setVendor("VN");
					setClassCode("M");
					setFlightCode("VN253");
					setOriginCode("HAN");
					setDestinationCode("SGN");
					setDepartureDate(dateFormat.parse("2014-04-01T08:30:00.000"));						
					setArrivalDate(dateFormat.parse("2014-02-08T10:30:00.000"));
					setReference("FARE1");
				}
			});	
			fareInfos.add(new AirFareInfo() {
				{			
					setVendor("VN");
					setClassCode("J");
					setFlightCode("VN232");
					setOriginCode("SGN");
					setDestinationCode("HAN");
					setDepartureDate(dateFormat.parse("2014-04-04T08:30:00.000"));						
					setArrivalDate(dateFormat.parse("2014-02-08T10:30:00.000"));
					setReference("FARE2");
				}
			});			
			List<AirPassengerInfo> passengerInfos = new ArrayList<AirPassengerInfo>();
			passengerInfos.add(new AirPassengerInfo() {
				{
					setPassengerType(AirPassengerType.ADT);
					setFirstName("Nguyen");
					setLastName("Minh Tuan");
					setGender(Gender.MALE);
					setReference("PAX1");
				}
			});
			
			passengerInfos.add(new AirPassengerInfo() {
				{
					setPassengerType(AirPassengerType.INF);
					setFirstName("Nguyen");
					setLastName("Huyen Anh");
					setGender(Gender.FEMALE);
					setAccompaniedBy("PAX1");
					setBirthDate(dateFormat.parse("2013-09-04T10:30:00.000"));
				}
			});
			
			passengerInfos.add(new AirPassengerInfo() {
				{
					setPassengerType(AirPassengerType.CHD);
					setFirstName("Nguyen");
					setLastName("Van Huy Hoang");
					setGender(Gender.MALE);
				}
			});
			
			ContactInfo contactInfo = new ContactInfo(){
				{
					setAddress("3 Kham Thien");
					setCity("Ha Noi");
					setEmail("minhtuan.nguyen@mobivi.com");
					setMobile("0973918228");
				}
			};
			
			AgentInfo agentInfo = new AgentInfo(){
				{
					setAgentId("myAgentId2");
					setUserId("tuan");
				}
			};
			
			AirTicketingInfo ticketingInfo = new AirTicketingInfo();
			itinerary.setTicketingInfo(ticketingInfo);
			itinerary.setContactInfo(contactInfo);
			itinerary.setFareInfos(fareInfos);
			itinerary.setPassengerInfos(passengerInfos);
			itinerary.setAgentInfo(agentInfo);
		}
		
		public void preStart(){
			ActorRef bookAgent = this.context().actorOf(
					Props.create(BookAgent.class, this.itinerary));
		}
		
		@Override
		public void onReceive(Object arg0) throws Exception {
			// TODO Auto-generated method stub
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(arg0));
		}
		
	}
	
	private static class SearchAgentTest extends UntypedActor{

		public void preStart(){
			// AirFareInfo
			AirFareInfo fareInfo = new AirFareInfo();
			fareInfo.setVendor("VN");
			fareInfo.setOriginCode("SGN");
			fareInfo.setDestinationCode("PXU");
			fareInfo.setDepartureDate(new Date(2014,3,20,0,0));
			
			List<AirFareInfo> fareInfoList = new ArrayList<AirFareInfo>();
			
			fareInfoList.add(fareInfo);
			
//			fareInfo = new AirFareInfo();
//			fareInfo.setVendor("VN");
//			fareInfo.setOriginCode("SGN");
//			fareInfo.setDestinationCode("HAN");
//			fareInfo.setDepartureDate(new Date(2014,3,1,0,0));
//			
//			fareInfoList.add(fareInfo);
			
			// Passenger
			List<AirPassengerTypeQuantity> passList = new ArrayList<AirPassengerTypeQuantity>();
			passList.add(new AirPassengerTypeQuantity(AirPassengerType.ADT, 1));
//			passList.add(new AirPassengerTypeQuantity(AirPassengerType.CHD, 1));
//			passList.add(new AirPassengerTypeQuantity(AirPassengerType.INF, 1));
			
			// FarePriceCommand
			SearchItineraryCmd fpCmd = new SearchItineraryCmd();
			fpCmd.setOriginDestinationInfos(fareInfoList);
			fpCmd.setPassengerInfos(passList);
			
			context().actorSelection("/user/CommandHandler").tell(fpCmd, getSelf());
			
		}
		
		@Override
		public void onReceive(Object arg0) throws Exception {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	private static class SearchTest extends UntypedActor {
		private DateTime d0;
		private int count = 30;
		
		public void preStart(){
			d0 = DateTime.now();
			DateTime date = DateTime.now();

            AirFareInfo fareInfo = new AirFareInfo();
            fareInfo.setVendor("VN");
            fareInfo.setOriginCode("SGN");
            fareInfo.setDestinationCode("HAN");

			Command command;
			
			for (int i = 0; i < count; i++) {
                fareInfo.setDepartureDate(date.toDate());
//				command = new Availability(fareInfo);
//				context().actorSelection("/user/SearchHandlers").tell(command, getSelf());
				date = date.plusDays(2);				
			}
		}

		@Override
		public void onReceive(Object message) throws Exception {
			// TODO Auto-generated method stub
			if(message instanceof String){
				DateTime d1 = DateTime.now();
				System.out.println(count + ": " + (d1.getMillis() - d0.getMillis()));
				count--;
			}
		}
	}
}
