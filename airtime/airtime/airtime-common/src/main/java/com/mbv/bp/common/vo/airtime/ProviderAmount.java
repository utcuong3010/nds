package com.mbv.bp.common.vo.airtime;

public class ProviderAmount {
	
	public static final String SELECT_ALL = "ProviderAmount_SelectAll";
	public static final String SELECT = "ProviderAmount_Select";
	public static final String INSERT = "ProviderAmount_Insert";
	public static final String UPDATE = "ProviderAmount_Update";
	public static final String UPDATE_USED_AMOUNT = "ProviderAmount_UpdateUsedAmount";
	public static final String SELECT_LOCK = "ProviderAmount_SelectLock";
	public static final String UPDATE_LOADED_AMOUNT = "ProviderAmount_UpdateLoadedAmount";
	
	private String providerId;

    private Long totalLoaded;

    private Long totalUsed;

    private Long notifAmount;

    private Boolean notifSent;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId == null ? null : providerId.trim();
    }

    public Long getTotalLoaded() {
        return totalLoaded;
    }

    public void setTotalLoaded(Long totalLoaded) {
        this.totalLoaded = totalLoaded;
    }

    public Long getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(Long totalUsed) {
        this.totalUsed = totalUsed;
    }

    public Long getNotifAmount() {
        return notifAmount;
    }

    public void setNotifAmount(Long notifAmount) {
        this.notifAmount = notifAmount;
    }

    public Boolean getNotifSent() {
        return notifSent;
    }

    public void setNotifSent(Boolean notifSent) {
        this.notifSent = notifSent;
    }
}