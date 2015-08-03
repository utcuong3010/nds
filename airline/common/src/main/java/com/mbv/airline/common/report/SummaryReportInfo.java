package com.mbv.airline.common.report;

public class SummaryReportInfo {
	
	protected int totalTrans;
	
	protected long totalMonney;
	
	protected int succeedBooks;
	
	protected int failedBooks;
	
	protected int succeedPays;
	
	protected int failedPays;
	
	protected int totalBooks;
	
	protected int totalPays;

	public int getTotalTrans() {
		return totalTrans;
	}

	public void setTotalTrans(int totalTrans) {
		this.totalTrans = totalTrans;
	}

	public long getTotalMonney() {
		return totalMonney;
	}

	public void setTotalMonney(long totalMonney) {
		this.totalMonney = totalMonney;
	}

	public int getSucceedBooks() {
		return succeedBooks;
	}

	public void setSucceedBooks(int succeedBooks) {
		this.succeedBooks = succeedBooks;
	}

	public int getFailedBooks() {
		return failedBooks;
	}

	public void setFailedBooks(int failedBooks) {
		this.failedBooks = failedBooks;
	}

	public int getSucceedPays() {
		return succeedPays;
	}

	public void setSucceedPays(int succeedPays) {
		this.succeedPays = succeedPays;
	}

	public int getFailedPays() {
		return failedPays;
	}

	public void setFailedPays(int failedPays) {
		this.failedPays = failedPays;
	}

	public int getTotalBooks() {
		return totalBooks;
	}

	public void setTotalBooks(int totalBooks) {
		this.totalBooks = totalBooks;
	}

	public int getTotalPays() {
		return totalPays;
	}

	public void setTotalPays(int totalPays) {
		this.totalPays = totalPays;
	}

	@Override
	public String toString() {
		return "SummaryReportInfo [totalTrans=" + totalTrans + ", totalMonney="
				+ totalMonney + ", succeedBooks=" + succeedBooks
				+ ", failedBooks=" + failedBooks + ", succeedPays="
				+ succeedPays + ", failedPays=" + failedPays + ", totalBooks="
				+ totalBooks + ", totalPays=" + totalPays + "]";
	}

}
