package com.mbv.bp.core.workflow;

import com.mbv.bp.common.integration.ContextBase;



public interface Task {
    boolean execute(ContextBase context) throws Exception;
}