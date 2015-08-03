package com.mbv.airtime2.epay.domain;

import java.util.Date;

public class AtTransaction implements java.io.Serializable {

	private String txn_id;
	private String channel_id;
	private long deleted;

	
	public String getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}

	public String getChannel_id() {
		return channel_id;
	}

	public void setChannel_id(String channel_id) {
		this.channel_id = channel_id;
	}

	public long getDeleted() {
		return deleted;
	}

	public void setDeleted(long deleted) {
		this.deleted = deleted;
	}

	private Date txn_date;
	private Long at_txn_id;
	private Date delivery_date;
	private Date response_date;
	private Integer amount;
	private String message_id;
	private String msg_type;
	private String msisdn;
	private String server_id;
	private String telco_id;
	private String conn_type;
	private String txn_type;
	private Integer time_out;
	private String status;
	private String error_code;
	private String txn_status;
	private String created_by;
	private Date created_date;
	private String updated_by;
	private Date updated_date;

	public AtTransaction() {
	}

	public Date getTxn_date() {
		return txn_date;
	}

	public void setTxn_date(Date txn_date) {
		this.txn_date = txn_date;
	}

	public Long getAt_txn_id() {
		return at_txn_id;
	}

	public void setAt_txn_id(Long at_txn_id) {
		this.at_txn_id = at_txn_id;
	}

	public Date getDelivery_date() {
		return delivery_date;
	}

	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}

	public Date getResponse_date() {
		return response_date;
	}

	public void setResponse_date(Date response_date) {
		this.response_date = response_date;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getServer_id() {
		return server_id;
	}

	public void setServer_id(String server_id) {
		this.server_id = server_id;
	}

	public String getTelco_id() {
		return telco_id;
	}

	public void setTelco_id(String telco_id) {
		this.telco_id = telco_id;
	}

	public String getConn_type() {
		return conn_type;
	}

	public void setConn_type(String conn_type) {
		this.conn_type = conn_type;
	}

	public String getTxn_type() {
		return txn_type;
	}

	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}

	public Integer getTime_out() {
		return time_out;
	}

	public void setTime_out(Integer time_out) {
		this.time_out = time_out;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getTxn_status() {
		return txn_status;
	}

	public void setTxn_status(String txn_status) {
		this.txn_status = txn_status;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
}
