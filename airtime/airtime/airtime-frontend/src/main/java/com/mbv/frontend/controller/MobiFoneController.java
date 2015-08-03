package com.mbv.frontend.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AsyncReqTempDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.model.MobiBalanceInfo;
import com.mbv.bp.common.vo.airtime.AsyncReqTemp;
import com.mbv.bp.common.vo.airtime.AsyncReqTempFilter;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.model.MobifoneModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;

public class MobiFoneController extends ActionControllerBase {
	private Log log=LogFactory.getLog(MobiFoneController.class);
	private static final long serialVersionUID = 1L;
	private MobifoneModel mobifoneModel;

	public String listView() throws DaoException {
		return "success";
	}
	
	public String changePwdView() throws DaoException {
		return "success";
	}
	public String balanceListView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		if (mobifoneModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			mobifoneModel=new MobifoneModel();
			mobifoneModel.setFromDate(defaultDate);
			mobifoneModel.setToDate(defaultDate);
			return "success";
		}
		
		AsyncReqTempFilter filter=new AsyncReqTempFilter();
		
		if (StringUtils.isNotBlank(mobifoneModel.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(mobifoneModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(mobifoneModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(mobifoneModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (toDate!=null){
					filter.setToDate(toDate);
					if (filter.getFromDate()!=null)
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						addFieldError("toDate", getText("fromdate.greater.todate"));
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(), new Date())<0)
						addFieldError("toDate", getText("todate.greater.curdate"));
				}else
					addFieldError("toDate", getText("field.date.type.invalid"));
		}
		if (getFieldErrors().size()>0) return "success";
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			addFieldError("toDate", getText("dates.input.not.enough"));
		
		if (getFieldErrors().size()>0) return "success";
		
		
		filter.setRecordSize(FeConstant.MAX_RECORD);
		int page=1;
		if (StringUtils.isBlank(mobifoneModel.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(mobifoneModel.getPage())){
			page=Integer.valueOf(mobifoneModel.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";
		try{
			AsyncReqTempDao dao=new AsyncReqTempDao();
			filter.setOperatorType(AppConstants.MOBIFONE_SETTINGS.getBalanceInquiryOpeation());
			int recordCount=dao.searchCount(filter);
			Paging<MobiBalanceInfo> paging=new Paging<MobiBalanceInfo>(page, 10, filter.getRecordSize(), recordCount);
			mobifoneModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<AsyncReqTemp> list= dao.search(filter);
	
			List<MobiBalanceInfo> balanceInfos=new ArrayList<MobiBalanceInfo>();
			if (list!=null){
				for (int i=0;i<list.size();i++){
					AsyncReqTemp reqTemp=list.get(i);
					Gson gson=new Gson();
					MobiBalanceInfo balanceInfo=gson.fromJson(reqTemp.getValue(), MobiBalanceInfo.class); 
					if(balanceInfo.getTxnDate()!=null)
						balanceInfo.setStrDate(DateUtil.convertDate2String(balanceInfo.getTxnDate(), "GMT+7", "dd/MM/yyyy HH:mm:ss"));
					balanceInfos.add(balanceInfo);
				}
			}
			paging.setItems(balanceInfos);
			request.setAttribute("pageView", paging);
			System.out.println("**************** "+filter);
		}catch (Exception e) {
			log.error("Fail to search Async data",e);
			addFieldError("sys_message", getText("database.exception"));
		}
		return "success";
	}
	
	public String checkTxn(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		if (mobifoneModel==null) return "success";
		if (mobifoneModel.getTxnId()==null) return "success";
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_WFP_NAME, "wfpAsyncReqInquiryTxn");
		context.put(Attributes.ATTR_TRANSACTION_ID,mobifoneModel.getTxnId());
		try{
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.get(Attributes.ATTR_ERROR_CODE))){
				resultMap.put("error", "ERROR");
				resultMap.put("errorCode", result.get(Attributes.ATTR_ERROR_CODE));
			}else{
				resultMap.put("error", "SUCCESS");
				resultMap.put("txn_status",result.get(Attributes.ATTR_TXN_STATUS));
				resultMap.put("txn_errorcode",result.get(Attributes.ATTR_TXN_ERROR_CODE));
				if (AppConstants.MOBIFONE_SETTINGS.getChangePasswordOperation().equalsIgnoreCase(resultMap.get(Attributes.ATTR_OPERATOR_TYPE))){
					resultMap.put("isAlert", "Y");
					resultMap.put("isRefresh", "N");
				}else if (AppConstants.MOBIFONE_SETTINGS.getBalanceInquiryOpeation().equalsIgnoreCase(resultMap.get(Attributes.ATTR_OPERATOR_TYPE))){
					resultMap.put("isAlert", "N");
					resultMap.put("isRefresh", "Y");
				}
// check for vietpay	
				String messageResult="";
				if (AppConstants.VIETPAY_SETTINGS.getTxnInquiryOperation().equalsIgnoreCase(result.get(Attributes.ATTR_OPERATOR_TYPE))){
					String resultValue=result.get(Attributes.ATTR_TXT_INQUIRY_RESULT);
					
					try{
						String[] content=StringUtils.split(resultValue,"|");
						messageResult="Ma giao dich :"+content[0] +"\n";
						
						if (AppConstants.VIETPAY_SETTINGS.isSuccessResponse(content[1])){
							messageResult=messageResult+"Trang Thai : THANH CONG \n";
						}else if (AppConstants.VIETPAY_SETTINGS.isUnknownResponse(content[1])){
							messageResult=messageResult+"Trang Thai : DANG XU LY \n";
						}else
							messageResult=messageResult+"Trang Thai : THAT BAI \n";
					}catch (Exception e) {
						log.error(e);
						messageResult=messageResult+e.getMessage()+"\n";
					}	

					messageResult=messageResult+"Dien Giai :"+resultValue;
					
				}
				resultMap.put("messageResult", messageResult);
			}
		}catch (Exception e) {
			resultMap.put("errorCode", ErrorCode.SYS_INTERNAL_ERROR);
			resultMap.put("error", "ERROR");
			log.error("Fail to check txn -" +mobifoneModel.getTxnId(),e);
		}
		log.info("txn status:"+resultMap);
		return AppUtils.builJsonResult(request, resultMap, "success");
	}

	public String updateBalance(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_WFP_NAME, "wfpMobiFoneBalanceRequestPushToQueue");
		try{
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.get(Attributes.ATTR_ERROR_CODE)))
				resultMap.put("error", "ERROR");
			else{
				resultMap.put("transaction_id", result.get(Attributes.ATTR_TRANSACTION_ID));
				resultMap.put("error", "SUCCESS");
			}
		}catch (Exception e) {
			resultMap.put("error", ErrorCode.SYS_INTERNAL_ERROR);
			log.error("Fail to invoke airtime core for update mobifone balance",e);
		}
		
		return AppUtils.builJsonResult(request, resultMap, "success");
	}
	
	public String changePwd(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		
		if (mobifoneModel==null) resultMap.put("error", "INVALID_INPUT");
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (!StringUtils.isAlphanumeric(mobifoneModel.getPassword())) resultMap.put("error", "INVALID_INPUT");
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		try{
			ContextBase context=new ContextBase();
			context.put(Attributes.ATTR_WFP_NAME, "wfpMobiFoneChangePwdRequestPushToQueue");
			context.put(Attributes.ATTR_NEW_PASSWORD,mobifoneModel.getPassword());
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			if (!ErrorCode.SUCCESS.equalsIgnoreCase(result.get(Attributes.ATTR_ERROR_CODE)))
				resultMap.put("error", "ERROR");
			else{
				resultMap.put("transaction_id", result.get(Attributes.ATTR_TRANSACTION_ID));
				resultMap.put("error", "SUCCESS");
			}
		}catch (Exception e) {
			resultMap.put("error", ErrorCode.SYS_INTERNAL_ERROR);
			log.error("Fail to invoke airtime core for update mobifone balance",e);
		}
		
		return AppUtils.builJsonResult(request, resultMap, "success");
	}

	public MobifoneModel getMobifoneModel() {
		return mobifoneModel;
	}

	public void setMobifoneModel(MobifoneModel mobifoneModel) {
		this.mobifoneModel = mobifoneModel;
	}
	
	
	
}
