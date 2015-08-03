/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.generator.IdGenerator
 *  com.mbv.bp.common.generator.IdGeneratorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.generator.IdGenerator;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ViettelNwkPreprocessTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)ViettelNwkPreprocessTask.class);

    public boolean execute(ContextBase context) throws Exception {
        context.put(Attributes.ATTR_DEST_WFP,AppConstants.VNPAY_SETTINGS.getNetworkDeliveryWfp());
        context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VNPAY_SETTINGS.getDeliveryQueueId());
        context.put(Attributes.ATTR_CONN_TYPE,AppConstants.VNPAY_SETTINGS.getConnectionType());
        String seq = IdGeneratorFactory.getInstance().getAirTimeIdGenentor().generateId();
        context.put(Attributes.ATTR_TRANSACTION_ID,AppUtils.getAtTxnId((Date)new Date(), (String)seq));
        context.put(Attributes.ATTR_QUEUE_REQUEST_ID, context.get(Attributes.ATTR_TRANSACTION_ID));
        return true;
    }
}

