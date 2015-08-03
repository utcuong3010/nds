/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.core.airtime.task.MobiFoneGetNextIdTask;
import com.mbv.bp.core.airtime.util.CoreUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFoneDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        if (StringUtils.isBlank((String)((String)context.get(Attributes.ATTR_MOBI_BUY_TARGET)))) {
            context.put(Attributes.ATTR_MOBI_BUY_TARGET,AppConstants.MOBIFONE_SETTINGS.getBuyPrePaidTarget());
        }
        if (!CoreUtils.isVaildTimeOut(context, true)) {
            return false;
        }
        this.delivery(context);
        boolean result = this.processIfPostPaid(context);
        if (!result) {
            return result;
        }
        if ("CONNECTION_FAILED".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if (!"SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            return false;
        }
        return true;
    }

    private boolean processIfPostPaid(ContextBase context) {
        boolean isOk = true;
        if ("SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE)) && AppConstants.MOBIFONE_SETTINGS.isPostPaidErrror(context)) {
            MobiFoneGetNextIdTask task = new MobiFoneGetNextIdTask();
            try {
                isOk = task.execute(context);
            }
            catch (Exception e) {
                context.put(Attributes.ATTR_ERROR_CODE,"SYS_INTERNAL_ERROR");
                isOk = false;
                log.error(("Fail to invoke MobiFoneGetNextIdTask| context: " +context), (Throwable)e);
                return isOk;
            }
            if (isOk && CoreUtils.isVaildTimeOut(context, true)) {
                context.put(Attributes.ATTR_MOBI_BUY_TARGET,AppConstants.MOBIFONE_SETTINGS.getBuyPostPaidTarget());
                this.delivery(context);
            }
        }
        return isOk;
    }

    private void delivery(ContextBase context) {
        try {
            ContextBase ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_TRANSACTION_DATE,context.getString(Attributes.ATTR_TRANSACTION_DATE));
            ctxRequest.put(Attributes.ATTR_AMOUNT, context.get(Attributes.ATTR_AMOUNT));
            ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.MOBIFONE_SETTINGS.getBuyOperation());
            ctxRequest.put(Attributes.ATTR_MOBI_BUY_TARGET, context.get(Attributes.ATTR_MOBI_BUY_TARGET));
            ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
            IExecutor executor = ExecutorFactory.getInstance().getExecutor("MOBI");
            ContextBase ctxResult = executor.process(ctxRequest);
            context.putAll((Map)ctxResult);
        }
        catch (Exception e) {
            log.error(("Unable to delivery mobifone request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
        }
    }
}

