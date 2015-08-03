package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class ProviderAccountFilter extends ProviderAccount{

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
	public int getStartRecord() {
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
	@Override
	public String toString() {
		return super.toString()+ "ProviderAccountFilter [fromDate=" + fromDate + ", toDate="
				+ toDate + "]";
	}


}