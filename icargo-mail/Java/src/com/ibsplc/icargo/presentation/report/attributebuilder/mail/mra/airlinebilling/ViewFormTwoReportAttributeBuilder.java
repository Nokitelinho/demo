/*
 * ViewFormTwoReportAttributeBuilder.java Created on DEC 09, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.airlinebilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineForBillingVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class ViewFormTwoReportAttributeBuilder extends AttributeBuilderAdapter {
	/**
	 * Blank Space
	 */
	public static final String BLANKSPACE = "";

	/**
	 * Zero
	 */
	public static final String ZERO = "0.0";

	/**
	 * log defined
	 */
	private Log log = LogFactory.getLogger("ViewFormTwoReportAttributeBuilder");

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

		reportColumns.add("ARLCOD");
		reportColumns.add("ARLNUM");
		reportColumns.add("BILCURCOD");
		reportColumns.add("CRGAMT");
		reportColumns.add("OUTMISAMT");
		reportColumns.add("OUTTOTAMT");

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

		Vector<Vector> airlineForBillingVos = new Vector<Vector>();
		Vector<Object> row = null;
		List<AirlineForBillingVO> airlineForBillingVOs = new ArrayList<AirlineForBillingVO>();
		airlineForBillingVOs = new ArrayList<AirlineForBillingVO>(data);

		for (AirlineForBillingVO airlineForBillingVO : airlineForBillingVOs) {

			row = new Vector<Object>();
			if (airlineForBillingVO.getAirlineCode() != null) {
				row.add(airlineForBillingVO.getAirlineCode());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (airlineForBillingVO.getAirlineNumber() != null) {
				row.add(airlineForBillingVO.getAirlineNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (airlineForBillingVO.getBillingCurrency() != null) {
				row.add(airlineForBillingVO.getBillingCurrency());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			row.add(ReportConstants.EMPTY_STRING);

			if (airlineForBillingVO.getMiscAmountInBilling() != null) {
				row.add(airlineForBillingVO.getMiscAmountInBilling()
						.getAmount());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (airlineForBillingVO.getMiscBillingTotal() != null) {
				row.add(airlineForBillingVO.getMiscBillingTotal().getAmount());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			airlineForBillingVos.add(row);

			log.log(Log.INFO, " \n xxxxxx", airlineForBillingVOs);
		}

		log.log(Log.INFO, "cCADetailsVOs", airlineForBillingVos);
		return airlineForBillingVos;
	}

	/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 * 
	 * @param parameters
	 *            the parameter data
	 * @param extraInfo
	 *            information required for formatting the parameters
	 * @return Vector the report parameters
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilder#getReportParameters(java.util.Collection,
	 *      java.util.Collection)
	 */
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		InterlineFilterVO interlineFilterVO = (InterlineFilterVO) dataParameters;
		log.log(Log.INFO, "interlineFilterVO", interlineFilterVO);
		if (interlineFilterVO.getClearancePeriod() != null) {
			reportParameters.add(interlineFilterVO.getClearancePeriod());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
