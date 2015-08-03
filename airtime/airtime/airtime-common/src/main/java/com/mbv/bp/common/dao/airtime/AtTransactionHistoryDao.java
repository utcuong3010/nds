package com.mbv.bp.common.dao.airtime;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AtTransaction;


public class AtTransactionHistoryDao extends BaseDao<AtTransaction>{
	
	
	public AtTransactionHistoryDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AtTransactionHistoryDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public List<AtTransaction> selectByAtTxnIds(AtTransaction atTxn) throws DaoException{
		return  selectList(atTxn, AtTransaction.SELECT_BY_AT_TXN_IDS_HISTORY);
	}
	
	public static void main(String[] args) throws DaoException {
		AtTransactionDao AtTransactionDao=new AtTransactionDao();
		AtTransaction obj=new AtTransaction();
		obj.setAtTxnId(201105150000003014L);
		AtTransactionDao.selectByAtTxnId(obj);
		System.out.println(obj);
		List<Long> list=new ArrayList<Long>();
		list.add(101105150000003014L);
		list.add(101105150000001003L);
		obj.setAtTxnIdList(list);
		Gson gson=new Gson();
		List<AtTransaction> list2=AtTransactionDao.selectByAtTxnIds(obj);
		System.out.println(gson.toJson(list2));
		System.out.println(list2.size());
		

	}
	
}
