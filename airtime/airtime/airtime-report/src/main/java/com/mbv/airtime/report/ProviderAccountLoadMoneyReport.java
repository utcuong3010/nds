package com.mbv.airtime.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.mbv.airtime.report.constant.ReportConstant;
import com.mbv.airtime.report.model.ProviderAccountModel;
import com.mbv.airtime.report.utils.DateUtil;
import com.mbv.airtime.report.utils.ExcelWriter;
import com.mbv.airtime.report.utils.DateUtil.Type;
import com.mbv.bp.common.dao.airtime.ProviderAccountDao;
//import com.mbv.bp.common.util.DateUtil;
//import com.mbv.bp.common.util.DateUtil.Type;
import com.mbv.bp.common.vo.airtime.ProviderAccount;
import com.mbv.bp.common.vo.airtime.ProviderAccountFilter;


public class ProviderAccountLoadMoneyReport extends BaseReport {
	
	
//	private String inputDateFormat="yyyy-MM-dd";
	private String inputDateTimeFormat="yyyy-MM-dd HH:mm:ss";
	private String outputDateFormat="dd/MM/yyyy";
	private ProviderAccountFilter filter;
	
	@Override
	public void processInputParams() {
		try{
			String inputParams=propertyMap.get(ReportConstant.ATTR_INPUT_PARA);
			validate(ProviderAccountModel.build(inputParams));
		}catch (Exception e) {
			log.error("Invalid request.",e);
			addproperty(ReportConstant.ATTR_ERROR_MSG, e.getMessage());
			addproperty(ReportConstant.ATTR_ERROR_CODE, ReportConstant.STATUS_ERROR);
		}
	}

	private void validate(ProviderAccountModel model) throws Exception {
		filter=new ProviderAccountFilter();
		if (StringUtils.isNotBlank(model.getProviderId()))
			filter.setProviderId(model.getProviderId().trim());
		if (StringUtils.isNotBlank(model.getEmployee()))
			filter.setEmployee(model.getEmployee());
		if (StringUtils.isNotBlank(model.getTxnId()))
			filter.setTxnId(model.getTxnId().trim());

		if (StringUtils.isNotBlank(model.getFromDate())){
			
			Date fromDate=DateUtil.convertStringToDate(model.getFromDate().trim(),inputDateTimeFormat);
			fromDate=(fromDate!=null)?fromDate:DateUtil.convertStringToDate(model.getFromDate().trim(),inputDateTimeFormat);
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					throw new Exception("Du lieu 'Tu ngay' khong chinh xac yyyy-MM-dd"); 
					
		}

		if (StringUtils.isNotBlank(model.getToDate())){
			Date toDate=DateUtil.convertStringToDate(model.getToDate().trim(), inputDateTimeFormat);
			toDate=(toDate!=null)?toDate:DateUtil.convertStringToDate(model.getToDate().trim(),inputDateTimeFormat);
				if (toDate!=null){
					filter.setToDate(toDate);
					if (filter.getFromDate()!=null)
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						throw new Exception("'Tu ngay' khong the lon hon 'Den ngay' de trong"); 
					if (DateUtil.dateDiffGMT2GMT7( filter.getToDate(), new Date())<0)
						throw new Exception("'Den ngay' lon hon ngay hien tai"); 
					
				}else
					throw new Exception("Du lieu 'Den ngay' khong chinh xac yyyy-MM-dd"); 
		}
		
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			throw new Exception("Chua nhap day du 'Tu ngay' va 'Den ngay'"); 
	}

	@Override
	public void process() {
		if (ReportConstant.STATUS_ERROR.equalsIgnoreCase(propertyMap.get(ReportConstant.ATTR_ERROR_CODE)))	
			return;
		ExcelWriter excelWriter=new ExcelWriter();
		try{
			ProviderAccountDao dao=new ProviderAccountDao(sqlMapClient);
			String reportTemp=propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME);
			String reportFile=propertyMap.get(ReportConstant.ATTR_REPORT_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_FILE_NAME);
//			addproperty(ReportConstant.ATTR_REPORT_FILE_NAME, reportFile);
			excelWriter.open(reportTemp,reportFile, null);
			
			if (propertyMap.containsKey(ReportConstant.ATTR_QUERY_RECORD_SIZE))
				filter.setRecordSize(Integer.valueOf(propertyMap.get(ReportConstant.ATTR_QUERY_RECORD_SIZE)));
			else filter.setRecordSize(100);
			
			int startReccord=0;
			List<ProviderAccount> list;
			int startRow=4;
			int startCol=0;
			log.info(filter);
			do{
				filter.setStartRecord(startReccord);
				list= dao.search(filter);
				if (list==null) list=new ArrayList<ProviderAccount>();
				for(int i=0;i<list.size();i++){
					ProviderAccount record=list.get(i);
					startRow++;
					startCol=0;
					excelWriter.WriteLong(startCol++,startRow,new Long(startReccord+i+1),excelWriter.longFormatDashDot(false,10));
					excelWriter.WriteString(startCol++,startRow,DateUtil.convertDate2String(record.getTxnDate(),"GMT+7", outputDateFormat),excelWriter.formatDashDot(false,10));
					excelWriter.WriteString(startCol++,startRow,""+record.getProviderId(),excelWriter.formatDashDot(false,10));
					excelWriter.WriteString(startCol++,startRow,""+record.getTxnId(),excelWriter.formatDashDot(false,10));
					excelWriter.WriteLong(startCol++,startRow,record.getInputAmount(),excelWriter.longFormatDashDot(false,10));
					excelWriter.WriteString(startCol++,startRow,""+record.getDiscount(),excelWriter.formatDashDot(false,10));
					excelWriter.WriteLong(startCol++,startRow,record.getTotalAmount(),excelWriter.longFormatDashDot(false,10));
					excelWriter.WriteString(startCol++,startRow,""+record.getEmployee(),excelWriter.formatDashDot(false,10));
					if (StringUtils.isNotBlank(record.getDescription())){
						excelWriter.WriteString(startCol++,startRow,record.getDescription(),excelWriter.formatDashDot(false,10));
					}else{
						excelWriter.WriteBlank(startCol++,startRow,excelWriter.formatDashDot(false,10));
					}
				}
				startReccord+=list.size();
			}while (list.size()==filter.getRecordSize());
			log.info("Report created | total record-"+startReccord+"| filename-"+reportFile);
			addproperty(ReportConstant.ATTR_ERROR_CODE, ReportConstant.STATUS_SUCCESS);
		}catch (Exception e) {
			log.error("Fail to create report",e);
			addproperty(ReportConstant.ATTR_ERROR_MSG, e.getMessage());
			addproperty(ReportConstant.ATTR_ERROR_CODE, ReportConstant.STATUS_ERROR);
		}finally{
			excelWriter.close();
		}
	}
	
	public static void main(String[] args) {
		ProviderAccountLoadMoneyReport accountLoadMoneyReport=new ProviderAccountLoadMoneyReport();
		accountLoadMoneyReport.addproperty(ReportConstant.ATTR_INPUT_PARA, "");
		
		System.out.println("hello");
	}

}
