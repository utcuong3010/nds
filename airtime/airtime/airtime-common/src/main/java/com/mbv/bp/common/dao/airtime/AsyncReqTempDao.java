package com.mbv.bp.common.dao.airtime;



import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.common.vo.airtime.AsyncReqTempFilter;


public class AsyncReqTempDao extends BaseDao<AsyncReqTemp>{

	public AsyncReqTempDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AsyncReqTempDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean select(AsyncReqTemp obj) throws DaoException{
		return  select(obj, AsyncReqTemp.SELECT);
	}
	
	public List<AsyncReqTemp> selectByOperationType(AsyncReqTemp obj) throws DaoException{
		return  selectList(obj, AsyncReqTemp.SELECT_BY_OPERATION_TYPE);
	}
	
	public boolean insert(AsyncReqTemp obj) throws DaoException{
		return  insert(obj, AsyncReqTemp.INSERT);
	}
	
	public boolean update(AsyncReqTemp obj) throws DaoException {
		return update(obj, AsyncReqTemp.UPDATE);
		
	}

	public int searchCount(AsyncReqTempFilter filter) throws DaoException {
		return (Integer)selectObject(filter, AsyncReqTempFilter.SEARCH_BY_FILTER_COUNT);
	}

	public List<AsyncReqTemp> search(AsyncReqTempFilter filter) throws DaoException {
		return selectList(filter, AsyncReqTempFilter.SEARCH_BY_FILTER);
	}
	
}
