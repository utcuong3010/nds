package com.mbv.bp.common.executor.viettel;

import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.executor.client.IClientManager;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.settings.NotificationSettings.TEMPLATE_TYPE;

public class ViettelClientManager{
	protected static final Log log = LogFactory.getLog(ViettelClientManager.class);
	private ViettelClient[] clients				= null;
	private AtomicBoolean runningFlag			= new AtomicBoolean(false);
	private AtomicBoolean shutdownFlag			= new AtomicBoolean(false);
	private AtomicBoolean suspendFlag			= new AtomicBoolean(false);
	private Integer curConnIndex				= new Integer(0);
	int numOfConns								= AppConstants.VIETTEL_SETTINGS.getMaxConnsToVtelServer();

private static class ViettelClientFactoryHolder{
	private static ViettelClientManager instance=new ViettelClientManager();
}

private ViettelClientManager(){

}

public void init(String ip,int port){
	if (runningFlag.compareAndSet(false, true)){
		ViettelConnectionProperty.getInstance().setProperties(ip, port);
		clients=new ViettelClient[numOfConns];
		ViettelClient client=null;
		for(int i=0;i<numOfConns;i++){
			client=new ViettelClient("VTEL_CONN_"+(i+1));
			client.startUpViettelClient();
			clients[i]=client;
		}
		shutdownFlag.set(false);
	}
}


public void shutdown(){
		if (shutdownFlag.compareAndSet(false, true)){
			log.info("Going to shutdown Viettel Client.");
			for(int i=0;i<numOfConns;i++){
				try{
					clients[i].shutdown();
				}catch (Exception e) {
					log.error("Error while process shutdown.",e);
				}
			}
		}else{
			log.info("Already in shutdown state");
		}
}

public ContextBase process(ContextBase context){
	
	ContextBase result=new ContextBase();
	result.setErrorCode(ErrorCode.SUCCESS);
	
	if (isSuspend()){
		result.setErrorCode(ErrorCode.SUSPEND_EXCEPTION);
		return result;
	}
		
	try{
		if (runningFlag.get()){
			if (shutdownFlag.get())
				result.setErrorCode(ErrorCode.SYSTEM_SHUTINGDOWN);
			else
				result= getClient().process(context);
		}else{
			result.setErrorCode(ErrorCode.NOT_STARTED_EXCEPTION);
		}
	}catch (Exception e) {
		log.error("Exception while getting Viettel Client.",e);
		result.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
	}
	return result;
}
private ViettelClient getClient() {
	if (numOfConns!=1){
		synchronized (curConnIndex){
			curConnIndex++;
			if (curConnIndex>=numOfConns) curConnIndex=0;
			return clients[curConnIndex];
		}
	}else 
		return clients[0];
}

public static ViettelClientManager getInstance(){
	return  ViettelClientFactoryHolder.instance;
}


public boolean isSuspend(){
	return suspendFlag.get();
}

public void setSuspend(boolean newValue){
//	if (suspendFlag.compareAndSet(!newValue, newValue)){
//		AppConstants.NOTIFICATION_SETTINGS.sendNotification(TEMPLATE_TYPE.CONNECTION_STATUS_TEMPLATE, AppConstants.VIETTEL_SETTINGS.getConnectionType(),!newValue);
//	}
}
}
