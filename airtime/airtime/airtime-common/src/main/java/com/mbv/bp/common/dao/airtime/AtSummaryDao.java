package com.mbv.bp.common.dao.airtime;

import java.util.Date;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AtSummary;
import com.mbv.bp.common.vo.airtime.AtSummaryFilter;


public class AtSummaryDao extends BaseDao<AtSummary>{

	public AtSummaryDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AtSummaryDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(AtSummary obj) throws DaoException{
		return  insert(obj, AtSummary.INSERT);
	}
	
	public boolean update(AtSummary obj) throws DaoException{
		return  update(obj, AtSummary.UPDATE);
	}
	
	public boolean select(AtSummary obj) throws DaoException{
		return  select(obj, AtSummary.SELECT);
	}
	
	public Date searchMaxDate(AtSummary obj) throws DaoException{
		return  (Date)selectObject(obj, AtSummary.SELECT_MAX_DATE_BY_PROVIDER_ID);
	}
	
	public static void main(String[] args) throws DaoException {

	}

	public List<AtSummary> search(AtSummaryFilter filter) throws DaoException {
		return  selectList(filter, AtSummary.SEARCH_BY_FILTER);
	}
	
	public int searchCount(AtSummaryFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, AtSummary.SEARCH_BY_FILTER_COUNT);
	}
	
	public long selectBeginAmount(AtSummary obj) throws DaoException {
		return  (Long)selectObject(obj, AtSummary.SELECT_BEGIN_AMOUNT);
	}
}
