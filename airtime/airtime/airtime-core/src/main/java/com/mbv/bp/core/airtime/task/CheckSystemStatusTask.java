/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.core.airtime.task.AsynReqInquiryTxnTask;
import com.mbv.bp.core.workflow.Task;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckSystemStatusTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)AsynReqInquiryTxnTask.class);

    public boolean execute(ContextBase context) throws Exception {
        context.put(Attributes.ATTR_ERROR_CODE,"SUCCESS");
        if (AppConstants.SYSTEM_IS_SHUTDOWN.get()) {
            context.put(Attributes.ATTR_ERROR_CODE,"SYSTEM_SHUTINGDOWN");
            return false;
        }
        return true;
    }
}

