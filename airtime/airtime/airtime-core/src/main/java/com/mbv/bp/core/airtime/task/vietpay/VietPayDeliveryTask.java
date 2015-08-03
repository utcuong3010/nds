/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.vietpay;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.core.airtime.util.CoreUtils;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VietPayDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VietPayDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        boolean isOk = false;
        for (int i = 0; i < 3; ++i) {
            try {
                context.putLong(Attributes.ATTR_BALANCE_BEFORE_TXN, CoreUtils.getVietPayBalance());
                isOk = true;
                break;
            }
            catch (Exception e) {
                log.error(e);
                continue;
            }
        }
        if (!isOk) {
            context.setErrorCode("UNABLE_QUERY_BALANCE");
            return isOk;
        }
        if (!CoreUtils.isVaildTimeOut(context, true)) {
            return false;
        }
        try {
            ContextBase ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VIETPAY_SETTINGS.getTopupOperation());
            ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
            ctxRequest.put(Attributes.ATTR_TELCO_ID, context.get(Attributes.ATTR_TELCO_ID));
            ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
            ctxRequest.put(Attributes.ATTR_SERVER_ID, context.get(Attributes.ATTR_SERVER_ID));
            ctxRequest.put(Attributes.ATTR_AMOUNT, context.get(Attributes.ATTR_AMOUNT));
            context.put(Attributes.ATTR_IS_CDR_FORWARD,"Y");
            IExecutor executor = ExecutorFactory.getInstance().getExecutor("VIETPAY");
            ContextBase ctxResult = executor.process(ctxRequest);
            context.setErrorCode(ctxResult.getErrorCode());
            if ("SUCCESS".equalsIgnoreCase(ctxResult.getErrorCode())) {
                String[] results = StringUtils.split((String)((String)ctxResult.get(Attributes.ATTR_RESULT_INFO)), (String)"|");
                long resultCode = Long.valueOf(results[0]);
                context.putLong(Attributes.ATTR_RESPONSE_CODE, resultCode);
            }
        }
        catch (Exception e) {
            log.error(("Unable to delivery VietPay Request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if ("CONNECTION_FAILED".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if (!"SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            return false;
        }
        return true;
    }
}

