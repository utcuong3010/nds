package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.SimInfo;

public class SimInfoDao extends BaseDao<SimInfo>{

	public SimInfoDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public SimInfoDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}

	public List<SimInfo> selectAll()throws DaoException{
		return selectList(new SimInfo(), SimInfo.SELECT_ALL);
	}
	
	public boolean selectByComId(SimInfo simInfo)throws DaoException{
		return select(new SimInfo(), SimInfo.SELECT_BY_COM_ID);
	}
	
	public boolean selectByMsisdn(SimInfo simInfo)throws DaoException{
		return select(simInfo, SimInfo.SELECT_BY_MSISDN);
	}

	public List<SimInfo> selectAllAvailableSimPort() throws DaoException{
		return selectList(new SimInfo(), SimInfo.SELECT_ALL_AVAILABLE_MSISDN);
	}

	public boolean updateCurrentLockAmountByMsisdn(SimInfo simInfo) throws DaoException{
		return update(simInfo, simInfo.UPDATE_CURRENT_LOCK_AMOUNT_BY_MSISDN);
	}
}
