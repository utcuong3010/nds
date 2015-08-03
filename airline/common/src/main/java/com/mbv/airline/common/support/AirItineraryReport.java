package com.mbv.airline.common.support;

import java.util.Date;
import java.util.List;

import com.mbv.airline.common.report.AirTicketReport;

public interface AirItineraryReport {

	public void add(AirTicketReport ticketReport) throws Exception;

	public AirTicketReport findById(String id);

	public void updatePayCheckInfo(String id, String result) throws Exception;

	public void updatePayCheckStatus(String id, String status) throws Exception;

	public List<AirTicketReport> findByDate(Date startdate, Date enddate) throws Exception;

}
