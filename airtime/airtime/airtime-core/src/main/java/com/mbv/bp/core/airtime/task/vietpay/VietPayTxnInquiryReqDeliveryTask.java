/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AsyncReqTempDao
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.vo.airtime.AsyncReqTemp
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.vietpay;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AsyncReqTempDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VietPayTxnInquiryReqDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VietPayTxnInquiryReqDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            ContextBase ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_REQUEST_TXN_ID));
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VIETPAY_SETTINGS.getTxnInquiryOperation());
            IExecutor executor = ExecutorFactory.getInstance().getExecutor("VIETPAY");
            ContextBase ctxResult = executor.process(ctxRequest);
            context.put(Attributes.ATTR_RESULT_INFO, ctxResult.get(Attributes.ATTR_RESULT_INFO));
            context.put(Attributes.ATTR_ERROR_CODE, ctxResult.get(Attributes.ATTR_ERROR_CODE));
        }
        catch (Exception e) {
            log.error(("Unable to Inquiry VietPay TXN.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
        }
        AsyncReqTemp reqTemp = new AsyncReqTemp();
        reqTemp.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
        if ("SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            reqTemp.setStatus("SUCCESS");
            reqTemp.setValue((String)context.get(Attributes.ATTR_REQUEST_TXN_ID) + "|" + (String)context.get(Attributes.ATTR_RESULT_INFO));
        } else {
            reqTemp.setStatus("ERROR");
            reqTemp.setErrorCode((String)context.get(Attributes.ATTR_ERROR_CODE));
        }
        try {
            AsyncReqTempDao asyncReqTempDao = new AsyncReqTempDao();
            asyncReqTempDao.update(reqTemp);
        }
        catch (Exception e) {
            log.error("Fail to update Txn Inquiry request to database.", (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            return false;
        }
        return true;
    }
}

