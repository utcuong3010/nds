package com.mbv.bp.common.dao.airtime;

import java.util.Arrays;
import java.util.List;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.ReservedTelco;

public class ReservedTelcoDao extends BaseDao<ReservedTelco>{

	public ReservedTelcoDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public ReservedTelcoDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean updateLockTelcos(String[] telcoIds, long amount)throws DaoException {
		ReservedTelco reservedTelco=new ReservedTelco();
		reservedTelco.setReservedAmount(amount);
		reservedTelco.setTelcoids(Arrays.asList(telcoIds));
		return update(reservedTelco, ReservedTelco.UPDATE_LOCK_TELCOS);
	}

	public List<ReservedTelco> selectAll() throws DaoException {
		return selectList(new ReservedTelco(), ReservedTelco.SELECT_ALL);
	}
	
	
}
