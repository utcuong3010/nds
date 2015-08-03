package com.mbv.airline.sabre.actors.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import scala.concurrent.duration.Duration;
import akka.actor.UntypedActor;
import akka.actor.ReceiveTimeout;

import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirFarePriceInfo;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirFarePriceOption;
import com.mbv.airline.common.info.AirPassengerType;
import com.mbv.airline.common.info.AirPassengerTypePrice;
import com.mbv.airline.common.support.MongoAirFarePriceCache;
import com.mbv.airline.sabre.SpringExt;
import com.mbv.airline.sabre.commands.Availability;
import com.mbv.airline.sabre.commands.result.AvailabilityResult;
import com.mbv.airline.sabre.commands.Command;
import com.mbv.airline.sabre.commands.FareQuote;
import com.mbv.airline.sabre.commands.result.FareQuoteResult;
import com.mbv.airline.sabre.commands.result.FlightSegment;
import com.mbv.airline.sabre.commands.result.QuoteInfo;
import com.mbv.airline.sabre.commands.result.Result.Code;

public class SearchAgent extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private AvailabilityResult avResult = null;
	private FareQuoteResult fqResult = null;

	private AvailabilityResult filteredAvResult = null;

	private SearchItineraryCmd cmd;
	private ApplicationContext appCtx;
	private MongoAirFarePriceCache cache;
	private AirFarePriceInfos priceInfos;

	private int numOfChild;
	private int numOfInfant;
	private int numOfSeat;

	private LinkedList<Command> cmdList;
	private LinkedHashMap<String, String> bkClassMap;
	private HashMap<String, Long> adtPriceList;
	private HashMap<String, Long> chdPriceList;
	private HashMap<String, Long> infPriceList;

	/*
	 * 
	 */
	public SearchAgent(SearchItineraryCmd cmd, MongoAirFarePriceCache cache) {
		this.cmd = cmd;
		this.cache = cache;
		this.priceInfos = new AirFarePriceInfos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see akka.actor.UntypedActor#preStart()
	 */
	public void preStart() {
		logger.debug(self().path() + " started");

		appCtx = SpringExt.Extention.get(context().system()).getAppCtx();
		bkClassMap = (LinkedHashMap<String, String>) appCtx
				.getBean("bookingClassMap");

		// Create a list of av and fq commands
		createCommandList();

		// Invoke Terminal actor to handle each command
		context().actorSelection("/user/SabreTerminals").tell(cmdList.poll(),
				getSelf());

		context().setReceiveTimeout(Duration.create(15, TimeUnit.SECONDS));
	}

	public void postRestart() {
		context().stop(getSelf());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see akka.actor.UntypedActor#postStop()
	 */
	public void postStop() {
		logger.debug(self().path() + " stopped");
	}

	@Override
	public void onReceive(Object message) throws Exception {

		try {

			StringBuilder msgLog = new StringBuilder();
			msgLog.append("[" + self().path() + "] ");
			msgLog.append("[" + getSender().path() + "] ");
			msgLog.append("[Message : " + message.getClass().getName() + "]"
					+ "\n");
			msgLog.append(message.toString());

			logger.debug(msgLog.toString());

			if (message instanceof AvailabilityResult) {
				logger.info("Receive Availability Result from VNA");

				this.avResult = (AvailabilityResult) message;

				if (this.avResult.getStatus() != Code.SUCCESS)
					context().stop(getSelf());

				filteredAvResult = filterAvailability(avResult, bkClassMap,
						numOfSeat);

				if (filteredAvResult.size() == 0)
					cmdList.poll();

			} else if (message instanceof FareQuoteResult) {
				logger.info("Receive FareQuote Result from VNA");
				this.fqResult = (FareQuoteResult) message;

				if (this.fqResult.getStatus() != Code.SUCCESS)
					context().stop(getSelf());

				// Make price list based on paxType
				this.adtPriceList = makePriceList(fqResult, "ADT");

				if (numOfChild > 0)
					this.chdPriceList = makePriceList(fqResult, "CNN");

				if (numOfInfant > 0)
					this.infPriceList = makePriceList(fqResult, "INF");

				// Filter avalabiltiy by selecting the cheapest price
				filteredAvResult = filterAvailabilityByPrice(filteredAvResult,
						bkClassMap, adtPriceList);

				// Create air fare price info list
				priceInfos.addAll(createFarePriceInfoList(filteredAvResult,
						adtPriceList));

			} else if (message instanceof ReceiveTimeout) {
				logger.info("Sabre search Receive Timeout");
				context().stop(getSelf());
			}

			// Handle rest of commands
			if (cmdList.size() != 0) {
				context().actorSelection("/user/SabreTerminals").tell(
						cmdList.poll(), getSelf());

			} else {
				// Save to cache
				if (priceInfos.size() != 0) {
					cache.update(cmd.toHashString(), priceInfos);
				}

				// Stop child actor
				context().setReceiveTimeout(Duration.Undefined());
				context().stop(getSelf());
			}

		} catch (Exception e) {
			logger.error("Search Agent Error : ", e);
		}

	}

	/*
	 * Create a list of commands
	 */
	private void createCommandList() {
		cmdList = new LinkedList<Command>();

		this.numOfChild = cmd.getPassengerQuantity(AirPassengerType.CHD);
		this.numOfInfant = cmd.getPassengerQuantity(AirPassengerType.INF);
		this.numOfSeat = cmd.getPassengerQuantity(AirPassengerType.ADT)
				+ numOfChild;

		for (AirFareInfo fareInfo : cmd.getOriginDestinationInfos()) {
			DateTime dtDepDate = new DateTime(fareInfo.getDepartureDate());

			Availability av = new Availability(fareInfo.getOriginCode(),
					fareInfo.getDestinationCode(), dtDepDate);

			FareQuote fq = new FareQuote(fareInfo.getOriginCode()
					+ fareInfo.getDestinationCode(), av.getDepartureDate(),
					this.numOfChild > 0 ? true : false,
					this.numOfInfant > 0 ? true : false);

			cmdList.add(av);
			cmdList.add(fq);

		}
	}

	/*
	 * Create AirFarePriceInfo list
	 */
	private AirFarePriceInfos createFarePriceInfoList(
			AvailabilityResult avResult, HashMap<String, Long> adtPriceList) {
		AirFarePriceInfos priceInfoList = new AirFarePriceInfos();

		Iterator<FlightSegment> iterator = avResult.iterator();

		while (iterator.hasNext()) {
			// save special vendor
			AirFarePriceInfo priceInfo = createFarePriceInfo(iterator.next(),
					adtPriceList);
			// if (!priceInfo.getFareInfo().getVendor().equals("VN")) {
			// try {
			// ObjectMapper mapper = new ObjectMapper();
			// File f = new File(fileSpecialVendor);
			// if (!f.exists())
			// f.createNewFile();
			//
			// PrintWriter out = new PrintWriter(new BufferedWriter(new
			// FileWriter(f.getAbsolutePath(), true)));
			// out.println(mapper.writeValueAsString(priceInfo));
			// out.println();
			// out.close();
			//
			// } catch (Exception ex) {
			// ex.printStackTrace();
			// }
			// }
			// filter vendor VN
			if (priceInfo.getFareInfo().getVendor().equals("VN")) {
				priceInfoList.add(priceInfo);
			}
			logger.debug("Receive Search Result from VNA: "
					+ priceInfo.toString());
		}

		return priceInfoList;
	}

	/*
	 * Create AirFarePriceInfo based price list
	 */
	private AirFarePriceInfo createFarePriceInfo(FlightSegment segment,
			HashMap<String, Long> adtPriceList) {
		// Create AirFareInfo
		AirFareInfo fareInfo = new AirFareInfo();
		fareInfo.setVendor(segment.getCarrierCode());
		fareInfo.setOriginCode(segment.getDepartureCode());
		fareInfo.setDestinationCode(segment.getArrivalCode());
		fareInfo.setDepartureDate(segment.getDepartureTime().toDate());
		fareInfo.setArrivalDate(segment.getArrivalTime().toDate());
		fareInfo.setFlightCode(segment.getCarrierCode()
				+ segment.getFlightCode().trim());

		// Initialize PriceOption List
		List<AirFarePriceOption> priceOptionList = new ArrayList<AirFarePriceOption>();

		// Handle each flight segment to create price option list
		for (Entry<String, Integer> entry : segment.getClasses().entrySet()) {
			if (adtPriceList.get(entry.getKey()) == null)
				continue;

			// Create PriceDetail
			List<AirPassengerTypePrice> priceDetail = new ArrayList<AirPassengerTypePrice>();

			priceDetail.add(new AirPassengerTypePrice(AirPassengerType.ADT,
					adtPriceList.get(entry.getKey())));

			if (numOfChild > 0)
				priceDetail.add(new AirPassengerTypePrice(AirPassengerType.CHD,
						chdPriceList.get(entry.getKey())));

			if (numOfInfant > 0)
				priceDetail.add(new AirPassengerTypePrice(AirPassengerType.INF,
						infPriceList.get(entry.getKey())));

			// Create price option
			AirFarePriceOption priceOption = new AirFarePriceOption();
			priceOption.setClassCode(entry.getKey());
			priceOption.setClassName((String) bkClassMap.get(entry.getKey()));
			priceOption.setPriceDetail(priceDetail);

			// Add price option to list
			priceOptionList.add(priceOption);
		}

		// Create Price Info
		AirFarePriceInfo priceInfo = new AirFarePriceInfo();
		priceInfo.setFareInfo(fareInfo);
		priceInfo.setPriceOptions(priceOptionList);

		return priceInfo;
	}

	/*
	 * Filter availability by selecting the min price of booking classes
	 */
	private AvailabilityResult filterAvailabilityByPrice(
			AvailabilityResult avResult, LinkedHashMap bkClassMap,
			HashMap priceList) {
		AvailabilityResult result = new AvailabilityResult(
				avResult.getFlightFrom(), avResult.getFlightTo());

		Iterator<FlightSegment> iterator = avResult.iterator();

		while (iterator.hasNext()) {
			FlightSegment segment = iterator.next();
			result.add(filterClassByPrice(segment, bkClassMap, priceList));
		}

		return result;
	}

	/*
	 * Get the min price of classes in a flight segment based on given price
	 * list
	 */
	private FlightSegment filterClassByPrice(FlightSegment segment,
			LinkedHashMap bkClassMap, HashMap<String, Long> priceList) {

		Map<String, Integer> classes = segment.getClasses();
		Map<String, String> minMap = new HashMap<String, String>();

		for (Entry<String, Integer> entry : classes.entrySet()) {
			String className = (String) bkClassMap.get(entry.getKey());
			if (!minMap.containsKey(className)) {
				minMap.put(className, entry.getKey());
			} else {
				Long tmpPrice = priceList.get(entry.getKey());
				Long minPrice = priceList.get(minMap.get(className));

				if (tmpPrice != null
						&& minPrice != null
						&& (priceList.get(entry.getKey()) < priceList
								.get(minMap.get(className)))) {
					minMap.put(className, entry.getKey());
				}
			}
		}

		Map<String, Integer> newClasses = new HashMap<String, Integer>();
		for (Entry<String, String> entry : minMap.entrySet()) {
			newClasses.put(entry.getValue(), classes.get(entry.getValue()));
		}

		FlightSegment result = new FlightSegment(segment);
		result.setClasses(newClasses);

		return result;
	}

	/*
	 * Make the price list based on fare quote
	 */
	private HashMap makePriceList(FareQuoteResult fqResult, String paxType) {
		HashMap<String, Long> priceList = new HashMap<String, Long>();

		Iterator<QuoteInfo> iterator = fqResult.getQuotesByPaxType(paxType)
				.iterator();

		while (iterator.hasNext()) {
			QuoteInfo quote = iterator.next();

			if (!priceList.containsKey(quote.getBookingCode()))
				priceList.put(quote.getBookingCode(),
						quote.getOnewayFareAmount());
		}

		return priceList;
	}

	/*
	 * Filter availability by given booking class map and number of seat
	 */
	private AvailabilityResult filterAvailability(AvailabilityResult avResult,
			LinkedHashMap bkClassMap, int numberOfSeat) {
		AvailabilityResult result = new AvailabilityResult(
				avResult.getFlightFrom(), avResult.getFlightTo());

		if (avResult.size() != 0) {
			Iterator<FlightSegment> iterator = avResult.iterator();

			// Handle each flight segment
			while (iterator.hasNext()) {
				FlightSegment segment = iterator.next();

				// Filter departure and destination
				if (!result.getFlightFrom().equalsIgnoreCase(
						segment.getDepartureCode())
						|| !result.getFlightTo().equalsIgnoreCase(
								segment.getArrivalCode()))
					continue;

				// Filter booking classes
				Map<String, Integer> classes = new HashMap<String, Integer>();

				for (Entry<String, Integer> entry : segment.getClasses()
						.entrySet()) {
					if (!bkClassMap.containsKey(entry.getKey()))
						continue;

					if (entry.getValue() < numberOfSeat)
						continue;

					classes.put(entry.getKey(), entry.getValue());
				}

				if (classes.size() != 0) {
					FlightSegment newSegment = new FlightSegment(segment);
					newSegment.setClasses(classes);

					result.add(newSegment);
				}
			}
		}

		return result;
	}

}
