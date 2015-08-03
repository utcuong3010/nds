package com.mbv.bp.common.constants;

import java.io.StringReader;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;

public class AppConfig {
	protected static Log log = LogFactory.getLog(AppConfig.class);
	public static String AT_SQL_MAP_CONFIG_FILE = "sqlmap-config-airtime.xml";
	private static String XML_TYPE="xml";
	private static String PROP_TYPE="prop";
	protected static CompositeConfiguration getConfig(String module, String type) throws DaoException, ConfigurationException{
		CompositeConfiguration configuration=new CompositeConfiguration();
		AirtimeConfigDao configDao=new AirtimeConfigDao();
		AirtimeConfig airtimeConfig=new AirtimeConfig();
		airtimeConfig.setModule(module);
		airtimeConfig.setType(type);
		List<AirtimeConfig> configs= configDao.selectByModuleAndType(airtimeConfig); 
		XMLConfiguration xmlConfig;
		for(AirtimeConfig atConfig:configs){
			if (XML_TYPE.equalsIgnoreCase(atConfig.getValueType())){
				xmlConfig=new XMLConfiguration();
				xmlConfig.load(new StringReader(atConfig.getParamValue()));
				configuration.addConfiguration(xmlConfig);
			}else if(PROP_TYPE.equalsIgnoreCase(atConfig.getValueType())){
				configuration.addProperty(atConfig.getParamKey(), atConfig.getParamValue());
			}
		}
		return configuration;
	}
	protected static CompositeConfiguration getConfig(String module, String type, String key) throws DaoException, ConfigurationException{
		CompositeConfiguration configuration=new CompositeConfiguration();
		AirtimeConfigDao configDao=new AirtimeConfigDao();
		AirtimeConfig airtimeConfig=new AirtimeConfig();
		airtimeConfig.setModule(module);
		airtimeConfig.setType(type);
		airtimeConfig.setParamKey(key);
		if (configDao.select(airtimeConfig)){
			XMLConfiguration xmlConfig;
				if (XML_TYPE.equalsIgnoreCase(airtimeConfig.getValueType())){
					xmlConfig=new XMLConfiguration();
					xmlConfig.load(new StringReader(airtimeConfig.getParamValue()));
					configuration.addConfiguration(xmlConfig);
				}else if(PROP_TYPE.equalsIgnoreCase(airtimeConfig.getValueType())){
					configuration.addProperty(airtimeConfig.getParamKey(), airtimeConfig.getParamValue());
				}else
					return null;
		}
		return configuration;
	}
}
