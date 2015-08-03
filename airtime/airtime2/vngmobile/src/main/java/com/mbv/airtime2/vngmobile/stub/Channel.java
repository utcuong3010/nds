package com.mbv.airtime2.vngmobile.stub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.DESedeParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Throwables;
import com.mbv.airtime2.vngmobile.Config;

public class Channel {
	private static Logger LOGGER = Logger.getLogger(Channel.class);
	private Config config;
	private Socket socket;
	private String sessionToken;

	PaddedBufferedBlockCipher encrypter;
	PaddedBufferedBlockCipher decrypter;

	public Channel(Config config){
		this.config = config;
	}
	
	private void InitCiphers() throws Exception {
		if(encrypter == null){
			encrypter = new PaddedBufferedBlockCipher(new DESedeEngine());
			encrypter.init(true, new KeyParameter(config.getPgTokenKey().getBytes()));	
		}
		if(decrypter == null){
			decrypter = new PaddedBufferedBlockCipher(new DESedeEngine());
			decrypter.init(false, new KeyParameter(config.getPgTokenKey().getBytes()));
		}
	}

	public ISO8583Message Send(ISO8583Message request) throws Exception {
		String tmpStr;
		byte[] buf;
		byte[] messagePart;
		int msgLength;
		byte[] lengthPart;
		try{
			tmpStr = serialize(request);
			LOGGER.info("Channel Sending: " + tmpStr);
			buf = tmpStr.getBytes();

			messagePart = new byte[encrypter.getOutputSize(buf.length)];
			msgLength = encrypter.processBytes(buf, 0, buf.length, messagePart, 0);
			msgLength += encrypter.doFinal(messagePart, msgLength);
			encrypter.reset();
			lengthPart = new byte[] { (byte) (msgLength >>> 24),
				(byte) (msgLength >>> 16), (byte) (msgLength >>> 8),
				(byte) msgLength };

			socket.getOutputStream().write(lengthPart);
			socket.getOutputStream().write(messagePart, 0, msgLength);
		}catch(Exception ex){
			throw Throwables.propagate(ex);
		}
		// receive
		try{
			socket.getInputStream().read(lengthPart, 0, 4);
	
			msgLength = ((lengthPart[0] & 0xff) << 24)
					| ((lengthPart[1] & 0xff) << 16)
					| ((lengthPart[2] & 0xff) << 8) | (lengthPart[3] & 0xff);
	
			messagePart = new byte[msgLength];
			socket.getInputStream().read(messagePart, 0, msgLength);
	
			buf = new byte[decrypter.getOutputSize(messagePart.length)];
			msgLength = decrypter.processBytes(messagePart, 0, messagePart.length, buf, 0);
			msgLength += decrypter.doFinal(buf, msgLength);
			decrypter.reset();
	
			tmpStr = new String(buf, 0, msgLength);
			LOGGER.info("Channel Received: " + tmpStr);
			return deserialize(tmpStr);
		}catch(Exception ex){			
			LOGGER.error("Channel Receiving Error: " + ex.getMessage());
			return null;
		}
	}
	
	public String getSessionToket(){
		return this.sessionToken;
	}
	
	private String generateSessionToken(String data, String tokenKey) throws Exception{
		
		BufferedBlockCipher cipher = new BufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
		CipherParameters cipherParams = new ParametersWithIV(
				new DESedeParameters(Hex.decodeHex(tokenKey.toCharArray())), 
				new byte[] { 0, 0, 0, 0, 0, 0, 0, 0 });			
		cipher.init(true, cipherParams);
		
		ByteArrayOutputStream tmpByteStream = new ByteArrayOutputStream();
		byte[] buf = data.getBytes();
		tmpByteStream.write(buf, 0, buf.length);
		while(tmpByteStream.size() %8 != 0){
			tmpByteStream.write(0xFF);
		}
		buf = tmpByteStream.toByteArray();
		
		byte[] enc = new byte[cipher.getOutputSize(buf.length)];
		int msgLength = cipher.processBytes(buf, 0, buf.length, enc, 0);		
		msgLength += cipher.doFinal(enc, msgLength);
		
		return new String(Hex.encodeHex(enc, false));
	}
	
	public boolean isConnected(){
		if(socket == null || socket.isClosed() || "".equals(sessionToken)) return false;
		return true;
	}
	
	public void Connect() {
		try{			
			InitCiphers();
			socket = new Socket();
			socket.connect(new InetSocketAddress(config.getHost(), config.getPort()), 10000);
			
			//Connect
			ISO8583Message request = new ISO8583Message();
			request.setType(ISO8583Message.TYPE_AUTHORIZATION_REQUEST);
			request.setTransmissionDate(new Date());
			request.setRefInfo(config.getPgLogin() + "|" + config.getPgPassword());
			ISO8583Message response = Send(request);
			if (!response.isSuccess()) throw new Exception("Connect Error");
			
			//Login
			request = new ISO8583Message();
			request.setType(ISO8583Message.TYPE_AUTHORIZATION_REQUEST);
			request.setProcessingCode("010000");
			request.setTransmissionDate(new Date());
			request.setServiceRestrictionCode(config.getEvourcherPassword());
			request.setRefInfo(config.getEvourcherLogin() + "|" + config.getEvourcherMsisdn());
			request.setDate(new Date());
			request.setServiceIndicator(config.getEvourcherServiceCode());
			response = Send(request);
			
			if (!response.isSuccess()) throw new Exception("Login Error");
			sessionToken = generateSessionToken(config.getEvourcherMpin(), response.getTokenKey());
		}catch(Exception ex){
			Disconnect();
			LOGGER.error("Channel Connecting Error : " + ex.getMessage());
		}
	}
		
	public void Disconnect() {				
		try{
			socket.close();
		}catch(Exception ex){
			//TODO
		}finally{
			socket = null;
			sessionToken = "";
		}
	}
	

	private static final String ISOMSG = "isomsg";
	private static final String ISOMSG_FIELD = "field";
	private static final String ISOMSG_FIELD_ID = "id";
	private static final String ISOMSG_FIELD_VALUE = "value";
	
	public static String serialize(ISO8583Message msg) throws Exception{
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Element rootElement = doc.createElement(ISOMSG);
		doc.appendChild(rootElement);
		for(Entry<Integer,String> entry : msg.entrySet()){
			Element field = doc.createElement(ISOMSG_FIELD);
			field.setAttribute(ISOMSG_FIELD_ID, entry.getKey().toString());
			field.setAttribute(ISOMSG_FIELD_VALUE, entry.getValue());
			rootElement.appendChild(field);				
		}	     
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.toString();
	}
	
	public static ISO8583Message deserialize(String text) throws Exception{
		Document doc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new ByteArrayInputStream(text.getBytes()));
		doc.getDocumentElement().normalize();
		
		NodeList nList = doc.getDocumentElement().getChildNodes();
 
		ISO8583Message msg = new ISO8583Message();
		for (int temp = 0; temp < nList.getLength(); temp++) { 
		   Node nNode = nList.item(temp);
		   if (nNode.getNodeType() == Node.ELEMENT_NODE && ISOMSG_FIELD.equals(nNode.getNodeName())) {
		      Element eElement = (Element) nNode;
		      msg.add(eElement.getAttribute(ISOMSG_FIELD_ID) , eElement.getAttribute(ISOMSG_FIELD_VALUE));
		   }
		}
		return msg;
	}
}
