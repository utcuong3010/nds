package com.mbv.airtime2.xpay.domain;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@XmlRootElement(name = "response")
public class Response {
	@XmlElement(name = "reqtype")
	public String reqtype;

	@XmlElement(name = "requestid")
	public String requestid;

	@XmlElement(name = "sessionid")
	public String sessionid;

	@XmlElement(name = "username")
	public String username;

	@XmlElement(name = "state")
	public String state;

	@XmlElement(name = "message")
	public String message;

	@XmlElement(name = "accountid")
	public String accountid;

	@XmlElement(name = "balance")
	public String balance;

	@XmlElement(name = "transtime")
	public String transtime;

	@XmlElement(name = "account_name")
	public String account_name;

	@XmlElement(name = "tracenumber")
	public String tracenumber;

	@XmlElement(name = "ord")
	public String ord;

	@XmlElement(name = "partnertrace")
	public String partnertrace;

	@XmlElement(name = "product_type")
	public String product_type;

	@XmlElement(name = "amount")
	public String amount;

	@XmlElement(name = "topup_account")
	public String topup_account;

	@XmlElement(name = "topup_value")
	public String topup_value;

	@XmlElement(name = "quantity")
	public String quantity;

	@XmlElement(name = "trans_type")
	public String trans_type;

	@XmlElement(name = "url")
	public String url;

	@XmlElement(name = "products")
	Products _products;

	public Response() {

	}

	public Response(String xml) throws SAXException, IOException,
			ParserConfigurationException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
		InputSource is = new InputSource(new StringReader(xml));
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Response r = (Response) jaxbUnmarshaller.unmarshal(is);

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

	public String toXMLString() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Response.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		m.marshal(this, sw);

		return sw.toString();
	}

	public enum State {
		SUCCESS("0"), 
		ERROR("1"), 
		INVALID("2"), 
		REFUND("95"), 
		KICKOFF("98"), // require login again
		UNKNOWN("99");

		private String state;

		State(String p) {
			state = p;
		}

		public String getValue() {
			return state;
		}

	}

}
