/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.model.Field
 *  com.mbv.bp.common.model.MessageInfo
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.common.util.VnPayUtils
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.Field;
import com.mbv.bp.common.model.MessageInfo;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.util.VnPayUtils;
import com.mbv.bp.core.airtime.task.VnPayDnProcessorDbProcessTask;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VnPayDnProcessorPreProcessTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)VnPayDnProcessorDbProcessTask.class);

    public boolean execute(ContextBase context) throws Exception {
        String message = context.getString(Attributes.ATTR_RESPONSE_MESSAGE);
        String responseCode = null;
        if (AppConstants.VNPAY_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            try {
                MessageInfo messageInfo = VnPayUtils.deSerialize((String)message);
                responseCode = ((Field)messageInfo.getFieldMaps().get(39)).getValue();
                if (StringUtils.isBlank((String)responseCode)) {
                    context.put(Attributes.ATTR_ERROR_CODE,"INVALID_RESPONSE");
                    throw new Exception("Invalid response");
                }
            }
            catch (Exception e) {
                context.put(Attributes.ATTR_ERROR_CODE,"INVALID_RESPONSE");
                log.error(("Fail to deserialize message| errMsg-" + e.getMessage()), (Throwable)e);
                return false;
            }
        }
        context.put(Attributes.ATTR_TXN_STATUS,"DELIVERED");
        context.put(Attributes.ATTR_RESPONSE_CODE,(StringUtils.repeat((String)"0", (int)(2 - responseCode.length())) + responseCode));
        return true;
    }
}

