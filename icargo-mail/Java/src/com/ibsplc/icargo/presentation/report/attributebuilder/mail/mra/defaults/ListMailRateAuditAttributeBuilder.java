/*
 * ListMailRateAuditAttributeBuilder.java Created on June 9, 2009
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3434
 *
 */

public class ListMailRateAuditAttributeBuilder extends AttributeBuilderAdapter{
	private Log log=LogFactory.getLogger("ListMailRateAuditAttributeBuilder Entry");
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

		reportColumns.add("DSNSTATUS");
		reportColumns.add("DSN");
		reportColumns.add("DSNDATE");
		reportColumns.add("ORIGIN");
		reportColumns.add("DESTINATION");
		reportColumns.add("FLTNO");
		reportColumns.add("FLTDATE");
		reportColumns.add("PCS");
		reportColumns.add("GRSWGT");
		reportColumns.add("RATE");
		reportColumns.add("CURRENCY");
		reportColumns.add("PRESENTWGTCHG");
		reportColumns.add("AUDITEDWGTCHG");
		reportColumns.add("DISCREPANCY");
		reportColumns.add("GPACODE");
		reportColumns.add("CATEGORY");
		reportColumns.add("CLASS");
		reportColumns.add("SUBCLASS");
		reportColumns.add("BILLTO");
		log.log(Log.FINE, "reportColumns is --------->", reportColumns);
		return reportColumns;
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
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		Vector<Vector> rateAuditVOVector = new Vector<Vector>();
		Vector<Object> row = null;
		Object extraInfor = ((ArrayList<OneTimeVO>) extraInfo).get(0);
		Collection<OneTimeVO> dsnStatuses = (Collection<OneTimeVO>) extraInfor;

		List<RateAuditVO> rateAuditVOs = new ArrayList<RateAuditVO>();
		rateAuditVOs = new ArrayList<RateAuditVO>(data);

		for (RateAuditVO rateAuditVO : rateAuditVOs) {

			row = new Vector<Object>();
			if (rateAuditVO.getDsnStatus() != null) {
				for (OneTimeVO oneTimeVO : dsnStatuses) {
					if (rateAuditVO.getDsnStatus().equals(oneTimeVO.getFieldValue())) {
						rateAuditVO.setDsnStatus(oneTimeVO.getFieldDescription());
					}
				}
				row.add(rateAuditVO.getDsnStatus());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (rateAuditVO.getDsn()!= null) {
				row.add(rateAuditVO.getDsn());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getDsnDate()!= null) {
				row.add(rateAuditVO.getDsnDate().toDisplayDateOnlyFormat());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getOrigin()!= null) {
				row.add(rateAuditVO.getOrigin().substring(2,5));
			} else {
				row.add(ReportConstants.EMPTY_STRING);

			}if (rateAuditVO.getDestination()!= null) {
				row.add(rateAuditVO.getDestination().substring(2,5));
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getFlightNumber()!= null) {
				row.add(rateAuditVO.getFlightNumber());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getFlightDate()!= null) {
				row.add(rateAuditVO.getFlightDate().toDisplayDateOnlyFormat());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getPcs()!= null) {
				row.add(rateAuditVO.getPcs());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getGrossWt()!= null) {
				row.add(rateAuditVO.getGrossWt());
			} else {
				row.add(0);
			}

			if (rateAuditVO.getRate()!= null) {
				row.add(rateAuditVO.getRate());
			} else {
				row.add(0);
			}

			if (rateAuditVO.getCurrency()!= null) {
				row.add(rateAuditVO.getCurrency());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getPresentWtCharge()!= null) {
				row.add(rateAuditVO.getPresentWtCharge());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getAuditedWtCharge()!= null) {
				row.add(rateAuditVO.getAuditedWtCharge());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getDiscrepancy()!= null) {
				row.add(rateAuditVO.getDiscrepancy());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getGpaCode()!= null) {
				row.add(rateAuditVO.getGpaCode());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getCategory()!= null) {
				row.add(rateAuditVO.getCategory());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			if (rateAuditVO.getClass()!= null) {
				row.add(rateAuditVO.getMalClass());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (rateAuditVO.getSubClass()!= null) {
				row.add(rateAuditVO.getSubClass());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}
			if (rateAuditVO.getBillTo()!= null) {
				row.add(rateAuditVO.getBillTo());
			} else {
				row.add(ReportConstants.EMPTY_STRING);
			}

			rateAuditVOVector.add(row);


		}

		log.log(Log.INFO, "rateAuditVOVector...", rateAuditVOVector);
		return rateAuditVOVector;
	}
}
