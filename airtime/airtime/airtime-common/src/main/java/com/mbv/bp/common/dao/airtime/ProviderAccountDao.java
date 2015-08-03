package com.mbv.bp.common.dao.airtime;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.ProviderAccount;
import com.mbv.bp.common.vo.airtime.ProviderAccountFilter;




public class ProviderAccountDao extends BaseDao<ProviderAccount>{

	public ProviderAccountDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public ProviderAccountDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);

	}

	public List<ProviderAccount> selectAll() throws DaoException{
		return  selectList(new ProviderAccount(), ProviderAccount.SELECT_ALL);
	}

	public boolean insert(ProviderAccount obj) throws DaoException{
		return  insert(obj, ProviderAccount.INSERT);
	}

	public boolean updateDeleted(ProviderAccount obj) throws DaoException{
		return  update(obj, ProviderAccount.UPDATE_DELETED);
	}

	public boolean dynamicUpdate(ProviderAccount obj) throws DaoException{
		return  update(obj, ProviderAccount.DYNAMIC_UPDATE);
	}

	public  boolean select(ProviderAccount obj) throws DaoException{
		return  select(obj, ProviderAccount.SELECT);
	}

	public  List<ProviderAccount> selectAll(ProviderAccount obj) throws DaoException{
		return  selectList(obj, ProviderAccount.SELECT_ALL);
	}

	public List<ProviderAccount> search(ProviderAccountFilter filter) throws DaoException {
		return  selectList(filter, ProviderAccount.SEARCH_BY_FILTER);
	}
	
	public int searchCount(ProviderAccountFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, ProviderAccount.SEARCH_BY_FILTER_COUNT);
	}
	
	public Date searchMinDate(ProviderAccount obj) throws DaoException {
		return  (Date)selectObject(obj, ProviderAccount.SEARCH_BY_MIN_DATE_BY_PROVIDER_ID);
	}
	
	public Date searchMaxDate(ProviderAccount obj) throws DaoException {
		return  (Date)selectObject(obj, ProviderAccount.SEARCH_BY_MAX_DATE_BY_PROVIDER_ID);
	}
	
	public boolean selectProviderSummaryByDate(ProviderAccount obj) throws DaoException {
		return  select(obj, ProviderAccount.PROVIDER_SUMMARY_BY_DATE);
	}
	public long selectTotalAmountByProviderId(String providerId) throws DaoException {
		ProviderAccount obj=new ProviderAccount();
		obj.setProviderId(providerId);
		return  (Long)selectObject(obj, ProviderAccount.SELECT_TOTAL_AMOUNT_BY_PROVIDER_ID);
	}
	
	public static void main(String[] args) {

		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
		try{
			sqlMapClient.startTransaction();
			ProviderAccountDao dao=new ProviderAccountDao(sqlMapClient);
//			update for delete
			ProviderAccount oldAccount=new ProviderAccount();
			oldAccount.setTxnId("54321");
			oldAccount.setDeleted(1L);
			oldAccount.setUpdatedBy("test");
			oldAccount.setUpdatedDate(new Date());	 
			dao.updateDeleted(oldAccount);
//			insert new record
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e1) {
				
			}
			
		}
	}
}
