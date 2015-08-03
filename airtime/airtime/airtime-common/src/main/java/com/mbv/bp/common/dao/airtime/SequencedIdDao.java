package com.mbv.bp.common.dao.airtime;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.SequencedId;

public class SequencedIdDao extends BaseDao<SequencedId>{

	public SequencedIdDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public SequencedIdDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public SequencedId selectSequencedIdByType(String type) throws DaoException{
		SequencedId obj=new SequencedId();
		obj.setType(type);
		if (select(obj, SequencedId.SELECT_BY_TYPE))
		return obj;
		else return null;
	}
	
	public boolean insert(SequencedId obj) throws DaoException{
		return  insert(obj, SequencedId.INSERT);
	}

	public boolean updateSequencedId(SequencedId obj) throws DaoException {
		return update(obj, SequencedId.UPDATE);
	}
}
