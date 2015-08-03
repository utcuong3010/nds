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
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.mbv.bp.common.config.SqlConfig;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtSummaryViewDao;
import com.mbv.bp.common.dao.airtime.ProviderAccountDao;
import com.mbv.bp.common.dao.airtime.ProviderAmountDao;
import com.mbv.bp.common.dao.airtime.ReservedTelcoDao;
import com.mbv.bp.common.model.TelcoProvider;
import com.mbv.bp.common.vo.airtime.AtSummaryFilter;
import com.mbv.bp.common.vo.airtime.AtSummaryView;
import com.mbv.bp.common.vo.airtime.AtSummaryViewFilter;
import com.mbv.bp.common.vo.airtime.ProviderAccount;
import com.mbv.bp.common.vo.airtime.ProviderAccountFilter;
import com.mbv.bp.common.vo.airtime.ProviderAmount;
import com.mbv.bp.common.vo.airtime.ReservedTelco;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.generator.IdGeneratorFactory;
import com.mbv.frontend.model.AtSummaryModel;
import com.mbv.frontend.model.LockTelcoInfoModel;
import com.mbv.frontend.model.ProviderAccountModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.ReportUtils;
import com.mbv.frontend.util.DateUtil.Type;


public class ProviderAccountController extends ActionControllerBase {
	private Log log=LogFactory.getLog(ProviderAccountController.class);
	private static final long serialVersionUID = 1L;
	private ProviderAccountModel proAcc;
	
	public String listView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		
		List<AtSummaryModel> listAmountModel=new ArrayList<AtSummaryModel>();  
		try{
			ProviderAmountDao providerAmountDao=new ProviderAmountDao();
			List<ProviderAmount> listCurProviderAmounts=providerAmountDao.selectAll();
			if (listCurProviderAmounts!=null){
				long totalAmount;
				for(int i=0;i<listCurProviderAmounts.size();i++){
					AtSummaryModel summaryModel=new AtSummaryModel();
					ProviderAmount view=listCurProviderAmounts.get(i);
					summaryModel.setEndAmount(AppUtils.convertStringToCurrency(String.valueOf(view.getTotalLoaded()-view.getTotalUsed())));
					summaryModel.setTxnDate("");
					summaryModel.setProviderId(view.getProviderId());
					listAmountModel.add(summaryModel);
				}
			}
			
		}catch (Exception e) {
			log.error(e);
		}
		List<LockTelcoInfoModel> listAllProductInfos=getLockTelcoInfo();
		List<LockTelcoInfoModel> listLockTelcoProduct=new ArrayList<LockTelcoInfoModel>();
		List<LockTelcoInfoModel> listLockGameProduct=new ArrayList<LockTelcoInfoModel>();
		
		TelcoProvider productProvider;
		for (LockTelcoInfoModel infoModel:listAllProductInfos){
			productProvider= AppConstants.TELCO_PROVIDER.get(infoModel.getTelcoId());
			if (productProvider!=null){
				if (AppConstants.GAME_GROUP_CODE.equals(productProvider.getGroup()))
					listLockGameProduct.add(infoModel);
				if (AppConstants.TELCO_GROUP_CODE.equals(productProvider.getGroup()))
					listLockTelcoProduct.add(infoModel);

			} 
		}
		request.setAttribute("listLockTelcoProduct", listLockTelcoProduct);
		request.setAttribute("listLockGameProduct", listLockGameProduct);
		request.setAttribute("listAmountModel", listAmountModel);
		
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		providerAccountMap.put("", "----"+getText("select.all.text")+"----");
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);

		List<ProviderAccountModel> listContent=new ArrayList<ProviderAccountModel>();
		request.setAttribute("listContent", listContent);
		if (proAcc==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			proAcc=new ProviderAccountModel();
			proAcc.setFromDate(defaultDate);
			proAcc.setToDate(defaultDate);
			return "success";
		}

		ProviderAccountFilter filter=new ProviderAccountFilter();
		if (StringUtils.isNotBlank(proAcc.getProviderId()))
			filter.setProviderId(proAcc.getProviderId().trim());
		if (StringUtils.isNotBlank(proAcc.getEmployee()))
			filter.setEmployee(proAcc.getEmployee());
		if (StringUtils.isNotBlank(proAcc.getTxnId()))
			filter.setTxnId(proAcc.getTxnId().trim());

		if (StringUtils.isNotBlank(proAcc.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(proAcc.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(proAcc.getToDate())){
			Date toDate=DateUtil.convertString2Date(proAcc.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
		
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			addFieldError("toDate", getText("dates.input.not.enough"));
		
		if (getFieldErrors().size()>0) return "success";
		
		filter.setRecordSize(FeConstant.MAX_RECORD);
		int page=1;
		if (StringUtils.isBlank(proAcc.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(proAcc.getPage())){
			page=Integer.valueOf(proAcc.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";

		try{
			ProviderAccountDao dao=new ProviderAccountDao();
			int recordCount=dao.searchCount(filter);
			Paging<ProviderAccountModel> paging=new Paging<ProviderAccountModel>(page, 10, filter.getRecordSize(), recordCount);
			proAcc.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<ProviderAccount> list= dao.search(filter);
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				ProviderAccountModel model=new ProviderAccountModel();
				ProviderAccount account=list.get(i);
				model.setTxnDate(DateUtil.convertDate2String(account.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setProviderId(account.getProviderId());
				model.setTxnId(account.getTxnId());
				model.setInputAmount(AppUtils.convertStringToCurrency(String.valueOf(account.getInputAmount())));
				model.setDiscount(account.getDiscount());
				model.setTotalAmount(AppUtils.convertStringToCurrency(String.valueOf(account.getTotalAmount())));
				model.setEmployee(account.getEmployee());
				model.setDescription(account.getDescription());
				listContent.add(model);
			}
			paging.setItems(listContent);
			request.setAttribute("pageView", paging);
//			System.out.println("**************** "+filter);
		}catch (Exception e) {
			log.error("Fail to search provider account",e);
			addFieldError("sys_message", getText("database.exception"));
		}
		return "success";
	}
	
	public String listViewRpt(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		ProviderAccountFilter filter=new ProviderAccountFilter();
		if (StringUtils.isNotBlank(proAcc.getProviderId()))
			filter.setProviderId(proAcc.getProviderId().trim());
		if (StringUtils.isNotBlank(proAcc.getEmployee()))
			filter.setEmployee(proAcc.getEmployee());
		if (StringUtils.isNotBlank(proAcc.getTxnId()))
			filter.setTxnId(proAcc.getTxnId().trim());

		if (StringUtils.isNotBlank(proAcc.getFromDate())){ 
			Date fromDate=DateUtil.convertString2Date(proAcc.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
		
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					resultMap.put("error", "INVALID_INPUT");
		}

		if (resultMap.size()>0) {
			return AppUtils.builJsonResult(request, resultMap, "success");
		}
		
		if (StringUtils.isNotBlank(proAcc.getToDate())){ 
			Date toDate=DateUtil.convertString2Date(proAcc.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
		if (resultMap.size()>0) {
			return AppUtils.builJsonResult(request, resultMap, "success");
		}
		
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			resultMap.put("error", "INVALID_INPUT");
		
		if (resultMap.size()>0) {
			return AppUtils.builJsonResult(request, resultMap, "success");
		}
				
		String reportRequest = ReportUtils.createReportHeader(FeConstant.REPORT_PROVIDER_ACCOUNT_RPT_ID, FeConstant.REPORT_PROVIDER_ACCOUNT_FILENAME, AppUtils.getUserNameLogin(), "5");
		String inputParameters=ReportUtils.providerAmountRptParams(filter);
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
	
	public String addView() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);
		return "success";
	}
	public String updateView() {
//		sys_message
		clearFieldErrors();
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);
		
		ProviderAccountDao accountDao=new ProviderAccountDao();
		ProviderAccount providerAccount=new ProviderAccount();
		if (proAcc==null){
			addFieldError("sys_message", getText("data.not.existed"));
			return "error";
		}
		
		if (StringUtils.isBlank(proAcc.getTxnId())){
			addFieldError("sys_message", getText("data.not.existed"));
			return "error";
		}
		
		try{
			providerAccount.setTxnId(proAcc.getTxnId());
		    if (!accountDao.select(providerAccount)){
		    	addFieldError("sys_message", getText("data.not.existed"));
		    	return "error";
		    }
		}catch (Exception e) {
			addFieldError("sys_message", getText("database.exception"));
	    	return "error";
		}
		proAcc=convertVotoModel(providerAccount);
		proAcc.setOldTxnId(proAcc.getTxnId());
		return "success";
	}
	public String update() {
		clearFieldErrors();
		ProviderAccount account=new ProviderAccount();
		ProviderAccount oldAccount=new ProviderAccount();
		String temp;
		addView();
		if (proAcc==null) return "failure";
		
		if (StringUtils.isBlank(proAcc.getOldTxnId())) {
			addFieldError("sys_message", getText("data.not.existed"));
			return "failure";
		}
		
		try{
			ProviderAccountDao dao=new ProviderAccountDao();
			oldAccount.setTxnId(proAcc.getOldTxnId().trim());
			if (!dao.select(oldAccount)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "failure";
			}
		}catch (Exception e) {
			log.error("Fail to select to Database",e);
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}
		
		
//txnId
		if (StringUtils.isNotBlank(proAcc.getTxnId())){
			proAcc.setTxnId(proAcc.getTxnId().trim());
			account.setTxnId(proAcc.getTxnId());
			if (!oldAccount.getTxnId().equalsIgnoreCase(account.getTxnId())) 
			try{
				ProviderAccountDao dao=new ProviderAccountDao();
				if (dao.select(account)){
					addFieldError("txnId", getText("data.existed"));
					return "failure";
				}
			}catch (Exception e) {
				log.error("Fail to Insert to Database",e);
				addFieldError("sys_message", getText("database.exception"));
				return "failure";
			}
		}else
			addFieldError("txnId", getText("field.required"));
		
		if (getFieldErrors().size()>0) 	return "failure";
		
		if(!StringUtils.isAlphanumeric(proAcc.getTxnId().trim()))
			addFieldError("txnId", getText("field.alpha.numeric.required"));
		
		if (getFieldErrors().size()>0) 	return "failure";
//provider id
		if (StringUtils.isNotBlank(proAcc.getProviderId())){
			account.setProviderId(proAcc.getProviderId().trim());
		}else
			addFieldError("providerId", getText("field.required"));
//inputAmount
		if (StringUtils.isNotBlank(proAcc.getInputAmount())){
			temp=proAcc.getInputAmount().replace(".","");
			if (NumberUtils.isNumber(temp))
				account.setInputAmount(Long.valueOf(temp));
			else
				addFieldError("inputAmount", getText("field.numeric.required"));
		}else
			addFieldError("inputAmount", getText("field.required"));
// discount
		if (StringUtils.isNotBlank(proAcc.getDiscount())){
			account.setDiscount(proAcc.getDiscount().trim());
		}else
			addFieldError("discount", getText("field.required"));

//totalAmount
		if (StringUtils.isNotBlank(proAcc.getTotalAmount())){
			temp=proAcc.getTotalAmount().replace(".","");
			if (NumberUtils.isNumber(temp))
				account.setTotalAmount(Long.valueOf(temp));
			else
				addFieldError("totalAmount", getText("field.numeric.required"));
		}else
			addFieldError("totalAmount", getText("field.required"));
//txnDate
		if (StringUtils.isNotBlank(proAcc.getTxnDate())){
			Date date=DateUtil.convertString2Date(proAcc.getTxnDate().trim(),"GMT+7","dd/MM/yyyy HH:mm");
			if (date==null)
				addFieldError("txnDate", getText("field.date.type.invalid"));
			else{
				account.setTxnDate(date);
				if (DateUtil.dateDiff(Type.BY_MILLISECOND, account.getTxnDate(), new Date())<0)
				addFieldError("txnDate", getText("txndate.greater.curdate"));
			}
		}else
			addFieldError("txnDate", getText("field.required"));  
//employee
		if (StringUtils.isNotBlank(proAcc.getEmployee())){
			account.setEmployee(proAcc.getEmployee().trim());
		}else
			addFieldError("employee", getText("field.required"));

//description
		if (StringUtils.isNotBlank(proAcc.getDescription())){
			if (proAcc.getDescription().trim().length()>100)
				addFieldError("description", getText("field.too.long"));
			else
				account.setDescription(proAcc.getDescription().trim());
		}

		if (getFieldErrors().size()>0)
			return "failure";
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
		try{
//			long amountChange=account.getTotalAmount()-oldAccount.getTotalAmount();
			sqlMapClient.startTransaction();
			ProviderAccountDao dao=new ProviderAccountDao(sqlMapClient);
			ProviderAmountDao amountDao=new ProviderAmountDao(sqlMapClient);
			
//			update for delete

			oldAccount.setDeleted(Long.valueOf(IdGeneratorFactory.getInstance().getFeIdGenerator().generateId()));
			try{
				oldAccount.setUpdatedBy(AppUtils.getUserLogin().getUsername());
			}catch (Exception e) {
				oldAccount.setUpdatedBy("unknown");
			}
			oldAccount.setUpdatedDate(new Date());	
			dao.updateDeleted(oldAccount);
			amountDao.updateLoadedAmount(oldAccount.getProviderId(), -1*oldAccount.getTotalAmount());
//			insert new record
			
			account.setCreatedBy(oldAccount.getCreatedBy());
			if( StringUtils.isBlank(account.getCreatedBy())) account.setCreatedBy("unknown");
			account.setCreatedDate(oldAccount.getCreatedDate());	
			account.setUpdatedBy(oldAccount.getUpdatedBy());
			account.setUpdatedDate(oldAccount.getUpdatedDate());	
			dao.insert(account);
			
			amountDao.updateLoadedAmount(account.getProviderId(), account.getTotalAmount());
			
			addFieldError("sys_message", getText("data.insert.successful"));
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error("Fail to Insert to Database",e);
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}finally{
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e1) {
				log.error("Fail to rollback to Database",e1);
			}
		}

		return "success";
	}
	public String save() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		ProviderAccount account=new ProviderAccount();
		ProviderAccountDao dao=new ProviderAccountDao();
		String temp;
		addView();

		if (proAcc==null) return "failure";
//
		if (StringUtils.isNotBlank(proAcc.getProviderId())){
			account.setProviderId(proAcc.getProviderId().trim());
		}else
			addFieldError("providerId", getText("field.required"));
//txnId
		if (StringUtils.isNotBlank(proAcc.getTxnId())){
			try{
				account.setTxnId(proAcc.getTxnId().trim());
				if (dao.select(account)){
					addFieldError("txnId", getText("data.existed"));
				}else
					account.setTxnId(proAcc.getTxnId().trim());
			}catch (Exception e) {
				log.error("Fail to Insert to Database",e);
				addFieldError("sys_message", getText("database.exception"));
				return "failure";
			}
		}else
			addFieldError("txnId", getText("field.required"));
		
		if (getFieldErrors().size()>0) 	return "failure";
		
		if(!StringUtils.isAlphanumeric(proAcc.getTxnId().trim()))
			addFieldError("txnId", getText("field.alpha.numeric.required"));
		
		if (getFieldErrors().size()>0) 	return "failure";
//inputAmount
		if (StringUtils.isNotBlank(proAcc.getInputAmount())){
			temp=proAcc.getInputAmount().replace(".","");
			if (NumberUtils.isNumber(temp))
				account.setInputAmount(Long.valueOf(temp));
			else
				addFieldError("inputAmount", getText("field.numeric.required"));
		}else
			addFieldError("inputAmount", getText("field.required"));
// discount
		if (StringUtils.isNotBlank(proAcc.getDiscount())){
			account.setDiscount(proAcc.getDiscount().trim());
		}else
			addFieldError("discount", getText("field.required"));

//totalAmount
		if (StringUtils.isNotBlank(proAcc.getTotalAmount())){
			temp=proAcc.getTotalAmount().replace(".","");
			if (NumberUtils.isNumber(temp))
				account.setTotalAmount(Long.valueOf(temp));
			else
				addFieldError("totalAmount", getText("field.numeric.required"));
		}else
			addFieldError("totalAmount", getText("field.required"));
//txnDate
		if (StringUtils.isNotBlank(proAcc.getTxnDate())){
			Date date=DateUtil.convertString2Date(proAcc.getTxnDate().trim(),"GMT+7","dd/MM/yyyy HH:mm");
			if (date==null)
				addFieldError("txnDate", getText("field.date.type.invalid"));
			else{
				account.setTxnDate(date);
				if (DateUtil.dateDiff(Type.BY_MILLISECOND, account.getTxnDate(), new Date())<0)
				addFieldError("txnDate", getText("txndate.greater.curdate"));
			}
		}else
			addFieldError("txnDate", getText("field.required"));  
//employee
		if (StringUtils.isNotBlank(proAcc.getEmployee())){
			account.setEmployee(proAcc.getEmployee().trim());
		}else
			addFieldError("employee", getText("field.required"));

//description
		if (StringUtils.isNotBlank(proAcc.getDescription())){
			if (proAcc.getDescription().trim().length()>100)
				addFieldError("description", getText("field.too.long"));
			else
				account.setDescription(proAcc.getDescription().trim());
		}

		if (getFieldErrors().size()>0)
			return "failure";
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
		try{
			sqlMapClient.startTransaction();
			ProviderAccountDao providerAccountDao=new ProviderAccountDao(sqlMapClient);
			ProviderAmountDao amountDao=new ProviderAmountDao(sqlMapClient);
			account.setCreatedBy(AppUtils.getUserLogin().getUsername());
			if( StringUtils.isBlank(account.getCreatedBy())) account.setCreatedBy("unknown");
			account.setCreatedDate(new Date());
			
			providerAccountDao.insert(account);
			amountDao.updateLoadedAmount(account.getProviderId(), account.getTotalAmount());
			addFieldError("sys_message", getText("data.insert.successful"));
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error("Fail to Insert to Database",e);
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}finally{
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e1) {
				log.error("Fail to rollback to Database",e1);
			}
		}

		return "success";
	}

	
	public String delete() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		if (proAcc==null) return "success";
		if (StringUtils.isBlank(proAcc.getTxnId())) return "success";
		ProviderAccount providerAccount=new ProviderAccount();
		providerAccount.setTxnId(proAcc.getTxnId().trim());
		try{
			ProviderAccountDao dao=new ProviderAccountDao();
			if (!dao.select(providerAccount)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "failure";
			}
		}catch (Exception e) {
			log.error("Fail to select to Database",e);
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
		try{
			sqlMapClient.startTransaction();
			ProviderAccountDao providerAccountDao=new ProviderAccountDao(sqlMapClient);
			ProviderAmountDao amountDao=new ProviderAmountDao(sqlMapClient);
			providerAccount.setDeleted(Long.valueOf(IdGeneratorFactory.getInstance().getFeIdGenerator().generateId()));
			providerAccount.setUpdatedBy(AppUtils.getUserLogin().getUsername());
			providerAccount.setUpdatedDate(new Date());	
			providerAccountDao.updateDeleted(providerAccount);
			amountDao.updateLoadedAmount(providerAccount.getProviderId(), -1*providerAccount.getTotalAmount());
			addFieldError("sys_message", getText("data.insert.successful"));
			sqlMapClient.commitTransaction();
		}catch (Exception e) {
			log.error("Fail to Insert to Database",e);
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}finally{
			try {
				sqlMapClient.endTransaction();
			} catch (SQLException e1) {
				log.error("Fail to rollback to Database",e1);
			}
		}
		return "success";
	}
	
	public String listAccountSummaryView() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		providerAccountMap.put("", "----"+getText("select.all.text")+"----");
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);
		List<AtSummaryModel> listContent=new ArrayList<AtSummaryModel>();
		List<AtSummaryModel> listAmountModel=new ArrayList<AtSummaryModel>();  
		try{
			ProviderAmountDao providerAmountDao=new ProviderAmountDao();
			List<ProviderAmount> listCurProviderAmounts=providerAmountDao.selectAll();
			
			if (listCurProviderAmounts!=null){
				long totalAmount;
				for(int i=0;i<listCurProviderAmounts.size();i++){
					AtSummaryModel summaryModel=new AtSummaryModel();
					ProviderAmount view=listCurProviderAmounts.get(i);
					summaryModel.setEndAmount(AppUtils.convertStringToCurrency(String.valueOf(view.getTotalLoaded()-view.getTotalUsed())));
					summaryModel.setTxnDate("");
					summaryModel.setProviderId(view.getProviderId());
					listAmountModel.add(summaryModel);
				}
			}
			
		}catch (Exception e) {
			log.error(e);
		}
		request.setAttribute("listAmountModel", listAmountModel);
		if (proAcc==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			proAcc=new ProviderAccountModel();
			proAcc.setFromDate(defaultDate);
			proAcc.setToDate(defaultDate);
			return "success";
		}
		
		AtSummaryViewFilter filter=new AtSummaryViewFilter();
		if (StringUtils.isNotBlank(proAcc.getProviderId()))
			filter.setProviderId(proAcc.getProviderId().trim());

		if (StringUtils.isNotBlank(proAcc.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(proAcc.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}else
			addFieldError("fromDate", getText("field.required"));

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(proAcc.getToDate())){
			Date toDate=DateUtil.convertString2Date(proAcc.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (toDate!=null){
					filter.setToDate(toDate);
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						addFieldError("toDate", getText("fromdate.greater.todate"));
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(), new Date())<0)
						addFieldError("toDate", getText("todate.greater.curdate"));
					
				}else
					addFieldError("toDate", getText("field.date.type.invalid"));
		}else
			addFieldError("toDate", getText("field.required"));
		
		if (getFieldErrors().size()>0) return "success";
		
		filter.setRecordSize(FeConstant.MAX_RECORD);
//		filter.setRecordSize(2);
		int page=1;
		if (StringUtils.isBlank(proAcc.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(proAcc.getPage())){
			page=Integer.valueOf(proAcc.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";

		try{
			
			AtSummaryViewDao dao=new AtSummaryViewDao();
			int recordCount=dao.searchCount(filter);
			Paging<AtSummaryModel> paging=new Paging<AtSummaryModel>(page, 10, filter.getRecordSize(), recordCount);
			proAcc.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<AtSummaryView> list= dao.search(filter);
			
			for(int i=0; i<list.size();i++){ 
				AtSummaryModel model=new AtSummaryModel();
				AtSummaryView atSummary=list.get(i);
				model.setProviderId(atSummary.getProviderId());
				model.setBeginAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getBeginAmount())));
				model.setEndAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getEndAmount())));
				model.setTotalTxn(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getTotalTxn())));
				model.setTotalAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getTotalAmount())));
				model.setTotalInputAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getTotalInputAmount())));
				model.setUsedAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getUsedAmount())));
//				already GMT date
				model.setTxnDate(DateUtil.convertDatetoString(atSummary.getTxnDate(), "dd/MM/yyyy"));
				listContent.add(model);  
			}
			paging.setItems(listContent);
			request.setAttribute("pageView", paging);
			System.out.println("**************** "+filter);
			System.out.println("**************** "+listContent);
			
		}catch (Exception e) {
			log.error("Fail to search provider account",e);
			addFieldError("sys_message", getText("database.exception"));
		}
		return "success";
	} 
	
	public String listAccountSummaryViewRpt() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		AtSummaryFilter filter=new AtSummaryFilter();
		if (StringUtils.isNotBlank(proAcc.getProviderId()))
			filter.setProviderId(proAcc.getProviderId().trim());

		if (StringUtils.isNotBlank(proAcc.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(proAcc.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					resultMap.put("error", "INVALID_INPUT");
					
		}else
			resultMap.put("error", "INVALID_INPUT");

		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isNotBlank(proAcc.getToDate())){
			Date toDate=DateUtil.convertString2Date(proAcc.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (toDate!=null){
					filter.setToDate(toDate);
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						resultMap.put("error", "INVALID_INPUT");
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(),new Date())<0)
						resultMap.put("error", "INVALID_INPUT");
					
				}else
					resultMap.put("error", "INVALID_INPUT");
		}else
			resultMap.put("error", "INVALID_INPUT");
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		String reportRequest = ReportUtils.createReportHeader(FeConstant.REPORT_PROVIDER_ACCOUNT_SUMMARY_RPT_ID, FeConstant.REPORT_PROVIDER_ACCOUNT_SUMMARY_FILENAME, AppUtils.getUserNameLogin(), "5");
		String inputParameters=ReportUtils.providerAccountSummaryRptParams(filter);
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
	
	public String listTxnSummaryView() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		Map<String, String> providerAccountMap=new HashMap<String, String>();
		providerAccountMap.put("", "----"+getText("select.all.text")+"----");
		for(Entry<String, String> entry:AppConstants.AIRTIME_PROVIDER.entrySet() ){
			providerAccountMap.put(entry.getKey(), entry.getValue());
		}
		request.setAttribute("providerAccountMap", providerAccountMap);
		List<AtSummaryModel> listContent=new ArrayList<AtSummaryModel>(); 
		if (proAcc==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			proAcc=new ProviderAccountModel();
			proAcc.setFromDate(defaultDate);
			proAcc.setToDate(defaultDate);
			return "success";
		}
		
		AtSummaryViewFilter filter=new AtSummaryViewFilter();
		if (StringUtils.isNotBlank(proAcc.getProviderId()))
			filter.setProviderId(proAcc.getProviderId().trim());

		if (StringUtils.isNotBlank(proAcc.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(proAcc.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}else
			addFieldError("fromDate", getText("field.required"));

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(proAcc.getToDate())){
			Date toDate=DateUtil.convertString2Date(proAcc.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (toDate!=null){
					filter.setToDate(toDate);
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						addFieldError("toDate", getText("fromdate.greater.todate"));
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(), new Date())<0)
						addFieldError("toDate", getText("todate.greater.curdate"));
					
				}else
					addFieldError("toDate", getText("field.date.type.invalid"));
		}else
			addFieldError("toDate", getText("field.required"));
		
		if (getFieldErrors().size()>0) return "success";
		
		filter.setRecordSize(FeConstant.MAX_RECORD);
//		filter.setRecordSize(2);
		int page=1;
		if (StringUtils.isBlank(proAcc.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(proAcc.getPage())){
			page=Integer.valueOf(proAcc.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";

		try{
			
			AtSummaryViewDao dao=new AtSummaryViewDao();
			int recordCount=dao.searchCount(filter);
			Paging<AtSummaryModel> paging=new Paging<AtSummaryModel>(page, 10, filter.getRecordSize(), recordCount);
			proAcc.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<AtSummaryView> list= dao.search(filter);
			
			for(int i=0; i<list.size();i++){ 
				AtSummaryModel model=new AtSummaryModel();
				AtSummaryView atSummary=list.get(i);
				model.setProviderId(atSummary.getProviderId());
				model.setBeginAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getBeginAmount())));
				model.setEndAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getEndAmount())));
				model.setTotalTxn(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getTotalTxn())));
				model.setTotalAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getTotalAmount())));
				model.setTotalInputAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getTotalInputAmount())));
				model.setUsedAmount(AppUtils.convertStringToCurrency(String.valueOf(atSummary.getUsedAmount())));
//				already GMT date
				model.setTxnDate(DateUtil.convertDatetoString(atSummary.getTxnDate(), "dd/MM/yyyy"));
				listContent.add(model);  
			}
			paging.setItems(listContent);
			request.setAttribute("pageView", paging);
			System.out.println("**************** "+filter);
			System.out.println("**************** "+listContent);
			
		}catch (Exception e) {
			log.error("Fail to search provider account",e);
			addFieldError("sys_message", getText("database.exception"));
		}
		return "success";
	} 
	public String listTxnSummaryViewRpt() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> resultMap=new HashMap<String, String>();
		AtSummaryFilter filter=new AtSummaryFilter();
		if (StringUtils.isNotBlank(proAcc.getProviderId()))
			filter.setProviderId(proAcc.getProviderId().trim());

		if (StringUtils.isNotBlank(proAcc.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(proAcc.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					resultMap.put("error", "INVALID_INPUT");
					
		}else
			resultMap.put("error", "INVALID_INPUT");

		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		
		if (StringUtils.isNotBlank(proAcc.getToDate())){
			Date toDate=DateUtil.convertString2Date(proAcc.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (toDate!=null){
					filter.setToDate(toDate);
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						resultMap.put("error", "INVALID_INPUT");
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(), new Date())<0)
						resultMap.put("error", "INVALID_INPUT");
					
				}else
					resultMap.put("error", "INVALID_INPUT");
		}else
			resultMap.put("error", "INVALID_INPUT");
		
		if (resultMap.size()>0) return AppUtils.builJsonResult(request, resultMap, "success");
		String reportRequest = ReportUtils.createReportHeader(FeConstant.REPORT_PROVIDER_TXN_SUMMARY_RPT_ID, FeConstant.REPORT_PROVIDER_TXN_SUMMARY_FILENAME, AppUtils.getUserNameLogin(), "5");
		String inputParameters=ReportUtils.providerTxnSummaryRptParams(filter);
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
	public ProviderAccountModel getProAcc() {
		return proAcc;
	}
	public void setProAcc(ProviderAccountModel proAcc) {
		this.proAcc = proAcc;
	}

	public ProviderAccountModel convertVotoModel(ProviderAccount account){
		ProviderAccountModel model=new ProviderAccountModel();
		model.setTxnDate(DateUtil.convertDate2String(account.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
		model.setProviderId(account.getProviderId());
		model.setTxnId(account.getTxnId());
		model.setInputAmount(AppUtils.convertStringToCurrency(String.valueOf(account.getInputAmount())));
		model.setDiscount(account.getDiscount());
		model.setTotalAmount(AppUtils.convertStringToCurrency(String.valueOf(account.getTotalAmount())));
		model.setEmployee(account.getEmployee());
		model.setDescription(account.getDescription());
		return model;
	}

	
	private List<LockTelcoInfoModel> getLockTelcoInfo() throws DaoException{
		List<LockTelcoInfoModel> result = new ArrayList<LockTelcoInfoModel>();
		ProviderAmountDao providerAmountDao=new ProviderAmountDao(); 
		ReservedTelcoDao reserveTelcoDao=new ReservedTelcoDao(); 
		
		Map<String, ProviderAmount> providerAmountMap=new HashMap<String, ProviderAmount>(); 
		Map<String, ReservedTelco> telcoLockAmountMap=new HashMap<String, ReservedTelco>(); 
		
		List<ProviderAmount> providerAmountList= providerAmountDao.selectAll();
		List<ReservedTelco> reservedTelcoList= reserveTelcoDao.selectAll();
		
		for (ProviderAmount providerAmount:providerAmountList)
			providerAmountMap.put(providerAmount.getProviderId(),providerAmount );
		
		for (ReservedTelco telcoLock:reservedTelcoList)
			telcoLockAmountMap.put(telcoLock.getTelcoId(),telcoLock);
		
		LockTelcoInfoModel telcoInfoModel;
		long sumProviderAmount;
		ProviderAmount providerAmount;
		long temp;
		for (TelcoProvider telcoProviderInfo:AppConstants.TELCO_PROVIDER.values()){
			try{
				telcoInfoModel=new LockTelcoInfoModel();
				ReservedTelco reservedTelco=telcoLockAmountMap.get(telcoProviderInfo.getProviderId());
				providerAmount=providerAmountMap.get(telcoProviderInfo.getConnectionIds()[0]);
				temp =reservedTelco.getReservedAmount();
//				if (reservedTelco.getPercent()!=null ) temp=temp*reservedTelco.getPercent()/100;
				telcoInfoModel.setLockAmount(AppUtils.convertStringToCurrency(String.valueOf(temp)));
				telcoInfoModel.setProviderAmount(AppUtils.convertStringToCurrency(String.valueOf(providerAmount.getTotalLoaded()-providerAmount.getTotalUsed()-temp)));
				telcoInfoModel.setProviderId(telcoProviderInfo.getConnectionIds()[0]);
				telcoInfoModel.setProviderIds(StringUtils.join(telcoProviderInfo.getConnectionIds(),","));
				sumProviderAmount=0;
				
				for (String providerId : telcoProviderInfo.getConnectionIds()){
					sumProviderAmount=sumProviderAmount+providerAmountMap.get(providerId).getTotalLoaded()-providerAmountMap.get(providerId).getTotalUsed();
				}
				telcoInfoModel.setProviderAmountSumary(AppUtils.convertStringToCurrency(String.valueOf(sumProviderAmount-temp)));
				telcoInfoModel.setTelcoId(telcoProviderInfo.getProviderId());
				result.add(telcoInfoModel);
			}catch (Exception e) {
				log.error("Fail to get Lock Telco Info from database.| telcoProviderInfo"+telcoProviderInfo,e);
			}
		}
		
		return result;
	}
}
