package com.mbv.frontend.util;

import java.util.ArrayList;
import java.util.List;

public class Paging<T> {
	private int nlinks=2;
	private int currentPage;
	private int totalPage;
	private int lastPage;
	private int firstPage;
	private boolean hasNext=true;
	private boolean hasPre=true;
	private int pageSize;
	private int totalItems;
	private List<T> items;
	private List<Integer>pageRange; 
	public Paging(int currentPage,int nlinks,int pageSize,int totalItems) {
		this.nlinks=nlinks;
		this.items=items;
		this.pageSize=pageSize;
		this.totalItems=totalItems;
		this.currentPage=currentPage;
		
		totalPage=totalItems/pageSize;
		if (totalItems % pageSize!=0)
			totalPage++;
		
		if (currentPage>=totalPage){
			currentPage=totalPage;	
			hasNext=false;
			lastPage=totalPage;
		}else{
			hasNext=true;
			lastPage=currentPage+nlinks;
			if (lastPage>=totalPage) {
				lastPage=totalPage;
			}
		}
		if (currentPage<=1) {
			currentPage=1;
			hasPre=false;
			firstPage=1;
		}else{
			hasPre=true;
			firstPage=currentPage-nlinks;
			if (firstPage<=1){ 
				firstPage=1;
			}
		}
		
		pageRange=new ArrayList<Integer>();
		for(int i=firstPage;i<=lastPage;i++)
			pageRange.add(i);
		
	} 
	public void init(){
			
	}
	
	public int getNlinks() {
		return nlinks;
	}
	public void setNlinks(int nlinks) {
		this.nlinks = nlinks;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getLastPage() {
		return lastPage;
	}
	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}
	public int getFirstPage() {
		return firstPage;
	}
	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}
	public boolean isHasNext() {
		return hasNext;
	}
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	public boolean isHasPre() {
		return hasPre;
	}
	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}
	public List<Integer> getPageRange() {
		return pageRange;
	}
	public void setPageRange(List<Integer> pageRange) {
		this.pageRange = pageRange;
	}
	public List<T> getItems() {
		return items;
	}
	public void setItems(List<T> items) {
		this.items = items;
	}
	public int getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "Paging [currentPage=" + currentPage + ", firstPage="
				+ firstPage + ", hasNext=" + hasNext + ", hasPre=" + hasPre
				+ ", items=" + items + ", lastPage=" + lastPage + ", nlinks="
				+ nlinks + ", pageRange=" + pageRange + ", pageSize="
				+ pageSize + ", totalItems=" + totalItems + ", totalPage="
				+ totalPage + "]";
	}
	
}
