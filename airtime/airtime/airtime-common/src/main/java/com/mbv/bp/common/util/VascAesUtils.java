package com.mbv.bp.common.util;
import java.io.UnsupportedEncodingException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;


public class VascAesUtils {
	
	private static String padString(String source) 
	{
		int size = 0;
		//set keysize
		if(source.length() <= 16)
			 size = 16;
		else if(source.length() <= 24)
			 size = 24;
		else
			 size = 32;

		//padding byte null vao key
			if(source.length() % size != 0  )
			{
				  int b = 0;
				  char paddingChar = (char) b;
				  int padLength = size - source.length() % size;
				 
				  
				  for (int i = 0; i < padLength; i++) {
				    source += paddingChar;
				  }
			}
		return source;
	}
	

	public static String Rijndael_Encrypt(String input, String key) throws UnsupportedEncodingException, DataLengthException, IllegalStateException, InvalidCipherTextException
	{
		 		//init cipher
		
				// keysize = 16bytes/24bytes/32bytes
				// blocksize = 128bits / 256bits
		
				int blocksize = 256;

				key = padString(key);
				if(key.length() == 16 || key.length() == 24 || key.length() == 32)
				{
					KeyParameter keyparam = new KeyParameter(key.getBytes());
					
					BlockCipherPadding padding = new ZeroBytePadding();
					BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new RijndaelEngine(blocksize), padding);
					cipher.reset();
					cipher.init(true, keyparam);
			
					byte[] buf = new byte[cipher.getOutputSize(input.length())];
					int len = cipher.processBytes(input.getBytes(), 0, input.length(), buf, 0);
						
					cipher.doFinal(buf, len);
					
					String crypted = new String(Base64.encodeBase64(buf));
					
					return crypted;
				}
				else
				{
					
					return "keysize khong dung;keysize = 16 or 24 or 32";
				}

	}
	
	
	public static String Rijndael_Decrypt(String input, String key) throws UnsupportedEncodingException, DataLengthException, IllegalStateException, InvalidCipherTextException
	{
		 		//init cipher
		
				// keysize = 16bytes/24bytes/32bytes
				// blocksize = 128bits / 256bits
		
				int blocksize = 256;
				byte[] data_decrypted = Base64.decodeBase64(input.getBytes());

				key = padString(key);
				if(key.length() == 16 || key.length() == 24 || key.length() == 32)
				{
					KeyParameter keyparam = new KeyParameter(key.getBytes());
					
					BlockCipherPadding padding = new ZeroBytePadding();
					BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new RijndaelEngine(blocksize), padding);
					cipher.reset();
					cipher.init(false, keyparam);
			
					byte[] buf = new byte[cipher.getOutputSize(data_decrypted.length)];
					int len = cipher.processBytes(data_decrypted, 0, data_decrypted.length, buf, 0);
						
					cipher.doFinal(buf, len);
					
					String decrypted = new String(buf);
					
					return decrypted;
				}
				else
				{
					
					return "keysize khong dung;keysize = 16 or 24 or 32";
				}

	}
	public static void main(String[] args) {
		try {
			System.out.println(VascAesUtils.Rijndael_Encrypt("84916720012", "123456"));
//			System.out.println(VascAesUtils.Rijndael_Decrypt("0jjDHNB+AeUHsuVJYZJZAABLNe/JhXvv/5BPehNf0w4=", "1234561112345611123456111"));
		} catch (DataLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidCipherTextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}


