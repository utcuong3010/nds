package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.AtTransactionFilter;
import com.mbv.bp.common.vo.airtime.ReservedTxn;
import com.mbv.bp.common.vo.airtime.ReservedTxnFilter;

public class ReservedTxnDao extends BaseDao<ReservedTxn>{

	public ReservedTxnDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public ReservedTxnDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(ReservedTxn obj) throws DaoException{
		return  insert(obj, ReservedTxn.INSERT);
	}

	public boolean select(ReservedTxn reservedTxn) throws DaoException{
		return select(reservedTxn,ReservedTxn.SELECT);
	}

	public List<ReservedTxn> search(ReservedTxnFilter filter) throws DaoException {
		return  selectList(filter, ReservedTxnFilter.SEARCH_BY_FILTER);
	}
	
	public int searchCount(ReservedTxnFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, ReservedTxnFilter.SEARCH_BY_FILTER_COUNT);
	}
}
