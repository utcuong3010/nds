package com.mbv.bp.common.model;

public class QueueConfig {

	public static final String SELECT_ALL="QueueConfig_SelectAll";
    
	private String queueId;

    private Integer maxSize;

    private Integer dequeue;

    private String queueTable;

    private Integer maxRetry;
    
    private boolean terminable;

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId == null ? null : queueId.trim();
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getDequeue() {
        return dequeue;
    }

    public void setDequeue(Integer dequeue) {
        this.dequeue = dequeue;
    }

    public String getQueueTable() {
        return queueTable;
    }

    public void setQueueTable(String queueTable) {
        this.queueTable = queueTable == null ? null : queueTable.trim();
    }

    public Integer getMaxRetry() {
        return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
        this.maxRetry = maxRetry;
    }

    
	public boolean isTerminable() {
		return terminable;
	}

	public void setTerminable(boolean terminable) {
		this.terminable = terminable;
	}

	@Override
	public String toString() {
		return "QueueConfig [dequeue=" + dequeue + ", maxRetry=" + maxRetry
				+ ", maxSize=" + maxSize + ", queueId=" + queueId
				+ ", queueTable=" + queueTable + "]";
	}

	
    
}