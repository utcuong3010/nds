package com.mbv.bp.common.model;

import java.util.Map;


public class MessageInfo {
	private String messageType;
	private Map<Integer,Field> fieldMaps;
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Map<Integer, Field> getFieldMaps() {
		return fieldMaps;
	}
	public void setFieldMaps(Map<Integer, Field> fieldMaps) {
		this.fieldMaps = fieldMaps;
	}
	@Override
	public String toString() {
		return "MessageInfo [fieldMaps=" + fieldMaps + ", messageType="
				+ messageType + "]\n";
	}
	
	
}
