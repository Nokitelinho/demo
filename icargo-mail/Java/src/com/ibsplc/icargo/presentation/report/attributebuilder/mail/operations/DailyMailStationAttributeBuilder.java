/*
 * DailyMailStationAttributeBuilder.java Created on FEB 29, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationReportVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-3353
 *
 */
public class DailyMailStationAttributeBuilder extends AttributeBuilderAdapter{
	private static final String CLASS_NAME = "MailDamageReportAttributeBuilder";
	private Log log = LogFactory.getLogger(CLASS_NAME);
/**
 * Method to construct the report column names. The column names corresponds
 * to the column names of the view used while laying out the report. The
 * order of the column names should match the order in which the database
 * fields are laid out in the report
 * @param parameters
 * @param extraInfo
 * @return Vector<String> the column names
 */
@Override
public Vector<String> getReportColumns() {
	
	Vector<String> columns = new Vector<String>();
	columns.add("FLTNUM");
	columns.add("ULDNUM");
	columns.add("DESTN");
	columns.add("NTWGT");
	columns.add("GRWGT");
	columns.add("RMRKS");
	columns.add("BAGCNT");
	return columns;
	
}
/**
 * Method to construct the report data. Each row in the details section of
 * the report corresponds to one element in the outer Vector. Each element
 * in the inner Vector corresponds to a field in the report. The order in
 * which the data is returned should match the order in which the fields are
 * laid out in the report
 * @param data
 * @param extraInfo
 * @return Vector<Vector> the reportData
 */
@Override
public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
	List<DailyMailStationReportVO> dailyMailStationReportVOS = (ArrayList<DailyMailStationReportVO>)data;		
	Vector<Vector> reportData = new Vector<Vector>();
	log.log(Log.FINE, "Entering Attribute ---->>", data);
	if (data != null && data.size() > 0){
	
		for(DailyMailStationReportVO dailyMailStationReportVO:dailyMailStationReportVOS){
			Vector<Object> row = new Vector<Object>();
			dailyMailStationReportVO.setCarrierCode(dailyMailStationReportVO.getCarrierCode()!=null?dailyMailStationReportVO.getCarrierCode():ReportConstants.EMPTY_STRING);
			dailyMailStationReportVO.setFlightNumber(dailyMailStationReportVO.getFlightNumber()!=null?dailyMailStationReportVO.getFlightNumber():ReportConstants.EMPTY_STRING);
			String str = new StringBuilder().append(dailyMailStationReportVO.getCarrierCode())
		       .append(" ").append(dailyMailStationReportVO.getFlightNumber()).toString();
			row.add((str!= null)?str:ReportConstants.EMPTY_STRING);
			
			row.add((dailyMailStationReportVO.getUldnum()!= null)
					?dailyMailStationReportVO.getUldnum()
					:ReportConstants.EMPTY_STRING);
			row.add((dailyMailStationReportVO.getDestination()!= null) ?dailyMailStationReportVO.getDestination():ReportConstants.EMPTY_STRING);
			//Added by A-7794 as part of ICRD-249591
			Float wt = new Float(dailyMailStationReportVO.getNetweight().getRoundedDisplayValue());
			row.add( wt != null ? String.valueOf(wt) :ReportConstants.EMPTY_STRING);
			wt = new Float(dailyMailStationReportVO.getGrossweight().getRoundedDisplayValue());
			row.add( wt != null ? (String.valueOf(wt)) :ReportConstants.EMPTY_STRING);
			row.add((dailyMailStationReportVO.getRemark()!= null)?dailyMailStationReportVO.getRemark():ReportConstants.EMPTY_STRING);
			row.add((dailyMailStationReportVO.getBagCount()!= null)?dailyMailStationReportVO.getBagCount():ReportConstants.EMPTY_STRING);
			reportData.add(row);
		}
	
	}
	log.log(Log.FINE, "Exiting Attribute ---->>", reportData);
	return reportData;
}
/**
 * Method to construct the report parameters. The report parameters
 * corresponds to the parameter fields in the report. The order of the
 * parameters should match the order in which the parameter fields are laid
 * out in the report
 * @param parameters the parameter data
 * @param extraInfo information required for formatting the parameters
 * @return Vector the report parameters
 */
@Override
public Vector<Object> getReportParameters(
						Collection parameters, Collection extraInfo) {
	
	Vector<Object> reportParameters = new Vector<Object>();		
	log.log(Log.FINE, "parameters..", parameters);
	log.log(Log.FINE, "extraInfo..", extraInfo);
	Object dataParameters = ((ArrayList<Object>)parameters).get(0);
	
	DailyMailStationFilterVO dailyMailStationFilterVO = (DailyMailStationFilterVO)dataParameters;
	
	//reportParameters.add((dailyMailStationFilterVO.getFilghtDate()!= null)?dailyMailStationFilterVO.getFilghtDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
	
	
	dailyMailStationFilterVO.setCarrierCode(dailyMailStationFilterVO.getCarrierCode()!=null?dailyMailStationFilterVO.getCarrierCode():ReportConstants.EMPTY_STRING);
	dailyMailStationFilterVO.setFlightNumber(dailyMailStationFilterVO.getFlightNumber()!=null?dailyMailStationFilterVO.getFlightNumber():ReportConstants.EMPTY_STRING);
	
	String str = new StringBuilder().append(dailyMailStationFilterVO.getCarrierCode())
    .append(" ").append(dailyMailStationFilterVO.getFlightNumber()).toString();
	reportParameters.add((str!= null)?str:ReportConstants.EMPTY_STRING);
	reportParameters.add((dailyMailStationFilterVO.getDestination()!= null)?dailyMailStationFilterVO.getDestination():ReportConstants.EMPTY_STRING);
	reportParameters.add((dailyMailStationFilterVO.getFlightFromDate()!= null)?dailyMailStationFilterVO.getFlightFromDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
	reportParameters.add((dailyMailStationFilterVO.getFlightToDate()!= null)?dailyMailStationFilterVO.getFlightToDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
	
	return reportParameters;
}	
}
