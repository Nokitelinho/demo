/*
 * ULDDiscrepancyAttributeBuilder.java Created on Mar 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author a-3045
 *
 */
public class ULDDiscrepancyAttributeBuilder extends AttributeBuilderAdapter{


private Log log = LogFactory.getLogger("ULDDiscrepancyAttributeBuilder");

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
		reportColumns.add("DISCOD");
		reportColumns.add("DISDAT");
		reportColumns.add("RPTARP");
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
		Vector<Vector> uldDiscrepancyReportDetails = new Vector<Vector>();
		Vector<Object> row = null;
		Iterator iterator = data.iterator();
		Map<String, Collection<ULDDiscrepancyVO>> discrepancyMap =new HashMap<String, Collection<ULDDiscrepancyVO>>();
		List<ULDDiscrepancyVO> uldDiscrepancyVOs = new ArrayList<ULDDiscrepancyVO>();
		uldDiscrepancyVOs = new ArrayList<ULDDiscrepancyVO>(data);

		for(ULDDiscrepancyVO uldDiscrepancyVO : uldDiscrepancyVOs){
			row = new Vector<Object>();
			if (uldDiscrepancyVO.getUldNumber() != null) {
				row.add(uldDiscrepancyVO.getUldNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldDiscrepancyVO.getDiscrepencyCode()!=null){
				row.add(uldDiscrepancyVO.getDiscrepencyCode());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldDiscrepancyVO.getDiscrepencyDate()!=null){
				String discrepencyDate = TimeConvertor.toStringFormat(uldDiscrepancyVO
						.getDiscrepencyDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT);
				row.add(discrepencyDate);

			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldDiscrepancyVO.getReportingStation()!=null){
				row.add(uldDiscrepancyVO.getReportingStation());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldDiscrepancyVO.getRemarks()!=null){
				row.add(uldDiscrepancyVO.getRemarks());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			uldDiscrepancyReportDetails.add(row);
			log.log(Log.INFO, " \n xxxxxx", uldDiscrepancyVO);
		}
		log.log(Log.INFO, "uldHistoryDetails", uldDiscrepancyReportDetails);
		return uldDiscrepancyReportDetails;
	}

	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		ULDDiscrepancyFilterVO uldDiscrepancyFilterVO = (ULDDiscrepancyFilterVO) dataParameters;
		if (uldDiscrepancyFilterVO.getFromDate() != null) {
			String frmDate = TimeConvertor.toStringFormat(uldDiscrepancyFilterVO
					.getFromDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(frmDate);
			// reportParameters.add(uldHistoryVO.getFromDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldDiscrepancyFilterVO.getTodate() != null) {
			String toDate = TimeConvertor.toStringFormat(uldDiscrepancyFilterVO
					.getTodate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(toDate);
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldDiscrepancyFilterVO.getUldNumber() != null) {
			reportParameters.add(uldDiscrepancyFilterVO.getUldNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldDiscrepancyFilterVO.getAirlineCode() != null) {
			reportParameters.add(uldDiscrepancyFilterVO.getAirlineCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldDiscrepancyFilterVO.getReportingStation() != null) {
			reportParameters.add(uldDiscrepancyFilterVO.getReportingStation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}


}
