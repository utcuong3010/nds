/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.ProviderAmountDao
 *  com.mbv.bp.common.dao.airtime.ReservedTelcoDao
 *  com.mbv.bp.common.dao.airtime.manager.AmountManager
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.model.TelcoProvider
 *  com.mbv.bp.common.vo.airtime.ProviderAmount
 *  com.mbv.bp.common.vo.airtime.ReservedTelco
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.task.billing;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.dao.airtime.ReservedTelcoDao;
import com.mbv.bp.common.dao.airtime.manager.AmountManager;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.common.vo.airtime.ProviderAmount;
import com.mbv.bp.common.vo.airtime.ReservedTelco;
import com.mbv.bp.core.workflow.Task;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CreateLockAmountTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)CreateLockAmountTask.class);

    public boolean execute(ContextBase context) throws Exception {
        String[] telcoIds = null;
        telcoIds = StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_TELCO_IDS))) ? ((String)context.get(Attributes.ATTR_TELCO_IDS)).split(",") : AppConstants.TELCO_PROVIDER.keySet().toArray(new String[AppConstants.TELCO_PROVIDER.keySet().size()]);
        long amount = context.getLong(Attributes.ATTR_AMOUNT);
        ProviderAmountDao providerAmountDao = new ProviderAmountDao();
        ReservedTelcoDao reserveTelcoDao = new ReservedTelcoDao();
        HashMap<String, Long> providerAmountMap = new HashMap<String, Long>();
        HashMap<String, Long> telcoLockAmountMap = new HashMap<String, Long>();
        List<ProviderAmount> providerAmountList = providerAmountDao.selectAll();
        List<ReservedTelco> reservedTelcoList = reserveTelcoDao.selectAll();
        for (ProviderAmount providerAmount : providerAmountList) {
            providerAmountMap.put(providerAmount.getProviderId(), providerAmount.getTotalLoaded() - providerAmount.getTotalUsed());
        }
        for (ReservedTelco telcoLock : reservedTelcoList) {
            telcoLockAmountMap.put(telcoLock.getTelcoId(), (telcoLock.getReservedAmount() + amount) * (long)telcoLock.getPercent().intValue() / 100);
        }
        boolean isOk = true;
        for (String telco : telcoIds) {
            TelcoProvider telcoProvider = (TelcoProvider)AppConstants.TELCO_PROVIDER.get(telco);
            long currentAmount = 0;
            for (String providerId : telcoProvider.getConnectionIds()) {
                currentAmount+=((Long)providerAmountMap.get(providerId)).longValue();
            }
            if ((currentAmount-=((Long)telcoLockAmountMap.get(telco)).longValue()) >= 0) continue;
            log.error(("Fail to lock amount of provider| telcoId : " + telco));
            isOk = false;
            break;
        }
        if (!isOk) {
            context.put(Attributes.ATTR_ERROR_CODE,"SYS_NOT_ENOUGH_MONEY");
            return false;
        }
        AmountManager amountManager = new AmountManager();
        String errorCode = amountManager.createLockAccount((String)context.get(Attributes.ATTR_OPERATION), (String)context.get(Attributes.ATTR_CHANNEL_ID), (String)context.get(Attributes.ATTR_REQUEST_TXN_ID), (String)context.get(Attributes.ATTR_TRANSACTION_ID), context.getLong(Attributes.ATTR_AMOUNT), telcoIds, (String)context.get(Attributes.ATTR_DESCRIPTION));
        context.put(Attributes.ATTR_ERROR_CODE,errorCode);
        if (!"SUCCESS".equalsIgnoreCase(errorCode)) {
            return false;
        }
        return true;
    }
}

