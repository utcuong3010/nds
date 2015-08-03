package com.mbv.bp.core.workflow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IWorkflow;

public class WfpExecutor implements IWorkflow<ContextBase> {
	private static final Log log = LogFactory.getLog(WfpExecutor.class);
    private Task task = null;
    private String wfName = null;
    private static Workflow workflow;
    static{
    	if (Workflow.getInstance().getWorkflows()==null){
    		Workflow.getInstance().init();
    		workflow=Workflow.getInstance();
    	}
    }
    public WfpExecutor(String wfName) throws Exception {
        this.wfName = wfName;
        try {
            task = workflow.getFlow(this.wfName);
            if (task == null) {
                log.error("Task is null in work flow : " + wfName);
                throw new RuntimeException("Task is null in workflow");
            }
        } catch (Exception e) {
            log.error("Fail to create workflow-" + wfName +"| errMsg-"+e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
   
    public String getWfName() {
    	
        return wfName;
    }

	@Override
	public ContextBase process(ContextBase context) throws Exception {
        log.info(wfName + " is started: " + context.toString());
        context.putString(Attributes.ATTR_WFP_NAME, wfName);
        try {
            task.execute(context);
        } catch (Exception e) {
            log.error("Exception occured in workflow " + wfName + " : "
                    + e.getMessage(), e);
        }

		return context;
	}
	
}
