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
import com.mbv.bp.core.airtime.util.CoreUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneGetNextIdTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFoneGetNextIdTask.class);

    public boolean execute(ContextBase context) throws Exception {
        context.remove(Attributes.ATTR_NEXT_MESSAGE_ID);
        context.remove(Attributes.ATTR_RESULT_NAMESPACE);
        context.remove(Attributes.ATTR_RESPONSE_CODE);
        context.remove(Attributes.ATTR_MESSAGE_ID);
        context.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.MOBIFONE_SETTINGS.getNextIdOperation());
        int retry = 0;
        while (CoreUtils.isVaildTimeOut(context, true) && retry < AppConstants.MOBIFONE_SETTINGS.getNextIdMaxRetry()) {
            log.info(("Going to get Next TxnId from Mobifone.|retryTime-" + retry));
            ++retry;
            try {
                ContextBase ctxRequest = new ContextBase();
                ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.MOBIFONE_SETTINGS.getNextIdOperation());
                IExecutor executor = ExecutorFactory.getInstance().getExecutor("MOBI");
                ContextBase ctxResult = executor.process(ctxRequest);
                context.putAll((Map)ctxResult);
                if (!"SUCCESS".equalsIgnoreCase((String)ctxResult.get(Attributes.ATTR_ERROR_CODE))) continue;
                if ("0".equalsIgnoreCase(context.getString(Attributes.ATTR_RESPONSE_CODE).trim())) break;
                ContextBase result = null;
                try {
                    log.info("Invalid Session. Going to reset session...");
                    ContextBase ctx = new ContextBase();
                    ctx.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.MOBIFONE_SETTINGS.getResetSessionOperation());
                    result = executor.process(ctx);
                    continue;
                }
                catch (Exception e) {
                    log.error(("Unable to Reset session.|context-" +result + "|Error message is:" + e.getMessage()), (Throwable)e);
                }
            }
            catch (Exception e) {
                log.error(("Unable to invoke Call Next ID.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
                context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
            }
        }
        if (!"SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            return false;
        }
        if (StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESPONSE_CODE)))) {
            if (context.getInt(Attributes.ATTR_RESPONSE_CODE) != 0) {
                context.put(Attributes.ATTR_ERROR_CODE,"UNABLE_GET_NEXT_ID");
                return false;
            }
        } else {
            context.put(Attributes.ATTR_ERROR_CODE,"UNABLE_GET_NEXT_ID");
            return false;
        }
        return true;
    }
}

