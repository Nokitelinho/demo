/*
 * ULDInventoryAttributeBuilder.java Created on Sep 12, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4072
 */

public class ULDInventoryAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("Inventory Attribute Builder");
	
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
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		ULDListFilterVO uldListFilterVO = (ULDListFilterVO) dataParameters;

		Vector<Object> reportParameters = new Vector<Object>();
		if(uldListFilterVO.getCurrentStation()!=null && 
				!uldListFilterVO.getCurrentStation().isEmpty()) {
			reportParameters.add(uldListFilterVO.getCurrentStation()); 
		}else {
			reportParameters.add(ReportConstants.EMPTY_STRING); 
		}
		log.log(Log.FINE, "\n\n reportParameters ----->", reportParameters);
		return reportParameters;
	} 

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
		columns.add("OWNARL");
		columns.add("ULDTYP");		
		columns.add("ULDNOONE");
		columns.add("ULDNOTWO");
		columns.add("ULDNOTHR");
		columns.add("ULDNOFOU");
		columns.add("ULDNOFIV");
		columns.add("ULDNOSIX");
		columns.add("ULDNOSEV");
		columns.add("ULDNOEIG");
		columns.add("ULDNONIN");
		columns.add("ULDNOTEN");
		columns.add("ULDCNT");
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
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> listUldData = new Vector<Vector>();
		Collection<ULDListVO> uldInventoryDetails = new ArrayList<ULDListVO>(data);
		log.log(Log.INFO, "inside getReportData");
		
		 Map<String, Collection<ULDListVO>> uldInventoryMap = new LinkedHashMap<String, Collection<ULDListVO>>();
		if(uldInventoryDetails!=null && uldInventoryDetails.size()>0) {    
			for(ULDListVO uldInvetoryVo : uldInventoryDetails){
				String key = uldInvetoryVo.getOperatingAirline()+uldInvetoryVo.getUldTypeCode();
				if(uldInventoryMap.get(key)==null) {
					Collection<ULDListVO> listUld= new ArrayList<ULDListVO>();  
					listUld.add(uldInvetoryVo);    
					uldInventoryMap.put(key, listUld);  
				}else {
					uldInventoryMap.get(key).add(uldInvetoryVo);   
				}
			}	
			for(String key:uldInventoryMap.keySet() ) {
				Vector<Object> row = new Vector<Object>();
				Collection<ULDListVO> uldInventory= uldInventoryMap.get(key);
				int rowCount=1;
				int totalUldCnt = uldInventory.size();
				int resetCount = 11; 
				//To set 10 ULD number in a row
				while(uldInventory.size()%10 !=0){
					uldInventory.add(new ULDListVO());   
				}
				for(ULDListVO uldStock:uldInventory) {
					if(rowCount==1) { 
						if(uldStock.getOperatingAirline()!=null && !uldStock.getOperatingAirline().isEmpty()) {
							row.add(uldStock.getOperatingAirline()); //ROW 1 Owner Airline Code
							log.log(Log.INFO, "%%%%%%%%%%%%%1 ", uldStock.getOperatingAirline());
						}else {
							row.add(ReportConstants.EMPTY_STRING); 
						}  
						if(uldStock.getUldTypeCode()!=null && !uldStock.getUldTypeCode().isEmpty()) {
							row.add(uldStock.getUldTypeCode()); //ROW 2 Uld Type
							log.log(Log.INFO, "%%%%%%%%%%%%%2 ", uldStock.getUldTypeCode());
						}else {
							row.add(ReportConstants.EMPTY_STRING);           
						}
						if(uldStock.getUldTypeNumber()!=null && !uldStock.getUldTypeNumber().isEmpty()) {
							row.add(uldStock.getUldTypeNumber()); //ROW 3 Uld Number
							log.log(Log.INFO, "%%%%%%%%%%%%%3 ",uldStock.getUldTypeNumber());
						}else {
							row.add(ReportConstants.EMPTY_STRING); 
						}
					}else {
						if(rowCount%resetCount==0) { //For adding next row
							row.add(ReportConstants.EMPTY_STRING); //ROW 11 Total Count set blank
							listUldData.add(row);
							resetCount=resetCount+10;     
							row = new Vector<Object>();
							row.add(ReportConstants.EMPTY_STRING); //ROW 1 Owner Airline Code set blank
							row.add(ReportConstants.EMPTY_STRING); //ROW 2 Owner Airline Code set blank
						}
						if(uldStock.getUldTypeNumber()!=null && !uldStock.getUldTypeNumber().isEmpty()) {
							row.add(uldStock.getUldTypeNumber()); //ROW 4..10 Uld Number
							log.log(Log.INFO, "%%%%%%%%%%%%%3 ",uldStock.getUldTypeNumber());
						}else {
							row.add(ReportConstants.EMPTY_STRING); 
						}
					}					
					rowCount++;
				}
				row.add(Integer.toString(totalUldCnt));  //ROW 11 Total Count
				listUldData.add(row);
				row = new Vector<Object>();
			}
		}
		
		return listUldData;
	}

	
	
	
}

