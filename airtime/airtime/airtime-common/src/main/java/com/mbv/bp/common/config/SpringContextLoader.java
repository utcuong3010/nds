package com.mbv.bp.common.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringContextLoader {
    private static AbstractApplicationContext contextLoader;//new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
    public SpringContextLoader(String[] contextPaths) {
    	contextLoader= new ClassPathXmlApplicationContext(contextPaths);
    }
    public static void start() {
//    	contextLoader.refresh();
    	contextLoader.start();
    }
    
    public static void destroy() {
    	contextLoader.stop();
    	contextLoader.close();
    }
    public static ApplicationContext getContext() {
    	return contextLoader;
    }
    
}
