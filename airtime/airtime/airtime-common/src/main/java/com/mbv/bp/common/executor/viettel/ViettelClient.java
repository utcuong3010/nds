package com.mbv.bp.common.executor.viettel;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.exception.IntegrationException;
import com.mbv.bp.common.exception.RequestException;
import com.mbv.bp.common.exception.TimeOutException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.integration.IExecutor;
import com.mbv.bp.common.model.ViettelRequest;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.util.ViettelUtils;
import com.mbv.bp.common.util.DateUtils.Operation;
import com.mbv.bp.common.util.DateUtils.Type;

public class ViettelClient 
{
  private static final Log log  							= LogFactory.getLog(ViettelClient.class);
  private Socket socket										= null;
  private Map<String, ViettelRequest> penddingResponses		= null;
  private List<ViettelRequest> deliveryRequests				= null;
  private AtomicBoolean reconnectFlag						= new AtomicBoolean(false);
  private AtomicBoolean shutdownFlag						= new AtomicBoolean(false);
  private AtomicBoolean status								= new AtomicBoolean(false);
  private AtomicBoolean runningFlag							= new AtomicBoolean(false);
  private AtomicInteger curReqQueue							= new AtomicInteger(0);
  private BlockingQueue<ViettelRequest> reqQueue			= null;
  private Object lock										= new Object();
  private ViettelRequestClient requestPoller				= null;
  private ViettelResponseClient responsePoller				= null;
  private String name										= null;
  private AtomicLong lastRequestSendTime					= null;
  private AtomicInteger syncId								= new AtomicInteger(0);
  
  public ViettelClient(String name)
  {
	this.name=name;  
    reqQueue=new ArrayBlockingQueue<ViettelRequest>(AppConstants.VIETTEL_SETTINGS.getMaxPendingRequest());
    deliveryRequests=Collections.synchronizedList(new ArrayList<ViettelRequest>());
    penddingResponses=new ConcurrentHashMap<String, ViettelRequest>();
    lastRequestSendTime = new AtomicLong(System.currentTimeMillis());
  }
  
  public void startUpViettelClient(){
	  if (runningFlag.compareAndSet(false, true)){
		  log.info("Starting Viettel Client.| Client name - "+name);
		  try{
			  connect();
		  }catch (Exception e) {
			log.error("Fail to connect to Viettel Server",e);
		  }
		  
		  requestPoller=new ViettelRequestClient(this);
		  requestPoller.setName("REQUEST_POLLER_"+name);
		  requestPoller.setDaemon(true);
		  requestPoller.setPriority(Thread.NORM_PRIORITY);
		  requestPoller.start();
		  
		  responsePoller=new ViettelResponseClient(this);
		  responsePoller.setName("RESPONSE_POLLER_"+name);
		  responsePoller.setDaemon(true);
		  responsePoller.setPriority(Thread.NORM_PRIORITY);
		  responsePoller.start();
		  
	  }else{
		  log.info(name +" already started.");
	  }
  }
  
  public void sendRequest(ViettelRequest request) throws RequestException,TimeOutException{
	  
	  	if (isRequestTimeOut(request)) throw new TimeOutException("Request Timed Out");
	  	
	  	try{

	    	long startTime=System.currentTimeMillis();  
	    	
	    	OutputStream out =socket.getOutputStream();
	    	out.write(request.getRequestMessage().getBytes());
	    	out.flush();
	    	
	    	lastRequestSendTime.set(startTime);
	    	
	    	if (request.getRequestType().equals(AppConstants.TOPUP_REQUEST))
	    		request.setFirstDelivery(startTime);
	    	request.setLastDelivery(startTime);
	    	
/*
 * 	    	No need to put process response for network request if request is network request. 
 * 			overwrite TOPUP_REQUEST to TXN_OPERATION_RETRY_RESPONSE.
 */
	    	if (!request.getRequestType().equals(AppConstants.NETWORK_REQUEST))
	    		penddingResponses.put(request.getTrace(), request);
	    	
	    	log.info(name +" | Sending request to Viettel successful.|request-"+request+" PendingResponse-"+penddingResponses.size()+"| processed time(ms):"+(System.currentTimeMillis()-startTime));
	    	
	    }catch (Exception e) {
	    	throw new RequestException(e.getMessage(),e);
		}
  }
  
  private boolean isRequestTimeOut(ViettelRequest request) {
	  boolean result=false;
	  	Date txnDate=request.getTxnDate();
		Date timeOutDate=DateUtils.dateAdd(txnDate, AppConstants.VIETTEL_SETTINGS.getTxnTimeOut(), Type.BY_MILLISECOND, Operation.PLUS);
		if (timeOutDate.before(new Date())){
			result= true;
		}
	return result;
}

public ViettelRequest getPollViettelRequest(){
	
	if (syncId.get()!=ViettelConnectionProperty.getInstance().getSyncId()){
		setUsable(false);
		log.info("Going to reconnect due to connnection properties changed !");
	}
	
  	int retry=0;
  	while (!status.get()){
  		if (retry==AppConstants.VIETTEL_SETTINGS.getMaxConnectionRetry()){
  			log.info(name +" | Fail to reconnect to Viettel Server..|retry: "+retry);
  			break;
  		}
  		
  		retry++;
  		reconnect();
  		
  		if (status.get()) break;
		
  		log.info(name +" | Waiting for Viettel connection..|retry: "+retry);
		
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			log.warn(name +" | Exception while waiting for Viettel Connection",e);
		}
  	}
	
	ViettelRequest request=null;
	
	try{
		  if (deliveryRequests.size()>0)
			  request=deliveryRequests.remove(0);	
		  
		  if (request==null){
			  
			  if (penddingResponses.size()>= AppConstants.VIETTEL_SETTINGS.getMaxPendingResponse()) 
				return null;  
			  
			  request=reqQueue.poll(AppConstants.VIETTEL_SETTINGS.getRequestPollerInterval(),TimeUnit.MILLISECONDS);
			  if (request!=null){
				curReqQueue.decrementAndGet();
				log.info(name +" | current reqQueue size is " +curReqQueue.get());
			  }
		  }
	}catch (Exception e) {
		log.error(name +" | Exception while poll request",e);
	}
	return request;

}
  
  public ViettelRequest getPendingResponseRequest(String messageId){
	  ViettelRequest request=null;
		try {
			request = penddingResponses.get(messageId).clone();
		} catch (Exception e){
			log.error(name +" | Fail to get request from pending responses",e);
			request=null;
		}
	  return request;
  }
  
  public String getResponse(){
	  if (!reconnectFlag.get()){
 		 try{
 			 if (socket!=null && socket.isConnected()){
 				 InputStream in=socket.getInputStream();
		    	 byte[] lenbuf = new byte[4];
		    	 in.read(lenbuf);
		         int size = Integer.parseInt(new String(lenbuf));
		   		 byte[] buf = new byte[size];
		   		 in.read(buf);
		   		 return new String(lenbuf)+new String(buf);
		   		 
 			 }
	    }catch (IOException e){
	    }catch (Exception e) {
//			log.error(name +" | Exception in getting response from Viettel server", e);
		}
	  }else{
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				log.warn(name +" | Error while waiting for reconnect.",e);
			}
	  }
	  return null;
	}
 
  

public ContextBase process(ContextBase context) {
	
	ContextBase result=new ContextBase();
	result.setErrorCode(ErrorCode.SUCCESS);
	
	if (shutdownFlag.get()){
		result.setErrorCode(ErrorCode.SYSTEM_SHUTINGDOWN);
		return result;
	}
	ViettelRequest request=new ViettelRequest();
	request.setTrace(context.getString(Attributes.ATTR_MESSAGE_ID));
	if (StringUtils.isBlank(request.getTrace())){
		result.setErrorCode(ErrorCode.MISSING_MESSAGE_ID);
		return result;
	}
	request.setMsisdn(context.getString(Attributes.ATTR_MSISDN));
	
	if (StringUtils.isBlank(request.getMsisdn())){
		result.setErrorCode(ErrorCode.MISSING_MSISDN);
		return result;
	}
	request.setAmount(context.getString(Attributes.ATTR_AMOUNT));
	if (StringUtils.isBlank(request.getAmount())){
		result.setErrorCode(ErrorCode.MISSING_AMOUNT);
		return result;
	}
	request.setTxnDate(context.getDate(Attributes.ATTR_TRANSACTION_DATE));
	if (request.getTxnDate()==null){
		result.setErrorCode(ErrorCode.MISSING_TXN_DATE);
		return result;
	}
	
	request.setRequestType(context.getString(Attributes.ATTR_MESSAGE_TYPE));
	
	request.setTxnId(context.getString(Attributes.ATTR_TRANSACTION_ID));
	
	if (StringUtils.isBlank(request.getTxnId())){
		result.setErrorCode(ErrorCode.MISSING_TXN_ID);
		return result;
	}
	
	request.setRequestMessage(context.getString(Attributes.ATTR_REQUEST_MESSAGE));
	if (StringUtils.isBlank(request.getRequestMessage())){
		result.setErrorCode(ErrorCode.MISSING_REQUEST_MESSAGE);
		return result;
	}
	
	
	synchronized (lock) {
		try {
			if (curReqQueue.get()>=AppConstants.VIETTEL_SETTINGS.getMaxPendingRequest()){
				log.error("Delivery request queue is full.| Viettel Server is busy.");
				result.setErrorCode(ErrorCode.SERVER_IS_BUSY);
				return result;
			}else{
				curReqQueue.incrementAndGet();
				reqQueue.put(request);
				log.info("Current reqQueue is "+curReqQueue.get());
			}
		} catch (InterruptedException e) {
			log.error("Exception while put request to viettel queue",e);
			curReqQueue.decrementAndGet();
			result.setErrorCode(ErrorCode.SYS_INTERNAL_ERROR);
		}
	}
	return result;
}





public void processRetryAllResponses(){
		ViettelRequest request;
		synchronized (deliveryRequests) {
			deliveryRequests.clear();
			Iterator<ViettelRequest> iterator=penddingResponses.values().iterator();
			while (iterator.hasNext()){
				try{
					request=iterator.next().clone();
					String inquiryMsg=ViettelUtils.serializeInquiryTxnMessage(request.getMsisdn(), request.getAmount(), request.getTxnDate(), request.getTrace());
					request.setRequestType(AppConstants.TXN_OPERATION_RETRY_RESPONSE);
					request.setRequestMessage(inquiryMsg);
					deliveryRequests.add(request);
					if (log.isDebugEnabled())
						log.debug("current deliveryQueue size is "+ deliveryRequests.size());
				}catch (Exception e) {
					request=null;
					log.error("Error while process retry all Response.",e);
				}
			}
		}
}


public boolean isUsable() {
	return status.get();
}


public void setUsable(boolean bstatus) {
	this.status.set(bstatus);
	
}

//public void processRetryResponse(String messageId) {
//	ViettelRequest request= getPendingResponseRequest(messageId);
//	processRetryResponse(request);
//}

public void processRetryResponse(ViettelRequest request){
	if (request!=null){
		try{
			synchronized (deliveryRequests){
				if (deliveryRequests.contains(request)){
//					log.info("request already in deliveryRequests| request:"+request);
				}else{
					String inquiryMsg=ViettelUtils.serializeInquiryTxnMessage(request.getMsisdn(), request.getAmount(), request.getTxnDate(), request.getTrace());
					request.setRequestType(AppConstants.TXN_OPERATION_RETRY_RESPONSE);
					request.setRequestMessage(inquiryMsg);
					deliveryRequests.add(request);
					if (log.isDebugEnabled())
						log.debug("current deliveryQueue size is "+ deliveryRequests.size());
				}
			}
		}catch (Exception e) {
			log.error("Fail to process retry response.",e);
		}	
	}
}

public int getCurPendingResponseSize() {
	return penddingResponses.size();
}

public int getCurReqQueue() {
	return curReqQueue.get();
}



private Socket openSocket(String server, int port) throws Exception
{
  Socket socket;
  try
  {
    InetAddress inteAddress = InetAddress.getByName(server);
    SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);
    socket = new Socket();
    socket.connect(socketAddress, AppConstants.VIETTEL_SETTINGS.getResponseTimeOut());
    socket.setSoTimeout(AppConstants.VIETTEL_SETTINGS.getResponseTimeOut());
    return socket;
  } catch (Exception e){
    throw e;
  }
}


public void checkPendingResponseState() {
	long curTimeMillis=System.currentTimeMillis();
	long txnTimeOutMillis=AppConstants.VIETTEL_SETTINGS.getTxnTimeOut();
	long retryResInterval=AppConstants.VIETTEL_SETTINGS.getRetryResponseInterval();
	long firstDelivery=0;
	long lastDelivery=0;
		
		for(Iterator<Entry<String,ViettelRequest>> iterator = penddingResponses.entrySet().iterator(); iterator.hasNext();) {  
			
		   ViettelRequest request=null;	
		   
		   try{	
			   request=iterator.next().getValue().clone();
		   }catch (Exception e) {
			   log.error("Fail to get Object from penddingResponses",e);
			   request=null;
		   }
		   
		   if (request==null) continue;
		   
		   if (request.getFirstDelivery()!=null) firstDelivery=request.getFirstDelivery();
		   if (request.getLastDelivery()!=null) lastDelivery=request.getLastDelivery();

		   if (curTimeMillis-firstDelivery>txnTimeOutMillis){
			   ContextBase context=new ContextBase();
			   context.putString(Attributes.ATTR_MSISDN,request.getMsisdn());
			   context.putString(Attributes.ATTR_AMOUNT,request.getAmount());
			   context.putDate(Attributes.ATTR_TRANSACTION_DATE,request.getTxnDate());
			   context.putString(Attributes.ATTR_MESSAGE_TYPE,request.getRequestType());
			   context.putString(Attributes.ATTR_TRANSACTION_ID,request.getTxnId());
			   context.putString(Attributes.ATTR_AMOUNT,request.getAmount());
			   context.putString(Attributes.ATTR_MESSAGE_ID, request.getTrace());
			   
			   if (StringUtils.isNotBlank(request.getResponseCode())) 
				   	context.putString(Attributes.ATTR_RESPONSE_CODE, request.getResponseCode());
			   else	   
			   		context.putString(Attributes.ATTR_RESPONSE_CODE, ErrorCode.RESPONSE_TIME_OUT);
			   
			   Date deliveryDate=null;
			   if (request.getFirstDelivery()!=null){
					try{
						deliveryDate=new Date(request.getFirstDelivery());
					}catch (Exception e) {
						log.error("Exception while process delivery date",e);
					}
			   }
			   
			   context.putDate(Attributes.ATTR_DELIVERY_DATE, deliveryDate);
			   context.putDate(Attributes.ATTR_RESPONSE_DATE, new Date());
			   context.putString(Attributes.ATTR_CONN_TYPE, AppConstants.VIETTEL_SETTINGS.getConnectionType());
			   context.putString(Attributes.ATTR_OPERATION_TYPE, AppConstants.VIETTEL_SETTINGS.getResponseOperation());
			   context.put(Attributes.ATTR_DEST_WFP, AppConstants.VIETTEL_SETTINGS.getDnProcessorWfp());
			   context.put(Attributes.ATTR_QUEUE_ID, AppConstants.VIETTEL_SETTINGS.getDnProcessorQueueId());
			   
			   try{
					IExecutor<ContextBase> executor;
					executor = ExecutorFactory.getInstance().getExecutor(ExecutorFactory.QUEUE_EXECUTOR);
					ContextBase result=executor.process(context);
					if (log.isDebugEnabled())
						log.debug("Push Viettel delivery request result to queue completed. | context: "+result);
			   }catch (IntegrationException e) {
				   log.error("Fail to process Viettel request result| context: "+context,e);
				}
			   
			   iterator.remove();
		   }else  if(curTimeMillis-lastDelivery>retryResInterval){
/*		
 * remove txn query	   
			if (!reconnectFlag.get()){
				   processRetryResponse(request);
			}
*/			   
		   }
		}  
		
	
	
}

public void removePendingResponse(String messageId) {
	if (!StringUtils.isBlank(messageId))
		penddingResponses.remove(messageId);
	
}

public void updateResponseInprogress(String messageId, String responseCode) {
	try{
		if (messageId==null) return; 
		messageId=messageId.trim();
		ViettelRequest request=penddingResponses.get(messageId);
		if (request!=null){
			request.setResponseCode(responseCode);
			request.setResponseDate(new Date());
			penddingResponses.put(messageId, request);
		}
		
	}catch (Exception e) {
		log.error("Fail to update inprogress status",e);
	}
	
}

public void keepAliveConnection(){
	try{
		
		if (!status.get()) return;
		
		long currentTime=System.currentTimeMillis();
		Date currentDate=new Date();
		long time=currentTime-lastRequestSendTime.get();
		if (time>AppConstants.VIETTEL_SETTINGS.getSendNetworkCheckTimeInterval()){
			log.info("Going to check viettel network...");
			ViettelRequest request=new ViettelRequest();
			request.setRequestType(AppConstants.NETWORK_REQUEST);
			request.setTxnDate(currentDate);
			request.setRequestMessage(ViettelUtils.serializeNetworkMessage(currentDate));
			try{
				sendRequest(request);
			}catch (Exception e) {
				setUsable(false);
				log.error("Fail to send network check message");
			}
			lastRequestSendTime.set(currentTime);
		}
	}catch (Exception e) {
		log.error("Fail to process viettel network check message",e);
	}
}
public void reconnect(){
	log.info("recover lost connection...");
		if (reconnectFlag.compareAndSet(false, true)){
			try{
				disconnect();
				connect();
				status.set(true);
/*				remove txn query 
 *				processRetryAllResponses();
 */
			}catch (Exception ex) {
				log.error("Fail to Reconnect...");
				status.set(false);
			}
			reconnectFlag.set(false);
		}
		
  }

public void connect() throws Exception{
  try{
	  lastRequestSendTime =new AtomicLong(System.currentTimeMillis());
      socket = openSocket(ViettelConnectionProperty.getInstance().getIp(), ViettelConnectionProperty.getInstance().getPort());
      syncId.set(ViettelConnectionProperty.getInstance().getSyncId());
      status.set(true);
      log.info("connected to Viettel Server.");
    }catch (Exception e){
    	 status.set(false);
    	throw e;
    }
}

public void disconnect() {
  try{
	  if (socket!=null){
		  socket.close();
		  socket=null;
	  }
    }catch (Exception e){
    	log.error("Fail to disconnect ",e);
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
public void shutdown() {
	boolean result=true;
	if (shutdownFlag.compareAndSet(false, true)){
		log.info("Going to perform shutdown Viettel Client...| clientName-"+name);
			requestPoller.shutdown();
			responsePoller.shutdown();
			try {
				requestPoller.join();
				log.info( "Shutdown request poller completed !" );
			} catch (InterruptedException e) {
				log.error( "Exception occur when waiting for request poller to shutdown. | ErrMsg: " + e.getMessage(), e );
				result=false;
			}		
			
			try {
				responsePoller.join();
				log.info( "Shutdown response poller completed !" );
			} catch (InterruptedException e) {
				log.error( "Exception occur when waiting for response poller to shutdown. | ErrMsg: " + e.getMessage(), e );
				result=false;
			}	
			
			disconnect();
			
			if (result) 
				log.info("Viettel client shutted down!");
			else
				log.error("Error while Viettel client shutting down!");
	}else{
		log.info("Viettel client already in shutdown state!");
	}
	
}

	
}