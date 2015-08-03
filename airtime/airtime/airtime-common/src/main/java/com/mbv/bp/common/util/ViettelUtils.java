package com.mbv.bp.common.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.exception.RequestException;
import com.mbv.bp.common.exception.ResponseException;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.Field;
import com.mbv.bp.common.util.DateUtils.Operation;
import com.mbv.bp.common.util.DateUtils.Type;

public class ViettelUtils{
	private static Log log = LogFactory.getLog(ViettelUtils.class);
	private static Map<String, String> hex2BinMap;
	private static Map<String, String> bin2HexMap;
	private static Map<Integer, Field> responseFields;
	static{
		hex2BinMap=new HashMap<String, String>();
		hex2BinMap.put("0", "0000");
		hex2BinMap.put("1", "0001");
		hex2BinMap.put("2", "0010");
		hex2BinMap.put("3", "0011");
		hex2BinMap.put("4", "0100");
		hex2BinMap.put("5", "0101");
		hex2BinMap.put("6", "0110");
		hex2BinMap.put("7", "0111");
		hex2BinMap.put("8", "1000");
		hex2BinMap.put("9", "1001");
		hex2BinMap.put("A", "1010");
		hex2BinMap.put("B", "1011");
		hex2BinMap.put("C", "1100");
		hex2BinMap.put("D", "1101");
		hex2BinMap.put("E", "1110");
		hex2BinMap.put("F", "1111");
		bin2HexMap=new HashMap<String, String>();
		bin2HexMap.put("0000","0");
		bin2HexMap.put("0001","1");
		bin2HexMap.put("0010","2");
		bin2HexMap.put("0011","3");
		bin2HexMap.put("0100","4");
		bin2HexMap.put("0101","5");
		bin2HexMap.put("0110","6");
		bin2HexMap.put("0111","7");
		bin2HexMap.put("1000","8");
		bin2HexMap.put("1001","9");
		bin2HexMap.put("1010","A");
		bin2HexMap.put("1011","B");
		bin2HexMap.put("1100","C");
		bin2HexMap.put("1101","D");
		bin2HexMap.put("1110","E");
		bin2HexMap.put("1111","F");
		responseFields=new HashMap<Integer, Field>();
		responseFields.put(2, new Field(2, "MSISDN", 0, 2, null, true));
		responseFields.put(3, new Field(3, "Processing Code", 6, 0, null, true));
		responseFields.put(4, new Field(4, "Transaction Amount", 12, 0, null, true));
		responseFields.put(5, new Field(5, "Card Amount", 12, 0, null, true));
		responseFields.put(7, new Field(7, "Transmission DateTime", 14, 0, null, true));
		responseFields.put(11, new Field(11, "System Trace Audit Number", 15, 0, null, true));
		responseFields.put(14, new Field(14, "Sub Balance", 12, 0, null, false));
		responseFields.put(23, new Field(23, "Customer Code", 0, 2, null, true));
		responseFields.put(28, new Field(28, "Expire Date", 8, 0, null, false));
		responseFields.put(34, new Field(34, "Card Number", 0, 3, null, true));
		responseFields.put(39, new Field(39, "Response Code", 2, 0, null, true));
		responseFields.put(48, new Field(48, "Additional Data", 0, 3, null, false));
		responseFields.put(54, new Field(54, "Transaction description", 0, 2, null, false));
		responseFields.put(63, new Field(63, "Client ID", 0, 2, null, true));
		responseFields.put(64, new Field(64, "Message Signature", 0, 3, null, true));
	}
	public static String serializeTopupMessage(String msisdn, String amount, Date txnDate, String trace) throws Exception{
		String vtDateFormat="yyyyMMddHHmmss";
		Map<Integer, Field> fieldMap=new HashMap<Integer, Field>();
		fieldMap.put(2, new Field(2,"MSISDN",0,2,addMsisdnPrefix(msisdn),true)); 
		fieldMap.put(3, new Field(3,"Processing Code",6,0,"000000",true)); 
		fieldMap.put(4, new Field(4,"Transaction Amount",12,0,amount,true)); 
		fieldMap.put(7, new Field(7,"Transmission DateTime",14,0,DateUtils.convertDatetoString(txnDate,vtDateFormat),true)); 
		fieldMap.put(11, new Field(11,"System Trace Audit Number",15,0,trace,true)); 
		fieldMap.put(63, new Field(63,"Client ID",0,2,AppConstants.VIETTEL_SETTINGS.getClientId(),true)); 
		String signature=serializeSignature(fieldMap);
		fieldMap.put(64, new Field(64,"Message Signature",0,3,signature,false)); 
		return serializeMessage("0200",fieldMap);
	}
//	for testing purpose only
	public static String serializeResponse(String messageType,Map<Integer, Field> fieldMap) throws Exception{
		return serializeMessage(messageType,fieldMap);
	}
	
	public static String serializeInquiryTxnMessage(String msisdn, String amount, Date txnDate, String trace) throws Exception{
		String vtDateFormat="yyyyMMddHHmmss";
		Map<Integer, Field> fieldMap=new HashMap<Integer, Field>();
		fieldMap.put(2, new Field(2,"MSISDN",0,2,addMsisdnPrefix(msisdn),true)); 
		fieldMap.put(3, new Field(3,"Processing Code",6,0,"000000",true)); 
		fieldMap.put(4, new Field(4,"Transaction Amount",12,0,amount,true)); 
		fieldMap.put(7, new Field(7,"Transmission DateTime",14,0,DateUtils.convertDatetoString(txnDate,vtDateFormat),true)); 
		fieldMap.put(11, new Field(11,"System Trace Audit Number",15,0,trace,true)); 
		fieldMap.put(48, new Field(48,"Additional Data",0,3,"query",false)); 
		fieldMap.put(63, new Field(63,"Client ID",0,2,AppConstants.VIETTEL_SETTINGS.getClientId(),true)); 
		String signature=serializeSignature(fieldMap);
		fieldMap.put(64, new Field(64,"Message Signature",0,3,signature,false)); 
		return serializeMessage("0201",fieldMap);
	}
	
	public static String serializeNetworkMessage(Date txnDate) throws Exception{
		String vtDateFormat="yyyyMMddHHmmss";
		Map<Integer, Field> fieldMap=new HashMap<Integer, Field>();
		fieldMap.put(3, new Field(3,"Processing Code",6,0,"000000",true)); 
		fieldMap.put(7, new Field(7,"Transmission DateTime",14,0,DateUtils.convertDatetoString(txnDate,vtDateFormat),true)); 
		fieldMap.put(63, new Field(63,"Client ID",0,2,AppConstants.VIETTEL_SETTINGS.getClientId(),true)); 
		return serializeMessage("0800",fieldMap);
	}
	
	
	private static String serializeMessage(String messageType,Map<Integer, Field> fieldMap) throws Exception{
		Integer[] keys=new Integer[fieldMap.size()];
		fieldMap.keySet().toArray(keys);
		Arrays.sort(keys);
		String message="";
		char[] binBitmap=new char[]{
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0',
				'0','0','0','0'
			};
		for(int i=0;i<keys.length;i++ ){
			Field field= fieldMap.get(keys[i]);
			if (StringUtils.isNotBlank(field.getValue())){
				binBitmap[keys[i]-1]='1';
				message=message+build(field);
			}
		}
		message=messageType+convertBinToHex(String.valueOf(binBitmap))+message;
		message=StringUtils.repeat("0",4-String.valueOf(message.length()).length())+message.length()+message;
		return message;
	}
	
	private static String serializeSignature(Map<Integer, Field> fieldMap)throws Exception {
		Integer[] keys=new Integer[fieldMap.size()];
		fieldMap.keySet().toArray(keys);
		Arrays.sort(keys);
		String sign="";
		for(int i=0;i<keys.length;i++ ){
			Field field= fieldMap.get(keys[i]);
			if (field.isSignable() && StringUtils.isNotBlank(field.getValue()))
				if (field.getLength()!=0){
					sign=sign+StringUtils.repeat("0",field.getLength()-field.getValue().length())+field.getValue();
				}else{
					sign=sign+field.getValue();
				}
				
		}
		PKCS8EncodedKeySpec specPriv = new PKCS8EncodedKeySpec(Base64.decodeBase64(AppConstants.VIETTEL_SETTINGS.getPrivateKey().getBytes()));
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey= kf.generatePrivate(specPriv);
		Signature instance = Signature.getInstance("SHA1withRSA");
		instance.initSign(privateKey);
		instance.update((sign).getBytes());
		byte[] signature = instance.sign();
		return new String(Base64.encodeBase64(signature));
	}

	public static String addMsisdnPrefix(String msisdn){
		if (StringUtils.isBlank(msisdn)) return msisdn;
		if (msisdn.startsWith("84")) return msisdn;
		if (msisdn.startsWith("+84")) return msisdn.substring(1);
		if (msisdn.startsWith("0084")) return msisdn.substring(2);
//		come here if not start with 84, ignore prefix 0 (0-xxxxxxxx)
		return "84"+msisdn.substring(1);
	}
	
	private static String convertHexToBin(String strHex){
		String result = "";
		for (int i = 0; i < strHex.length(); i++) 
			result=result+hex2BinMap.get(strHex.substring(i,i+1));
		return result;
	}
	
	
	private static String convertBinToHex(String strBin) {
		String result="";
		for (int i=0;i<strBin.length();i=i+4){
			result=result+bin2HexMap.get(strBin.substring(i,i+4));
		}
		return result;
	}
	
	private static String build(Field field) throws Exception
    {
		String result = null;
        if (field.getValue() == null) throw new Exception("Value can not be null. | detail-"+field);
		if (field.getLength() != 0){
           result =StringUtils.repeat("0",field.getLength()-field.getValue().length() )+field.getValue();
           if (result.length()!=field.getLength()) throw new Exception("Value length is not correct. | detail-"+field+" | result-"+result);
		}
        else 
        	result=StringUtils.repeat("0",field.getLengthIndicator()-String.valueOf(field.getValue().length()).length() )+field.getValue().length()+""+field.getValue();
        return result;
    }
	
	public static Map<Integer, Field> deserializeMessage(String responseMessage) throws Exception{
		String bitmap=convertHexToBin(responseMessage.substring(8,24));
		String message=responseMessage.substring(24);
		Map<Integer, Field> resultMap=new HashMap<Integer, Field>();
		for (int i=1;i<64;i++){
			if (bitmap.substring(i, i+1).equalsIgnoreCase("1")){
				Field field= getResponseField(i+1);
				if (field.getLength()!=0){
					field.setValue(message.substring(0,field.getLength()));
					message=message.substring(field.getLength());
					resultMap.put(field.getNumber(), field);
				}else{
					int valueLength=Integer.valueOf(message.substring(0,field.getLengthIndicator()));
					message=message.substring(field.getLengthIndicator());
					field.setValue(message.substring(0,valueLength));
					message=message.substring(valueLength);
					resultMap.put(field.getNumber(), field);
				}
//				System.out.println(field.getNumber()+"  : " +field.getValue());
			}
		}
		return resultMap;
	}
	
	private static Field getResponseField(int i) throws Exception {
		return (Field)BeanUtils.cloneBean(responseFields.get(i));
	}

	static Socket socket;
	static int timeoutInMs=60000;
	public static void connect(String ip,int port) throws Exception{
		socket = openSocket(ip, port);
	  }
	  public static void disconnect() {
		  try
		    {
			  if (socket!=null){
				  socket.close();
				  socket=null;
			  }
		    }
		    catch (Exception e)
		    {
		    	log.error("Fail to disconnect ",e);
		    }finally{
		    	try{
		    		if (socket!=null){
		    		 socket.close();
		    		 socket=null;
		    		}
		    	}catch (Exception e) {
		    		e.printStackTrace();
				}
		    }
		  
	  }
	  
	  private static String delivery(String message) throws ResponseException, RequestException 
	  {
		
	    try 
	    {
	    	OutputStream out =socket.getOutputStream();
	    	out.write(message.getBytes());
	    	out.flush();
	    }catch (Exception e) {
	    
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
	    	throw new ResponseException(e.getMessage(),e);
	    }
	  }
	
	  private static void sendMessage(String message) throws RequestException 
	  {
		
	    try 
	    {
	    	OutputStream out =socket.getOutputStream();
	    	out.write(message.getBytes());
	    	out.flush();
	    }catch (Exception e) {
	    
	    	throw new RequestException(e.getMessage(),e);
		}
	  }
	  
	  private static void getResponse()
	  {
//		while (socket!=null && socket.isConnected()){
		    try{
		    	 InputStream in=socket.getInputStream();
		    	 byte[] lenbuf = new byte[4];
		    	 in.read(lenbuf);
		         int size = Integer.parseInt(new String(lenbuf));
		   		 byte[] buf = new byte[size];
		   		 in.read(buf);
		   		 System.out.println(new String(lenbuf)+new String(buf));
		    } 
		    catch (Exception e) 
		    {
		    	log.error(e.getMessage());
		    }
		}
//	  }
	  
	  private static Socket openSocket(String server, int port) throws Exception
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
	  
//	  public static boolean exportCdrFile(CdrDataFilter filter, String fileName,String dateFormat) throws Exception {
//			boolean result =true;
//			log.info("Going to generate Viettel Cdr file| Filter: " +filter+"| FileName:"+fileName);
//			CdrDataDao txnDao=new CdrDataDao();
//			List<CdrData> txnList; 
//			FileWriter outFile;
//			PrintWriter out;
//			boolean hasResult=false;
//			try{
//				outFile= new FileWriter(fileName);
//				out = new PrintWriter(outFile);
//			}catch (IOException e) {
//				log.error("Error while generating Viettel Cdr file | Fail to open FileWriter. | FileName-"+fileName);
//				throw e;
//			}
//			try{
//				int startReccord=0;
//				filter.setRecordSize(50);
//				StringBuffer stringBuffer;
//				do{
//					filter.setStartRecord(startReccord);
//					txnList= txnDao.search(filter);
//					if (txnList==null) txnList=new ArrayList<CdrData>();
//					String agentName=AppConstants.VIETTEL_SETTINGS.getClientId();
//					for(int i=0;i<txnList.size();i++){
//						CdrData cdrData=txnList.get(i);
//						stringBuffer=new StringBuffer("");
//						stringBuffer.append( cdrData.getMethod()).append(":")
//											.append(ViettelUtils.addMsisdnPrefix(cdrData.getMsisdn())).append(":")
//											.append(cdrData.getAmount()).append(":")
//											.append("000000").append(":")
//											.append(agentName).append(":")
//											.append(cdrData.getMessageId()).append(":")
//											.append(cdrData.getExt1()).append(":")
//											.append(cdrData.getExt2()).append(":")
//											.append(cdrData.getErrorCode());
//						out.println(stringBuffer.toString());
//						hasResult=true;
//					}
//					startReccord+=txnList.size();
//				}while (txnList.size()==filter.getRecordSize());
//				out.close();
//				out=null;
//				log.info("File created. | Total record: "+startReccord+"| FileName: "+fileName);
//			}catch (Exception e) {
//				log.error("Error while generate Mobifone Cdr file.",e);
//				throw e;
//			}finally{
//				try{
//					if (out!=null)
//						out.close();
//				}catch (Exception e) {
//					log.error("Fail to close FileWriter",e);
//				}
//			}
//			if (!hasResult){
//				log.info("No transaction | Filter: "+filter);
//				return false;
//			}
//			
//			return result;
//		}  
	public static void main(String[] args) throws Exception {
	/*	
		ViettelUtils.connect("localhost", 8583);
		String strDate="20110816020625";
		Date date=DateUtils.convertStringToDate(strDate, "yyyyMMddHHmmss");
		String response="";
		String message;
//		message=ViettelUtils.serializeTopupMessage("84973440912","10000",date,strDate+"6");
		message=ViettelUtils.serializeNetworkMessage(date);
		System.out.println(message);
		response=ViettelUtils.delivery(message);
		System.out.println("response: "+response);
		System.out.println(deserializeMessage(response));
		ViettelUtils.disconnect();
*/
		System.out.println("0240081022200000020000030000002".substring(4, 8));
		
//		System.out.println(deserializeMessage("0048080022000000000000020000002011082913250206mobivi"));
		
		
		
	}
	
	
	
}
