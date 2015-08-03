package com.mbv.frontend.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.frontend.constant.FeConstant;

public class ReportTempCleanJob {
	private static Log log = LogFactory.getLog(ReportTempCleanJob.class);
public void execute(){
	File dir=new File(FeConstant.REPORT_TEMP_FULL_PATH);
	if (!dir.isDirectory()) {
		return;
	}
	for(String fileName:dir.list()){
		if (fileName.endsWith(".zip")){
			if (FeConstant.REPORT_TEMP_FULL_PATH.endsWith("/")){
				File file=new File(FeConstant.REPORT_TEMP_FULL_PATH+"/"+fileName);
				file.delete();
			}else{ 
				File file=new File(FeConstant.REPORT_TEMP_FULL_PATH+"/"+fileName);
				file.delete();
			}
			log.info("Delete report -" +FeConstant.REPORT_TEMP_FULL_PATH+"/"+fileName);
		}
	}
	
}

}
