/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.manager.AmountManager
 *  com.mbv.bp.common.dao.airtime.manager.AmountManager$CHARGE_TYPE
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.model.TelcoProvider
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.manager.AmountManager;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GameTopupDetermineProviderAndBillingTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)GameTopupDetermineProviderAndBillingTask.class);

    public boolean execute(ContextBase context) throws Exception {
        String errorCode = "SUCCESS";
        context.setErrorCode(errorCode);
        String[] providerIds = ((TelcoProvider)AppConstants.TELCO_PROVIDER.get(context.get(Attributes.ATTR_TELCO_ID))).getConnectionIds();
        AmountManager amountManager = new AmountManager();
        long amount = context.getLong(Attributes.ATTR_AMOUNT);
        for (int i = 0; i < providerIds.length; ++i) {
            String providerId = providerIds[i];
            errorCode = StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESERVED_ID))) ? amountManager.providerCharge(AmountManager.CHARGE_TYPE.UNIT_RESERVATION, providerId, amount) : amountManager.providerCharge(AmountManager.CHARGE_TYPE.DIRECT_DEBITING, providerId, amount);
            if (!"SUCCESS".equalsIgnoreCase(errorCode)) continue;
            context.put(Attributes.ATTR_CONN_TYPE,providerId);
            context.put(Attributes.ATTR_PROVIDER_BILLING,"Y");
            break;
        }
        if (!"SUCCESS".equalsIgnoreCase(errorCode)) {
            context.setErrorCode(errorCode);
            context.put(Attributes.ATTR_PROVIDER_BILLING,"N");
            return false;
        }
        if (StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESERVED_ID)))) {
            errorCode = amountManager.creditLockAccount("AT_TXN", (String)context.get(Attributes.ATTR_TRANSACTION_ID), (String)context.get(Attributes.ATTR_CHANNEL_ID), (String)context.get(Attributes.ATTR_RESERVED_ID), (String)context.get(Attributes.ATTR_REQUEST_TXN_ID), context.getLong(Attributes.ATTR_AMOUNT), (String)context.get(Attributes.ATTR_DESCRIPTION));
            if (!"SUCCESS".equalsIgnoreCase(errorCode)) {
                context.setErrorCode(errorCode);
                context.put(Attributes.ATTR_RESERVED_BILLING,"N");
                return false;
            }
            context.put(Attributes.ATTR_RESERVED_BILLING,"Y");
        }
        return true;
    }
}

