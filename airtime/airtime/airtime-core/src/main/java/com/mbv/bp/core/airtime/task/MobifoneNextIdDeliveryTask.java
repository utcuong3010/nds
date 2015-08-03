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
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobifoneNextIdDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobifoneNextIdDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            ContextBase ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_TRANSACTION_DATE, context.get(Attributes.ATTR_TRANSACTION_DATE));
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.MOBIFONE_SETTINGS.getNextIdOperation());
            IExecutor executor = ExecutorFactory.getInstance().getExecutor("MOBI");
            ContextBase ctxResult = executor.process(ctxRequest);
            context.putAll((Map)ctxResult);
        }
        catch (Exception e) {
            log.error(("Unable to Call transquery to Mobifone Ws.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
        }
        return true;
    }
}

