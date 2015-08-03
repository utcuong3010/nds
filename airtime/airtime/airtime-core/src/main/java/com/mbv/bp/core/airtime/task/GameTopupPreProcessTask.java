/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.generator.IdGenerator
 *  com.mbv.bp.common.generator.IdGeneratorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.settings.ViettelSettings
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
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.settings.ViettelSettings;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GameTopupPreProcessTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)GameTopupPreProcessTask.class);

    public boolean execute(ContextBase context) throws Exception {
        context.put(Attributes.ATTR_IS_REQUEST_DB_PROCESSED,"N");
        String providerId = (String)context.get(Attributes.ATTR_CONN_TYPE);
        if (AppConstants.VNPAY_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VNPAY_SETTINGS.getDeliveryWfp());
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VNPAY_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_MESSAGE_ID,IdGeneratorFactory.getInstance().getVnPayIdGenentor().generateId());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VNPAY_SETTINGS.getResponseTimeOut() - AppConstants.VNPAY_SETTINGS.getInquiryTime();
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.MOBIFONE_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.MOBIFONE_SETTINGS.getDeliveryWfp());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.MOBIFONE_SETTINGS.getProcessFinalStatusTime() - AppConstants.MOBIFONE_SETTINGS.getResponseTimeOut() * 6;
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.VIETPAY_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VIETPAY_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VIETPAY_SETTINGS.getDeliveryWfp());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VIETPAY_SETTINGS.getResponseTimeOut() * 4;
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.VIETTEL_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VIETTEL_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VIETTEL_SETTINGS.getDeliveryWfp());
            context.put(Attributes.ATTR_MESSAGE_ID,AppUtils.getViettelId((Date)context.getDate(Attributes.ATTR_TRANSACTION_DATE), (String)IdGeneratorFactory.getInstance().getVnPayIdGenentor().generateId()));
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VIETTEL_SETTINGS.getTxnTimeOut() - AppConstants.VIETTEL_SETTINGS.getProcessFinalStatusTime();
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else {
            context.put(Attributes.ATTR_ERROR_CODE,"SYS_INTERNAL_ERROR");
            log.error(("Missing config for telcoId-" + (String)context.get(Attributes.ATTR_TELCO_ID)));
            return false;
        }
        context.put(Attributes.ATTR_TXN_STATUS,"PENDING");
        return true;
    }
}

