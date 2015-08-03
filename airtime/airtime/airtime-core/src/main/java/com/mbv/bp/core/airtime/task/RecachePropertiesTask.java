/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.mobifone.MobifoneConnectionProperty
 *  com.mbv.bp.common.executor.viettel.ViettelConnectionProperty
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.mobifone.MobifoneConnectionProperty;
import com.mbv.bp.common.executor.viettel.ViettelConnectionProperty;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RecachePropertiesTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)RecachePropertiesTask.class);

    /*
     * Enabled aggressive block sorting
     */
    public boolean execute(ContextBase context) throws Exception {
        context.setErrorCode("SUCCESS");
        if ("MOBIFONE_CONN_PROP_RECACHE".equalsIgnoreCase((String)context.get(Attributes.ATTR_RECACHE_TYPE))) {
            log.info("Going to recache Mobifone connection properties");
            String newUrl = (String)context.get(Attributes.ATTR_URL);
            if (StringUtils.isNotBlank((String)newUrl)) {
                MobifoneConnectionProperty.getInstance().setProperties(newUrl);
                return true;
            }
            context.setErrorCode("INVALID_REQUEST");
            return false;
        }
        if ("VIETTEL_CONN_PROP_RECACHE".equalsIgnoreCase((String)context.get(Attributes.ATTR_RECACHE_TYPE))) {
            String host = context.getString(Attributes.ATTR_HOST);
            int port = context.getInt(Attributes.ATTR_PORT, -1);
            if (StringUtils.isNotBlank((String)host) && port != -1) {
                ViettelConnectionProperty.getInstance().setProperties(host, port);
                return true;
            }
            context.setErrorCode("INVALID_REQUEST");
            return false;
        }
        if ("TELCO_PROVIDER_RECACHE".equalsIgnoreCase((String)context.get(Attributes.ATTR_RECACHE_TYPE))) {
            boolean result = AppConstants.reload();
            if (result) return true;
            context.setErrorCode("OPERATION_FAILED");
            return result;
        }
        if ("NOTIFICATION_TEMPLATE_RECACHE".equalsIgnoreCase((String)context.get(Attributes.ATTR_RECACHE_TYPE))) {
            boolean result = AppConstants.reloadNotifTemplate();
            if (result) return true;
            context.setErrorCode("OPERATION_FAILED");
            return result;
        }
        context.setErrorCode("UNSUPPORTED_OPERATION");
        return false;
    }
}

