package com.mbv.bp.common.generator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;

public class IdGeneratorFactory {
	private static Log log=LogFactory.getLog(IdGeneratorFactory.class);
	private static IdGenerator vnPayIdGenerator;
	private static IdGenerator vtelIdGenerator;
	private static IdGenerator airTimeGenerator;
	private static IdGenerator vascIdGenerator;
	private static IdGenerator vinaphoneIdGenerator;
	private static IdGeneratorFactory instance;
	static {
		instance=new IdGeneratorFactory();
	}
	private IdGeneratorFactory() {
		vnPayIdGenerator=new VnPayIdGenerator(AppConstants.VNPAY_SETTINGS.getInitialValue(), 
				AppConstants.VNPAY_SETTINGS.getMaxValue(), AppConstants.VNPAY_SETTINGS.getUpdateIncerment(),  AppConstants.VNPAY_SETTINGS.getSeqName());
		airTimeGenerator=new AirTimeGenerator(0, 
				9999999999L, 1000,  "AirTimeSeq");
		
		vtelIdGenerator=new ViettelIdGenerator(1, 
				99999999L, 1000,  "VtelSeq");
		vascIdGenerator=new VascIdGenerator(1, 
				99999L, 1000,  "VascSeq");
		vinaphoneIdGenerator=new VinaphoneIdGenerator(1, 
				99999L, 1000,  "VinaSeq");
	}
	
	public static IdGeneratorFactory getInstance(){
		return instance;
	}
	
	public IdGenerator getVascIdGenentor() {
		return vascIdGenerator;
	}
	
	public IdGenerator getVinaphoneIdGenentor() {
		return vinaphoneIdGenerator;
	}
	
	public IdGenerator getVnPayIdGenentor() {
		return vnPayIdGenerator;
	}
	
	public IdGenerator getAirTimeIdGenentor() {
		return airTimeGenerator;
	}
	
	public IdGenerator getVtelIdGenerator() {
		return vtelIdGenerator;
	}
	
	public boolean destroy() {
		boolean result=false;
		try{
			vnPayIdGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown VnPayIdGenerator.",e);
			result=false;
		}
		
		try{
			vtelIdGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown vtelIdGenerator.",e);
			result=false;
		}
		
		try{
			airTimeGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown AirtimeIdGenerator.",e);
			result=false;
		}
		
		try{
			vascIdGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown vascIdGenerator.",e);
			result=false;
		}
		
		try{
			vinaphoneIdGenerator.shutdownSeq();
			result=true;
		}catch (Exception e) {
			log.error("fail to shutdown vinaphoneIdGenerator.",e);
			result=false;
		}
		
		return result;
	}
	
}
