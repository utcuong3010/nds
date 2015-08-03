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
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AnypayAccountTxnDao;
import com.mbv.bp.common.dao.airtime.SimInfoDao;
import com.mbv.bp.common.vo.airtime.AnypayAccountTxn;
import com.mbv.bp.common.vo.airtime.AnypayAccountTxnFilter;
import com.mbv.bp.common.vo.airtime.SimInfo;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.generator.IdGeneratorFactory;
import com.mbv.frontend.model.AnypayAccountTxnModel;
import com.mbv.frontend.model.SimInfoModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;


public class AnypayAccountController extends ActionControllerBase {
	private Log log=LogFactory.getLog(AnypayAccountController.class);
	private static final long serialVersionUID = 1L;
	private AnypayAccountTxnModel proAcc;
	
	public String listView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		prepareView();
		
		if (proAcc==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			proAcc=new AnypayAccountTxnModel();
			proAcc.setSimId("");
			proAcc.setFromDate(defaultDate);
			proAcc.setToDate(defaultDate);
			return "success";
		}
		
		
		
		AnypayAccountTxnFilter filter=new AnypayAccountTxnFilter();
		if (StringUtils.isNotBlank(proAcc.getSimId()))
			filter.setSimId(proAcc.getSimId().trim());
		else{
			proAcc.setSimId("");
		}
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
		List<AnypayAccountTxnModel> listContent=new ArrayList<AnypayAccountTxnModel>();
		try{
			AnypayAccountTxnDao dao=new AnypayAccountTxnDao();
			int recordCount=dao.searchCount(filter);
			Paging<AnypayAccountTxnModel> paging=new Paging<AnypayAccountTxnModel>(page, 10, filter.getRecordSize(), recordCount);
			proAcc.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<AnypayAccountTxn> list= dao.search(filter);
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				AnypayAccountTxnModel model=new AnypayAccountTxnModel();
				AnypayAccountTxn account=list.get(i);
				model.setTxnDate(DateUtil.convertDate2String(account.getTxnDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setSimId(account.getSimId());
				model.setTxnId(account.getTxnId());
				model.setAmount(AppUtils.convertStringToCurrency(String.valueOf(account.getAmount())));
				model.setEmployee(account.getEmployee());
				model.setDescription(account.getDescription());
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
	
	public String prepareView() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		Map<String, String> simMap=new HashMap<String, String>();
		simMap.put("", "----"+getText("select.all.text")+"----");
		SimInfoDao simInfoDao=new SimInfoDao();
		
		List<SimInfoModel> simInfoModels=new ArrayList<SimInfoModel>();
		List<SimInfo> simInfos=simInfoDao.selectAll();
		SimInfoModel simInfoModel;
		if (simInfos!=null){
			for(SimInfo simInfo:simInfos){
				simMap.put(simInfo.getMsisdn(), simInfo.getComId()+"-"+simInfo.getMsisdn());
				simInfoModel=new SimInfoModel();
				simInfoModel.setComId(simInfo.getComId());
				simInfoModel.setCurrentAmount(AppUtils.convertStringToCurrency(String.valueOf(simInfo.getCurrentAmount())));
				simInfoModel.setLockAmount(AppUtils.convertStringToCurrency(String.valueOf(simInfo.getLockAmount())));
				simInfoModel.setMsisdn(simInfo.getMsisdn());
				simInfoModel.setSimStatus(simInfo.getSimStatus());
				simInfoModel.setSimType(simInfo.getSimType());
				if (simInfo.getLastSentSms()!=null)
					simInfoModel.setLastSentSms(DateUtil.convertDate2String(simInfo.getLastSentSms(),"GMT+7","dd/MM/yyyy"));
				else 
					simInfoModel.setLastSentSms("Haven't sent any yet");
				simInfoModels.add(simInfoModel);
			}
		}
		request.setAttribute("simInfoModels", simInfoModels);
		request.setAttribute("simMap", simMap);
		return "success";
	}
	public String addView() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		prepareView();
		if (proAcc==null){
			proAcc=new AnypayAccountTxnModel();
			proAcc.setSimId("");
		}
		return "success";
	}

	public String save() throws DaoException{
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors();
		AnypayAccountTxn account=new AnypayAccountTxn();
		AnypayAccountTxnDao dao=new AnypayAccountTxnDao();
		String temp;
		prepareView();

		if (proAcc==null) return "failure";
//
		if (StringUtils.isNotBlank(proAcc.getSimId())){
			account.setSimId(proAcc.getSimId().trim());
		}else
			addFieldError("simId", getText("field.required"));
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
//amount
		if (StringUtils.isNotBlank(proAcc.getAmount())){
			temp=proAcc.getAmount().replace(".","");
			if (NumberUtils.isNumber(temp))
				account.setAmount(Long.valueOf(temp));
			else
				addFieldError("amount", getText("field.numeric.required"));
		}else
			addFieldError("amount", getText("field.required"));
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
			AnypayAccountTxnDao providerAccountDao=new AnypayAccountTxnDao(sqlMapClient);
			SimInfoDao simInfoDao=new SimInfoDao(sqlMapClient);
			account.setCreatedBy(AppUtils.getUserLogin().getUsername());
			
			if( StringUtils.isBlank(account.getCreatedBy())) account.setCreatedBy("unknown");
				account.setCreatedDate(new Date());
			
			providerAccountDao.insert(account);
			SimInfo simInfo=new SimInfo();
			simInfo.setMsisdn(account.getSimId());
			simInfo.setLockAmount(0L); //plus value
			simInfo.setCurrentAmount(account.getAmount()); 
			simInfoDao.updateCurrentLockAmountByMsisdn(simInfo);
			
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
		AnypayAccountTxn anypayAccountTxn=new AnypayAccountTxn();
		anypayAccountTxn.setTxnId(proAcc.getTxnId().trim());
		try{
			AnypayAccountTxnDao dao=new AnypayAccountTxnDao();
			if (!dao.select(anypayAccountTxn)){
				addFieldError("sys_message", getText("data.not.existed"));
				return "failure";
			}
			if (anypayAccountTxn.getDeleted()!=0)
				 return "success";
			
		}catch (Exception e) {
			log.error("Fail to select to Database",e);
			addFieldError("sys_message", getText("database.exception"));
			return "failure";
		}
		SqlMapClient sqlMapClient=SqlConfig.getAtSqlMapInstance();
		try{
			sqlMapClient.startTransaction();
			AnypayAccountTxnDao anypayAccountTxnDao=new AnypayAccountTxnDao(sqlMapClient);
			SimInfoDao simInfoDao=new SimInfoDao(sqlMapClient);
			anypayAccountTxn.setDeleted(Long.valueOf(IdGeneratorFactory.getInstance().getFeIdGenerator().generateId()));
			anypayAccountTxn.setUpdatedBy(AppUtils.getUserLogin().getUsername());
			anypayAccountTxn.setUpdatedDate(new Date());	
			anypayAccountTxnDao.updateDeleted(anypayAccountTxn);
			
			SimInfo simInfo=new SimInfo();
			simInfo.setMsisdn(anypayAccountTxn.getSimId());
			simInfo.setLockAmount(0L); //plus value
			simInfo.setCurrentAmount(-1*anypayAccountTxn.getAmount()); 
			simInfoDao.updateCurrentLockAmountByMsisdn(simInfo);
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
	
	
	public AnypayAccountTxnModel getProAcc() {
		return proAcc;
	}
	public void setProAcc(AnypayAccountTxnModel proAcc) {
		this.proAcc = proAcc;
	}



	

}
