package com.mbv.airtime.report.model;

import java.io.Reader;
import java.io.StringReader;
import org.apache.commons.digester.Digester;

public class ProviderAccountModel{
	
	private String txnId;
    private String providerId;
    private String employee;
    private String fromDate;
    private String toDate;
            
    public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
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
		return "ProviderAccountModel [employee=" + employee + ", fromDate="
				+ fromDate + ", providerId=" + providerId + ", toDate="
				+ toDate + ", txnId=" + txnId + "]";
	}

	public ProviderAccountModel() {
	}
    
	public static ProviderAccountModel build(String inputParams) throws Exception{
		Reader reader = new StringReader(inputParams);
		Digester digester = new Digester();
        digester.setValidating( false );
        digester.addObjectCreate( "para", ProviderAccountModel.class );
        digester.addBeanPropertySetter("para/providerId", "providerId" );
        digester.addBeanPropertySetter("para/txnId", "txnId" );
        digester.addBeanPropertySetter("para/employee", "employee" );
        digester.addBeanPropertySetter("para/date.from", "fromDate" );
        digester.addBeanPropertySetter("para/date.to", "toDate" );
        return (ProviderAccountModel) digester.parse(reader);
	}
	
	
//	public static void main(String[] args) throws Exception {
//		String inputParameters = "<para>"; 
//		inputParameters+="<providerId>VNPAY</providerId>"; 
//		inputParameters+="<txnId>TXN0001</txnId>"; 
//		inputParameters +="<employee>Toantq</employee>";
//		inputParameters +="<date.from>21/12/2011</date.from>";
//		inputParameters +="<date.to>24/12/2011</date.to>";
//		inputParameters += "</para>"; 
//		System.out.println(ProviderAccountModel.build(inputParameters));				
//	}
}
