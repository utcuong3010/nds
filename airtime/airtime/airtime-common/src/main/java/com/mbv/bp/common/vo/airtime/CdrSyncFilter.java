package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class CdrSyncFilter extends CdrSync{

	private Date fromDate;
	private Date toDate;
	private Integer startRecord;
	private Integer recordSize;
	
	private Long minAtTxnId;
	private Long maxAtTxnId;
	private String filterOperation;
	
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
	
	public String getFilterOperation() {
		return filterOperation;
	}

	public void setFilterOperation(String filterOperation) {
		this.filterOperation = filterOperation;
	}

	public Long getMinAtTxnId() {
		return minAtTxnId;
	}

	public void setMinAtTxnId(Long minAtTxnId) {
		this.minAtTxnId = minAtTxnId;
	}

	public Long getMaxAtTxnId() {
		return maxAtTxnId;
	}

	public void setMaxAtTxnId(Long maxAtTxnId) {
		this.maxAtTxnId = maxAtTxnId;
	}

	@Override
	public String toString() {
		return "CdrSyncFilter [filterOperation=" + filterOperation
				+ ", fromDate=" + fromDate + ", maxAtTxnId=" + maxAtTxnId
				+ ", minAtTxnId=" + minAtTxnId + ", recordSize=" + recordSize
				+ ", startRecord=" + startRecord + ", toDate=" + toDate
				+ ", getAmount()=" + getAmount() + ", getAtTxnId()="
				+ getAtTxnId() + ", getErrorCode()=" + getErrorCode()
				+ ", getExt()=" + getExt() + ", getMessageId()=" + getMessageId() 
				+ ", getMsisdn()="
				+ getMsisdn() + ", getProviderId()=" + getProviderId()
				+ ", getStatus()=" + getStatus() + ", getTxnDate()="
				+ getTxnDate() + ", getpErrorCode()=" + getpErrorCode()
				+ ", getpPrice()=" + getpPrice() + ", getpStatus()="
				+ getpStatus() + ", getpTxnId()=" + getpTxnId()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
