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

public class LockAccountTxnQueryResultTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)LockAccountTxnQueryResultTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            context.setErrorCode("SUCCESS");
            ReservedTxnDao reservedTxnDao = new ReservedTxnDao();
            ReservedTxn reservedTxn = new ReservedTxn();
            reservedTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            if (reservedTxnDao.select(reservedTxn)) {
                context.put(Attributes.ATTR_RESERVED_ID,reservedTxn.getAccountId());
                context.put(Attributes.ATTR_CHANNEL_ID,reservedTxn.getSystemId());
                context.put(Attributes.ATTR_TRANSACTION_ID,reservedTxn.getTxnId());
                context.put(Attributes.ATTR_ACCOUNT_ID,reservedTxn.getAccountId());
                context.put(Attributes.ATTR_REFERENCE_ID,reservedTxn.getReferenceId());
                context.putLong(Attributes.ATTR_AMOUNT, reservedTxn.getAmount().longValue());
                context.put(Attributes.ATTR_OPERATION,reservedTxn.getOperation());
                context.put(Attributes.ATTR_DESCRIPTION,reservedTxn.getDescription());
                context.put(Attributes.ATTR_TXN_STATUS,"SUCCESS");
            } else {
                context.put(Attributes.ATTR_TXN_STATUS,"NOT_EXISTED");
            }
            return true;
        }
        catch (Exception e) {
            context.setErrorCode("DATABASE_EXCEPTION");
            log.error("Fail to query txnId", (Throwable)e);
            return false;
        }
    }
}

