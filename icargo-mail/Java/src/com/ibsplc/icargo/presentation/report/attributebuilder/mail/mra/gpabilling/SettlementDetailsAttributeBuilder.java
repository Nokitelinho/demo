/**
 *  SettlementDetailsAttributeBuilder.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class SettlementDetailsAttributeBuilder extends AttributeBuilderAdapter{
	private Log log = LogFactory
	.getLogger("SettlementDetailsAttributeBuilder");
	private static final String ZERO ="0.0";
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
		Vector<String> reportColumns = new Vector<String>();
		reportColumns.add("STLDAT");
		reportColumns.add("STLCURCOD");
		reportColumns.add("GPACOD");
		reportColumns.add("STLREFNO");
		reportColumns.add("CHQNUM");
		reportColumns.add("CHQDAT");
		reportColumns.add("CHQBRH");
		reportColumns.add("CHQBNK");		
		reportColumns.add("CHQAMT");
		reportColumns.add("CHQISSDEL");
		reportColumns.add("GPANAM");
		reportColumns.add("INVNUM");
		reportColumns.add("BLDPRD");
		reportColumns.add("BLGCURCOD");
		reportColumns.add("TOTAMTBLGCUR");			
		reportColumns.add("STLAMT");
		reportColumns.add("DUEAMT");
		reportColumns.add("STLSTA");


		log.log(Log.FINE, "reportColumns is --------->", reportColumns);
		return reportColumns;
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
	public Vector<Object> getReportParameters(
			Collection parameters, Collection extraInfo) {
		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		InvoiceSettlementFilterVO filterVO = (InvoiceSettlementFilterVO)dataParameters;
		if(filterVO.getGpaCode()!=null){
			reportParameters.add(filterVO.getGpaCode());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(filterVO.getCompanyCode()!=null){
			reportParameters.add(filterVO.getCompanyCode());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(filterVO.getInvoiceNumber()!=null){
			reportParameters.add(filterVO.getInvoiceNumber());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(filterVO.getToDate()!=null){
			reportParameters.add(filterVO.getToDate());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(filterVO.getFromDate()!=null){
			reportParameters.add(filterVO.getFromDate());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(filterVO.getSettlementStatus()!=null){
			reportParameters.add(filterVO.getSettlementStatus());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(filterVO.getChequeNumber()!=null){
			reportParameters.add(filterVO.getChequeNumber());
		}
		else {
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		return reportParameters;
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

	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> gpaSettlementVOs = new Vector<Vector>();



		HashMap<Integer, SettlementDetailsVO> settlementDetails = null;
		Object extraInfoObj = ((ArrayList<Object>)extraInfo).get(0);			
		Collection <OneTimeVO> settlementStatus =(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(0);
		SettlementDetailsVO settlementDetailsVO = new SettlementDetailsVO();
		Collection<GPASettlementVO> gpaSettlementVOsData = new ArrayList<GPASettlementVO>();
		gpaSettlementVOsData = new ArrayList<GPASettlementVO>(data);

		for(GPASettlementVO gpaSettlementVO: gpaSettlementVOsData){
			Collection<SettlementDetailsVO> settlementDetailsVOs= gpaSettlementVOsData.iterator().next().getSettlementDetailsVOs();
			Collection<InvoiceSettlementVO> invoiceSettlementVOs = gpaSettlementVOsData.iterator().next().getInvoiceSettlementVOsPage();//modified by A-7371 as part of ICRD-274402
			if(settlementDetailsVOs!=null && settlementDetailsVOs.size()>0){
				settlementDetails = new HashMap<Integer, SettlementDetailsVO>();
				for (SettlementDetailsVO settlementDetailsVo : settlementDetailsVOs) {
				
					if(!settlementDetails.containsKey(settlementDetailsVo.getSerialNumber())){
						settlementDetails.put((settlementDetailsVo
								.getSerialNumber()), settlementDetailsVo);
					}


				}

			}
			if(invoiceSettlementVOs!=null){
				for(InvoiceSettlementVO invoiceSettlementVO : invoiceSettlementVOs){
					Vector<Object> row = new Vector<Object>();
					if(settlementDetails!=null && !settlementDetails.isEmpty()){
					settlementDetailsVO = settlementDetails
					.get((invoiceSettlementVO.getSerialNumber()));
					}
					if(gpaSettlementVO.getSettlementDate()!=null){
						row.add(gpaSettlementVO.getSettlementDate().toDisplayDateOnlyFormat());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(gpaSettlementVO.getSettlementCurrency()!=null){
						row.add(gpaSettlementVO.getSettlementCurrency());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					//Settlement Details Section
				
					if(settlementDetailsVO.getGpaCode()!=null){
						row.add(settlementDetailsVO.getGpaCode());

					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getSettlementId()!=null){
						row.add(settlementDetailsVO.getSettlementId());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getChequeNumber()!=null){
						row.add(settlementDetailsVO.getChequeNumber());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getChequeDate()!=null){
						row.add(settlementDetailsVO.getChequeDate().toDisplayDateOnlyFormat());

					}			
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getChequeBranch()!=null){
						row.add(settlementDetailsVO.getChequeBranch());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getChequeBank()!=null){
						row.add(settlementDetailsVO.getChequeBank());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getChequeAmount()!=null){
						row.add(settlementDetailsVO.getChequeAmount().getAmount());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
					if(settlementDetailsVO.getIsDeleted()!=null ){
						if("Y".equals(settlementDetailsVO.getIsDeleted())){
							row.add("Y");
						}
						else{
							row.add("N");
						}
						
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
					}

					if(invoiceSettlementVO.getGpaName()!=null){
						row.add(invoiceSettlementVO.getGpaName());
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);	
					}
					if(settlementDetailsVO.getIsDeleted()!=null && !("Y".equals(settlementDetailsVO.getIsDeleted()))){
						if(invoiceSettlementVO.getInvoiceNumber()!=null){
							row.add(invoiceSettlementVO.getInvoiceNumber());
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						if(invoiceSettlementVO.getBillingPeriod()!=null){
							row.add(invoiceSettlementVO.getBillingPeriod());					
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						if(invoiceSettlementVO.getBillingCurrencyCode()!=null){
							row.add(invoiceSettlementVO.getBillingCurrencyCode());
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						if(invoiceSettlementVO.getAmountInSettlementCurrency()!=null){
							row.add(invoiceSettlementVO.getAmountInSettlementCurrency().getAmount());
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						if(invoiceSettlementVO.getAmountAlreadySettled()!=null){
							row.add(invoiceSettlementVO.getAmountAlreadySettled().getAmount()); 
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						if(invoiceSettlementVO.getDueAmount()!=null){
							row.add(invoiceSettlementVO.getDueAmount().getAmount());
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						if(invoiceSettlementVO.getSettlementStatus()!=null){
							//row.add(populateOneTimeDescription(invoiceStatus,invoiceSettlementVO.getSettlementStatus()));
							row.add(populateOneTimeDescription(settlementStatus,invoiceSettlementVO.getSettlementStatus()));
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);	
						}
						
					}
					else{
						row.add(ReportConstants.EMPTY_STRING);
						row.add(ReportConstants.EMPTY_STRING);	
						row.add(ReportConstants.EMPTY_STRING);	
						row.add(ZERO);	
						row.add(ZERO);						
						row.add(ZERO);
						row.add(ReportConstants.EMPTY_STRING);
						
					}
					
					gpaSettlementVOs.add(row);

				}
			}
			else{
				Vector<Object> row = new Vector<Object>();
				if(gpaSettlementVO.getSettlementDate()!=null){
					row.add(gpaSettlementVO.getSettlementDate().toDisplayDateOnlyFormat());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(gpaSettlementVO.getSettlementCurrency()!=null){
					row.add(gpaSettlementVO.getSettlementCurrency());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				//Settlement Details Section
				if(settlementDetails!=null && !settlementDetails.isEmpty()){
					settlementDetailsVO = settlementDetails
					.get((gpaSettlementVO.getSettlementId()));
				}
				if(settlementDetailsVO.getGpaCode()!=null){
					row.add(settlementDetailsVO.getGpaCode());

				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getSettlementId()!=null){
					row.add(settlementDetailsVO.getSettlementId());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getChequeNumber()!=null){
					row.add(settlementDetailsVO.getChequeNumber());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getChequeDate()!=null){
					row.add(settlementDetailsVO.getChequeDate().toDisplayDateOnlyFormat());

				}			
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getChequeBranch()!=null){
					row.add(settlementDetailsVO.getChequeBranch());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getChequeBank()!=null){
					row.add(settlementDetailsVO.getChequeBank());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getChequeAmount()!=null){
					row.add(settlementDetailsVO.getChequeAmount().getAmount());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(settlementDetailsVO.getIsDeleted()!=null ){
					row.add(settlementDetailsVO.getIsDeleted());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				row.add(ReportConstants.EMPTY_STRING);	

				gpaSettlementVOs.add(row);

			}
		}

		return gpaSettlementVOs;
	}
	/**
	 * 
	 * @param invoiceStatus
	 * @param settlementStatus
	 * @return
	 */
	private Object populateOneTimeDescription(
			Collection<OneTimeVO> invoiceStatus, String settlementStatus) {
		String description =  settlementStatus;
		for (OneTimeVO oneTimeVO : invoiceStatus) {
			if (settlementStatus.equals(
					oneTimeVO.getFieldValue())) {
				return oneTimeVO.getFieldDescription();
			}
		}
		return description;
	}

}
