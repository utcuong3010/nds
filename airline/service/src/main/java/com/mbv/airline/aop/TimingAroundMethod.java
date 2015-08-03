package com.mbv.airline.aop;

import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("timingAroundMethod")
public class TimingAroundMethod implements MethodInterceptor{
	
	Logger logger = Logger.getLogger(TimingAroundMethod.class);
	
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		try {
		
			long start = new Date().getTime();

			Object result = methodInvocation.proceed();//continue process
			
			//Calculating time execute 
			long end = new Date().getTime();
			
			logger.info("Total Time Execute method[ "+   methodInvocation.getMethod().getName() + "] is " +( (end-start)/1000) + " in second");
			
			
		
			return result;
		
		}catch(IllegalArgumentException exception) {
			
		}
		
		return null;
	}
}
