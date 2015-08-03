package com.mbv.bp.common.dao.airtime;

import java.util.Date;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.vo.airtime.AtSummaryView;
import com.mbv.bp.common.vo.airtime.AtSummaryViewFilter;

public class AtSummaryViewDao extends BaseDao<AtSummaryView>{

	public AtSummaryViewDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AtSummaryViewDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}

	public Date searchMaxDate(AtSummaryView obj) throws DaoException{
		return  (Date)selectObject(obj, AtSummaryView.SELECT_MAX_DATE_BY_PROVIDER_ID);
	}
	
	
	public List<AtSummaryView> search(AtSummaryViewFilter filter) throws DaoException {
		return  selectList(filter, AtSummaryView.SEARCH_BY_FILTER);
	}
	
	public int searchCount(AtSummaryViewFilter filter) throws DaoException {
		return  (Integer)selectObject(filter, AtSummaryView.SEARCH_BY_FILTER_COUNT);
	}
	public List<AtSummaryView> searchCurProviderAmounts() throws DaoException {
		return  selectList(new AtSummaryView(), AtSummaryView.SELECT_CUR_PROVIDERS_AMOUNT);
	}
	public AtSummaryView searchCurProviderAmountByProviderId(String providerId) throws DaoException {
		AtSummaryView obj=new AtSummaryView();
		obj.setProviderId(providerId);
		 List<AtSummaryView> list= selectList(obj, AtSummaryView.SELECT_CUR_PROVIDERS_AMOUNT_BY_PROVIDER_ID);
		 if (list!=null && list.size()>0) 
			 return list.get(0);
		 else
			 return null;
	}
	
	public static void main(String[] args) {
		AtSummaryViewFilter summaryView=new AtSummaryViewFilter();
		summaryView.setProviderId("VNPAY");
		summaryView.setFromDate(DateUtils.convertStringToDate("110701", "yyMMdd"));
		summaryView.setToDate(DateUtils.convertStringToDate("110731", "yyMMdd"));
		summaryView.setRecordSize(10);
		summaryView.setStartRecord(0);
		AtSummaryViewDao viewDao=new AtSummaryViewDao(); 
		try {
			System.out.println(viewDao.searchCurProviderAmounts());
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}

}
