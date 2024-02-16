package com.ibsplc.icargo.presentation.report.helper.stockcontrol.defaults;


import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.time.TimeConvertor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author A-1747
 *
 */
public class ReservationListingReportHelper {

	@Helper(
			{
					@Help(
							reportId = "RPRSTK002",
							voNames = {"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO",
									"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO"},
							fieldNames = {"reservationFromDate","reservationToDate","expiryFromDate", "expiryToDate", "reservationDate","expiryDate"}
					)
			}
	)
	/**
	 * Static method to perform the conversion of Local Date to String
	 */
	public static String formatFromDate(Object value, Collection extraInfo) {


		String date="";
		LocalDate localDate = (LocalDate)value;
		if((value)!=null){
			date=TimeConvertor.toStringFormat
					(localDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
		}

		return date;
	}

	@Helper(
			{
					@Help(
							reportId = "RPRSTK002",
							voNames = {"com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO"},
							fieldNames = {"documentType"}
					)
			}
	)
	/**
	 * Static method to perform the conversion of Local Date to String
	 */
	public static String formatDoctype(Object value, Collection extraInfo) {


		String doctype="";
		Object values= ((ArrayList)extraInfo).get(0);
		Collection<DocumentVO> documentVOs = (Collection<DocumentVO>)values;
		for(DocumentVO documentVO:documentVOs){
			   if(documentVO.getDocumentSubType().equals(value)){
				   doctype = documentVO.getDocumentSubTypeDes();
			   }
		   }

		return doctype;
	}



}
