package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;

public class AirtimeConfigDao extends BaseDao<AirtimeConfig>{

	public AirtimeConfigDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public AirtimeConfigDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean select(AirtimeConfig obj) throws DaoException{
		return  select(obj, AirtimeConfig.SELECT);
	}
	
	public boolean insert(AirtimeConfig obj) throws DaoException{
		return  insert(obj, AirtimeConfig.INSERT);
	}
	
	public List<AirtimeConfig> selectByModuleAndType(AirtimeConfig obj) throws DaoException{
		return  selectList(obj, AirtimeConfig.SELECT_BY_MODULE_AND_TYPE);
	}

	public boolean update(AirtimeConfig airtimeConfig) throws DaoException {
		return update(airtimeConfig, AirtimeConfig.UPDATE);
		
	}
	public String getValue(String module,String type, String key ) throws DaoException {
			AirtimeConfig airtimeConfig=new AirtimeConfig();
			AirtimeConfigDao airtimeConfigDao=new AirtimeConfigDao();
			airtimeConfig.setModule(module);
			airtimeConfig.setType(type);
			airtimeConfig.setParamKey(key);
			if (airtimeConfigDao.select(airtimeConfig)){
				return airtimeConfig.getParamValue();
			} else return null;
	
	}
	public void updateValue(String module,String type, String key, String value ) throws DaoException {
			AirtimeConfig airtimeConfig=new AirtimeConfig();
			airtimeConfig.setModule(module);
			airtimeConfig.setType(type);
			airtimeConfig.setParamKey(key);
			airtimeConfig.setParamValue(value);
			update(airtimeConfig);
	}
	
	public void insertOrUpdateValue(String module,String type, String key, String value ) throws DaoException {
		AirtimeConfig airtimeConfig=new AirtimeConfig();
		airtimeConfig.setModule(module);
		airtimeConfig.setType(type);
		airtimeConfig.setParamKey(key);
		if (select(airtimeConfig)){
			airtimeConfig.setParamValue(value);
			update(airtimeConfig);
		}else{
			insert(airtimeConfig);
		}
}
}
