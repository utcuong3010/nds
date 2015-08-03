package com.mbv.bp.common.dao.airtime;


import java.util.Date;
import java.util.List;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.AtTransactionFilter;

public class AtTransactionDao extends BaseDao<AtTransaction>{

	public AtTransactionDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AtTransactionDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(AtTransaction obj) throws DaoException{
		return  insert(obj, AtTransaction.INSERT);
	}
	
	public boolean update(AtTransaction obj) throws DaoException{
		return  update(obj, AtTransaction.UPDATE);
	}
	
	public boolean updateByAtTxnId(AtTransaction obj) throws DaoException{
		return  update(obj, AtTransaction.UPDATE_BY_AT_TXN_ID);
	}
	
	public boolean select(AtTransaction obj) throws DaoException{
		return  select(obj, AtTransaction.SELECT);
	}
	
	public boolean selectByAtTxnId(AtTransaction obj) throws DaoException{
		return  select(obj, AtTransaction.SELECT_BY_AT_TXN_ID);
	}
	
	public List<AtTransaction> selectByAtTxnIds(AtTransaction obj) throws DaoException{
		return  selectList(obj, AtTransaction.SELECT_BY_AT_TXN_IDS);
	}
	
	public List<AtTransaction> search(AtTransactionFilter filter) throws DaoException {
		return  selectList(filter, AtTransactionFilter.SEARCH_BY_FILTER);
	}
	
	public int searchCount(AtTransactionFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, AtTransactionFilter.SEARCH_BY_FILTER_COUNT);
	}
	
	
	public boolean terminatedTxn(AtTransaction obj) throws DaoException {
		return update(obj, AtTransaction.UPDATE_FOR_DELETE);
	}
	
	public Date selectMaxTxnDate() throws DaoException {
		return  (Date)selectObject(new AtTransaction(), AtTransaction.SELECT_MAX_TXN_DATE);
	}
	
	public List<AtTransaction> searchForComparesion(AtTransactionFilter filter) throws DaoException {
		return selectList(filter, AtTransactionFilter.SEARCH_FOR_COMPARE_BY_FILTER);
	}
	
	public static void main(String[] args) throws DaoException {
		AtTransactionDao atTransactionDao=new AtTransactionDao();
//		AtTransaction obj=new AtTransaction();
//		obj.setAtTxnId(201105150000003014L);
		AtTransactionFilter filter=new AtTransactionFilter();
//		filter.setAmount(1212);
		
		filter.setTxnStatus(AppConstants.TXN_STATUS_PENDING);  
		System.out.println(filter);
		filter.setRecordSize(20);
		filter.setStartRecord(0);
		System.out.println(atTransactionDao.searchCount(filter) );
		atTransactionDao.search(filter); 
//		atTransactionDao.selectByAtTxnId(obj);
//		System.out.println(obj);
//		List<Long> list=new ArrayList<Long>();
//		list.add(101105150000003014L);
//		list.add(101105150000001003L);
//		obj.setAtTxnIdList(list);
//		Gson gson=new Gson();
//		List<AtTransaction> list2=atTransactionDao.selectByAtTxnIds(obj);
//		System.out.println(gson.toJson(list2));
//		System.out.println(list2.size());
		

	}




}
