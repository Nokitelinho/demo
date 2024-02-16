/*
 * GPABillingInvoiceEnquiryReportAttributeBuilder.java Created on Mar 01, 2007
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

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class GPABillingInvoiceEnquiryReportAttributeBuilder extends AttributeBuilderAdapter{

	private Log log = LogFactory.getLogger("mailtracking_mra_gpabilling");
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

		columns.add("DSN");
		columns.add("DSNDAT");
		columns.add("ORGDST");
		columns.add("DSTCOD");
		columns.add("TOTWGT");
		columns.add("APLRAT");
		columns.add("TOTAMT");
		columns.add("BLGSTA");
		columns.add("CCAREFNUM");
		columns.add("C66RMK");

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
		log.log(Log.FINE, "\n\n extraInfo----in attribute----->  ", extraInfo);
		Vector<Vector> tableData = new Vector<Vector>();
		Collection<CN66DetailsVO> cN66DetailsVOs = null;
		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(1);
		Collection<OneTimeVO> BillingStatus = (Collection<OneTimeVO>) extraInform;
		Object dataRecords = ((ArrayList<Object>)extraInfo).get(2);
		CN51SummaryVO cN51SummaryVO = (CN51SummaryVO) dataRecords;
		cN66DetailsVOs =cN51SummaryVO.getCn66details();
		log.log(Log.FINE, "\n\n cN66DetailsVOs----in attribute----->  ",
				cN66DetailsVOs);
		Vector<Object> row =  null;

   		for(CN66DetailsVO cn66VO: cN66DetailsVOs){
   		 row= new Vector<Object>();
   			row.add(cn66VO.getDsn());
   			row.add(cn66VO.getReceivedDate().toDisplayDateOnlyFormat());
   			row.add(cn66VO.getOrigin());
   			row.add(cn66VO.getDestination());
   			row.add(cn66VO.getTotalWeight());
   			row.add(cn66VO.getApplicableRate());
   			row.add(cn66VO.getActualAmount().getAmount());
   			//row.add(cn66VO.getBillingStatus());
   			if (cn66VO.getBillingStatus() != null) {
				for (OneTimeVO oneTimeVO : BillingStatus) {
					if (cn66VO.getBillingStatus().equals(oneTimeVO.getFieldValue())) {
						cn66VO.setBillingStatus(oneTimeVO.getFieldDescription());
					}
				}
				row.add(cn66VO.getBillingStatus());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
   			row.add(cn66VO.getCcaRefNo());
   			row.add(cn66VO.getRemarks());

				tableData.add(row);

   		}



   		log.log(Log.INFO, "tableData-->", tableData);
	return tableData;
	}


	@Override
	public Vector<Object> getReportParameters(
			Collection parameters, Collection extraInfo) {


		Vector<Object> reportParameters = new Vector<Object>();

		Object dataParameters = ((ArrayList<Object>)parameters).get(0);



		Object extraInform = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> invoiceStatus = (Collection<OneTimeVO>) extraInform;
		CN51SummaryVO cN51SummaryVO = (CN51SummaryVO) dataParameters;

		reportParameters.add(cN51SummaryVO.getInvoiceNumber());
		reportParameters.add(cN51SummaryVO.getGpaCode());
		reportParameters.add(cN51SummaryVO.getGpaName());
		reportParameters.add(cN51SummaryVO.getBillingPeriodFrom());
		reportParameters.add(cN51SummaryVO.getBillingPeriodTo());
		//reportParameters.add(cN51SummaryVO.getInvoiceStatus());
		if (cN51SummaryVO.getInvoiceStatus()!= null) {
			log.log(Log.INFO, "InvoiceStatus-->", cN51SummaryVO.getInvoiceStatus());
			for (OneTimeVO oneTimeVO :invoiceStatus) {
				if (cN51SummaryVO.getInvoiceStatus().equals(oneTimeVO.getFieldValue())) {
					cN51SummaryVO.setInvoiceStatus(oneTimeVO.getFieldDescription());
				}
			}
			reportParameters.add(cN51SummaryVO.getInvoiceStatus());
		} else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		reportParameters.add(cN51SummaryVO.getBilledDate().toDisplayDateOnlyFormat());
		reportParameters.add(cN51SummaryVO.getContractCurrencyCode());

		return reportParameters;
	}


}
