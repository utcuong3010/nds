package com.mbv.frontend.generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.generator.IdGenerator;

public class IdGeneratorFactory {
	private static Log log=LogFactory.getLog(IdGeneratorFactory.class);
	private static FeIdGenerator feIdGenerator;
	private static IdGeneratorFactory instance;
	static {
		instance=new IdGeneratorFactory();
	}
	private IdGeneratorFactory() {
		feIdGenerator=new FeIdGenerator(1, 
				9999999999L, 1000,  "FeSeq");
	}
	
	public static IdGeneratorFactory getInstance(){
		return instance;
	}
	
	public IdGenerator getFeIdGenerator() {
		return feIdGenerator;
	}
	
	public boolean destroy() {
		boolean result=false;
		try{
			feIdGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown FeIdGenerator.",e);
			result=false;
		}
		return result;
	}
	
}
