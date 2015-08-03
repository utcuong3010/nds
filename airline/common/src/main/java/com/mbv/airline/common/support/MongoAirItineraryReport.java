package com.mbv.airline.common.support;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.info.ReportPayItinerary;

/**
 * Created by phuongvt on 3/30/15.
 */
public class MongoAirItineraryReport {
	private static final String COLLECTION_NAME = "ReportPayItinerary";
	private MongoTemplate mongoTemplate;

	@Autowired
	@Resource(name = "mongoTemplate")
	public void setMongoTemplate(MongoTemplate template) {
		mongoTemplate = template;
	}

	public void add(AirItinerary itinerary) throws Exception {
		ReportPayItinerary payItinerary = ReportPayItinerary.create(itinerary);
		add(payItinerary);
	}

	public void add(ReportPayItinerary report) throws Exception {
		try {
			report.setDateCreated(new Date());
			report.setDateUpdated(new Date());

			mongoTemplate.insert(report, COLLECTION_NAME);
		} catch (Exception ex) {
			throw new Exception("MONGO ADD DATA ERROR", ex);
		}
	}

	public ReportPayItinerary findById(String id) throws Exception {
		try {
			return mongoTemplate.findById(id, ReportPayItinerary.class,
					COLLECTION_NAME);
		} catch (Exception ex) {
			throw new Exception("MONGO FIND ID ERROR", ex);
		}
	}

	// optimistic locking by updatedDate
	public void update(ReportPayItinerary report) throws Exception {
		try {
			report.setDateUpdated(new Date());

			mongoTemplate.save(report, COLLECTION_NAME);
		} catch (Exception ex) {
			throw new Exception("MONGO UPDATE ERROR", ex);
		}
	}

	public List<ReportPayItinerary> findByDate(Date from, Date to)
			throws Exception {
		try {
			Query query = new Query(Criteria.where("dateCreated").gt(from)
					.lt(to));
			return mongoTemplate.find(query, ReportPayItinerary.class,
					COLLECTION_NAME);
		} catch (Exception ex) {
			throw new Exception("MONGO FINDBYDATE ERROR", ex);
		}
	}

	public List<ReportPayItinerary> reportPayItineraryList() throws Exception {
		try {
			Query query = new Query(Criteria.where("isChecked").is(false)
					.and("isWorking").is(false));
			return mongoTemplate.find(query, ReportPayItinerary.class,
					COLLECTION_NAME);
		} catch (Exception ex) {
			throw new Exception("MONGO REPORTPAYITINERARY ERROR", ex);
		}
	}

	public ReportPayItinerary findByReservationCode(String reservationCode)
			throws Exception {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(
					"itinerary.ticketingInfo.reservationCode").is(
					reservationCode));
			return mongoTemplate.findOne(query, ReportPayItinerary.class,
					COLLECTION_NAME);
		} catch (Exception ex) {
			throw new Exception("MONGO FIND RESERVATION CODE ERROR", ex);
		}
	}
}
