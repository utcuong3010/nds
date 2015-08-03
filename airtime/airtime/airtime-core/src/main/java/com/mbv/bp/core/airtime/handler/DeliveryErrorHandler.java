/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.forward.VnPayCdrForward
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
import com.mbv.bp.common.forward.VnPayCdrForward;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.workflow.Handler;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DeliveryErrorHandler
extends Handler {
    private static Log log = LogFactory.getLog((Class)DeliveryErrorHandler.class);

    public boolean handle(ContextBase context, Exception exception) {
        try {
            log.info(("Going to process error handle for workflow - " + (String)context.get(Attributes.ATTR_WFP_NAME)));
            log.info("Update request as error happened.");
            String errorCode = (String)context.get(Attributes.ATTR_ERROR_CODE);
            if (StringUtils.isBlank((String)errorCode)) {
                errorCode = "SYS_INTERNAL_ERROR";
            }
            AtTransaction atTxn = new AtTransaction();
            atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
            if ("DELIVERY_RESPONSE_ERROR".equalsIgnoreCase(context.getErrorCode()) || "INVALID_RESPONSE".equalsIgnoreCase(context.getErrorCode())) {
                atTxn.setTxnStatus("SUCCESS");
                context.put(Attributes.ATTR_RESPONSE_CODE,errorCode);
                VnPayCdrForward.getInstance().execute(context);
            } else {
                atTxn.setTxnStatus("ERROR");
            }
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

