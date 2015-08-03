/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.AtTransaction
 *  com.mbv.bp.core.workflow.Handler
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.handler;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.airtime.handler.BillingErrorHandler;
import com.mbv.bp.core.workflow.Handler;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RequestErrorHandler
extends Handler {
    private static Log log = LogFactory.getLog((Class)RequestErrorHandler.class);

    public boolean handle(ContextBase context, Exception exception) {
        try {
            BillingErrorHandler billingErrorHandler = new BillingErrorHandler();
            if (!billingErrorHandler.handle(context, exception)) {
                log.error("**** Fail to process billingErrorHandler... *****");
            }
            log.info(("Going to process error handle for workflow - " + (String)context.get(Attributes.ATTR_WFP_NAME)));
            if (!"Y".equalsIgnoreCase((String)context.get(Attributes.ATTR_IS_REQUEST_DB_PROCESSED))) {
                log.info("No db inserted.");
                return true;
            }
            log.info("Update request as error happened.");
            String errorCode = (String)context.get(Attributes.ATTR_ERROR_CODE);
            if (StringUtils.isBlank((String)errorCode)) {
                errorCode = "SYS_INTERNAL_ERROR";
            }
            AtTransaction atTxn = new AtTransaction();
            atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
            atTxn.setTxnStatus("ERROR");
            atTxn.setErrorCode(context.getErrorCode());
            atTxn.setUpdatedDate(new Date());
            atTxn.setUpdatedBy(AppConstants.APP_ID);
            AtTransactionDao dao = new AtTransactionDao();
            dao.update(atTxn);
        }
        catch (Exception e) {
            log.error(("Fail to Handle Error.| ErrMsg-" + e.getMessage()), (Throwable)e);
        }
        return true;
    }
}

