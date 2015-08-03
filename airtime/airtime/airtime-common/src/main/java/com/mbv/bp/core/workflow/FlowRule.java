package com.mbv.bp.core.workflow;

import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;
import com.mbv.bp.core.workflow.Flow;
import com.mbv.bp.core.workflow.Workflow;

class FlowRule extends Rule {
	  private String attribute = "name";

    public void begin(String namespace, String name, Attributes attributes)
        throws Exception {
    	String value = attributes.getValue(attribute);
    	Flow flow=new Flow();
    	flow.setName(value);
    	Workflow.getInstance().setFlow(flow);
    }
}
