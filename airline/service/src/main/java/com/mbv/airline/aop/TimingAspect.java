package com.mbv.airline.aop;

import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.mbv.airline.common.JsonUtils;

@Component
@Aspect
public class TimingAspect {
	
	
	
	Logger logger = Logger.getLogger(TimingAspect.class);
	
	@Around("execution(* com.mbv.airline.actors.AbstractWorker.*(..))")
	public void timingExecuteAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		
		System.err.println("aaaaaaaa");
		try {
			
			long start = new Date().getTime();

			Object result = proceedingJoinPoint.proceed();//continue process
			
			//Calculating time execute 
			long end = new Date().getTime();
			
			System.err.println(JsonUtils.toString(result));
			
			String methodName =  proceedingJoinPoint.getSignature().getName();
			
			logger.info("Total Time Execute method[ "+ methodName+ "] is " +( (end-start)/1000) + " in second");
			
	
		
		}catch(IllegalArgumentException exception) {
			logger.error("Timing Execution with error:" +exception);
			throw exception;
		}

	}
	
	
}
