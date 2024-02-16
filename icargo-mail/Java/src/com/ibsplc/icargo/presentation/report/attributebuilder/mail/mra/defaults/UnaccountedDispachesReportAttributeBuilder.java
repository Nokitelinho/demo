/*
 * UnaccountedDispachesReportAttributeBuilder.java Created on AUG, 2008
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2107
 * 
 */
public class UnaccountedDispachesReportAttributeBuilder extends AttributeBuilderAdapter {
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
	private Log log = LogFactory.getLogger("UnaccountedDispachesReportAttributeBuilder");

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

		reportColumns.add("DSN");
		reportColumns.add("ORG");
		reportColumns.add("DSTN");
		reportColumns.add("FLTNUM");
		reportColumns.add("FLTDATE");
		reportColumns.add("BILLTYP");
		reportColumns.add("CURRENCY");
		reportColumns.add("WEIGHT");
		reportColumns.add("RATE");
		reportColumns.add("AMT");
		reportColumns.add("MAILCATEGORY");
		reportColumns.add("MAILCLASS");
		reportColumns.add("SUBCLASS");
		reportColumns.add("SECTORFRM");
		reportColumns.add("SECTORTO");
		reportColumns.add("PRORATEDAMTINCTR");
		reportColumns.add("PRORATEDAMTINNZD");
		reportColumns.add("REASONCDE");
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

		Vector<Vector> unAccountedDispatchesDetailsVOs = new Vector<Vector>();
		Vector<Object> row = null;
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> reasonCode = (Collection<OneTimeVO>) extraInfor;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> category = (Collection<OneTimeVO>) extraInform;
		List<UnaccountedDispatchesDetailsVO> listVOs = new ArrayList<UnaccountedDispatchesDetailsVO>();
		listVOs = new ArrayList<UnaccountedDispatchesDetailsVO>(data);
		log.log(Log.FINE, "reasonCode.....>", reasonCode);
		for (UnaccountedDispatchesDetailsVO listVO : listVOs) {

			row = new Vector<Object>();
			if (listVO.getDsn() != null) {
				row.add(listVO.getDsn());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getOrigin() != null) {
				row.add(listVO.getOrigin());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getDestination() != null) {
				row.add(listVO.getDestination());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getFlightNumber() != null) {
				row.add(listVO.getFlightNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getFlightDate() != null) {
				row.add(listVO.getFlightDate());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getBillType() != null) {
				row.add(listVO.getBillType());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getCurrency()!= null) {
				row.add(listVO.getCurrency());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getWeight()!= null) {
				row.add(listVO.getWeight());
			} else {
				row.add(ZERO);
			}
			if (listVO.getRate()!= null) {
				row.add(listVO.getRate());
			} else {
				row.add(ZERO);
			}
			if (listVO.getAmount()!= null) {
				row.add(listVO.getAmount());
			} else {
				row.add(ZERO);
			}
			
			if (listVO.getMailCategory() != null) {
				for (OneTimeVO oneTimeVO : category) {
					if (listVO.getMailCategory().equals(
							oneTimeVO.getFieldValue())) {
						listVO.setMailCategory(oneTimeVO.getFieldDescription());
					}
				}
				row.add(listVO.getMailCategory());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getMailClass()!= null) {
				row.add(listVO.getMailClass());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getMailSubClass()!= null) {
				row.add(listVO.getMailSubClass());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getSectorFrom() != null) {
				row.add(listVO.getSectorFrom());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getSectorTo() != null) {
				row.add(listVO.getSectorTo());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			if (listVO.getProratedAmtinCtrcur() != null) {
				row.add(listVO.getProratedAmtinCtrcur());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getProratedAmt()!= null) {
				row.add(listVO.getProratedAmt());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			if (listVO.getReason() != null) {
				for (OneTimeVO oneTimeVO : reasonCode) {
					if (listVO.getReason().equals(
							oneTimeVO.getFieldValue())) {
						listVO.setReason(oneTimeVO.getFieldDescription());
					}
				}
				row.add(listVO.getReason());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			
			unAccountedDispatchesDetailsVOs.add(row);

			log.log(Log.INFO, " \n xxxxxx", listVO);
		}

		log.log(Log.INFO, "unAccountedDispatchesDetailsVOs",
				unAccountedDispatchesDetailsVOs);
		return unAccountedDispatchesDetailsVOs;
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
		log.log(Log.FINE, "parameters..", parameters);
		log.log(Log.FINE, "extraInfo..", extraInfo);
		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		Object extraInfor = ((ArrayList<OneTimeVO>)extraInfo).get(0);
		Collection<OneTimeVO> reasonCodes = (Collection<OneTimeVO>)extraInfor;
		StringBuilder fltNumber = new StringBuilder();
		log.log(Log.FINE, "reasonCodes..", reasonCodes);
		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO = (UnaccountedDispatchesFilterVO)dataParameters;
		
		reportParameters.add((unaccountedDispatchesFilterVO.getFromDate()!= null)?unaccountedDispatchesFilterVO.getFromDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
		reportParameters.add((unaccountedDispatchesFilterVO.getToDate()!= null)?unaccountedDispatchesFilterVO.getToDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
		fltNumber.append(unaccountedDispatchesFilterVO.getCarrierCode()).append(" ").append(unaccountedDispatchesFilterVO.getFlightNumber());
		
		reportParameters.add((fltNumber!= null)?fltNumber.toString():ReportConstants.EMPTY_STRING);
		reportParameters.add((unaccountedDispatchesFilterVO.getDeparturePort()!= null)?unaccountedDispatchesFilterVO.getDeparturePort():ReportConstants.EMPTY_STRING);
		reportParameters.add((unaccountedDispatchesFilterVO.getFinalDestination()!= null)?unaccountedDispatchesFilterVO.getFinalDestination():ReportConstants.EMPTY_STRING);
		if (unaccountedDispatchesFilterVO.getReasonCode() != null && !"".equals((unaccountedDispatchesFilterVO.getReasonCode()))) {
			for (OneTimeVO oneTimeVO : reasonCodes) {
				if (unaccountedDispatchesFilterVO.getReasonCode().equals(
						oneTimeVO.getFieldValue())) {
					reportParameters.add(oneTimeVO.getFieldDescription());
				}
			}
			
		} 
		/*else if(unaccountedDispatchesFilterVO.getReasonCode().equals("")){
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}*/
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		reportParameters.add((unaccountedDispatchesFilterVO.getNoOfDispatches()!= null)?unaccountedDispatchesFilterVO.getNoOfDispatches():ReportConstants.EMPTY_STRING);
		reportParameters.add((unaccountedDispatchesFilterVO.getProRatedAmt()!= null)?unaccountedDispatchesFilterVO.getProRatedAmt():ReportConstants.EMPTY_STRING);
		reportParameters.add((unaccountedDispatchesFilterVO.getCurrency()!= null)?unaccountedDispatchesFilterVO.getCurrency():ReportConstants.EMPTY_STRING);
		
		log.log(Log.FINE, "report parameters..", reportParameters);
		return reportParameters;
	}

}
