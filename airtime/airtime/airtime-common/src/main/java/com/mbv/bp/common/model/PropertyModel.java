package com.mbv.bp.common.model;

public class PropertyModel {
	
	private String key;
	private String value;
	
	public PropertyModel() {}
	
	public PropertyModel(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "PropertyModel [key=" + key + ", value=" + value + "]";
	}

}
