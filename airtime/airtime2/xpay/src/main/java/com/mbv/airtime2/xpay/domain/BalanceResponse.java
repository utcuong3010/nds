package com.mbv.airtime2.xpay.domain;

import javax.xml.bind.JAXBException;

public class BalanceResponse extends Response{

	public BalanceResponse(Response r) {
		this.reqtype = r.reqtype;
		this.requestid = r.requestid;
		this.sessionid = r.sessionid;

		this.username = r.username;
		this.state = r.state;
		this.message = r.message;
		this.accountid = r.accountid;
		this.balance = r.balance;
		this.transtime = r.transtime;
		this.account_name = r.account_name;
		this.tracenumber = r.tracenumber;
		this.ord = r.ord;
		this.partnertrace = r.partnertrace;
		this.product_type = r.product_type;
		this.amount = r.amount;
		this.topup_account = r.topup_account;
		this.topup_value = r.topup_value;
		this.quantity = r.quantity;
		this.trans_type = r.trans_type;
		this.url = r.url;
		this._products = r._products;
	}

	@Override
	public String toString() {
		try {
			return "BalanceResponse [toXMLString()=" + toXMLString() + "]";
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
}
