package com.mbv.airtime.report.model;

import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.digester.Digester;

public class ProviderTxnSummaryModel{
	private String providerId;
    private String fromDate;
    private String toDate;
    
    public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	@Override
	public String toString() {
		return "ProviderTxnSummaryModel [fromDate=" + fromDate
				+ ", providerId=" + providerId + ", toDate=" + toDate + "]";
	}
	public static ProviderTxnSummaryModel build(String inputParams) throws Exception{
		Reader reader = new StringReader(inputParams);
		Digester digester = new Digester();
        digester.setValidating( false );
        digester.addObjectCreate( "para", ProviderTxnSummaryModel.class );
        digester.addBeanPropertySetter("para/providerId", "providerId" );
        digester.addBeanPropertySetter("para/date.from", "fromDate" );
        digester.addBeanPropertySetter("para/date.to", "toDate" );
        return (ProviderTxnSummaryModel) digester.parse(reader);
	}
}
