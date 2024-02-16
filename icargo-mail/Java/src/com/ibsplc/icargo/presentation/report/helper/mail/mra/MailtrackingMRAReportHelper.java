/*
 * MailtrackingMRAReportHelper.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.helper.mail.mra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author a-2401
 *
 */
public class MailtrackingMRAReportHelper {
	private Log log = LogFactory.getLogger("MRA Helper");
	
	/**
	 * formatFlightDate helper class
	 * @param Object
	 * @param Collection
	 * @return String
	 * throws
	 *
	 */	
@Helper(
		{
				@Help(
						reportId = "RPTMRA031",
						voNames = {"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO"},
						fieldNames = {"flightDate"}
				),

				@Help(
						reportId = "RPTMRA032",
						voNames = {"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO"},
						fieldNames = {"flightDate"}
				),
				@Help(
						reportId = "RPTMRA015",
						voNames = {"com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO"},
						fieldNames = {"despatchDate"}
				),
				@Help(
						reportId = "RPTMRA009",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO"},
						fieldNames = {"reportingPeriodFrom","reportingPeriodTo","assignedDate","resolvedDate"}
				),
				@Help(
						reportId = "RPTMRA033",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO"},
						fieldNames = {"invoiceDate"}
				),
				@Help(
						reportId = "RPTMRA037",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO"},
						fieldNames = {"receivedDate"}
				),
				@Help(
						reportId = "RPTMRA038",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO"},
						fieldNames = {"receivedDate"}
				),
				@Help(
						reportId = "RPTMRA008",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO"},
						fieldNames = {"reportingPeriodFrom","reportingPeriodTo"}
				),
				@Help(
						reportId = "RPTMRA039",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO"},
						fieldNames = {"receivedDate"}
				),
				@Help(
						reportId = "RPTMRA007",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO"},
						fieldNames = {"reportingPeriodFrom","reportingPeriodTo"}
				),
				@Help(
						reportId = "RPTMRA040",
						voNames = {"com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoInInvoiceVO"},
						fieldNames = {"memoDate"}
				),
				@Help(
						reportId = "RPTMRA006",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO"},
						fieldNames = {"reportingPeriodFrom","reportingPeriodTo"}
				),
				@Help(
						reportId = "RPTMRA021",
						voNames = {"com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO"},
						fieldNames = {"assignedDate"}
				),
				//Added as part of ICRD-111958 starts
				@Help(reportId = "RPTMRA044",
					   voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO"},
					   fieldNames = {"receivedDate"}),
			    @Help(reportId = "RPRMTK090",
					  voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
							fieldNames = {"flightDate"}),	
				@Help(reportId = "RPRMTK088",
					  voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
							fieldNames = {"flightDate"}),		
				//Added as part of ICRD-111958 ends				
				@Help(
						reportId = "RPTMRA001",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
						fieldNames = {"receivedDate"}
				)
							
				,
				/**
				 * @author a-3447 for bug 27969
				 */
				@Help(
						reportId = "RPTMRA001",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
						fieldNames = {"receivedDate"}
				),
				@Help(
						reportId = "RPRMTK084",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
						fieldNames = {"receivedDate"}
				),
				/**
				 * @author A-7794 as part of ICRD-234354
				 */
				@Help(
						reportId = "RPTMRA046",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
						fieldNames = {"flightDate"}
				)
				
			}
		
		
		
)

/**
 * @param value
 * @param extraInfo
 * @return String
 */
public static String formatFlightDate(Object value, Collection extraInfo) {
	String date="";
	LocalDate localDate = (LocalDate)value;
	if((value)!=null){
		date=TimeConvertor.toStringFormat
				(localDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT).toUpperCase();
	}
	return date;

}

@Helper(		
	{ @Help(reportId = "RPTMRA006", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO", 
						"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO" },
			fieldNames = { "reportingPeriodFrom", "reportingPeriodTo" }),
	  @Help(reportId = "RPTMRA007", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO", 
						"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO" },
			fieldNames = { "reportingPeriodFrom", "reportingPeriodTo" }),
	  @Help(reportId = "RPTMRA008", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO", 
						"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO" },
			fieldNames = { "reportingPeriodFrom", "reportingPeriodTo" }),
	  @Help(reportId = "RPTMRA009", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO", 
						"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO" },
			fieldNames = { "reportingPeriodFrom", "reportingPeriodTo" }),				
	  @Help(reportId = "RPTMRA029", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO", 
						"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO" },
			fieldNames = { "fromDate", "toDate" }),
	  @Help(reportId = "RPTMRA030", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO", 
						"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO" },
			fieldNames = { "fromDate", "toDate" }),		
	  @Help(reportId = "RPTMRA033", 
			voNames = { "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO", 
							"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO" },
			fieldNames = { "fromDate", "toDate" }),
	  @Help(reportId = "RPTMRA034", 
		    voNames = { "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO", 
							"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsFilterVO" },
			fieldNames = { "fromDate", "toDate" }),
	  @Help(reportId = "RPTMRA035", 
		    voNames = { "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO", 
							"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO" },
			fieldNames = { "fromDate", "toDate" }),
	  @Help(reportId = "RPTMRA036", 
		    voNames = { "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO", 
	  						"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO" },
			fieldNames = { "fromDate", "toDate" }),
	  @Help(reportId = "RPTMRA037", 
		    voNames = { "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO", 
							"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO" },
			fieldNames = { "fromDate", "toDate" }),
	  @Help(reportId = "RPTMRA038", 
		    voNames = { "com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO", 
							"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintFilterVO" },
			fieldNames = { "fromDate", "toDate" })
	})
/**
 * This method is used to convert the Date type from LocalDate to String.
 * @param value - LocalDate
 * @param extraInfo - not initialized. Empty collection.
 * @return String - String format of LocalDate. 
 */	
public static String formatDate(Object value, Collection<Object> extraInfo) {
	String fromDate = "";
	LocalDate localDate = (LocalDate) value;
	if ((value) != null) {
		fromDate = localDate.toDisplayDateOnlyFormat();
	}
	return fromDate;
}


/**
 * @author A-2408
 * @param value
 * @param extraInfo
 * @return
 */
@Helper(
		{
				/*@Help(
						reportId = "RPTMRA035",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO"},
						fieldNames = {"mailCategoryCode"}
				),
				@Help(
						reportId = "RPTMRA036",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO"},
						fieldNames = {"mailCategoryCode"}
				),
				Commented by A-7929 for ICRD-260958   */ 
				@Help(
						reportId = "RPTMRA029",
						voNames = {"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO"},
						fieldNames = {"flightStatus"}
				),
				@Help(
						reportId = "RPTMRA037",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO"},
						fieldNames = {"mailCategoryCode"}
				),
				@Help(
						reportId = "RPTMRA038",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsPrintVO"},
						fieldNames = {"mailCategoryCode"}
				),
				@Help(
						reportId = "RPTMRA009",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
				   		   		   "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO"},
						fieldNames = {"exceptionCode","exceptionCode"}
				),
				@Help(
						reportId = "RPTMRA008",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
				   		   		   "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO"},
						fieldNames = {"exceptionCode","exceptionCode"}
				),
				@Help(
						reportId = "RPTMRA039",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO",
								"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO", 
								"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO"},
						fieldNames = {"billingStatus","mailCategoryCode", "billingStatus"}
				),
				//Added as part of ICRD-111958 starts
				@Help(
						reportId = "RPTMRA044",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO"},
						fieldNames = {"mailCategoryCode"}
				),
				//Added as part of ICRD-111958 ends	
				@Help(
						reportId = "RPTMRA007",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
						   		   "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO"},
						fieldNames = {"exceptionCode","exceptionCode"}
				),
				@Help(
						reportId = "RPTMRA006",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO",
								   "com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO"},
						fieldNames = {"exceptionCode","exceptionCode"}
				),
				@Help(
						reportId = "RPTMRA001",
						voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO"},
						fieldNames = {"mailCategoryCode"}
				),
				@Help(
						reportId = "RPTMRA021",
						voNames = {"com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO"},
						fieldNames = {"memStaus"}
				),
				@Help(
						reportId = "RPTMRA029",
						voNames = {"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO",
								"com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO"},
						fieldNames = {"flightStatus","segmentStatus"}
				)
		}
)
public static String formatOneTime(Object value,Collection extraInfo){
	String description = "";
	if(extraInfo!=null && value!=null){
	Map<String,Collection<OneTimeVO>> oneTimeData =
		((ArrayList<Map<String,Collection<OneTimeVO>>>)extraInfo).get(0);
	
	String code = (String)value;
	
	for (Collection<OneTimeVO> oneTimeVos : oneTimeData.values()) {
		for (OneTimeVO oneTimeVo : oneTimeVos) {
			if(oneTimeVo.getFieldValue().equals(code)){
				
				description = oneTimeVo.getFieldDescription();
				
			}
		}
	}
	}
	return description;

	}
//Added by A-7929 as part of ICRD-257574 starts...

@Helper(
		{
			@Help(
					reportId = "RPTMRA033",
					voNames = {"com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO","com.ibsplc.icargo.business.mail.mra.gpabilling.vo.BillingSummaryDetailsVO"},
					fieldNames = {"grandTotal","sumGrandTotal"}
			)
			
		}
)


public static String formatTotalAmount(Object value,Collection extraInfo){
	
		Double grandTotalIndouble = (Double)value;
		 String roundedGrandTotal = null;
		 Money grandTotalMoney = null;
		 Map<String,String> sysParMap =
					((ArrayList<Map<String,String>>)extraInfo).get(0);
		 
		 String currencyCode = sysParMap.get("shared.airline.basecurrency");
        try {
              grandTotalMoney = CurrencyHelper.getMoney(currencyCode);
  
        	if(currencyCode!=null && currencyCode.length()>0)
        	{
              grandTotalMoney.setAmount(grandTotalIndouble);
              roundedGrandTotal = CurrencyHelper.getDisplayValueForReports(grandTotalMoney);
               
        	}
        } catch (CurrencyException e) {
        	e.getErrorCode(); 
        }
        
	return roundedGrandTotal;
	
}
//Added by A-7929 as part of ICRD-257574 ends...
}
/*
@Helper(
				@Help(
						reportId = "RPTMRA016",
						voNames = {"com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO"},
						fieldNames = {"totalamountincontractcurrency"}
				)
		)
public static double formatMoney(Object value, Collection extraInfo) {

		double doubleAmount=0.0;

		if (null!=value){
			Money amount=(Money)value;
			doubleAmount=amount.getRoundedAmount();
		}
		return doubleAmount;
	}
}
*/