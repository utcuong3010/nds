package com.mbv.airtime.report;

import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface IReport {
	public void init(Map<String, String> propertyMap,SqlMapClient sqlMapClient);
	public String createReport();
}
