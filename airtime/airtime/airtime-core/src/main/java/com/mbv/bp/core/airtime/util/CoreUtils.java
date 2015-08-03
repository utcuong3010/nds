/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.executor.ExecutorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.integration.IExecutor
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.util.DateUtils
 *  com.mbv.bp.common.util.DateUtils$Operation
 *  com.mbv.bp.common.util.DateUtils$Type
 *  org.apache.commons.lang.StringUtils
 */
package com.mbv.bp.core.airtime.util;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.util.DateUtils;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class CoreUtils {
    public static boolean isVaildTimeOut(ContextBase context, boolean isCtxModifiable) {
        int timeOut = context.getInt(Attributes.ATTR_TIME_OUT);
        if (timeOut < 0) {
            if (isCtxModifiable) {
                context.put(Attributes.ATTR_ERROR_CODE,"REQUEST_TIME_OUT");
            }
            return false;
        }
        Date txnDate = context.getDate(Attributes.ATTR_TRANSACTION_DATE);
        Date timeOutDate = DateUtils.dateAdd((Date)txnDate, (long)timeOut, (DateUtils.Type)DateUtils.Type.BY_MILLISECOND, (DateUtils.Operation)DateUtils.Operation.PLUS);
        if (timeOutDate.before(new Date())) {
            if (isCtxModifiable) {
                context.put(Attributes.ATTR_ERROR_CODE,"REQUEST_TIME_OUT");
            }
            return false;
        }
        return true;
    }

    public static boolean isVaildTimeOut(ContextBase context, int timeOut) {
        Date txnDate = context.getDate(Attributes.ATTR_TRANSACTION_DATE);
        Date timeOutDate = DateUtils.dateAdd((Date)txnDate, (long)timeOut, (DateUtils.Type)DateUtils.Type.BY_MILLISECOND, (DateUtils.Operation)DateUtils.Operation.PLUS);
        if (timeOutDate.before(new Date())) {
            return false;
        }
        return true;
    }

    public static long getVietPayBalance() throws Exception {
        ContextBase ctxRequest = new ContextBase();
        ctxRequest.put(Attributes.ATTR_OPERATION_TYPE,AppConstants.VIETPAY_SETTINGS.getBalanceOperation());
        IExecutor executor = ExecutorFactory.getInstance().getExecutor("VIETPAY");
        ContextBase ctxResult = executor.process(ctxRequest);
        if ("SUCCESS".equalsIgnoreCase(ctxResult.getErrorCode())) {
            String[] results = StringUtils.split((String)((String)ctxResult.get(Attributes.ATTR_RESULT_INFO)), (String)"|");
            return Long.valueOf(results[1]);
        }
        throw new Exception("Fail to query balance from VietPay.| context:" +ctxRequest);
    }
}

