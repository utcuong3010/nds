package com.mbv.airtime.util;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
public class UtilityLauncher {
    static final Log log = LogFactory.getLog(UtilityLauncher.class);
    AbstractApplicationContext  applicationContext = null;

    public AbstractApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public UtilityLauncher() {
    }

    public void init(String[] args) {
        load(args);
    }

    public void load(String[] arguments) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("Launching Airtime-Utility ...");
        try {
    		applicationContext=new ClassPathXmlApplicationContext(new String[]{"common-context.xml","scheduler-context.xml"});
        } catch (Throwable ex) {
        	log.fatal("Faild to start Airtime-Utility", ex);
            throw new RuntimeException(ex);
        }
    }

    public void start() {
        applicationContext.start();
        log.info("Airtime-Utility started successfully. Server time: " + new Date());
    }

    public void stop() {
        log.info("Stopping Airtime-Utility ...");
        if (applicationContext != null) {
            applicationContext.stop();
        }
    }

    public void destroy() {
        if (applicationContext != null) {
            applicationContext.close();
        }
        log.info("Stopping terminated.");
    }

    public static void main(String[] args) throws IOException {
        UtilityLauncher launcher = new UtilityLauncher();
        launcher.load(args);
        launcher.start();
         System.out.println("Press any key to terminate . . .");
        System.in.read();
        launcher.stop();
        launcher.destroy();
    }
}
