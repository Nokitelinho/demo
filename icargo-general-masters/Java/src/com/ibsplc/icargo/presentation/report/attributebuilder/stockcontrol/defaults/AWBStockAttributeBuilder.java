/*
 * AWBStockAttributeBuilder.java Created on Jun 12, 2009
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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * Class for supporting
 * printing of AWBStock Report
 */
public class AWBStockAttributeBuilder extends AttributeBuilderAdapter {


	private static final Log log = LogFactory.getLogger("stockcontrol defaults AWBSTOCK");
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
		columns.add("STKHLDCOD");
		columns.add("STKHLDNAM");
		columns.add("STARNG");
		columns.add("ENDRNG");
		return columns;
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
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();		

		log.log(Log.INFO, "reportParameters------->", reportParameters);
		return reportParameters;
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

		Vector<Vector> detailsTableData = new Vector<Vector>();


		Object values= ((ArrayList)extraInfo).get(0);
		Collection<RangeVO> rangeVOs = (Collection<RangeVO>)values;
		log.log(Log.INFO, "Attribute Filter getReportData values------->",
				rangeVOs);
		if (rangeVOs != null && rangeVOs.size() > 0) {			
			for (RangeVO rangeVO : rangeVOs) {
				Vector<Object> row = new Vector<Object>();
				row.add(rangeVO.getStockHolderCode());
				row.add(rangeVO.getStockHolderName());
				row.add(rangeVO.getStartRange());
				row.add(rangeVO.getEndRange());
				detailsTableData.add(row);				
			}		
		}
		return detailsTableData;		
	}
}
