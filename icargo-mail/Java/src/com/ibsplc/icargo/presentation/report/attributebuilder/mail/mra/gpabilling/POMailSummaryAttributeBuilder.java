/**
 * POMailSummaryAttributeBuilder.java Created on May 14, 2012
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

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.POMailSummaryDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class POMailSummaryAttributeBuilder extends AttributeBuilderAdapter{
	private Log log = LogFactory
	.getLogger("SettlementDetailsAttributeBuilder");
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
		reportColumns.add("SUBCLSCOD");
		reportColumns.add("MALTYP");
		reportColumns.add("ORGSTN");
		reportColumns.add("DSTSTN");
		reportColumns.add("FLTNO");
		reportColumns.add("WGT");
		reportColumns.add("DST");
		reportColumns.add("BLGCUR");
		reportColumns.add("RATE");
		reportColumns.add("AMT");
		reportColumns.add("SRVTAX");
		reportColumns.add("TDS");
		reportColumns.add("PAYAMT");
		reportColumns.add("CHQAMT");
		reportColumns.add("DUEAMT");
		reportColumns.add("CHQNO");
		reportColumns.add("CHQDAT");
		reportColumns.add("STLDAT");
		reportColumns.add("RMK");
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
		return new Vector<Object>();
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
		Vector<Vector> tableData = new Vector<Vector>();
		Object extraInfoObj0 = ((ArrayList<Object>)extraInfo).get(0);			
		Collection <OneTimeVO> mailcategory =(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(0);
		Object extraInfoObj1 = ((ArrayList<Object>)extraInfo).get(1);			
		Collection <OneTimeVO> mailsubclass =(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(1);
		String flight[]=null;
		if (data != null && data.size()>0) {
			int count  =0;
			for (POMailSummaryDetailsVO reportVO : ((Collection<POMailSummaryDetailsVO>) data)) {
				Vector<Object> row = new Vector<Object>();
				count = count +1;
				if(reportVO.getSubClassCode()!=null){
					row.add(populateOneTimeDescription(mailcategory,reportVO.getSubClassCode()));

				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getMailType()!=null){
					row.add(populateOneTimeDescription(mailcategory,reportVO.getMailType()));
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getOriginStation()!=null){
					row.add(reportVO.getOriginStation());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getDestinationStation()!=null){
					row.add(reportVO.getDestinationStation());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getFlightNumber()!=null){
					if(reportVO.getFlightNumber().contains(",")){
						flight=reportVO.getFlightNumber().split(",");
						row.add(flight[0]);
					}
					else{
						row.add(reportVO.getFlightNumber());
					}

				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getWeight()!=null){
					row.add(reportVO.getWeight());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getDistance()!=null){
					row.add(reportVO.getDistance());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getBillingCurrency()!=null){
					row.add(reportVO.getBillingCurrency());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getRate()!=null){
					row.add(reportVO.getRate());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getAmount()!=null){
					row.add(reportVO.getAmount());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getServiceTax()!=null){
					row.add(reportVO.getServiceTax());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getTds()!=null){
					row.add(reportVO.getTds());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getPayableAmount()!=null){
					row.add(reportVO.getPayableAmount());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getChequeAmount()!=null){
					row.add(reportVO.getChequeAmount());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getDueAmount()!=null){
					row.add(reportVO.getDueAmount());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getChequeNumber()!=null){
					row.add(reportVO.getChequeNumber());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getChequeDate()!=null){
					row.add(reportVO.getChequeDate().toDisplayDateOnlyFormat());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getSettlementDate()!=null){
					row.add(reportVO.getSettlementDate().toDisplayDateOnlyFormat());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getRemarks()!=null){
					row.add(reportVO.getRemarks());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				tableData.add(row);

			}



		}
		else {
			Vector<Object> emptyRow = new Vector<Object>();

			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);			
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);
			emptyRow.add(ReportConstants.EMPTY_STRING);

			tableData.add(emptyRow);
		}
		return tableData;
	}
	/**
	 * 
	 * @param oneTimevoS
	 * @param mailType
	 * @return
	 */
	private Object populateOneTimeDescription(Collection<OneTimeVO> oneTimevoS,
			String mailType) {
		String description = mailType;
		if(oneTimevoS != null && oneTimevoS.size()>0){
			for(OneTimeVO oneTimeVO :oneTimevoS){

				if(mailType.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}

			}
		}

		return description;	
	}
}
