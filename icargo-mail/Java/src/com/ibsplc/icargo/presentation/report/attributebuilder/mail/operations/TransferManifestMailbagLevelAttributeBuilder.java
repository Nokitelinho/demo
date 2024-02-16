/*
 * TransferManifestMailbagLevelAttributeBuilder.java Created on 09 Nov, 2020
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
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class TransferManifestMailbagLevelAttributeBuilder  extends AttributeBuilderAdapter{
	private static final String CLASS_NAME = "TransferManifestMailbagLevelAttributeBuilder";
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
		columns.add("MALIDR");
		columns.add("ORG");
		columns.add("DST");
		columns.add("UPLARP");
		columns.add("CSGDOC");
		columns.add("CSGDAT");
		columns.add("POACOD");
		columns.add("AWB");

		
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

		if ( dsnVOS !=null && !dsnVOS.isEmpty()){
			
				for(DSNVO dsnVO:dsnVOS){
					Vector<Object> row = new Vector<Object>();

						row.add((dsnVO.getContainerNumber()!= null)
								?dsnVO.getContainerNumber()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getMailbagId()!= null)
								?dsnVO.getMailbagId()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getOrigin()!= null)
								?dsnVO.getOrigin()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getDestination()!= null)
								?dsnVO.getDestination()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getUpliftAirport()!= null)
								?dsnVO.getUpliftAirport()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getConsignmentNumber()!= null)
								?dsnVO.getConsignmentNumber()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getConsignmentDate()!= null)
								?dsnVO.getConsignmentDate().toDisplayFormat("dd-MMM-yyyy hh:mm:ss").toUpperCase()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getPaCode()!= null)
								?dsnVO.getPaCode()
								:ReportConstants.EMPTY_STRING);
						
						row.add((dsnVO.getAwbNumber()!= null)
								?dsnVO.getAwbNumber()
								:ReportConstants.EMPTY_STRING);
						
						 

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
