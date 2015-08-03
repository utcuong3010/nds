package com.mbv.bp.queue.core;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.QueueConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.QueueRequestDao;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.util.JacksonUtils;
import com.mbv.bp.common.vo.airtime.QueueRequest;

public class RequestDb {
	private static final Log log = LogFactory.getLog(RequestDb.class);

	public boolean delete(QueueRequest request) {
		try{
			QueueRequestDao requestDao=new QueueRequestDao();
			requestDao.delete(request);
		}catch (Exception e) {
			log.error("Fail to update Request to Database.",e );
		}
		return true;
	}

	public QueueRequest insert(ContextBase context){
		QueueRequest request=new QueueRequest();
		try{
			request.setQueueId( context.get( Attributes.ATTR_QUEUE_ID ) );
			request.setRequestId( context.getLong( Attributes.ATTR_QUEUE_REQUEST_ID ));
			request.setStatus(QueueConstants.QUEUE_STATUS_NEW );
			request.setContext(context);
			request.setContent( JacksonUtils.getJsonStringFromMap(request.getContext()));
			QueueRequestDao requestDao=new QueueRequestDao();
			requestDao.insert(request);
		}catch (Exception e) {
			log.error("Fail to indert request. |errMsg-" + e.getMessage(),e);
			request=null;
		}
		return request;
	}

	public List<QueueRequest> getPendingRequests(String queueId,String status) {
		List<QueueRequest> list =new ArrayList<QueueRequest>();
		try{
			QueueRequestDao requestDao=new QueueRequestDao();
			list= requestDao.selectRequestByQueueIdStatus(queueId,status);
		}catch (DaoException e) {
			log.error("Fail to load all pending request from database. | queueId-"+queueId+"| status-"+status,e);
		}
		return list;
	}
}
