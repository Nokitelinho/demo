/*
 * ULDAvailabilityAttributeBuilder.java Created on Apr 1, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.transaction.uldavailability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3278
 * 
 */
public class ULDAvailabilityAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("ULDAvailabilityAttributeBuilder");

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
		reportColumns.add("PTYTYP");
		reportColumns.add("PTYCOD");
		reportColumns.add("STNCOD");
		reportColumns.add("LOC");
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

		Vector<Vector> uldAvailabilityDetails = new Vector<Vector>();
		Vector<Object> row = null;
		
		List<ULDTransactionDetailsVO> listVOs = new ArrayList<ULDTransactionDetailsVO>();
		listVOs = new ArrayList<ULDTransactionDetailsVO>(data);
		for (ULDTransactionDetailsVO listVO : listVOs) {

			row = new Vector<Object>();
			if (listVO.getUldNumber() != null) {
				row.add(listVO.getUldNumber());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getPartyType() != null) {
				row.add(listVO.getPartyType());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getToPartyCode() != null) {
				row.add(listVO.getToPartyCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getTransactionStationCode() != null) {
				row.add(listVO.getTransactionStationCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getLocation() != null) {
				row.add(listVO.getLocation());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			uldAvailabilityDetails.add(row);

			log.log(Log.INFO, " \n xxxxxx", listVO);
		}

		log.log(Log.INFO, "uldHistoryDetails", uldAvailabilityDetails);
		return uldAvailabilityDetails;
	}

	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		TransactionFilterVO transactionFilterVO = (TransactionFilterVO) dataParameters;

		if (transactionFilterVO.getTransactionStationCode() != null) {
			reportParameters.add(transactionFilterVO
					.getTransactionStationCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (transactionFilterVO.getUldTypeCode() != null) {
			reportParameters.add(transactionFilterVO.getUldTypeCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (transactionFilterVO.getPartyType() != null) {
			reportParameters.add(transactionFilterVO.getPartyType());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (transactionFilterVO.getToPartyCode() != null) {
			reportParameters.add(transactionFilterVO.getToPartyCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
