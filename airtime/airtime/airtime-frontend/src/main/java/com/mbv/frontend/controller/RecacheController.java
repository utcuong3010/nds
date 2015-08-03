package com.mbv.frontend.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.frontend.model.RecacheModel;




public class RecacheController extends ActionControllerBase {
	private Log log=LogFactory.getLog(RecacheController.class);
	private static final long serialVersionUID = 1L;
	private static final String RECACHE_WFP="recachePropertiesWfp";
	private RecacheModel recacheModel;
	private static final String MODULE_TELCO_PROVIDER="airtime";
	private static final String TYPE_TELCO_PROVIDER="config";
	private static final String KEY_TELCO_PROVIDER="airtime.constant.telco-provider";
	public String recacheView(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		recacheModel=new RecacheModel();
		AirtimeConfigDao airtimeConfigDao=new AirtimeConfigDao();
		try {
			recacheModel.setParamValue(airtimeConfigDao.getValue(MODULE_TELCO_PROVIDER, TYPE_TELCO_PROVIDER, KEY_TELCO_PROVIDER));
		} catch (DaoException e) {
			log.error("Database exception",e);
		}
		return "success";
	}
	
	public String recacheMobiPropery(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		if (recacheModel==null) return "success";
		
		if (StringUtils.isBlank(recacheModel.getUrl()))
			addFieldError("url", getText("field.required"));
		if (getFieldErrors().size()>0) 	return "success";
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_WFP_NAME, RECACHE_WFP);
		context.put(Attributes.ATTR_RECACHE_TYPE, AppConstants.MOBIFONE_CONN_PROP_RECACHE);
		context.put(Attributes.ATTR_URL, recacheModel.getUrl());
		try{
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode()))
				addFieldError("sys_message", "Thay doi mobi url thanh cong, new mobi url:"+recacheModel.getUrl());
			else
				addFieldError("sys_message", "Thay doi mobi url that bai");
		}catch (Exception e) {
			log.error(e);
			addFieldError("sys_message", "Co loi xay ra khi thay doi mobi url| errMsg:"+e.getMessage());
		}
		return "success";
	}
	
	public String recacheViettelPropery(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		if (recacheModel==null) return "success";
		
		if (StringUtils.isBlank(recacheModel.getHost()))
			addFieldError("host", getText("field.required"));
		if (getFieldErrors().size()>0) 	return "success";
		
		if (StringUtils.isBlank(recacheModel.getPort()))
			addFieldError("port", getText("field.required"));
		if (getFieldErrors().size()>0) 	return "success";
		
		if (!StringUtils.isNumeric(recacheModel.getPort()))
			addFieldError("port", getText("field.numeric.required"));
		
		if (getFieldErrors().size()>0) 	return "success";
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_WFP_NAME, RECACHE_WFP);
		context.put(Attributes.ATTR_RECACHE_TYPE, AppConstants.VIETTEL_CONN_PROP_RECACHE);
		context.put(Attributes.ATTR_HOST, recacheModel.getHost());
		context.put(Attributes.ATTR_PORT, recacheModel.getPort());
		try{
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode()))
				addFieldError("sys_message", "Thay doi viettel connection properties thanh cong|host:"+recacheModel.getHost()+"| port:"+recacheModel.getPort());
			else
				addFieldError("sys_message", "Thay doi viettel connection properties that bai");
		}catch (Exception e) {
			log.error(e);
			addFieldError("sys_message", "Co loi xay ra khi thay doi viettel connection properties|host:"+recacheModel.getHost()+"| port:"+recacheModel.getPort()+"| errMsg:"+e.getMessage());
		}
		return "success";
	}
	
	public String recacheTelcoProvider(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		if (recacheModel==null) return "success";
		if (StringUtils.isBlank(recacheModel.getParamValue())) return "success";
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_WFP_NAME, RECACHE_WFP);
		context.put(Attributes.ATTR_RECACHE_TYPE, AppConstants.TELCO_PROVIDER_RECACHE);
		
		String backupData=null;
		boolean isDbProcessed=false;
		AirtimeConfigDao airtimeConfigDao=new AirtimeConfigDao();
		try{
			checkTelcoConfig(recacheModel.getParamValue());
			backupData=airtimeConfigDao.getValue(MODULE_TELCO_PROVIDER, TYPE_TELCO_PROVIDER, KEY_TELCO_PROVIDER);
			airtimeConfigDao.updateValue(MODULE_TELCO_PROVIDER, TYPE_TELCO_PROVIDER, KEY_TELCO_PROVIDER,recacheModel.getParamValue());
			isDbProcessed=true;
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode())){
				addFieldError("sys_message", "Cap nhat Telco provider thanh cong");
				try{
					AppConstants.reload();
				}catch (Exception e) {
					log.error("Fail to recache TELCO_PROVIDER from Airtime admin");
				}
			}else
				throw new Exception("Khong the recache airtime-launcher");
		} catch (ConfigurationException e1) {
			log.error(e1);
			addFieldError("sys_message", "Config telco provider nhap khong chinh xac| errMsg:"+e1.getMessage());
		}catch (DaoException e2) {
			log.error(e2);
			addFieldError("sys_message", "Co loi xay ra khi cap nhat config vao database | errMsg:"+e2.getMessage());
			try{
				if (isDbProcessed)
					airtimeConfigDao.updateValue(MODULE_TELCO_PROVIDER, TYPE_TELCO_PROVIDER, KEY_TELCO_PROVIDER,backupData);
			}catch (Exception e) {
				log.error(e);
			}
		}catch (Exception e) {
			log.error(e);
			addFieldError("sys_message", "Co loi xay ra khi cap nhat Telco provider| errMsg:"+e.getMessage());
			try{
				if (isDbProcessed)
					airtimeConfigDao.updateValue(MODULE_TELCO_PROVIDER, TYPE_TELCO_PROVIDER, KEY_TELCO_PROVIDER,backupData);
			}catch (Exception ex) {
				log.error(ex);
			}
		}
		return "success";
	}
	public String recacheNotifTemplate(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_WFP_NAME, RECACHE_WFP);
		context.put(Attributes.ATTR_RECACHE_TYPE, AppConstants.NOTIFICATION_TEMPLATE_RECACHE);
		
		try{
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode()))
				addFieldError("sys_message", "Cap nhat Notification Template tu database thanh cong");
			else
				addFieldError("sys_message", "Cap nhat TNotification Template tu database that bai");
		}catch (Exception e) {
			log.error(e);
			addFieldError("sys_message", "Co loi xay ra khi cap nhat Notification Template tu database| errMsg:"+e.getMessage());
		}
		return "success";
	}
	
	
	public RecacheModel getRecacheModel() {
		return recacheModel;
	}

	public void setRecacheModel(RecacheModel recacheModel) {
		this.recacheModel = recacheModel;
	}
	
	public void checkTelcoConfig(String xmlContent) throws ConfigurationException{
		XMLConfiguration config=new XMLConfiguration();
		config.load(new StringReader(xmlContent));
		Map<String, TelcoProvider> telcoProviders =new ConcurrentHashMap<String, TelcoProvider>();
		Object p=config.getProperty("airtime.constant.telco-provider.provider[@id]");
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	TelcoProvider provider=new TelcoProvider();
            	provider.setProviderId(config.getString("airtime.constant.telco-provider.provider("+i+")[@id]").toUpperCase());
            	provider.setProviderName(config.getString("airtime.constant.telco-provider.provider("+i+")[@name]"));
            	provider.setConnectionIds(config.getStringArray("airtime.constant.telco-provider.provider("+i+").connection-id"));
            	telcoProviders.put(provider.getProviderId(), provider);
            }
        }else{
        	TelcoProvider provider=new TelcoProvider();
        	provider.setProviderId(config.getString("airtime.constant.telco-provider.provider[@id]").toUpperCase());
        	provider.setProviderName(config.getString("airtime.constant.telco-provider.provider[@name]"));
        	provider.setConnectionIds(config.getStringArray("airtime.constant.telco-provider.provider.connection-id"));
        	telcoProviders.put(provider.getProviderId(), provider);
        }
	}
	
}
