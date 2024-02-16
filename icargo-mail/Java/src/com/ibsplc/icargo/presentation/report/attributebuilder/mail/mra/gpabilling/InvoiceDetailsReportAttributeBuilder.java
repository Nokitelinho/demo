/*
 * InvoiceDetailsReportAttributeBuilder.java Created on Feb 21, 2019
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-5526
 *
 */
public class InvoiceDetailsReportAttributeBuilder extends AttributeBuilderAdapter{

	private Log log = LogFactory.getLogger("mailtracking_mra_gpabilling");

	
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
		reportColumns.add("PACOD");  
		reportColumns.add("INVNUM");  
		reportColumns.add("MALIDR");
		reportColumns.add("CSGDOCNUM");
		reportColumns.add("MALDAT");
		reportColumns.add("ORGCOD");
		reportColumns.add("DSTCOD"); 
		reportColumns.add("FLNSEC");
		reportColumns.add("WEIGHT");
		reportColumns.add("RATE");
		reportColumns.add("CURCOD");
		reportColumns.add("MALCHG");
		reportColumns.add("SURCHG"); 
		reportColumns.add("TAX"); 
		reportColumns.add("NETAMT"); 
		reportColumns.add("STLCUR");
		reportColumns.add("OVRRND");
		log.log(Log.FINE, "reportColumns is ijj--------->", reportColumns);

		return reportColumns;
	}
	
	
	
	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the reportData
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> cN51SummaryVOs = new Vector<Vector>();
		Vector<Object> row = null;
		List<CN66DetailsVO> cn66DetailsVos = (ArrayList<CN66DetailsVO>)data;		
		Vector<Vector> reportData = new Vector<Vector>();
		log.log(Log.FINE, "Entering Attribute ---->>", data);
		if (data != null && data.size() > 0){
		
			for(CN66DetailsVO cN66DetailsVO:cn66DetailsVos){
				 row = new Vector<Object>();
				
				row.add((cN66DetailsVO.getGpaCode()!= null)?cN66DetailsVO.getGpaCode():ReportConstants.EMPTY_STRING);
				
				row.add((cN66DetailsVO.getInvoiceNumber()!= null)    
						?cN66DetailsVO.getInvoiceNumber()
						:ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getBillingBasis()!= null) ?cN66DetailsVO.getBillingBasis():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getConsDocNo()!=null)?cN66DetailsVO.getConsDocNo():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getReceivedDate()!=null)?(cN66DetailsVO.getReceivedDate().toDisplayDateOnlyFormat()):ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getOrigin()!= null)?cN66DetailsVO.getOrigin():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getDestination()!= null)?cN66DetailsVO.getDestination():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getSector()!= null)?cN66DetailsVO.getSector():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getTotalWeight()>0)?cN66DetailsVO.getTotalWeight():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getApplicableRate()>0)?cN66DetailsVO.getApplicableRate():"0.0");
				row.add((cN66DetailsVO.getCurrencyCode()!=null)?cN66DetailsVO.getCurrencyCode():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getMailCharge()!=null)?cN66DetailsVO.getMailCharge():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getSurCharge()!=null)?cN66DetailsVO.getSurCharge():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getServiceTax()>0)?cN66DetailsVO.getServiceTax():"0.0");
				row.add((cN66DetailsVO.getNetAmount()!=null)?String.valueOf(cN66DetailsVO.getNetAmount().getAmount()):"0.0");
				row.add((cN66DetailsVO.getSettlementCurrencyCode()!=null)?cN66DetailsVO.getSettlementCurrencyCode():ReportConstants.EMPTY_STRING);
				row.add((cN66DetailsVO.getOverrideRounding()!=null)?cN66DetailsVO.getOverrideRounding():"5");
				cN51SummaryVOs.add(row);
			}  
		
		}
		log.log(Log.FINE, "Exiting Attribute ---->>", reportData);
		return cN51SummaryVOs;
	}

	
					

}
