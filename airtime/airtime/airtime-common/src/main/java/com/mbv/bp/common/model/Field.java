package com.mbv.bp.common.model;

public class Field {
	
	
	private int number;
	private String name;
	private int length;
	private int lengthIndicator;
	private String value;
	
	private boolean isSignable;
	
	public Field() {
	
	}
	public Field(int number, String name, int length, int lengthIndicator,
			String value, boolean isSignable) {
		super();
		this.number = number;
		this.name = name;
		this.length = length;
		this.lengthIndicator = lengthIndicator;
		this.value = value;
		this.isSignable = isSignable;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getLengthIndicator() {
		return lengthIndicator;
	}
	public void setLengthIndicator(int lengthIndicator) {
		this.lengthIndicator = lengthIndicator;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isSignable() {
		return isSignable;
	}
	public void setSignable(boolean isSignable) {
		this.isSignable = isSignable;
	}
	
	@Override
	public String toString() {
		return "Field [isSignable=" + isSignable + ", length=" + length
				+ ", lengthIndicator=" + lengthIndicator + ", name=" + name
				+ ", number=" + number + ", value=" + value + "]";
	}
	
}
