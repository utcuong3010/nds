/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.SimTransactionDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.SimTransaction
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.anypay;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.SimTransactionDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.SimTransaction;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AnypayTransferRequestTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)AnypayTransferRequestTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            String fromSim = (String)context.get(Attributes.ATTR_COM_ID);
            String toSim = (String)context.get(Attributes.ATTR_MSISDN);
            long amount = context.getLong(Attributes.ATTR_AMOUNT);
            SimTransaction simTransaction = new SimTransaction();
            SimTransactionDao simTransactionDao = new SimTransactionDao();
            simTransaction.setTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
            simTransaction.setTxnType("TRANSFER");
            simTransaction.setTxnDate(context.getDate(Attributes.ATTR_TRANSACTION_DATE));
            simTransaction.setErrorCode("SUCCESS");
            simTransaction.setSimAmount(Long.valueOf(0));
            simTransaction.setLockAmount(Long.valueOf(0));
            simTransaction.setBilling("N");
            simTransaction.setTxnStatus("PENDING");
            simTransaction.setSimId(fromSim);
            simTransaction.setMsisdn(toSim);
            simTransaction.setAmount(Long.valueOf(amount));
            simTransactionDao.insert(simTransaction);
        }
        catch (Exception e) {
            log.error(("Unable to Call transquery to Mobifone Ws.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
        }
        return true;
    }
}

