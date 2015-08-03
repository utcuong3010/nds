package com.mbv.airtime2.xpay.domain;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

@XmlRootElement
public class Product {
	public Product() {

	}

	public Product(String xml) {
		// product r = JAXB.unmarshal(xml, product.class);

		XMLStreamReader xmlReader = readXMLFromString(xml);

		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(Product.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Unmarshaller jaxbUnmarshaller = null;
		try {
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		} catch (JAXBException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Product r = null;
		try {
			r = (Product) jaxbUnmarshaller.unmarshal(xmlReader);

			serial = r.serial;
			code = r.code;
			expdate = r.expdate;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public XMLStreamReader readXMLFromString(final String xmlContent) {
		final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		final StringReader reader = new StringReader(xmlContent);
		try {
			return inputFactory.createXMLStreamReader(reader);
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@XmlElement
	public String serial = null;

	@XmlElement
	public String code = null;

	@XmlElement
	public String expdate = null;

	public String ToXmlString() {

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Product.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

			StringWriter sw = new StringWriter();
			m.marshal(this, sw);

			return sw.toString();

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
