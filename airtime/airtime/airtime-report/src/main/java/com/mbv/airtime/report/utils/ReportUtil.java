package com.mbv.airtime.report.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;

public class ReportUtil {
	private static Log log=LogFactory.getLog(ReportUtil.class); 
		
	public static String getTextFromStatus(String txnStatus){
		String result="";
		if (AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(txnStatus)){
			result="\u0110ang x\u1eed l\u00fd";
		}else if (AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(txnStatus)){
			result="Kh\u00f4ng x\u00e1c \u0111\u1ecbnh";
		}else if (AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(txnStatus)){
			result="Th\u00e0nh c\u00f4ng";
		}else if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(txnStatus)){
			result="Th\u1ea5t b\u1ea1i";
		}
		
		return result;
	}
}
