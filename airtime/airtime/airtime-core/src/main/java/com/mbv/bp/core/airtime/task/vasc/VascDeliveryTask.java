/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.client.VascConnectionFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.VascSettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.vasc;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.client.VascConnectionFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.VascSettings;
import com.mbv.bp.core.airtime.util.CoreUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VascDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VascDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        boolean isOk = false;
        for (int i = 0; i < 3; ++i) {
            try {
                ContextBase ctxRequest = new ContextBase();
                ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VASC_SETTINGS.getCheckConnectionOperation());
                ContextBase ctxResult = VascConnectionFactory.getInstance().process(ctxRequest);
                if (!"SUCCESS".equalsIgnoreCase(ctxResult.getErrorCode())) continue;
                isOk = true;
                break;
            }
            catch (Exception e) {
                log.error(("Unable to delivery Vasc Request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            }
        }
        if (!isOk) {
            context.setErrorCode("CONNECTION_FAILED");
            return false;
        }
        if (!CoreUtils.isVaildTimeOut(context, true)) {
            return false;
        }
        try {
            ContextBase ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VASC_SETTINGS.getTopupOperation());
            ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
            ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
            ctxRequest.put(Attributes.ATTR_AMOUNT, context.get(Attributes.ATTR_AMOUNT));
            ctxRequest.put(Attributes.ATTR_IS_CDR_FORWARD,"Y");
            ContextBase ctxResult = VascConnectionFactory.getInstance().process(ctxRequest);
            context.putAll((Map)ctxResult);
        }
        catch (Exception e) {
            log.error(("Unable to delivery Vasc Request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if ("CONNECTION_FAILED".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            context.put(Attributes.ATTR_ERROR_CODE,"DELIVERY_RESPONSE_ERROR");
        }
        if (!"SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            return false;
        }
        return true;
    }
}

