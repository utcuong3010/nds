/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.dao.airtime.AirtimeConfigDao
 *  com.mbv.bp.common.dao.airtime.CdrSyncDao
 *  com.mbv.bp.common.integration.ContextBase
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.settings.NotificationSettings
 *  com.mbv.bp.common.settings.NotificationSettings$TEMPLATE_TYPE
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
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.settings.NotificationSettings;
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

public class MobifoneCdrSyncJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)MobifoneCdrSyncJob.class);
    private static final String MODULE = "cdrsync";
    private static final String TYPE = "cacher";
    private static final String KEY_LAST_AT_TXN = "mobifone.last-at_txn_id";

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
        try {
            maxAtTxnId = minAtTxnId = new Long(configDao.getValue("cdrsync", "cacher", "mobifone.last-at_txn_id"));
        }
        catch (Exception e1) {
            maxAtTxnId = 0l;
            minAtTxnId = null;
            this.log.error("Database Exception.", (Throwable)e1);
        }
        String localPath = AppConstants.MOBIFONE_SETTINGS.getLocalMobifonePath();
        String backupPath = AppConstants.MOBIFONE_SETTINGS.getBackupMobifonePath();
        if (StringUtils.isBlank((String)backupPath)) {
            backupPath = "";
        } else if (!backupPath.endsWith("/")) {
            backupPath = backupPath + "/";
        }
        if (StringUtils.isBlank((String)localPath)) {
            localPath = "";
        } else if (!localPath.endsWith("/")) {
            localPath = localPath + "/";
        }
        File dir = new File(localPath);
        if (!dir.isDirectory()) {
            this.log.error((localPath + " is not a directory."));
            return;
        }
        String[] fileDowloads = dir.list(new FilenameFilter(){

            @Override
            public boolean accept(File arg0, String arg1) {
                if (arg1.endsWith(".log")) {
                    return true;
                }
                return false;
            }
        });
        if (fileDowloads == null || fileDowloads.length == 0) {
            this.log.info("No file to process.");
            return;
        }
        BufferedReader reader = null;
        String text = null;
        CdrSyncDao cdrSyncDao = new CdrSyncDao();
        long totalRecord = 0;
        long fileRecords = 0;
        CdrSyncDao syncDao = new CdrSyncDao();
        for (String fileName : fileDowloads) {
            try {
                reader = new BufferedReader(new FileReader(localPath + fileName));
                fileRecords = 0;
                while ((text = reader.readLine()) != null) {
                    if (StringUtils.isBlank((String)text)) continue;
                    CdrSync cdrSync = new CdrSync();
                    String[] contents = text.split(",");
                    cdrSync = new CdrSync();
                    cdrSync.setProviderId(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
                    cdrSync.setAtTxnId(Long.valueOf(contents[9].trim()));
                    cdrSync.setErrorCode("DELIVERY_RESPONSE_ERROR");
                    if (!StringUtils.isNotBlank((String)contents[0])) continue;
                    cdrSync.setMessageId(contents[0].trim());
                    try {
                        if (syncDao.selectByMessageAndIdProviderId(cdrSync)) {
                            if (StringUtils.isNotBlank((String)cdrSync.getpTxnId())) {
                                this.log.warn((cdrSync.getpTxnId() + " already processed."));
                                continue;
                            }
                            this.operationType = AbstractJob.OPERARION_TYPE.UPDATE_BY_MSG_ID;
                        } else if (syncDao.selectByAtTxnIdAndProviderIdAndErrorCode(cdrSync)) {
                            if (StringUtils.isNotBlank((String)cdrSync.getpTxnId())) {
                                this.log.warn((cdrSync.getpTxnId() + " already processed."));
                                continue;
                            }
                            this.operationType = AbstractJob.OPERARION_TYPE.UPDATE_BY_AT_TXN;
                        } else {
                            this.operationType = StringUtils.equals((String)contents[8].trim(), (String)"42") ? AbstractJob.OPERARION_TYPE.NO_ACTION : AbstractJob.OPERARION_TYPE.INSERT;
                        }
                    }
                    catch (Exception e) {
                        this.log.error("Fail to select Object from database", (Throwable)e);
                        continue;
                    }
                    cdrSync = new CdrSync();
                    cdrSync.setStatus("");
                    cdrSync.setAmount(Integer.valueOf(contents[7].trim()));
                    cdrSync.setMsisdn(contents[6].trim());
                    cdrSync.setTxnDate(DateUtils.convertString2Date((String)contents[1], (String)"GMT+7", (String)"dd/MM/yyyy HH:mm:ss"));
                    cdrSync.setMessageId(contents[0].trim());
                    cdrSync.setAtTxnId(Long.valueOf(contents[9].trim()));
                    cdrSync.setProviderId(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
                    cdrSync.setpPrice(Integer.valueOf(contents[7].trim()));
                    cdrSync.setpTxnId(contents[0].trim());
                    if (StringUtils.isNotBlank((String)contents[4])) {
                        if ("2".equals(contents[4].trim())) {
                            cdrSync.setpStatus("SUCCESS");
                        } else {
                            cdrSync.setpStatus("ERROR");
                        }
                    }
                    cdrSync.setpErrorCode(contents[8].trim());
                    try {
                        switch (this.operationType) {
                            case INSERT: {
                                cdrSyncDao.insert(cdrSync);
                                break;
                            }
                            case UPDATE_BY_MSG_ID: {
                                cdrSyncDao.updateByMessageIdAndProviderId(cdrSync);
                                break;
                            }
                            case UPDATE_BY_AT_TXN: {
                                cdrSync.setErrorCode("DELIVERY_RESPONSE_ERROR");
                                cdrSyncDao.updateByAtTxnIdAndProviderIdAndErrorCode(cdrSync);
                                break;
                            }
                            case NO_ACTION: {
                                this.log.info("No action, error 42. first time sync?");
                                break;
                            }
                            default: {
                                throw new Exception("Unsupported operation");
                            }
                        }
                        ++fileRecords;
                        ++totalRecord;
                        if (maxAtTxnId >= cdrSync.getAtTxnId()) continue;
                        maxAtTxnId = cdrSync.getAtTxnId();
                    }
                    catch (Exception e) {
                        this.log.error("Fail to insert Mobifone record to Database", (Throwable)e);
                    }
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
                this.log.error("Exception while process Mobifone Cdr Data.", (Throwable)e);
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
            filter.setProviderId(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
            filter.setFilterOperation("UNMATCHED");
            int totalUnmatched = syncDao.searchCount(filter);
            if (totalUnmatched != 0) {
                ContextBase context = new ContextBase();
                context.put("provider_id",AppConstants.MOBIFONE_SETTINGS.getConnectionType());
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
            airtimeConfig.setParamKey("mobifone.last-at_txn_id");
            airtimeConfig.setParamValue("" + maxAtTxnId);
            if (minAtTxnId == null) {
                configDao.insert(airtimeConfig);
            } else {
                configDao.update(airtimeConfig);
            }
        }
        catch (Exception e) {
            this.log.info("Fail to update last atTxnId MOBIFONE compare date", (Throwable)e);
        }
    }

}

