/**
 * 
 */
package com.ibsplc.icargo.presentation.report.helper.products.defaults;

import java.util.Collection;

import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1747
 *
 */
public class ProductPerformanceReportHelper {
	
	@Helper(
			{
				@Help(
						reportId = "RPRPRD002",
						voNames = {"com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO",
								"com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO"},									
						fieldNames = {"fromDate","toDate"}
				)
			}
	) 
	/**
	 * Static method to perform the conversion of Local Date to String
	 */
	public static String formatFromDate(Object value, Collection extraInfo) {

		//System.out.println("ReportHelper.formatdate()");
		String date="";
		LocalDate localDate = (LocalDate)value;
		if((value)!=null){
			date=TimeConvertor.toStringFormat
					(localDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
		}
		//System.out.println("\n\n\n-----------------Obtained date is------------------>"+date);
		return date;
	}
	
	
	@Helper(
			{
					@Help(
							reportId = "RPRPRD002",
							voNames = {"com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO",
									"com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO"},									
							fieldNames = {"weight","revenue"}
					)
			}
	) 
	/**
	 * Static method to perform the conversion of Local Date to String
	 */
	public static String formatId(Object value, Collection extraInfo) {

		//System.out.println("ReportHelper.formatnumber()");
		String number="";		
		if((value)!=null){
			number=String.valueOf(value);
		}
		//System.out.println("\n\n\n-----------------Obtained number is------------------>"+number);
		return number;
	}
	
	
	
	

}
