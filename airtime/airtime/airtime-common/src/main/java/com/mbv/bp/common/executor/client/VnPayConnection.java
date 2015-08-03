package com.mbv.bp.common.executor.client;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.exception.RequestException;
import com.mbv.bp.common.exception.ResponseException;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutable;
import com.mbv.bp.common.model.MessageInfo;
import com.mbv.bp.common.util.VnPayUtils;

public class VnPayConnection implements IExecutable
{
  private static Log logger = LogFactory.getLog(VnPayConnection.class);
  private String ip="127.0.0.1";	
  private int port=10002;
  private int timeoutInMs=60000;
  private boolean status;
  private Socket socket=null;
  public VnPayConnection(String ip,int port)
  {
    this.ip=ip;
    this.port=port;
  }
  
  public void connect() throws Exception{
	  try
	    {
	      socket = openSocket(ip, port);
	      String message=VnPayUtils.buildNetworkMsg(AppConstants.VNPAY_SETTINGS.getNetworkLoginCode());
	      status=true;
	      message=delivery(message);
	      MessageInfo messageInfo=VnPayUtils.deSerialize(message);
	      if (!(messageInfo.getFieldMaps().get(39).getValue().equals("0") ||
	    	   messageInfo.getFieldMaps().get(39).getValue().equals("00"))){
	    	  logger.info("message-"+message +"| messageInfo-"+messageInfo);
	    	  socket.close();
	    	  socket=null;
	    	  throw new Exception("unable to login to Vnpay Server");
	      }
	    }
	    catch (Exception e)
	    {
	    	status=false;
	    	throw e;
	    }
  }
  public void disconnect() {
	  try
	    {
		  if (socket!=null){
			  /*
			   * no need send logout message 
			   * 
				  String message=VnPayMessageUtil.buildNetworkMsg(AirTimeConstants.VNPAY_SETTINGS.getNetworkLogOutCode());
				  status=true;
			      message=delivery(message);
			      MessageInfo messageInfo=VnPayMessageUtil.deSerialize(message);
			      logger.info("message-"+message +"| messageInfo-"+messageInfo);
		      */
			  socket.close();
			  socket=null;
		  }
	    }
	    catch (Exception e)
	    {
	    	logger.error("Fail to disconnect ",e);
	    }finally{
	    	try{
	    		if (socket!=null){
	    			socket.close();
	    			socket=null;
	    		}
	    	}catch (Exception e) {
	    		socket=null;
			}
	    }
	  
  }
  
  private String delivery(String message) throws ResponseException, RequestException 
  {
	if (!status) throw new RequestException("Fail to connect to VnPay Server");
    try 
    {
    	OutputStream out =socket.getOutputStream();
    	out.write(message.getBytes());
    	out.flush();
    }catch (Exception e) {
    	this.status=false;
    	throw new RequestException(e.getMessage(),e);
	}
    
    try{
    	 InputStream in=socket.getInputStream();
    	 byte[] lenbuf = new byte[4];
    	 in.read(lenbuf);
         int size = Integer.parseInt(new String(lenbuf));
   		 byte[] buf = new byte[size];
   		 in.read(buf);
   		 return new String(lenbuf)+new String(buf);
    } 
    catch (Exception e) 
    {
    	this.status=false;
    	throw new ResponseException(e.getMessage(),e);
    }
  }
  /*
  private String delivery(String message) throws ResponseException, RequestException 
  {
	if (!status) throw new RequestException("Fail to connect to VnPay Server");
    try 
    {
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      bufferedWriter.write(message);
      bufferedWriter.flush();
    }catch (Exception e) {
    	this.status=false;
    	throw new RequestException(e.getMessage(),e);
	}
    
    try{
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      StringBuilder sb = new StringBuilder();
      String str;
      int nRead=0;
      char[] cbuf=new char[9999];
      char[] msgLength=new char[4];
      nRead+=bufferedReader.read(cbuf,nRead,4);
      System.arraycopy(cbuf, 0, msgLength, 0, 4);
      int nToRead=Integer.valueOf(new String(msgLength));
      char[] msgChar=new char[nToRead+nRead];
      while (nRead<nToRead) 
      {
    	  nRead+=bufferedReader.read(cbuf,nRead,nToRead-nRead);
      }
      System.arraycopy(cbuf, 0, msgChar, 0, nToRead);
      return new String(msgChar);
    } 
    catch (Exception e) 
    {
    	this.status=false;
    	throw new ResponseException(e.getMessage(),e);
    }
  }
 */
  private Socket openSocket(String server, int port) throws Exception
  {
    Socket socket;
    try
    {
      InetAddress inteAddress = InetAddress.getByName(server);
      SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);
      socket = new Socket();
      socket.connect(socketAddress, timeoutInMs);
      socket.setSoTimeout(timeoutInMs);
      return socket;
    } catch (Exception e){
      throw e;
    }
  }

@Override
public ContextBase process(ContextBase context) {
//Never throw exception	
	String message=context.getString(Attributes.ATTR_REQUEST_MESSAGE);
	
		try {
			message =delivery(message);
			context.put(Attributes.ATTR_RESPONSE_MESSAGE, message);
		} catch (ResponseException e) {
			this.status=false;
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.DELIVERY_RESPONSE_ERROR);
			logger.error("Fail to send request| errCode-"+context.getErrorCode()+"|context-"+context,e);
		} catch (RequestException e) {
			this.status=false;
			context.put(Attributes.ATTR_ERROR_CODE, ErrorCode.DELIVERY_REQUEST_ERROR);
			logger.error("Fail to send request| errCode-"+context.getErrorCode()+"|context-"+context,e);
		}
	
	
	return context;
}

@Override
public void destroy() {
	disconnect();
	
}

@Override
public void reset() throws Exception  {
	logger.info("recover lost connection...");
		try{
			disconnect();
			connect();
			status=true;
		}catch (Exception ex) {
			logger.error("Fail to Reconnect...");
			status=false;
		}
}

@Override
public boolean isUsable() {
	return status;
}

@Override
public void setUsable(boolean status) {
	this.status=status;
	
}

}