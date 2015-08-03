/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.core.workflow.Handler
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.handler;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.core.workflow.Handler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneChangePwdErrorHandler
extends Handler {
    private static Log log = LogFactory.getLog((Class)MobiFoneChangePwdErrorHandler.class);

    public boolean handle(ContextBase context, Exception exception) {
        if (!"SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            log.error(("Request failed with error code:" + (String)context.get(Attributes.ATTR_ERROR_CODE) + "| context-" +context), (Throwable)exception);
            this.errorHandler(context, exception);
        } else if (exception != null) {
            log.error(("Exception captured. | context-" +context), (Throwable)exception);
            this.errorHandler(context, exception);
        }
        return true;
    }

    private void errorHandler(ContextBase context, Exception exception) {
    }
}

