/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.DaoException
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.forward.ActiveMqForwarding
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.AtTransaction
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.forward.ActiveMqForwarding;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.airtime.handler.BillingErrorHandler;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneDbUpdateResultTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFoneDbUpdateResultTask.class);

    public boolean execute(ContextBase context) throws Exception {
        BillingErrorHandler billingErrorHandler;
        AtTransaction atTxn = new AtTransaction();
        atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
        atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
        atTxn.setStatus("DELIVERED");
        atTxn.setErrorCode((String)context.get(Attributes.ATTR_RESPONSE_CODE));
        atTxn.setMessageId((String)context.get(Attributes.ATTR_MESSAGE_ID));
        if (StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESPONSE_CODE)))) {
            int mobiResult = context.getInt(Attributes.ATTR_RESPONSE_CODE);
            if (mobiResult != 0) {
                atTxn.setTxnStatus("ERROR");
                atTxn.setErrorCode(((String)context.get(Attributes.ATTR_RESULT_NAMESPACE)).toUpperCase() + "_" + mobiResult);
            } else {
                atTxn.setTxnStatus("SUCCESS");
                atTxn.setErrorCode("00");
            }
        }
        atTxn.setResponseDate(new Date());
        atTxn.setUpdatedDate(new Date());
        atTxn.setUpdatedBy(AppConstants.APP_ID);
        AtTransactionDao atTransactionDao = new AtTransactionDao();
        try {
            atTransactionDao.update(atTxn);
            context.put(Attributes.ATTR_IS_REQ_DB_DELIVERED_PROCESSED,"Y");
        }
        catch (DaoException e) {
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            log.error(("Fail to update data| errMsg-" + e.getMessage()), (Throwable)e);
            return false;
        }
        if (!("SUCCESS".equalsIgnoreCase(atTxn.getTxnStatus()) || (billingErrorHandler = new BillingErrorHandler()).handle(context, null))) {
            log.error("**** Fail to process billingErrorHandler... *****");
        }
        try {
            ActiveMqForwarding.getInstance().forward(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
        }
        catch (Exception e) {
            log.error("Fail to forward result to activemq", (Throwable)e);
        }
        return true;
    }
}

