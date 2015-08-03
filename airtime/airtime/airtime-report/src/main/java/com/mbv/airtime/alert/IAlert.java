package com.mbv.airtime.alert;

import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

public interface IAlert {
	public String execute(Map<String, String> propertyMap);
}
