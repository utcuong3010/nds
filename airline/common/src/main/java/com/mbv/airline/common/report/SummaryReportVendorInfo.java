package com.mbv.airline.common.report;

/**
 * 
 * @author phamtuyen
 *
 */
public class SummaryReportVendorInfo extends SummaryReportInfo{
	
	private String vendor;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "SummaryReportVendorInfo [vendor=" + vendor + "]";
	}
	
	public SummaryReportVendorInfo(String vendor){
		this.vendor = vendor;
	}
}