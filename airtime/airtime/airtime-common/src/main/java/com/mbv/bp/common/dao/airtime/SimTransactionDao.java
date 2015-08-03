package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.SimTransaction;
import com.mbv.bp.common.vo.airtime.SimTransactionFilter;

public class SimTransactionDao extends BaseDao<SimTransaction>{

	public SimTransactionDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public SimTransactionDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean select(SimTransaction simTransaction) throws DaoException{
		return select(simTransaction,simTransaction.SELECT_BY_TXN_ID);
	}

	public List<SimTransaction> selectPSStatusByMsisdnSimIdTxnType(SimTransaction simTransaction) throws DaoException{
		return selectList(simTransaction,simTransaction.SELECT_PD_STATUS_BY_MSISDN_SIMID_TXNTYPE);
	}
	
	public List<SimTransaction> selectPendingStatusBySimIdTxnType(SimTransaction simTransaction) throws DaoException{
		return selectList(simTransaction,simTransaction.SELECT_PENDING_STATUS_BY_SIMID_TXNTYPE);
	}
	
	public boolean insert(SimTransaction simTransaction) throws DaoException{
		return insert(simTransaction,simTransaction.INSERT);
	}

	public boolean update(SimTransaction simTransaction) throws DaoException{
		return update(simTransaction,simTransaction.UPDATE);
	}

	public int searchCount(SimTransactionFilter filter) throws DaoException{
		return (Integer)selectObject(filter,SimTransactionFilter.SEARCH_BY_FILTER_COUNT);
	}

	public List<SimTransaction> search(SimTransactionFilter filter) throws DaoException{
		return selectList(filter, SimTransactionFilter.SEARCH_BY_FILTER);
	}
}
