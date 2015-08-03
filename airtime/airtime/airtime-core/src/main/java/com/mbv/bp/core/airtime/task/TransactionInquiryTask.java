/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.constants.Attributes
 *  com.mbv.bp.common.dao.DaoException
 *  com.mbv.bp.common.dao.airtime.AtTransactionDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.model.ErrorConversion
 *  com.mbv.bp.common.settings.VnPaySettings
 *  com.mbv.bp.common.vo.airtime.AtTransaction
 *  com.mbv.bp.core.workflow.Task
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.airtime.task;

import com.google.gson.Gson;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.ErrorConversion;
import com.mbv.bp.common.settings.VnPaySettings;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.core.airtime.task.PreProcessTask;
import com.mbv.bp.core.workflow.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransactionInquiryTask
implements Task {
    private static final Log log = LogFactory.getLog((Class)PreProcessTask.class);

    public boolean execute(ContextBase context) throws Exception {
        context.put(Attributes.ATTR_ERROR_CODE,"SUCCESS");
        String txnIds = (String)context.get(Attributes.ATTR_REQUEST_TXN_ID_LIST);
        StringTokenizer tokenizer = new StringTokenizer(txnIds, ",");
        String key = "";
        ArrayList<Long> queryIds = new ArrayList<Long>();
        ArrayList queryIdsHistory = new ArrayList();
        HashMap<String, String> failIds = new HashMap<String, String>();
        Object housekeepingDate = null;
        String dateFormat = "yyyyMMdd";
        while (tokenizer.hasMoreTokens()) {
            key = tokenizer.nextToken().trim();
            if (!StringUtils.isNotEmpty((String)key)) continue;
            if (StringUtils.isNumeric((String)key) && key.length() == 18) {
                long id = Long.valueOf(key);
                if (queryIds.contains(id)) continue;
                queryIds.add(id);
                failIds.put(key, "TXN_NOT_EXISTED");
                continue;
            }
            failIds.put(key, "INVALID_TXN_ID");
        }
        if (queryIds.size() > 0 || queryIdsHistory.size() > 0) {
            List listResult = new ArrayList();
            ArrayList listResultHistory = new ArrayList();
            if (queryIds.size() > 0) {
                try {
                    AtTransaction atTxn = new AtTransaction();
                    atTxn.setAtTxnIdList(queryIds);
                    AtTransactionDao atTxnDao = new AtTransactionDao();
                    listResult = atTxnDao.selectByAtTxnIds(atTxn);
                }
                catch (DaoException e) {
                    context.put(Attributes.ATTR_ERROR_CODE,"DATABASE_EXCEPTION");
                    log.error(("Fail to select airtime request.| error-" + context.getErrorCode()), (Throwable)e);
                    return false;
                }
            }
            if (listResult.size() > 0) {
                for (int i = 0; i < listResult.size(); ++i) {
                    failIds.remove(String.valueOf(((AtTransaction)listResult.get(i)).getAtTxnId()));
                    String txnError = ((AtTransaction)listResult.get(i)).getErrorCode();
                    if (!AppConstants.VNPAY_SETTINGS.getConnectionType().equalsIgnoreCase(((AtTransaction)listResult.get(i)).getConnType()) || !AppConstants.VNPAY_SETTINGS.getResponseErrors().containsKey(txnError)) continue;
                    ((AtTransaction)listResult.get(i)).setErrorCode(((ErrorConversion)AppConstants.VNPAY_SETTINGS.getResponseErrors().get(txnError)).getDestError());
                }
                Gson gson = new Gson();
                context.put(Attributes.ATTR_AIRTIME_TXN_INQUIRY_RESULT,gson.toJson(listResult));
            }
        }
        String errorList = "";
        for (Map.Entry entry : failIds.entrySet()) {
            if (StringUtils.isBlank((String)errorList)) {
                errorList = (String)entry.getKey() + ":" + (String)entry.getValue();
                continue;
            }
            errorList = errorList + "," + (String)entry.getKey() + ":" + (String)entry.getValue();
        }
        if (StringUtils.isNotBlank((String)errorList)) {
            context.put(Attributes.ATTR_ERROR_LIST,errorList);
        }
        return true;
    }
}

