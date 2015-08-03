/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AsyncReqTempDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.AsyncReqTemp
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AsyncReqTempDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AsynReqInquiryTxnTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)AsynReqInquiryTxnTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            context.put(Attributes.ATTR_ERROR_CODE,"SUCCESS");
            AsyncReqTempDao reqTempDao = new AsyncReqTempDao();
            AsyncReqTemp asyncReqTemp = new AsyncReqTemp();
            asyncReqTemp.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
            if (!reqTempDao.select(asyncReqTemp)) {
                context.put(Attributes.ATTR_ERROR_CODE,"TXN_NOT_EXISTED");
            } else {
                context.put(Attributes.ATTR_OPERATOR_TYPE,asyncReqTemp.getOperatorType());
                context.put(Attributes.ATTR_TXN_STATUS,asyncReqTemp.getStatus());
                context.put(Attributes.ATTR_TXT_INQUIRY_RESULT,asyncReqTemp.getValue());
                context.put(Attributes.ATTR_TXN_ERROR_CODE,asyncReqTemp.getErrorCode());
            }
        }
        catch (Exception e) {
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            return false;
        }
        return true;
    }
}

