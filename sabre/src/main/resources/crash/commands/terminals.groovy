import org.crsh.cli.Command;
import org.crsh.cli.Usage;
import org.crsh.cli.Option;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.pattern.Patterns;
import akka.util.Timeout;
import java.util.Arrays;

import com.mbv.airline.sabre.actors.TerminalPool.Attach;
import com.mbv.airline.sabre.actors.TerminalPool.Detach;
import com.mbv.airline.sabre.actors.TerminalPool.Status;

import com.mbv.airline.sabre.commands.VoidTickets;

class terminals {
  	
  
	@Usage("Attach a terminal to the pool")
  	@Command
  	public void attach(@Required @Argument String account, @Required @Argument String username, @Required @Argument String password, @Argument String stationNumber) {
  		def actorSystem = context.attributes.beans["actorSystem"];
  		
  		def config = new Attach();
  		
  		if ("mbv" == account) {
  			config.setUsername(username);
  			config.setPassword(password);
  			config.setAirlineCode("auf");
			config.setPseudoCityCode("ouf");
			config.setHardcopyPrinter("4CB4BE");
			config.setTicketPrinter("4CB4BF");
			config.setPrintRoutine("GF");
			if (stationNumber == null|| stationNumber.isEmpty())
				config.setStationNumber("37983061");
			else
				config.setStationNumber(stationNumber);
  		}
  		
  		actorSystem.actorSelection("/user/SabreTerminals").tell(config, null);		
	}
	
	@Usage("Attach a terminal to the pool")
  	@Command
  	public void detach(@Required @Argument String username) {
  		def actorSystem = context.attributes.beans["actorSystem"];
  		actorSystem.actorSelection("/user/SabreTerminals").tell(new Detach(username), null);		
	}
	
	@Usage("Print the status of the pool")
  	@Command
	public String status() {      
    	def actorSystem = context.attributes.beans["actorSystem"];    
    	def timeout = new Timeout(Duration.create(5, "seconds"));
		def future = Patterns.ask(actorSystem.actorSelection("/user/SabreTerminals"), new Status(), timeout);
		return Await.result(future, timeout.duration());
  	}
  	
  	@Usage("Void Tickets")
  	@Command
	public String voidTicket(@Required @Argument String terminal, @Required @Argument String reservationCode,
						@Required @Argument String ticketNumbers) {  
						    
    	def actorSystem = context.attributes.beans["actorSystem"]; 
    	   
    	def timeout = new Timeout(Duration.create(30, "seconds"));
    	
    	List<String> tickets = Arrays.asList(ticketNumbers.split(','));
    	
    	def voidTickets = new VoidTickets(reservationCode, tickets);
    	
    	def future = Patterns.ask(actorSystem.actorSelection("/user/SabreTerminals/"+terminal), voidTickets, timeout);
    	
    	return Await.result(future, timeout.duration());
  	}
  	
  	/*
  	@Usage("Search flight with price")
  	@Command
	public String search(@Required @Argument String from, @Required @Argument String to, @Required @Argument String date,
		  				@Required @Argument String adult, @Required @Argument String children, @Required @Argument String infant) {      
    	
    	def actorSystem = context.attributes.beans["actorSystem"];    
    	def timeout = new Timeout(Duration.create(15, "seconds"));
    	
    	// AirFareInfo
		AirFareInfo fareInfo = new AirFareInfo();
		fareInfo.setVendor("VN");
		fareInfo.setOriginCode(from);
		fareInfo.setDestinationCode(to);
		fareInfo.setDepartureDate(new SimpleDateFormat("yyyyMMddHHmm").parse(date));

		List<AirFareInfo> fareInfoList = new ArrayList<AirFareInfo>();
		fareInfoList.add(fareInfo);

		// Passenger
		List<AirPassengerTypeQuantity> passList = new ArrayList<AirPassengerTypeQuantity>();
		passList.add(new AirPassengerTypeQuantity(AirPassengerType.ADT, adt));
		
		if(chd > 0)
			passList.add(new AirPassengerTypeQuantity(AirPassengerType.CHD, chd));
		
		if(inf > 0)
			passList.add(new AirPassengerTypeQuantity(AirPassengerType.INF, inf));

		// FarePriceCommand
		UpdateFarePriceCommand fpCmd = new UpdateFarePriceCommand();
		fpCmd.setOriginDestinationInfos(fareInfoList);
		fpCmd.setPassengerInfos(passList);
    	
		def future = Patterns.ask(actorSystem.actorSelection("/user/SearchHandlers"), fpCmd, timeout);
		
		return Await.result(future, timeout.duration());
		
  	}
  	*/

}