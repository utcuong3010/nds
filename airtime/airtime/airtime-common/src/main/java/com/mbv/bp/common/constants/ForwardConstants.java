package com.mbv.bp.common.constants;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.model.ForwardModel;



public class ForwardConstants extends AppConfig {
	public static int ACTIVE_MQ_POOLSIZE = 1;
	public static Map<String, ForwardModel> RESULT_FORWARD_CHANNEL_MAP	= null;
	public static String ACTIVE_MQ_URL	= null;
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
		reload();
	}
	
	public static void reload() throws ConfigurationException, DaoException {
		Configuration config=getConfig("forward","config");
		RESULT_FORWARD_CHANNEL_MAP=new ConcurrentHashMap<String, ForwardModel>();
		ACTIVE_MQ_URL=config.getString("forward.active-mq.url",StringUtils.EMPTY);
		ACTIVE_MQ_POOLSIZE=config.getInt("forward.active-mq.pool-size",1);
		Object p=config.getProperty("forward.result.channel[@id]");
		ForwardModel forwardModel=null;
		if (p instanceof Collection) {
            Collection c = (Collection) p;
            for (int i = 0; i < c.size(); i++) {
            	forwardModel=new ForwardModel();
            	forwardModel.setChannelId(config.getString("forward.result.channel("+i+")[@id]"));
            	forwardModel.setQueueId(config.getString("forward.result.channel("+i+").queue-id",StringUtils.EMPTY));
            	forwardModel.setTopicId(config.getString("forward.result.channel("+i+").topic-id",StringUtils.EMPTY));
            	forwardModel.setRemovable(config.getBoolean("forward.result.channel("+i+").removable",true));
            	RESULT_FORWARD_CHANNEL_MAP.put(forwardModel.getChannelId(), forwardModel);
            }
            
        }else{
        	forwardModel=new ForwardModel();
        	forwardModel.setChannelId(config.getString("forward.result.channel[@id]"));
        	forwardModel.setQueueId(config.getString("forward.result.channel.queue-id",StringUtils.EMPTY));
        	forwardModel.setTopicId(config.getString("forward.result.channel.topic-id",StringUtils.EMPTY));
        	forwardModel.setRemovable(config.getBoolean("forward.result.channel.removable",true));
        	RESULT_FORWARD_CHANNEL_MAP.put(forwardModel.getChannelId(), forwardModel);
        }
		log.info(ACTIVE_MQ_URL);
		log.info(ACTIVE_MQ_POOLSIZE);
		log.info(RESULT_FORWARD_CHANNEL_MAP);
	}
	

}