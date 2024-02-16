/*
 * CN51ReportAttributeBuilder.java Created on Mar 01, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.gpabilling.indigo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-4823
 *
 */
public class CN51ReportAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("mailtracking_mra_gpabilling");	

	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";


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

		columns.add("SECTOR");	
		columns.add("CATCOD");	
		columns.add("WGT");
		columns.add("RAT");	
		columns.add("AMT");		
		columns.add("DISTANCE");
		columns.add("POAADR");
		columns.add("BLDPRD");
		columns.add("ORGCOD");
		columns.add("SRVTAX");
		columns.add("PARVAL");
		columns.add("UNTCOD");
		columns.add("CTY");
		columns.add("PINCOD");

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
		Vector<Vector> tableData = new Vector<Vector>();

		log.log(Log.INFO, "extraInfo-->", extraInfo);
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection <OneTimeVO> category =(Collection <OneTimeVO>) ((ArrayList<Object>)extraInfo).get(0);
		if (data != null && data.size()>0) {
			int count  =0;
			for (CN51DetailsVO reportVO : ((Collection<CN51DetailsVO>) data)) {
				Vector<Object> row = new Vector<Object>();
				count = count +1;
				if(reportVO.getSector()!=null ){
					row.add(reportVO.getSector());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getMailCategoryCode()!=null){
					row.add(populateOneTimeDescription(category,reportVO.getMailCategoryCode()));
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				row.add(reportVO.getTotalWeight());
				row.add(reportVO.getApplicableRate());	
				if("Y".equals(reportVO.getConRatTax())){
					double amt=0.0;
					if(reportVO.getTotalAmount()!=null){

						amt=reportVO.getTotalAmount().getAmount()+reportVO.getServiceTax();
						row.add(amt);
					}	

				}
				else{

					if(reportVO.getTotalAmount()!=null){
						row.add(reportVO.getTotalAmount().getAmount());
					}	


					else{
						row.add(ReportConstants.EMPTY_STRING);
					}
				}

				row.add(reportVO.getDistance());			
				row.add(reportVO.getPoaAddress());

				if(reportVO.getBillingPeriod()!=null){
					row.add(reportVO.getBillingPeriod().substring(12, 20));
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(reportVO.getOrigin()!=null){
					row.add(reportVO.getOrigin());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				row.add(reportVO.getServiceTax());
				row.add(reportVO.getConRatTax());
				row.add(reportVO.getUnitCode());
				row.add(reportVO.getCity());
				row.add(reportVO.getPinCode());
				tableData.add(row);

			}
		}
		else{
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
			tableData.add(emptyRow);

		}

		log.log(Log.INFO, "tableData-->", tableData);
		return tableData;
	}

	@Override
	public Vector<Object> getReportParameters(
			Collection parameters, Collection extraInfo) {
		//System.out.println("Inside AttributeBuilder!!!!!!");

		Vector<Object> reportParameters = new Vector<Object>();
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		CN51CN66FilterVO cN51CN66VO = (CN51CN66FilterVO) dataParameters; 		
		//reportParameters.add(cN51CN66VO.getBillingPeriod().substring(16,
		//		22));
		reportParameters.add(cN51CN66VO.getInvoiceNumber());


		//Added by indu
		reportParameters.add(cN51CN66VO.getAirlineCode());
		return reportParameters;
	}

	/**
	 * 
	 * @param mailCategoryCode
	 * @param category
	 * @return
	 */
	private Object populateOneTimeDescription(
			Collection<OneTimeVO> mailCategoryCode, String category) {
		String description =  category;
		for (OneTimeVO oneTimeVO : mailCategoryCode) {
			if (category.equals(
					oneTimeVO.getFieldValue())) {
				return oneTimeVO.getFieldDescription();
			}
		}
		return description;
	}



}

