package com.mbv.airline.sabre;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mbv.airline.sabre.Terminal.TerminalException;
import com.mbv.airline.sabre.commands.result.AvailabilityResult;
import com.mbv.airline.sabre.commands.result.FareQuoteResult;
import com.mbv.airline.sabre.commands.result.FlightSegment;
import com.mbv.airline.sabre.commands.result.QuoteInfo;

public class GetPriceTest {
	
	@BeforeClass
	public static void setup(){
	}
		
	@Test
	public void getPrice() throws TerminalException{
		/*
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfiguration.class);
		
		LinkedHashMap bkClassMap = (LinkedHashMap) ctx.getBean("bookingClassMap");
		
		Terminal terminal = new Terminal("4428", "mbvsc858");
		boolean result = terminal.open();
		
		Availability av = new Availability("sgn", "han", new DateTime(2014, 4, 1, 0, 0));
		AvailabilityResult avResult = av.execute(terminal);
		
		FareQuote fq = new FareQuote("sgnhan", av.getDepartureDate(), true, true);
		FareQuoteResult fqResult = fq.execute(terminal);
		
		System.out.println(avResult);
		System.out.println(fqResult);
		
		AvailabilityResult filteredAvResult = filterAvailability(avResult, bkClassMap, 0);
		
		HashMap<String,Long> adtPriceList = makePriceList(fqResult, "ADT");
		HashMap<String,Long> chdPriceList = makePriceList(fqResult, "CNN");
		HashMap<String,Long> infPriceList = makePriceList(fqResult, "INF");
		
		AvailabilityResult finalAvResult = filterAvailabilityByPrice(filteredAvResult, bkClassMap, adtPriceList);
				
		System.out.println(filteredAvResult);
		System.out.println(adtPriceList);
		System.out.println(chdPriceList);
		System.out.println(infPriceList);
		System.out.println(finalAvResult);
		
		terminal.close();
		*/
	}
	

	public AvailabilityResult filterAvailabilityByPrice(AvailabilityResult avResult, LinkedHashMap bkClassMap, HashMap priceList){
		AvailabilityResult result = new AvailabilityResult(avResult.getFlightFrom(), avResult.getFlightTo());
		
		Iterator<FlightSegment> iterator = avResult.iterator();
		
		while(iterator.hasNext()){
			FlightSegment segment = iterator.next();
			result.add(filterClassByPrice(segment, bkClassMap, priceList));
		}
		
		return result;
	}

	
	public FlightSegment filterClassByPrice(FlightSegment segment, LinkedHashMap bkClassMap, HashMap<String,Long> priceList){
		Map<String, Integer> classes = segment.getClasses();
		Map<String, String> minMap = new HashMap<String, String>();
		
		for(Entry<String, Integer> entry : classes.entrySet()){
			String className = (String)bkClassMap.get(entry.getKey());
			if(!minMap.containsKey(className)){
				minMap.put(className, entry.getKey());
			}
			else{
				if(priceList.get(entry.getKey()) < priceList.get(minMap.get(className))){
					minMap.put(className, entry.getKey());
				}
			}
		}

		Map<String, Integer> newClasses = new HashMap<String, Integer>();
		for(Entry<String,String> entry : minMap.entrySet()){
			newClasses.put(entry.getValue(), classes.get(entry.getValue()));
		}
		
		FlightSegment result = new FlightSegment(segment);
		result.setClasses(newClasses);
		
		return result;
	}
		
	
	public HashMap makePriceList(FareQuoteResult fqResult, String paxType){
		HashMap<String, Long> priceList = new HashMap<String, Long>();
		
		Iterator<QuoteInfo> iterator = fqResult.getQuotesByPaxType(paxType).iterator();
		
		while(iterator.hasNext()){
			QuoteInfo quote = iterator.next();
					
			if(!priceList.containsKey(quote.getBookingCode()))
				priceList.put(quote.getBookingCode(), quote.getOnewayFareAmount());
		}

		return priceList;
	}
	
	
	public Set<String> getAvailableClass(AvailabilityResult avResult){
		Set<String> result = new HashSet<String>();
		
		Iterator<FlightSegment> iterator = avResult.iterator();
		
		while(iterator.hasNext()){
			FlightSegment segment = iterator.next();
			for(Entry<String, Integer> entry : segment.getClasses().entrySet()){
				result.add(entry.getKey());
			}
		}
		
		return result;
	}
	
	
	public AvailabilityResult filterAvailability(AvailabilityResult avResult, LinkedHashMap bkClassMap, int numberOfSeat) {
		AvailabilityResult result = new AvailabilityResult(avResult.getFlightFrom(), avResult.getFlightTo());
		
		if (avResult.size() != 0) {
			Iterator<FlightSegment> iterator = avResult.iterator();
			
			// Handle each flight segment
			while(iterator.hasNext()){
				FlightSegment segment = iterator.next();
				Map<String, Integer> classes = new HashMap<String, Integer>();
				
				// Filter booking classes
				for(Entry<String, Integer> entry : segment.getClasses().entrySet()){
					if(!bkClassMap.containsKey(entry.getKey()))
						continue;
					
					if(entry.getValue() < numberOfSeat)
						continue;
					
					classes.put(entry.getKey(), entry.getValue());
				}
				
				if(classes.size() != 0 ){
					FlightSegment newSegment = new FlightSegment(segment);
					newSegment.setClasses(classes);
					
					result.add(newSegment);
				}
			}
		}
		
		return result;
	}
	

}
