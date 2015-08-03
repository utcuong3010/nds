package com.mbv.bp.common.dao.airtime.manager;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.dao.airtime.ReservedAccountDao;
import com.mbv.bp.common.dao.airtime.ReservedTelcoDao;
import com.mbv.bp.common.dao.airtime.ReservedTxnDao;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.common.vo.airtime.ProviderAmount;
import com.mbv.bp.common.vo.airtime.ReservedAccount;
import com.mbv.bp.common.vo.airtime.ReservedTelco;
import com.mbv.bp.common.vo.airtime.ReservedTxn;

public class AmountManager {
	private static final Log log = LogFactory.getLog(AmountManager.class);
	public enum CHARGE_TYPE{
		DIRECT_DEBITING,
		UNIT_RESERVATION
	}
	
	public String checkAmountForDirectDebit(String providerId, long amount) throws Exception{
		String errorCode=ErrorCode.SUCCESS;
		ProviderAmountDao providerAmountDao=new ProviderAmountDao(); 
		ReservedTelcoDao reserveTelcoDao=new ReservedTelcoDao(); 
		
		Map<String, Long> providerAmountMap=new HashMap<String, Long>(); 
		Map<String, Long> telcoLockAmountMap=new HashMap<String, Long>(); 
		
		List<ProviderAmount> providerAmountList= providerAmountDao.selectAll();
		List<ReservedTelco> reservedTelcoList= reserveTelcoDao.selectAll();
		
		for (ProviderAmount providerAmount:providerAmountList)
			providerAmountMap.put(providerAmount.getProviderId(), providerAmount.getTotalLoaded()-providerAmount.getTotalUsed());
		
		for (ReservedTelco telcoLock:reservedTelcoList)
			telcoLockAmountMap.put(telcoLock.getTelcoId(), telcoLock.getReservedAmount()*telcoLock.getPercent()/100);
		
		List<String> telcoList=AppConstants.PROVIDER_TELCOS.get(providerId);
		
	
		long currentAmount;
		
		for (String telco:telcoList ){
			TelcoProvider telcoProvider=AppConstants.TELCO_PROVIDER.get(telco);
			currentAmount=0;
			for (String proId:telcoProvider.getConnectionIds())
				currentAmount=currentAmount+providerAmountMap.get(proId);
			currentAmount=currentAmount-telcoLockAmountMap.get(telco)-amount;
			if (currentAmount<0){
				log.error("not enough money| providerId : "+providerId+"|when check telco : "+telco);
				errorCode=ErrorCode.SYS_NOT_ENOUGH_MONEY;
				break;
			}
		}
		return errorCode;
	}
	
	public String providerCharge(CHARGE_TYPE chargeType ,String providerId, long amount) throws Exception{
		String errorCode=ErrorCode.SUCCESS;
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance(); 
		try{
			sqlMapClient.startTransaction();
		/*
		 * check snapshot balance	
		 */
			ProviderAmountDao providerAmountDao=new ProviderAmountDao(sqlMapClient);
			ProviderAmount providerAmount=new ProviderAmount();
			
			providerAmount.setProviderId(providerId);
			
			if (!providerAmountDao.selectLock(providerAmount)){
				errorCode=ErrorCode.DATABASE_EXCEPTION;
				throw new Exception("not found providerId in database!");
			}
			
			long currentAmount=providerAmount.getTotalLoaded()-providerAmount.getTotalUsed();
			
			switch (chargeType) {
			case DIRECT_DEBITING:
				
				if (currentAmount<amount){
					errorCode=ErrorCode.SYS_NOT_ENOUGH_MONEY;
					throw new Exception("not enough money");
				}
				
				errorCode=checkAmountForDirectDebit(providerId, amount);
				if (!ErrorCode.SUCCESS.equalsIgnoreCase(errorCode)){
					throw new Exception("not enough money");
				}
				
				if (!providerAmountDao.updateUsedAmount(providerId, amount)){
					errorCode=ErrorCode.SYS_INTERNAL_ERROR;
					throw new Exception("Fail to update providerAmount");
				}
				break;
			case UNIT_RESERVATION:
				
				if (currentAmount<amount){
					errorCode=ErrorCode.SYS_NOT_ENOUGH_MONEY;
					throw new Exception("not enough money");
				}
				if (!providerAmountDao.updateUsedAmount(providerId, amount)){
					errorCode=ErrorCode.SYS_INTERNAL_ERROR;
					throw new Exception("Fail to update providerAmount");
				}
				break;	
			default:
				errorCode=ErrorCode.UNSUPPORTED_OPERATION;
				throw new Exception("Unsupported for chargeType :"+chargeType);
			} 
			
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error("Fail to perform charging",e);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(errorCode))
				errorCode=ErrorCode.SYS_INTERNAL_ERROR;
		}finally{
			try{
				sqlMapClient.endTransaction();
			}catch (Exception ex) {
				log.fatal(ex);
			}
		}
		return errorCode;
	}
	
	public String providerRefund(String providerId, long amount){
		try{
			ProviderAmountDao providerAmountDao=new ProviderAmountDao();
			providerAmountDao.updateUsedAmount(providerId, -1*amount);
			return ErrorCode.SUCCESS;
		}catch (Exception e) {
			return ErrorCode.DATABASE_EXCEPTION;
		}
	}
	
	public String createLockAccount(String operation,String systemId, String accountId, String txnId, long amount, String[] telcoIds, String description){
		String errorCode=ErrorCode.SUCCESS;
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance(); 
		try{
			
			sqlMapClient.startTransaction();
			
			ReservedAccountDao amountDao=new ReservedAccountDao(sqlMapClient);
			ReservedTelcoDao telcoDao=new ReservedTelcoDao(sqlMapClient);
			ReservedAccount amountObj=new ReservedAccount();
			ReservedTxn txnObject=new ReservedTxn(); 
			ReservedTxnDao txnDao=new ReservedTxnDao(sqlMapClient);
			
			amountObj.setAccountId(accountId);
			amountObj.setTotalDebit(amount);
			amountObj.setSystemId(systemId);
			amountObj.setTelcoIds(StringUtils.join(telcoIds, ","));
			amountObj.setCreatedDate(new Date());
			amountObj.setDescription(description);
			amountDao.insert(amountObj);
			
			if (!telcoDao.updateLockTelcos(telcoIds, amount)){
				errorCode=ErrorCode.DATABASE_EXCEPTION;
				throw new Exception("Fail to perform update TelcoReservation");
			}
			
			txnObject.setTxnId(txnId);
			txnObject.setAmount(amount);
			txnObject.setAccountId(accountId);
			txnObject.setSystemId(systemId);
			txnObject.setOperation(operation);
			txnObject.setDescription(description);
			txnObject.setCreatedDate(new Date());
			txnDao.insert(txnObject);
			
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error(e);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(errorCode))
			errorCode=ErrorCode.SYS_INTERNAL_ERROR;
		}finally{
			try{
				sqlMapClient.endTransaction();
			}catch (Exception ex) {
				log.fatal(ex);
			}
		}
		return errorCode;
	}

	public String creditLockAccount(String operation,String txnId,String systemId, String accountId,String referenceId, long amount, String description){
		
		String errorCode=ErrorCode.SUCCESS;
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance(); 
		try{
			sqlMapClient.startTransaction();
			ReservedAccount amountObj=new ReservedAccount();
			ReservedAccountDao amountDao=new ReservedAccountDao(sqlMapClient);
			ReservedTelcoDao telcoDao=new ReservedTelcoDao(sqlMapClient);
			ReservedTxn txnObject=new ReservedTxn(); 
			ReservedTxnDao txnDao=new ReservedTxnDao(sqlMapClient);
			
			amountObj.setAccountId(accountId);
			if (amountDao.selectLock(amountObj)){ 
				
				if (amount==0)
					amount=amountObj.getTotalDebit()-amountObj.getTotalCredit();
				
				if (amountObj.getTotalDebit()-amountObj.getTotalCredit()>=amount){
					
					if (!amountDao.updateCredit(accountId,amount)){
						errorCode=ErrorCode.DATABASE_EXCEPTION;
						throw new Exception("Fail to perform unlock reservedAmount");
					}
					
					if (!telcoDao.updateLockTelcos(StringUtils.split(amountObj.getTelcoIds(),","),-1*amount)){
						errorCode=ErrorCode.DATABASE_EXCEPTION;
						throw new Exception("Fail to perform telco locking");
					}
					
					txnObject.setTxnId(txnId);
					txnObject.setAmount(-1*amount);
					txnObject.setAccountId(accountId);
					txnObject.setReferenceId(referenceId);
					txnObject.setSystemId(systemId);
					txnObject.setOperation(operation);
					txnObject.setDescription(description);
					txnObject.setCreatedDate(new Date());
					
					txnDao.insert(txnObject);
				}else{
					errorCode=ErrorCode.AMOUNT_TOO_BIG;
					throw new Exception("Locking amount is smaller than inputted.");
				}
			}else{
				errorCode=ErrorCode.NOT_EXISTED;
				throw new Exception("not found "+accountId +" in database");
			}
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error(e);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(errorCode))
			errorCode=ErrorCode.DATABASE_EXCEPTION;
		}finally{
			try{
				sqlMapClient.endTransaction();
			}catch (Exception ex) {
				log.fatal(ex);
			}
		}
		return errorCode;
		
	}
	
	public String debitLockAccount(String operation, String txnId, String systemId, String accountId, String referenceId, long amount, String description) {
		
		String errorCode=ErrorCode.SUCCESS;
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance(); 
		try{
			sqlMapClient.startTransaction();
			ReservedAccountDao amountDao=new ReservedAccountDao(sqlMapClient);
			ReservedTelcoDao telcoDao=new ReservedTelcoDao(sqlMapClient);
			ReservedAccount amountObj=new ReservedAccount();
			ReservedTxn txnObject=new ReservedTxn(); 
			ReservedTxnDao txnDao=new ReservedTxnDao(sqlMapClient);
			amountObj.setAccountId(accountId);
			if (!amountDao.selectLock(amountObj)){
				errorCode=ErrorCode.NOT_EXISTED;
				throw new Exception("not found accountId in database.");
			}
			if (!amountDao.updateDebit(accountId, amount)){
				errorCode=ErrorCode.DATABASE_EXCEPTION;
				throw new Exception("Fail to update debit.");
			}
			if (!telcoDao.updateLockTelcos(StringUtils.split(amountObj.getTelcoIds(),","), amount)){
				errorCode=ErrorCode.DATABASE_EXCEPTION;
				throw new Exception("Fail to perform update TelcoReservation");
			}
			txnObject.setTxnId(txnId);
			txnObject.setAmount(amount);
			txnObject.setAccountId(accountId);
			txnObject.setSystemId(systemId);
			txnObject.setReferenceId(referenceId);
			txnObject.setOperation(operation);
			txnObject.setDescription(description);
			txnObject.setCreatedDate(new Date());
			txnDao.insert(txnObject);
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error(e);
			if (ErrorCode.SUCCESS.equalsIgnoreCase(errorCode))
			errorCode=ErrorCode.SYS_INTERNAL_ERROR;
		}finally{
			try{
				sqlMapClient.endTransaction();
			}catch (Exception ex) {
				log.fatal(ex);
			}
		}
		return errorCode;
	}
	
	public void creditLockAccount(SqlMapClient sqlMapClient, String operation,String txnId,String systemId, 
			String accountId,String referenceId, long amount, String description) throws DaoException{

		ReservedAccount amountObj=new ReservedAccount();
		ReservedAccountDao amountDao=new ReservedAccountDao(sqlMapClient);
		ReservedTelcoDao telcoDao=new ReservedTelcoDao(sqlMapClient);
		ReservedTxn txnObject=new ReservedTxn(); 
		ReservedTxnDao txnDao=new ReservedTxnDao(sqlMapClient);
		amountObj.setAccountId(accountId);
		
		if (amountDao.selectLock(amountObj)){
			
			if (!amountDao.updateCredit(accountId,amount))
				throw new DaoException(ErrorCode.DATABASE_EXCEPTION+"|Fail to perform unlock reservedAmount");
			
			if (!telcoDao.updateLockTelcos(StringUtils.split(amountObj.getTelcoIds(),","),-1*amount))
				throw new DaoException(ErrorCode.DATABASE_EXCEPTION+"|Fail to perform telco locking");
			
			txnObject.setTxnId(txnId);
			txnObject.setAmount(-1*amount);
			txnObject.setAccountId(accountId);
			txnObject.setReferenceId(referenceId);
			txnObject.setSystemId(systemId);
			txnObject.setOperation(operation);
			txnObject.setDescription(description);
			txnObject.setCreatedDate(new Date());
			txnDao.insert(txnObject);

		}else{
			throw new DaoException(ErrorCode.NOT_EXISTED+"| not found "+accountId +" in database");
		}
	}
	
	public void debitLockAccount(SqlMapClient sqlMapClient,String operation, String txnId, String systemId, 
			String accountId, String referenceId, long amount, String description) throws DaoException{
		
		ReservedAccountDao amountDao=new ReservedAccountDao(sqlMapClient);
		ReservedTelcoDao telcoDao=new ReservedTelcoDao(sqlMapClient);
		ReservedAccount amountObj=new ReservedAccount();
		ReservedTxn txnObject=new ReservedTxn(); 
		ReservedTxnDao txnDao=new ReservedTxnDao(sqlMapClient);
		amountObj.setAccountId(accountId);
		
		if (!amountDao.selectLock(amountObj))
			throw new DaoException(ErrorCode.NOT_EXISTED+"| Not found accountId in database.");
		
		if (!amountDao.updateDebit(accountId, amount))
			throw new DaoException(ErrorCode.DATABASE_EXCEPTION+"| Fail to update debit.");
		
		if (!telcoDao.updateLockTelcos(StringUtils.split(amountObj.getTelcoIds(),","), amount))
			throw new DaoException(ErrorCode.DATABASE_EXCEPTION+"| Fail to perform update TelcoReservation");
		
		txnObject.setTxnId(txnId);
		txnObject.setAmount(amount);
		txnObject.setAccountId(accountId);
		txnObject.setSystemId(systemId);
		txnObject.setReferenceId(referenceId);
		txnObject.setOperation(operation);
		txnObject.setDescription(description);
		txnObject.setCreatedDate(new Date());
		txnDao.insert(txnObject);
	}
}
