/*
 * ULDHistoryAttributeBuilder.java Created on Oct 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.misc.uldhistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/*
 * RevisionHistory
 * -------------------------------------------------------------------------
 * Version Date Author Description
 * ------------------------------------------------------------------------ 0.1
 * Oct 23 , 2007 KumarPrashant Created
 */

/**
 * @author A-2619
 * 
 */
public class ULDHistoryAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("ULDHistoryAttributeBuilder");

	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * 
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> reportColumns = new Vector<String>();

		reportColumns.add("ULDNUM");
		reportColumns.add("DAT");
		reportColumns.add("STA");
		reportColumns.add("FRMARP");
		reportColumns.add("TOOARP");
		reportColumns.add("CARIDR");
		reportColumns.add("FLTNUM");
		reportColumns.add("RMK");
		log.log(Log.FINE, "reportColumns is --------->", reportColumns);
		return reportColumns;
	}

	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * 
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		Vector<Vector> uldHistoryDetails = new Vector<Vector>();
		// Collection<ULDHistoryVO> uldTransctionDetails = new
		// ArrayList<ULDHistoryVO>(data);

		// log.log(Log.INFO,"%%%%%%%%%%%%%uldTransctionDetails
		// "+uldTransctionDetails);

		Vector<Object> row = null;
		// for(ULDHistoryVO uldHistoryVO : uldTransctionDetails){
		Iterator iterator = data.iterator();

		while (iterator.hasNext()) {
			ULDHistoryVO uldHistoryVO = (ULDHistoryVO) iterator.next();

			log.log(Log.INFO, "%%%%%%%%%%%%%transactionDetailsVO ",
					uldHistoryVO);
			row = new Vector<Object>();

			if (uldHistoryVO.getUldNumber() != null) {
				row.add(uldHistoryVO.getUldNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getUldTransactionDate() != null) {
				String uldTxnDate = TimeConvertor.toStringFormat(uldHistoryVO
						.getUldTransactionDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT);
				row.add(uldTxnDate);
				
log.log(Log.INFO, "%%%%%%%%%%%%%uldTxnDate----------->> ",
						uldTxnDate);
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getUldStatus() != null) {
				row.add(uldHistoryVO.getUldStatus());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getFromStation() != null) {
				row.add(uldHistoryVO.getFromStation());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getToStation() != null) {
				row.add(uldHistoryVO.getToStation());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getCarrierCode() != null) {
				row.add(uldHistoryVO.getCarrierCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getFlightNumber() != null) {
				row.add(uldHistoryVO.getFlightNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldHistoryVO.getRemarks() != null) {
				row.add(uldHistoryVO.getRemarks());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			uldHistoryDetails.add(row);
		}

		log.log(Log.INFO, "uldHistoryDetails", uldHistoryDetails);
		return uldHistoryDetails;
	}

	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();

		Object dataParameters = ((ArrayList<Object>) extraInfo).get(0);
		ULDHistoryVO uldHistoryVO = (ULDHistoryVO) dataParameters;

		if (uldHistoryVO.getUldNumber() != null) {
			reportParameters.add(uldHistoryVO.getUldNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getUldStatus() != null) {
			reportParameters.add(uldHistoryVO.getUldStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getFromDate() != null) {
			String frmDate = TimeConvertor.toStringFormat(uldHistoryVO
					.getFromDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(frmDate);
			// reportParameters.add(uldHistoryVO.getFromDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getToDate() != null) {
			String toDate = TimeConvertor.toStringFormat(uldHistoryVO
					.getToDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(toDate);
			// reportParameters.add(uldHistoryVO.getToDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getCarrierCode() != null) {
			reportParameters.add(uldHistoryVO.getCarrierCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getFlightNumber() != null) {
			reportParameters.add(uldHistoryVO.getFlightNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getFlightDate() != null) {
			String flightDate = TimeConvertor.toStringFormat(uldHistoryVO
					.getFlightDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(flightDate);
			// reportParameters.add(uldHistoryVO.getFlightDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldHistoryVO.getFromStation() != null) {
			reportParameters.add(uldHistoryVO.getFromStation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
