/*
 * ListRepairReportAttributeBuilder.java Created on Dec 03 ,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.listrepairreport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
/**
 * @author A-2883
 * This class is used for Listing Repair Report
 */
public class ListRepairReportAttributeBuilder   extends AttributeBuilderAdapter{
	
	private Log log = LogFactory.getLogger("ListRepairReportAttributeBuilder");
	
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
		reportColumns.add("DMGREFNUM");
		reportColumns.add("RPRHED");
		reportColumns.add("RPRARP");
		reportColumns.add("RPRDAT");
		reportColumns.add("RPRAMT");
		reportColumns.add("RPRRMK");
		reportColumns.add("ULDPRC");
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

		Vector<Vector> listRepairReportDetails = new Vector<Vector>();
		Vector<Object> row = null;
		Iterator iterator = data.iterator();
		
		Map<String, Collection<ULDRepairDetailsListVO>> repairMap =new HashMap<String, Collection<ULDRepairDetailsListVO>>();
		List<ULDRepairDetailsListVO> listVOs = new ArrayList<ULDRepairDetailsListVO>();
		listVOs = new ArrayList<ULDRepairDetailsListVO>(data);
		
		for(ULDRepairDetailsListVO listVO : listVOs){
			
				row = new Vector<Object>();
				if (listVO.getUldNumber() != null) {
					row.add(listVO.getUldNumber());
					
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}
					row.add(listVO.getDamageReferenceNumber());
				
				if(listVO.getRepairHead()!=null){
					row.add(listVO.getRepairHead());
				}else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				
				if(listVO.getRepairedStation()!=null){
					row.add(listVO.getRepairedStation());
				}else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(listVO.getRepairDate()!=null){
					String repairDate = TimeConvertor.toStringFormat(listVO
							.getRepairDate().toCalendar(),
							TimeConvertor.CALENDAR_DATE_FORMAT);
					row.add(repairDate);
					
				}else{
					row.add(ReportConstants.EMPTY_STRING);
				}
					row.add(listVO.getRepairAmount());
				
				if(listVO.getRemarks()!=null){
					row.add(listVO.getRemarks());
				}else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				row.add(listVO.getInitialCost());
				listRepairReportDetails.add(row);
			
				log.log(Log.INFO, " \n xxxxxx", listVO);
		}
		

		log.log(Log.INFO, "uldHistoryDetails", listRepairReportDetails);
		return listRepairReportDetails;
	}

	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		ULDRepairFilterVO uldRepairFilterVO = (ULDRepairFilterVO) dataParameters;
		
		if (uldRepairFilterVO.getUldNumber() != null) {
			reportParameters.add(uldRepairFilterVO.getUldNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getRepairHead() != null) {
			reportParameters.add(uldRepairFilterVO.getRepairHead());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getUldTypeCode() != null) {
			reportParameters.add(uldRepairFilterVO.getUldTypeCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getCurrentStation() != null) {
			reportParameters.add(uldRepairFilterVO.getCurrentStation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getUldStatus() != null) {
			reportParameters.add(uldRepairFilterVO.getUldStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getRepairStatus() != null) {
			reportParameters.add(uldRepairFilterVO.getRepairStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getRepairStation()!= null) {
			reportParameters.add(uldRepairFilterVO.getRepairStation());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getFromDate() != null) {
			String frmDate = TimeConvertor.toStringFormat(uldRepairFilterVO
					.getFromDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(frmDate);
			// reportParameters.add(uldHistoryVO.getFromDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldRepairFilterVO.getToDate() != null) {
			String toDate = TimeConvertor.toStringFormat(uldRepairFilterVO
					.getToDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);
			reportParameters.add(toDate);
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		//Added by A-7359 for ICRD-268766 starts here
		if (uldRepairFilterVO.getCurrency() != null) {
			reportParameters.add(uldRepairFilterVO.getCurrency());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		//Added by A-7359 for ICRD-268766 ends here
		
		

		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}
	
	
	
	
	
}
