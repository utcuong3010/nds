package com.mbv.bp.common.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.JiBXException;

public class AirtimeResultFwd {
	private List<PropertyModel> result;
	static final IBindingFactory bindingFactory;
	static{
        try{
            bindingFactory = BindingDirectory.getFactory(AirtimeResultFwd.class);
        }catch(JiBXException ex){
            throw new RuntimeException((new StringBuilder("Unexpected exception ")).append(ex.getMessage()).toString() == null ? ex.toString() : ex.getMessage(), ex);
        }
	}
	public List<PropertyModel> getResult() {
		return result;
	}
	
	public void setResult(List<PropertyModel> result) {
		this.result = result;
	}
	public boolean add(PropertyModel property){
		if (result==null) result=new ArrayList<PropertyModel>();
		return result.add(property);
	}
	public boolean remote(PropertyModel property){
		if (result==null) result=new ArrayList<PropertyModel>();
		return result.remove(property);
	}
	public static String marshal(AirtimeResultFwd resultFwd)  throws IOException
	{
	    try{
	        IMarshallingContext mctx = bindingFactory.createMarshallingContext();
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        mctx.marshalDocument(resultFwd, "utf-8", null, os);
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
		return "AirtimeResultFwd [result=" + result + "]";
	}
	
}
