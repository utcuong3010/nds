package com.mbv.airline.webapp.controller;

import java.util.Arrays;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.DateUtils;
import com.mbv.airline.common.cmd.CreateBookingCmd;
import com.mbv.airline.common.cmd.PayBookingCmd;
import com.mbv.airline.common.cmd.RetrieveBookingCmd;
import com.mbv.airline.common.cmd.SearchItineraryCmd;
import com.mbv.airline.common.info.AirBookingInfo;
import com.mbv.airline.common.info.AirFarePriceInfos;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.common.info.ItineraryUpdateCommand;
import com.mbv.airline.common.support.AirFarePriceCache;
import com.mbv.airline.common.support.AirItineraryRepository;

@Controller("airlineController")
@RequestMapping("/AirService")
public class AirlineController {

	final static Logger LOG = Logger.getLogger(AirlineController.class);

	/**
	 * time delete cache
	 */
	private int timeCache;
	/**
	 * rabbit template
	 */
	private RabbitTemplate rabbitTemplate;
	/**
	 * CRUD cache data on mongo repo
	 */
	private AirFarePriceCache farePriceCache;
	/**
	 * CRUD itenerary data on mongo repo
	 */
	private AirItineraryRepository itineraryRepository;
		
	@Value("${timeWaitDeleteCache}")
	public void setTimeCache(int timeCache) {
		this.timeCache = timeCache;
	}

	@Autowired
	@Resource(name = "rabbitTemplate")
	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Autowired
	@Resource(name = "airFarePriceCache")
	public void setFarePriceCache(AirFarePriceCache farePriceCache) {
		this.farePriceCache = farePriceCache;
	}

	@Autowired
	@Resource(name = "airItineraryRepository")
	public void setItineraryRepository(AirItineraryRepository itineraryRepository) {
		this.itineraryRepository = itineraryRepository;
	}

	@RequestMapping(value = "test", method = RequestMethod.GET)
	@ResponseBody
	public String Test() throws Exception {
		return "Maka Faka";
	}

	/**
	 * get fare info with id's fare
	 *
	 * @param Id
	 * @return
	 * @throws FareNotFoundException
	 */

	@RequestMapping(value = "fare/{Id}", method = RequestMethod.GET)
	@ResponseBody
	public AirFarePriceInfos getFares(@PathVariable String Id) throws FareNotFoundException {
		AirFarePriceInfos result = farePriceCache.find(Id);
		if (result == null)
			throw new FareNotFoundException();
		return result;
	}

	/**
	 * search fares check if the result in cached return or push into queue
	 *
	 * @param request
	 * @return
	 * @throws InterruptedException
	 */
	@RequestMapping(value = "fare", method = RequestMethod.POST)
	public String searchFares(@RequestBody SearchItineraryCmd request) throws InterruptedException {		
		String id = request.toHashString();
		String routingKey = request.getOriginDestinationInfos().get(0).getVendor();
		AirFarePriceInfos result = farePriceCache.find(id);
		if (result != null) {
			boolean resultCompareDate = compareDate(id, timeCache);
			if (resultCompareDate) {
				farePriceCache.deleteId(id);
				result = null;
			}
		}
		if (result == null) {
			rabbitTemplate.convertAndSend("AirService", routingKey, request, new MessagePostProcessor() {
				public Message postProcessMessage(Message message) throws AmqpException {
					LOG.info("RabbitMQ send success search request: " + message.toString());
					return message;
				}
			});
		}
		LOG.info("Data Received(Search): " + id + "   " + request.toString());
		return "redirect:/AirService/fare/" + id;
	}

	/**
	 * @param Id
	 * @return
	 */
	@RequestMapping(value = "itinerary/{Id}", method = RequestMethod.GET)
	@ResponseBody
	public AirItinerary getItinerary(@PathVariable("Id") String Id) {
		return itineraryRepository.findById(Id);
	}

	/**
	 * retrieve status of booking info
	 *
	 * @param Id
	 * @return
	 */
	@RequestMapping(value = "itinerary/{Id}/status", method = RequestMethod.GET)
	@ResponseBody
	public AirTicketingInfo getTicketingInfo(@PathVariable("Id") String Id) {
		AirItinerary itinerary = itineraryRepository.findById(Id);
		if (itinerary != null)
			return itinerary.getTicketingInfo();
		return null;
	}

	/**
	 * booking with the flight info
	 *
	 * @param bookingInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "itinerary", method = RequestMethod.POST)
	public String book(@RequestBody AirBookingInfo bookingInfo) throws Exception {
		AirItinerary itinerary = AirItinerary.create(bookingInfo);
		itineraryRepository.add(itinerary);
		String routingKey = bookingInfo.getFareInfos().get(0).getVendor();
		CreateBookingCmd command = new CreateBookingCmd();
		command.setId(itinerary.getId());
		rabbitTemplate.convertAndSend("AirService", routingKey, command, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				LOG.info("RabbitMQ send success booking request: " + message.toString());
				return message;
			}
		});
		LOG.info("Data Recieved(Book): " + bookingInfo.toString());
		return "redirect:/AirService/itinerary/" + itinerary.getId();
	}

	/**
	 * payment with the booking info
	 *
	 * @param Id
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "itinerary/{Id}", method = RequestMethod.POST)
	public ResponseEntity<String> pay(@PathVariable("Id") String Id, @RequestBody ItineraryUpdateCommand command)
			throws Exception {
		AirItinerary itinerary = itineraryRepository.findById(Id);
		if (itinerary == null)
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		AirTicketingInfo info = itinerary.getTicketingInfo();
		Date expired = info.getExpiredDate();
		if (expired != null && DateUtils.compareDate(expired)) {
			if ("BUY".equals(command.getCommand())) {
				if (Arrays.asList(BookingStatus.BOOK_SUCCESS, BookingStatus.BUY_ONLY).contains(
						info.getStatus())) {
					info.setStatus(BookingStatus.BUY_PENDING);
					itineraryRepository.update(itinerary);
					String routingKey = itinerary.getFareInfos().get(0).getVendor();
					rabbitTemplate.convertAndSend("AirService", routingKey, new PayBookingCmd(Id));

					rabbitTemplate.convertAndSend("AirService", "CT", itinerary);
					LOG.info("RabbitMQ send success payment request: " + command.toString());
				}
			} else if ("CANCEL".equals(command.getCommand())) {
				if (info.getStatus() == BookingStatus.BOOK_SUCCESS) {
					info.setStatus(BookingStatus.BOOK_CANCELED);
					itineraryRepository.update(itinerary);
				}
			}
		} else {
			info.setStatus(BookingStatus.BOOK_EXPIRED);
			info.setDescription("BOOK_EXPIRED_DATE");
			itineraryRepository.update(itinerary);

		}
		LOG.info("Recieved(Pay): " + itinerary.toString());
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}

	/**
	 * retrieve booking info with the code
	 *
	 * @param bookingInfo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getItinerary", method = RequestMethod.POST)
	public String getBookId(@RequestBody AirItinerary bookingInfo) throws Exception {
		AirItinerary itinerary = itineraryRepository.findByReservationCode(bookingInfo.getTicketingInfo());
		if (itinerary != null) 
			itineraryRepository.delete(itinerary);

		itinerary = AirItinerary.create(bookingInfo);
		itineraryRepository.add(itinerary);
		String routingKey = itinerary.getFareInfos().get(0).getVendor();
		RetrieveBookingCmd command = new RetrieveBookingCmd();
		command.setId(itinerary.getId());
		rabbitTemplate.convertAndSend("AirService", routingKey, command, new MessagePostProcessor() {
			public Message postProcessMessage(Message message) throws AmqpException {
				LOG.info("RabbitMQ send success booking request: " + message.toString());
				return message;
			}
		});
		LOG.info("AirlineController: Search BookID Recieved: " + itinerary.toString());
		return "redirect:/AirService/itinerary/" + itinerary.getId();
	}

	private boolean compareDate(String id, int timeTemp) {
		Date dateMongodb = farePriceCache.findByFare(id);
		long ldateMongodb = dateMongodb.getTime();
		long lcurrentDate = new Date().getTime();
		long datebetween = lcurrentDate - ldateMongodb;
		if (datebetween > timeTemp * 60000)
			return true;
		return false;
	}

	@SuppressWarnings("serial")
	private static class FareNotFoundException extends Exception {
	}

	@ExceptionHandler(FareNotFoundException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void FareNotFound(FareNotFoundException ex) {
	}

	@SuppressWarnings("serial")
	private static class ItineraryNotFoundException extends Exception {
	}

	@ExceptionHandler(ItineraryNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void FareNotFound(ItineraryNotFoundException ex) {
	}

}
