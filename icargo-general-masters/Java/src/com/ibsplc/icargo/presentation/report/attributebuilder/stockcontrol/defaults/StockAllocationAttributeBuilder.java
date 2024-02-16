/*
 * StockAllocationAttributeBuilder.java Created on Apr 5, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class StockAllocationAttributeBuilder.
 */
public class StockAllocationAttributeBuilder extends AttributeBuilderAdapter {	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getReportColumns()
	 */
	@Override 
	public Vector<String> getReportColumns() {
		
		Vector<String> columns = new Vector<String>();	
		
		columns.add("STARNG");
		columns.add("ENDRNG");
		columns.add("NUMOFDOCS");
		return columns;
	}
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getReportParameters(java.util.Collection, java.util.Collection)
	 */
	@Override
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {
		
		Vector<Object> reportParameters = new Vector<Object>();	
		
		Collection<Object> values= ((ArrayList)extraInfo);
		if(values != null && values.size() > 0){
			for(Object value : values) {
				reportParameters.add(value);				
			}
		}
		
		return reportParameters;
	
	}	
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter#getReportData(java.util.Collection, java.util.Collection)
	 */
	@Override
	
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		Vector<Vector> detailsTableData = new Vector<Vector>();		
		
		if(data != null) {		
			Collection<RangeVO> rangeVOs = (Collection<RangeVO>)data;
			if (rangeVOs != null && rangeVOs.size() > 0) {			
				for (RangeVO rangeVO : rangeVOs) {
					Vector<Object> row = new Vector<Object>();
					row.add(rangeVO.getStartRange());
					row.add(rangeVO.getEndRange());
					row.add(rangeVO.getNumberOfDocuments());				
					detailsTableData.add(row);				
				}		
			}			
			
		}
				
		return detailsTableData;		
	}

}
