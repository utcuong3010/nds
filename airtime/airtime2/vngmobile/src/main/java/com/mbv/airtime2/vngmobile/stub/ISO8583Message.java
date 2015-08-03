package com.mbv.airtime2.vngmobile.stub;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ISO8583Message extends HashMap<Integer,String>{
	private static final long serialVersionUID = 1L;
	public static final String TYPE_AUTHORIZATION_REQUEST = "0100";
	public static final String TYPE_FINANCIAL_REQUEST = "0200";

	public ISO8583Message(){
		super();
	}
	

	// Helper method
	public void add(String id, String value){
		int _id = Integer.parseInt(id);
		this.put(_id, value);
	}
	
	public void setType(String value) {
		this.put(0, value);
	}
	
	public void setProcessingCode(String value){
		this.put(3, value);
	}
	
	private static SimpleDateFormat dateformatMMddHHmmss = new SimpleDateFormat("MMddHHmmss");
	public void setTransmissionDate(Date date){
		this.put(7, dateformatMMddHHmmss.format(date));
	}
	
	public String getErrorCode(){
		return this.get(39);
	}
	
	public void setServiceRestrictionCode(String value){
		this.put(40, value);
	}
	
	public void setRefEncryptedMpin(String value){
		this.put(41,value); 
	}
	
	public void setRefTransactionId(String value){
		this.put(42,value); 
	}
	
	public String getTokenKey(){
		return this.get(43);
	}
	
	public String getRefInfo(){
		return this.get(61);
	}
	
	public void setRefInfo(String value){
		this.put(61, value);
	}
	
	private static SimpleDateFormat dateformatyyMMdd = new SimpleDateFormat("yyMMdd");
	public void setDate(Date date){
		this.put(73, dateformatyyMMdd.format(date));
	}
	
	public void setServiceIndicator(String value){
		this.put(94, value);
	}
	
	private static String RESULT_SUCCESS = "00";
	public boolean isSuccess(){
		return RESULT_SUCCESS.equals(this.getErrorCode());
	}	
}
