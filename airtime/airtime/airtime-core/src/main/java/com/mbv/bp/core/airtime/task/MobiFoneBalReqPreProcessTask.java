/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.AsyncReqTempDao
 *  com.mbv.bp.common.generator.IdGenerator
 *  com.mbv.bp.common.generator.IdGeneratorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.common.vo.airtime.AsyncReqTemp
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.AsyncReqTempDao;
import com.mbv.bp.common.generator.IdGenerator;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.core.workflow.Task;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobiFoneBalReqPreProcessTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)MobiFoneBalReqPreProcessTask.class);

    public boolean execute(ContextBase context) throws Exception {
        context.put(Attributes.ATTR_ERROR_CODE,"SUCCESS");
        context.put(Attributes.ATTR_IS_REQUEST_DB_PROCESSED,"N");
        context.put(Attributes.ATTR_QUEUE_ID,AppConstants.MOBIFONE_SETTINGS.getDeliveryQueueId());
        context.put(Attributes.ATTR_DEST_WFP,AppConstants.MOBIFONE_SETTINGS.getBalQueryDeliveryWfp());
        context.put(Attributes.ATTR_TXN_STATUS,"PENDING");
        Date txnDate = new Date();
        context.putDate(Attributes.ATTR_TRANSACTION_DATE, txnDate);
        String seq = IdGeneratorFactory.getInstance().getAirTimeIdGenentor().generateId();
        context.put(Attributes.ATTR_TRANSACTION_ID,AppUtils.getAtTxnId((Date)txnDate, (String)seq));
        AsyncReqTemp reqTemp = new AsyncReqTemp();
        reqTemp.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
        reqTemp.setErrorCode("SUCCESS");
        reqTemp.setStatus((String)context.get(Attributes.ATTR_TXN_STATUS));
        reqTemp.setTxnDate(txnDate);
        reqTemp.setOperatorType(AppConstants.MOBIFONE_SETTINGS.getBalanceInquiryOpeation());
        try {
            AsyncReqTempDao reqTempDao = new AsyncReqTempDao();
            reqTempDao.insert(reqTemp);
        }
        catch (Exception e) {
            log.error("Fail to update bal request to database.", (Throwable)e);
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            return false;
        }
        return true;
    }
}

