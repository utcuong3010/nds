package com.mbv.frontend.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.ReservedAccountDao;
import com.mbv.bp.common.dao.airtime.ReservedTxnDao;
import com.mbv.bp.common.vo.airtime.ReservedAccount;
import com.mbv.bp.common.vo.airtime.ReservedAccountFilter;
import com.mbv.bp.common.vo.airtime.ReservedTxn;
import com.mbv.bp.common.vo.airtime.ReservedTxnFilter;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.model.ReservedAccountModel;
import com.mbv.frontend.model.ReservedTxnModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;


public class ReservedAccountController extends ActionControllerBase {
	private Log log=LogFactory.getLog(ReservedAccountController.class);
	private static final long serialVersionUID = 1L;
	private ReservedAccountModel accountModel;
	private ReservedTxnModel txnModel;
	
	public String reservedAccountView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		if (accountModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			accountModel=new ReservedAccountModel();
			accountModel.setFromDate(defaultDate);
			accountModel.setToDate(defaultDate);
			return "success";
		}
		List<ReservedAccountModel> listContent=new ArrayList<ReservedAccountModel>();
		listContent.clear();
		ReservedAccountFilter filter=new ReservedAccountFilter();
		
		if (StringUtils.isNotBlank(accountModel.getSystemId()))
			filter.setSystemId(accountModel.getSystemId());

		if (StringUtils.isNotBlank(accountModel.getAccountId()))
			filter.setAccountId(accountModel.getAccountId());
		
		if (StringUtils.isNotBlank(accountModel.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(accountModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}else
			addFieldError("fromDate", getText("field.required"));

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(accountModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(accountModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
		
		int page=1;
		if (StringUtils.isBlank(accountModel.getPage()))
			filter.setStartRecord(0);
		else if (StringUtils.isNumeric(accountModel.getPage())){
			page=Integer.valueOf(accountModel.getPage());
			if (page<1) page=1;
			
		}else {
			filter.setStartRecord(0);
		}
		if (getFieldErrors().size()>0)
			return "success";

		try{
			
			ReservedAccountDao dao=new ReservedAccountDao();
			int recordCount=dao.searchCount(filter);
			Paging<ReservedAccountModel> paging=new Paging<ReservedAccountModel>(page, 10, filter.getRecordSize(), recordCount);
			accountModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<ReservedAccount> list= dao.search(filter);
			
			for(int i=0; i<list.size();i++){ 
				ReservedAccountModel model=new ReservedAccountModel();
				ReservedAccount reservedAccount=list.get(i);
				model.setAccountId(reservedAccount.getAccountId());
				model.setSystemId(reservedAccount.getSystemId());
				model.setDescription(reservedAccount.getDescription());
				model.setTotalCredit(AppUtils.convertStringToCurrency(String.valueOf(reservedAccount.getTotalCredit())));
				model.setTotalDebit(AppUtils.convertStringToCurrency(String.valueOf(reservedAccount.getTotalDebit())));
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(reservedAccount.getTotalDebit()-reservedAccount.getTotalCredit())));
				model.setCreatedDate(DateUtil.convertDate2String(reservedAccount.getCreatedDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setUpdatedDate(DateUtil.convertDate2String(reservedAccount.getUpdatedDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setTelcoIds(reservedAccount.getTelcoIds());
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


	public String reservedTxnView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		List<ReservedTxnModel> listContent=new ArrayList<ReservedTxnModel>();
		listContent.clear();
		ReservedTxnFilter filter=new ReservedTxnFilter();
		if (txnModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			txnModel=new ReservedTxnModel();
			txnModel.setFromDate(defaultDate);
			txnModel.setToDate(defaultDate);
			return "success";
		}
		if (StringUtils.isNotBlank(txnModel.getSystemId()))
			filter.setSystemId(txnModel.getSystemId());
		
		if (StringUtils.isNotBlank(txnModel.getTxnId()))
			filter.setTxnId(txnModel.getTxnId());
		
		if (StringUtils.isNotBlank(txnModel.getAccountId()))
			filter.setAccountId(txnModel.getAccountId());
		
		if (StringUtils.isNotBlank(txnModel.getReferenceId()))
			filter.setReferenceId(txnModel.getReferenceId());
		
		if (StringUtils.isNotBlank(txnModel.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(txnModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					addFieldError("fromDate", getText("field.date.type.invalid"));
					
		}else
			addFieldError("fromDate", getText("field.required"));

		if (getFieldErrors().size()>0) return "success";
		
		if (StringUtils.isNotBlank(txnModel.getToDate())){
			Date toDate=DateUtil.convertString2Date(txnModel.getToDate().trim()+" 23:59:59","GMT+7","dd/MM/yyyy HH:mm:ss");
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
			
			ReservedTxnDao dao=new ReservedTxnDao();
			int recordCount=dao.searchCount(filter);
			Paging<ReservedTxnModel> paging=new Paging<ReservedTxnModel>(page, 10, filter.getRecordSize(), recordCount);
			txnModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<ReservedTxn> list= dao.search(filter);
			
			for(int i=0; i<list.size();i++){ 
				ReservedTxnModel model=new ReservedTxnModel();
				ReservedTxn reservedTxn=list.get(i);
				model.setTxnId(reservedTxn.getTxnId());
				model.setAccountId(reservedTxn.getAccountId());
				model.setSystemId(reservedTxn.getSystemId());
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(reservedTxn.getAmount())));
				model.setReferenceId(reservedTxn.getReferenceId());
				model.setOperation(reservedTxn.getOperation());
				model.setDescription(reservedTxn.getDescription());
				model.setCreatedDate(DateUtil.convertDate2String(reservedTxn.getCreatedDate(),"GMT+7","dd/MM/yyyy HH:mm"));
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
	
	public ReservedAccountModel getAccountModel() {
		return accountModel;
	}

	public void setAccountModel(ReservedAccountModel accountModel) {
		this.accountModel = accountModel;
	}

	public ReservedTxnModel getTxnModel() {
		return txnModel;
	}

	public void setTxnModel(ReservedTxnModel txnModel) {
		this.txnModel = txnModel;
	}
	
	
}
