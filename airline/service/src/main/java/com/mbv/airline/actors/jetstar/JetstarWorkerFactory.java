package com.mbv.airline.actors.jetstar;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbv.airline.actors.AbstractWorker;
import com.mbv.airline.actors.AbstractWorkerFactory;

@Component
public class JetstarWorkerFactory extends AbstractWorkerFactory implements InitializingBean{
    
	@Autowired
	private JetstarConfig jetstarConfig;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		this.setNumWorkers(jetstarConfig.getNumWorkers());
		this.setWorkerName(jetstarConfig.getWorkerName());
	}

    @Override
    protected AbstractWorker doCreate() {
        return new JetstarWorker(jetstarConfig);
    }
}