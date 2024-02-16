/*
 * TransferManifestAttributeBuilder.java Created on 28 MAR, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3353
 *
 */
public class TransferManifestAttributeBuilder  extends AttributeBuilderAdapter{
	private static final String CLASS_NAME = "MailTransferManifestAttributeBuilder";
	private Log log = LogFactory.getLogger(CLASS_NAME);
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

		columns.add("CONT");
		columns.add("AWB");
		columns.add("DSN");
		columns.add("OOE");
		columns.add("DOE");
		columns.add("CATG");
		columns.add("CLASS");
		columns.add("SUBCL");
		columns.add("NOB");
		columns.add("WGT");

		
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
	 * @return Vector<Vector> the reportData
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {


		List<DSNVO> dsnVOS = (ArrayList<DSNVO>)data;
		Vector<Vector> reportData = new Vector<Vector>();

		log.log(Log.FINE, "Entering Attribute ---->>", data);
		if (data != null && data.size() > 0){
			if(dsnVOS !=null && dsnVOS.size()>0){
				for(DSNVO dsnVO:dsnVOS){
					Vector<Object> row = new Vector<Object>();

					
						row.add((dsnVO.getContainerNumber()!= null)
								?dsnVO.getContainerNumber()
								:ReportConstants.EMPTY_STRING);
						row.add((dsnVO.getAwbNumber()!= null)
								?dsnVO.getAwbNumber()
								:ReportConstants.EMPTY_STRING);
						row.add((dsnVO.getDsn()!= null)
								?dsnVO.getDsn()
								:ReportConstants.EMPTY_STRING);
						row.add((dsnVO.getOriginExchangeOffice()!= null)
								?dsnVO.getOriginExchangeOffice()
								:ReportConstants.EMPTY_STRING);
						row.add((dsnVO.getDestinationExchangeOffice()!= null)
								?dsnVO.getDestinationExchangeOffice()
								:ReportConstants.EMPTY_STRING);
						row.add((dsnVO.getMailCategoryCode()!= null)
								?dsnVO.getMailCategoryCode()
								:ReportConstants.EMPTY_STRING);
						row.add((dsnVO.getMailClass()!= null)
								?dsnVO.getMailClass()
								:ReportConstants.EMPTY_STRING);
						if(dsnVO.getMailSubclass()!= null){
						
							if(	'_' == dsnVO.getMailSubclass().charAt(1) ){
								row.add(dsnVO.getMailSubclass().charAt(0));
							}
							else{
								row.add(dsnVO.getMailSubclass());
							}
						}
						else{
							row.add(ReportConstants.EMPTY_STRING);
						}
						row.add((dsnVO.getBags()>0)
								?String.valueOf(dsnVO.getBags())
								:0);
						/*row.add((dsnVO.getWeight()>0)
								?String.valueOf(TextFormatter.formatDouble(dsnVO.getWeight() , 2))
								:0);  */
						row.add((dsnVO.getWeight().getRoundedSystemValue()>0)
								?String.valueOf(dsnVO.getWeight().getRoundedSystemValue())
								:0);  //added by A-8236 for ICRD-251462

					reportData.add(row);
				}
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
			 * @param parameters the parameter data
			 * @param extraInfo information required for formatting the parameters
			 * @return Vector the report parameters
			 */
			@Override
			public Vector<Object> getReportParameters(
									Collection parameters, Collection extraInfo) {

				Vector<Object> reportParameters = new Vector<Object>();
				log.log(Log.FINE, "parameters..in attribute ::::", parameters);
				Object dataParameters = ((ArrayList<Object>)parameters).get(0);


				TransferManifestVO manifestVO = (TransferManifestVO)dataParameters;
				reportParameters.add((manifestVO.getTransferManifestId()!= null)?manifestVO.getTransferManifestId():ReportConstants.EMPTY_STRING);
				reportParameters.add((manifestVO.getAirPort()!= null)?manifestVO.getAirPort():ReportConstants.EMPTY_STRING);
				reportParameters.add((manifestVO.getTransferredDate()!= null)?manifestVO.getTransferredDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
				if(!(manifestVO.getTransferredToFltNumber()==null || "-1".equals(manifestVO.getTransferredToFltNumber()))){
					String toFltDate="";
					if(manifestVO.getToFltDat()!=null ){
						toFltDate = manifestVO.getToFltDat().toDisplayDateOnlyFormat().toUpperCase();
					}
					String str = new StringBuilder().append(manifestVO.getTransferredToCarrierCode()).append(" ")
					.append(manifestVO.getTransferredToFltNumber()).append("  ").append(toFltDate).toString();
					reportParameters.add(str);
				}
				else{
					if(manifestVO.getTransferredToCarrierCode()!=null){
						reportParameters.add(manifestVO.getTransferredToCarrierCode());
					}
					else{
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
				}
				reportParameters.add((manifestVO.getFromCarCodeDesc()!= null)?manifestVO.getFromCarCodeDesc():ReportConstants.EMPTY_STRING);
				reportParameters.add((manifestVO.getToCarCodeDesc()!= null)?manifestVO.getToCarCodeDesc():ReportConstants.EMPTY_STRING);
				reportParameters.add((manifestVO.getTransferredDate()!= null)?manifestVO.getTransferredDate().toDisplayFormat("hh:mm"):ReportConstants.EMPTY_STRING);
				reportParameters.add((manifestVO.getTransferredDate()!= null)?manifestVO.getTransferredDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);

				return reportParameters;
			}
		}
