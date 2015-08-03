package com.mbv.frontend.util;

import java.util.Date;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mbv.bp.common.constants.AppConstants;
import com.mbv.bp.common.dao.DaoException;
import com.mbv.bp.common.dao.airtime.AtSummaryDao;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
import com.mbv.bp.common.dao.airtime.ProviderAccountDao;
import com.mbv.bp.common.util.DateUtils;
import com.mbv.bp.common.util.DateUtils.Operation;
import com.mbv.bp.common.util.DateUtils.Type;
import com.mbv.bp.common.vo.airtime.AtSummary;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.ProviderAccount;

public class AtSummaryRptUtil {
	
	private static Log log = LogFactory.getLog(AtSummaryRptUtil.class);
	public void generateDate() throws DaoException{
		TimeZone oldTimezone=TimeZone.getDefault();
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
		String strMinDate="20110715";
		Date minDate=DateUtils.convertStringToDate(strMinDate, "yyyyMMdd");
		Date curDate=new Date();
		String providerId="abc";
		AtSummaryDao atSummaryDao=new AtSummaryDao();
		AtSummary atSummary=new AtSummary();
		atSummary.setProviderId(providerId);
		Date maxDate=atSummaryDao.searchMaxDate(atSummary);
		if (maxDate==null) maxDate=minDate;
		long nDay=DateUtils.dateDiff(Type.BY_DAY, maxDate, curDate);
		if (nDay==0) return;
		
		
	} 
}
