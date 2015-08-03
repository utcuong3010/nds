package com.mbv.bp.common.model;

import java.util.List;
public class GwErrConverter {
	private String service;
	private String method;
	private String defaultError;
	private List<String> bypassList;
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getDefaultError() {
		return defaultError;
	}
	public void setDefaultError(String defaultError) {
		this.defaultError = defaultError;
	}
	public List<String> getBypassList() {
		return bypassList;
	}
	public void setBypassList(List<String> bypassList) {
		this.bypassList = bypassList;
	}
	@Override
	public String toString() {
		return "GwErrConvertor [bypassList=" + bypassList + ", defaultError="
				+ defaultError + ", method=" + method + ", service=" + service
				+ "]";
	}
	
}
