package com.mbv.airtime.ws;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jibx.runtime.IMarshaller;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshaller;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.IXMLReader;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.Utility;
import org.jibx.runtime.impl.MarshallingContext;
import org.jibx.runtime.impl.UnmarshallingContext;

/**
 * <meta>
 * 		<Entry Name="key" Type="int">10</entry>
 * 		<Entry Name="key2" Type="float">5.5</entry>
 * 		<Entry Name="key3" Type="string">str</entry>
 * 		<Entry Name="key4" Type="list">
 * 			<Item type="string">test</Item>
 * 			<Item type="dateTime">2012-07-13T19:53:09.358Z</Item>
 * 		</Entry>
 * 		<Entry key="map">
 * 			<Entry key="string" type="string">str</Entry>
 * 			<Entry key="date" type="date">2012-07-13</Entry>
 * 		</Entry>
 * </meta>
 * 
 * @author Nam Pham
 *
 */
public class JiBXMapMapper implements IMarshaller, IUnmarshaller, org.jibx.runtime.IAliasable {
	
	static enum DataType {
		NULL,
		STRING,
		INTEGER,
		FLOAT,
		DATE,
		DATETIME,
		BYTES,
		MAP,
		LIST
	}
	
	private String m_uri;
	private int m_index;
	private String m_name;

	public JiBXMapMapper() {
		m_uri = null;
		m_index = 0;
		m_name = "hashmap";
	}

	public JiBXMapMapper(String uri, int index, String name) {
		m_uri = uri;
		m_index = index;
		m_name = name;
	}
	
	@Override
	public boolean isPresent(IUnmarshallingContext ctx) throws JiBXException {
		return ctx.isAt(m_uri, m_name) && ctx.isStart();
	}

	@Override
	public boolean isExtension(String ctx) {
		return false;
	}

	@Override
	public Object unmarshal(Object map, IUnmarshallingContext ctx)
			throws JiBXException {
		UnmarshallingContext c = (UnmarshallingContext)ctx;
		@SuppressWarnings("unchecked")
		Map<String, Object> meta = (Map<String, Object>)map;
		
		if (c.currentEvent()!=IXMLReader.END_TAG) {
			c.next();
			meta = unmarshalMap(c);
		}
		else {
			if (meta==null) meta = new LinkedHashMap<String, Object>();
		}
		c.next();
		return meta;
	}


	private Object unmarshalObject(UnmarshallingContext c, DataType dt) throws JiBXException {
		Object value;
		switch (dt) {
		case NULL:
			value = null;
			break;
		case STRING:
			if (c.currentEvent() == IXMLReader.TEXT)
				value = c.getText();
			else
				value = "";
			break;
		case INTEGER: 
		case FLOAT:
		case DATE:
		case DATETIME:
		case BYTES:
			if (c.currentEvent() != IXMLReader.TEXT)
				throw new JiBXException("Expecting text at " + c.getName() + " " + dt);
			String text = c.getText();
			switch (dt) {
			case INTEGER:
				value = Long.parseLong(text.trim());
				break;
			case FLOAT:
				value = Double.parseDouble(text.trim());
				break;
			case DATE:
				value = Utility.deserializeSqlDate(text.trim());
				break;
			case DATETIME:
				value = Utility.deserializeDateTime(text.trim());
				break;
			case BYTES:
				value = Utility.deserializeBase64(text.trim());
				break;
			default:
				throw new RuntimeException("impossible");
			}
			break;
		case LIST:
			value = unmarshalList(c);
			break;
		case MAP:
			value = unmarshalMap(c);
			break;
		default:
			throw new RuntimeException("impossible");
		}
		
		return value;
		
	}

	private Map<String, Object> unmarshalMap(UnmarshallingContext c) throws JiBXException {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		int current = c.currentEvent();
		while (true) {
			if (current!=IXMLReader.START_TAG && current!=IXMLReader.END_TAG) {
				current = c.next();
				current = c.toTag();
			}
			if (current==IXMLReader.END_TAG) break;
			assert (c.getName().equals("Entry"));
			String key = c.attributeText("", "Name");
			String type = c.attributeText("", "Type");
			if (type==null) type = "string";
			else if (type.equals("date-time")) type="datetime";
			DataType dt = DataType.valueOf(type.toUpperCase());
			current = c.next(); // proceed to value
			Object value = unmarshalObject(c, dt);
			
			if (current!=-1 && current!=IXMLReader.END_TAG)
				current = c.toTag();
			if (current!=IXMLReader.END_TAG || !c.getName().equals("Entry"))
				throw new JiBXException("expecting close Entry tag " + c.getText());
			
			map.put(key, value);
			current = c.next();
		}
		return map;
	}

	private Object unmarshalList(UnmarshallingContext c) throws JiBXException {
		List<Object> list = new ArrayList<Object>();
		
		int current = c.currentEvent();
		while (true) {
			if (current!=IXMLReader.START_TAG && current!=IXMLReader.END_TAG) {
				current = c.next();
				current = c.toTag();
			}
			if (current==IXMLReader.END_TAG) break;
			assert (c.getName().equals("Item"));
			String type = c.attributeText("", "Type");
			if (type==null) type = "string";
			else if (type.equals("date-time")) type="datetime";
			DataType dt = DataType.valueOf(type.toUpperCase());
			current = c.next(); // proceed to value
			
			Object value = unmarshalObject(c, dt);
			list.add(value);
			
			if (current!=-1 && current!=IXMLReader.END_TAG)
				current = c.toTag();
			if (current!=IXMLReader.END_TAG || !c.getName().equals("Item"))
				throw new JiBXException("expecting close Item tag " + c.getText());
			current = c.next();
		}
		return list;
	}

	@Override
	public void marshal(Object objMap, IMarshallingContext ctx)
			throws JiBXException {
		MarshallingContext c = (MarshallingContext)ctx;
		
		@SuppressWarnings("unchecked")
		Map<String, Object> meta = (Map<String, Object>)objMap;
		
		// start marshalling
		c.startTag(m_index, m_name);
		marshalMap(meta, c);
		// end marhsalling
		c.endTag(m_index, m_name);
	}

	private void marshalMap(Map<String, Object> map, MarshallingContext c) throws JiBXException {
		for (Map.Entry<String, Object>entry:map.entrySet()) {
			c.startTag(m_index, "Entry");
			if (entry.getKey()==null)
				throw new JiBXException("Null key is not allow for map entry");
			c.attribute(0, "Name", entry.getKey());
			Object value = entry.getValue();
			marshalObject(value, c);
			c.endTag(m_index, "Entry");
		}
	}
	
	private void marshalList(List<Object> listVal, MarshallingContext c) throws JiBXException {
		for (Object item:listVal) {
			c.startTag(m_index, "Item");
			marshalObject(item, c);
			c.endTag(m_index, "Item");
		}
	}

	private void marshalObject(Object value, MarshallingContext c) throws JiBXException {
		if (value==null) {
			c.attribute(0, "Type", "null");
		}
		else if (value instanceof Map) {
			c.attribute(0, "Type", "map");
			@SuppressWarnings("unchecked")
			Map<String, Object> mapVal = (Map<String, Object>) value;
			marshalMap(mapVal, c);
		}
		else if (value instanceof List) {
			c.attribute(0, "Type", "list");
			@SuppressWarnings("unchecked")
			List<Object> listVal = (List<Object>) value;
			marshalList(listVal, c);
		}
		else {
			String dt;
			String text;
			if (value.getClass().isArray()) {
				dt = "bytes";
				byte[] bytes = (byte[])value;
				text = Utility.serializeBase64(bytes);
			}
			if (value instanceof java.sql.Date) {
				dt = "date";
				java.sql.Date date = (java.sql.Date)value;
				text = Utility.serializeDate(date);
			}
			else if (value instanceof Date) {
				dt = "date-time";
				Date date = (Date)value;
				text = Utility.serializeDateTime(date);
			}
			else if (value instanceof Number) {
				Number number = (Number) value;
				if (value instanceof Float || value instanceof Double) {
					dt = "float";
					text = Utility.serializeDouble(number.doubleValue());
				} else {
					dt = "integer";
					text = Utility.serializeLong(number.longValue());
				}	
			}
			else {
				dt = "string";
				text = (String)value;
			}
			c.attribute(0, "Type", dt);
			c.content(text);
		}
	}

}
