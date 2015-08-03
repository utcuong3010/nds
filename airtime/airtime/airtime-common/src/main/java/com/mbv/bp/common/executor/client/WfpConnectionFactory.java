package com.mbv.bp.common.executor.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.mbv.bp.common.executor.ExecutorCfg;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.integration.IObjectFactory;
import com.mbv.bp.common.util.AppUtils;


public class WfpConnectionFactory implements IObjectFactory<IExecutable>{
	private static Log logger = LogFactory.getLog(WfpConnectionFactory.class);
	ExecutorCfg cfg;
	public WfpConnectionFactory(ExecutorCfg cfg) {
		this.cfg=cfg;
	} 
	@Override
	public IExecutable createObject() {
		IExecutable executable=null;
		if (!cfg.isLocalEnable()){
			executable=new WfpConnection(cfg.getHost(), cfg.getPort()); 
			try{
				executable.connect();
				executable.setUsable(true);
			}catch (Exception e) {
				executable.setUsable(false);
				logger.error("Fail to Connect to Workflow Server", e);
			}
			return executable;
		}else{
			try {
				executable=(IExecutable)AppUtils.create(cfg.getClazz());
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				executable=null;
			}
		}
		return executable;
	}

}
