/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.VtcSettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.vtc;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.VtcSettings;
import com.mbv.bp.core.airtime.util.CoreUtils;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VtcDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VtcDeliveryTask.class);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean execute(ContextBase context) throws Exception {
        ContextBase ctxResult;
        String[] results;
        ContextBase ctxRequest;
        IExecutor executor;
        try {
            ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VTC_SETTINGS.getCheckAccountOperation());
            ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
            executor = ExecutorFactory.getInstance().getExecutor("VTC");
            ctxResult = executor.process(ctxRequest);
            if (!"SUCCESS".equalsIgnoreCase(ctxResult.getErrorCode())) throw new Exception("Fail to Check Account from Vtc.| context:" +ctxRequest);
            if (!AppConstants.VTC_SETTINGS.verifyResult((String)ctxResult.get(Attributes.ATTR_RESULT_INFO))) {
                context.setErrorCode("UNABLE_CHECK_ACCOUNT");
                log.error("Fail to verify response signature");
                return false;
            }
            results = StringUtils.split((String)((String)ctxResult.get(Attributes.ATTR_RESULT_INFO)), (String)"|");
            if (!AppConstants.VTC_SETTINGS.isSuccessResponse(results[0])) {
                context.setErrorCode("UNABLE_CHECK_ACCOUNT");
                context.put(Attributes.ATTR_RESPONSE_CODE,results[0]);
                return false;
            }
        }
        catch (Exception e) {
            log.error(e);
            context.setErrorCode("UNABLE_CHECK_ACCOUNT");
            return false;
        }
        if (!CoreUtils.isVaildTimeOut(context, true)) {
            return false;
        }
        try {
            ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VTC_SETTINGS.getTopupGameOperation());
            ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
            ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
            ctxRequest.put(Attributes.ATTR_AMOUNT, context.get(Attributes.ATTR_AMOUNT));
            ctxRequest.put(Attributes.ATTR_TRANSACTION_DATE, context.get(Attributes.ATTR_TRANSACTION_DATE));
            ctxRequest.put(Attributes.ATTR_IS_CDR_FORWARD,"Y");
            executor = ExecutorFactory.getInstance().getExecutor("VTC");
            ctxResult = executor.process(ctxRequest);
            context.setErrorCode(ctxResult.getErrorCode());
            if ("SUCCESS".equalsIgnoreCase(ctxResult.getErrorCode())) {
                if (!AppConstants.VTC_SETTINGS.verifyResult((String)ctxResult.get(Attributes.ATTR_RESULT_INFO))) {
                    context.setErrorCode("DELIVERY_RESPONSE_ERROR");
                    log.error("Fail to verify response signature");
                    return false;
                }
                results = StringUtils.split((String)((String)ctxResult.get(Attributes.ATTR_RESULT_INFO)), (String)"|");
                long resultCode = Long.valueOf(results[0]);
                context.putLong(Attributes.ATTR_RESPONSE_CODE, resultCode);
            }
        }
        catch (Exception e) {
            log.error(("Unable to delivery Vtc Request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if ("CONNECTION_FAILED".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if ("SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) return true;
        return false;
    }
}

