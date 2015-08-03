package com.mbv.bp.core;

import com.mbv.bp.core.workflow.Workflow;

public class TestWfp {
public static void main(String[] args) {
	Workflow.getInstance().init();
	System.out.println(Workflow.getInstance().getWorkflows());
}
}
