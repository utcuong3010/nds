/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.forward.MobifoneCdrForward
 *  com.mbv.bp.common.forward.VascCdrForward
 *  com.mbv.bp.common.forward.VietPayCdrForward
 *  com.mbv.bp.common.forward.VinaphoneCdrForward
 *  com.mbv.bp.common.forward.VnPayCdrForward
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.settings.VascSettings
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.settings.VinaphoneSettings
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.forward.MobifoneCdrForward;
import com.mbv.bp.common.forward.VascCdrForward;
import com.mbv.bp.common.forward.VietPayCdrForward;
import com.mbv.bp.common.forward.VinaphoneCdrForward;
import com.mbv.bp.common.forward.VnPayCdrForward;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.settings.VascSettings;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.settings.VinaphoneSettings;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.core.workflow.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneProcessCdrTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFoneProcessCdrTask.class);

    public boolean execute(ContextBase context) throws Exception {
        log.info("Going to process Cdr");
        if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            return MobifoneCdrForward.getInstance().process(context);
        }
        if (AppConstants.VNPAY_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            return VnPayCdrForward.getInstance().process(context);
        }
        if (AppConstants.VIETPAY_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            return VietPayCdrForward.getInstance().process(context);
        }
        if (AppConstants.VINAPHONE_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            return VinaphoneCdrForward.getInstance().process(context);
        }
        if (AppConstants.VASC_SETTINGS.getConnectionType().equalsIgnoreCase((String)context.get(Attributes.ATTR_CONN_TYPE))) {
            return VascCdrForward.getInstance().process(context);
        }
        log.error("Unsupported Cdr Forward Operation");
        return false;
    }
}

