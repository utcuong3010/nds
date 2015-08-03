package com.mbv.airtime.report.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Blank;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelWriter {
	
	private Log log=LogFactory.getLog(ExcelWriter.class);
    WritableSheet   sheet;
    WritableWorkbook copy;
    boolean isClose=true;
	private void copyFile(String tempFileName, String fileName) throws Exception{
		File file = new File(fileName);
		file.delete();
		FileInputStream inStream = new FileInputStream(tempFileName);
		int inBytes = inStream.available();
		byte inBuf[] = new byte[inBytes];
		int bytesRead = inStream.read(inBuf, 0, inBytes);
		FileOutputStream outStream = new FileOutputStream(fileName);
		outStream.write(inBuf);
		outStream.close();
		inStream.close();
	}
	public void open(String tempFileName, String fileName,Integer sheetId) throws Exception {
		if (isClose){
			copyFile(tempFileName, fileName);
			int id=(sheetId==null)?0:sheetId;
			File reportFile = new File(fileName);
			Workbook workbook = Workbook.getWorkbook(reportFile);
			copy =Workbook.createWorkbook(reportFile, workbook);
			sheet = copy.getSheet(id);
			isClose=false;
		}
	}
	
	public void close() {
		try{
			if (!isClose){
				copy.write();
				copy.close();
				isClose=true;
			}
		}catch (Exception e) {
			log.error("Fail to create workbook",e);
		}finally{
			try {
				if (!isClose){
					copy.write();
					copy.close();
					isClose=true;
				}
			} catch (Exception e) {}
		}
	}
	public void WriteString(int col,int row,String labelText,WritableCellFormat format) throws Exception {
		Label label=new Label(col,row,labelText);
		label.setCellFormat(format);
		sheet.addCell(label);
	}
	
	public void WriteDate(int col,int row,Date dateValue,WritableCellFormat format) throws Exception {
		DateTime label=new DateTime(col,row,dateValue);
		label.setCellFormat(format);
		sheet.addCell(label);
	}
	
	public void WriteStringMeger(int startCol,int startRow,int endCol,int endRow,String labelText,WritableCellFormat format) throws Exception {
		Label label=new Label(startCol,startRow,labelText);
		label.setCellFormat(format);
		sheet.addCell(label);
		sheet.mergeCells( startCol, startRow, endCol, endRow);
	}

	public void WriteLong(int col,int row,Long numberValue,WritableCellFormat intformat ) throws Exception {
			Number number=new Number(col,row,numberValue);
			number.setCellFormat(intformat);
			sheet.addCell(number);
	}	
	
	public void WriteFloat(int col,int row,float numberValue,WritableCellFormat intformat ) throws Exception {
		Number number=new Number(col,row,numberValue);
		number.setCellFormat(intformat);
		sheet.addCell(number);
	}
	public void WriteBlank(int col,int row,WritableCellFormat format) throws Exception {
		
		Blank blank=new Blank(col,row);
		blank.setCellFormat(format);
		sheet.addCell(blank);
		}
	public void WriteFormula(int col,int row,String strFormula,WritableCellFormat format) throws Exception {
		Formula formula=new Formula(col,row,strFormula,format);
		sheet.addCell(formula);
	}
	public  WritableCellFormat formatDashDot(boolean isBold,int fontSize) throws Exception {
		WritableCellFormat format = new WritableCellFormat ();
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	public  WritableCellFormat dateFormatDashDot(boolean isBold,int fontSize) throws Exception {
		DateFormat customDateFormat = new DateFormat("dd/MM/yyyy hh:mm:ss"); 
		WritableCellFormat format = new WritableCellFormat (customDateFormat);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	public  WritableCellFormat formatDashDotAlign(boolean isBold,int fontSize,Alignment alignment) throws Exception {
		WritableCellFormat format = new WritableCellFormat ();
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setAlignment(alignment);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat formatDashDotColor(boolean isBold,Colour colour,int fontSize) throws Exception {
		WritableCellFormat format = new WritableCellFormat ();
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBackground(colour);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat longFormatDashDot(boolean isBold,int fontSize) throws Exception {
		WritableCellFormat format = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat intFormatDashDot(boolean isBold,int fontSize,Colour colour) throws Exception {
		WritableCellFormat format = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
		format.setBackground(colour);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.THIN);
		format.setBorder(Border.TOP,BorderLineStyle.THIN);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	public  WritableCellFormat stringFormatDashDot(boolean isBold,int fontSize,Colour colour) throws Exception {
		WritableCellFormat format = new WritableCellFormat();
		format.setBackground(colour);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.THIN);
		format.setBorder(Border.TOP,BorderLineStyle.THIN);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat floatFormatDashDot(boolean isBold,int fontSize) throws Exception {
		WritableCellFormat format = new WritableCellFormat(NumberFormats.THOUSANDS_FLOAT);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat floatFormatDashDot(boolean isBold,int fontSize,Colour colour) throws Exception {
		WritableCellFormat format = new WritableCellFormat(NumberFormats.THOUSANDS_FLOAT);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.THIN);
		format.setBorder(Border.TOP,BorderLineStyle.THIN);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBackground(colour);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, fontSize, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat formatDashDot(boolean isBold) throws Exception {
		WritableCellFormat format = new WritableCellFormat ();
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat formatDashDotColor(boolean isBold,Colour colour) throws Exception {
		WritableCellFormat format = new WritableCellFormat ();
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		format.setBackground(colour);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat intFormatDashDot(boolean isBold) throws Exception {
		WritableCellFormat format = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
	
	public  WritableCellFormat floatFormatDashDot(boolean isBold) throws Exception {
		WritableCellFormat format = new WritableCellFormat(NumberFormats.THOUSANDS_FLOAT);
		format.setBorder(Border.LEFT,BorderLineStyle.THIN);
		format.setBorder(Border.RIGHT,BorderLineStyle.THIN);
		format.setBorder(Border.BOTTOM,BorderLineStyle.HAIR);
		format.setBorder(Border.TOP,BorderLineStyle.HAIR);
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		if (isBold){
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD,false);
			format.setFont(fontx); 
		}else{
			WritableFont fontx = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.NO_BOLD,false);
			format.setFont(fontx); 
		}
		return format;
	}
}