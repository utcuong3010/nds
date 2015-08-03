package com.mbv.bp.common.executor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.executor.client.AnypayQueueConnectionFactory;
import com.mbv.bp.common.executor.client.MobifoneConnectionFactory;
import com.mbv.bp.common.executor.client.QueueConnectionFactory;
import com.mbv.bp.common.executor.client.VietPayConnectionFactory;
import com.mbv.bp.common.executor.client.ViettelConnectionFactory;
import com.mbv.bp.common.executor.client.VnpayConnectionFactory;
import com.mbv.bp.common.executor.client.VtcConnectionFactory;
import com.mbv.bp.common.executor.client.WfpConnectionFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;

public class ExecutorFactory {

	protected static final Log log = LogFactory.getLog(ExecutorFactory.class);

	public static final String VNPAY_EXECUTOR = "VNPAY";
	public static final String MOBI_EXECUTOR = "MOBI";
	public static final String VTEL_EXECUTOR = "VTEL";
	public static final String VIETPAY_EXECUTOR = "VIETPAY";
	public static final String VTC_EXECUTOR = "VTC";
	public static final String ANYPAY_EXECUTOR = "ANYPAY";	
	public static final String QUEUE_EXECUTOR = "QueueConnection";
	public static final String WFP_EXECUTOR = "WorkFlowConnection";


	
	
	private static ExecutorFactory instance		= null;
	
	private Map<String,Map<String,IExecutor<ContextBase>>> executorMap	= null;
    
    static {
    	instance = new ExecutorFactory();
    }
    
    private ExecutorFactory(){
    	executorMap = new ConcurrentHashMap<String, Map<String,IExecutor<ContextBase>>>();
    }
    
    public void initExecutor(String module) throws IntegrationException {
    	List<ExecutorCfg>  list =AppConstants.MODULE_EXECUTOR_CONFIG_MAP.get(module);
    	if (list==null) return;
    	for(ExecutorCfg executorCfg:list){
    		log.info(executorCfg.getModule() +" init "+executorCfg.getType() +" executor| detail-"+executorCfg);
	    	IExecutor<ContextBase> executor = null;
	    	if( VNPAY_EXECUTOR.equalsIgnoreCase(executorCfg.getType()) ) {
	    		
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new VnpayConnectionFactory(executorCfg.getHost(),executorCfg.getPort()));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}
	    	}else if( MOBI_EXECUTOR.equalsIgnoreCase(executorCfg.getType()) ) {
	    		
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new MobifoneConnectionFactory(executorCfg.getHost()));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}
	    	}else if (QUEUE_EXECUTOR.equalsIgnoreCase(executorCfg.getType())){
	    		
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new QueueConnectionFactory(executorCfg));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}
	    	}else if (WFP_EXECUTOR.equalsIgnoreCase(executorCfg.getType())){

	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new WfpConnectionFactory(executorCfg));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}
	    	}else if (VTEL_EXECUTOR.equalsIgnoreCase(executorCfg.getType())){
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new ViettelConnectionFactory(executorCfg.getHost(),executorCfg.getPort()));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}
	    	}else if (VIETPAY_EXECUTOR.equalsIgnoreCase(executorCfg.getType())){
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new VietPayConnectionFactory(executorCfg.getHost()));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}	
	    	}else if (VTC_EXECUTOR.equalsIgnoreCase(executorCfg.getType())){
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new VtcConnectionFactory(executorCfg.getHost()));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}	
	    	}else if (ANYPAY_EXECUTOR.equalsIgnoreCase(executorCfg.getType())){
	    		if (!executorMap.containsKey(executorCfg.getClientConnType()))
	    			executorMap.put( executorCfg.getClientConnType(), new ConcurrentHashMap<String,IExecutor<ContextBase>>());
	    		if (!executorMap.get(executorCfg.getClientConnType()).containsKey(executorCfg.getType())){
	    			executor = new Executor();
	    			executor.init(executorCfg, new AnypayQueueConnectionFactory(executorCfg));
	    			executorMap.get(executorCfg.getClientConnType()).put(executorCfg.getType(), executor);
	    		}	
	    	}else {
	    		throw new IntegrationException( "Unsupported executor type. | Type-" + executorCfg.getType() ); 		
	    	}
    	}
    }
    
    public IExecutor<ContextBase> getExecutor(String clientConnType,String subConnType) throws IntegrationException {
    	IExecutor<ContextBase> executor = null;   
    	try{
    		executor=executorMap.get(clientConnType).get(subConnType);
    	}catch (Exception e) {
    		throw new IntegrationException( "Unsupported executor type or executor not initialized.|client-type-"+clientConnType+"|type-" +subConnType,e);
		}
    	return executor;
    }
    
    public IExecutor<ContextBase> getExecutor(String subConnType) throws IntegrationException {
    	IExecutor<ContextBase> executor = null;   
    	try{
    		executor=executorMap.get(AppConstants.DEFAULT_CLIENT_CONN_TYPE).get(subConnType);
    	}catch (Exception e) {
    		throw new IntegrationException( "Unsupported executor type or executor not initialized.|client-type-"+AppConstants.DEFAULT_CLIENT_CONN_TYPE+"|type-" +subConnType,e);
		}
    	return executor;
    }
    
    public static ExecutorFactory getInstance() {
    	return instance;
    }
    
    public void destroyExecutor(String module){
    	List<ExecutorCfg>  list =AppConstants.MODULE_EXECUTOR_CONFIG_MAP.get(module);
    	if (list==null) return;
    	for(ExecutorCfg executorCfg:list){
    	 try{	
    		log.info(executorCfg.getModule() +" destroy "+executorCfg.getType() +" executor| detail-"+executorCfg);
	    	IExecutor<ContextBase> executor=executorMap.get(executorCfg.getClientConnType()).get(executorCfg.getType());
	    	executor.destroy();
    	 }catch (Exception e) {
    		 log.error("Unable to destroy executor| executorType-"+executorCfg.getClientConnType()+"| config-"+executorCfg,e);
		 }
    	}
    }
   
}
