package com.mbv.bp.common.vo.airtime;

import java.util.Date;

public class AtSummaryKey {
    private String providerId;

    private Date txnDate;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId == null ? null : providerId.trim();
    }

    public Date getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }
}