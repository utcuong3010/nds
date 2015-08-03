package com.mbv.request;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

import com.mbv.airline.common.DateUtils;

public class Main {

	public static void main(String[] args) throws Exception {
		
		String dateString = "Vui lòng thanh toán trước 13:48 13/05/2015 sau thời hạn trên vé sẽ bị hủy";
		String expireDate = dateString.replaceAll("[^0-9,:,/,\\s]","").trim();
		System.out.println(DateUtils.parse(expireDate, DateUtils.HH_MM_DD_MM_YYYY)); 
		Date currentDate = new Date(System.currentTimeMillis()+5*60*1000);
		System.out.println(currentDate);
		
		// encode data on your side using BASE64
		String str = "123";
		byte[]   bytesEncoded = Base64.encodeBase64(str .getBytes());
		System.out.println("ecncoded value is: " + new String(bytesEncoded ));

		// Decode data on other side, by processing encoded data	
		byte[] valueDecoded= Base64.decodeBase64(bytesEncoded);
		System.out.println("Decoded value is: " + new String(valueDecoded));
	}

}
