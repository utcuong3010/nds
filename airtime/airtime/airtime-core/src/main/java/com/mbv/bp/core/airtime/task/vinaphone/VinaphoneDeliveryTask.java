/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.client.VinaphoneConnectionFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.VinaphoneSettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task.vinaphone;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.client.VinaphoneConnectionFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.VinaphoneSettings;
import com.mbv.bp.core.airtime.util.CoreUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VinaphoneDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VinaphoneDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        boolean isOk = false;
        for (int i = 0; i < 3; ++i) {
            try {
                ContextBase ctxRequest = new ContextBase();
                ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VINAPHONE_SETTINGS.getLoginOperation());
                ContextBase ctxResult = VinaphoneConnectionFactory.getInstance().process(ctxRequest);
                if (!"SUCCESS".equalsIgnoreCase(ctxResult.getErrorCode())) continue;
                if (AppConstants.VINAPHONE_SETTINGS.isSuccessResponse((String)ctxResult.get(Attributes.ATTR_RESPONSE_CODE))) {
                    context.put(Attributes.ATTR_SESSION_ID, ctxResult.get(Attributes.ATTR_SESSION_ID));
                    isOk = true;
                    break;
                }
                log.info(("Fail to login| context:" +ctxResult));
                break;
            }
            catch (Exception e) {
                log.error(("Unable to delivery Vinaphone Request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
            }
        }
        if (!isOk) {
            context.setErrorCode("UNABLE_LOGIN");
            return false;
        }
        if (!CoreUtils.isVaildTimeOut(context, true)) {
            return false;
        }
        try {
            ContextBase ctxRequest = new ContextBase();
            ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VINAPHONE_SETTINGS.getTopupOperation());
            ctxRequest.put(Attributes.ATTR_TRANSACTION_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
            ctxRequest.put(Attributes.ATTR_MSISDN, context.get(Attributes.ATTR_MSISDN));
            ctxRequest.put(Attributes.ATTR_AMOUNT, context.get(Attributes.ATTR_AMOUNT));
            ctxRequest.put(Attributes.ATTR_SESSION_ID, context.get(Attributes.ATTR_SESSION_ID));
            ctxRequest.put(Attributes.ATTR_IS_CDR_FORWARD,"Y");
            ContextBase ctxResult = VinaphoneConnectionFactory.getInstance().process(ctxRequest);
            context.putAll((Map)ctxResult);
        }
        catch (Exception e) {
            log.error(("Unable to delivery Vinaphone Request.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
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

