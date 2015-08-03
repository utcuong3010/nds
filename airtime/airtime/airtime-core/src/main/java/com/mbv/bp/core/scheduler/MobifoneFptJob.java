/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.dao.DaoException
 *  com.mbv.bp.common.dao.airtime.AirtimeConfigDao
 *  com.mbv.bp.common.ftp.AirtimeFtpClient
 *  com.mbv.bp.common.settings.MobifoneSettings
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.common.util.DateUtils
 *  com.mbv.bp.common.util.DateUtils$Operation
 *  com.mbv.bp.common.util.DateUtils$Type
 *  com.mbv.bp.common.vo.airtime.AirtimeConfig
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 */
package com.mbv.bp.core.scheduler;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.ftp.AirtimeFtpClient;
import com.mbv.bp.common.settings.MobifoneSettings;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;
import com.mbv.bp.core.scheduler.AbstractJob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MobifoneFptJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)MobifoneFptJob.class);
    private static final String MODULE = "cdrsync";
    private static final String TYPE = "cacher";
    private static final String KEY = "mobifone.last-import-date";

    @Override
    public void execute() {
        AirtimeConfigDao configDao;
        String strGmtDateMatch;
        ArrayList<String> fileDowloads;
        String lastDateSync;
        if (!this.isEnable()) {
            this.log.info(("no process for this job | enable-" + this.isEnable()));
            return;
        }
        String host = AppConstants.MOBIFONE_SETTINGS.getMobifoneFtpHost();
        String username = AppConstants.MOBIFONE_SETTINGS.getMobifoneFtpUserName();
        String password = AppConstants.MOBIFONE_SETTINGS.getMobifoneFtpPassword();
        int port = AppConstants.MOBIFONE_SETTINGS.getMobifoneFtpPort();
        boolean isPassive = AppConstants.MOBIFONE_SETTINGS.getMobifoneFtpPassiveMode();
        Date curDateUTC = new Date();
        Date lastDate = DateUtils.dateAdd((Date)curDateUTC, (long)AppConstants.MOBIFONE_SETTINGS.getFromLastDateToCurDate(), (DateUtils.Type)DateUtils.Type.BY_DAY, (DateUtils.Operation)DateUtils.Operation.MINUS);
        strGmtDateMatch = DateUtils.convertDate2String((Date)lastDate, (String)"GMT+7", (String)"yyyyMMdd");
        configDao = new AirtimeConfigDao();
        lastDateSync = null;
        try {
            lastDateSync = configDao.getValue("cdrsync", "cacher", "mobifone.last-import-date");
        }
        catch (DaoException e1) {
            this.log.error("Database Exception.", (Throwable)e1);
        }
        if (strGmtDateMatch.equalsIgnoreCase(lastDateSync)) {
            this.log.info("Already compare Mobifone Data.");
            return;
        }
        this.log.info(("Going to check Mobifone Cdr File: " + strGmtDateMatch + "| LastDate: " + lastDateSync));
        AirtimeFtpClient ftpClient = new AirtimeFtpClient(host, port, username, password, isPassive);
        fileDowloads = new ArrayList<String>();
        try {
            String remotePath = AppConstants.MOBIFONE_SETTINGS.getRemoteMobifonePath();
            String localPath = AppConstants.MOBIFONE_SETTINGS.getLocalMobifonePath();
            if (StringUtils.isBlank((String)remotePath)) {
                remotePath = "";
            } else if (!remotePath.endsWith("/")) {
                remotePath = remotePath + "/";
            }
            if (StringUtils.isBlank((String)localPath)) {
                localPath = "";
            } else if (!localPath.endsWith("/")) {
                localPath = localPath + "/";
            }
            this.log.info(("remotePath:" + remotePath));
            this.log.info(("localPath:" + localPath));
            ftpClient.connect();
            List<String> fileList = ftpClient.fileList(remotePath);
            if (fileList != null) {
                this.log.info(("Total file in mobifone server:" + fileList.size()));
            }
            this.log.info(("match file name : " + AppConstants.MOBIFONE_SETTINGS.getFilePrefix() + "_" + strGmtDateMatch + AppConstants.MOBIFONE_SETTINGS.getSyncFileExtension()));
            for (String fileName : fileList) {
                if (!fileName.trim().equalsIgnoreCase(AppConstants.MOBIFONE_SETTINGS.getFilePrefix() + "_" + strGmtDateMatch + AppConstants.MOBIFONE_SETTINGS.getSyncFileExtension())) continue;
                try {
                    ftpClient.retrieveFile(remotePath + fileName, localPath + fileName + ".tmp");
                    if (!AppUtils.moveFile((String)(localPath + fileName + ".tmp"), (String)(localPath + fileName))) {
                        this.log.error(("Fail to rename file " + fileName + ".tmp" + " to " + fileName));
                    }
                    fileDowloads.add(fileName);
                    this.log.info((fileName + " downloaded."));
                    continue;
                }
                catch (Exception e) {
                    this.log.error(("Fail to download file from Mobifone Ftp server| fileName:" + fileName));
                    ftpClient.disconnect();
                    return;
                }
            }
        }
        catch (Exception e) {
            this.log.info("Error while connecting to Mobifone ftp server", (Throwable)e);
            return;
        }
        finally {
            ftpClient.disconnect();
        }
        if (fileDowloads.size() == 0) {
            this.log.info("no file match found in Mobifone ftp server.");
            return;
        }
        AirtimeConfig airtimeConfig = new AirtimeConfig();
        airtimeConfig.setModule("cdrsync");
        airtimeConfig.setType("cacher");
        airtimeConfig.setParamKey("mobifone.last-import-date");
        airtimeConfig.setParamValue(strGmtDateMatch);
        try {
            if (StringUtils.isBlank((String)lastDateSync)) {
                configDao.insert(airtimeConfig);
            } else {
                configDao.update(airtimeConfig);
            }
        }
        catch (Exception e) {
            this.log.info("Fail to update last Mobifone compare date", (Throwable)e);
        }
    }
}

