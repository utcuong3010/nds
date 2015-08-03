package com.mbv.airtime.alert;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.airtime.alert.constant.AlertConstant;

import freemarker.template.Template;
import freemarker.template.Configuration;

public abstract class BaseAlert implements IAlert {
	
	protected Map<String, String> propertyMap;
	protected Log log=LogFactory.getLog(BaseAlert.class); 
	protected final String ALERT_MODULE="alert";
	public final String ALERT_MIN_AMOUNT_TYPE="provider.min.amount";
	public final String ALERT_UNKNOWN_STATUS_TYPE="unknown.txn.status";
	public final String ALERT_UNKNOWN_STATUS_TXNID="last.txnid";
	public void addproperty(String key, String value){
		if (propertyMap==null) propertyMap=new HashMap<String, String>();
			propertyMap.put(key, value);
	} 
	@Override
	public String execute(Map<String, String> propertyMap) {
		this.propertyMap=propertyMap;
		return execute();
	}
	
	public abstract String execute();
	
	protected void writeAlertFile(Map resultMap) {
		try{
			if (resultMap.size()==0){
				log.info("No Alert !");
				return;
			}
			String basePath=System.getProperty("user.dir");
			String fileName=propertyMap.get(AlertConstant.ATTR_ALERT_OUTPUT_FILENAME);
			String templateDir=propertyMap.get(AlertConstant.ATTR_ALERT_TEMP_DIR);
			String templateFile=propertyMap.get(AlertConstant.ATTR_ALERT_TEMP_FILE);
			if (StringUtils.isBlank(templateDir))
				templateDir=basePath+"/template/";
			FileWriter writer = new FileWriter(new File(fileName));
			Configuration cfg = new Configuration();
			cfg.setDirectoryForTemplateLoading(new File(templateDir));
			Template tpl = cfg.getTemplate(templateFile);
			tpl.process(resultMap, writer);
			writer.close();
			log.info("Alert found.|fileName-"+fileName);
		}catch (Exception e) {
			log.error("Fail to write alert file| ErrMsg-"+e.getMessage(),e);
		}
	}
	
}
