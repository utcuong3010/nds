package com.mbv.bp.common.settings;



public interface AirtimeProviderSettings {
	String getConnectionType();
	String getDeliveryWfp();
	String getDnProcessorWfp();
	String getDeliveryQueueId();
	String getDnProcessorQueueId();
	int getResponseTimeOut();
}