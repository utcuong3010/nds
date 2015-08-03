/*
 * Decompiled with CFR 0_101.
 * 
 * Could not load the following classes:
 *  com.mbv.bp.common.constants.AppConstants
 *  com.mbv.bp.common.dao.airtime.AirtimeConfigDao
 *  com.mbv.bp.common.ftp.AirtimeFtpClient
 *  com.mbv.bp.common.settings.VietPaySettings
 *  com.mbv.bp.common.util.AppUtils
 *  com.mbv.bp.common.vo.airtime.AirtimeConfig
 *  org.apache.commons.lang.StringUtils
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apache.commons.net.ftp.FTPFile
 *  org.apache.commons.net.ftp.FTPFileFilter
 */
package com.mbv.bp.core.scheduler;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.ftp.AirtimeFtpClient;
import com.mbv.bp.common.settings.VietPaySettings;
import com.mbv.bp.common.util.AppUtils;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;
import com.mbv.bp.core.scheduler.AbstractJob;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

public class VietPayGameFtpJob
extends AbstractJob {
    private Log log = LogFactory.getLog((Class)VietPayGameFtpJob.class);
    private static final String MODULE = "cdrsync";
    private static final String TYPE = "cacher";
    private static final String KEY = "vietpay.last-import-date-game";

    @Override
    public void execute() {
        Long lastImportDate;
        AirtimeConfigDao configDao;
        Long maxDateFile;
        List<String> fileList;
        if (!this.isEnable()) {
            this.log.info(("no process for this job | enable-" + this.isEnable()));
            return;
        }
        String host = AppConstants.VIETPAY_SETTINGS.getVietPayFtpHost();
        String username = AppConstants.VIETPAY_SETTINGS.getVietPayFtpUserName();
        String password = AppConstants.VIETPAY_SETTINGS.getVietPayFtpPassword();
        int port = AppConstants.VIETPAY_SETTINGS.getVietPayFtpPort();
        boolean isPassive = AppConstants.VIETPAY_SETTINGS.getVietPayFtpPassiveMode();
        configDao = new AirtimeConfigDao();
        maxDateFile = lastImportDate = null;
        try {
            lastImportDate = new Long(configDao.getValue("cdrsync", "cacher", "vietpay.last-import-date-game"));
        }
        catch (Exception e1) {
            lastImportDate = null;
            this.log.error("Database Exception.", (Throwable)e1);
        }
        this.log.info(("Last Import Date " + lastImportDate));
        AirtimeFtpClient ftpClient = new AirtimeFtpClient(host, port, username, password, isPassive);
        fileList = new ArrayList();
        try {
            String remotePath = AppConstants.VIETPAY_SETTINGS.getRemoteVietPayPath();
            String localPath = AppConstants.VIETPAY_SETTINGS.getLocalVietPayPath();
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
            VietPayFTPFileFilterImp filter = new VietPayFTPFileFilterImp(lastImportDate);
            fileList = ftpClient.fileList(remotePath, (FTPFileFilter)filter);
            if (fileList != null) {
                this.log.info(("Total file in VIETPAY server:" + fileList.size()));
            }
            for (String fileName : fileList) {
                try {
                    ftpClient.retrieveFile(remotePath + fileName, localPath + fileName + ".tmp");
                    if (!AppUtils.moveFile((String)(localPath + fileName + ".tmp"), (String)(localPath + fileName))) {
                        this.log.error(("Fail to rename file " + fileName + ".tmp" + " to " + fileName));
                    }
                    this.log.info((fileName + " downloaded."));
                    Long temp = Long.valueOf(fileName.substring(0, 14));
                    if (maxDateFile == null) {
                        maxDateFile = temp;
                        continue;
                    }
                    if (maxDateFile >= temp) continue;
                    maxDateFile = temp;
                    continue;
                }
                catch (Exception e) {
                    this.log.error(("Fail to download file from VIETPAY Ftp server| fileName:" + fileName));
                    ftpClient.disconnect();
                    return;
                }
            }
        }
        catch (Exception e) {
            this.log.info("Error while connecting to VIETPAY ftp server", (Throwable)e);
            return;
        }
        finally {
            ftpClient.disconnect();
        }
        if (fileList.size() == 0) {
            this.log.info("no file match found in VIETPAY ftp server.");
            return;
        }
        AirtimeConfig airtimeConfig = new AirtimeConfig();
        airtimeConfig.setModule("cdrsync");
        airtimeConfig.setType("cacher");
        airtimeConfig.setParamKey("vietpay.last-import-date-game");
        if (maxDateFile == null) {
            airtimeConfig.setParamValue("0");
        } else {
            airtimeConfig.setParamValue("" + maxDateFile);
        }
        try {
            if (lastImportDate == null) {
                configDao.insert(airtimeConfig);
            } else {
                configDao.update(airtimeConfig);
            }
        }
        catch (Exception e) {
            this.log.info("Fail to update last VIETPAY compare date", (Throwable)e);
        }
    }

    public static void main(String[] args) {
        VietPayGameFtpJob cdrSyncJob = new VietPayGameFtpJob();
        cdrSyncJob.execute();
    }

    private class VietPayFTPFileFilterImp
    implements FTPFileFilter {
        Long lastImportDate;

        public VietPayFTPFileFilterImp(Long lastImportDate) {
            this.lastImportDate = lastImportDate;
        }

        public boolean accept(FTPFile ftpFile) {
            if (ftpFile.isFile() && ftpFile.getName().endsWith("_vietpay_atgame.txt") && ftpFile.getSize() > 0) {
                if (this.lastImportDate != null) {
                    try {
                        Long fileNamePart = new Long(ftpFile.getName().substring(0, 14));
                        if (fileNamePart > this.lastImportDate) {
                            return true;
                        }
                        return false;
                    }
                    catch (Exception e) {
                        VietPayGameFtpJob.this.log.error(("Invalid fileName: " + ftpFile.getName()));
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }

}

