/*
 * ListDamageReportAttributeBuilder.java Created on Mar 20, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageFilterVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-2883
 *
 */
public class ListDamageReportAttributeBuilder extends AttributeBuilderAdapter {

	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	
	private Log log = LogFactory.getLogger("ListDamageReportAttributeBuilder");
	
	private static final String ALL="ALL";
	
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> columns = new Vector<String>();

		columns.add("ULDNUM");
		columns.add("DMGREFNUM");
		columns.add("DMGSEC");
		columns.add("DMGDES");
		columns.add("RPTSTN");
		columns.add("CURSTN");
		columns.add("DMGRPTDAT");
		columns.add("STATUS");
		columns.add("DMGSTA");
		columns.add("OVLSTA");
		columns.add("RMK");
		columns.add("PTYCOD");
		columns.add("PTYTYP");
		columns.add("LOC");
		return columns;
	}


	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();

		Iterator iterator = data.iterator();

		while(iterator.hasNext()) {
           // Collection<ULDDamageRepairDetailsVO> uldDamageRepairDetailsVO = new ArrayList<ULDDamageRepairDetailsVO>();
			ULDDamageDetailsListVO uldDamageDetailsListVO  = (ULDDamageDetailsListVO)iterator.next();
			log.log(Log.FINE,
					"\n uldDamageDetailsListVO .>>>>>>>>>>.........>...>>>>>>",
					uldDamageDetailsListVO);
				Vector<Object> row = new Vector<Object>();
			//	ULDDamageVO uldDamageVO = (ULDDamageVO)dataIterator.next();
				row.add(uldDamageDetailsListVO.getUldNumber());
				row.add(String.valueOf(uldDamageDetailsListVO.getDamageReferenceNumber()));
				row.add(uldDamageDetailsListVO.getSection());
				row.add(String.valueOf(uldDamageDetailsListVO.getDamageDescription()));
				row.add(uldDamageDetailsListVO.getReportedStation());
				row.add(uldDamageDetailsListVO.getCurrentStation());
				if(uldDamageDetailsListVO.getReportedDate()!=null ){
				row.add(uldDamageDetailsListVO.getReportedDate().toDisplayDateOnlyFormat());
				}else{
					row.add("");
				}
					
				if(uldDamageDetailsListVO.getRepairDate()!= null ){
					row.add("Closed");
				}else{
					row.add("Open");
				}
				row.add(uldDamageDetailsListVO.getDamageStatus());
				row.add(uldDamageDetailsListVO.getOverallStatus());
				//changed by a-3045 for bug 20387
				if(uldDamageDetailsListVO.getRemarks() != null){
					row.add(uldDamageDetailsListVO.getRemarks());
				}else{
					row.add("");
				}
				if(uldDamageDetailsListVO.getParty() != null){
					row.add(uldDamageDetailsListVO.getParty());
				}else{
					row.add("");
				}
				if(uldDamageDetailsListVO.getPartyType() != null){
					if(("A").equals(uldDamageDetailsListVO.getPartyType())){
						row.add("Airline");
					}else if (("G").equals(uldDamageDetailsListVO.getPartyType())){
						row.add("Agent");
					}else if(("O").equals(uldDamageDetailsListVO.getPartyType())){
						row.add("Others");
					}
					else{
						row.add(uldDamageDetailsListVO.getPartyType());
					}
				}else{
					row.add("");
				}
				if(uldDamageDetailsListVO.getLocation() != null){
					row.add(uldDamageDetailsListVO.getLocation());
				}else{
					row.add("");
				}
				tableData.add(row);
			}
		//}
		return tableData;
	}
	
	
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		ULDDamageFilterVO uldDamageFilterVO = (ULDDamageFilterVO) dataParameters;
		log
				.log(Log.FINE, "\n uldDamageFilterVO .>>>>....>>",
						uldDamageFilterVO);
		if (uldDamageFilterVO.getUldNumber() != null) {
			reportParameters.add(uldDamageFilterVO.getUldNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(uldDamageFilterVO.getDamageReferenceNumber() != 0){
			reportParameters.add(String.valueOf(uldDamageFilterVO.getDamageReferenceNumber()));
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(uldDamageFilterVO.getUldTypeCode() != null){
			reportParameters.add(uldDamageFilterVO.getUldTypeCode());
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(uldDamageFilterVO.getCurrentStation() != null){
			reportParameters.add(uldDamageFilterVO.getCurrentStation());
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(uldDamageFilterVO.getUldStatus() != null){
			reportParameters.add(uldDamageFilterVO.getUldStatus());
		}else{
			reportParameters.add(ALL);
		}
		if(uldDamageFilterVO.getDamageStatus() != null){
			reportParameters.add(uldDamageFilterVO.getDamageStatus());
		}else{
			reportParameters.add(ALL);
		}
		if(uldDamageFilterVO.getReportedStation() != null){
			log.log(Log.FINE, "\n uldDamageFilterVO .getReportedStation is  not null>>>>>>");
			reportParameters.add(uldDamageFilterVO.getReportedStation());
		}else{
			log.log(Log.FINE, "\n uldDamageFilterVO .getReportedStation is null>>>>>>");
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldDamageFilterVO.getFromDate() != null) {
			/*String frmDate = TimeConvertor.toStringFormat(uldDamageFilterVO
					.getFromDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);*/
			reportParameters.add(uldDamageFilterVO.getFromDate());
			
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (uldDamageFilterVO.getToDate() != null) {
			/*String toDate = TimeConvertor.toStringFormat(uldDamageFilterVO
					.getToDate().toCalendar(),
					TimeConvertor.CALENDAR_DATE_FORMAT);*/
			reportParameters.add(uldDamageFilterVO.getToDate());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		log
				.log(
						Log.FINE,
						"\n reportParameters for ListDamageReportAttributeBuilder .>>>>>>",
						reportParameters);
		return reportParameters;
	}
	
	

/*public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();	
		StringBuilder stringBuilder1=null;	
		StringBuilder stringBuilder2=null;	
		StringBuilder stringBuilder3=null;	
		Iterator iterator = data.iterator();
		Vector<Object> row = new Vector<Object>();					
		ULDDamageVO uldDamageVO = new ULDDamageVO();		
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO  = (ULDDamageRepairDetailsVO)iterator.next(); 
		while(iterator.hasNext()) { 		                  
            row.add(uldDamageRepairDetailsVO.getUldNumber()); 
         	stringBuilder1 = new StringBuilder(String.valueOf(uldDamageVO.getDamageReferenceNumber()));
    		row.add(stringBuilder1.toString());	
    		stringBuilder2 = new StringBuilder(String.valueOf(uldDamageVO.getReportedStation()));
    		row.add(stringBuilder2.toString());
            row.add(uldDamageRepairDetailsVO.getCurrentStation());
			row.add(uldDamageRepairDetailsVO.getRepairStatus());
			row.add(uldDamageRepairDetailsVO.getOverallStatus());
			stringBuilder3 = new StringBuilder(String.valueOf(uldDamageVO.getRemarks()));
			row.add(stringBuilder3.toString());
		}		
		tableData.add(row);
		return tableData;
	    }*/
	
}

