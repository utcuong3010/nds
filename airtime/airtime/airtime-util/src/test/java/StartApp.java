
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mbv.bp.common.config.SpringContextLoader;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.core.StartUpScheduler;
import com.mbv.bp.queue.core.QueueManager;

public class StartApp {
	private static Logger log=Logger.getLogger(StartApp.class);
	public static void startUp(){
		 TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringContextLoader contextLoader=new SpringContextLoader(new String[]{"common-context.xml","scheduler-context.xml"});
		SpringContextLoader.start();
		  
	}
	public static void shutdown(){
		SpringContextLoader.destroy();
	}
	
	public static void registerShutdownHook(){
		Runtime.getRuntime().addShutdownHook(
                new Thread() {
                    public void run() {
                    	log.info("Going to shutdown");
                    	try{
                    		shutdown();
                    		log.info( "Successfully shutdown");
                    	}catch (Exception e) {
                    		log.error( "Fail to shutdown",e);
						}
                    }
                }
        );
	}
	public static void main(String[] args) {
		startUp();
		registerShutdownHook();
	}
}
