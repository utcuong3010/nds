/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.DaoException
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.vo.airtime.AtTransaction
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.workflow.Task;

public class DbProcessTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)DbProcessTask.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            AtTransactionDao atTransactionDao = new AtTransactionDao();
            AtTransaction atTxn = new AtTransaction();
            atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
            atTxn.setAtTxnId(Long.valueOf(context.getLong(Attributes.ATTR_TRANSACTION_ID)));
            atTxn.setTxnDate(context.getDate(Attributes.ATTR_TRANSACTION_DATE));
            atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
            atTxn.setMsisdn((String)context.get(Attributes.ATTR_MSISDN));
            atTxn.setTelcoId((String)context.get(Attributes.ATTR_TELCO_ID));
            atTxn.setTimeOut(Integer.valueOf(context.getInt(Attributes.ATTR_TIME_OUT)));
            atTxn.setAmount(Integer.valueOf(context.getInt(Attributes.ATTR_AMOUNT)));
            atTxn.setConnType((String)context.get(Attributes.ATTR_CONN_TYPE));
            atTxn.setMsgType((String)context.get(Attributes.ATTR_MESSAGE_TYPE));
            atTxn.setMessageId((String)context.get(Attributes.ATTR_MESSAGE_ID));
            atTxn.setStatus((String)context.get(Attributes.ATTR_TXN_STATUS));
            atTxn.setTxnStatus("PENDING");
            atTxn.setErrorCode("00");
            atTxn.setCreatedBy(AppConstants.APP_ID);
            atTxn.setCreatedDate(new Date());
            atTxn.setTxnType((String)context.get(Attributes.ATTR_TXN_TYPE));
            if (StringUtils.isNotBlank((String)((String)context.get(Attributes.ATTR_SERVER_ID)))) {
                atTxn.setServerId((String)context.get(Attributes.ATTR_SERVER_ID));
            }
            atTransactionDao.insert(atTxn);
            context.put(Attributes.ATTR_IS_REQUEST_DB_PROCESSED,"Y");
        }
        catch (DaoException e) {
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            log.error(("Fail to insert airtime request.| error-" + context.getErrorCode()), (Throwable)e);
            return false;
        }
        return true;
    }
}

