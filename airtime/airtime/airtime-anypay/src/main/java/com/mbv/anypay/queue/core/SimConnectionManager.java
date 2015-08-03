package com.mbv.anypay.queue.core;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.SimInfoDao;
import com.mbv.bp.common.dao.airtime.SimTransactionDao;
import com.mbv.bp.common.executor.client.SimConnectionFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.SimInfo;
import com.mbv.bp.common.vo.airtime.SimTransaction;


public class SimConnectionManager {
	
	private static Log log=LogFactory.getLog(SimConnectionManager.class);
	Map<String , SimInfo> simInfos=new Hashtable<String, SimInfo>(); 
	List<String> simIds=new Vector<String>(); 
 	
	private static class SimConnectionManagerHolder{
		private static SimConnectionManager instance = new SimConnectionManager();
	} 
	
	private SimConnectionManager() {
		load();
	}
	
	public static SimConnectionManager getInstance(){
		return SimConnectionManagerHolder.instance;
	}
	
	public SimInfo checkOutSimPort(long amount, String msisdn){
		SimInfo result=null;
		SimInfo temp=null;
		SimInfo simInfo=null;
		SimInfoDao infoDao=new SimInfoDao();
		try{
			for (String simId:simIds){
				temp=simInfos.get(simId);
				
				if (temp==null) continue;
				
				if(!AppConstants.YES_FLAG.equalsIgnoreCase(temp.getSimStatus())) continue; 
				
				simInfo=new SimInfo();
				simInfo.setMsisdn(temp.getMsisdn());
	//			validate in database. status and amount
				try {
					if (!infoDao.selectByMsisdn(simInfo)) continue;
				} catch (DaoException e) {
					log.error("Fail to get simInfo from database",e);
					try {
						Thread.sleep(2000L);
					} catch (InterruptedException e1) {}
					continue;
				}
				
				if (!AppConstants.YES_FLAG.equalsIgnoreCase(simInfo.getSimStatus())) continue;
					
				
				if (simInfo.getCurrentAmount()-simInfo.getLockAmount()-amount<0) continue;
//				check for available transferTxn
				SimTransaction simTransaction=new SimTransaction();
				simTransaction.setSimId(simInfo.getMsisdn()); 
				simTransaction.setTxnType(AppConstants.ANYPAY_SETTINGS.TRANSFER_TXN); 
				SimTransactionDao simTransactionDao=new SimTransactionDao();
				List<SimTransaction> xferTxns=simTransactionDao.selectPendingStatusBySimIdTxnType(simTransaction);
				if (xferTxns!=null){
					for(SimTransaction xferTxn:xferTxns){
						transfer(xferTxn);
					}
				}
				
				if (result==null)
					result=simInfo;
				else{
					if (result.getCurrentAmount()-result.getLockAmount()<simInfo.getCurrentAmount()-simInfo.getLockAmount())
						result=simInfo;
				}
			}
		}catch (Exception e) {
			log.error("Error while checkout simInfo",e);
		}
		if (result!=null){
			result.setSimStatus(AppConstants.NO_FLAG);
			simInfos.put(result.getMsisdn(), result);
		}
		return result;
	}
 	
	public void checkInSim(String msisdn){
		SimInfo simInfo=simInfos.get(msisdn);
		if (simInfo!=null){
			simInfo.setSimStatus(AppConstants.YES_FLAG);
			simInfos.put(msisdn, simInfo);
		}
	}
	
	
	private void load(){
		simInfos.clear();
		simIds.clear();
		try{
			SimInfoDao infoDao=new SimInfoDao();
			List<SimInfo> list=infoDao.selectAll(); 
			
			for (SimInfo simInfo:list){
				simIds.add(simInfo.getMsisdn());
				simInfo.setSimStatus(AppConstants.YES_FLAG);
				simInfos.put(simInfo.getMsisdn(), simInfo);
			}
		}catch (Exception e) {
			log.error("Fail to init sim manager",e);
		}
	}
	
	public void transfer(SimTransaction txn){
		try {
			SimTransaction simTransaction=new SimTransaction();
			simTransaction.setTxnId(txn.getTxnId());
			simTransaction.setTxnStatus(AppConstants.TXN_STATUS_DELIVERING);
			
			SimTransactionDao simTransactionDao=new SimTransactionDao(); 
			if (simTransactionDao.update(simTransaction)){
				ContextBase ctxRequest=new ContextBase();
				ctxRequest.put(Attributes.ATTR_TXN_TYPE, txn.getTxnType());
				ctxRequest.put(Attributes.ATTR_COM_ID, txn.getSimId());
				ctxRequest.put(Attributes.ATTR_MSISDN, txn.getMsisdn());
				ctxRequest.putLong(Attributes.ATTR_AMOUNT, txn.getAmount());
				ContextBase response=SimConnectionFactory.getInstance().process(ctxRequest);
				log.info("Transfer money:"+response);
			}else
				log.info("Fail to change txn status of transfer txn :"+txn);
		} catch (Exception e) {
			log.error("Fail to transfer money",e);
		}
	}
}
