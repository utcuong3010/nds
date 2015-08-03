package com.mbv.bp.common.vo.airtime;

import java.util.List;

public class ReservedTelco {
	
	public static final String SELECT_ALL = "ReservedTelco_Select_All";

	public static final String UPDATE_LOCK_TELCOS = "ReservedTelco_Update_Lock_Telco";

    private String telcoId;

    private Long reservedAmount;

    private Integer percent;

    private List<String> telcoIds;
    
    public String getTelcoId() {
        return telcoId;
    }

    public void setTelcoId(String telcoId) {
        this.telcoId = telcoId == null ? null : telcoId.trim();
    }

    public Long getReservedAmount() {
        return reservedAmount;
    }

    public void setReservedAmount(Long reservedAmount) {
        this.reservedAmount = reservedAmount;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

	public List<String> getTelcoids() {
		return telcoIds;
	}

	public void setTelcoids(List<String> telcoIds) {
		this.telcoIds = telcoIds;
	}
    
    
}