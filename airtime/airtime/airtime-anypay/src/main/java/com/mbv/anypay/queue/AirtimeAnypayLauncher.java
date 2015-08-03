package com.mbv.anypay.queue;

import java.io.IOException;import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.anypay.queue.core.QueueManager;

 
public class AirtimeAnypayLauncher {
    static final Log log = LogFactory.getLog(AirtimeAnypayLauncher.class);
    AbstractApplicationContext  applicationContext = null;

    public AbstractApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public AirtimeAnypayLauncher() {
    }

    public void init(String[] args) {
        load(args);
    }

    public void load(String[] arguments) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("Launching Airtime-Anypay ...");
        
//        try {
//			ExecutorFactory.getInstance().initExecutor(
//					ExecutorFactory.ANYPAY_EXECUTOR);
//		} catch (Exception e) {
//			log.error("Error in startUp core server");
//		}
		
        try {
    		applicationContext=new ClassPathXmlApplicationContext(new String[]{"common-context.xml", "anypay-context.xml" });
        } catch (Throwable ex) {
        	log.fatal("Faild to start Airtime-Anypay", ex);
            throw new RuntimeException(ex);
        }
        
    }

    public void start() {
        applicationContext.start();
        log.info("Airtime-Anypay started successfully. Server time: " + new Date());
    }

    public void stop() {
        log.info("Stopping Airtime-Anypay ...");
        try{
			QueueManager.getInstance().shutdownQueueManager();
		}catch (Exception e) {
			log.error("Error in stopping Airtime-Anypay",e);
		}
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
        AirtimeAnypayLauncher launcher = new AirtimeAnypayLauncher();
        launcher.load(args);
        launcher.start();
         System.out.println("Press any key to terminate . . .");
        System.in.read();
        launcher.stop();
        launcher.destroy();
    }
}
