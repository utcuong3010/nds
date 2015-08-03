package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class ReservedTxnFilter extends ReservedTxn{
	
	private Date fromDate;
	private Date toDate;
	private Integer startRecord;
	private Integer recordSize;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public Integer getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(Integer startRecord) {
		this.startRecord = startRecord;
	}
	public Integer getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}
	

	
}