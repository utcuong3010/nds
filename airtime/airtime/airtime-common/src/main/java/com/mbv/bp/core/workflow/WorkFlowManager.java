package com.mbv.bp.core.workflow;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.integration.ContextBase;

public class WorkFlowManager {

	private static class WorkFlowManagerHolder {
		private static WorkFlowManager instance = new WorkFlowManager();
	}

	public static WorkFlowManager getInstance() {
		return WorkFlowManagerHolder.instance;
	}

	private static Map<String, WfpExecutor> wfpMaps = new ConcurrentHashMap<String, WfpExecutor>();
	Object lock = new Object();

	private WorkFlowManager() {
		wfpMaps.clear();
	}

	public ContextBase process(ContextBase context) throws Exception {
		String wfpName = context.getString(Attributes.ATTR_WFP_NAME);
		WfpExecutor executor = null;
		
		if (wfpMaps.containsKey(wfpName)) {
			executor = wfpMaps.get(wfpName);
		} else {
			try {
				executor = new WfpExecutor(wfpName);
			} catch (Exception e) {
				throw e;
			}
			if (executor != null) {
				synchronized (lock) {
					wfpMaps.put(wfpName, executor);
				}
			}
		}
		if (executor != null) {
			context = executor.process(context);
		} else
			throw new Exception("not found " + wfpName);
		return context;
	}

}
