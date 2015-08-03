package com.mbv.frontend.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mbv.security.AtUserDetails;
import com.mbv.services.xsecd.SessionInfo;

public class AppUtils {
	private static Log log=LogFactory.getLog(AppUtils.class); 
	public static AtUserDetails getUserLogin() {
		try{
			SecurityContext sc = SecurityContextHolder.getContext();
			Authentication user = sc.getAuthentication();
			Object details = user.getPrincipal();
			if (details instanceof UserDetails) {
				return (AtUserDetails)details;
			}else return new AtUserDetails(new SessionInfo(),new GrantedAuthority[]{});
		}catch (Exception e) {
			return new AtUserDetails(new SessionInfo(),new GrantedAuthority[]{});
		}
	}
	public static String getUserNameLogin() {
		return getUserLogin().getUsername();
	}
	public static String convertStringToCurrency(String str) {
		if (StringUtils.isBlank(str)) return str; 
		if (str.length()<=3) return str;
		String convertStr="";
		while (str.length()>3){
			if (StringUtils.isBlank(convertStr))
				convertStr=str.substring(str.length()-3,str.length());
			else
				convertStr=str.substring(str.length()-3,str.length())+"."+convertStr;
			str=str.substring(0,str.length()-3);
		}
		convertStr=str+"."+convertStr;
		if (convertStr.startsWith("-.")) convertStr="-"+convertStr.substring(2);
		if (convertStr.startsWith("+.")) convertStr="+"+convertStr.substring(2);
		return convertStr;
	}
	public static String builJsonResult(HttpServletRequest request,Map<String, String>result,String strutsPage){
		Gson gson=new Gson();
		String jsonResult=gson.toJson(result);
		request.setAttribute("jsonResult", jsonResult);
		return strutsPage;
	}
	
	public static void main(String[] args) {
		Gson gson=new GsonBuilder().disableHtmlEscaping().create();
		Map<String, String> map=new HashMap<String, String>();
		map.put("s", "<");
		System.out.println(gson.toJson(map));
	}
	
	public static String unicode2UnicodeEsc(String uniStr)
    {
        StringBuffer ret = new StringBuffer();
        if(uniStr == null)
            return null;
        int maxLoop = uniStr.length();
        for(int i = 0; i < maxLoop; i++)
        {
            char character = uniStr.charAt(i);
            if(character <= '\177')
            {
                ret.append(character);
            } else
            {
                ret.append("\\u");
                String hexStr = Integer.toHexString(character);
                int zeroCount = 4 - hexStr.length();
                for(int j = 0; j < zeroCount; j++)
                    ret.append('0');

                ret.append(hexStr);
            }
        }

        return ret.toString();
    }
	 public static String unicodeEsc2Unicode(String unicodeStr)
	    {
	        if(unicodeStr == null)
	            return null;
	        StringBuffer retBuf = new StringBuffer();
	        int maxLoop = unicodeStr.length();
	        for(int i = 0; i < maxLoop; i++)
	            if(unicodeStr.charAt(i) == '\\')
	            {
	                if(i < maxLoop - 5 && (unicodeStr.charAt(i + 1) == 'u' || unicodeStr.charAt(i + 1) == 'U'))
	                    try
	                    {
	                        retBuf.append((char)Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
	                        i += 5;
	                    }
	                    catch(NumberFormatException _ex)
	                    {
	                        retBuf.append(unicodeStr.charAt(i));
	                    }
	                else
	                    retBuf.append(unicodeStr.charAt(i));
	            } else
	            {
	                retBuf.append(unicodeStr.charAt(i));
	            }

	        return retBuf.toString();
	    }
}
