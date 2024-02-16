/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ConsignmentReportAttributeBuilder.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Sep 26, 2016
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
//import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations.ConsignmentReportAttributeBuilder.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Sep 26, 2016	:	Draft
 */
public class ConsignmentReportAttributeBuilder extends
AttributeBuilderAdapter{
	private static final String CLASS_NAME = "ConsignmentReportAttributeBuilder";
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
		columns.add("COUNT");
		columns.add("OPRORG");
		columns.add("OPRDST");
		columns.add("ORGEXGOFCDES");
		columns.add("DSTEXGOFCDES");
		columns.add("TRPMNS");
		columns.add("CSGPRI");
		columns.add("FLTDTL");
		columns.add("FLTRUT");
		columns.add("FSTFLTDEPDAT");
		columns.add("ARLCOD");
		columns.add("OOE");
		columns.add("DOE");
		columns.add("MALCTG");
		columns.add("MALSUBCLS");
		columns.add("YEAR");
		columns.add("DSN");
		columns.add("RSN");
		columns.add("HNI");
		columns.add("RI");
		columns.add("WGT");
		columns.add("CSGDOCNUM");
		columns.add("CSGDOCDAT");
		columns.add("ARPCOD");
		columns.add("PCS");
		columns.add("WGHTHG");
		columns.add("TIME");
		columns.add("ORGARP");
		columns.add("DSTARP");
		columns.add("SUBTYP");
		columns.add("ULDNUM");
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
				Vector<Object> row = new Vector<Object>();
				count++;
				row.add(String.valueOf(count));

				row.add((consignDocumentVO.getOperatorOrigin() != null) ? consignDocumentVO
						.getOperatorOrigin() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getOperatorDestination() != null) ? consignDocumentVO
						.getOperatorDestination()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getOoeDescription() != null) ? consignDocumentVO
						.getOoeDescription() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getDoeDescription() != null) ? consignDocumentVO
						.getDoeDescription() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getTransportationMeans() != null) ? consignDocumentVO
						.getTransportationMeans()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getConsignmentPriority() != null) ? consignDocumentVO
						.getConsignmentPriority()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getFlightDetails() != null) ? consignDocumentVO
						.getFlightDetails() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getFlightRoute() != null) ? consignDocumentVO
						.getFlightRoute() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getFirstFlightDepartureDate() != null) ? consignDocumentVO
						.getFirstFlightDepartureDate()
						.toDisplayDateOnlyFormat()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getAirlineCode() != null) ? consignDocumentVO
						.getAirlineCode() : ReportConstants.EMPTY_STRING);

				row.add((mailVo.getOriginExchangeOffice() != null) ? mailVo
						.getOriginExchangeOffice()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getDestinationExchangeOffice() != null) ? mailVo
						.getDestinationExchangeOffice()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getMailCategoryCode() != null) ? mailVo
						.getMailCategoryCode() : ReportConstants.EMPTY_STRING);
				row.add((mailVo.getMailSubclass() != null) ? mailVo
						.getMailSubclass() : ReportConstants.EMPTY_STRING);
				row.add((String.valueOf(mailVo.getYear()) != null) ? String
						.valueOf(mailVo.getYear())
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getDsn() != null) ? mailVo.getDsn()
						: ReportConstants.EMPTY_STRING);

				row.add((mailVo.getReceptacleSerialNumber() != null) ? mailVo
						.getReceptacleSerialNumber()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getHighestNumberedReceptacle() != null) ? mailVo
						.getHighestNumberedReceptacle()
						: ReportConstants.EMPTY_STRING);
				row.add((mailVo.getRegisteredOrInsuredIndicator() != null) ? mailVo
						.getRegisteredOrInsuredIndicator()
						: ReportConstants.EMPTY_STRING);
				/*row.add((mailVo.getStatedWeight() > 0) ? String
						.valueOf(TextFormatter.formatDouble(
								mailVo.getStatedWeight(), 2))
						: ReportConstants.EMPTY_STRING);*/
				row.add((mailVo.getStatedWeight().getRoundedDisplayValue() > 0) ? String
						.valueOf(
								mailVo.getStatedWeight().getRoundedDisplayValue())
						: ReportConstants.EMPTY_STRING);//modified by A-8353 for ICRD-260603
				row.add((consignDocumentVO.getConsignmentNumber() != null) ? consignDocumentVO
						.getConsignmentNumber() : ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getConsignmentDate() != null) ? consignDocumentVO
						.getConsignmentDate().toDisplayDateOnlyFormat()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getAirportCode() != null) ? consignDocumentVO
						.getAirportCode() : ReportConstants.EMPTY_STRING);

				row.add(String.valueOf(mailVo.getStatedBags()));
				row.add((mailVo.getStrWeight().getRoundedDisplayValue() > 0) ? String.valueOf(mailVo.getStrWeight().getRoundedDisplayValue()) : ReportConstants.EMPTY_STRING);//modified by A-8353 for ICRD-260603
				row.add((consignDocumentVO.getFirstFlightDepartureDate() != null) ? consignDocumentVO
						.getFirstFlightDepartureDate().toDisplayTimeOnlyFormat(
								true) : ReportConstants.EMPTY_STRING);

				if (consignDocumentVO.getFlightRoute() != null
						&& consignDocumentVO.getFlightRoute().contains("-")) {
					String[] routes = consignDocumentVO.getFlightRoute().split(
							"-");

					row.add((routes != null && routes.length > 0) ? routes[0]
							: ReportConstants.EMPTY_STRING);
					row.add((routes != null && routes.length > 1) ? routes[1]
							: ReportConstants.EMPTY_STRING);

				}else{
					row.add(ReportConstants.EMPTY_STRING);
					row.add(ReportConstants.EMPTY_STRING);
				}
				row.add((consignDocumentVO.getSubType() != null) ? consignDocumentVO
						.getSubType() : ReportConstants.EMPTY_STRING);          

				row.add((mailVo.getUldNumber() != null) ? mailVo.getUldNumber()
						: ReportConstants.EMPTY_STRING);
				row.add((consignDocumentVO.getRemarks()!= null) ? consignDocumentVO.getRemarks()
						: ReportConstants.EMPTY_STRING);
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
			ConsignmentDocumentVO consignmentDocumentVO = (ConsignmentDocumentVO) dataParameters;
			// Set if the report Type is flight Type or Carrier Type
			reportParameters.add(consignmentDocumentVO.getReportType());
		}
		return reportParameters;
	}
}
