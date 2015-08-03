package com.mbv.loader;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.ExecutorFactory;

public class LoaderListener implements ServletContextListener {
	
	private static Log log = LogFactory.getLog(LoaderListener.class);
	private static final String module="AirTimeGateWay";
	public void contextInitialized(ServletContextEvent sce) {
		log.info("+ LoaderListener.contextInitialized +");
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		try {
			ExecutorFactory.getInstance().initExecutor(module);
		} catch (IntegrationException e) { 
			e.printStackTrace();
		}
		log.info("- LoaderListener.contextInitialized -");
		
	} 
	
	public void contextDestroyed(ServletContextEvent sce) {
		log.info("+ LoaderListener.contextDestroyed +");
		
		log.info("- LoaderListener.contextDestroyed -");
	} 
}