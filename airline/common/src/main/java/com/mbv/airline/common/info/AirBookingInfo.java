package com.mbv.airline.common.info;

import java.util.ArrayList;
import java.util.List;

public class AirBookingInfo {
	protected AgentInfo agentInfo = new AgentInfo();
	protected List<AirFareInfo> fareInfos = new ArrayList<AirFareInfo>();
	protected List<AirPassengerInfo> passengerInfos = new ArrayList<AirPassengerInfo>();
	protected ContactInfo contactInfo = new ContactInfo();
	protected List<AirExtraService> extraServices = new ArrayList<AirExtraService>();

	public List<AirExtraService> getExtraServices() {
		return extraServices;
	}

	public void setExtraServices(List<AirExtraService> extraServices) {
		this.extraServices = extraServices;
	}

	public List<AirFareInfo> getFareInfos() {
		return fareInfos;
	}

	public void setFareInfos(List<AirFareInfo> fareInfos) {
		this.fareInfos = fareInfos;
	}

	public List<AirPassengerInfo> getPassengerInfos() {
		return passengerInfos;
	}

	public void setPassengerInfos(List<AirPassengerInfo> passengerInfos) {
		this.passengerInfos = passengerInfos;
	}

	public AgentInfo getAgentInfo() {
		return agentInfo;
	}

	public void setAgentInfo(AgentInfo agentInfo) {
		this.agentInfo = agentInfo;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	@Override
	public String toString() {
		return "AirBookingInfo [agentInfo=" + agentInfo + ", fareInfos="
				+ fareInfos + ", passengerInfos=" + passengerInfos
				+ ", contactInfo=" + contactInfo + ", extraServices="
				+ extraServices + "]";
	}
}
