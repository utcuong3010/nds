/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.DaoException
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.model.ErrorConversion
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.common.vo.airtime.AtTransaction
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.ErrorConversion;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.workflow.Task;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidateAirTimeRequest
implements Task {
    private static final Log log = LogFactory.getLog((Class)ValidateAirTimeRequest.class);

    public boolean execute(ContextBase context) throws Exception {
        try {
            AtTransactionDao atTransactionDao = new AtTransactionDao();
            AtTransaction atTxn = new AtTransaction();
            atTxn.setTxnId((String)context.get(Attributes.ATTR_REQUEST_TXN_ID));
            atTxn.setChannelId((String)context.get(Attributes.ATTR_CHANNEL_ID));
            if (atTransactionDao.select(atTxn)) {
                context.put(Attributes.ATTR_TXN_STATUS,atTxn.getTxnStatus());
                context.putLong(Attributes.ATTR_TRANSACTION_ID, atTxn.getAtTxnId().longValue());
                context.put(Attributes.ATTR_CONN_TYPE,atTxn.getConnType());
                if (AppConstants.VNPAY_SETTINGS.getConnectionType().equalsIgnoreCase(atTxn.getConnType()) && AppConstants.VNPAY_SETTINGS.getResponseErrors().containsKey(atTxn.getErrorCode())) {
                    atTxn.setErrorCode(((ErrorConversion)AppConstants.VNPAY_SETTINGS.getResponseErrors().get(atTxn.getErrorCode())).getDestError());
                }
                context.put(Attributes.ATTR_TXN_ERROR_CODE,atTxn.getErrorCode());
                return false;
            }
        }
        catch (DaoException e) {
            context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
            log.error(("Fail to insert airtime request.| error-" + context.getErrorCode()), (Throwable)e);
            return false;
        }
        return true;
    }
}

