package com.mbv.frontend.controller;


import java.sql.SQLException;
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
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.dao.airtime.CdrSyncDao;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.dao.airtime.ReservedTxnDao;
import com.mbv.bp.common.dao.airtime.manager.AmountManager;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.AtTransactionFilter;
import com.mbv.bp.common.vo.airtime.CdrSync;
import com.mbv.bp.common.vo.airtime.ReservedTxn;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.generator.IdGeneratorFactory;
import com.mbv.frontend.model.AirtimeTxnModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.ReportUtils;
import com.mbv.frontend.util.DateUtil.Type;

public class AirtimeTxnController extends ActionControllerBase {
	private Log log=LogFactory.getLog(AirtimeTxnController.class);
	private static final long serialVersionUID = 1L;
	private AirtimeTxnModel txnModel;

	public String listView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		providerAccountMap.put("", "----"+getText("select.all.text")+"----");
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);
		
		Map<String, String> telcoMap=new HashMap<String, String>();
		telcoMap.put("", "----"+getText("select.all.text")+"----");
		for(Entry<String, TelcoProvider> entry:AppConstants.TELCO_PROVIDER.entrySet() ){
			TelcoProvider telcoProvider=entry.getValue();
			telcoMap.put(entry.getKey(), telcoProvider.getProviderName());
		}
		request.setAttribute("telcoMap", telcoMap);
		
		
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put("", "----"+getText("select.all.text")+"----");
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
		statusMap.put(AppConstants.TXN_STATUS_UNKNOWN, getText("txn.status.unknown"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		statusMap.put(AppConstants.TXN_STATUS_PENDING, getText("txn.status.pending"));
		
		request.setAttribute("statusMap", statusMap);
		if (txnModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			txnModel=new AirtimeTxnModel();
			txnModel.setFromDate(defaultDate);
			txnModel.setToDate(defaultDate);
			return "success";
		}

		AtTransactionFilter filter=new AtTransactionFilter();
		if (StringUtils.isNotBlank(txnModel.getConnType()))
			filter.setConnType(txnModel.getConnType().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getChannelId()))
			filter.setChannelId(txnModel.getChannelId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getTxnId()))
			filter.setTxnId(txnModel.getTxnId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getTelcoId()))
			filter.setTelcoId(txnModel.getTelcoId().trim());
		
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
			filter.setTxnStatus(txnModel.getStatus());
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
			AtTransactionDao dao=new AtTransactionDao();
			int recordCount=dao.searchCount(filter);
			Paging<AirtimeTxnModel> paging=new Paging<AirtimeTxnModel>(page, 10, filter.getRecordSize(), recordCount);
			txnModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<AtTransaction> list= dao.search(filter);
			List<AirtimeTxnModel> listContent=new ArrayList<AirtimeTxnModel>();
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				AirtimeTxnModel model=new AirtimeTxnModel();
				AtTransaction atTxn=list.get(i);
				model.setTxnDate(DateUtil.convertDate2String(atTxn.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setConnType(atTxn.getConnType());
				model.setTxnId(atTxn.getTxnId());
				model.setAtTxnId(String.valueOf(atTxn.getAtTxnId()));
				model.setChannelId(atTxn.getChannelId());
				model.setTelcoId(atTxn.getTelcoId());
				model.setMsisdn(atTxn.getMsisdn());
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(atTxn.getAmount())));
				model.setMessageId(atTxn.getMessageId());
				model.setErrorCode(atTxn.getErrorCode());
				if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(atTxn.getTxnStatus())){
					model.setStatus(getText("txn.status.error"));
					model.setCurrentStatus(AppConstants.TXN_STATUS_ERROR);
				}else if(AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(atTxn.getTxnStatus())){
						model.setStatus(getText("txn.status.success"));
						model.setCurrentStatus(AppConstants.TXN_STATUS_SUCCESS);
				}else if(AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(atTxn.getTxnStatus())){
					model.setStatus(getText("txn.status.pending"));
					model.setCurrentStatus(AppConstants.TXN_STATUS_PENDING);
				}else if(AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(atTxn.getTxnStatus())){
						model.setStatus(getText("txn.status.unknown"));
						model.setCurrentStatus(AppConstants.TXN_STATUS_UNKNOWN);
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
		statusMap.put(AppConstants.TXN_STATUS_UNKNOWN, getText("txn.status.unknown"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		statusMap.put(AppConstants.TXN_STATUS_PENDING, getText("txn.status.pending"));
		
		request.setAttribute("statusMap", statusMap);
		if (txnModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			txnModel=new AirtimeTxnModel();
			txnModel.setFromDate(defaultDate);
			txnModel.setToDate(defaultDate);
			return "success";
		}

		AtTransactionFilter filter=new AtTransactionFilter();
		filter.setConnType("MOBI");
		
		filter.setStatus(AppConstants.TXN_STATUS_DELIVERED);
		
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
			filter.setTxnStatus(txnModel.getStatus());
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
			AtTransactionDao dao=new AtTransactionDao();
			int recordCount=dao.searchCount(filter);
			Paging<AirtimeTxnModel> paging=new Paging<AirtimeTxnModel>(page, 10, filter.getRecordSize(), recordCount);
			txnModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<AtTransaction> list= dao.search(filter);
			List<AirtimeTxnModel> listContent=new ArrayList<AirtimeTxnModel>();
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				AirtimeTxnModel model=new AirtimeTxnModel();
				AtTransaction atTxn=list.get(i);
				model.setTxnDate(DateUtil.convertDate2String(atTxn.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setConnType(atTxn.getConnType());
				model.setTxnId(atTxn.getTxnId());
				model.setAtTxnId(String.valueOf(atTxn.getAtTxnId()));
				model.setChannelId(atTxn.getChannelId());
				model.setTelcoId(atTxn.getTelcoId());
				model.setMsisdn(atTxn.getMsisdn());
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(atTxn.getAmount())));
				model.setMessageId(atTxn.getMessageId());
				model.setErrorCode(atTxn.getErrorCode());
				if (AppConstants.TXN_STATUS_ERROR.equalsIgnoreCase(atTxn.getTxnStatus())){
					model.setStatus(getText("txn.status.error"));
					model.setCurrentStatus(AppConstants.TXN_STATUS_ERROR);
				}else if(AppConstants.TXN_STATUS_SUCCESS.equalsIgnoreCase(atTxn.getTxnStatus())){
						model.setStatus(getText("txn.status.success"));
						model.setCurrentStatus(AppConstants.TXN_STATUS_SUCCESS);
				}else if(AppConstants.TXN_STATUS_PENDING.equalsIgnoreCase(atTxn.getTxnStatus())){
					model.setStatus(getText("txn.status.pending"));
					model.setCurrentStatus(AppConstants.TXN_STATUS_PENDING);
				}else if(AppConstants.TXN_STATUS_UNKNOWN.equalsIgnoreCase(atTxn.getTxnStatus())){
						model.setStatus(getText("txn.status.unknown"));
						model.setCurrentStatus(AppConstants.TXN_STATUS_UNKNOWN);
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
		AtTransactionFilter filter=new AtTransactionFilter();
		filter.setConnType("MOBI");
		
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
			filter.setTxnStatus(txnModel.getStatus());
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
	*/
	public String listViewRpt() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		AtTransactionFilter filter=new AtTransactionFilter();
		if (StringUtils.isNotBlank(txnModel.getConnType()))
			filter.setConnType(txnModel.getConnType().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getChannelId()))
			filter.setChannelId(txnModel.getChannelId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getTxnId()))
			filter.setTxnId(txnModel.getTxnId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getTelcoId()))
			filter.setTelcoId(txnModel.getTelcoId().trim());
		
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
			filter.setTxnStatus(txnModel.getStatus());
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
		String reportRequest = ReportUtils.createReportHeader(FeConstant.REPORT_TXN_INFO_RPT_ID, FeConstant.REPORT_TXN_INFO_FILENAME, AppUtils.getUserNameLogin(), "5");
		String inputParameters=ReportUtils.txnInfoRptParams(filter);
		String result=ReportUtils.createReport(reportRequest, inputParameters);
		if (result.equalsIgnoreCase("ERROR"))
			resultMap.put("error", "ERROR");
		else{
			resultMap.put("reportId", result);
			resultMap.put("error", "SUCCESS");
		}
		System.out.println(result+" "+filter);
		return AppUtils.builJsonResult(request, resultMap, "success");
	}
	
	public String updateView(){
		clearFieldErrors();
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
//		statusMap.put(AppConstants.TXN_STATUS_UNKNOWN, getText("txn.status.unknown"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		request.setAttribute("statusMap", statusMap);
		if (txnModel==null) return "success";
		
		if (StringUtils.isBlank(txnModel.getAtTxnId())) return "success";
		
		if (!StringUtils.isNumeric(txnModel.getAtTxnId())) return "success";
		
		AtTransactionDao transactionDao=new AtTransactionDao();
		AtTransaction atTxn=new AtTransaction();
		atTxn.setAtTxnId(Long.valueOf(txnModel.getAtTxnId()));
		try{
			if (!transactionDao.selectByAtTxnId(atTxn)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "error";
			}
		}catch (DaoException e) {
			addFieldError("sys_message", getText("database.exception"));
			return "error";
		}
		
		txnModel.setStatus(atTxn.getTxnStatus());
		return "success";
	}
	
	public String update(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		request.setAttribute("statusMap", statusMap);
		if (txnModel==null) return "success";
		if (StringUtils.isBlank(txnModel.getAtTxnId())) return "success";
		if (StringUtils.isBlank(txnModel.getStatus())) return "success";
		if (!StringUtils.isNumeric(txnModel.getAtTxnId())) return "success";
		
		AtTransactionDao transactionDao=new AtTransactionDao();
		AtTransaction atTxn=new AtTransaction();
		atTxn.setAtTxnId(Long.valueOf(txnModel.getAtTxnId()));
		try{
			if (!transactionDao.selectByAtTxnId(atTxn)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "failure";
			}
		}catch (DaoException e) {
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}
		
		if (!atTxn.getTxnStatus().equalsIgnoreCase(txnModel.getStatus())){
			SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
			try{
				sqlMapClient.startTransaction();
				AtTransactionDao atTransactionDao=new AtTransactionDao(sqlMapClient);
				CdrSyncDao syncDao=new CdrSyncDao(sqlMapClient);
				AmountManager amountManager=new AmountManager();
				AtTransaction transactionOld=new AtTransaction();
				transactionOld.setAtTxnId(Long.valueOf(txnModel.getAtTxnId()));
				transactionOld.setDeleted(Long.valueOf(IdGeneratorFactory.getInstance().getFeIdGenerator().generateId()));
				atTransactionDao.terminatedTxn(transactionOld);
//				update new status
				atTxn.setTxnStatus(txnModel.getStatus());
				atTxn.setUpdatedBy(FeConstant.MODULE);
				atTxn.setUpdatedDate(new Date());
				atTransactionDao.insert(atTxn);
//				update sync table
				CdrSync cdrSync=new CdrSync();
				cdrSync.setAtTxnId(atTxn.getAtTxnId());
				cdrSync.setProviderId(atTxn.getConnType());
				cdrSync.setMessageId(atTxn.getMessageId());
				cdrSync.setStatus(atTxn.getTxnStatus());
				
				if (AppConstants.MOBIFONE_SETTINGS.getConnectionType().equalsIgnoreCase(cdrSync.getProviderId()))
					syncDao.updateStatusByMessageIdAndProviderId(cdrSync);
				else if (AppConstants.VIETPAY_SETTINGS.getConnectionType().equalsIgnoreCase(cdrSync.getProviderId()))
					syncDao.updateStatusByAtTxnIdAndProviderId(cdrSync);
				
				int currentAmount=atTxn.getAmount();
				ReservedTxnDao reservedTxnDao=new ReservedTxnDao(sqlMapClient); 
				ReservedTxn reservedTxn=new ReservedTxn();
				reservedTxn.setTxnId(""+atTxn.getAtTxnId());
				boolean isReservedTxn=reservedTxnDao.select(reservedTxn);
				String feTxnId="";
				
				if (isReservedTxn){
					feTxnId=FeConstant.MODULE+":"+IdGeneratorFactory.getInstance().getFeIdGenerator().generateId();
				}
				
				if (txnModel.getStatus().equalsIgnoreCase(AppConstants.TXN_STATUS_ERROR)){
					currentAmount=0-currentAmount;
					if (isReservedTxn)
						amountManager.debitLockAccount(sqlMapClient, AppConstants.CHANGE_TXN_STATUS_OPERATION, feTxnId, FeConstant.MODULE, reservedTxn.getAccountId(), ""+atTxn.getAtTxnId(), atTxn.getAmount(), "ChangeStatus(SUCCESS->ERROR)");
				}else{
					if (isReservedTxn)
						amountManager.creditLockAccount(sqlMapClient, AppConstants.CHANGE_TXN_STATUS_OPERATION, feTxnId, FeConstant.MODULE, reservedTxn.getAccountId(), ""+atTxn.getAtTxnId(), atTxn.getAmount(), "ChangeStatus(ERROR->SUCCESS)");
				}
				ProviderAmountDao providerAmountDao=new ProviderAmountDao(sqlMapClient);
				providerAmountDao.updateUsedAmount(atTxn.getConnType(), currentAmount);
		
				sqlMapClient.commitTransaction();
				return "success";
			}catch (Exception e) {
				addFieldError("sys_message", getText("database.exception"));
				log.error(e.getMessage(),e);
				return "failure";
			}finally{
				try {
					sqlMapClient.endTransaction();
				} catch (SQLException e1) {
					log.error("Fail to rollback to Database",e1);
				}
			}
		}
		return "success";
	}
	
	public AirtimeTxnModel getTxnModel() {
		return txnModel;
	}

	public void setTxnModel(AirtimeTxnModel txnModel) {
		this.txnModel = txnModel;
	}
	
	
}
