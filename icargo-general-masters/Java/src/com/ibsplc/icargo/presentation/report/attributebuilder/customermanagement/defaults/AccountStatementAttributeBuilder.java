/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.customermanagement.defaults.AccountStatementAttributeBuilder.java
 *
 *	Created by	:	A-8169
 *	Created on	:	Nov 15, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.customermanagement.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

import com.ibm.icu.text.DecimalFormat;
import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.report.attributebuilder.customermanagement.defaults.AccountStatementAttributeBuilder.java
 *	Version	:	Name						 :	Date					:	Updation
 * ------------------------------------------------------------------------------------------------------
 *		0.1		:	A-8169					 :	Nov 15, 2018	:	Draft
 *		0.2		:	for IASCB-118899	 : 	Aug 18, 2021	: 	Modified
 */
public class AccountStatementAttributeBuilder extends AttributeBuilderAdapter {

	private static final String UNPAID = "UNPAID";
	private static final String SHORTPAY = "SHORT PAY";
	private static final String OVERPAID = "OVERPAID";
	private static final String ITEM = "Item";
	private static final String ITEMS = "Items";

	/**
	 * Method to populate the report column names. The column names corresponds to
	 * the column names of the view used while laying out the report. The order of
	 * the column names should match the order in which the database fields are laid
	 * out in the report.
	 */
	@Override
	public Vector<String> getReportColumns() {
		List<String> columns = new ArrayList<>();
		columns.add("INVNUM");
		columns.add("AGEDAYS");
		columns.add("AWB");
		columns.add("ORG");
		columns.add("DEST");
		columns.add("ACTBILAMT");
		columns.add("STATUS");
		columns.add("DUEAMT");
		columns.add("RMK");
		columns.add("AWBEXEDAT");

		return new Vector<>(columns);
	}

	/**
	 * Method to populate the report data. The order of the data should match the
	 * order in which the column names are laid out in the report column vector.
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> tableData = new Vector<>();
		Collection<CustomerInvoiceAWBDetailsVO> awbVOs = (Collection<CustomerInvoiceAWBDetailsVO>) data;

		awbVOs.forEach(awbVO -> {
			Vector<Object> row = new Vector<>();
			row.add(setString(awbVO.getInvoiceNumber()));
			row.add(awbVO.getAgeDays());
			row.add(setString(awbVO.getAwbNumber()));
			row.add(setString(awbVO.getOrigin()));
			row.add(setString(awbVO.getDestination()));
			row.add(awbVO.getActualBilledAmt());
			row.add(setString(awbVO.getStatus()));
			row.add(awbVO.getAmountDue());
			row.add(setString(awbVO.getRemarks()));
			row.add(setString(awbVO.getAwbExecutionDate()));

			tableData.add(row);
		});

		return tableData;
	}

	/**
	 * Method to populate the report parameters. The report parameters corresponds
	 * to the parameter fields in the report. The order of the parameters should
	 * match the order in which the parameter fields are laid out in the report.
	 */
	@Override
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {
		List<Object> parameter = new ArrayList<>();
		List<CustomerInvoiceAWBDetailsVO> awbDetailsVOs = (List<CustomerInvoiceAWBDetailsVO>) ((ArrayList<Object>) parameters).get(0);
		CustomerInvoiceAWBDetailsVO invoiceVO = awbDetailsVOs.get(0); 

		parameter.add(invoiceVO.getCustomerCode());
		parameter.add(setString(invoiceVO.getCustomerName()));
		parameter.add(setString(invoiceVO.getCustomerAccountNumber()));
		parameter.add(setString(invoiceVO.getName()));
		parameter.add(setString(invoiceVO.getEmail()));
		parameter.add(setString(invoiceVO.getTelephone()));
		parameter.add(setString(invoiceVO.getCustomerAddress()));
		parameter.add(setString(invoiceVO.getStreet()));
		parameter.add(setString(invoiceVO.getLocation()));
		parameter.add(setString(invoiceVO.getCity()));
		parameter.add(setString(invoiceVO.getState()));
		parameter.add(setString(invoiceVO.getPrimaryEmail()));

		int overpaidCount = 0;
		int shortpaidCount = 0;
		int unpaidCount = 0;
		double overpaidAmt = 0.0;
		double shortpaidAmt = 0.0;
		double unpaidAmt = 0.0;
		String overpaid = "Overpaid";
		String shortpaid = "Short Pay";
		String unpaid = "Unpaid";

		List<CustomerInvoiceAWBDetailsVO> awbVOs = (List<CustomerInvoiceAWBDetailsVO>) awbDetailsVOs;
		for (CustomerInvoiceAWBDetailsVO awbVO : awbVOs) {
			if (OVERPAID.equals(awbVO.getStatus())) {
				overpaidCount++;
				overpaidAmt = overpaidAmt + awbVO.getAmountDue();
			} else if (SHORTPAY.equals(awbVO.getStatus())) {
				shortpaidCount++;
				shortpaidAmt = shortpaidAmt + awbVO.getAmountDue();
			} else if (UNPAID.equals(awbVO.getStatus())) {
				unpaidCount++;
				unpaidAmt = unpaidAmt + awbVO.getAmountDue();
			}
		}

		DecimalFormat numberFormat = new DecimalFormat("#.00");
		int totalCount = overpaidCount + shortpaidCount + unpaidCount;
		double totalAmt = overpaidAmt + shortpaidAmt + unpaidAmt;

		parameter.add(String.valueOf(overpaidCount));
		if (overpaidCount > 1) {
			parameter.add(ITEMS);
		} else {
			parameter.add(ITEM);
		}
		if (overpaidCount > 0) {
			parameter.add(overpaid);
		} else {
			parameter.add(ReportConstants.EMPTY_STRING);
		}
		parameter.add(numberFormat.format(overpaidAmt));

		parameter.add(String.valueOf(shortpaidCount));
		if (shortpaidCount > 1) {
			parameter.add(ITEMS);
		} else {
			parameter.add(ITEM);
		}
		if (shortpaidCount > 0) {
			parameter.add(shortpaid);
		} else {
			parameter.add(ReportConstants.EMPTY_STRING);
		}
		parameter.add(numberFormat.format(shortpaidAmt));

		parameter.add(String.valueOf(unpaidCount));
		if (unpaidCount > 1) {
			parameter.add(ITEMS);
		} else {
			parameter.add(ITEM);
		}
		if (unpaidCount > 0) {
			parameter.add(unpaid);
		} else {
			parameter.add(ReportConstants.EMPTY_STRING);
		}
		parameter.add(numberFormat.format(unpaidAmt));

		parameter.add(String.valueOf(totalCount));
		if (totalCount > 1) {
			parameter.add(ITEMS);
		} else {
			parameter.add(ITEM);
		}
		parameter.add(numberFormat.format(totalAmt));

		parameter.add(invoiceVO.getCurrentDate());
		parameter.add(invoiceVO.getCurrency());

		return new Vector<>(parameter);
	}

	private String setString(String input) {
		String output = null;
		if (Objects.nonNull(input)) {
			output = input;
		} else {
			output = ReportConstants.EMPTY_STRING;
		}
		return output;
	}

}
