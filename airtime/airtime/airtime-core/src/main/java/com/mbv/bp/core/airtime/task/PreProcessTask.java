/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.generator.IdGenerator
 *  com.mbv.bp.common.generator.IdGeneratorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.AnypaySettings
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.settings.VascSettings
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.settings.ViettelSettings
 *  com.mbv.bp.common.settings.VinaphoneSettings
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.common.settings.VtcSettings
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.core.workflow.Task;

public class PreProcessTask
implements Task {
    private static final Log log = LogFactory.getLog(PreProcessTask.class);

    @Override
	public boolean execute(ContextBase context) throws Exception {
        context.put(Attributes.ATTR_IS_REQUEST_DB_PROCESSED,"N");
        String providerId = context.get(Attributes.ATTR_CONN_TYPE);
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
            if (StringUtils.isNotBlank((context.get(Attributes.ATTR_SERVER_ID)))) {
                context.put(Attributes.ATTR_SERVER_ID,AppConstants.VIETPAY_SETTINGS.getGamseServerId(context.get(Attributes.ATTR_SERVER_ID)));
            }
        } else if (AppConstants.VIETTEL_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VIETTEL_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VIETTEL_SETTINGS.getDeliveryWfp());
            context.put(Attributes.ATTR_MESSAGE_ID,AppUtils.getViettelId(context.getDate(Attributes.ATTR_TRANSACTION_DATE), IdGeneratorFactory.getInstance().getVnPayIdGenentor().generateId()));
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VIETTEL_SETTINGS.getTxnTimeOut() - AppConstants.VIETTEL_SETTINGS.getProcessFinalStatusTime();
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.VTC_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VTC_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VTC_SETTINGS.getDeliveryWfp());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VTC_SETTINGS.getResponseTimeOut() * 4;
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.ANYPAY_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.ANYPAY_SETTINGS.getDeliveryQueueId());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.ANYPAY_SETTINGS.getResponseTimeOut() * 8;
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.VASC_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VASC_SETTINGS.getDeliveryQueueId());
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VASC_SETTINGS.getDeliveryWfp());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VASC_SETTINGS.getResponseTimeOut() * 4;
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if (AppConstants.VINAPHONE_SETTINGS.getConnectionType().equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,AppConstants.VINAPHONE_SETTINGS.getDeliveryQueueId());
            int timeOut = context.getInt(Attributes.ATTR_TIME_OUT) - AppConstants.VINAPHONE_SETTINGS.getResponseTimeOut() * 4;
            context.put(Attributes.ATTR_DEST_WFP,AppConstants.VINAPHONE_SETTINGS.getDeliveryWfp());
            context.putInt(Attributes.ATTR_TIME_OUT, timeOut);
        } else if ("VNMOBILE".equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,"VNMOBILE");
        } else if ("GMOBILE".equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,"GMOBILE");
        } else if ("EPAY".equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,"EPAY");
        } else if ("XPAY".equalsIgnoreCase(providerId)) {
            context.put(Attributes.ATTR_QUEUE_ID,"XPAY");
        } else {
            context.put(Attributes.ATTR_ERROR_CODE,"SYS_INTERNAL_ERROR");
            log.error(("Missing config for telcoId-" + context.get(Attributes.ATTR_TELCO_ID)));
            return false;
        }
        context.put(Attributes.ATTR_TXN_STATUS,"PENDING");
        return true;
    }
}

