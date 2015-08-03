package com.mbv.airtime.ws;

import java.util.List;

public abstract class BaseList<T> {
	protected List<T> items;
	protected long total;
	
	protected BaseList(){}
	
	protected BaseList(List<T> items, long total) {
		this.items = items;
		this.total = total;
	}
	
	public List<T> getItems() {
		return items;
	}
	
	public long getTotal() {
		return total;
	}
	
	public void setItems(List<T> items) {
		this.items = items;
	}
	
	public void setTotal(long total) {
		this.total = total;
	}
	
}
