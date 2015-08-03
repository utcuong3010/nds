package com.mbv.bp.common.model;

import java.util.Date;

public class ViettelRequest {
	private String msisdn;
	private String amount;
	private String trace;
	private Date txnDate;	
	private Long firstDelivery;
	private Long lastDelivery;
	private String responseCode=null;
	private Date responseDate=null;
	private String requestMessage;
	private String responseMessage;
	private String txnId;
	private String requestType;
	
	
	public Long getFirstDelivery() {
		return firstDelivery;
	}
	public void setFirstDelivery(Long firstDelivery) {
		this.firstDelivery = firstDelivery;
	}
	public Long getLastDelivery() {
		return lastDelivery;
	}
	public void setLastDelivery(Long lastDelivery) {
		this.lastDelivery = lastDelivery;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTrace() {
		return trace;
	}
	public void setTrace(String trace) {
		this.trace = trace;
	}
	public Date getTxnDate() {
		return txnDate;
	}
	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	public Date getResponseDate() {
		return responseDate;
	}
	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}
	public ViettelRequest clone(){
		ViettelRequest request=new ViettelRequest();
		request.setMsisdn(this.msisdn);
		request.setAmount(this.amount);
		request.setTrace(this.trace);
		request.setTxnDate(this.txnDate);	
		request.setFirstDelivery(this.firstDelivery);
		request.setLastDelivery(this.lastDelivery);
		request.setRequestMessage(this.requestMessage);
		request.setResponseMessage(this.responseMessage);
		request.setTxnId(this.txnId);
		request.setRequestType(this.requestType);
		request.setResponseCode(this.responseCode);
		request.setResponseDate(this.responseDate);
		return request;
	}
	
	
	@Override
	public String toString() {
		return "ViettelRequest [amount=" + amount + ", firstDelivery="
				+ firstDelivery + ", lastDelivery=" + lastDelivery
				+ ", msisdn=" + msisdn + ", requestMessage=" + requestMessage
				+ ", requestType=" + requestType + ", responseCode="
				+ responseCode + ", responseDate=" + responseDate
				+ ", responseMessage=" + responseMessage + ", trace=" + trace
				+ ", txnDate=" + txnDate + ", txnId=" + txnId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((firstDelivery == null) ? 0 : firstDelivery.hashCode());
		result = prime * result
				+ ((lastDelivery == null) ? 0 : lastDelivery.hashCode());
		result = prime * result + ((msisdn == null) ? 0 : msisdn.hashCode());
		result = prime * result
				+ ((requestMessage == null) ? 0 : requestMessage.hashCode());
		result = prime * result
				+ ((requestType == null) ? 0 : requestType.hashCode());
		result = prime * result
				+ ((responseCode == null) ? 0 : responseCode.hashCode());
		result = prime * result
				+ ((responseDate == null) ? 0 : responseDate.hashCode());
		result = prime * result
				+ ((responseMessage == null) ? 0 : responseMessage.hashCode());
		result = prime * result + ((trace == null) ? 0 : trace.hashCode());
		result = prime * result + ((txnDate == null) ? 0 : txnDate.hashCode());
		result = prime * result + ((txnId == null) ? 0 : txnId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViettelRequest other = (ViettelRequest) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (firstDelivery == null) {
			if (other.firstDelivery != null)
				return false;
		} else if (!firstDelivery.equals(other.firstDelivery))
			return false;
		if (lastDelivery == null) {
			if (other.lastDelivery != null)
				return false;
		} else if (!lastDelivery.equals(other.lastDelivery))
			return false;
		if (msisdn == null) {
			if (other.msisdn != null)
				return false;
		} else if (!msisdn.equals(other.msisdn))
			return false;
		if (requestMessage == null) {
			if (other.requestMessage != null)
				return false;
		} else if (!requestMessage.equals(other.requestMessage))
			return false;
		if (requestType == null) {
			if (other.requestType != null)
				return false;
		} else if (!requestType.equals(other.requestType))
			return false;
		if (responseCode == null) {
			if (other.responseCode != null)
				return false;
		} else if (!responseCode.equals(other.responseCode))
			return false;
		if (responseDate == null) {
			if (other.responseDate != null)
				return false;
		} else if (!responseDate.equals(other.responseDate))
			return false;
		if (responseMessage == null) {
			if (other.responseMessage != null)
				return false;
		} else if (!responseMessage.equals(other.responseMessage))
			return false;
		if (trace == null) {
			if (other.trace != null)
				return false;
		} else if (!trace.equals(other.trace))
			return false;
		if (txnDate == null) {
			if (other.txnDate != null)
				return false;
		} else if (!txnDate.equals(other.txnDate))
			return false;
		if (txnId == null) {
			if (other.txnId != null)
				return false;
		} else if (!txnId.equals(other.txnId))
			return false;
		return true;
	}
	
	
	
}
