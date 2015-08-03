package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.SmsContent;
import com.mbv.bp.common.vo.airtime.SmsContentFilter;

public class SmsContentDao extends BaseDao<SmsContent>{

	public SmsContentDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public SmsContentDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(SmsContent obj) throws DaoException{
		return  insert(obj, SmsContent.INSERT);
	}

	public List<SmsContent> search(SmsContentFilter filter) throws DaoException {
		return  selectList(filter, SmsContentFilter.SEARCH_BY_FILTER);
	}
	
	public int searchCount(SmsContentFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, SmsContentFilter.SEARCH_BY_FILTER_COUNT);
	}
}
