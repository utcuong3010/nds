package com.mbv.bp.common.dao.airtime;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.dao.BaseDao;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.vo.airtime.QueueRequest;

public class QueueRequestDao extends BaseDao<QueueRequest>{

	public QueueRequestDao() {
		sqlMapClient = SqlConfig.getAtSqlMapInstance();
	}

	public QueueRequestDao(SqlMapClient sqlMapClient) {
		super(sqlMapClient);
		
	}
	
	public boolean insert(QueueRequest obj) throws DaoException{
		return  insert(obj, QueueRequest.INSERT);
	}
	
	public boolean update(QueueRequest obj) throws DaoException{
		return  update(obj, QueueRequest.UPDATE);
	}
	
	public boolean delete(QueueRequest obj) throws DaoException{
		return  delete(obj, QueueRequest.DELETE);
	}
	public List<QueueRequest> selectRequestByStatus(String status) throws DaoException{
		QueueRequest obj=new QueueRequest();
		obj.setStatus(status);
		return  selectList(obj, QueueRequest.SELECT_BY_STATUS);
	}

	public List<QueueRequest> selectRequestByQueueIdStatus(String queueId,
			String status) throws DaoException {
		QueueRequest obj=new QueueRequest();
		obj.setStatus(status);
		obj.setQueueId(queueId);
		return selectList(obj, QueueRequest.SELECT_BY_QUEUE_ID_STATUS);
	}
}
