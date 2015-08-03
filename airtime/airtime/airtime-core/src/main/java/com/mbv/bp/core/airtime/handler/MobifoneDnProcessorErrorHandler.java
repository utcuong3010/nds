/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.forward.ActiveMqForwarding
 *  com.mbv.bp.common.forward.MobifoneCdrForward
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
import com.mbv.bp.common.forward.ActiveMqForwarding;
import com.mbv.bp.common.forward.MobifoneCdrForward;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.airtime.handler.BillingErrorHandler;
import com.mbv.bp.core.workflow.Handler;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobifoneDnProcessorErrorHandler
extends Handler {
    private static Log log = LogFactory.getLog((Class)MobifoneDnProcessorErrorHandler.class);

    public boolean handle(ContextBase context, Exception exception) {
        BillingErrorHandler billingErrorHandler;
        AtTransaction atTxn = new AtTransaction();
        try {
            log.info(("Going to process error handle for workflow - " + (String)context.get(Attributes.ATTR_WFP_NAME)));
            log.info("Update request as error happened.");
            String errorCode = (String)context.get(Attributes.ATTR_ERROR_CODE);
            if (StringUtils.isBlank((String)errorCode)) {
                errorCode = "SYS_INTERNAL_ERROR";
            }
            atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
            if (StringUtils.isBlank((String)((String)context.get(Attributes.ATTR_MESSAGE_ID)))) {
                atTxn.setMessageId((String)context.get(Attributes.ATTR_NEXT_MESSAGE_ID));
            } else {
                atTxn.setMessageId((String)context.get(Attributes.ATTR_MESSAGE_ID));
            }
            if (!"DELIVERY_RESPONSE_ERROR".equalsIgnoreCase(errorCode)) {
                int mobiResult;
                atTxn.setErrorCode(errorCode);
                if (StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESPONSE_CODE))) && StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESULT_NAMESPACE))) && (mobiResult = context.getInt(Attributes.ATTR_RESPONSE_CODE)) != 0) {
                    atTxn.setErrorCode(((String)context.get(Attributes.ATTR_RESULT_NAMESPACE)).toUpperCase() + "_" + mobiResult);
                }
                atTxn.setTxnStatus("ERROR");
            } else {
                atTxn.setTxnStatus("SUCCESS");
                if (log.isDebugEnabled()) {
                    log.debug(("convert " + errorCode + " as " + "DELIVERY_RESPONSE_ERROR"));
                }
                atTxn.setErrorCode(errorCode);
                context.put(Attributes.ATTR_RESPONSE_CODE,errorCode);
                MobifoneCdrForward.getInstance().execute(context);
            }
            atTxn.setUpdatedDate(new Date());
            atTxn.setUpdatedBy(AppConstants.APP_ID);
            AtTransactionDao dao = new AtTransactionDao();
            dao.update(atTxn);
        }
        catch (Exception e) {
            log.error(("Fail to Handle Error.| ErrMsg-" + e.getMessage()), (Throwable)e);
            return false;
        }
        if (!("SUCCESS".equalsIgnoreCase(atTxn.getTxnStatus()) || (billingErrorHandler = new BillingErrorHandler()).handle(context, exception))) {
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

