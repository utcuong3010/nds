package com.mbv.bp.common.ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

public class AirtimeFtpClient {
	private static Log log = LogFactory.getLog(AirtimeFtpClient.class);
	private FTPClient ftp = null;
	private String hostname = null;
	private String username =null;
	private String password =null;
	private boolean isPassiveMode=false;
	private int port;
	
	public AirtimeFtpClient(String hostname,int port,String username, String password,boolean isPassiveMode ) {
		ftp=new FTPClient();
		this.hostname=hostname;
		this.username=username;
		this.password=password;
		this.port=port;
		this.isPassiveMode=isPassiveMode;
	}
	
	public void connect() throws Exception{
		log.info("Connecting to Ftp server.| host-"+hostname+":"+port);
		try {
			ftp.connect(hostname,port);
			ftp.setDataTimeout(10000);
		} catch (Exception e) {
			log.error("Fail to connect to Ftp server.| hostname-"+hostname,e);
			throw e;
		}
		
		try {
			ftp.login(username, password);
		} catch (IOException e) {
			log.error("Fail to login to Ftp server.| hostname-"+hostname,e);
			throw e;
		}
		
		int reply = ftp.getReplyCode();
		if (!FTPReply.isPositiveCompletion(reply)){
			log.error("Fail to login to Ftp server. Incorrect username or password| hostname-"+hostname+"| username-"+username);
			ftp.disconnect();
			throw new Exception("Incorrect username or password");
		}
	
		try {
			if (isPassiveMode)
				ftp.enterLocalPassiveMode();
			else
				ftp.enterLocalActiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (IOException e) {
			log.warn("Fail to set ftp filetype to binary mode. Using filetype default",e);
		}
		
		log.info("Connected to Ftp server.| host-"+hostname+":"+port);
	}
	
	public void disconnect(){
		try {
			log.info("Ftp client disconnecting ...| hostname-"+hostname);
			ftp.logout();
			ftp.disconnect();
			log.info("Ftp client disconnected ...| hostname-"+hostname);
		} catch (IOException e) {
			log.error("Fail to disconnect to Ftp server.| hostname-"+hostname,e);
		}
		
	}
	
	public void retrieveFile(String remoteFilePath, String localFilePath) throws Exception {
		synchronized (this){
			int retryData=5;
			for (int i=0;i<retryData;i++){
				try{
					File file = new File( localFilePath );
					FileOutputStream out = new FileOutputStream(file);
					ftp.retrieveFile( remoteFilePath , out );
					out.close();
					break;
				}catch (Exception e) {
					if (i<retryData-1){
						log.error("Fail to retrieve file from ftp server. Going to retry | sourceFile-"+remoteFilePath +" | errMsg:"+e.getMessage()+"| retry:"+i);
						try{
							reconnect();
						}catch (Exception e1) {
							log.error("Fail to reconnect to ftp server.",e1);
						}
					}else{
						log.error("Fail to retrieve file from ftp server. | sourceFile-"+remoteFilePath ,e);
						throw e;
					}
				}
			}
		}
	}
	
	public void storeFile(String localFilePath, String remoteFilePath) throws Exception{
		InputStream in;
		synchronized (this){
			try {
				in = new FileInputStream(localFilePath);
				ftp.storeFile(remoteFilePath,in);
				in.close();
				log.info("uploaded "+localFilePath+" to "+remoteFilePath);
			}catch (Exception e) {
				log.error("Fail to upload file to ftp server.| hostname-"+hostname+"| localFilePath-"+localFilePath,e);
				throw e;
			}
		}
	}
	
	public void renameFile(String remoteFilePath, String newRemoteFilePath) throws IOException{
		try {
			ftp.rename(remoteFilePath, newRemoteFilePath);
			log.info("Renamed "+remoteFilePath+" to "+newRemoteFilePath);
		} catch (IOException e) {
			log.error("Fail to rename file from " +remoteFilePath +" to "+newRemoteFilePath);
			throw e;
		}
	}
	
	public List<String> fileList(String remoteDirPath) throws IOException{
		try {
			List<String> resultList=new ArrayList<String>();
			FTPFile[] ftpFiles;
			
			log.info("get All Files from directory.| dir:"+remoteDirPath);
			
			if (StringUtils.isNotBlank(remoteDirPath))
				ftpFiles=ftp.listFiles(remoteDirPath);
			else 
				ftpFiles=ftp.listFiles();
			
			if (ftpFiles!=null)
				for (int i=0;i<ftpFiles.length;i++){
					if (ftpFiles[i].isFile())
						resultList.add(ftpFiles[i].getName());
				}
			return resultList;
		} catch (IOException e) {
			log.error("Fail to retrieve file list from " +remoteDirPath) ;
			throw e;
		}
	}
	
	public List<String> fileList(String remoteDirPath, FTPFileFilter filter) throws IOException{
		try {
			List<String> resultList=new ArrayList<String>();
			FTPFile[] ftpFiles;

			log.info("get All Files from directory.| dir:"+remoteDirPath);

			ftpFiles=ftp.listFiles(remoteDirPath,filter);
			if (ftpFiles!=null)
				for (int i=0;i<ftpFiles.length;i++){
					if (ftpFiles[i].isFile())
						resultList.add(ftpFiles[i].getName());
				}
			return resultList;
		} catch (IOException e) {
			log.error("Fail to retrieve file list from " +remoteDirPath) ;
			throw e;
		}
	}
	
	public void changeDirectory(String dirPath) throws Exception{
		try{
			ftp.changeWorkingDirectory(dirPath);
			log.info("current remote directory "+dirPath);
		}catch (Exception e) {
			log.error("Fail to change directory. | dirPath-"+dirPath,e);
			throw e;
		}
	}
	
	public void reconnect() throws Exception{
		disconnect();
		connect();
	}

	
}
