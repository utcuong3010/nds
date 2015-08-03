package com.mbv.bp.core.integration;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.protoc.IntegrationProtocServiceImp;
import com.mbv.bp.core.workflow.WorkFlowManager;

public class WfpRemoteExecutor extends IntegrationProtocServiceImp{ 
	@Override
	public ContextBase process(ContextBase context) {
		ContextBase result=null;
		try{
			ContextBase reqContext=new ContextBase();
			reqContext.putAll(context);
			result=WorkFlowManager.getInstance().process(reqContext);
		}catch (Exception e) {
			result=context;
			result.put(Attributes.ATTR_ERROR_CODE, ErrorCode.WORKFLOW_EXCEPTION);
			log.error(e.getMessage(), e);
		}
		return result;
	}
}
