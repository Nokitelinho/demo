/*
 * RejectionMemoAttributeBuilder.java Created on Mar 27, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.airlinebilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-2391
 *
 */

public class RejectionMemoAttributeBuilder extends AttributeBuilderAdapter{
	private Log log=LogFactory.getLogger("AccounRejectionMemoAttributeBuilder Entry");
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

		columns.add("INVNUM");
		columns.add("CLRPRD");
		columns.add("DSN");
		columns.add("EXPCOD");
		columns.add("PVNRAT");
		columns.add("PVNWGT");
		columns.add("RPTWGT");
		return columns;
		
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
	
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<Vector>();
		
		//Object dataRecords = ((ArrayList<Object>)data).get(0);
		Collection<AirlineExceptionsVO> airlineExceptionsVOs=new ArrayList<AirlineExceptionsVO>(data);
		if(airlineExceptionsVOs!=null){
   		for(AirlineExceptionsVO vo:airlineExceptionsVOs){
   			Vector<Object> row=new Vector<Object>();
   			row.add(vo.getInvoiceNumber());
   			row.add(vo.getClearancePeriod());
   			row.add(vo.getDespatchSerNo());
   			row.add(vo.getExceptionCode());
   			row.add(vo.getProvRate());
   			row.add(vo.getProvWeight());
   			row.add(vo.getRptdWeight());
   			tableData.add(row);
   		}
		}
   		log.log(Log.INFO, "tableData-->", tableData);
	return tableData;
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
		/* (non-Javadoc)
		 * @see com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilder#getReportParameters(java.util.Collection, java.util.Collection)
		 */
		@Override
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {
			Vector<Object> reportParameters = new Vector<Object>();
			String fromDate="";
			String toDate="";
			if(parameters!=null&& parameters.size()>0){
				log.log(Log.FINE,
						"reportParameters are ----------------->>>>>>",
						parameters);
				Object dataParameters = ((ArrayList<Object>)parameters).get(0);
				RejectionMemoVO rejectionMemoVO = (RejectionMemoVO)dataParameters;
				if(rejectionMemoVO!=null)	{
					
					if(rejectionMemoVO.getAirlineName()!=null){
						reportParameters.add(rejectionMemoVO.getAirlineName());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(String.valueOf(rejectionMemoVO.getAirlineIdentifier())!=null){
						String airlineNumber=String.valueOf(rejectionMemoVO.getAirlineIdentifier());
						airlineNumber=airlineNumber.substring(1,4);
						reportParameters.add(airlineNumber);
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getSerialNumber()!=null){
						reportParameters.add(rejectionMemoVO.getSerialNumber());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					
					if(rejectionMemoVO.getInwardInvoiceNumber()!=null){
						reportParameters.add(rejectionMemoVO.getInwardInvoiceNumber());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					
					if (rejectionMemoVO.getInwardInvoiceDate()!=null ) {
														
								LocalDate localToDate = (LocalDate)rejectionMemoVO.getInwardInvoiceDate();
								
								toDate=TimeConvertor.toStringFormat
											(localToDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
									 
								
										reportParameters.add(toDate);		
							
						}
						else {
						
						reportParameters.add(ReportConstants.EMPTY_STRING);
					 }
					if(rejectionMemoVO.getOutwardInvoiceNumber()!=null){
						reportParameters.add(rejectionMemoVO.getOutwardInvoiceNumber());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getOutwardClearancePeriod()!=null){
						reportParameters.add(rejectionMemoVO.getOutwardClearancePeriod());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getMonthOfTrans()!=null){
						reportParameters.add(rejectionMemoVO.getMonthOfTrans());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getContractCurrencyCode()!=null){
						reportParameters.add(rejectionMemoVO.getContractCurrencyCode());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getContractBilledAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getContractBilledAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getContractAcceptedAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getContractAcceptedAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getContractRejectedAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getContractRejectedAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getContractBillingExchangeRate()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getContractBillingExchangeRate()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					
					if(rejectionMemoVO.getBillingBilledAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getBillingBilledAmount().getRoundedAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getBillingAcceptedAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getBillingAcceptedAmount().getRoundedAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getBillingRejectedAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getBillingRejectedAmount().getRoundedAmount()));
					}
					if(rejectionMemoVO.getBillingCurrencyCode()!=null){
						reportParameters.add(rejectionMemoVO.getBillingCurrencyCode());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getBillingClearanceExchangeRate()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getBillingClearanceExchangeRate()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getClearanceBilledAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getClearanceBilledAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getClearanceAcceptedAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getClearanceAcceptedAmount()));
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getClearanceRejectedAmount()!=null){
						reportParameters.add(String.valueOf(rejectionMemoVO.getClearanceRejectedAmount()));
					}
					if(rejectionMemoVO.getClearanceCurrencyCode()!=null){
						reportParameters.add(rejectionMemoVO.getClearanceCurrencyCode());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getRequestAuthorisationIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getRequestAuthorisationIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getRequestAuthorisationReference()!=null){
						reportParameters.add(rejectionMemoVO.getRequestAuthorisationReference());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if (rejectionMemoVO.getRequestAuthorisationDate()!=null ) {
														
								LocalDate localToDate = (LocalDate)rejectionMemoVO.getRequestAuthorisationDate();
								
								toDate=TimeConvertor.toStringFormat
											(localToDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
									 
								
										reportParameters.add(toDate);		
							
						}
						else {
						
						reportParameters.add(ReportConstants.EMPTY_STRING);
					 }
					if(rejectionMemoVO.getDuplicateBillingIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getDuplicateBillingIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getDuplicateBillingInvoiceNumber()!=null){
						reportParameters.add(rejectionMemoVO.getDuplicateBillingInvoiceNumber());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if (rejectionMemoVO.getDuplicateBillingInvoiceDate()!=null ) {
						
								
								LocalDate localToDate = (LocalDate)rejectionMemoVO.getDuplicateBillingInvoiceDate();
								
								toDate=TimeConvertor.toStringFormat
											(localToDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
									 
								
										reportParameters.add(toDate);		
							
						}
						else {
						
						reportParameters.add(ReportConstants.EMPTY_STRING);
					 }
					if(rejectionMemoVO.getOutTimeLimitsForBillingIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getOutTimeLimitsForBillingIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getChargeNotCoveredByContractIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getChargeNotCoveredByContractIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getChargeNotConvertedToContractIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getChargeNotConvertedToContractIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getNoApprovalIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getNoApprovalIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getNoReceiptIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getNoReceiptIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getIncorrectExchangeRateIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getIncorrectExchangeRateIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getOtherIndicator()!=null){
						reportParameters.add(rejectionMemoVO.getOtherIndicator());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getRemarks()!=null){
						reportParameters.add(rejectionMemoVO.getRemarks());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getOutwardInvoiceNumber()!=null){
						reportParameters.add(rejectionMemoVO.getOutwardInvoiceNumber());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if (rejectionMemoVO.getOutwardInvoiceDate()!=null ) {
																		
								LocalDate localToDate = (LocalDate)rejectionMemoVO.getOutwardInvoiceDate();
								
								toDate=TimeConvertor.toStringFormat
											(localToDate.toCalendar(),TimeConvertor.CALENDAR_DATE_FORMAT);
									 
								
										reportParameters.add(toDate);		
							
						}
						else {
						
						reportParameters.add(ReportConstants.EMPTY_STRING);
					 }
					if(rejectionMemoVO.getOutMonthOfClearance()!=null){
						reportParameters.add(rejectionMemoVO.getOutMonthOfClearance());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getOrigin()!=null){
						reportParameters.add(rejectionMemoVO.getOrigin());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getDestination()!=null){
						reportParameters.add(rejectionMemoVO.getDestination());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getSectorFrom()!=null){
						reportParameters.add(rejectionMemoVO.getSectorFrom());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getSectorTo()!=null){
						reportParameters.add(rejectionMemoVO.getSectorTo());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getDsn()!=null){
						reportParameters.add(rejectionMemoVO.getDsn());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getMemoCode()!=null){
						reportParameters.add(rejectionMemoVO.getMemoCode());
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
					if(rejectionMemoVO.getOutwardInvoiceDate()!=null){
						reportParameters.add(rejectionMemoVO.getOutwardInvoiceDate().toDisplayFormat(" MMM "));
					}else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
				}
				
			
		}
			log.log(Log.FINE, "reportParameters are ----------------->>>>>>",
					reportParameters);
		return reportParameters;
		
	}

}
