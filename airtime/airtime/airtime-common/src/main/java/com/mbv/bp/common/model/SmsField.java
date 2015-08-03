package com.mbv.bp.common.model;

public class SmsField {
	private String startWith;
	private String endWith;
	private String fieldId;
	public String getStartWith() {
		return startWith;
	}
	public void setStartWith(String startWith) {
		this.startWith = startWith;
	}
	public String getEndWith() {
		return endWith;
	}
	public void setEndWith(String endWith) {
		this.endWith = endWith;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	@Override
	public String toString() {
		return "SmsField [endWith=" + endWith + ", fieldId=" + fieldId
				+ ", startWith=" + startWith + "]";
	}
	
}
