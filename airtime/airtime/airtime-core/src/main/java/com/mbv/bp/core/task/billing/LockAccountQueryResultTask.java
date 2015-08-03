/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.ReservedAccountDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.ReservedAccount
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.task.billing;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.ReservedAccountDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.ReservedAccount;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LockAccountQueryResultTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)LockAccountQueryResultTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            context.setErrorCode("SUCCESS");
            ReservedAccountDao reservedAccountDao = new ReservedAccountDao();
            ReservedAccount reservedAccount = new ReservedAccount();
            reservedAccount.setAccountId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            if (reservedAccountDao.select(reservedAccount)) {
                context.putLong(Attributes.ATTR_DEBIT_AMOUNT, reservedAccount.getTotalDebit().longValue());
                context.putLong(Attributes.ATTR_CREDIT_AMOUNT, reservedAccount.getTotalCredit().longValue());
                context.put(Attributes.ATTR_RESERVED_ID,reservedAccount.getAccountId());
                context.put(Attributes.ATTR_CHANNEL_ID,reservedAccount.getSystemId());
                context.put(Attributes.ATTR_TELCO_IDS,reservedAccount.getTelcoIds());
                context.put(Attributes.ATTR_DESCRIPTION,reservedAccount.getDescription());
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

