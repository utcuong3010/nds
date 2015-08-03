package com.mbv.security.authentication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.ewallet.coissue.ac.Privilege;
import com.mbv.ewallet.coissue.protobuf.CoIssueClientImpl;
import com.mbv.services.ServicePool;

public class AuthorizationCheck {
	private static final Log log = LogFactory.getLog(AuthorizationCheck.class);
	Map<String, List<String>> actionMap=new HashMap<String, List<String>>();
	ServicePool<CoIssueClientImpl> servicePool;
	String objectType;
	public AuthorizationCheck(Map<String, List<String>> functionMap) {
		actionMap=new HashMap<String, List<String>>();
		
			for(Entry<String, List<String>> entry:functionMap.entrySet()){
				for(String action:entry.getValue()){
					if (!actionMap.containsKey(action)) actionMap.put(action, new ArrayList<String>());
					actionMap.get(action).add(entry.getKey());
				}
			}
	}
	
	public boolean checkPermission(String sessionId, String action) throws Exception{
		boolean result=false;
		List<String> functionList=actionMap.get(action);
		CoIssueClientImpl clientImpl=servicePool.acquire();
		try{
			
			if (functionList==null) return false;
			for(String function:functionList){
				try{
					Privilege privilege=new Privilege();
					privilege.setObjectType(objectType);
					privilege.addAllowedAction(function);
					List<Privilege> targets  =new ArrayList<Privilege>();
					targets.add(privilege);
					clientImpl.setSessionId(sessionId);
					clientImpl.checkPrivilege(targets);
					return true;
				}catch (Exception e) {
					log.warn(e.getMessage());
				}
			}
		}catch (Exception e) {
			return false;
		}finally{
			servicePool.release(clientImpl);
		}
		return result;
	}

	public ServicePool<CoIssueClientImpl> getServicePool() {
		return servicePool;
	}

	public void setServicePool(ServicePool<CoIssueClientImpl> servicePool) {
		this.servicePool = servicePool;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public Map<String, List<String>> getActionMap() {
		return actionMap;
	}

	public void setActionMap(Map<String, List<String>> actionMap) {
		this.actionMap = actionMap;
	}
}
