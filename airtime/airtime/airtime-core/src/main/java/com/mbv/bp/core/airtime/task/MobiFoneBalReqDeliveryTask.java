/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AsyncReqTempDao
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.vo.airtime.AsyncReqTemp
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.google.gson.Gson;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AsyncReqTempDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneBalReqDeliveryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFoneBalReqDeliveryTask.class);

    public boolean execute(ContextBase context) throws Exception {
        for (int retry = 0; retry < 3; ++retry) {
            try {
                ContextBase ctxRequest = new ContextBase();
                ctxRequest.put(Attributes.ATTR_TRANSACTION_DATE, context.get(Attributes.ATTR_TRANSACTION_DATE));
                ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.MOBIFONE_SETTINGS.getBalanceInquiryOpeation());
                IExecutor executor = ExecutorFactory.getInstance().getExecutor("MOBI");
                ContextBase ctxResult = executor.process(ctxRequest);
                context.putAll((Map)ctxResult);
                if (!"SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) continue;
                break;
            }
            catch (Exception e) {
                log.error(("Unable to Call transquery to Mobifone Ws.|context-" +context + "|Error message is:" + e.getMessage()), (Throwable)e);
                context.put(Attributes.ATTR_ERROR_CODE,"CONNECTION_FAILED");
                continue;
            }
        }
        Gson gson = new Gson();
        AsyncReqTemp reqTemp = new AsyncReqTemp();
        reqTemp.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
        if ("SUCCESS".equalsIgnoreCase((String)context.get(Attributes.ATTR_ERROR_CODE))) {
            if (StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_RESPONSE_CODE))) && context.getInt(Attributes.ATTR_RESPONSE_CODE) == 0) {
                reqTemp.setStatus("SUCCESS");
                reqTemp.setValue((String)context.get(Attributes.ATTR_BALANCE_INQUIRY_RESULT));
            } else {
                reqTemp.setStatus("ERROR");
                reqTemp.setErrorCode(context.getString(Attributes.ATTR_RESULT_NAMESPACE).toUpperCase() + "_" + context.getString(Attributes.ATTR_RESPONSE_CODE));
            }
        } else {
            reqTemp.setStatus("ERROR");
            reqTemp.setErrorCode((String)context.get(Attributes.ATTR_ERROR_CODE));
        }
        try {
            AsyncReqTempDao asyncReqTempDao = new AsyncReqTempDao();
            asyncReqTempDao.update(reqTemp);
        }
        catch (Exception e) {
            log.error("Fail to update bal request to database.", (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            return false;
        }
        return true;
    }
}

