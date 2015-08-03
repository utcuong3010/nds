package com.mbv.frontend.model;

import java.util.List;
import java.util.Map;

import com.mbv.bp.common.vo.airtime.AlertContact;

public class AlertConfigModel {
private String alertId;
private String alertName;
private boolean smsEnable;
private boolean emailEnable;
private List<AlertContact> listSmsContact;
private List<AlertContact> listEmailContact;
private String contactName;
private String contactAddress;
private String contactType;

public String getAlertId() {
	return alertId;
}
public String getContactName() {
	return contactName;
}
public void setContactName(String contactName) {
	this.contactName = contactName;
}
public String getContactAddress() {
	return contactAddress;
}
public void setContactAddress(String contactAddress) {
	this.contactAddress = contactAddress;
}
public String getContactType() {
	return contactType;
}
public void setContactType(String contactType) {
	this.contactType = contactType;
}
public void setAlertId(String alertId) {
	this.alertId = alertId;
}
public List<AlertContact> getListSmsContact() {
	return listSmsContact;
}
public void setListSmsContact(List<AlertContact> listSmsContact) {
	this.listSmsContact = listSmsContact;
}
public List<AlertContact> getListEmailContact() {
	return listEmailContact;
}
public void setListEmailContact(List<AlertContact> listEmailContact) {
	this.listEmailContact = listEmailContact;
}
public boolean isSmsEnable() {
	return smsEnable;
}
public void setSmsEnable(boolean smsEnable) {
	this.smsEnable = smsEnable;
}
public boolean isEmailEnable() {
	return emailEnable;
}
public void setEmailEnable(boolean emailEnable) {
	this.emailEnable = emailEnable;
}
public String getAlertName() {
	return alertName;
}
public void setAlertName(String alertName) {
	this.alertName = alertName;
}

 
 
}
