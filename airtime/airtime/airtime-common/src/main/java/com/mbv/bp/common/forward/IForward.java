package com.mbv.bp.common.forward;

import com.mbv.bp.common.integration.ContextBase;

public interface IForward {
	public boolean execute(ContextBase context);
	public boolean process(ContextBase context);
}
