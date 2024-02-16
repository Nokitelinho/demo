/*
 * MailHandedOverAttributeBuilder.java Created on FEB 29, 2008
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

import com.ibsplc.icargo.business.mail.operations.vo.MailHandedOverFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandedOverVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3353
 *
 */
public class MailHandedOverAttributeBuilder extends AttributeBuilderAdapter{
	private static final String CLASS_NAME = "MailHandedOverAttributeBuilder";
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
				
		columns.add("MALIDR");
		columns.add("DSN");
		columns.add("INBFLT");
		columns.add("FLTDAT");
		columns.add("ONCAR");
		columns.add("ONFLT");
		columns.add("ONFLDAT");
		columns.add("OOE");
		columns.add("DOE");
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
		
		
		List<MailHandedOverVO> mailHandedOverVOS = (ArrayList<MailHandedOverVO>)data;		
		Vector<Vector> reportData = new Vector<Vector>();
		log.log(Log.FINE, "Entering Attribute ---->>", data);
		if (data != null && data.size() > 0){
		
			for(MailHandedOverVO mailHandedOverVO:mailHandedOverVOS){
				Vector<Object> row = new Vector<Object>();
				row.add((mailHandedOverVO.getMailbagId()!= null)
						?mailHandedOverVO.getMailbagId()
						:ReportConstants.EMPTY_STRING);
				
				row.add((mailHandedOverVO.getDsn()!= null)
						?mailHandedOverVO.getDsn()
						:ReportConstants.EMPTY_STRING);
				mailHandedOverVO.setInwardFlightCarrierCode((mailHandedOverVO.getInwardFlightCarrierCode()!=null)?mailHandedOverVO.getInwardFlightCarrierCode():ReportConstants.EMPTY_STRING);
				mailHandedOverVO.setInwardFlightNum((mailHandedOverVO.getInwardFlightNum()!=null)?mailHandedOverVO.getInwardFlightNum():ReportConstants.EMPTY_STRING);
				String str = new StringBuilder().append(mailHandedOverVO.getInwardFlightCarrierCode())
			       .append(" ").append(mailHandedOverVO.getInwardFlightNum()).toString();
				row.add((str!= null)?str:ReportConstants.EMPTY_STRING);
				row.add((mailHandedOverVO.getInwardFlightDate()!= null)?mailHandedOverVO.getInwardFlightDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
				row.add((mailHandedOverVO.getOnwardCarrier()!= null)?mailHandedOverVO.getOnwardCarrier():ReportConstants.EMPTY_STRING);
				mailHandedOverVO.setOnwardCarrier((mailHandedOverVO.getOnwardCarrier()!=null)?mailHandedOverVO.getOnwardCarrier():ReportConstants.EMPTY_STRING);
				mailHandedOverVO.setOnwardFlightNum((mailHandedOverVO.getOnwardFlightNum()!=null)?mailHandedOverVO.getOnwardFlightNum():ReportConstants.EMPTY_STRING);
				String strOf="";
				if(mailHandedOverVO.getOnwardFlightNum()!=null && mailHandedOverVO.getOnwardFlightNum().trim().length()>0)
					{
						strOf = new StringBuilder().append(mailHandedOverVO.getOnwardCarrier()).append(" ").append(mailHandedOverVO.getOnwardFlightNum()).toString();
					}
				row.add((strOf!= null)?strOf:ReportConstants.EMPTY_STRING);
				row.add((mailHandedOverVO.getOnwardFlightDate()!= null)?mailHandedOverVO.getOnwardFlightDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
				row.add((mailHandedOverVO.getOoe()!= null)?mailHandedOverVO.getOoe():ReportConstants.EMPTY_STRING);
				row.add((mailHandedOverVO.getDoe()!= null)?mailHandedOverVO.getDoe():ReportConstants.EMPTY_STRING);
				row.add((mailHandedOverVO.getWeight()!= null)?mailHandedOverVO.getWeight().getRoundedSystemValue():ReportConstants.EMPTY_STRING);
							 
				reportData.add(row);
			}
		
		}
		log.log(Log.FINE, "Exiting Attribute ---->>", reportData);
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
		log.log(Log.FINE, "parameters..", parameters);
		Object dataParameters = ((ArrayList<Object>)parameters).get(0);
		
		MailHandedOverFilterVO mailHandedOverFilterVO = (MailHandedOverFilterVO)dataParameters;
		reportParameters.add((mailHandedOverFilterVO.getFromDate()!= null)?mailHandedOverFilterVO.getFromDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
		reportParameters.add((mailHandedOverFilterVO.getToDate()!= null)?mailHandedOverFilterVO.getToDate().toDisplayFormat("dd-MMM-yyyy"):ReportConstants.EMPTY_STRING);
		reportParameters.add((mailHandedOverFilterVO.getCarrierCode()!= null)?mailHandedOverFilterVO.getCarrierCode():ReportConstants.EMPTY_STRING);
		mailHandedOverFilterVO.setFlightCarrierCode((mailHandedOverFilterVO.getFlightCarrierCode()!=null)?mailHandedOverFilterVO.getFlightCarrierCode():ReportConstants.EMPTY_STRING);
		mailHandedOverFilterVO.setFlightNumber((mailHandedOverFilterVO.getFlightNumber()!=null)?mailHandedOverFilterVO.getFlightNumber():ReportConstants.EMPTY_STRING);
		String str =new StringBuilder().append(mailHandedOverFilterVO.getFlightCarrierCode()).append(" ").append(mailHandedOverFilterVO.getFlightNumber()).toString();
		reportParameters.add((str!= null)?str:ReportConstants.EMPTY_STRING);
		
		return reportParameters;
	}

}
