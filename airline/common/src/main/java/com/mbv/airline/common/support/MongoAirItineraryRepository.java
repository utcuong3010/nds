package com.mbv.airline.common.support;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mbv.airline.common.info.AgentInfo;
import com.mbv.airline.common.info.AirExtraService;
import com.mbv.airline.common.info.AirFareInfo;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.AirPassengerInfo;
import com.mbv.airline.common.info.AirTicketingInfo;
import com.mbv.airline.common.info.ContactInfo;

@Component("airItineraryRepository")
public class MongoAirItineraryRepository implements AirItineraryRepository {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String COLLECTION_NAME = "AirItinerary";
	private MongoTemplate mongoTemplate;

	@Autowired
	@Resource(name = "mongoTemplate")
	public void setMongoTemplate(MongoTemplate template) {
		mongoTemplate = template;
	}

	public void add(AirItinerary itinerary) throws Exception {
		try {
			mongoTemplate.insert(itinerary, COLLECTION_NAME);
		} catch (Exception ex) {
			logger.error("SYS_INTERNAL_ERROR", ex);
			throw new Exception("SYS_INTERNAL_ERROR");
		}
	}

	public AirItinerary findById(String id) {
		try {
			return mongoTemplate.findById(id, AirItinerary.class, COLLECTION_NAME);
		} catch (Exception ex) {
			logger.error("MONGO_FINDID_ERROR", ex);
			return null;
		}
	}

	// optimistic locking by updatedDate
	public void update(AirItinerary itinerary) throws Exception {
		try {
			AirTicketingInfo ticketingInfo = itinerary.getTicketingInfo();
			Date oldDate = ticketingInfo.getUpdatedDate();
			ticketingInfo.setUpdatedDate(new Date());

			Criteria criteria = Criteria.where("_id").is(itinerary.getId()).and("ticketingInfo.updatedDate")
					.is(oldDate);

			Query query = new Query(criteria);
			Update update = new Update().set("ticketingInfo", ticketingInfo);

			if (mongoTemplate.updateFirst(query, update, COLLECTION_NAME).getN() != 1)
				throw new Exception("UPDATE_FAILED");

		} catch (Exception ex) {
			throw new Exception("MONGO UPDATE ERROR", ex);
		}
	}

	public void updateBookingInfo(AirItinerary itinerary) throws Exception {
		try {
			AgentInfo agentInfo = itinerary.getAgentInfo();
			ContactInfo contactInfo = itinerary.getContactInfo();
			AirTicketingInfo ticketingInfo = itinerary.getTicketingInfo();
			List<AirPassengerInfo> passengerInfos = itinerary.getPassengerInfos();
			List<AirFareInfo> fareInfos = itinerary.getFareInfos();
			List<AirExtraService> extraServices = itinerary.getExtraServices();

			Criteria criteria = Criteria.where("_id").is(itinerary.getId());

			Query query = new Query(criteria);
			Update update = new Update().set("agentInfo", agentInfo).set("ticketingInfo", ticketingInfo)
					.set("fareInfos", fareInfos).set("passengerInfos", passengerInfos).set("contactInfo", contactInfo)
					.set("extraServices", extraServices);

			if (mongoTemplate.updateFirst(query, update, COLLECTION_NAME).getN() != 1)
				throw new Exception("UPDATE_FAILED");

		} catch (Exception ex) {
			throw new Exception("MONGO UPDATE BOOKING ERROR", ex);
		}
	}

	public List<AirItinerary> findByFare(AirFareInfo fareInfo) {
		// mongoTemplate.find(query, entityClass, collectionName)
		// vendor
		// originCode
		// destinationCode
		// departureDate
		// flightCode
		Query query = new Query();
		Criteria fareCriteria = Criteria.where("vendor").is(fareInfo.getVendor()).and("classCode")
				.is(fareInfo.getClassCode()).and("flightCode").is(fareInfo.getFlightCode()).and("departureDate")
				.is(fareInfo.getDepartureDate());
		query.addCriteria(Criteria.where("fareInfos").elemMatch(fareCriteria));
		// query.addCriteria(Criteria.where("fareInfos").elemMatch(Criteria.where("vendor").is("VN")));
		// query.addCriteria(Criteria.where("fareInfos").elemMatch(Criteria.where("flightCode").is("221")));
		// query.addCriteria(Criteria.where("ticketingInfo.status").is(AirTicketingStatus.BOOKED));

		// query.addCriteria(Criteria.where("fareInfos").elemMatch(Criteria.where("departureDate").is(fareInfo.getDepartureDate())));
		return mongoTemplate.find(query, AirItinerary.class, COLLECTION_NAME);
		// return null;
	}

	public AirItinerary findByReservationCode(AirTicketingInfo ticketingInfo) {
		Query query = new Query();
		query.addCriteria(Criteria.where("ticketingInfo.reservationCode").is(ticketingInfo.getReservationCode()));
		return mongoTemplate.findOne(query, AirItinerary.class, COLLECTION_NAME);
	}

	@Override
	public void delete(AirItinerary itinerary) throws Exception {
		try {
			mongoTemplate.remove(new Query(Criteria.where("id").is(itinerary.getId())), COLLECTION_NAME);
		} catch (Exception ex) {
			logger.error("CAN NOT REMOVE ITINERARY HAVE ID: " + itinerary.getId());
			throw ex;
		}
		
	}
	
	@Override
	public List<AirItinerary> findByDate(Date dateStart, Date dateEnd)
			throws Exception {
		Query query = new Query();		
		query.addCriteria(Criteria.where("ticketingInfo.createdDate").gte(dateStart).lte(dateEnd));
		return mongoTemplate.find(query, AirItinerary.class, COLLECTION_NAME);
	}
	
	@Override
	public List<AirItinerary> findByStatus(String status) {
		Date toDate = new Date();
		@SuppressWarnings("deprecation")
		Date fromDate = new Date(toDate.getYear(), toDate.getMonth(), toDate.getDate() - 14);
		Query query = new Query();
		query.addCriteria(Criteria.where("ticketingInfo.status").is(status).and("ticketingInfo.updatedDate")
				.lte(toDate).gte(fromDate));
		return mongoTemplate.find(query, AirItinerary.class, COLLECTION_NAME);
	}

}
