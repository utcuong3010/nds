package com.mbv.airtime.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.write.WritableCellFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.mbv.airtime.report.constant.ReportConstant;
import com.mbv.airtime.report.model.TxnInfoModel;
import com.mbv.airtime.report.utils.DateUtil;
import com.mbv.airtime.report.utils.ExcelWriter;
import com.mbv.airtime.report.utils.ReportUtil;
import com.mbv.airtime.report.utils.DateUtil.Type;
import com.mbv.bp.common.dao.airtime.AtTransactionDao;
//import com.mbv.bp.common.util.DateUtil;
//import com.mbv.bp.common.util.DateUtil.Type;
import com.mbv.bp.common.vo.airtime.AtTransaction;
import com.mbv.bp.common.vo.airtime.AtTransactionFilter;

public class TxnInfoReport extends BaseReport {
	
//	private String inputDateTimeFormat="yyyy-MM-dd";
	private String inputDateTimeFormat="yyyy-MM-dd HH:mm:ss";
	private String outputDateFormat="dd/MM/yyyy HH:mm:ss";
	private AtTransactionFilter filter;
	
	@Override
	public void processInputParams() {
		try{
			String inputParams=propertyMap.get(ReportConstant.ATTR_INPUT_PARA);
			validate(TxnInfoModel.build(inputParams));
		}catch (Exception e) {
			log.error("Invalid request.",e);
			addproperty(ReportConstant.ATTR_ERROR_MSG, e.getMessage());
			addproperty(ReportConstant.ATTR_ERROR_CODE, ReportConstant.STATUS_ERROR);
		}
	}

	private void validate(TxnInfoModel model) throws Exception {
		filter=new AtTransactionFilter();
		
		if (StringUtils.isNotBlank(model.getConnType()))
			filter.setConnType(model.getConnType().trim());
		
		if (StringUtils.isNotBlank(model.getMessageId()))
			filter.setMessageId(model.getMessageId().trim());
		
		if (StringUtils.isNotBlank(model.getChannelId()))
			filter.setChannelId(model.getChannelId().trim());
		
		if (StringUtils.isNotBlank(model.getTxnId()))
			filter.setTxnId(model.getTxnId().trim());
		
		if (StringUtils.isNotBlank(model.getTelcoId()))
			filter.setTelcoId(model.getTelcoId().trim());
		
		if (StringUtils.isNotBlank(model.getAtTxnId())){
			if (StringUtils.isNumeric(model.getAtTxnId().trim())&& model.getAtTxnId().trim().length()==18)
				filter.setAtTxnId(Long.valueOf(model.getAtTxnId().trim()));
			else
				throw new Exception("Ma giao dich he thong khong chinh xac");
		}
		
		if (StringUtils.isNotBlank(model.getMsisdn()))
			filter.setMsisdn(model.getMsisdn().trim());
		
		if (StringUtils.isNotBlank(model.getAmount())){
			if (StringUtils.isNumeric(model.getAmount().trim().replace(".", "")))
				filter.setAmount(Integer.valueOf(model.getAmount().replace(".", "").trim()));
			else{
				throw new Exception("Menh gia khong chinh xac");
			}
		}
		
		if (StringUtils.isNotBlank(model.getTxnStatus())){
			filter.setTxnStatus(model.getTxnStatus());
		}
		
		if (StringUtils.isNotBlank(model.getFromDate())){
			Date fromDate=DateUtil.convertStringToDate(model.getFromDate().trim(),inputDateTimeFormat);
				if (fromDate!=null)
					filter.setFromDate(fromDate);
				else
					throw new Exception("du lieu 'Tu Ngay' khong chinh xac");
					
		}

		if (StringUtils.isNotBlank(model.getToDate())){
			Date toDate=DateUtil.convertStringToDate(model.getToDate().trim(),inputDateTimeFormat);
				if (toDate!=null){
					filter.setToDate(toDate);
					if (filter.getFromDate()!=null)
					if (DateUtil.dateDiff(Type.BY_MILLISECOND, filter.getFromDate(), filter.getToDate())<0)
						throw new Exception("'Tu Ngay' khong the lon hon 'Den ngay'");
					if (DateUtil.dateDiffGMT2GMT7(filter.getToDate(), new Date())<0)
						throw new Exception("'Den Ngay' khong the lon hon 'Ngay Hien Tai'");
				}else
					throw new Exception("'Den Ngay' khong chinh xac");
		}
		
		if ((filter.getFromDate()==null && filter.getToDate()!=null)||((filter.getFromDate()!=null && filter.getToDate()==null)))
			throw new Exception("Du lieu 'Tu Ngay' 'Den Ngay' khong duoc nhap day du");
		
	}

	@Override
	public void process() {
		if (ReportConstant.STATUS_ERROR.equalsIgnoreCase(propertyMap.get(ReportConstant.ATTR_ERROR_CODE)))	
			return;
		ExcelWriter excelWriter=new ExcelWriter();
		try{
			AtTransactionDao dao=new AtTransactionDao(sqlMapClient);
			String reportTemp=propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_TEMP_FILE_NAME);
			String reportFile=propertyMap.get(ReportConstant.ATTR_REPORT_DIR)+propertyMap.get(ReportConstant.ATTR_REPORT_FILE_NAME);
			excelWriter.open(reportTemp,reportFile, null);
			
			if (propertyMap.containsKey(ReportConstant.ATTR_QUERY_RECORD_SIZE))
				filter.setRecordSize(Integer.valueOf(propertyMap.get(ReportConstant.ATTR_QUERY_RECORD_SIZE)));
			else filter.setRecordSize(100);
			
			int startReccord=0;
			List<AtTransaction> list;
			int startRow=4;
			int startCol=0;
			log.info(filter);
			
			WritableCellFormat longFormat=excelWriter.longFormatDashDot(false,10);
			WritableCellFormat dateFormat=excelWriter.dateFormatDashDot(false,10);
			WritableCellFormat txtFormat=excelWriter.formatDashDot(false,10);
			
			do{
				filter.setStartRecord(startReccord);
				list= dao.search(filter);
				if (list==null) list=new ArrayList<AtTransaction>();
				for(int i=0;i<list.size();i++){
					AtTransaction atTransaction=list.get(i);
					startRow++;
					startCol=0;
					excelWriter.WriteLong(startCol++,startRow,new Long(startReccord+i+1),longFormat);
					excelWriter.WriteDate(startCol++,startRow,DateUtils.addHours(atTransaction.getTxnDate(), 7),dateFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getAtTxnId(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getChannelId(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getTxnId(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getTelcoId(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getMsisdn(),txtFormat);
					excelWriter.WriteLong(startCol++,startRow,new Long(atTransaction.getAmount()),longFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getConnType(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getMessageId(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+ReportUtil.getTextFromStatus(atTransaction.getTxnStatus()),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getTxnStatus(),txtFormat);
					excelWriter.WriteString(startCol++,startRow,""+atTransaction.getErrorCode(),txtFormat);
				}
				startReccord+=list.size();
			}while (list.size()==filter.getRecordSize());
			log.info("Report created | total record-"+startReccord);
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


