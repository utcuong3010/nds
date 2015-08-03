/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mbv.helpers;


import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class StringHelper {

    static final byte[] HEX_CHAR_TABLE = {
        (byte) '0', (byte) '1', (byte) '2', (byte) '3',
        (byte) '4', (byte) '5', (byte) '6', (byte) '7',
        (byte) '8', (byte) '9', (byte) 'a', (byte) 'b',
        (byte) 'c', (byte) 'd', (byte) 'e', (byte) 'f'
    };
    
    public static String hexEncode(byte[] raw) {
        byte[] hex = new byte[2 * raw.length];
        int index = 0;

        for (byte b : raw) {
            int v = b & 0xFF;
            hex[index++] = HEX_CHAR_TABLE[v >>> 4];
            hex[index++] = HEX_CHAR_TABLE[v & 0xF];
        }
        return new String(hex);
    }
    
    public static byte[] base64Encode(byte[] raw) {
    	byte[] base64 = Base64.encodeBase64(raw, false);
    	return base64;
    }
    
    public static String base64EncodeString(String raw, boolean urlSafe ) {
    	try {
			byte[] data = base64Encode(raw.getBytes("utf-8"));
			if (urlSafe) {
				for (int index=0; index<data.length; index ++) {
					switch (data[index]) {
					case '+':
						data[index] = (byte)'-';
						break;
					case '/':
						data[index] = (byte)'_';
						break;
					}
				}
			}
			return new String(data);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected exception: " + e.getMessage(), e);
		}
    }
        
    public static byte[] base64Decode(byte[] raw) {
    	return Base64.decodeBase64(raw);
    }
    
    public static String base64DecodeString(String raw, boolean urlSafe) {
    	try {
    		byte[] data = raw.getBytes("utf-8");
    		if (urlSafe) {
				for (int index=0; index<data.length; index ++) {
					switch (data[index]) {
					case '-':
						data[index] = (byte)'+';
						break;
					case '_':
						data[index] = (byte)'/';
						break;
					}
				}
			}
			byte[] decoded = Base64.decodeBase64(data);
			String strRet = new String(decoded, "utf-8");
			
			return strRet;
    	} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected exception: " + e.getMessage(), e);
		}
    }
    
    
    
    public static boolean isEmpty(String str){
    	if (str==null || str.isEmpty() || str.trim().isEmpty()) return true;
    	else return false;
    }
}
