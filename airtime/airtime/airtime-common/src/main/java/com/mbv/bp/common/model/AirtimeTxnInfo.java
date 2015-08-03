package com.mbv.bp.common.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

public class AirtimeTxnInfo {
	private String atTxnId;
	private String providerId;
	private String txnStatus;
	private String txnErrorCode;
	static final IBindingFactory bindingFactory;
	static{
        try{
            bindingFactory = BindingDirectory.getFactory(AirtimeTxnInfo.class);
        }catch(JiBXException ex){
            throw new RuntimeException((new StringBuilder("Unexpected exception ")).append(ex.getMessage()).toString() == null ? ex.toString() : ex.getMessage(), ex);
        }
	}
	
	public String getAtTxnId() {
		return atTxnId;
	}
	public void setAtTxnId(String atTxnId) {
		this.atTxnId = atTxnId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getTxnStatus() {
		return txnStatus;
	}
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	public String getTxnErrorCode() {
		return txnErrorCode;
	}
	public void setTxnErrorCode(String txnErrorCode) {
		this.txnErrorCode = txnErrorCode;
	}
	
	public static String marshal(AirtimeTxnInfo airtimeTxnInfo)  throws IOException
	{
	    try{
	        IMarshallingContext mctx = bindingFactory.createMarshallingContext();
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        mctx.marshalDocument(airtimeTxnInfo, "utf-8", null, os);
	        return os.toString("utf-8");
	    }catch(Throwable ex){
	        if(ex instanceof IOException)
	            throw (IOException)ex;
	        else
	            throw new IOException((new StringBuilder("Unexpected exception ")).append(ex.getMessage()).toString() == null ? ex.toString() : ex.getMessage(), ex);
	    }
	}
	@Override
	public String toString() {
		return "AirtimeTxnInfo [atTxnId=" + atTxnId + ", providerId="
				+ providerId + ", txnErrorCode=" + txnErrorCode
				+ ", txnStatus=" + txnStatus + "]";
	}
	public static void main(String[] args) {
		System.out.println("hello");
	}
}
