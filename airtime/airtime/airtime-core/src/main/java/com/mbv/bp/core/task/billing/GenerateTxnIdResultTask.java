/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.task.billing;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GenerateTxnIdResultTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)GenerateTxnIdResultTask.class);

    public boolean execute(ContextBase context) throws Exception {
        String txnId = (String)context.get(Attributes.ATTR_TRANSACTION_ID);
        if (StringUtils.isNotBlank((String)txnId)) {
            String systemId = (String)context.get(Attributes.ATTR_CHANNEL_ID);
            systemId = systemId == null ? "" : systemId.trim() + ":";
            context.put(Attributes.ATTR_TRANSACTION_ID,(systemId + txnId));
            return true;
        }
        context.setErrorCode("SYS_INTERNAL_ERROR");
        return false;
    }
}

