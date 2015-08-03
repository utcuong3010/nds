/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.ProviderAmountDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.util.DateUtils
 *  com.mbv.bp.common.util.DateUtils$Operation
 *  com.mbv.bp.common.util.DateUtils$Type
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFonePreprocessAfterQueueTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFonePreprocessAfterQueueTask.class);

    public boolean execute(ContextBase context) throws Exception {
        int timeOut = context.getInt(Attributes.ATTR_TIME_OUT);
        if (timeOut < 0) {
            context.put(Attributes.ATTR_ERROR_CODE,"REQUEST_TIME_OUT");
            return false;
        }
        Date txnDate = context.getDate(Attributes.ATTR_TRANSACTION_DATE);
        Date timeOutDate = DateUtils.dateAdd((Date)txnDate, (long)timeOut, (DateUtils.Type)DateUtils.Type.BY_MILLISECOND, (DateUtils.Operation)DateUtils.Operation.PLUS);
        if (timeOutDate.before(new Date())) {
            context.put(Attributes.ATTR_ERROR_CODE,"REQUEST_TIME_OUT");
            return false;
        }
        if (AppConstants.MOBIFONE_SETTINGS.isAmountValidate()) {
            ProviderAmountDao amountDao = new ProviderAmountDao();
            Long currentAmount = amountDao.selectAmount((String)context.get(Attributes.ATTR_CONN_TYPE));
            log.info(("current " + (String)context.get(Attributes.ATTR_CONN_TYPE) + " amount: " + currentAmount));
            if (currentAmount != null && context.getLong(Attributes.ATTR_AMOUNT) > currentAmount) {
                context.put(Attributes.ATTR_ERROR_CODE,"SYS_NOT_ENOUGH_MONEY");
                return false;
            }
        }
        context.put(Attributes.ATTR_IS_REQ_DB_DELIVERING_PROCESSED,"N");
        context.put(Attributes.ATTR_IS_REQ_DB_DELIVERED_PROCESSED,"N");
        context.putDate(Attributes.ATTR_DELIVERY_DATE, new Date());
        return true;
    }
}

