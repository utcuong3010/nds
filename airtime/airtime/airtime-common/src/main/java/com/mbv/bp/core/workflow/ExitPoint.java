package com.mbv.bp.core.workflow;

import com.mbv.bp.common.integration.ContextBase;


public abstract class ExitPoint implements Task{
	 public boolean execute(ContextBase context) throws Exception{
		 return true;
	 }
	 public abstract boolean process(ContextBase context) throws Exception;
}
