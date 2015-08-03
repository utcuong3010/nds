package com.mbv.airtime.launcher;

import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.airtime.AtTransactionDao; 
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.forward.ActiveMqForwarding;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.util.DateUtils.Type;
import com.mbv.bp.queue.core.QueueManager;
 
public class ConsoleLauncher {
    static final Log log = LogFactory.getLog(ConsoleLauncher.class);
    private static String module="Application";
    AbstractApplicationContext  applicationContext = null;

    public AbstractApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public ConsoleLauncher() {
    }

    public void init(String[] args) {
        load(args);
    }

    public void load(String[] arguments) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        log.info("Launching Airtime-Core ...");
        try {
        	try{
        		
    			ExecutorFactory.getInstance().initExecutor(module);
    		}catch (Exception e) {
    			log.error("Error in starting server");
    			System.exit(-1);
    		}
    		applicationContext=new ClassPathXmlApplicationContext(AppConstants.AIRTIME_LAUNCHER_CONTEXT_LOADERS);
        } catch (Throwable ex) {
        	log.fatal("Faild to start Airtime-Core", ex);
            throw new RuntimeException(ex);
        }
    }

    public void start() {
        applicationContext.start();
        log.info("Airtime-Core started successfully. Server time: " + new Date());
    }

    public void stop() {
        log.info("Stopping Airtime-Core ...");
        AppConstants.SYSTEM_IS_SHUTDOWN.set(true);
        boolean isShutdownable=false;
        long maxTimeOut=AppConstants.INQUIRY_TXN_MAX_TIME_OUT;
        long sleepTime=10000;
        while (!isShutdownable){
        	try{
        		AtTransactionDao transactionDao=new AtTransactionDao();
        		Date maxTxnDate=transactionDao.selectMaxTxnDate();
        		if (maxTxnDate!=null){
        			long mstime=DateUtils.dateDiff(Type.BY_MILLISECOND, maxTxnDate, new Date());
        			while (mstime<maxTimeOut){
	        			log.info("system will be shutted down in -"+(maxTimeOut-mstime)+"(ms).");
	        			if (mstime+sleepTime<=maxTimeOut){
	        				Thread.sleep(sleepTime);
	        			}else{
	        				Thread.sleep(maxTimeOut-mstime);
	        			}
	        			mstime=mstime+sleepTime;
	        			continue;
	        		}
        		}
        		isShutdownable=true;
        	}catch (Exception e) {
				log.error("Exception while processing for shutdown..",e);
			}
        }
        ExecutorFactory.getInstance().destroyExecutor(module);
		try{
			IdGeneratorFactory.getInstance().destroy();
			QueueManager.getInstance().shutdownQueueManager();
			com.mbv.anypay.queue.core.QueueManager.getInstance().shutdownQueueManager();
			ActiveMqForwarding.getInstance().destroy();
		}catch (Exception e) {
			log.error("Error in stopping Airtime-Core",e);
		}
        if (applicationContext != null) {
            applicationContext.stop();
        }
    }

    public void destroy() {
        if (applicationContext != null) {
            applicationContext.close();
        }
        //Thread.currentThread().join();
        log.info("Stopping terminated.");
    }

    public static void main(String[] args) throws IOException {
        ConsoleLauncher launcher = new ConsoleLauncher();
        launcher.load(args);
        launcher.start();
         System.out.println("Press any key to terminate . . .");
        System.in.read();
        launcher.stop();
        launcher.destroy();
    }
}
