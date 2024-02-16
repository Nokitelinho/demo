/*
 * MailStatusAttributeBuilder.java Created on FEB 29, 2008
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

import com.ibsplc.icargo.business.mail.operations.vo.MailStatusFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailStatusVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-3353
 *
 */
public class MailStatusAttributeBuilder extends AttributeBuilderAdapter{
	private static final String CLASS_NAME = "MailStatusAttributeBuilder";
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
		columns.add("POL");
		columns.add("POU");
		columns.add("FLTNUM");
		columns.add("FLTDAT");
		columns.add("DSN");
		columns.add("MALIDR");
		columns.add("WGT");
		columns.add("CDTAVL");
		columns.add("INFLTNUM");
		columns.add("INFLTDAT");
		
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
		
		
		List<MailStatusVO> mailStatusVOS = (ArrayList<MailStatusVO>)data;		
		Vector<Vector> reportData = new Vector<Vector>();
		log.log(Log.FINE, "Entering Attribute ---->>", data);
		if (data != null && data.size() > 0){
		
			for(MailStatusVO mailStatusVO:mailStatusVOS){
				Vector<Object> row = new Vector<Object>();
				row.add((mailStatusVO.getPol()!= null)
						?mailStatusVO.getPol()
						:ReportConstants.EMPTY_STRING);
				if("-1".equals(mailStatusVO.getFlightNumber())){
					row.add(ReportConstants.EMPTY_STRING);
				}
				else{
					row.add((mailStatusVO.getPou()!= null)
							?mailStatusVO.getPou()
							:ReportConstants.EMPTY_STRING);
				}
				mailStatusVO.setFlightCarrierCode((mailStatusVO.getFlightCarrierCode()!=null)?mailStatusVO.getFlightCarrierCode():ReportConstants.EMPTY_STRING);
				mailStatusVO.setFlightNumber((mailStatusVO.getFlightNumber()!=null)?mailStatusVO.getFlightNumber():ReportConstants.EMPTY_STRING);
				if("-1".equals(mailStatusVO.getFlightNumber())){
					mailStatusVO.setFlightNumber(ReportConstants.EMPTY_STRING);
				}
				String str = new StringBuilder().append(mailStatusVO.getFlightCarrierCode())
			       .append(" ").append(mailStatusVO.getFlightNumber()).toString();
				
				row.add((str!= null)?str:ReportConstants.EMPTY_STRING);
				row.add((mailStatusVO.getFlightDate()!= null)?mailStatusVO.getFlightDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
				row.add((mailStatusVO.getDsn()!= null)?mailStatusVO.getDsn():ReportConstants.EMPTY_STRING);
				row.add((mailStatusVO.getMailBagId()!= null)?mailStatusVO.getMailBagId():ReportConstants.EMPTY_STRING);
				//row.add((mailStatusVO.getWeight() != null) ? Double.valueOf(mailStatusVO.getWeight()) : ReportConstants.EMPTY_STRING);
				row.add((mailStatusVO.getWeight() != null) ? Double.valueOf(mailStatusVO.getWeight().getDisplayValue()) : ReportConstants.EMPTY_STRING);//added by A-7371
			   
				row.add((mailStatusVO.getCarditAvailable()!= null)?mailStatusVO.getCarditAvailable():ReportConstants.EMPTY_STRING);
				mailStatusVO.setIncommingFlightCarrierCode((mailStatusVO.getIncommingFlightCarrierCode()!=null)?mailStatusVO.getIncommingFlightCarrierCode():ReportConstants.EMPTY_STRING);
				mailStatusVO.setIncommingFlightNumber((mailStatusVO.getIncommingFlightNumber()!=null)?mailStatusVO.getIncommingFlightNumber():ReportConstants.EMPTY_STRING);
				String strIn =new StringBuilder().append(mailStatusVO.getIncommingFlightCarrierCode()).append(" ")
				.append(mailStatusVO.getIncommingFlightNumber()).toString();
				row.add((strIn!= null)?strIn:ReportConstants.EMPTY_STRING);
				row.add((mailStatusVO.getIncommingFlightDate()!= null)?mailStatusVO.getIncommingFlightDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
				
				
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
		
		MailStatusFilterVO mailStatusFilterVO = (MailStatusFilterVO)dataParameters;
		reportParameters.add((mailStatusFilterVO.getFromDate()!= null)?mailStatusFilterVO.getFromDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
		reportParameters.add((mailStatusFilterVO.getToDate()!= null)?mailStatusFilterVO.getToDate().toDisplayFormat("dd-MMM-yyyy").toUpperCase():ReportConstants.EMPTY_STRING);
		reportParameters.add((mailStatusFilterVO.getCarrierCode()!= null)?mailStatusFilterVO.getCarrierCode():ReportConstants.EMPTY_STRING);
		mailStatusFilterVO.setFlightCarrierCode((mailStatusFilterVO.getFlightCarrierCode()!=null)?mailStatusFilterVO.getFlightCarrierCode():ReportConstants.EMPTY_STRING);
		mailStatusFilterVO.setFlightNumber((mailStatusFilterVO.getFlightNumber()!=null)?mailStatusFilterVO.getFlightNumber():ReportConstants.EMPTY_STRING);
		String str =new StringBuilder().append(mailStatusFilterVO.getFlightCarrierCode()).append(" ").append(mailStatusFilterVO.getFlightNumber()).toString();
		reportParameters.add((str!= null)?str:ReportConstants.EMPTY_STRING);
		reportParameters.add((mailStatusFilterVO.getPol()!= null)?mailStatusFilterVO.getPol():ReportConstants.EMPTY_STRING);
		reportParameters.add((mailStatusFilterVO.getPou()!= null)?mailStatusFilterVO.getPou():ReportConstants.EMPTY_STRING);
		reportParameters.add((mailStatusFilterVO.getPacode()!= null)?mailStatusFilterVO.getPacode():ReportConstants.EMPTY_STRING);
		reportParameters.add((mailStatusFilterVO.getCurrentStatus()!= null)?mailStatusFilterVO.getCurrentStatus():ReportConstants.EMPTY_STRING);
		return reportParameters;
	}
	
	
}

