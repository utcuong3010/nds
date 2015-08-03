package com.mbv.airline.actors.vietjet;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mbv.airline.actors.AbstractWorker;
import com.mbv.airline.actors.AbstractWorkerFactory;

@Component
public class VietjetWorkerFactory extends AbstractWorkerFactory implements InitializingBean {
	

	@Autowired
    private VietJetConfig vietjetConfig;
	
	public void afterPropertiesSet() throws Exception {
		System.out.println(vietjetConfig);
		this.setNumWorkers(vietjetConfig.getNumWorkers());
		this.setWorkerName(vietjetConfig.getWorkerName());		
	}

    @Override
    protected AbstractWorker doCreate() {
    	
    	return new VietjetWorker(vietjetConfig);
    }

}
