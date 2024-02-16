/*
 * PrintCommand.java Created on Jan 9, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.reserveawb;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReserveAWBForm;

/**
 * @author A-1619
 *
 */
public class PrintCommand extends AbstractPrintCommand {
	private static final String REPORT_ID = "RPRSTK001";

	private static final String ERROR_FWD = "normal-report-error-jsp";

	private Log log = LogFactory.getLogger("ReserveAWB PrintCommand");
	
	private static final String ACTION_STK = "generateReserveAWBReport";

	/**
	 * execute method 
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ReserveAWBForm form = (ReserveAWBForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();

		ReserveAWBVO reserveAWBVO = new ReserveAWBVO();
		reserveAWBVO = getReserveAWBVOToPrint(form, logonAttributes);
		log.log(Log.FINE, "\n\n\n----------AFTER getReserveAWBVOToPrint---");
		getReportSpec().addFilterValue(reserveAWBVO);
	/*	ReportMetaData parameterMetaData = new ReportMetaData();
		parameterMetaData.setFieldNames(new String[] { "airlineCode",
				"customerCode", "", "lastUpdateUser", "", "expiryDate",
				"documentSubType" });
		getReportSpec().addParameterMetaData(parameterMetaData);
		log.log(Log.FINE, "\n\n\n----------AFTER addParameterMetaData---");

		ReportMetaData reportMetaData = new ReportMetaData();
		reportMetaData.setColumnNames(new String[] { "CSHTYP", "RCPNUM" });
		getReportSpec().setReportMetaData(reportMetaData);
		log.log(Log.FINE, "\n\n\n----------AFTER setReportMetaData---");*/
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode("stockcontrol");
		getReportSpec().setSubProductCode("defaults");
	//	getReportSpec().addExtraInfo(reserveAWBVO);
		 getReportSpec().setAction(ACTION_STK);
		getReportSpec().setPreview(false);
		getReportSpec().setResourceBundle(form.getBundle());
		log.log(Log.FINE, "\n\n\n----------AFTER setPreview---");
		generateReport();

		invocationContext.target = getTargetPage();

	}

	private ReserveAWBVO getReserveAWBVOToPrint(ReserveAWBForm form,
			LogonAttributes logonAttributes) {
		ReserveAWBVO vo = new ReserveAWBVO();

		if (form.getAirline() != null && form.getAirline().trim().length() > 0) {
			vo.setAirlineCode(form.getAirline().trim().toUpperCase());
		}
		if (form.getAwbprefixforprint() != null
				&& form.getAwbprefixforprint().trim().length() > 0) {
			vo.setShipmentPrefix(form.getAwbprefixforprint().trim());
		}
		if (form.getAwbType() != null && form.getAwbType().trim().length() > 0) {
			vo.setDocumentSubType(form.getAwbType().trim());
		}
		if (form.getCustCode() != null
				&& form.getCustCode().trim().length() > 0) {
			vo.setCustomerCode(form.getCustCode().trim());
		}
		if (form.getExpiryDate() != null
				&& form.getExpiryDate().trim().length() > 0) {
			LocalDate ld = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, false);
			vo.setExpiryDate(ld.setDate(form.getExpiryDate().trim()));
		}
		vo.setLastUpdateUser(logonAttributes.getUserId());
		if (form.getDocnumForPrint() != null
				&& form.getDocnumForPrint().trim().length() > 0) {
			log.log(Log.FINE, "\n\n\n----------DOCCOL3---", form.getDocnumForPrint());
			String[] docs = form.getDocnumForPrint().trim().split(",");
			log.log(Log.FINE, "\n\n\n----------DOCCOL4---", docs);
			Collection<String> docCol = new ArrayList<String>();
			for (String str : docs) {
				docCol.add(str);
				log.log(Log.FINE, "\n\n\n----------DOCCOL---", str);
				log.log(Log.FINE, "\n\n\n----------DOCCOLend---", docCol);
			}
			vo.setDocumentNumbers(docCol);
		}
		return vo;
	}

}
