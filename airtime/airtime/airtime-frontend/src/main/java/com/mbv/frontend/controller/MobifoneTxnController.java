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
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.model.AirtimeTxnModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;

public class MobifoneTxnController extends ActionControllerBase {
	private Log log=LogFactory.getLog(MobifoneTxnController.class);
	private static final long serialVersionUID = 1L;
	private AirtimeTxnModel txnModel;
/*
	public String mobifoneListView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		providerAccountMap.put("MOBI", "Mobifone");
		
		request.setAttribute("providerAccountMap", providerAccountMap);
		
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put("", "----"+getText("select.all.text")+"----");
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		
		request.setAttribute("statusMap", statusMap);
		if (txnModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			txnModel=new AirtimeTxnModel();
			txnModel.setFromDate(defaultDate);
			txnModel.setToDate(defaultDate);
			return "success";
		}

		CdrExtFilter filter=new CdrExtFilter();
		filter.setProviderId(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
		
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getAtTxnId())){
			if (StringUtils.isNumeric(txnModel.getAtTxnId().trim())&& txnModel.getAtTxnId().trim().length()==18)
				filter.setAtTxnId(Long.valueOf(txnModel.getAtTxnId().trim()));
			else{
				addFieldError("txnModel.atTxnId", getText("airtime.txn.invalid"));
			}
		}
		
		if (StringUtils.isNotBlank(txnModel.getMsisdn()))
			filter.setMsisdn(txnModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(txnModel.getAmount())){
			if (StringUtils.isNumeric(txnModel.getAmount().trim().replace(".", "")))
				filter.setAmount(Integer.valueOf(txnModel.getAmount().replace(".", "").trim()));
			else{
				addFieldError("txnModel.amount", getText("airtime.amount.invalid"));
			}
		}
		
		if (StringUtils.isNotBlank(txnModel.getStatus())){
			filter.setStatus(txnModel.getStatus());
		}
		
		if (StringUtils.isNotBlank(txnModel.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(txnModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(txnModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(txnModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
		if (StringUtils.isBlank(txnModel.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(txnModel.getPage())){
			page=Integer.valueOf(txnModel.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";
		
		try{
			CdrExtDao dao=new CdrExtDao();
			int recordCount=dao.searchCount(filter);
			Paging<AirtimeTxnModel> paging=new Paging<AirtimeTxnModel>(page, 10, filter.getRecordSize(), recordCount);
			txnModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<CdrExt> list= dao.search(filter);
			List<AirtimeTxnModel> listContent=new ArrayList<AirtimeTxnModel>();
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				AirtimeTxnModel model=new AirtimeTxnModel();
				CdrExt atTxn=list.get(i);
				model.setTxnDate(DateUtil.convertDate2String(atTxn.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setConnType(atTxn.getProviderId());
				model.setAtTxnId(String.valueOf(atTxn.getAtTxnId()));
				model.setMsisdn(atTxn.getMsisdn());
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(atTxn.getAmount())));
				model.setMessageId(atTxn.getMessageId());
				if (StringUtils.isNotBlank(atTxn.getExt3()))
					model.setErrorCode(atTxn.getExt3().trim()+"_"+atTxn.getErrorCode());
				else
					model.setErrorCode(atTxn.getErrorCode());
				
				if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(atTxn.getStatus())){
					model.setStatus(getText("txn.status.error"));
					model.setCurrentStatus(AppConstants.TXN_STATUS_ERROR);
				}else if(AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(atTxn.getStatus())){
						model.setStatus(getText("txn.status.success"));
						model.setCurrentStatus(AppConstants.TXN_STATUS_SUCCESS);
				}
				listContent.add(model);
			}
			paging.setItems(listContent);
			request.setAttribute("pageView", paging);
			System.out.println("**************** "+filter);
		}catch (Exception e) {
			log.error("Fail to search provider account",e);
			addFieldError("sys_message", getText("database.exception"));
		}
		return "success";
	}
	public String exportMobiCdr() throws DaoException { 
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		CdrExtFilter filter=new CdrExtFilter();
		filter.setProviderId(AppConstants.MOBIFONE_SETTINGS.getConnectionType());
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getAtTxnId())){
			if (StringUtils.isNumeric(txnModel.getAtTxnId().trim())&& txnModel.getAtTxnId().trim().length()==18)
				filter.setAtTxnId(Long.valueOf(txnModel.getAtTxnId().trim()));
			else{
				resultMap.put("error", "INVALID_INPUT");
			}
		}
		
		if (StringUtils.isNotBlank(txnModel.getMsisdn()))
			filter.setMsisdn(txnModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(txnModel.getAmount())){
			if (StringUtils.isNumeric(txnModel.getAmount().trim().replace(".", "")))
				filter.setAmount(Integer.valueOf(txnModel.getAmount().replace(".", "").trim()));
			else{
				resultMap.put("error", "INVALID_INPUT");
			}
		}
		
		if (StringUtils.isNotBlank(txnModel.getStatus())){
			filter.setStatus(txnModel.getStatus());
		}
		
		if (StringUtils.isNotBlank(txnModel.getFromDate())){ 
			Date fromDate=DateUtil.convertString2Date(txnModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					resultMap.put("error", "INVALID_INPUT");
					
		}

		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isNotBlank(txnModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(txnModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (toDate!=null){
					filter.setToDate(toDate);
					if (filter.getFromDate()!=null)
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						resultMap.put("error", "INVALID_INPUT"); 
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(), new Date())<0)
						resultMap.put("error", "INVALID_INPUT");
				}else
					resultMap.put("error", "INVALID_INPUT");
		}
		
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			resultMap.put("error", "INVALID_INPUT");
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
//		create file here
		
		String fileName=AppConstants.MOBIFONE_SETTINGS.getFilePrefix()+"_"+DateUtil.convertDate2String(new Date(),"GMT+7","yyyyMMddHHmm")+AppConstants.MOBIFONE_SETTINGS.getSyncFileExtension(); 
		try{
			boolean result= MobifoneUtils.exportCdrFile(filter, request.getRealPath(FeConstant.CDR_TEMP_PATH)+"/"+fileName);
			if (!result){
				resultMap.put("error", "ERROR");
				resultMap.put("errorMsg", "No record found!");
			}else{
				resultMap.put("error", "SUCCESS");
				resultMap.put("fileName", fileName);
			}
		}catch (Exception e) {
			resultMap.put("error", "ERROR");
			resultMap.put("errorMsg", "Fail to create file. System error!");
			log.error("Fail to create file.",e);
		}
		
		System.out.println(resultMap+ " | " + filter);
		return AppUtils.builJsonResult(request, resultMap, "success");
	}
	public AirtimeTxnModel getTxnModel() {
		return txnModel;
	}

	public void setTxnModel(AirtimeTxnModel txnModel) {
		this.txnModel = txnModel;
	}
	
*/	
}
