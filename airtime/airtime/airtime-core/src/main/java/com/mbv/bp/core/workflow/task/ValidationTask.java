/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.workflow.task;

import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.core.workflow.Task;
import com.mbv.bp.core.workflow.task.GenerateTxnIdTask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidationTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)GenerateTxnIdTask.class);

    public boolean execute(ContextBase context) throws Exception {
        return true;
    }
}

