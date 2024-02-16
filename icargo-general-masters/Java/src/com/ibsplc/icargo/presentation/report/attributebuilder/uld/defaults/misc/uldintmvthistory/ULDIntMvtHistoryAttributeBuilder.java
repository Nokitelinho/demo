/*
 * ULDIntMvtHistoryAttributeBuilder.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.misc.uldintmvthistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author a-3045
 *
 */
public class ULDIntMvtHistoryAttributeBuilder extends AttributeBuilderAdapter{
	private Log log = LogFactory.getLogger("ULDIntMvtHistoryAttributeBuilder");

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
		reportColumns.add("AGTCOD");
		reportColumns.add("AGTNAM");
		reportColumns.add("CNT");
		reportColumns.add("ARP");
		reportColumns.add("FRMLOC");
		reportColumns.add("TOOLOC");
		reportColumns.add("MVTTYP");
		reportColumns.add("MVTDAT");
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
		Vector<Vector> uldIntMvtReportDetails = new Vector<Vector>();
		Vector<Object> row = null;
		Iterator iterator = data.iterator();
		List<ULDIntMvtDetailVO> uldIntMvtDetailVOs = new ArrayList<ULDIntMvtDetailVO>();
		uldIntMvtDetailVOs = new ArrayList<ULDIntMvtDetailVO>(data);
		for(ULDIntMvtDetailVO uldIntMvtDetailVO : uldIntMvtDetailVOs){
			row = new Vector<Object>();			
			if(uldIntMvtDetailVO.getAgentCode()!=null){
				row.add(uldIntMvtDetailVO.getAgentCode());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (uldIntMvtDetailVO.getAgentName() != null) {
				row.add(uldIntMvtDetailVO.getAgentName());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getContent()!=null){
				row.add(uldIntMvtDetailVO.getContent());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getAirport()!=null){
				row.add(uldIntMvtDetailVO.getAirport());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getFromLocation()!=null){
				row.add(uldIntMvtDetailVO.getFromLocation());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getToLocation()!=null){
				row.add(uldIntMvtDetailVO.getToLocation());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getMvtType()!=null){				
				row.add(uldIntMvtDetailVO.getMvtType());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getMvtDate()!=null){
				String movementDate = TimeConvertor.toStringFormat(uldIntMvtDetailVO
						.getMvtDate(),TimeConvertor.CALENDAR_DATE_FORMAT);
				row.add(movementDate);
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(uldIntMvtDetailVO.getRemark()!=null){
				row.add(uldIntMvtDetailVO.getRemark());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			uldIntMvtReportDetails.add(row);
			log.log(Log.INFO, " \n xxxxxx", uldIntMvtDetailVO);
		}
		log.log(Log.INFO, "uldHistoryDetails", uldIntMvtReportDetails);
		return uldIntMvtReportDetails;
	}

	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {
		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		Object dataParams = ((ArrayList<Object>) parameters).get(1);
		ULDIntMvtHistoryFilterVO uldIntMvtHistoryFilterVO = (ULDIntMvtHistoryFilterVO) dataParameters;
		ULDValidationVO uldValidationVO = (ULDValidationVO) dataParams;
		if (uldIntMvtHistoryFilterVO.getUldNumber() != null) {
			reportParameters.add(uldIntMvtHistoryFilterVO.getUldNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldIntMvtHistoryFilterVO.getFromDate() != null) {
			String frmDate = TimeConvertor.toStringFormat(uldIntMvtHistoryFilterVO
					.getFromDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(frmDate);			
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldIntMvtHistoryFilterVO.getToDate() != null) {
			String toDate = TimeConvertor.toStringFormat(uldIntMvtHistoryFilterVO
					.getToDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(toDate);
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}			
		if (uldValidationVO.getOwnerAirlineCode() != null) {
			reportParameters.add(uldValidationVO.getOwnerAirlineCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldValidationVO.getOwnerStation() != null) {
			reportParameters.add(uldValidationVO.getOwnerStation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldValidationVO.getLocation() != null) {
			reportParameters.add(uldValidationVO.getLocation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
