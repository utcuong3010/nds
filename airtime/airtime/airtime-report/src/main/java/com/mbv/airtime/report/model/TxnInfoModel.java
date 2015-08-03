package com.mbv.airtime.report.model;

import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.digester.Digester;

public class TxnInfoModel {
	
	private String fromDate;
	private String toDate;
    private String atTxnId;
    private String amount;
    private String messageId;
    private String msisdn;
    private String telcoId;
    private String connType;
    private String txnStatus;
    private String errorCode;
    private String channelId;
    private String txnId;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getAtTxnId() {
		return atTxnId;
	}
	public void setAtTxnId(String atTxnId) {
		this.atTxnId = atTxnId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getTelcoId() {
		return telcoId;
	}
	public void setTelcoId(String telcoId) {
		this.telcoId = telcoId;
	}
	public String getConnType() {
		return connType;
	}
	public void setConnType(String connType) {
		this.connType = connType;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	@Override
	public String toString() {
		return "TxnInfoModel [amount=" + amount + ", atTxnId=" + atTxnId
				+ ", channelId=" + channelId + ", connType=" + connType
				+ ", errorCode=" + errorCode + ", fromDate=" + fromDate
				+ ", messageId=" + messageId + ", msisdn=" + msisdn
				+ ", telcoId=" + telcoId + ", toDate=" + toDate + ", txnId="
				+ txnId + ", txnStatus=" + txnStatus + "]";
	}

	public static TxnInfoModel build(String inputParams) throws Exception{
		Reader reader = new StringReader(inputParams);
		Digester digester = new Digester();
        digester.setValidating( false );
        digester.addObjectCreate( "para", TxnInfoModel.class );
        digester.addBeanPropertySetter("para/providerId", "connType" );
        digester.addBeanPropertySetter("para/messageId", "messageId" );
        digester.addBeanPropertySetter("para/channelId", "channelId" );
        digester.addBeanPropertySetter("para/txnId", "txnId" );
        digester.addBeanPropertySetter("para/telcoId", "telcoId" );
        digester.addBeanPropertySetter("para/atTxnId", "atTxnId" );
        digester.addBeanPropertySetter("para/msisdn", "msisdn" );
        digester.addBeanPropertySetter("para/amount", "amount" );
        digester.addBeanPropertySetter("para/txnStatus", "txnStatus" );
        digester.addBeanPropertySetter("para/date.from", "fromDate" );
        digester.addBeanPropertySetter("para/date.to", "toDate" );
        
        return (TxnInfoModel) digester.parse(reader);
	}
   
}
