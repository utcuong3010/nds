package com.mbv.airline.common.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mbv.airline.common.report.AirTicketReport;

@Component("airItineraryReport")
public class MongoDbAirItineraryReport implements AirItineraryReport {
	private static final String COLLECTION_NAME = "AirItineraryReport";
	private MongoTemplate mongoTemplate;

	@Autowired
	@Resource(name = "mongoTemplate")
	public void setMongoTemplate(MongoTemplate template) {
		mongoTemplate = template;
	}

	public AirTicketReport findById(String id) {
		try {
			return mongoTemplate.findById(id, AirTicketReport.class, COLLECTION_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void add(AirTicketReport ticketReport) throws Exception {
		try {
			mongoTemplate.insert(ticketReport, COLLECTION_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SYS_INTERNAL_ERROR");
		}
	}

	@Override
	public void updatePayCheckInfo(String id, String result) throws Exception {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = new Query(criteria);
		Update update = new Update().set("status", "checked").set("result", result);
		if (mongoTemplate.updateFirst(query, update, COLLECTION_NAME).getN() != 1)
			throw new Exception("UPDATE_FAILED");
	}

	@Override
	public List<AirTicketReport> findByDate(Date startdate, Date enddate) throws Exception {
		List<AirTicketReport> result = new ArrayList<AirTicketReport>();

		Criteria criteria = Criteria.where("checkedDate").gte(startdate).lte(enddate);
		Query query = new Query(criteria);

		try {
			result = mongoTemplate.find(query, AirTicketReport.class, COLLECTION_NAME);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("SYS_INTERNAL_ERROR");
		}

		return result;
	}

	@Override
	public void updatePayCheckStatus(String id, String status) throws Exception {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = new Query(criteria);
		Update update = new Update().set("status", status);
		if (mongoTemplate.updateFirst(query, update, COLLECTION_NAME).getN() != 1)
			throw new Exception("UPDATE_STATUS_FAILED");
	}
}
