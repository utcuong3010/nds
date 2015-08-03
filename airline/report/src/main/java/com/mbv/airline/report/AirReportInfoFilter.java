package com.mbv.airline.report;

import java.util.ArrayList;
import java.util.List;

import com.mbv.airline.common.BookingStatus;
import com.mbv.airline.common.info.AirItinerary;
import com.mbv.airline.common.report.AirReportForm;
import com.mbv.airline.common.report.BookingReportInfo;
import com.mbv.airline.common.report.SummaryReportInfo;
import com.mbv.airline.common.report.SummaryReportVendorInfo;

/**
 * 
 * @author phamtuyen
 *
 */
public class AirReportInfoFilter {
	
	public static AirReportForm getAirReportForm(List<AirItinerary> itineraries,int pageSize,int page){
		
		List<BookingReportInfo> bookingReportInfos = new ArrayList<BookingReportInfo>();
		List<SummaryReportVendorInfo> reportVendorInfos = new ArrayList<SummaryReportVendorInfo>();
		
		SummaryReportVendorInfo summaryReportBLInfo = new SummaryReportVendorInfo("BL");
		SummaryReportVendorInfo summaryReportVJInfo = new SummaryReportVendorInfo("VJ");
		SummaryReportVendorInfo summaryReportVNInfo = new SummaryReportVendorInfo("VN");
		
		for(AirItinerary airItinerary : itineraries){		
			BookingReportInfo bookingReportInfo = new BookingReportInfo();		
			bookingReportInfo.setVendor(airItinerary.getFareInfos().get(0).getVendor());
			bookingReportInfo.setReservationCode(airItinerary.getTicketingInfo().getReservationCode());
			bookingReportInfo.setMoney(airItinerary.getTicketingInfo().getAmount());
			bookingReportInfo.setBookingStatus(airItinerary.getTicketingInfo().getStatus());
			bookingReportInfo.setCreateDate(airItinerary.getTicketingInfo().getCreatedDate());
			// add to list
			bookingReportInfos.add(bookingReportInfo);
			
			String vendor = bookingReportInfo.getVendor();
			if(vendor.equals("BL"))
				summaryReportBLInfo = getReportVendorInfo(summaryReportBLInfo,bookingReportInfo);
			else if(vendor.equals("VJ"))
				summaryReportVJInfo = getReportVendorInfo(summaryReportVJInfo,bookingReportInfo);
			else
				summaryReportVNInfo= getReportVendorInfo(summaryReportVNInfo,bookingReportInfo);
			
		}
		// set SummaryReportVendorInfo
		reportVendorInfos.add(summaryReportBLInfo);
		reportVendorInfos.add(summaryReportVJInfo);
		reportVendorInfos.add(summaryReportVNInfo);
		// paging
		int totalTrans = bookingReportInfos.size();
		int fromIndex = getIndexOfList(pageSize, page, "fromIndex", totalTrans);
		int toIndex =  getIndexOfList(pageSize, page, "toIndex", totalTrans);
		// get AirReportForm
		AirReportForm airReportForm = new AirReportForm();
		airReportForm.setSummaryReportInfo(getReportInfo(reportVendorInfos));
		airReportForm.setBookingReportInfos(bookingReportInfos.subList(fromIndex, toIndex));
		airReportForm.setReportVendorInfos(reportVendorInfos); 
		
		return airReportForm; 
	}
	
	/**
	 * get getReportVendorInfo
	 * @param reportVendorInfo
	 * @param bookingReportInfo
	 * @return
	 */
	private static SummaryReportVendorInfo getReportVendorInfo(SummaryReportVendorInfo reportVendorInfo,BookingReportInfo bookingReportInfo){				
		reportVendorInfo.setTotalTrans(reportVendorInfo.getTotalTrans() + 1);		
		reportVendorInfo.setTotalMonney(reportVendorInfo.getTotalMonney() + bookingReportInfo.getMoney());
		
		String bookingStatus = bookingReportInfo.getBookingStatus().toString();
		if(bookingStatus.contains("BOOK")){
			if(bookingStatus.equals(BookingStatus.BOOK_SUCCESS.toString()) || bookingStatus.equals(BookingStatus.BOOK_CANCELED.toString()) || bookingStatus.equals(BookingStatus.BOOK_EXPIRED.toString()))
				reportVendorInfo.setSucceedBooks(reportVendorInfo.getSucceedBooks() + 1);			
			else{
				if(!bookingStatus.equals(BookingStatus.BOOKING_NOT_FOUND.toString()))
					reportVendorInfo.setFailedBooks(reportVendorInfo.getFailedBooks() + 1);
			}				
			if(!bookingStatus.equals(BookingStatus.BOOKING_NOT_FOUND.toString()))
				reportVendorInfo.setTotalBooks(reportVendorInfo.getTotalBooks() + 1);
		}
		else if(bookingStatus.contains("BUY")){
			if(bookingStatus.equals(BookingStatus.BUY_SUCCESS.toString())){
				reportVendorInfo.setSucceedPays(reportVendorInfo.getSucceedPays() + 1);
				reportVendorInfo.setSucceedBooks(reportVendorInfo.getSucceedBooks() + 1);
			}
				
			else{
				reportVendorInfo.setFailedPays(reportVendorInfo.getFailedPays() + 1);
				reportVendorInfo.setSucceedBooks(reportVendorInfo.getSucceedBooks() + 1);
			}
				
			reportVendorInfo.setTotalPays(reportVendorInfo.getTotalPays() + 1);
			reportVendorInfo.setTotalBooks(reportVendorInfo.getTotalBooks() + 1);
		}
		
		return reportVendorInfo;
	}
	
	/**
	 * get SummaryReportInfo
	 * @param reportVendorInfos
	 * @return
	 */
	private static SummaryReportInfo getReportInfo(List<SummaryReportVendorInfo> reportVendorInfos){
		SummaryReportInfo reportInfo = new SummaryReportInfo();		
		for(SummaryReportVendorInfo reportVendorInfo : reportVendorInfos){
			reportInfo.setTotalMonney(reportInfo.getTotalMonney() + reportVendorInfo.getTotalMonney());
			reportInfo.setTotalBooks(reportInfo.getTotalBooks() + reportVendorInfo.getFailedBooks() + reportVendorInfo.getSucceedBooks());
			reportInfo.setTotalPays(reportInfo.getTotalPays() + reportVendorInfo.getFailedPays() + reportVendorInfo.getSucceedPays());
			
			reportInfo.setFailedBooks(reportInfo.getFailedBooks() + reportVendorInfo.getFailedBooks());
			reportInfo.setSucceedBooks(reportInfo.getSucceedBooks()+ reportVendorInfo.getSucceedBooks());
			
			reportInfo.setFailedPays(reportInfo.getFailedPays() + reportVendorInfo.getFailedPays());
			reportInfo.setSucceedPays(reportInfo.getSucceedPays() + reportVendorInfo.getSucceedPays());
			
			reportInfo.setTotalTrans(reportInfo.getTotalTrans() +reportVendorInfo.getTotalTrans()); 
		}		
		return reportInfo;
	}
	
	/**
	 * do: get index of List support paging
	 * @param pageSize
	 * @param page
	 * @param strInput
	 * @param totalTrans
	 * @return
	 */
	private static int getIndexOfList(int pageSize,int page, String strInput,int totalTrans){
		int fromIndex = (page-1)*pageSize;
		int toIndex =  fromIndex + pageSize;		
		if(toIndex > totalTrans)
			toIndex = totalTrans;		
		if(fromIndex > toIndex){
			fromIndex = 0;
			toIndex = totalTrans;
		}	
		if(strInput.equals("fromIndex"))
			return fromIndex;
		return toIndex;
	}
}
