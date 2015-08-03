/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.ReservedTxnDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.ReservedTxn
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.task.billing;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.ReservedTxnDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.ReservedTxn;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckLockAccountTxnIdTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)CheckLockAccountTxnIdTask.class);

    public boolean execute(ContextBase context) throws Exception {
        boolean result = true;
        context.setErrorCode("SUCCESS");
        ReservedTxnDao reservedTxnDao = new ReservedTxnDao();
        ReservedTxn reservedTxn = new ReservedTxn();
        reservedTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
        if (reservedTxnDao.select(reservedTxn)) {
            context.put(Attributes.ATTR_ERROR_CODE,"ALREDY_EXISTED");
            return false;
        }
        return result;
    }
}

