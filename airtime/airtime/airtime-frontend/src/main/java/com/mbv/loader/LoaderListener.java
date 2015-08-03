package com.mbv.loader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.frontend.constant.FeConstant;

public class LoaderListener implements ServletContextListener {
	
	private static Log log = LogFactory.getLog(LoaderListener.class);
	private static final String module="AirtimeFrontEnd";
	public void contextInitialized(ServletContextEvent sce) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		log.info("+ LoaderListener.contextInitialized +");
		try{
			ExecutorFactory.getInstance().initExecutor("AirTimeAdmin");
		}catch (Exception e) {
			log.error("Unable to Init executor for module-AirTimeAdmin",e);
		}
		System.out.println(TimeZone.getDefault());
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Properties properties=new Properties();
		String filename = "/WEB-INF/airtime-frontend.properties";
		InputStream is = sce.getServletContext().getResourceAsStream(filename);
		try {
			properties.load(is);
			FeConstant.load(properties);
			FeConstant.REPORT_TEMP_FULL_PATH=sce.getServletContext().getRealPath(FeConstant.REPORT_TEMP_PATH);
		} catch (IOException e) {
			log.error("unable to load config.",e);
		}
		log.info("- LoaderListener.contextInitialized -");
	} 
	
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("+ LoaderListener.contextDestroyed +");
		
		log.info("- LoaderListener.contextDestroyed -");
	} 
}