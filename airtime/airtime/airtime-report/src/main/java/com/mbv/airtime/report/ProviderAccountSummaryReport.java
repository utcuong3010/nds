package com.mbv.airtime.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.mbv.airtime.report.constant.ReportConstant;
import com.mbv.airtime.report.model.ProviderAccountSummaryModel;
import com.mbv.airtime.report.utils.DateUtil;
import com.mbv.airtime.report.utils.ExcelWriter;
import com.mbv.airtime.report.utils.DateUtil.Type;
import com.mbv.bp.common.dao.airtime.AtSummaryViewDao;
import com.mbv.bp.common.vo.airtime.AtSummaryView;
import com.mbv.bp.common.vo.airtime.AtSummaryViewFilter;

public class ProviderAccountSummaryReport extends BaseReport {
	
//	private String inputDateTimeFormat="yyyy-MM-dd";
	private String inputDateTimeFormat="yyyy-MM-dd HH:mm:ss";
	private String outputDateFormat="dd/MM/yyyy";
	private AtSummaryViewFilter filter;
	
	@Override
	public void processInputParams() {
		try{
			String inputParams=propertyMap.get(ReportConstant.ATTR_INPUT_PARA);
			validate(ProviderAccountSummaryModel.build(inputParams));
		}catch (Exception e) {
			log.error("Invalid request.",e);
			addproperty(ReportConstant.ATTR_ERROR_MSG, e.getMessage());
			addproperty(ReportConstant.ATTR_ERROR_CODE, ReportConstant.STATUS_ERROR);
		}
	}

	private void validate(ProviderAccountSummaryModel model) throws Exception {
		filter=new AtSummaryViewFilter();
		if (StringUtils.isNotBlank(model.getProviderId()))
			filter.setProviderId(model.getProviderId().trim());
		
		if (StringUtils.isNotBlank(model.getFromDate())){
			Date fromDate=DateUtil.convertStringToDate(model.getFromDate().trim(),inputDateTimeFormat);
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					throw new Exception("Du lieu 'Tu ngay' khong chinh xac yyyy-MM-dd"); 
					
		}

		if (StringUtils.isNotBlank(model.getToDate())){
			Date toDate=DateUtil.convertStringToDate(model.getToDate().trim(), inputDateTimeFormat);
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
//			AtSummaryRptUtil.updateSummaryTable();
			AtSummaryViewDao dao=new AtSummaryViewDao(sqlMapClient);
			String reportTemp=propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME);
			String reportFile=propertyMap.get(ReportConstant.ATTR_REPORT_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_FILE_NAME);
//			addproperty(ReportConstant.ATTR_REPORT_FILE_NAME, reportFile);
			excelWriter.open(reportTemp,reportFile, null);
			
			if (propertyMap.containsKey(ReportConstant.ATTR_QUERY_RECORD_SIZE))
				filter.setRecordSize(Integer.valueOf(propertyMap.get(ReportConstant.ATTR_QUERY_RECORD_SIZE)));
			else filter.setRecordSize(100);
			int startReccord=0;
			List<AtSummaryView> list;
			int startRow=4;
			int startCol=0;
			log.info(filter);
			do{
				filter.setStartRecord(startReccord);
				list= dao.search(filter);
				if (list==null) list=new ArrayList<AtSummaryView>();
				for(int i=0;i<list.size();i++){
					AtSummaryView record=list.get(i);
					startRow++;
					startCol=0;
					
					excelWriter.WriteLong(startCol++,startRow,new Long(startReccord+i+1),excelWriter.longFormatDashDot(false,10));
//					already GMT+7
					excelWriter.WriteString(startCol++,startRow,DateUtil.convertDatetoString(record.getTxnDate(), outputDateFormat),excelWriter.formatDashDot(false,10));
					excelWriter.WriteString(startCol++,startRow,""+record.getProviderId(),excelWriter.formatDashDot(false,10));
					excelWriter.WriteLong(startCol++,startRow,record.getBeginAmount(),excelWriter.longFormatDashDot(false,10));
					excelWriter.WriteLong(startCol++,startRow,record.getTotalAmount(),excelWriter.longFormatDashDot(false,10));
					excelWriter.WriteLong(startCol++,startRow,record.getUsedAmount(),excelWriter.longFormatDashDot(false,10));
					excelWriter.WriteLong(startCol++,startRow,record.getEndAmount(),excelWriter.longFormatDashDot(false,10));
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
}
