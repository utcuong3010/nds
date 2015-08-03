package com.mbv.bp.common.vo.airtime;

import java.util.Date;

import com.mbv.bp.common.integration.ContextBase;

public class QueueRequest{
	public static final String INSERT="QueueRequest_Insert";
	public static final String UPDATE="QueueRequest_Update";
	public static final String DELETE="QueueRequest_Delete";
	public static final String SELECT_BY_STATUS="QueueRequest_SelectByStatus";
	public static final String SELECT_BY_QUEUE_ID_STATUS = "QueueRequest_SelectByQueueIdStatus";

	private String content;

    private String status;

    private String errorCode;

    private Date createdDate;

    private Date updatedDate;

    private String queueId;

    private Long requestId;
    
    private String connId;

    private ContextBase context;
    
    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId == null ? null : queueId.trim();
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode == null ? null : errorCode.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

	public ContextBase getContext() {
		return context;
	}

	public void setContext(ContextBase context) {
		this.context = context;
	}

	public String getConnId() {
		return connId;
	}

	public void setConnId(String connId) {
		this.connId = connId;
	}

}