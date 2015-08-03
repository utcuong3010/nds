package com.mbv.bp.common.util;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.generator.IdGeneratorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.Field;
import com.mbv.bp.common.model.MessageInfo;

public class VnPayUtils{
	private static Log log = LogFactory.getLog(VnPayUtils.class);
	
	public static String serialize(MessageInfo messageInfo)  throws Exception{
		Integer[] keys=new Integer[messageInfo.getFieldMaps().size()];
		messageInfo.getFieldMaps().keySet().toArray(keys);
		Arrays.sort(keys);
		StringBuilder builder = new StringBuilder();
		builder.append(messageInfo.getMessageType());
		for(int i=0;i<keys.length;i++){
			builder.append(build(messageInfo.getFieldMaps().get(keys[i])));
		}
		String message=builder.toString();
		message=StringUtils.repeat("0",4-(message.length()+"").length())+""+message.length()+message;
		return message;
	}
	
	public static String serialize(ContextBase context) throws Exception{
		MessageInfo messageInfo=AppConstants.VNPAY_SETTINGS.getMessageInfo(context.get(Attributes.ATTR_MESSAGE_TYPE));
		if( messageInfo==null) throw new Exception("Invalid context input");
		if (messageInfo.getMessageType().equalsIgnoreCase(AppConstants.TOPUP_REQUEST)){
			String value="";
			String macBuilder=AppConstants.VNPAY_SETTINGS.getSceretKey();
//			<field num="2"    length="0"   lengthIndicator="2" name="msisdn"></field>
			value=context.get(Attributes.ATTR_MSISDN);
			messageInfo.getFieldMaps().get(2).setValue(value);
			macBuilder=macBuilder+value;
//			<field num="3"    length="6"   lengthIndicator="0" name="processing code"></field>
			value=AppConstants.VNPAY_SETTINGS.getProcessingCode();
			messageInfo.getFieldMaps().get(3).setValue(value);
			macBuilder=macBuilder+StringUtils.repeat("0",messageInfo.getFieldMaps().get(3).getLength()- value.length())+ value;
//			<field num="4"    length="12"  lengthIndicator="0" name="transaction amount"></field>
			value=context.get(Attributes.ATTR_AMOUNT);
			messageInfo.getFieldMaps().get(4).setValue(value);
			macBuilder=macBuilder+StringUtils.repeat("0",messageInfo.getFieldMaps().get(4).getLength()- value.length())+ value;
//			<field num="18"   length="4"   lengthIndicator="0" name="merchant type">6014</field>
			messageInfo.getFieldMaps().get(18).setValue(AppConstants.VNPAY_SETTINGS.getMerchantType());
			macBuilder=macBuilder+messageInfo.getFieldMaps().get(18).getValue();
//			<field num="32"   length="0"   lengthIndicator="2" name="agent code">1000000030</field>
			messageInfo.getFieldMaps().get(32).setValue(AppConstants.VNPAY_SETTINGS.getAgentCode());
			macBuilder=macBuilder+messageInfo.getFieldMaps().get(32).getValue();
//			<field num="48"   length="0"   lengthIndicator="3" name="additional information"></field>
			value=buildAddInfoValue(context.getDate(Attributes.ATTR_DELIVERY_DATE),context.get(Attributes.ATTR_MESSAGE_ID));
			messageInfo.getFieldMaps().get(48).setValue(value);
			macBuilder=macBuilder+value;
			macBuilder=DigestUtils.md5Hex(macBuilder).toUpperCase();
//			<field num="64"   length="16"   lengthIndicator="0" name="mac1"></field>
			messageInfo.getFieldMaps().get(64).setValue(macBuilder.substring(0,16));
//			<field num="128"  length="16"  lengthIndicator="0" name="mac2"></field>
			messageInfo.getFieldMaps().get(128).setValue(macBuilder.substring(16,32));
			return serialize(messageInfo);
			
		}else if (messageInfo.getMessageType().equalsIgnoreCase(AppConstants.NETWORK_REQUEST)){
			
//			<field num="1"    length="32"  lengthIndicator="0" name="bitmap">82200001000000000400000000000000</field>
			messageInfo.getFieldMaps().get(7).setValue(DateUtils.convertDatetoString(context.getDate(Attributes.ATTR_DELIVERY_DATE), "MMddHHmmss"));
//			<field num="7"    length="10"  lengthIndicator="0" name="transaction date"></field>
			messageInfo.getFieldMaps().get(11).setValue(context.get(Attributes.ATTR_MESSAGE_ID));
//			<field num="11"   length="6"   lengthIndicator="0" name="transaction id"></field>
			messageInfo.getFieldMaps().get(32).setValue(AppConstants.VNPAY_SETTINGS.getAgentCode());
//			<field num="32"   length="0"   lengthIndicator="0" name="agent code"></field>
			messageInfo.getFieldMaps().get(70).setValue(context.get(Attributes.ATTR_NETWORK_CODE));
//			<field num="70"   length="4"   lengthIndicator="0" name="network code"></field>
			return serialize(messageInfo);
		}else {
			throw new Exception("Unsupported messageType-"+context.get(Attributes.ATTR_MESSAGE_TYPE));
		}
		
	}
	
	public static String buildNetworkMsg(String networkCode) throws Exception{
		ContextBase context=new ContextBase(); 
	      context.put(Attributes.ATTR_MESSAGE_TYPE, AppConstants.NETWORK_REQUEST);
	      context.put(Attributes.ATTR_MESSAGE_ID,IdGeneratorFactory.getInstance().getVnPayIdGenentor().generateId());
	      context.put(Attributes.ATTR_NETWORK_CODE,networkCode);
	      context.putDate(Attributes.ATTR_DELIVERY_DATE,new Date());
	      return serialize(context);
	}
	
	public static String buildAddInfoValue(Date date,String trace)
    {
        return "01" + "014" + DateUtils.convertDatetoString(date,"yyyyMMddHHmmss") + "02" + "014" + DateUtils.convertDatetoString(date,"yyyyMMdd") +StringUtils.repeat("0", 6-trace.length()) +trace;
        
    }	

	public static MessageInfo deSerialize(String message) throws Exception {
		MessageInfo info=getMessageInfoTempFromMessage(message);
		Integer[] keys=new Integer[info.getFieldMaps().size()];
		info.getFieldMaps().keySet().toArray(keys);
		Arrays.sort(keys);
		String curMsg=message.substring(8);
		
		for(int i=0;i<keys.length;i++){
			Field field=info.getFieldMaps().get(keys[i]);
			if (field.getLength()!=0){
				String temp=curMsg.substring(0,field.getLength());
				while(temp.startsWith("0")&&temp.length()>1)
					temp=temp.substring(1);
				field.setValue(temp);
				curMsg=curMsg.substring(field.getLength());
				info.getFieldMaps().put(field.getNumber(), field);
			}else{
				int length=Integer.parseInt(curMsg.substring(0,field.getLengthIndicator()));
				field.setValue(curMsg.substring(field.getLengthIndicator(),length+field.getLengthIndicator()));
				curMsg=curMsg.substring(field.getLengthIndicator()+length);
				info.getFieldMaps().put(field.getNumber(), field);
			}
		}
		return info;
	}
	
	public static MessageInfo deSerializeAddtionalInfo(String message) throws Exception {
		MessageInfo info=AppConstants.VNPAY_SETTINGS.getMessageInfo(AppConstants.TOPUP_RESPONSE_ADD_INFO);
		String data=message;
		while (data.length()>0){
		String tag=data.substring(0,2);
		data=data.substring(2);
			if (tag.equalsIgnoreCase("01")){
				int length=Integer.parseInt(data.substring(0,3));
				data=data.substring(3);
				info.getFieldMaps().get(1).setValue(data.substring(0,length));
				data=data.substring(length);
			}else if (tag.equalsIgnoreCase("02")){
				int length=Integer.parseInt(data.substring(0,3));
				data=data.substring(3);
				info.getFieldMaps().get(2).setValue(data.substring(0,length));
				data=data.substring(length);
			}else if (tag.equalsIgnoreCase("03")){
				int length=Integer.parseInt(data.substring(0,3));
				data=data.substring(3);
				info.getFieldMaps().get(3).setValue(data.substring(0,length));
				data=data.substring(length);
			}else if (tag.equalsIgnoreCase("04")){
				int length=Integer.parseInt(data.substring(0,3));
				data=data.substring(3);
				info.getFieldMaps().get(4).setValue(data.substring(0,length));
				data=data.substring(length);
			}else{
				throw new Exception("invalid message");
			}
		}
		return info;
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
	
	
	
	public static void printMessage(MessageInfo messageInfo) {
		Integer[] keys=new Integer[messageInfo.getFieldMaps().size()];
		messageInfo.getFieldMaps().keySet().toArray(keys);
		Arrays.sort(keys);
		log.info(messageInfo.getMessageType());
		for(int i=0;i<keys.length;i++){
			log.info(messageInfo.getFieldMaps().get(keys[i]));
		}
		
	}
	
	public static MessageInfo getMessageInfoTempFromMessage(String message){
		try{
			String messageType=message.substring(4,8);
			MessageInfo messageInfo=AppConstants.VNPAY_SETTINGS.getMessageInfo(messageType);
			return messageInfo;
		}catch (Exception e) {
			log.error("Invalid vnpay message");
			return null;
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		
//		MessageInfo messageInfo=null;
//		String msg=null;
//		messageInfo = AppConstants.VNPAY_SETTINGS.getMessageInfo(AppConstants.TOPUP_RESPONSE);
//		msg="01810210F0004001020100010000000000000001100973440912019001000000001000601410100000003000062010142011042519335302014201104250190010300610000004008201107255A4200E1AC159E30963A9906F6DA";
//		VnPayMessageUtil.deSerialize(msg, messageInfo);
//		VnPayMessageUtil.printMessage(messageInfo);
//		System.out.println(DateUtil.convertDatetoString(new Date(), "MMddHHmmss"));
//		System.out.println(buildNetworkMsg(AppConstants.VNPAY_SETTINGS.getNetworkLoginCode()));
//		msg="01014201104240101030201420110424000160030061000000400820110724";
//		messageInfo=VnPayMessageUtil.deSerializeAddtionalInfo(msg);
//		VnPayMessageUtil.printMessage(messageInfo);
		String str=
		"MBV553286"+
		"0973440912"+
		"000000" +
		"000000010000"+
		"6014"+
		"101000000030"+
		"79"+
		"010142011071111254602014201107110460045";
		System.out.println(DigestUtils.md5Hex(str).toUpperCase());
	}
}
