/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.exception.IntegrationException
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViettelDeliveryRequestTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)ViettelDeliveryRequestTask.class);

    public boolean execute(ContextBase context) throws Exception {
        boolean result = false;
        try {
            IExecutor executor = ExecutorFactory.getInstance().getExecutor((String)context.get(Attributes.ATTR_CONN_TYPE));
            ContextBase ctxResult = executor.process(context);
            context.putAll((Map)ctxResult);
            result = "SUCCESS".equalsIgnoreCase(context.getErrorCode());
        }
        catch (IntegrationException e) {
            log.error(("Fail to delivery request to Viettel server.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
        }
        return result;
    }
}

