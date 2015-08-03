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
import com.mbv.bp.common.dao.airtime.SimInfoDao;
import com.mbv.bp.common.dao.airtime.SmsContentDao;
import com.mbv.bp.common.vo.airtime.SimInfo;
import com.mbv.bp.common.vo.airtime.SimTransactionFilter;
import com.mbv.bp.common.vo.airtime.SmsContent;
import com.mbv.bp.common.vo.airtime.SmsContentFilter;
import com.mbv.frontend.constant.FeConstant;
import com.mbv.frontend.model.AnypaySmsModel;
import com.mbv.frontend.util.AppUtils;
import com.mbv.frontend.util.DateUtil;
import com.mbv.frontend.util.Paging;
import com.mbv.frontend.util.DateUtil.Type;

public class AnypaySmsController extends ActionControllerBase {
	private Log log=LogFactory.getLog(AnypaySmsController.class);
	private static final long serialVersionUID = 1L;
	private AnypaySmsModel txnModel;

	public String listView() throws DaoException {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		
		Map<String, String> simMap=new HashMap<String, String>();
		simMap.put("", "----"+getText("select.all.text")+"----");
		SimInfoDao simInfoDao=new SimInfoDao();
		List<SimInfo> simInfos=simInfoDao.selectAll();
		
		if (simInfos!=null){
			for(SimInfo simInfo:simInfos){
				simMap.put(simInfo.getMsisdn(), simInfo.getComId()+"-"+simInfo.getMsisdn());
			}
		}
		
		request.setAttribute("simMap", simMap);
		
		Map<String, String> txnTypeMap=new HashMap<String, String>();
		txnTypeMap.put("", "----"+getText("select.all.text")+"----");
		txnTypeMap.put(AppConstants.ANYPAY_SETTINGS.TOPUP_TXN, AppConstants.ANYPAY_SETTINGS.TOPUP_TXN);
		txnTypeMap.put(AppConstants.ANYPAY_SETTINGS.TRANSFER_TXN, AppConstants.ANYPAY_SETTINGS.TRANSFER_TXN);
		txnTypeMap.put(AppConstants.ANYPAY_SETTINGS.UNKNOWN_TXN, AppConstants.ANYPAY_SETTINGS.UNKNOWN_TXN);
		request.setAttribute("txnTypeMap", txnTypeMap);
		
		Map<String, String> questionMap=new HashMap<String, String>();
		questionMap.put("", "----"+getText("select.all.text")+"----");
		questionMap.put(AppConstants.YES_FLAG, AppConstants.YES_FLAG);
		questionMap.put(AppConstants.NO_FLAG, AppConstants.NO_FLAG);
		request.setAttribute("questionMap", questionMap);
		
		if (txnModel==null) {
			String defaultDate=DateUtil.convertDate2String(new Date(),"GMT+7","dd/MM/yyyy");
			txnModel=new AnypaySmsModel();
			txnModel.setSimId("");
			txnModel.setTxnType("");
			txnModel.setProcessed("");
			txnModel.setFromDate(defaultDate);
			txnModel.setToDate(defaultDate);
			return "success";
		}

		SmsContentFilter filter=new SmsContentFilter();
		
		if (StringUtils.isNotBlank(txnModel.getTxnType()))
			filter.setTxnType(txnModel.getTxnType().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMsisdn()))
			filter.setMsisdn(txnModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(txnModel.getProcessed())){
			filter.setProcessed(txnModel.getProcessed());
		}
		
		if (StringUtils.isNotBlank(txnModel.getPartId())){
			filter.setPartId(txnModel.getPartId().trim());
		}
		
		if (StringUtils.isNotBlank(txnModel.getPartId())){
			filter.setPartId(txnModel.getPartId().trim());
		}
		
		if (StringUtils.isNotBlank(txnModel.getSimId())){
			filter.setSimId(txnModel.getSimId().trim());
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
			SmsContentDao dao=new SmsContentDao();
			int recordCount=dao.searchCount(filter);
			Paging<AnypaySmsModel> paging=new Paging<AnypaySmsModel>(page, 10, filter.getRecordSize(), recordCount);
			txnModel.setPage(String.valueOf(paging.getCurrentPage()));
			filter.setStartRecord((paging.getCurrentPage()-1)*filter.getRecordSize());
			List<SmsContent> list= dao.search(filter);
			List<AnypaySmsModel> listContent=new ArrayList<AnypaySmsModel>();
			listContent.clear();
			for(int i=0; i<list.size();i++){ 
				AnypaySmsModel model=new AnypaySmsModel();
				SmsContent txn=list.get(i);
				model.setFraudStatus(txn.getFraudStatus());
				model.setMessageId(txn.getMessageId());
				model.setMsgDate(DateUtil.convertDate2String(txn.getMsgDate(),"GMT+7","dd/MM/yyyy HH:mm"));
				model.setMsisdn(txn.getMsisdn());
				model.setOrgContent(txn.getOrgContent());
				model.setPartId(txn.getPartId());
				model.setPartNo(""+txn.getPartNo());
				model.setProcessed(txn.getProcessed());
				model.setSender(txn.getSender());
				model.setSimId(txn.getSimId());
				model.setSmsAmount(txn.getSmsAmount());
				model.setTotalPart(""+txn.getTotalPart());
				model.setTxnAmount(txn.getTxnAmount());
				model.setTxnStatus(txn.getTxnStatus());
				model.setTxnType(txn.getTxnType());
				model.setTxtContent(txn.getTxtContent());
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

	public AnypaySmsModel getTxnModel() {
		return txnModel;
	}

	public void setTxnModel(AnypaySmsModel txnModel) {
		this.txnModel = txnModel;
	}
	
	public String anypaySmsExportCsv() throws DaoException { 
		
		Map<String, String> resultMap=new HashMap<String, String>();
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		clearFieldErrors(); 
		
		SmsContentFilter filter=new SmsContentFilter();
		
		if (StringUtils.isNotBlank(txnModel.getTxnType()))
			filter.setTxnType(txnModel.getTxnType().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMessageId()))
			filter.setMessageId(txnModel.getMessageId().trim());
		
		if (StringUtils.isNotBlank(txnModel.getMsisdn()))
			filter.setMsisdn(txnModel.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(txnModel.getProcessed())){
			filter.setProcessed(txnModel.getProcessed());
		}
		
		if (StringUtils.isNotBlank(txnModel.getPartId())){
			filter.setPartId(txnModel.getPartId().trim());
		}
		
		if (StringUtils.isNotBlank(txnModel.getPartId())){
			filter.setPartId(txnModel.getPartId().trim());
		}
		
		if (StringUtils.isNotBlank(txnModel.getSimId())){
			filter.setSimId(txnModel.getSimId().trim());
		}
		
		if (StringUtils.isNotBlank(txnModel.getFromDate())){
			Date fromDate=DateUtil.convertString2Date(txnModel.getFromDate().trim()+" 00:00:00","GMT+7","dd/MM/yyyy HH:mm:ss");
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					resultMap.put("error", "INVALID_INPUT");
					
		}

		if (getFieldErrors().size()>0) return "success";
		
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
		if (getFieldErrors().size()>0) return "success";
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			resultMap.put("error", "INVALID_INPUT");
		
		if (getFieldErrors().size()>0) return "success";
		
		filter.setRecordSize(FeConstant.MAX_RECORD);		
		filter.setStartRecord(0);
		
		
		String fileName="anypay_sms"+"_"+DateUtil.convertDate2String(new Date(),"GMT+7","yyyyMMddHHmm")+".csv"; 
		try{
			boolean result= com.mbv.bp.common.util.AppUtils.exportAnypaySms(filter, request.getRealPath(FeConstant.CDR_TEMP_PATH)+"/"+fileName);
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
}
