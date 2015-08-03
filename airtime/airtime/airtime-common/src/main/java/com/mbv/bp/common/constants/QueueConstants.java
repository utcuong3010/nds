package com.mbv.bp.common.constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.model.QueueConfig;


public class QueueConstants extends AppConfig {

	public static final long DEQUEUE_POLLER_INTERVAL = 1000L;
	public static final String QUEUE_STATUS_NEW = "NEW";
	public static final String QUEUE_STATUS_ERROR = "ERROR";
	public static final String QUEUE_STATUS_DELIVERED = "DELIVERED";
	public static List<QueueConfig> QUEUE_CONFIGS;

	static {
	    try {
			load();
		} catch (ConfigurationException e) {
			log.error("Fail to load config. ",e);
		} catch (DaoException e) {
			log.error("Fail to load config| Database exception. ",e);
		}
	} 
	
	public static void load() throws ConfigurationException, DaoException {
		loadConfig(getConfig("queue","config"));
	    
	}

	@SuppressWarnings("unchecked")
	private static void loadConfig(Configuration config) {
		QUEUE_CONFIGS=new ArrayList<QueueConfig>();
		QUEUE_CONFIGS.clear();
		Object p=config.getProperty("queue.constant.queue-config.queue[@id]");
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	QueueConfig queueConfig=new QueueConfig();
            	queueConfig.setQueueId(config.getString("queue.constant.queue-config.queue("+i+")[@id]"));
            	queueConfig.setMaxSize(config.getInt("queue.constant.queue-config.queue("+i+").max-queue-size"));
            	queueConfig.setDequeue(config.getInt("queue.constant.queue-config.queue("+i+").dequeue-size"));
            	queueConfig.setMaxRetry(config.getInt("queue.constant.queue-config.queue("+i+").max-dequeue-retry"));
            	queueConfig.setQueueTable(config.getString("queue.constant.queue-config.queue("+i+").queue-table"));
            	queueConfig.setTerminable(config.getBoolean("queue.constant.queue-config.queue("+i+").terminable"));
            	QUEUE_CONFIGS.add(queueConfig);
            }
        }else{
        	QueueConfig queueConfig=new QueueConfig();
        	queueConfig.setQueueId(config.getString("queue.constant.queue-config.queue[@id]"));
        	queueConfig.setMaxSize(config.getInt("queue.constant.queue-config.queue.max-queue-size"));
        	queueConfig.setDequeue(config.getInt("queue.constant.queue-config.queue.dequeue-size"));
        	queueConfig.setMaxRetry(config.getInt("queue.constant.queue-config.queue.max-dequeue-retry"));
        	queueConfig.setQueueTable(config.getString("queue.constant.queue-config.queue.queue-table"));
        	QUEUE_CONFIGS.add(queueConfig);
        }
		
	}
	public static void main(String[] args) {
		System.out.println(QUEUE_CONFIGS);
		
	}
}