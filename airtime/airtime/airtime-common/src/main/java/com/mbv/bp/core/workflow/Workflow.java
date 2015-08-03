package com.mbv.bp.core.workflow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.digester.Digester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Workflow{
	private static final Log log = LogFactory.getLog(Workflow.class);
	private Map<String, Flow> workflowMap=null;
	private Flow flow;
	private static class WorkflowHolder{
		private static Workflow instance = new Workflow();
	}
	public static Workflow getInstance() {
        return WorkflowHolder.instance;
    }
	
	private Workflow() {
	}
	
	public void addFlow(){
		workflowMap.put(flow.getName(),flow);
	}

	public void setFlow(Flow flow) {
		this.flow=flow;
	}
	
	public Flow getFlow(String name) {
		Flow result=null;
		try{
			result=(Flow)workflowMap.get(name);
		}catch (Exception e) {
			result=null;
		}
		return result;
	}
	
	public void addTask(String type,Task task){
		flow.addTask(type,task);
	}
	
	public void init(){
		 workflowMap=new ConcurrentHashMap<String, Flow>();
		 Digester digester = new Digester();
	     digester.setValidating( false );
	     digester.addRule("*/workflow/flow",new FlowRule());
	     digester.addRule("*/workflow/flow/task",new TaskRule());
	     try {
			digester.parse(Workflow.class.getResource("/workflows.xml"));
		} catch (Exception e) {
			log.error("Error on initializing workflow" + e.getMessage(), e);
		} 
	} 
	
	public Map<String, Flow> getWorkflows(){
		return workflowMap;
	}
	
}
