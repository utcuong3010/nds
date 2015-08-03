package com.mbv.frontend.model;

import java.util.List;

public class PageView<T> {
	int pageNumber;
	int pageSize;
	int totalItems;
	
	List<T> items;
	
	public PageView(List<T> items) {
		this.items = items;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public List<T> getItems() {
		return items;
	}
	@Override
	public String toString() {
		return "PageView [items=" + items + ", pageNumber=" + pageNumber
				+ ", pageSize=" + pageSize + ", totalItems=" + totalItems + "]";
	}
}
	