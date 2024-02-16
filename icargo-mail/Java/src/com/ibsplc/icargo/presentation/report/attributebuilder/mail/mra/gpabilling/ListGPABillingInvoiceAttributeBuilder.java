/*
 * ListGPABillingInvoiceAttributeBuilder.java Created on OCT 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class ListGPABillingInvoiceAttributeBuilder extends
		AttributeBuilderAdapter {
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
			.getLogger("ListGPABillingInvoiceAttributeBuilder");

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

		reportColumns.add("INVNUM");  
		reportColumns.add("BLDDAT");
		reportColumns.add("BLGPRDFRM");
		reportColumns.add("BLGPRDTOO");
		reportColumns.add("GPACOD"); 
		reportColumns.add("BLGCURCOD");
		reportColumns.add("TOTAMTSTLCUR");
		reportColumns.add("INVSTA");
		reportColumns.add("POANAM");
		reportColumns.add("OVRRND"); //Added by A-8164 for ICRD-267499      
		log.log(Log.FINE, "reportColumns is ijj--------->", reportColumns);
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

		Vector<Vector> cN51SummaryVOs = new Vector<Vector>();
		Vector<Object> row = null;
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> invoicetatus = (Collection<OneTimeVO>) extraInfor;
		Collection<CN51SummaryVO> listVOs = new ArrayList<CN51SummaryVO>();
		listVOs = new ArrayList<CN51SummaryVO>(data);  
		for (CN51SummaryVO listVO : listVOs) {

			row = new Vector<Object>();
			if (listVO.getInvoiceNumber() != null) {
				row.add(listVO.getInvoiceNumber());

			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getBilledDate() != null) {
				row.add(listVO.getBilledDate().toDisplayDateOnlyFormat());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getBillingPeriodFrom() != null) {
				row.add(listVO.getBillingPeriodFrom());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getBillingPeriodTo() != null) {
				row.add(listVO.getBillingPeriodTo());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			

			if (listVO.getGpaCode() != null) {
				row.add(listVO.getGpaCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getBillingCurrencyCode() != null) {
				row.add(listVO.getBillingCurrencyCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (listVO.getTotalAmountInBillingCurrency() != null) {
				double amountInBillingCurrency = listVO
						.getTotalAmountInBillingCurrency().getAmount();
				
				row.add(amountInBillingCurrency);
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			log.log(Log.INFO,
					"Invoice Status inside Attirbute Builder====>>>>", listVO.getInvoiceStatusDisplay());
			if (listVO.getInvoiceStatus() != null) {
				log
						.log(Log.INFO,
								"Invoice Status != NULL inside Attirbute Builder====>>>>");
				for (OneTimeVO oneTimeVO : invoicetatus) {
					log
							.log(Log.INFO,
									"Invoice Status OnetimeVO iteration inside Attirbute Builder====>>>>");
					if (listVO.getInvoiceStatus().equals(
							oneTimeVO.getFieldValue())) {
						log
								.log(Log.INFO,
										"Equating with field value Attirbute Builder====>>>>");
						listVO
								.setInvoiceStatus(oneTimeVO
										.getFieldDescription());
					}
				}
				row.add(listVO.getInvoiceStatus());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getGpaName()!= null) {
				row.add(listVO.getGpaName());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (listVO.getOverrideRounding()!= null) { //Added by A-8164 for ICRD-267499 
				row.add(listVO.getOverrideRounding());        
			} else{
				row.add(ReportConstants.EMPTY_STRING);   
			}
			cN51SummaryVOs.add(row);

			log.log(Log.INFO, " \n xxxxxx", listVO);
		}

		log.log(Log.INFO, "cCADetailsVOs", cN51SummaryVOs);
		return cN51SummaryVOs;
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
		Collection<OneTimeVO> invoicetatus = (Collection<OneTimeVO>) extraInfor;
		CN51SummaryFilterVO cN51SummaryFilterVO = (CN51SummaryFilterVO) dataParameters;
		log.log(Log.INFO, "listccafilterVo", cN51SummaryFilterVO);
		if (cN51SummaryFilterVO.getGpaCode() != null) {
			reportParameters.add(cN51SummaryFilterVO.getGpaCode());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cN51SummaryFilterVO.getFromDate() != null) {
			reportParameters.add(cN51SummaryFilterVO.getFromDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cN51SummaryFilterVO.getToDate() != null) {
			reportParameters.add(cN51SummaryFilterVO.getToDate()
					.toDisplayDateOnlyFormat());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (cN51SummaryFilterVO.getInvoiceNumber() != null) {
			reportParameters.add(cN51SummaryFilterVO.getInvoiceNumber());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if (cN51SummaryFilterVO.getInvoiceStatus() != null) {
			for (OneTimeVO oneTimeVO : invoicetatus) {
				if (cN51SummaryFilterVO.getInvoiceStatus().equals(
						oneTimeVO.getFieldValue())) {
					cN51SummaryFilterVO.setInvoiceStatus(oneTimeVO
							.getFieldDescription());
				}
			}
			reportParameters.add(cN51SummaryFilterVO.getInvoiceStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
	}

}
