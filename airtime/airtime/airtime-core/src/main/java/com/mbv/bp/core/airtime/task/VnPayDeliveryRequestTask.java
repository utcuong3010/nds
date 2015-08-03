/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.core.airtime.task.ViettelPreprocessAfterQueueTask;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VnPayDeliveryRequestTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)ViettelPreprocessAfterQueueTask.class);

    public boolean execute(ContextBase context) throws Exception {
        String errorCode = context.getErrorCode();
        if (!"SUCCESS".equalsIgnoreCase(context.getErrorCode())) {
            context.setErrorCode("SUCCESS");
        }
        try {
            IExecutor executor = ExecutorFactory.getInstance().getExecutor((String)context.get(Attributes.ATTR_CONN_TYPE));
            context = executor.process(context);
            context.putDate(Attributes.ATTR_RESPONSE_DATE, new Date());
            if (!"SUCCESS".equalsIgnoreCase(context.getErrorCode())) {
                return false;
            }
        }
        catch (Exception e) {
            log.error(("Unable to delivery Vnpay request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
            return false;
        }
        return true;
    }
}

