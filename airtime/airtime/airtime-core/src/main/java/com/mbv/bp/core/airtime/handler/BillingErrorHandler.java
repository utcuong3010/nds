/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.airtime.manager.AmountManager
 *  com.mbv.bp.common.generator.IdGenerator
 *  com.mbv.bp.common.generator.IdGeneratorFactory
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.core.workflow.Handler
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.handler;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.airtime.manager.AmountManager;
import com.mbv.bp.common.generator.IdGenerator;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.core.workflow.Handler;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BillingErrorHandler
extends Handler {
    private static Log log = LogFactory.getLog((Class)BillingErrorHandler.class);

    public boolean handle(ContextBase context, Exception exception) {
        boolean result = true;
        try {
            log.info(("Going to process error handle for workflow - " + (String)context.get(Attributes.ATTR_WFP_NAME)));
            String errorCode = "SUCCESS";
            AmountManager amountManager = new AmountManager();
            try {
                if ("Y".equalsIgnoreCase((String)context.get(Attributes.ATTR_RESERVED_BILLING))) {
                    String seq = IdGeneratorFactory.getInstance().getAirTimeIdGenentor().generateId();
                    errorCode = amountManager.debitLockAccount("AT_TXN", AppUtils.getAtTxnId((Date)context.getDate(Attributes.ATTR_TRANSACTION_DATE), (String)seq), (String)context.get(Attributes.ATTR_CHANNEL_ID), (String)context.get(Attributes.ATTR_RESERVED_ID), (String)context.get(Attributes.ATTR_TRANSACTION_ID), context.getLong(Attributes.ATTR_AMOUNT), "ERROR");
                    if (!"SUCCESS".equalsIgnoreCase(errorCode)) {
                        throw new Exception("Fail to process debit");
                    }
                }
            }
            catch (Exception e) {
                result = false;
                log.error(("Fail to process relock|context : " +context), (Throwable)e);
            }
            errorCode = "SUCCESS";
            try {
                if ("Y".equalsIgnoreCase((String)context.get(Attributes.ATTR_PROVIDER_BILLING)) && !"SUCCESS".equalsIgnoreCase(errorCode = amountManager.providerRefund((String)context.get(Attributes.ATTR_CONN_TYPE), context.getLong(Attributes.ATTR_AMOUNT)))) {
                    throw new Exception("Fail to process refund");
                }
            }
            catch (Exception e) {
                result = false;
                log.error(("Fail to process refund|context : " +context), (Throwable)e);
            }
        }
        catch (Exception e) {
            log.error(("Fail to Handle Error.| ErrMsg-" + e.getMessage()), (Throwable)e);
            result = false;
        }
        return result;
    }
}

