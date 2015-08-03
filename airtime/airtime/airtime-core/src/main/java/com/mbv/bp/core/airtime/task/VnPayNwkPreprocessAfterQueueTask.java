/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.common.util.VnPayUtils
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.util.VnPayUtils;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VnPayNwkPreprocessAfterQueueTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VnPayNwkPreprocessAfterQueueTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            String message = VnPayUtils.buildNetworkMsg((String)AppConstants.VNPAY_SETTINGS.getNetworkCheckingCode());
            context.put(Attributes.ATTR_REQUEST_MESSAGE,message);
        }
        catch (Exception e) {
            context.setErrorCode("INVALID_REQUEST");
            return false;
        }
        return true;
    }
}

