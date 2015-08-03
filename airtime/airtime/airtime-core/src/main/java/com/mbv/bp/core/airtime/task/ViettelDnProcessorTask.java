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
 *  com.mbv.bp.common.settings.ViettelSettings
 *  com.mbv.bp.common.vo.airtime.AtTransaction
 *  com.mbv.bp.core.workflow.Task
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
import com.mbv.bp.common.settings.ViettelSettings;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.airtime.handler.BillingErrorHandler;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViettelDnProcessorTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)ViettelDnProcessorTask.class);

    public boolean execute(ContextBase context) throws Exception {
        AtTransactionDao atTxnDao;
        BillingErrorHandler billingErrorHandler;
        AtTransaction atTxn = new AtTransaction();
        if (AppConstants.VIETTEL_SETTINGS.getRequestOperation().equals(context.get(Attributes.ATTR_OPERATION_TYPE))) {
            atTxnDao = new AtTransactionDao();
            atTxn.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
            atTxn.setTxnStatus("ERROR");
            atTxn.setErrorCode((String)context.get(Attributes.ATTR_RESPONSE_CODE));
            atTxn.setUpdatedDate(new Date());
            atTxn.setUpdatedBy(AppConstants.APP_ID);
            try {
                atTxnDao.updateByAtTxnId(atTxn);
            }
            catch (DaoException e) {
                context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
                log.error(("Fail to update data| errMsg-" + e.getMessage()), (Throwable)e);
                return false;
            }
        } else if (AppConstants.VIETTEL_SETTINGS.getResponseOperation().equals(context.get(Attributes.ATTR_OPERATION_TYPE))) {
            atTxnDao = new AtTransactionDao();
            atTxn.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
            atTxn.setDeliveryDate(context.getDate(Attributes.ATTR_DELIVERY_DATE));
            atTxn.setResponseDate(context.getDate(Attributes.ATTR_RESPONSE_DATE));
            atTxn.setStatus("DELIVERED");
            atTxn.setErrorCode((String)context.get(Attributes.ATTR_RESPONSE_CODE));
            if (AppConstants.VIETTEL_SETTINGS.isSuccessRc(atTxn.getErrorCode())) {
                atTxn.setTxnStatus("SUCCESS");
            } else if (AppConstants.VIETTEL_SETTINGS.isTxnInProgress(atTxn.getErrorCode())) {
                atTxn.setTxnStatus("SUCCESS");
            } else if ("RESPONSE_TIME_OUT".equalsIgnoreCase(atTxn.getErrorCode())) {
                atTxn.setTxnStatus("SUCCESS");
            } else {
                atTxn.setTxnStatus("ERROR");
            }
            atTxn.setUpdatedDate(new Date());
            atTxn.setUpdatedBy(AppConstants.APP_ID);
            try {
                atTxnDao.updateByAtTxnId(atTxn);
            }
            catch (DaoException e) {
                context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
                log.error(("Fail to update data| errMsg-" + e.getMessage()), (Throwable)e);
                return false;
            }
        } else {
            context.setErrorCode("UNSUPPORTED_OPERATION");
            log.error(("Unsupported Operation for request.| context-" +context));
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

