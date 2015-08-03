package com.mbv.airline.common.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * 
 * @author phamtuyen
 *
 */

public class AirReportForm {
	
	private SummaryReportInfo summaryReportInfo;

	private List<BookingReportInfo> bookingReportInfos = new ArrayList<BookingReportInfo>();
	
	private List<SummaryReportVendorInfo> reportVendorInfos = new ArrayList<SummaryReportVendorInfo>();
	
	public SummaryReportInfo getSummaryReportInfo() {
		return summaryReportInfo;
	}

	public void setSummaryReportInfo(SummaryReportInfo summaryReportInfo) {
		this.summaryReportInfo = summaryReportInfo;
	}

	public List<BookingReportInfo> getBookingReportInfos() {
		return bookingReportInfos;
	}

	public void setBookingReportInfos(List<BookingReportInfo> bookingReportInfos) {
		this.bookingReportInfos = bookingReportInfos;
	}

	public List<SummaryReportVendorInfo> getReportVendorInfos() {
		return reportVendorInfos;
	}

	public void setReportVendorInfos(List<SummaryReportVendorInfo> reportVendorInfos) {
		this.reportVendorInfos = reportVendorInfos;
	}

	public static String toHashString(Date dateStart,Date dateEnd) throws Exception{ 
		try {					
			StringBuilder tmp = new StringBuilder();								
			tmp.append(dateStart).append(",").append(dateEnd);
			HashFunction hashFunc = Hashing.md5();
			return hashFunc.newHasher().putBytes(tmp.toString().getBytes()).hash().toString();
		} catch (Exception ex) {
			throw ex;
		}	
	}
}
