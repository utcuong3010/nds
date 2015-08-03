/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.dao.airtime.ProviderAccountDao
 *  com.mbv.bp.common.dao.airtime.ProviderAmountDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.NotificationSettings
 *  com.mbv.bp.common.settings.NotificationSettings$TEMPLATE_TYPE
 *  com.mbv.bp.common.vo.airtime.ProviderAmount
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.scheduler;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.airtime.ProviderAccountDao;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.NotificationSettings;
import com.mbv.bp.common.vo.airtime.ProviderAmount;
import com.mbv.bp.core.scheduler.AbstractJob;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProviderAmountCheckJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)ProviderAmountCheckJob.class);

    @Override
    public void execute() {
        if (!this.isEnable()) {
            this.log.info(("no process for this job | enable-" + this.isEnable()));
            return;
        }
        try {
            ProviderAmountDao providerAmountDao = new ProviderAmountDao();
            List listCurProviderAmounts = providerAmountDao.selectAll();
            if (listCurProviderAmounts != null) {
                for (int i = 0; i < listCurProviderAmounts.size(); ++i) {
                    ProviderAmount providerAmount = (ProviderAmount)listCurProviderAmounts.get(i);
                    String providerId = providerAmount.getProviderId();
                    ProviderAccountDao accountDao = new ProviderAccountDao();
                    long totalAmount = accountDao.selectTotalAmountByProviderId(providerAmount.getProviderId());
                    long currentAmount = totalAmount - providerAmount.getTotalUsed();
                    if (currentAmount < providerAmount.getNotifAmount()) {
                        if (providerAmount.getNotifSent().booleanValue()) continue;
                        ContextBase context = new ContextBase();
                        context.put("provider_id",providerId);
                        context.putLong("current_amount", currentAmount);
                        context.putLong("notif_amount", providerAmount.getNotifAmount().longValue());
                        if (AppConstants.NOTIFICATION_SETTINGS.sendNotification(NotificationSettings.TEMPLATE_TYPE.PROVIDER_AMOUNT_ALERT, new Object[]{context})) {
                            providerAmount = new ProviderAmount();
                            providerAmount.setProviderId(providerId);
                            providerAmount.setNotifSent(Boolean.valueOf(true));
                            providerAmountDao.update(providerAmount);
                            continue;
                        }
                        this.log.error("======================================================");
                        this.log.error("========= Fail to send PROVIDER_AMOUNT_ALERT =========");
                        this.log.error("======================================================");
                        continue;
                    }
                    if (!providerAmount.getNotifSent().booleanValue()) continue;
                    providerAmount = new ProviderAmount();
                    providerAmount.setProviderId(providerId);
                    providerAmount.setNotifSent(Boolean.valueOf(false));
                    providerAmountDao.update(providerAmount);
                }
            }
        }
        catch (Exception e) {
            this.log.error(e);
        }
    }
}

