/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.manager.AmountManager
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.task.billing;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.manager.AmountManager;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CreditAmountTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)CreditAmountTask.class);

    public boolean execute(ContextBase context) throws Exception {
        AmountManager amountManager = new AmountManager();
        String errorCode = amountManager.creditLockAccount((String)context.get(Attributes.ATTR_OPERATION), (String)context.get(Attributes.ATTR_REQUEST_TXN_ID), (String)context.get(Attributes.ATTR_CHANNEL_ID), (String)context.get(Attributes.ATTR_RESERVED_ID), (String)context.get(Attributes.ATTR_REFERENCE_ID), context.getLong(Attributes.ATTR_AMOUNT), (String)context.get(Attributes.ATTR_DESCRIPTION));
        context.put(Attributes.ATTR_ERROR_CODE,errorCode);
        if (!"SUCCESS".equalsIgnoreCase(errorCode)) {
            return false;
        }
        return true;
    }
}

