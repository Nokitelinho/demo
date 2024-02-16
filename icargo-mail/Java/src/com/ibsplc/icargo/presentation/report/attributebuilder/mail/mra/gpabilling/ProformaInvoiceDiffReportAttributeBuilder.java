/*
 * ProformaInvoiceDiffReportAttributeBuilder.java Created on Aug 12, 2008
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

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.ProformaInvoiceDiffReportVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3271
 *
 */
public class ProformaInvoiceDiffReportAttributeBuilder extends AttributeBuilderAdapter {
	
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
		
		columns.add("DSNNUM");
		columns.add("CCANUM");
		columns.add("INVNUM");
		columns.add("BLGFRM");
		columns.add("BLGTO");
		columns.add("COUNTRY");
		columns.add("FRTCHG");
		columns.add("DUEARL");
		columns.add("RMK");
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
		
		log.entering("ProformaInvoiceDiffReport", "getReportData");
		
		Vector<Vector> tableData = new Vector<Vector>();
		Vector<Object> row = null;
		Object dataRecords = ((ArrayList<Object>)extraInfo).get(0);
		Object datas = ((ArrayList<Object>)extraInfo).get(1);
		
		log.log(Log.INFO, "DATARECORDS $$$", dataRecords);
		Collection<ProformaInvoiceDiffReportVO> proformaInvoiceDiffReportVOs   = (Collection<ProformaInvoiceDiffReportVO>) dataRecords; 
		CN51SummaryVO cn51SummaryVo = (CN51SummaryVO)datas;
		String totalAmt = null;	
		for(ProformaInvoiceDiffReportVO proformaInvoiceDiffReportVO : proformaInvoiceDiffReportVOs){
			for(CN51SummaryVO cn51SummaryVO : proformaInvoiceDiffReportVO.getCn51SummaryVOs()){
				for(CN66DetailsVO cn66DetailsVO : cn51SummaryVO.getCn66details()){
					if("WD".equals(cn66DetailsVO.getBillingStatus())){
						row = new Vector<Object>();
						if(cn66DetailsVO.getDsn()!= null){
							row.add(cn66DetailsVO.getDsn());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}						
						if(cn51SummaryVO.getCcaReference() != null){
							row.add(cn51SummaryVO.getCcaReference());	
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}						
						if(proformaInvoiceDiffReportVO.getInvoiceNumber()!= null){
							row.add(proformaInvoiceDiffReportVO.getInvoiceNumber());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}	
						if(cn51SummaryVO.getFromDate()!= null){
							row.add(cn51SummaryVO.getFromDate().toDisplayDateOnlyFormat());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}
						if(cn51SummaryVO.getToDate()!= null){
							row.add(cn51SummaryVO.getToDate().toDisplayDateOnlyFormat());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}						
						if(cn51SummaryVo.getCountryCode()!= null){
							row.add(cn51SummaryVo.getCountryCode());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}
						
						totalAmt = String.valueOf(cn51SummaryVO.getTotalAmountInBillingCurrency().getAmount());
						if(totalAmt != null){
							row.add(totalAmt);
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}						
						if(cn51SummaryVO.getDueAirline() != null){
							row.add(cn51SummaryVO.getDueAirline());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}											
						if(cn66DetailsVO.getRemarks() != null){
							row.add(cn66DetailsVO.getRemarks());
						}else{
							row.add(ReportConstants.EMPTY_STRING);
						}
						tableData.add(row);
					}
				}				
			}
		}
		log.log(Log.INFO, "tableData-->", tableData);
		return tableData;
	}
	
	
	/**
	 *  @param parameters
	 *  @param extraInfo
	 *  @return
	 * 
	 */
	@Override
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {
		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		CN51SummaryVO cn51SummaryVO=(CN51SummaryVO)dataParameters;
		
		Object dataRecords = ((ArrayList<Object>)extraInfo).get(0);
		log.log(Log.INFO, "DATARECORDS ", dataRecords);
		Collection<ProformaInvoiceDiffReportVO> proformaInvoiceDiffReportVOs   = (Collection<ProformaInvoiceDiffReportVO>) dataRecords; 
		int dsnCount = 0;
		int ccaCount = 0;
		if(cn51SummaryVO!=null){
			
			reportParameters.add(cn51SummaryVO.getFunctionPoint());
			String fromDate = TimeConvertor.toStringFormat((cn51SummaryVO.getFromDate()).
					toCalendar(),"dd-MMM-yyyy");
			reportParameters.add(fromDate);
			
			String toDate = TimeConvertor.toStringFormat((cn51SummaryVO.getToDate()).
					toCalendar(),"dd-MMM-yyyy");
			reportParameters.add(toDate);

			double totalAmountInBillingCurrency = 0.0;
			double totDueAirline = 0.0;
			
			if(proformaInvoiceDiffReportVOs != null && proformaInvoiceDiffReportVOs.size()>0){
				for(ProformaInvoiceDiffReportVO proformaInvoiceDiffReportVO:proformaInvoiceDiffReportVOs){
					if(proformaInvoiceDiffReportVO.getCn51SummaryVOs()!= null &&
							proformaInvoiceDiffReportVO.getCn51SummaryVOs().size()>0){
						for(CN51SummaryVO vo : proformaInvoiceDiffReportVO.getCn51SummaryVOs()){
							
							String ccaReference = vo.getCcaReference();
							if(vo.getTotalAmountInBillingCurrency().getAmount() != 0.0){
								double totalFrt = vo.getTotalAmountInBillingCurrency().getAmount();
								
								if (totalFrt != 0.0 ){
									totalAmountInBillingCurrency += totalFrt;
								}
							}
							if(vo.getDueAirline() != null){
								double totDueArl = Double.parseDouble(vo.getDueAirline());
								if (totDueArl != 0.0 ){
									totDueAirline += totDueArl;
								}
							}
							if (ccaReference != null){
								ccaCount = 0 + ccaCount;
								ccaCount++;
							}
							dsnCount = 0 + dsnCount;
							dsnCount++;
						}
					}
				}	
			}
			reportParameters.add(String.valueOf(totalAmountInBillingCurrency)); 
			
			reportParameters.add(String.valueOf(totDueAirline));
			
			if (dsnCount != 0 ){
				reportParameters.add(String.valueOf(dsnCount));
			}else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			
			reportParameters.add(String.valueOf(ccaCount));
		}
		log.log(Log.FINE, "report param", reportParameters);
		return reportParameters;
	}
}
