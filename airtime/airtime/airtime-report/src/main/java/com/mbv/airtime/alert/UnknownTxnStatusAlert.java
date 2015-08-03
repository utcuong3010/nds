package com.mbv.airtime.alert;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.util.DateUtils.Operation;
import com.mbv.bp.common.util.DateUtils.Type;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.AtTransactionFilter;

public class UnknownTxnStatusAlert extends BaseAlert {

	@Override 
	public String execute() {
		Map<String, Long> resultMap=new HashMap<String, Long>();
		try{
			Date curDate= new Date();
			AtTransactionFilter filter=new AtTransactionFilter(); 	
			filter.setFromDate(DateUtils.dateAdd(curDate, 1, Type.BY_DAY, Operation.MINUS));
			filter.setToDate(curDate);
			filter.setStartRecord(0); 
			filter.setRecordSize(1);
			filter.setTxnStatus(AppConstants.TXN_STATUS_UNKNOWN);
			AtTransactionDao transactionDao=new AtTransactionDao();
			List<AtTransaction> txnList= transactionDao.search(filter);
			if (txnList!=null && txnList.size()>0){
				 long unknownTxnId=txnList.get(0).getAtTxnId();
				 AirtimeConfigDao airtimeConfigDao=new AirtimeConfigDao();
				 AirtimeConfig airtimeConfig=new AirtimeConfig();
				 airtimeConfig.setModule(ALERT_MODULE);
				 airtimeConfig.setType(ALERT_UNKNOWN_STATUS_TYPE);
				 airtimeConfig.setParamKey(ALERT_UNKNOWN_STATUS_TXNID);
				 String cfgValue=airtimeConfigDao.getValue(airtimeConfig.getModule(), airtimeConfig.getType(), airtimeConfig.getParamKey());
				 if (!String.valueOf(unknownTxnId).equalsIgnoreCase(cfgValue)){
					 resultMap.put("unknown_txn", unknownTxnId);
					 if (cfgValue==null){
						 airtimeConfig.setParamValue(String.valueOf(unknownTxnId));
						 airtimeConfigDao.insert(airtimeConfig);
					 }else{
						 airtimeConfig.setParamValue(String.valueOf(unknownTxnId));
						 airtimeConfigDao.updateValue(airtimeConfig.getModule(), airtimeConfig.getType(), airtimeConfig.getParamKey(),airtimeConfig.getParamValue());
					 }
				 }else 
					 log.info("Alert already processed !");
			}
		}catch (Exception e) {
			log.error("Fail to check unknown transaction |ErrMsg-"+e.getMessage(),e);
		}
		writeAlertFile(resultMap);
		return null;
	}
}