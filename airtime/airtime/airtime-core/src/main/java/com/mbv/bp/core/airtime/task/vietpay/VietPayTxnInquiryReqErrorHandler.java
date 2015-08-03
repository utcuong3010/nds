/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AsyncReqTempDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.AsyncReqTemp
 *  com.mbv.bp.core.workflow.Handler
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.vietpay;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AsyncReqTempDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.core.workflow.Handler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VietPayTxnInquiryReqErrorHandler
extends Handler {
    private static Log log = LogFactory.getLog((Class)VietPayTxnInquiryReqErrorHandler.class);

    public boolean handle(ContextBase context, Exception exception) {
        String errorCode = (String)context.get(Attributes.ATTR_ERROR_CODE);
        if (StringUtils.isBlank((String)errorCode)) {
            errorCode = "SYS_INTERNAL_ERROR";
        }
        AsyncReqTemp reqTemp = new AsyncReqTemp();
        reqTemp.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
        reqTemp.setErrorCode(errorCode);
        reqTemp.setStatus("ERROR");
        try {
            AsyncReqTempDao asyncReqTempDao = new AsyncReqTempDao();
            asyncReqTempDao.update(reqTemp);
        }
        catch (Exception e) {
            log.error("Fail to update Txn Inquiry request to database.", (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
        }
        return true;
    }
}

