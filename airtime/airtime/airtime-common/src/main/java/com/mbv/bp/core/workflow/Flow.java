package com.mbv.bp.core.workflow;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.integration.ContextBase;


public class Flow implements Task{

    private static final Log log = LogFactory.getLog(Flow.class);
    protected Task[] tasks = new Task[0];
    protected Task[] handlers = new Task[0];
    protected Task[] endPoints = new Task[0];
    private String name;
    
    public Flow() {
    }

 	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public void addTask(String type,Task task) {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        
        if ("task".equalsIgnoreCase(type)){
	        Task[] taskTemps = new Task[tasks.length + 1];
	        System.arraycopy(tasks, 0, taskTemps, 0, tasks.length);
	        taskTemps[tasks.length] = task;
	        tasks = taskTemps;
        }

        if ("handler".equalsIgnoreCase(type)){
        	Task[] taskTemps = new Task[handlers.length + 1];
	        System.arraycopy(handlers, 0, taskTemps, 0, handlers.length);
	        taskTemps[handlers.length] = task;
	        handlers = taskTemps;
        }
        
        if ("exit".equalsIgnoreCase(type)){
        	Task[] taskTemps = new Task[endPoints.length + 1];
	        System.arraycopy(endPoints, 0, taskTemps, 0, endPoints.length);
	        taskTemps[endPoints.length] = task;
	        endPoints = taskTemps;
        }
    }

    protected boolean exitTask(ContextBase context, long startElapse) {
        StringBuilder builder = new StringBuilder();
        builder.append("Task.End| ");
        builder.append(context.getString(Attributes.ATTR_TASK_NAME));
        builder.append("| processed time(ms):");
        builder.append((System.currentTimeMillis() - startElapse));
        builder.append("| context:"+context);
        if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getString(Attributes.ATTR_ERROR_CODE))) {
            if (log.isDebugEnabled()) {
                log.debug(builder.toString());
            }
        } else {
            log.error(builder.toString());
        }
        return true;
    }

    protected void exitFlow(ContextBase context, Exception exception, boolean processStatus, long startElapse) {
        StringBuilder builder = new StringBuilder();
        builder.append("Flow.End| ");
        builder.append(context.getString(Attributes.ATTR_WFP_NAME));
        builder.append("|processed time(ms):");
        builder.append((System.currentTimeMillis() - startElapse));
        builder.append("|context:");
        builder.append(context);
        if (ErrorCode.SUCCESS.equalsIgnoreCase(context.getString(Attributes.ATTR_ERROR_CODE)) && (processStatus))      
            log.info(builder.toString());
        else{
        	if(exception==null)
        		log.error(builder.toString());
        	else
        		log.error(builder.toString(),exception);
        }
    }
   public boolean execute(ContextBase context) throws Exception{

        if (log.isDebugEnabled())
            log.debug("receive request:" + context.toString());
        Exception exception = null;
        long startFlowElapse = System.currentTimeMillis();
        long startElapse=0;
        boolean result = true;

        for (int i = 0; i < tasks.length; i++) {
        	if (!result)
        		break;
	        startElapse = System.currentTimeMillis();
	        context.putString(Attributes.ATTR_TASK_NAME, tasks[i].getClass().getSimpleName());
            try {
            	result = tasks[i].execute(context);
            	exitTask(context, startElapse);
            } catch (Exception e) {
                context.putString(Attributes.ATTR_ERROR_CODE, ErrorCode.SYS_INTERNAL_ERROR);
                exception = e;
                result = false;
                exitTask(context, startElapse);
                break;
            }
        }
        
        
        Handler handler;
        if (!result){
        	log.info("Going to process handler...");
        	for (int i = 0; i < handlers.length; i++) {
        		startElapse = System.currentTimeMillis();
                try {
                    handler = (Handler) handlers[i];
                    handler.handle(context, exception);
                    exitTask(context, startElapse);
                } catch (Exception e) {
                	exitTask(context, startElapse);
                    log.fatal("Fail to perform handle| handler-" + handlers[i].getClass().getSimpleName(), e);
                }
            }
        }
        
        ExitPoint exitPoint;
    	log.info("Going to process exit point...");
    	for (int i = 0; i < endPoints.length; i++) {
    		startElapse = System.currentTimeMillis();
            try {
            	exitPoint = (ExitPoint) endPoints[i];
                exitPoint.process(context);
                exitTask(context, startElapse);
            } catch (Exception e) {
            	exitTask(context, startElapse);
            	log.fatal("Fail to perform exitPoint| exitPoint-" + endPoints[i].getClass().getSimpleName(), e);
            }
        }
    	exitFlow(context, exception, result, startFlowElapse);
        return true;
    }

@Override
public String toString() {
	return "Flow [endPoints=" + Arrays.toString(endPoints) + ", handlers="
			+ Arrays.toString(handlers) + ", name=" + name + ", tasks="
			+ Arrays.toString(tasks) + "] \n";
}
	
}
