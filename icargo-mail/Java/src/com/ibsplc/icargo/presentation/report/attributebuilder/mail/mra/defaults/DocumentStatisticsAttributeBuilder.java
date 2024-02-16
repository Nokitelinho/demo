/*
 * DocumentStatisticsAttributeBuilder.java Created on SEP 02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class DocumentStatisticsAttributeBuilder extends AttributeBuilderAdapter {
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
	private Log log = LogFactory
			.getLogger("DocumentStatisticsAttributeBuilder");

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
		reportColumns.add("FLTCARCOD");
		reportColumns.add("FLTNUM");
		reportColumns.add("FLTDAT");
		reportColumns.add("NOOFDOCUMENTS");
		reportColumns.add("RATAUD");
		reportColumns.add("TOORATAUD");
		reportColumns.add("ACCNTD");
		reportColumns.add("CLSFLG");
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

		Vector<Vector> statisticsDetailsVOs = new Vector<Vector>();
		Vector<Object> row = null;
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> flightStatus = (Collection<OneTimeVO>) extraInfor;
		log.log(Log.INFO, "flightStatus====>in get report getReportData",
				flightStatus);
		List<DocumentStatisticsDetailsVO> listVOs = new ArrayList<DocumentStatisticsDetailsVO>();
		listVOs = new ArrayList<DocumentStatisticsDetailsVO>(data);

		for (DocumentStatisticsDetailsVO listVO : listVOs) {

			row = new Vector<Object>();
			if (listVO.getCarrierCode() != null) {
				row.add(listVO.getCarrierCode());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getFlightNo() != null) {
				row.add(listVO.getFlightNo());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getFlightDate() != null) {
				row.add(listVO.getFlightDate().toDisplayDateOnlyFormat());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getNoOfDocuments() != null) {
				row.add(listVO.getNoOfDocuments());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getToBeRateAudited() != null) {
				row.add(listVO.getToBeRateAudited());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getRateAudited() != null) {
				row.add(listVO.getRateAudited());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getAccounted() != null) {
				row.add(listVO.getAccounted());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getFlightStatus() != null) {
				for (OneTimeVO oneTimeVO : flightStatus) {
					if (listVO.getFlightStatus().equals(
							oneTimeVO.getFieldValue())) {
						listVO.setFlightStatus(oneTimeVO.getFieldDescription());
					}
				}
				row.add(listVO.getFlightStatus());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			statisticsDetailsVOs.add(row);

			log.log(Log.INFO, " \n xxxxxx", listVO);
		}

		log.log(Log.INFO, "uldHistoryDetails", statisticsDetailsVOs);
		return statisticsDetailsVOs;
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
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> subSystem = (Collection<OneTimeVO>) extraInfor;
		log.log(Log.INFO, "subSystem====>in get report paarmeters", subSystem);
		DocumentStatisticsFilterVO statisticsFilterVO = (DocumentStatisticsFilterVO) dataParameters;
		log.log(Log.INFO, "statisticsFilterVO", statisticsFilterVO);
		if (statisticsFilterVO.getSubSystem() != null) {
			for (OneTimeVO oneTimeVO : subSystem) {
				if (statisticsFilterVO.getSubSystem().equals(
						oneTimeVO.getFieldValue())) {
					statisticsFilterVO.setSubSystem(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(statisticsFilterVO.getSubSystem());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (statisticsFilterVO.getCarrierCode() != null) {
			reportParameters.add(statisticsFilterVO.getCarrierCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (statisticsFilterVO.getFlightNo() != null) {
			reportParameters.add(statisticsFilterVO.getFlightNo());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (statisticsFilterVO.getFromDate() != null) {
			log.log(Log.INFO, "statisticsFilterVO.getFromDate()",
					statisticsFilterVO.getFromDate());
			reportParameters.add(statisticsFilterVO.getFromDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (statisticsFilterVO.getToDate() != null) {
			log.log(Log.INFO, "statisticsFilterVO.getToDate()",
					statisticsFilterVO.getToDate());
			reportParameters.add(statisticsFilterVO.getToDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
