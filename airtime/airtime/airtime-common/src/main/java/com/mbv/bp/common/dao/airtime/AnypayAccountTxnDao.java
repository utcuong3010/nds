package com.mbv.bp.common.dao.airtime;

import java.util.List;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AnypayAccountTxn;
import com.mbv.bp.common.vo.airtime.AnypayAccountTxnFilter;


public class AnypayAccountTxnDao extends BaseDao<AnypayAccountTxn>{

	public AnypayAccountTxnDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AnypayAccountTxnDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);

	}

	public boolean insert(AnypayAccountTxn obj) throws DaoException{
		return  insert(obj, AnypayAccountTxn.INSERT);
	}

	public boolean updateDeleted(AnypayAccountTxn obj) throws DaoException{
		return  update(obj, AnypayAccountTxn.UPDATE_DELETED);
	}

	public boolean dynamicUpdate(AnypayAccountTxn obj) throws DaoException{
		return  update(obj, AnypayAccountTxn.UPDATE);
	}

	public  boolean select(AnypayAccountTxn obj) throws DaoException{
		return  select(obj, AnypayAccountTxn.SELECT);
	}

	public List<AnypayAccountTxn> search(AnypayAccountTxnFilter filter) throws DaoException {
		return  selectList(filter, AnypayAccountTxn.SEARCH_BY_FILTER);
	}
	
	public int searchCount(AnypayAccountTxnFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, AnypayAccountTxn.SEARCH_BY_FILTER_COUNT);
	}
	
}
