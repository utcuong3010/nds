package com.mbv.anypay.queue.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.generator.IdGenerator;

public class SimIdFactory {
	private static Log log=LogFactory.getLog(SimIdFactory.class);
	private static SimIdGenerator vtelSimIdGenerator;
	private static SimIdFactory instance;
	static {
		instance=new SimIdFactory();
	}
	private SimIdFactory() {
		vtelSimIdGenerator=new SimIdGenerator(1, 
				9999999999L, 1000,  "SimSeq");
	}
	
	public static SimIdFactory getInstance(){
		return instance;
	}
	
	public IdGenerator getSimIdGenerator() {
		return vtelSimIdGenerator;
	}
	
	public boolean destroy() {
		boolean result=false;
		try{
			vtelSimIdGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown SimIdGenerator.",e);
			result=false;
		}
		return result;
	}
	
}
