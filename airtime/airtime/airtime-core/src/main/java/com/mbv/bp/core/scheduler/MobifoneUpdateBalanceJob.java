/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.scheduler;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.core.scheduler.AbstractJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobifoneUpdateBalanceJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)MobifoneUpdateBalanceJob.class);

    @Override
    public void execute() {
        if (!this.isEnable()) {
            this.log.info(("no process for this job | enable-" + this.isEnable()));
            return;
        }
        ContextBase context = new ContextBase();
        try {
            context.put(Attributes.ATTR_ERROR_CODE,"SUCCESS");
            context.put(Attributes.ATTR_WFP_NAME,"wfpMobiFoneBalanceRequestPushToQueue");
            IExecutor executor = ExecutorFactory.getInstance().getExecutor("WorkFlowConnection");
            context = executor.process(context);
        }
        catch (Exception e) {
            this.log.error(("Fail to process request.| errorCode-" + context.getErrorCode() + "| context-" +context), (Throwable)e);
        }
    }
}

