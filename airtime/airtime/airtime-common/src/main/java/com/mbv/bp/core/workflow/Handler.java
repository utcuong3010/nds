package com.mbv.bp.core.workflow;

import com.mbv.bp.common.integration.ContextBase;

public abstract class Handler implements Task{
	public boolean execute(ContextBase context){
		return true;
	}
	
	public abstract boolean handle(ContextBase context, Exception exception);
}
