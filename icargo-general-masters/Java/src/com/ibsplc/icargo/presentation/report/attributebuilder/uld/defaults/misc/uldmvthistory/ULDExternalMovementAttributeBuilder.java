/*
 * ULDExternalMovementAttributeBuilder.java Created on 28 JUL 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.misc.uldmvthistory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
/**
 * 
 * @author a-3353
 *
 */

public class ULDExternalMovementAttributeBuilder  extends AttributeBuilderAdapter{
	
	private static final String CLASS_NAME = "ULDExternalMovementAttributeBuilder";
	private Log log = LogFactory.getLogger(CLASS_NAME);
	private static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
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
		columns.add("CARCOD");
		columns.add("FLTNUM");
		columns.add("REGNUM");
		columns.add("FLTDATE");
		columns.add("CONTS");
		columns.add("FRMARPTS");
		columns.add("TOARPT");
		columns.add("MVMNTTYP");
		columns.add("MVMNTDAT");
		columns.add("RMRKS");
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
		Collection<ULDMovementDetailVO> uldMovementDetails = (ArrayList<ULDMovementDetailVO>)data;
		Vector<Vector> reportData = new Vector<Vector>();
		log.log(Log.FINE, "Entering Attribute ---->>", data);
		Object extraInfor = ((ArrayList<OneTimeVO>)extraInfo).get(0);
		Collection<OneTimeVO> contents = (Collection<OneTimeVO>)extraInfor;
				
		if (data != null && data.size() > 0){
		
		for(ULDMovementDetailVO uldMovementDetailVO:uldMovementDetails){
				Vector<Object> row = new Vector<Object>();
				
				row.add(uldMovementDetailVO.getCarrierCode()!=null?uldMovementDetailVO.getCarrierCode():ReportConstants.EMPTY_STRING);
				row.add(uldMovementDetailVO.getFlightNumber()!=null?uldMovementDetailVO.getFlightNumber():ReportConstants.EMPTY_STRING);
				row.add(uldMovementDetailVO.getRegdNumber()!=null?uldMovementDetailVO.getRegdNumber():ReportConstants.EMPTY_STRING);
				//Modified by A-8368 as part of bug - IASCB-36861
				row.add(uldMovementDetailVO.getFlightDate()!=null?TimeConvertor.toStringFormat(uldMovementDetailVO.getFlightDate().toCalendar(),
						TimeConvertor.ADVANCED_DATE_FORMAT):ReportConstants.EMPTY_STRING);
				for (OneTimeVO oneTimeVO : contents) {
					//if check added for bug 56634 on 14Aug09
					//Exception generated if content code is null
					if (uldMovementDetailVO.getContent() != null &&
							uldMovementDetailVO.getContent().trim().length() >0){
						if (uldMovementDetailVO.getContent().equals(
								oneTimeVO.getFieldValue())) {
							uldMovementDetailVO.setContent(oneTimeVO
									.getFieldDescription());
						} 
					}
				}
				row.add(uldMovementDetailVO.getContent()!=null?uldMovementDetailVO.getContent():ReportConstants.EMPTY_STRING);
				row.add(uldMovementDetailVO.getPointOfLading()!=null?uldMovementDetailVO.getPointOfLading():ReportConstants.EMPTY_STRING);
				row.add(uldMovementDetailVO.getPointOfUnLading()!=null?uldMovementDetailVO.getPointOfUnLading():ReportConstants.EMPTY_STRING);
				row.add(uldMovementDetailVO.getIsDummyMovement()?"DummyMovement":"NormalMovement");
				row.add(uldMovementDetailVO.getLastUpdatedTime()!=null?TimeConvertor.toStringFormat(uldMovementDetailVO.getLastUpdatedTime().
															toCalendar(),DATE_FORMAT):ReportConstants.EMPTY_STRING);
				row.add(uldMovementDetailVO.getRemark()!=null?uldMovementDetailVO.getRemark():ReportConstants.EMPTY_STRING);
				reportData.add(row);
		}
		}return reportData;
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
		ULDMovementFilterVO uldMovementFilterVO =(ULDMovementFilterVO)dataParameters;
		reportParameters.add(uldMovementFilterVO.getUldNumber()!=null?uldMovementFilterVO.getUldNumber():ReportConstants.EMPTY_STRING);
		reportParameters.add(uldMovementFilterVO.getFromDate()!=null?uldMovementFilterVO.getFromDate().toDisplayDateOnlyFormat():ReportConstants.EMPTY_STRING);
		 reportParameters.add(uldMovementFilterVO.getToDate()!=null?uldMovementFilterVO.getToDate().toDisplayDateOnlyFormat():ReportConstants.EMPTY_STRING);
		Object txnParameters = ((ArrayList<Object>)parameters).get(1);
		ULDNumberVO uldNumberVO = (ULDNumberVO)txnParameters;
		
		reportParameters.add(String.valueOf(uldNumberVO.getNoOfMovements()));
		reportParameters.add(String.valueOf(uldNumberVO.getNoOfLoanTxns()));
		reportParameters.add(String.valueOf(uldNumberVO.getNoOfTimesDmged()));
		reportParameters.add(String.valueOf(uldNumberVO.getNoOfTimesRepaired()));
		
		return reportParameters;
	}	
}
