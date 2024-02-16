/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.AV7ReportAttributeBuilder.java
 *
 *	Created by	:	A-6986
 *	Created on	:	May 08, 2018
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AV7ReportAttributeBuilder extends
AttributeBuilderAdapter{
	
	private static final String CLASS_NAME = "AV7ReportAttributeBuilder";
	private Log log = LogFactory.getLogger(CLASS_NAME);
	
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
		
		Vector<String> columns = new Vector<String>();
		columns.add("ORIGINOFFICE");
		columns.add("DESTNOFFICE");
		columns.add("LCAONO");
		columns.add("CPNO");
		columns.add("LCAOWT");
		columns.add("LCAOWTDEC");
		columns.add("CPWT");
		columns.add("CPWTDEC");
		columns.add("REMARKS");
		
		
		return columns;
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
	 * @return Vector<Vector> the reportData
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		
		
		List<ConsignmentDocumentVO> consignmentDocumentVOs = (ArrayList<ConsignmentDocumentVO>) data;
		Vector<Vector> reportData = new Vector<Vector>();
		int count = 0;
		
		if (consignmentDocumentVOs != null && consignmentDocumentVOs.size() > 0) {

			ConsignmentDocumentVO consignDocumentVO = consignmentDocumentVOs
					.iterator().next();

			for (MailInConsignmentVO mailVo : consignDocumentVO
					.getMailInConsignmentcollVOs()) {
				
				String weightOfLetters = null;
				String weightOfLettersDec = null;
				String weightOfParcels = null;
				String weightOfParcelsDec = null;
				Vector<Object> row = new Vector<Object>();
				
				row.add((mailVo.getOriginExchangeOffice() != null) ? String.valueOf(mailVo
						.getOriginExchangeOffice()) : ReportConstants.EMPTY_STRING);
				
				row.add((mailVo.getDestinationExchangeOffice() != null) ? String.valueOf(mailVo
						.getDestinationExchangeOffice()) : ReportConstants.EMPTY_STRING);
				
				row.add((mailVo.getTotalLetterBags() >0 ) ? String.valueOf(mailVo
						.getTotalLetterBags()) : ReportConstants.EMPTY_STRING);
				
				row.add((mailVo.getTotalParcelBags() > 0) ? String.valueOf(mailVo
						.getTotalParcelBags()) : ReportConstants.EMPTY_STRING);
				
				if(mailVo.getTotalLetterWeight().getRoundedSystemValue()>0){
					
					double value = mailVo.getTotalLetterWeight().getDisplayValue();
					weightOfLetters = String.valueOf(Math.round(value * 10) /10.0);
					weightOfLettersDec = weightOfLetters.substring(weightOfLetters.indexOf(".")+1,weightOfLetters.length());
				}

				
				row.add((mailVo.getTotalLetterWeight().getRoundedSystemValue() > 0) ? 
						weightOfLetters.substring(0,weightOfLetters.indexOf(".")) : ReportConstants.EMPTY_STRING);
				row.add((weightOfLettersDec != null &&(Integer.parseInt(weightOfLettersDec)) > 0) ?
						weightOfLettersDec : ReportConstants.EMPTY_STRING);
				
				/*row.add((mailVo.getTotalLetterWeight().getRoundedSystemValue() > 0) ? 
				weightOfLetters.split(",")[1] : ReportConstants.EMPTY_STRING);*/
				
				if(mailVo.getTotalParcelWeight().getRoundedSystemValue()>0){
				 
				 double value = mailVo.getTotalParcelWeight().getDisplayValue();
				 weightOfParcels = String.valueOf(Math.round(value * 10) /10.0);
				 weightOfParcelsDec = weightOfParcels.substring(weightOfParcels.indexOf(".")+1,weightOfParcels.length());
				}

				row.add((mailVo.getTotalParcelWeight().getRoundedSystemValue() > 0) ?
						weightOfParcels.substring(0,weightOfParcels.indexOf(".")) : ReportConstants.EMPTY_STRING);
				
				row.add((weightOfParcelsDec!= null && (Integer.parseInt(weightOfParcelsDec)) > 0) ?
						weightOfParcelsDec : ReportConstants.EMPTY_STRING);
				
				/*row.add((consignDocumentVO.getTotalParcelWeight().getRoundedSystemValue() > 0) ? 
				weightOfParcels.split(",")[1] : ReportConstants.EMPTY_STRING);*/
				
				row.add((consignDocumentVO.getRemarks() != null) ? String.valueOf(consignDocumentVO
						.getRemarks()) : ReportConstants.EMPTY_STRING);
				
				reportData.add(row);
				
			}
		
		}
		
		log.log(Log.FINE, "REPORT DATA", reportData);
		return reportData;
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
	@Override
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {

		Vector<Object> reportParameters = new Vector<Object>();
		if (parameters != null) {
			Object dataParameters = ((ArrayList<Object>) parameters).get(0);
			ConsignmentDocumentVO consignmentDocumentVO =  (ConsignmentDocumentVO) dataParameters;
			// Set if the report Type is flight Type or Carrier Type
			String date = "";
			String letterWeight = "";
			String parcelWeight = "";
			LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			 fromDate=consignmentDocumentVO.getConsignmentDate();
			date=fromDate.toDisplayDateOnlyFormat();
			
			reportParameters.add(consignmentDocumentVO.getConsignmentNumber());
			reportParameters.add(date);
			reportParameters.add(consignmentDocumentVO.getDestination());
			reportParameters.add(consignmentDocumentVO.getFlightDetails());
			reportParameters.add(consignmentDocumentVO.getFlightRoute());
			reportParameters.add(Integer.toString(consignmentDocumentVO.getTotalSacks()));
			
			if(consignmentDocumentVO.getTotalLetterWeight()!= null
					&& consignmentDocumentVO.getTotalLetterWeight().getRoundedSystemValue() > 0){
				double value = consignmentDocumentVO.getTotalLetterWeight().getDisplayValue();
				letterWeight = String.valueOf(Math.round(value * 10) /10.0);
			}
			if(consignmentDocumentVO.getTotalParcelWeight()!= null
					&& consignmentDocumentVO.getTotalParcelWeight().getRoundedSystemValue() > 0){
				double value = consignmentDocumentVO.getTotalParcelWeight().getDisplayValue();
				 parcelWeight = String.valueOf(Math.round(value * 10) /10.0);		
			}
			//Letter Weight
			if(letterWeight!= null && letterWeight.trim().length()>0 && letterWeight != "0.0"){
				reportParameters.add(letterWeight.substring(0,letterWeight.indexOf(".")));
			}else{
				reportParameters.add("");
			}
			//ParcelWeight
			if(parcelWeight!= null && parcelWeight.trim().length()>0 && parcelWeight != "0.0"){
				reportParameters.add(parcelWeight.substring(0,parcelWeight.indexOf(".")));
			}else{
				reportParameters.add("");
			}
			//LetterWeightDecimal
			if(letterWeight!= null && letterWeight.trim().length()>0 &&  !letterWeight.contains(".0") ){	
				reportParameters.add(letterWeight.substring(letterWeight.indexOf(".")+1,letterWeight.length()));
			}else{
				reportParameters.add("");
			}
			//ParcelWeightDecimal
			if(parcelWeight!= null && parcelWeight.trim().length()>0 && !parcelWeight.contains(".0")){
				reportParameters.add(parcelWeight.substring(parcelWeight.indexOf(".")+1,parcelWeight.length()));
			}else{
				reportParameters.add("");
			}
			
		}
		return reportParameters;
	}

}
