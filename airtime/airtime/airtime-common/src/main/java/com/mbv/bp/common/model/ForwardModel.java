package com.mbv.bp.common.model;

public class ForwardModel {
	private String channelId;
	private String queueId;
	private String topicId;
	private boolean removable;
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getQueueId() {
		return queueId;
	}
	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public boolean isRemovable() {
		return removable;
	}
	public void setRemovable(boolean removable) {
		this.removable = removable;
	}
	@Override
	public String toString() {
		return "ForwardModel [channelId=" + channelId + ", queueId=" + queueId
				+ ", removable=" + removable + ", topicId=" + topicId + "]";
	}
	
}
