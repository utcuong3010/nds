package com.mbv.airtime.alert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import com.mbv.airtime.alert.constant.AlertConstant;
import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AirtimeConfigDao;
import com.mbv.bp.common.dao.airtime.AtSummaryDao;
import com.mbv.bp.common.vo.airtime.AirtimeConfig;
import com.mbv.bp.common.vo.airtime.AtSummary;

public class ProviderNotEnoughMoneyAlert extends BaseAlert {

	@Override
	public String execute() {
		
//		int minAmount=10000;
		Date currentDate=new Date();
//		String fileName="filename.xml";
/*		get total amount from provider - has alert? y
		check last snapshot total price
		if diff --> write alert
		save snapshot
*/	
/*		
		Map<String, Long> minAmountMap=new HashMap<String, Long>();
		String minAccounts=propertyMap.get(AlertConstant.ATTR_ALERT_MIN_ACCOUNT_STR);
		for (String minAcc:minAccounts.split(",")){
			String[] arrStr=minAcc.split(":");
			minAmountMap.put(arrStr[0], Long.valueOf(arrStr[1]));
		}
		Map<String, Long> resultMap=new HashMap<String, Long>();
		for (Entry<String, AirtimeProvider> entry:AppConstants.AIRTIME_PROVIDER.entrySet()){
			try{
			 AirtimeProvider airtimeProvider=entry.getValue();
			 AtSummary atSummary=AtSummaryRptUtil.getAtSummaryByDate(currentDate, airtimeProvider.getProviderId(), airtimeProvider.getSuccessCode(), airtimeProvider.getSuccessStatus());
			 AtSummaryDao dao=new AtSummaryDao();
			 long beginAmount=dao.selectBeginAmount(atSummary);
			 long endAmount=beginAmount+atSummary.getTotalAmount()-atSummary.getUsedAmount();
			 if (endAmount<minAmountMap.get(airtimeProvider.getProviderId())){
				 AirtimeConfigDao airtimeConfigDao=new AirtimeConfigDao();
				 AirtimeConfig airtimeConfig=new AirtimeConfig();
				 airtimeConfig.setModule(ALERT_MODULE);
				 airtimeConfig.setType(ALERT_MIN_AMOUNT_TYPE);
				 airtimeConfig.setParamKey(airtimeProvider.getProviderId());
				 String cfgValue=airtimeConfigDao.getValue(ALERT_MODULE, ALERT_MIN_AMOUNT_TYPE, airtimeProvider.getProviderId());
				 long minProviderAmount=0;
				 if (NumberUtils.isNumber(cfgValue)) 
					 minProviderAmount=Long.valueOf(cfgValue);
				 else minProviderAmount=-1;
				 if (endAmount != minProviderAmount){
					 resultMap.put(airtimeProvider.getProviderId().toUpperCase(), endAmount);
					 if (cfgValue==null){
						 airtimeConfig.setParamValue(String.valueOf(endAmount));
						 airtimeConfigDao.insert(airtimeConfig);
					 }else{
						 airtimeConfigDao.updateValue(ALERT_MODULE, ALERT_MIN_AMOUNT_TYPE, airtimeProvider.getProviderId(),String.valueOf(endAmount));
					 }
				 }else 
					 log.info("Alert already processed !");
				 
					 
			 }
			}catch (Exception e) {
				log.error("Fail to check provider account min |ErrMsg-"+e.getMessage(),e);
			}
		}
		writeAlertFile(resultMap);
*/		
		return null;
	}

}
