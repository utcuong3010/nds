package com.mbv.bp.common.dao.airtime;

import java.util.List;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.ProviderAmount;

public class ProviderAmountDao extends BaseDao<ProviderAmount>{

	public ProviderAmountDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public ProviderAmountDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);

	}

	public List<ProviderAmount> selectAll() throws DaoException{
		return  selectList(new ProviderAmount(), ProviderAmount.SELECT_ALL);
	}

	public boolean insert(ProviderAmount obj) throws DaoException{
		return  insert(obj, ProviderAmount.INSERT);
	}

	public boolean update(ProviderAmount obj) throws DaoException{
		return  update(obj, ProviderAmount.UPDATE);
	}

	public  boolean select(ProviderAmount obj) throws DaoException{
		return  select(obj, ProviderAmount.SELECT);
	}
	
	public boolean updateUsedAmount(String providerId, long addAmount) throws DaoException{
		ProviderAmount obj=new ProviderAmount();
		obj.setTotalUsed(addAmount);
		obj.setProviderId(providerId);
		return update(obj, ProviderAmount.UPDATE_USED_AMOUNT);
	}
	
	public Long selectAmount(String providerId){
		try{
			long totalAmount;
			long usedAmount;
			ProviderAccountDao accountDao=new ProviderAccountDao();
			totalAmount=accountDao.selectTotalAmountByProviderId(providerId);
			ProviderAmount obj=new ProviderAmount();
			obj.setProviderId(providerId);
			if (select(obj)){
				usedAmount=obj.getTotalUsed();
			}else{
				usedAmount=0L;
				obj.setProviderId(providerId);
				obj.setTotalLoaded(0L);
				obj.setTotalUsed(0L);
				insert(obj);
			}
			return (totalAmount-usedAmount);
		}catch (Exception e) {
			log.error("Fail to get current Amount from database");
			return null;
		}
	}

	public boolean selectLock(ProviderAmount obj) throws DaoException {
		return select(obj,ProviderAmount.SELECT_LOCK);
	}

	public boolean updateLoadedAmount(String providerId, long amountChange) throws DaoException{
		ProviderAmount obj=new ProviderAmount();
		obj.setTotalLoaded(amountChange);
		obj.setProviderId(providerId);
		return update(obj, ProviderAmount.UPDATE_LOADED_AMOUNT);
	}
}
