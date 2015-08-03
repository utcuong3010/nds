package com.mbv.bp.common.dao.airtime;

import java.util.Date;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.ReservedAccount;
import com.mbv.bp.common.vo.airtime.ReservedAccountFilter;
import com.mbv.bp.common.vo.airtime.ReservedTxn;
import com.mbv.bp.common.vo.airtime.ReservedTxnFilter;

public class ReservedAccountDao extends BaseDao<ReservedAccount>{

	public ReservedAccountDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public ReservedAccountDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(ReservedAccount obj) throws DaoException{
		return  insert(obj, ReservedAccount.INSERT);
	}
	
	public boolean selectLock(ReservedAccount obj) throws DaoException{
		return  select(obj, ReservedAccount.SELECT_LOCK);
	}
	
	public boolean select(ReservedAccount obj) throws DaoException{
		return  select(obj, ReservedAccount.SELECT);
	}
	

	public boolean updateCredit(String accountId, long amount) throws DaoException {
		ReservedAccount obj=new ReservedAccount(); 
		obj.setAccountId(accountId);
		obj.setTotalCredit(amount);
		obj.setUpdatedDate(new Date());
		return update(obj,ReservedAccount.UPDATE_CREDIT_AMOUNT);
	}
	
	public boolean updateDebit(String accountId, long amount) throws DaoException {
		ReservedAccount obj=new ReservedAccount(); 
		obj.setAccountId(accountId);
		obj.setTotalDebit(amount);
		obj.setUpdatedDate(new Date());
		return update(obj,ReservedAccount.UPDATE_DEBIT_AMOUNT);
	}

	public List<ReservedAccount> search(ReservedAccountFilter filter) throws DaoException {
		return  selectList(filter, ReservedAccountFilter.SEARCH_BY_FILTER);
	}
	
	public int searchCount(ReservedAccountFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, ReservedAccountFilter.SEARCH_BY_FILTER_COUNT);
	}

}
