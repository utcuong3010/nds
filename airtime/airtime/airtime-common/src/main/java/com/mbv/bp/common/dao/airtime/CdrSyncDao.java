package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.CdrSync;
import com.mbv.bp.common.vo.airtime.CdrSyncFilter;



public class CdrSyncDao extends BaseDao<CdrSync>{

	public CdrSyncDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public CdrSyncDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(CdrSync obj) throws DaoException{
		return  insert(obj, CdrSync.INSERT);
	}
	
	public boolean selectByAtTxnIdAndProviderId(CdrSync cdrSync) throws DaoException {
		return select(cdrSync, CdrSync.SELECT_BY_AT_TXN_ID_AND_PROVIDER_ID);
	}
	
	public boolean selectByAtTxnIdAndProviderIdAndErrorCode(CdrSync cdrSync) throws DaoException {
		return select(cdrSync, CdrSync.SELECT_BY_AT_TXN_ID_AND_PROVIDER_ID_ERROR_CODE);
	}
	
	public boolean selectByPTxnIdAndProviderId(CdrSync cdrSync) throws DaoException {
		return select(cdrSync, CdrSync.SELECT_BY_P_TXN_ID_AND_PROVIDER_ID);
	}
	
	public boolean selectByMessageAndIdProviderId(CdrSync cdrSync) throws DaoException {
		return select(cdrSync, CdrSync.SELECT_BY_MESSAGE_ID_AND_PROVIDER_ID);
	}
	
	public boolean updateByAtTxnIdAndProviderId(CdrSync cdrSync) throws DaoException {
		return update(cdrSync, CdrSync.UPDATE_BY_AT_TXN_ID_AND_PROVIDER_ID);
	}
	
	public boolean updateByMessageIdAndProviderId(CdrSync cdrSync) throws DaoException {
		return update(cdrSync, CdrSync.UPDATE_BY_MESSAGE_ID_AND_PROVIDER_ID);
	}
	
	public boolean updateByAtTxnIdAndProviderIdAndErrorCode(CdrSync cdrSync) throws DaoException {
		return update(cdrSync, CdrSync.UPDATE_BY_AT_TXN_ID_AND_PROVIDER_ID_ERROR_CODE);
	}
	
	public boolean updateStatusByAtTxnIdAndProviderId(CdrSync cdrSync) throws DaoException {
		return update(cdrSync, CdrSync.UPDATE_STATUS_BY_AT_TXN_ID_AND_PROVIDER_ID);
	}
	
	public boolean updateStatusByMessageIdAndProviderId(CdrSync cdrSync) throws DaoException {
		return update(cdrSync, CdrSync.UPDATE_STATUS_BY_MESSAGE_ID_AND_PROVIDER_ID);
	}
	
	public List<CdrSync> search(CdrSyncFilter filter) throws DaoException {
		return  selectList(filter, CdrSyncFilter.SEARCH_BY_FILTER);
	}
	
	public int searchCount(CdrSyncFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, CdrSyncFilter.SEARCH_BY_FILTER_COUNT);
	}
}
