package com.mbv.bp.core.workflow;
import org.apache.commons.digester.Rule;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import com.mbv.bp.common.config.SpringContextLoader;
import com.mbv.bp.core.workflow.Task;
import com.mbv.bp.core.workflow.Workflow;

class TaskRule extends Rule {
	  private String classAttr = "className";
	  private String refAttr="ref";
	  private String typeAttr="type";
    public TaskRule() {}

    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
        String value = attributes.getValue(classAttr);
        String type = attributes.getValue(typeAttr);
        if (StringUtils.isBlank(type)) throw new Exception("attribute type is null in workflow");
        Task task;
        if (StringUtils.isNotBlank(value)){
        	Class clazz = digester.getClassLoader().loadClass(value);
        	task = (Task) clazz.newInstance();
            Workflow.getInstance().addTask(type,task);
        }else{
        	 value = attributes.getValue(refAttr);
        	 Workflow.getInstance().addTask(type,(Task)SpringContextLoader.getContext().getBean(value));
        }
        Workflow.getInstance().addFlow();
    }
}
