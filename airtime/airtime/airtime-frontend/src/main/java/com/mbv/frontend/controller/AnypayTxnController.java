package com.mbv.frontend.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.constants.Attributes;
import com.mbv.bp.common.constants.ErrorCode;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.SimInfoDao;
import com.mbv.bp.common.dao.airtime.SimTransactionDao;
import com.mbv.bp.common.executor.ExecutorFactory;
import com.mbv.bp.common.integration.ContextBase;
import com.mbv.bp.common.vo.airtime.SimInfo;
import com.mbv.bp.common.vo.airtime.SimTransaction;
import com.mbv.bp.common.vo.airtime.SimTransactionFilter;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.model.AnypayTxnModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;

public class AnypayTxnController extends ActionControllerBase {
	private Log log=LogFactory.getLog(AnypayTxnController.class);
	private static final long serialVersionUID = 1L;
	private AnypayTxnModel txnModel;
	private static final String ADD_ANYPAY_TRANSFER_WFP="wfpAnypayTransferRequest";

	public String listView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//		clearFieldErrors(); 
		
		
		Map<String, String> simMap=new HashMap<String, String>();
		simMap.put("", "----"+getText("select.all.text")+"----");
		SimInfoDao simInfoDao=new SimInfoDao();
		List<SimInfo> simInfos=simInfoDao.selectAll();
		
		if (simInfos!=null){
			for(SimInfo simInfo:simInfos){
				simMap.put(simInfo.getMsisdn(), simInfo.getMsisdn());
			}
		}
		
		request.setAttribute("simMap", simMap);
		
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put("", "----"+getText("select.all.text")+"----");
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		statusMap.put(AppConstants.TXN_STATUS_PENDING, getText("txn.status.pending"));
		statusMap.put(AppConstants.TXN_STATUS_DELIVERING, getText("txn.status.delivering"));
		request.setAttribute("statusMap", statusMap);
		
		Map<String, String> txnTypeMap=new HashMap<String, String>();
		txnTypeMap.put("", "----"+getText("select.all.text")+"----");
		txnTypeMap.put(AppConstants.ANYPAY_SETTINGS.TOPUP_TXN, AppConstants.ANYPAY_SETTINGS.TOPUP_TXN);
		txnTypeMap.put(AppConstants.ANYPAY_SETTINGS.TRANSFER_TXN, AppConstants.ANYPAY_SETTINGS.TRANSFER_TXN);
		request.setAttribute("txnTypeMap", txnTypeMap);
		
		if (txnModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			txnModel=new AnypayTxnModel();
			txnModel.setSimId("");
			txnModel.setTxnStatus("");
			txnModel.setTxnType("");
			txnModel.setFromDate(defaultDate);
			txnModel.setToDate(defaultDate);
			return "success";
		}

		SimTransactionFilter filter=new SimTransactionFilter();
		if (StringUtils.isNotBlank(txnModel.getTxnType()))
			filter.setTxnType(txnModel.getTxnType().trim());
		else{
			txnModel.setTxnType("");
		}
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getTxnId())){
			if (StringUtils.isNumeric(txnModel.getTxnId().trim())&& txnModel.getTxnId().trim().length()==18)
				filter.setTxnId(Long.valueOf(txnModel.getTxnId().trim()));
			else{
				addFieldError("txnModel.atTxnId", getText("airtime.txn.invalid"));
			}
		}
		
		if (StringUtils.isNotBlank(txnModel.getMsisdn()))
			filter.setMsisdn(txnModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(txnModel.getAmount())){
			if (StringUtils.isNumeric(txnModel.getAmount().trim().replace(".", "")))
				filter.setAmount(Long.valueOf(txnModel.getAmount().replace(".", "").trim()));
			else{
				addFieldError("txnModel.amount", getText("airtime.amount.invalid"));
			}
		}
		
		if (StringUtils.isNotBlank(txnModel.getTxnStatus())){
			filter.setTxnStatus(txnModel.getTxnStatus());
		}else{
			txnModel.setTxnStatus("");
		}
		
		if (StringUtils.isNotBlank(txnModel.getSimId())){
			filter.setSimId(txnModel.getSimId());
		}else{
			txnModel.setSimId("");
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
			SimTransactionDao dao=new SimTransactionDao();
			int recordCount=dao.searchCount(filter);
			Paging<AnypayTxnModel> paging=new Paging<AnypayTxnModel>(page, 10, filter.getRecordSize(), recordCount);
			txnModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<SimTransaction> list= dao.search(filter);
			List<AnypayTxnModel> listContent=new ArrayList<AnypayTxnModel>();
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				AnypayTxnModel model=new AnypayTxnModel();
				SimTransaction txn=list.get(i);
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(txn.getAmount())));
				model.setBilling(txn.getBilling());
				model.setErrorCode(txn.getErrorCode());
				model.setLockAmount(AppUtils.convertStringToCurrency(String.valueOf(txn.getLockAmount())));
				model.setMessageId(txn.getMessageId());
				model.setMsisdn(txn.getMsisdn());
				model.setSimAmount(AppUtils.convertStringToCurrency(String.valueOf(txn.getSimAmount())));
				model.setSimId(txn.getSimId());
				model.setTxnDate(DateUtil.convertDate2String(txn.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setTxnId(String.valueOf(txn.getTxnId()));
				model.setTxnStatus(txn.getTxnStatus());
				model.setTxnType(txn.getTxnType());
				listContent.add(model);
			}
			paging.setItems(listContent);
			request.setAttribute("pageView", paging);
			System.out.println("**************** "+filter);
		}catch (Exception e) {
			log.error("Fail to search sim transaction account",e);
			addFieldError("sys_message", getText("database.exception"));
		}
		return "success";
	}

		
	public String anypayTxnExportCsv() throws DaoException { 
		
		Map<String, String> resultMap=new HashMap<String, String>();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		
		SimTransactionFilter filter=new SimTransactionFilter();
		if (StringUtils.isNotBlank(txnModel.getTxnType()))
			filter.setTxnType(txnModel.getTxnType().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getTxnId())){
			if (StringUtils.isNumeric(txnModel.getTxnId().trim())&& txnModel.getTxnId().trim().length()==18)
				filter.setTxnId(Long.valueOf(txnModel.getTxnId().trim()));
			else{
				resultMap.put("error", "INVALID_INPUT");
			}
		}
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isNotBlank(txnModel.getMsisdn()))
			filter.setMsisdn(txnModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(txnModel.getAmount())){
			if (StringUtils.isNumeric(txnModel.getAmount().trim().replace(".", "")))
				filter.setAmount(Long.valueOf(txnModel.getAmount().replace(".", "").trim()));
			else{
				resultMap.put("error", "INVALID_INPUT");
			}
		}
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isNotBlank(txnModel.getTxnStatus())){
			filter.setTxnStatus(txnModel.getTxnStatus());
		}
		
		if (StringUtils.isNotBlank(txnModel.getSimId())){
			filter.setSimId(txnModel.getSimId());
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
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			resultMap.put("error", "INVALID_INPUT");
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		filter.setRecordSize(FeConstant.MAX_RECORD);
		filter.setStartRecord(0);
		
		
		String fileName="anypay_transaction"+"_"+DateUtil.convertDate2String(new Date(),"GMT+7","yyyyMMddHHmm")+".csv"; 
		try{
			boolean result= com.mbv.bp.common.util.AppUtils.exportAnypayTxn(filter, request.getRealPath(FeConstant.CDR_TEMP_PATH)+"/"+fileName);
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
	
	public String addTransferTxnView() throws DaoException{
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	
		
		Map<String, String> simMap=new HashMap<String, String>();
		simMap.put("", "--------");
		SimInfoDao simInfoDao=new SimInfoDao();
		List<SimInfo> simInfos=simInfoDao.selectAll();
		

		if (simInfos!=null){
			for(SimInfo simInfo:simInfos){
				simMap.put(simInfo.getMsisdn(), simInfo.getComId()+"-"+simInfo.getMsisdn());
			}
		}
		request.setAttribute("simMap", simMap);
		
		if (txnModel==null) txnModel=new AnypayTxnModel();
		
		if (StringUtils.isBlank(txnModel.getSimId()))
			txnModel.setSimId("");
		
		if (StringUtils.isBlank(txnModel.getMsisdn()))
			txnModel.setMsisdn("");
		
		return "success";
	}
	
	public String saveTransferTxn() throws DaoException{
		
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		
		Map<String, String> simMap=new HashMap<String, String>();
		simMap.put("", "--------");
		SimInfoDao simInfoDao=new SimInfoDao();
		List<SimInfo> simInfos=simInfoDao.selectAll();
		
		if (simInfos!=null){
			for(SimInfo simInfo:simInfos){
				simMap.put(simInfo.getMsisdn(), simInfo.getComId()+"-"+simInfo.getMsisdn());
			}
		}
		request.setAttribute("simMap", simMap);
		
		if (txnModel==null) return "success";
		
		if (StringUtils.isBlank(txnModel.getSimId()))
			addFieldError("simId", getText("field.required"));
		
		if (StringUtils.isBlank(txnModel.getMsisdn()))
			addFieldError("msisdn", getText("field.required"));
		if (getFieldErrors().size()>0) 	return "failure";
		if (StringUtils.isNotBlank(txnModel.getAmount())){
			String temp=txnModel.getAmount().trim().replace(".","");
			if (!NumberUtils.isNumber(temp))
				addFieldError("amount", getText("field.numeric.required"));
		}else
			addFieldError("amount", getText("field.required"));
		if (getFieldErrors().size()>0) 	return "failure";
		if (Long.parseLong(txnModel.getAmount().replace(".", ""))<=0) 
			addFieldError("amount", getText("invalid.input.data"));
		if (getFieldErrors().size()>0) 	return "failure";
		
		if (txnModel.getSimId().equalsIgnoreCase(txnModel.getMsisdn()))
			addFieldError("msisdn", getText("fromsim.tosim.invalid"));
		
		if (getFieldErrors().size()>0) 	return "failure";
//		goi workflow for txnId
		ContextBase context=new ContextBase();
		context.put(Attributes.ATTR_ERROR_CODE,ErrorCode.SUCCESS);
		context.put(Attributes.ATTR_WFP_NAME, ADD_ANYPAY_TRANSFER_WFP);
		context.put(Attributes.ATTR_COM_ID, txnModel.getSimId());
		context.put(Attributes.ATTR_MSISDN, txnModel.getMsisdn());
		context.put(Attributes.ATTR_AMOUNT, txnModel.getAmount().trim().replace(".",""));
		try{
			ContextBase result=ExecutorFactory.getInstance().getExecutor(ExecutorFactory.WFP_EXECUTOR).process(context);
			
			if (ErrorCode.SUCCESS.equalsIgnoreCase(result.getErrorCode()))
				addFieldError("sys_message", "Yeu cau chuyen tien tu sim Anypay den Sim Anypay gui toi he thong thanh cong.");
			else{
				addFieldError("sys_message", "Yeu cau chuyen tien tu sim Anypay den Sim Anypay gui toi he thong that bai.| erroCode: "+result.getErrorCode());
				return "failure";
			}
			txnModel.setTxnId(result.getString(Attributes.ATTR_TRANSACTION_ID));
			
		}catch (Exception e) {
			log.error(e);
			addFieldError("sys_message", "He thong loi| errMsg:"+e.getMessage());
			return "failure";
		}
		return "success";
	}
	public String updateView(){
		clearFieldErrors();
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		request.setAttribute("statusMap", statusMap);

		if (txnModel==null) return "success";
		
		if (StringUtils.isBlank(txnModel.getTxnId())) return "success";
		
		if (!StringUtils.isNumeric(txnModel.getTxnId())) return "success";
		
		SimTransactionDao transactionDao=new SimTransactionDao();
		SimTransaction txn=new SimTransaction();
		txn.setTxnId(Long.valueOf(txnModel.getTxnId()));
		try{
			if (!transactionDao.select(txn)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "error";
			}
		}catch (DaoException e) {
			addFieldError("sys_message", getText("database.exception"));
			return "error";
		}
		txnModel.setTxnStatus(txn.getTxnStatus());
		return "success";
	}
	
	
	public String update(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> statusMap=new HashMap<String, String>();
		statusMap.put(AppConstants.TXN_STATUS_ERROR, getText("txn.status.error"));
		statusMap.put(AppConstants.TXN_STATUS_SUCCESS, getText("txn.status.success"));
		request.setAttribute("statusMap", statusMap);
		if (txnModel==null) return "success";
		if (StringUtils.isBlank(txnModel.getTxnId())) return "success";
		if (StringUtils.isBlank(txnModel.getTxnStatus())) return "success";
		if (!StringUtils.isNumeric(txnModel.getTxnId())) return "success";
		
		SimTransactionDao transactionDao=new SimTransactionDao();
		SimTransaction txn=new SimTransaction();
		txn.setTxnId(Long.valueOf(txnModel.getTxnId()));
		try{
			if (!transactionDao.select(txn)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "failure";
			}
		}catch (DaoException e) {
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}
		
		if (!txn.getTxnStatus().equalsIgnoreCase(txnModel.getTxnStatus())){
			SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
			try{
				sqlMapClient.startTransaction();
				long currentAmount=txn.getAmount();
				long currentTxnId=txn.getTxnId();
//				String currentStatus=txn.getTxnStatus();
				String currentTxnType=txn.getTxnType();
				String currentBillingStatus=txn.getBilling();
				String simId=txn.getSimId();
				
				transactionDao=new SimTransactionDao(sqlMapClient);
				SimInfoDao simInfoDao=new SimInfoDao(sqlMapClient);
				
				if (txnModel.getTxnStatus().equalsIgnoreCase(AppConstants.TXN_STATUS_ERROR)){
					txn=new SimTransaction();
					txn.setTxnStatus(txnModel.getTxnStatus());
					txn.setErrorCode("RECONSOLIDATION");
					txn.setBilling(AppConstants.NO_FLAG);
					txn.setTxnId(currentTxnId);
					transactionDao.update(txn);
					if (AppConstants.YES_FLAG.equalsIgnoreCase(currentBillingStatus) && AppConstants.ANYPAY_SETTINGS.TOPUP_TXN.equalsIgnoreCase(currentTxnType)){
//						only pending status
						SimInfo simInfo=new SimInfo();
						simInfo.setMsisdn(simId);
						simInfo.setLockAmount(-1*currentAmount); //plus value
						simInfo.setCurrentAmount(0L); 
						simInfoDao.updateCurrentLockAmountByMsisdn(simInfo);
					}
				}else if(txnModel.getTxnStatus().equalsIgnoreCase(AppConstants.TXN_STATUS_SUCCESS)){
//					only delivering status
					txn=new SimTransaction();
					txn.setTxnStatus(txnModel.getTxnStatus());
					txn.setBilling(AppConstants.YES_FLAG);
					txn.setTxnId(currentTxnId);
					txn.setErrorCode("RECONSOLIDATION");
					transactionDao.update(txn);
					if (AppConstants.ANYPAY_SETTINGS.TOPUP_TXN.equalsIgnoreCase(currentTxnType)){
						if (AppConstants.YES_FLAG.equalsIgnoreCase(currentBillingStatus)){
							SimInfo simInfo=new SimInfo();
							simInfo.setMsisdn(simId);
							simInfo.setLockAmount(-1*currentAmount); //plus value
							simInfo.setCurrentAmount(-1*currentAmount); 
							simInfoDao.updateCurrentLockAmountByMsisdn(simInfo);
						}else if (AppConstants.NO_FLAG.equalsIgnoreCase(currentBillingStatus)){
							SimInfo simInfo=new SimInfo();
							simInfo.setMsisdn(simId);
							simInfo.setLockAmount(0L); //plus value
							simInfo.setCurrentAmount(-1*currentAmount); 
							simInfoDao.updateCurrentLockAmountByMsisdn(simInfo);
						}
					}
				}
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
	
	public AnypayTxnModel getTxnModel() {
		return txnModel;
	}


	public void setTxnModel(AnypayTxnModel txnModel) {
		this.txnModel = txnModel;
	}
	
}
