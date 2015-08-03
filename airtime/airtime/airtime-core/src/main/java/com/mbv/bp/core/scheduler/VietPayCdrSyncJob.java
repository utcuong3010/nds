/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.dao.airtime.AirtimeConfigDao
 *  com.mbv.bp.common.dao.airtime.CdrSyncDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.NotificationSettings
 *  com.mbv.bp.common.settings.NotificationSettings$TEMPLATE_TYPE
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.common.util.DateUtils
 *  com.mbv.bp.common.vo.airtime.AirtimeConfig
 *  com.mbv.bp.common.vo.airtime.CdrSync
 *  com.mbv.bp.common.vo.airtime.CdrSyncFilter
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.scheduler;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.dao.airtime.CdrSyncDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.NotificationSettings;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;
import com.mbv.bp.common.vo.airtime.CdrSync;
import com.mbv.bp.common.vo.airtime.CdrSyncFilter;
import com.mbv.bp.core.scheduler.AbstractJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class VietPayCdrSyncJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)VietPayCdrSyncJob.class);
    private static final String MODULE = "cdrsync";
    private static final String TYPE = "cacher";
    private static final String KEY_LAST_AT_TXN = "vietpay.last-at_txn_id";

    @Override
    public void execute() {
        if (!this.isEnable()) {
            this.log.info(("no process for this job | enable-" + this.isEnable()));
            return;
        }
        AirtimeConfigDao configDao = new AirtimeConfigDao();
        AirtimeConfig airtimeConfig = new AirtimeConfig();
        Long maxAtTxnId = null;
        Long minAtTxnId = null;
        String localPath = AppConstants.VIETPAY_SETTINGS.getLocalVietPayPath();
        String backupPath = AppConstants.VIETPAY_SETTINGS.getBackupVietPayPath();
        if (StringUtils.isBlank((String)localPath)) {
            localPath = "";
        } else if (!localPath.endsWith("/")) {
            localPath = localPath + "/";
        }
        if (StringUtils.isBlank((String)backupPath)) {
            backupPath = "";
        } else if (!backupPath.endsWith("/")) {
            backupPath = backupPath + "/";
        }
        try {
            maxAtTxnId = minAtTxnId = new Long(configDao.getValue("cdrsync", "cacher", "vietpay.last-at_txn_id"));
        }
        catch (Exception e1) {
            maxAtTxnId = 0l;
            minAtTxnId = null;
            this.log.error("Database Exception.", (Throwable)e1);
        }
        BufferedReader reader = null;
        String text = null;
        long fileRecords = 0;
        long disCount = 0;
        long totalRecord = 0;
        File dir = new File(localPath);
        if (!dir.isDirectory()) {
            this.log.error((localPath + " is not a directory."));
            return;
        }
        String[] fileDowloads = dir.list(new FilenameFilter(){

            @Override
            public boolean accept(File arg0, String arg1) {
                if (arg1.endsWith(".txt")) {
                    return true;
                }
                return false;
            }
        });
        if (fileDowloads == null || fileDowloads.length == 0) {
            this.log.info("No file to process.");
            return;
        }
        CdrSyncDao syncDao = new CdrSyncDao();
        for (String fileName : fileDowloads) {
            try {
                reader = new BufferedReader(new FileReader(localPath + fileName));
                fileRecords = 0;
                while ((text = reader.readLine()) != null) {
                    if (StringUtils.isBlank((String)text)) continue;
                    CdrSync cdrSync = new CdrSync();
                    String[] contents = StringUtils.split((String)text, (String)"|");
                    if (contents.length < 7) {
                        this.log.warn(("Ignore old format " + fileName));
                        break;
                    }
                    if (!StringUtils.isNotBlank((String)contents[0])) continue;
                    cdrSync.setAtTxnId(Long.valueOf(contents[0].trim()));
                    cdrSync.setProviderId(AppConstants.VIETPAY_SETTINGS.getConnectionType());
                    try {
                        if (syncDao.selectByAtTxnIdAndProviderId(cdrSync)) {
                            if (StringUtils.isNotBlank((String)cdrSync.getpTxnId())) {
                                this.log.warn((cdrSync.getpTxnId() + " already processed."));
                                continue;
                            }
                            this.operationType = AbstractJob.OPERARION_TYPE.UPDATE_BY_AT_TXN;
                        } else {
                            this.operationType = AbstractJob.OPERARION_TYPE.INSERT;
                        }
                    }
                    catch (Exception e) {
                        this.log.error("Fail to select Object from database", (Throwable)e);
                        continue;
                    }
                    disCount = Integer.valueOf(contents[1].trim()) - Integer.valueOf(contents[2].trim());
                    cdrSync = new CdrSync();
                    cdrSync.setProviderId(AppConstants.VIETPAY_SETTINGS.getConnectionType());
                    cdrSync.setAtTxnId(Long.valueOf(contents[0].trim()));
                    cdrSync.setMessageId("");
                    cdrSync.setStatus("");
                    cdrSync.setAmount(Integer.valueOf(contents[1].trim()));
                    cdrSync.setMsisdn(contents[3].trim());
                    cdrSync.setTxnDate(DateUtils.convertString2Date((String)contents[4].trim(), (String)"GMT+7", (String)"yyyyMMddHHmmss"));
                    cdrSync.setProviderId(AppConstants.VIETPAY_SETTINGS.getConnectionType());
                    cdrSync.setpPrice(Integer.valueOf(contents[2].trim()));
                    cdrSync.setpStatus("SUCCESS");
                    cdrSync.setpErrorCode("1");
                    cdrSync.setpTxnId(contents[6]);
                    try {
                        CdrSyncDao cdrSyncDao = new CdrSyncDao();
                        switch (this.operationType) {
                            case INSERT: {
                                cdrSyncDao.insert(cdrSync);
                                break;
                            }
                            case UPDATE_BY_AT_TXN: {
                                cdrSyncDao.updateByAtTxnIdAndProviderId(cdrSync);
                                break;
                            }
                            default: {
                                throw new Exception("Unsupported operation");
                            }
                        }
                        if (maxAtTxnId < cdrSync.getAtTxnId()) {
                            maxAtTxnId = cdrSync.getAtTxnId();
                        }
                        ++fileRecords;
                        ++totalRecord;
                    }
                    catch (Exception e) {
                        this.log.error("Fail to insert VietPay record to Database", (Throwable)e);
                    }
                    continue;
                }
                this.log.info(("filename:" + fileName + "| Total records:" + fileRecords + " inserted."));
                reader.close();
                reader = null;
            }
            catch (FileNotFoundException e) {
                this.log.error("File not found exception.", (Throwable)e);
                return;
            }
            catch (IOException e) {
                this.log.error("Exception while process VietPay Cdr Data.", (Throwable)e);
                return;
            }
            finally {
                try {
                    if (reader != null) {
                        reader.close();
                        reader = null;
                    }
                }
                catch (Exception e) {
                    this.log.error("Fail to close file.", (Throwable)e);
                }
            }
            if (AppUtils.moveFile((String)(localPath + fileName), (String)(backupPath + System.currentTimeMillis() + "_" + fileName))) continue;
            this.log.warn(("Fail to backup cdr file| old path: " + localPath + fileName + "| new path: " + backupPath + System.currentTimeMillis() + "_" + fileName));
        }
        this.log.info(("Total records: " + totalRecord));
        if (totalRecord == 0) {
            this.log.info("No record found in files.");
            return;
        }
        try {
            CdrSyncFilter filter = new CdrSyncFilter();
            filter.setMaxAtTxnId(maxAtTxnId);
            if (minAtTxnId == null) {
                filter.setMinAtTxnId(Long.valueOf(0));
            } else {
                filter.setMinAtTxnId(minAtTxnId);
            }
            filter.setProviderId(AppConstants.VIETPAY_SETTINGS.getConnectionType());
            filter.setFilterOperation("UNMATCHED");
            filter.setStatus("SUCCESS");
            int totalUnmatched = syncDao.searchCount(filter);
            if (totalUnmatched != 0) {
                ContextBase context = new ContextBase();
                context.put("provider_id",AppConstants.VIETPAY_SETTINGS.getConnectionType());
                context.putInt("total_unmatched", totalUnmatched);
                context.putLong("from_date", filter.getMinAtTxnId().longValue());
                context.putLong("to_date", filter.getMaxAtTxnId().longValue());
                AppConstants.NOTIFICATION_SETTINGS.sendNotification(NotificationSettings.TEMPLATE_TYPE.CDR_COMPARE_STATUS_TEMPLATE, new Object[]{context});
            }
        }
        catch (Exception e) {
            this.log.error("Fail to send notification .", (Throwable)e);
        }
        try {
            airtimeConfig = new AirtimeConfig();
            airtimeConfig.setModule("cdrsync");
            airtimeConfig.setType("cacher");
            airtimeConfig.setParamKey("vietpay.last-at_txn_id");
            airtimeConfig.setParamValue("" + maxAtTxnId);
            if (minAtTxnId == null) {
                configDao.insert(airtimeConfig);
            } else {
                configDao.update(airtimeConfig);
            }
        }
        catch (Exception e) {
            this.log.info("Fail to update last atTxnId VIETPAY compare date", (Throwable)e);
        }
    }

}

