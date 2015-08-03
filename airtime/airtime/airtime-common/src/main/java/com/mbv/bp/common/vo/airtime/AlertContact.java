package com.mbv.bp.common.vo.airtime;

public class AlertContact {
	
	public static final String SELECT_BY_ALERT_ID="AlertContact_SelectByAlertId";

	public static final String SELECT = "AlertContact_Select";

	public static final String INSERT = "AlertContact_Insert";
	
	public static final String DELETE = "AlertContact_Delete";
    
	private String contactName;

    private String alertId;

    private String contactAddress;

    private String contactType;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType == null ? null : contactType.trim();
    }
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

	@Override
	public String toString() {
		return "AlertContact [alertId=" + alertId + ", contactAddress="
				+ contactAddress + ", contactName=" + contactName
				+ ", contactType=" + contactType + "]";
	}
    
}