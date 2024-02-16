/*
 * AcceptedNotUpliftedAttributeBuilder.java Created on FEB 29, 2010
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
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
public class AcceptedNotUpliftedAttributeBuilder extends
		AttributeBuilderAdapter {
	private static final String CLASS_NAME = "AcceptedNotUpliftedAttributeBuilder";

	private static final String BLANK = " ";

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

		columns.add("CONNUM");
		columns.add("FLTNUM");
		columns.add("POL");
		columns.add("POU");
		columns.add("FLTROU");
		columns.add("STD");
		columns.add("LEGSTA");
		columns.add("OOE");
		columns.add("DOE");
		columns.add("MALCTGCOD");
		columns.add("MALSUBCLS");
		columns.add("YER");
		columns.add("DSN");
		columns.add("RSN");
		columns.add("HNI");
		columns.add("RI");
		columns.add("WGT");

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

		List<MailStatusVO> mailStatusVOS = (ArrayList<MailStatusVO>) data;
		Vector<Vector> reportData = new Vector<Vector>();
		log.log(Log.FINE, "Entering Attribute --->>->>" + data);		
		if (data != null && data.size() > 0) {

			for (MailStatusVO mailStatusVO : mailStatusVOS) {
				Vector<Object> row = new Vector<Object>();

				if (mailStatusVO.getContainerNumber() != null
						&& mailStatusVO.getContainerNumber().trim().length() > 0) {
					row.add(mailStatusVO.getContainerNumber());
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}

				if (mailStatusVO.getFlightNumber() != null
						&& mailStatusVO.getFlightNumber().trim().length() > 0) {
					if("-1".equals(mailStatusVO.getFlightNumber())){
						row.add(mailStatusVO.getFlightNumber());
					}else{
						row.add(new StringBuilder().append(mailStatusVO.getFlightCarrierCode()).
								 append(mailStatusVO.getFlightNumber()).toString());
					}
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}
				if (mailStatusVO.getPol()!= null
						&& mailStatusVO.getPol().trim().length() > 0) {
					row.add(mailStatusVO.getPol());
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}
				if (mailStatusVO.getPou() != null
						&& mailStatusVO.getPou().trim().length() > 0) {
					row.add(mailStatusVO.getPou());
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}
				if (mailStatusVO.getFlightRoute() != null
						&& mailStatusVO.getFlightRoute().trim().length() > 0) {
					row.add(mailStatusVO.getFlightRoute());
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}

				if (mailStatusVO.getScheduledDepartureTime() != null) {
					row.add(mailStatusVO.getScheduledDepartureTime().toDisplayDateOnlyFormat());
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}

				if (mailStatusVO.getLegStatus() != null
						&& mailStatusVO.getLegStatus().trim().length() > 0) {
					row.add(mailStatusVO.getLegStatus());
				} else {
					row.add(ReportConstants.EMPTY_STRING);
				}

				String mailBagIdentifier = mailStatusVO.getMailBagId();
				if (mailBagIdentifier != null
						&& mailBagIdentifier.trim().length() > 0) {
					if(mailBagIdentifier.length()>=6)
					{row.add(mailBagIdentifier.substring(0, 6));
					}
					if(mailBagIdentifier.length()>=12){
					row.add(mailBagIdentifier.substring(6, 12));
					}
					if(mailBagIdentifier.length()>=13){
					row.add(mailBagIdentifier.substring(12, 13));
					}
					if(mailBagIdentifier.length()>=15){
					row.add(mailBagIdentifier.substring(13, 15));
					}
					if(mailBagIdentifier.length()>=16){
					row.add(mailBagIdentifier.substring(15, 16));
					}
					if(mailBagIdentifier.length()>=20){
					row.add(mailBagIdentifier.substring(16, 20));
					}
					if(mailBagIdentifier.length()>=23){
					row.add(mailBagIdentifier.substring(20, 23));
					}
					if(mailBagIdentifier.length()>=24){
					row.add(mailBagIdentifier.substring(23, 24));
					}
					if(mailBagIdentifier.length()>=25){
					row.add(mailBagIdentifier.substring(24, 25));
					}
					if(mailBagIdentifier.length()>=29){
					row.add(mailBagIdentifier.substring(25, 29));
					}
				} else {
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);

				}
				reportData.add(row);
			}

		}
		log.log(Log.FINE, "Exiting Attribute ---->>" + reportData);
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
		log.log(Log.FINE, "parameters.." + parameters);

		Object dataParameters = ((ArrayList<Object>) parameters).get(0);

		MailStatusFilterVO mailStatusFilterVO = (MailStatusFilterVO) dataParameters;

		// mailStatusFilterVO.setFlightNumber((mailStatusFilterVO.getFlightNumber()!=null)?mailStatusFilterVO.getFlightNumber():ReportConstants.EMPTY_STRING);
		/*
		 * String str =new
		 * StringBuilder().append(mailStatusFilterVO.getFlightCarrierCode()).append("
		 * ").append(mailStatusFilterVO.getFlightNumber()).toString();
		 * reportParameters.add((str!= null)?str:ReportConstants.EMPTY_STRING);
		 */
		reportParameters
				.add((mailStatusFilterVO.getPol() != null) ? mailStatusFilterVO
						.getPol() : ReportConstants.EMPTY_STRING);
		reportParameters
				.add((mailStatusFilterVO.getPou() != null) ? mailStatusFilterVO
						.getPou() : ReportConstants.EMPTY_STRING);
		return reportParameters;
	}

}
