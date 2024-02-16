package com.ibsplc.icargo.presentation.report.attributebuilder.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;

public class ReserveAWBAttributeBuilder extends AttributeBuilderAdapter {

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
		Object values= ((ArrayList)extraInfo).get(0);
		ReserveAWBVO reportVO = (ReserveAWBVO)values;
		if(reportVO!=null){
			if(reportVO.getAirlineCode()!=null) {
				reportParameters.add(reportVO.getAirlineCode());
			}else{
				reportParameters.add("");
			}
			if(reportVO.getCustomerCode()!=null) {
				reportParameters.add(reportVO.getCustomerCode());
			}
			else{
				reportParameters.add("");
			}
			reportParameters.add("");
			if(reportVO.getLastUpdateUser()!=null) {
				reportParameters.add(reportVO.getLastUpdateUser());
			}else{
				reportParameters.add("");
			}
			reportParameters.add("");
			if(reportVO.getExpiryDate()!=null) {
				reportParameters.add(reportVO.getExpiryDate().toDisplayDateOnlyFormat());
			}else{
				reportParameters.add("");
			}
			if(reportVO.getDocumentSubType()!=null) {
				reportParameters.add(reportVO.getDocumentSubType());
			}else{
				reportParameters.add("");
			}
		}
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
		columns.add("CSHTYP");
		columns.add("RCPNUM");
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

		Vector<Vector> detailsTableData = new Vector<Vector>();
		//String mainKey = String.valueOf(((ArrayList)extraInfo).get(0));

		Object values= ((ArrayList)extraInfo).get(0);
		ReserveAWBVO reserveAWBVO = (ReserveAWBVO)values;
		if(reserveAWBVO!=null && reserveAWBVO.getDocumentNumbers()!=null &&
				reserveAWBVO.getDocumentNumbers().size()>0){
			Collection<String> docnums = reserveAWBVO.getDocumentNumbers();
			String shpPrefix = reserveAWBVO.getShipmentPrefix();
	    		for(String strDoc : docnums){
					if(strDoc!=null && strDoc.length()>0){
						Vector<Object> row = new Vector<Object>();
						row.add(shpPrefix);
						row.add(strDoc);
						detailsTableData.add(row);
					}
	    		}
	    	}
		return detailsTableData;
	}
}