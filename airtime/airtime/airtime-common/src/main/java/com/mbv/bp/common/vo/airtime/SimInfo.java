package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class SimInfo {
    
	public static final String SELECT_ALL = "SimInfo_SelectAll";

	public static final String SELECT_BY_COM_ID = "SimInfo_SelectByComId";

	public static final String SELECT_BY_MSISDN = "SimInfo_SelectByMsisdn";

	public static final String SELECT_ALL_AVAILABLE_MSISDN = "SimInfo_SelectAllAvailableMsisdn";
	
	public static final String UPDATE_CURRENT_LOCK_AMOUNT_BY_MSISDN = "SimInfo_UpdateCurrentLockAmountByMsisdn";
	

	private String comId;

    private String simType;

    private Long currentAmount;

    private Long lockAmount;

    private String ip;
    
    private String msisdn;

    private String simStatus;
    
    private Date lastSentSms;
    
    public String getComId() {
        return comId;
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getSimType() {
        return simType;
    }

    public void setSimType(String simType) {
        this.simType = simType == null ? null : simType.trim();
    }

    public Long getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }

    public Long getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(Long lockAmount) {
        this.lockAmount = lockAmount;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }
    
    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getSimStatus() {
        return simStatus;
    }

    public void setSimStatus(String simStatus) {
        this.simStatus = simStatus == null ? null : simStatus.trim();
    }

	public Date getLastSentSms() {
		return lastSentSms;
	}

	public void setLastSentSms(Date lastSentSms) {
		this.lastSentSms = lastSentSms;
	}

	@Override
	public String toString() {
		return "SimInfo [comId=" + comId + ", currentAmount=" + currentAmount
				+ ", ip=" + ip + ", lockAmount=" + lockAmount + ", msisdn="
				+ msisdn + ", simStatus=" + simStatus + ", simType=" + simType
				+ "]";
	}
    
}