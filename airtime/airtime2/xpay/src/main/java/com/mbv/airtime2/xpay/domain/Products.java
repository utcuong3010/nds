package com.mbv.airtime2.xpay.domain;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

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
public class Products {
	public Products() {

	}

	public Products(String xml) {
		// product r = JAXB.unmarshal(xml, product.class);

		XMLStreamReader xmlReader = readXMLFromString(xml);

		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(Products.class);
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
		Products r = null;
		try {
			r = (Products) jaxbUnmarshaller.unmarshal(xmlReader);

			product = r.product;

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@XmlElement
	public ArrayList<Product> product = new ArrayList<Product>();

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

	public String ToXmlString() {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Products.class);
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
