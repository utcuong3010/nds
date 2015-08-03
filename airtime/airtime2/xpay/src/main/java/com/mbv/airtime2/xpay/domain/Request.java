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

@XmlRootElement(name = "request")
public class Request {
	@XmlElement(name = "reqtype")
	public String reqtype;

	@XmlElement(name = "appid")
	public String appid;

	@XmlElement(name = "version")
	public String version;

	@XmlElement(name = "partnercode")
	public String partnercode;

	@XmlElement(name = "requestid")
	public String requestid;

	@XmlElement(name = "username")
	public String username;

	@XmlElement(name = "password")
	public String password;

	@XmlElement(name = "sign")
	public String sign;

	@XmlElement(name = "sessionid")
	public String sessionid;

	@XmlElement(name = "new_pass")
	public String new_pass;

	@XmlElement(name = "product_type")
	public String product_type;

	@XmlElement(name = "topup_account")
	public String topup_account;

	@XmlElement(name = "amount")
	public String amount;

	@XmlElement(name = "quantity")
	public String quantity;

	@XmlElement(name = "trace_number")
	public String trace_number;

	@XmlElement(name = "fromdate")
	public String fromdate;

	@XmlElement(name = "todate")
	public String todate;

	public Request() {

	}

	public Request(String xml) throws SAXException, IOException, ParserConfigurationException,
			JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
		InputSource is = new InputSource(new StringReader(xml));
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Request r = (Request) jaxbUnmarshaller.unmarshal(is);

		this.reqtype = r.reqtype;
		this.appid = r.appid;
		this.version = r.version;
		this.partnercode = r.partnercode;
		this.requestid = r.requestid;
		this.username = r.username;
		this.password = r.password;
		this.sign = r.sign;
		this.sessionid = r.sessionid;
		this.new_pass = r.new_pass;
		this.product_type = r.product_type;
		this.topup_account = r.topup_account;
		this.amount = r.amount;
		this.quantity = r.quantity;
		this.trace_number = r.trace_number;
		this.fromdate = r.fromdate;
		this.todate = r.todate;
	}

	public String toXMLString() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Request.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter sw = new StringWriter();
		m.marshal(this, sw);

		return sw.toString();
	}
}
