package com.mbv.frontend.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.frontend.model.VietPayModel;
import com.mbv.frontend.util.AppUtils;


public class VietPayController extends ActionControllerBase {
	private Log log=LogFactory.getLog(VietPayController.class);
	private static final long serialVersionUID = 1L;
	private VietPayModel vietPayModel;

	public String view() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		return "success";
	}

	public String txnInquiry(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		
		if (vietPayModel==null) resultMap.put("error", "INVALID_INPUT");
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isBlank(vietPayModel.getTxnId()))
			resultMap.put("error", "INVALID_INPUT");
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		try{
			ContextBase context=new ContextBase();
			context.put(Attributes.ATTR_WFP_NAME, "wfpVietPayTxnInquiryRequestPushToQueue");
			context.put(Attributes.ATTR_REQUEST_TXN_ID,vietPayModel.getTxnId());
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.get(Attributes.ATTR_ERROR_CODE)))
				resultMap.put("error", "ERROR");
			else{
				resultMap.put("transaction_id", result.get(Attributes.ATTR_TRANSACTION_ID));
				resultMap.put("error", "SUCCESS");
			}
		}catch (Exception e) {
			resultMap.put("error", ErrorCode.SYS_INTERNAL_ERROR);
			log.error("Fail to invoke airtime core to inquiry VietPay transaction",e);
		}
		
		return AppUtils.builJsonResult(request, resultMap, "success");
	}

	public String balInquiry(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		
		try{
			ContextBase context=new ContextBase();
			context.put(Attributes.ATTR_WFP_NAME, "wfpVietPayBalRequestPushToQueue");
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.get(Attributes.ATTR_ERROR_CODE)))
				resultMap.put("error", "ERROR");
			else{
				resultMap.put("transaction_id", result.get(Attributes.ATTR_TRANSACTION_ID));
				resultMap.put("error", "SUCCESS");
			}
		}catch (Exception e) {
			resultMap.put("error", ErrorCode.SYS_INTERNAL_ERROR);
			log.error("Fail to invoke airtime core to inquiry VietPay transaction",e);
		}
		
		return AppUtils.builJsonResult(request, resultMap, "success");
	}
	
	public VietPayModel getVietPayModel() {
		return vietPayModel;
	}

	public void setVietPayModel(VietPayModel vietPayModel) {
		this.vietPayModel = vietPayModel;
	}

	
	
	
}
