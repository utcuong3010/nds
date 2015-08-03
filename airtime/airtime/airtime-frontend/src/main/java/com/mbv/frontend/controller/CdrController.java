package com.mbv.frontend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.dao.airtime.CdrSyncDao;
import com.mbv.bp.common.vo.airtime.CdrSync;
import com.mbv.bp.common.vo.airtime.CdrSyncFilter;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.model.CdrDataModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;

public class CdrController extends ActionControllerBase {
	private Log log=LogFactory.getLog(CdrController.class);
	private static final long serialVersionUID = 1L;
	private CdrDataModel cdrDataModel;
	private static final String MODULE="cdrsync";
	private static final String TYPE="cacher";
	private static final String KEY="vietpay.last-import-date";
	private static final String KEY_VIETPAY_LAST_AT_TXN="vietpay.last-at_txn_id";
	private static final String KEY_MOBI_LAST_AT_TXN="mobifone.last-at_txn_id";
	public String compareListView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equals(entry.getKey())||
					AppConstants.VIETPAY_SETTINGS.getConnectionType().equals(entry.getKey())
				)
					providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);
		
		Map<String, String> resultMap=new HashMap<String, String>();
		resultMap.put("", "----"+getText("select.all.text")+"----");
		resultMap.put(AppConstants.TXN_STATUS_MATCHED, getText("txn.status.matched"));
		resultMap.put(AppConstants.TXN_STATUS_UNMATCHED, getText("txn.status.unmatched"));
		request.setAttribute("resultMap", resultMap);
		
		
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put("", "----"+getText("select.all.text")+"----");
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success")+"(OK)");
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error")+"(FAIL)");
		request.setAttribute("statusMap", statusMap);
		
		
		if (cdrDataModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			cdrDataModel=new CdrDataModel();
			cdrDataModel.setFromDate(defaultDate);
			cdrDataModel.setToDate(defaultDate);
			return "success";
		}

		CdrSyncFilter filter=new CdrSyncFilter();
		
		if (StringUtils.isNotBlank(cdrDataModel.getProviderId()))
			filter.setProviderId(cdrDataModel.getProviderId().trim());
		
		if (StringUtils.isNotBlank(cdrDataModel.getPtxnId()))
			filter.setpTxnId(cdrDataModel.getPtxnId().trim());
		
		if (StringUtils.isNotBlank(cdrDataModel.getAtTxnId())){
			if (StringUtils.isNumeric(cdrDataModel.getAtTxnId().trim())&& cdrDataModel.getAtTxnId().trim().length()==18)
				filter.setAtTxnId(Long.valueOf(cdrDataModel.getAtTxnId().trim()));
			else{
				addFieldError("cdrDataModel.atTxnId", getText("airtime.txn.invalid"));
			}
		}
		
		if (StringUtils.isNotBlank(cdrDataModel.getMsisdn()))
			filter.setMsisdn(cdrDataModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(cdrDataModel.getAmount())){
			if (StringUtils.isNumeric(cdrDataModel.getAmount().trim().replace(".", "")))
				filter.setAmount(Integer.valueOf(cdrDataModel.getAmount().replace(".", "").trim()));
			else{
				addFieldError("cdrDataModel.amount", getText("airtime.amount.invalid"));
			}
		}
		
		if (StringUtils.isNotBlank(cdrDataModel.getStatus())){
			filter.setStatus(cdrDataModel.getStatus());
		}
		
		if (StringUtils.isNotBlank(cdrDataModel.getResult())){
			filter.setFilterOperation(cdrDataModel.getResult());
		}
		
		if (StringUtils.isNotBlank(cdrDataModel.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(cdrDataModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(cdrDataModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(cdrDataModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
		
		String key=null;
		if (AppConstants.VIETPAY_SETTINGS.getConnectionType().equalsIgnoreCase(filter.getProviderId()))
			key=KEY_VIETPAY_LAST_AT_TXN;
		if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equalsIgnoreCase(filter.getProviderId()))
			key=KEY_MOBI_LAST_AT_TXN;
		
		AirtimeConfigDao configDao=new AirtimeConfigDao();
		try {
			filter.setMaxAtTxnId(new Long(configDao.getValue(MODULE, TYPE, key)));
			filter.setMinAtTxnId(0L);
		} catch (Exception e1) {
			filter.setMaxAtTxnId(null);
		}
		
		
		filter.setRecordSize(FeConstant.MAX_RECORD);
		int page=1;
		if (StringUtils.isBlank(cdrDataModel.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(cdrDataModel.getPage())){
			page=Integer.valueOf(cdrDataModel.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";

		try{
			CdrSyncDao dao=new CdrSyncDao();
			int recordCount=dao.searchCount(filter);
			Paging<CdrDataModel> paging=new Paging<CdrDataModel>(page, 10, filter.getRecordSize(), recordCount);
			cdrDataModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<CdrSync> list= dao.search(filter);
			List<CdrDataModel> listContent=new ArrayList<CdrDataModel>();
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				CdrDataModel model=new CdrDataModel();
				CdrSync atTxn=list.get(i);
				model.setTxnDate(DateUtil.convertDate2String(atTxn.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setProviderId(atTxn.getProviderId());
				model.setAtTxnId(String.valueOf(atTxn.getAtTxnId()));
				model.setMsisdn(atTxn.getMsisdn());
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(atTxn.getAmount())));
				if (atTxn.getpPrice()!=null)
					model.setPrice(AppUtils.convertStringToCurrency(String.valueOf(atTxn.getpPrice())));
				model.setMessageId(atTxn.getMessageId());
				model.setErrorCode(atTxn.getErrorCode());
				model.setPerrorCode(atTxn.getpErrorCode());
				if (StringUtils.equals(atTxn.getStatus(), atTxn.getpStatus()))
					model.setResult(getText("txn.status.matched"));
				else
					model.setResult(getText("txn.status.unmatched"));
				
				
				
				model.setStatus(atTxn.getStatus());
				model.setPstatus(atTxn.getpStatus());
				model.setPtxnId(atTxn.getpTxnId());
				
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
	public String compareExportData() throws DaoException { 
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		CdrSyncFilter filter=new CdrSyncFilter();
		
		if (StringUtils.isNotBlank(cdrDataModel.getProviderId()))
			filter.setProviderId(cdrDataModel.getProviderId().trim());
		
		if (StringUtils.isNotBlank(cdrDataModel.getPtxnId()))
			filter.setMessageId(cdrDataModel.getPtxnId().trim());
		
		if (StringUtils.isNotBlank(cdrDataModel.getAtTxnId())){
			if (StringUtils.isNumeric(cdrDataModel.getAtTxnId().trim())&& cdrDataModel.getAtTxnId().trim().length()==18)
				filter.setAtTxnId(Long.valueOf(cdrDataModel.getAtTxnId().trim()));
			else{
				resultMap.put("error", "INVALID_INPUT");
			}
		}
		
		if (StringUtils.isNotBlank(cdrDataModel.getMsisdn()))
			filter.setMsisdn(cdrDataModel.getMsisdn().trim());
		
		
		if (StringUtils.isNotBlank(cdrDataModel.getResult())){
			filter.setFilterOperation(cdrDataModel.getResult());
		}
		
		if (StringUtils.isNotBlank(cdrDataModel.getFromDate())){ 
			Date fromDate=DateUtil.convertString2Date(cdrDataModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					resultMap.put("error", "INVALID_INPUT");
					
		}

		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isNotBlank(cdrDataModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(cdrDataModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
		
		String key=null;
		if (AppConstants.VIETPAY_SETTINGS.getConnectionType().equalsIgnoreCase(filter.getProviderId()))
			key=KEY_VIETPAY_LAST_AT_TXN;
		if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equalsIgnoreCase(filter.getProviderId()))
			key=KEY_MOBI_LAST_AT_TXN;
		
		AirtimeConfigDao configDao=new AirtimeConfigDao();
		try {
			filter.setMaxAtTxnId(new Long(configDao.getValue(MODULE, TYPE, key)));
			filter.setMinAtTxnId(0L);
		} catch (Exception e1) {
			filter.setMaxAtTxnId(null);
		}
		

		String fileName="cdr_comparison"+"_"+DateUtil.convertDate2String(new Date(),"GMT+7","yyyyMMddHHmm")+".csv"; 
		try{
			boolean result= com.mbv.bp.common.util.AppUtils.exportCdrFile(filter, request.getRealPath(FeConstant.CDR_TEMP_PATH)+"/"+fileName);
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
	public CdrDataModel getcdrDataModel() {
		return cdrDataModel;
	}

	public void setcdrDataModel(CdrDataModel cdrDataModel) {
		this.cdrDataModel = cdrDataModel;
	}
	
	
}
