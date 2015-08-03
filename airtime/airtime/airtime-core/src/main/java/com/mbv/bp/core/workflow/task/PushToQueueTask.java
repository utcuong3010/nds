/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.AnypaySettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.workflow.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.AnypaySettings;
import com.mbv.bp.core.workflow.Task;
import com.mbv.bp.core.workflow.task.GenerateTxnIdTask;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PushToQueueTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)GenerateTxnIdTask.class);

    public boolean execute(ContextBase context) throws Exception {
        boolean status = false;
        String executorId = "QueueConnection";
        if (AppConstants.ANYPAY_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            executorId = "ANYPAY";
        }
        try {
            IExecutor executor = ExecutorFactory.getInstance().getExecutor(executorId);
            ContextBase result = executor.process(context);
            context.putAll((Map)result);
            status = "SUCCESS".equalsIgnoreCase(context.getErrorCode());
        }
        catch (Exception e) {
            log.error(("Unable to invoke PushToQueueTask.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
            status = false;
        }
        return status;
    }
}

