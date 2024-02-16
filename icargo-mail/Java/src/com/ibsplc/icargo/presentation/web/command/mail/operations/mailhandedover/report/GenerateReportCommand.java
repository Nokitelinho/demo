/*
 * GenerateReportCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailhandedover.report;

import com.ibsplc.icargo.business.mail.operations.vo.MailHandedOverFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailHandedOverReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class GenerateReportCommand  extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String REPORT_ID = "RPTOPS029";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailHandedOverReport";
	private static final String BUNDLE = "mailHandedOverReportResources";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("GenerateReportCommand","execute");
    	
		MailHandedOverReportForm mailHandedOverReportForm =(MailHandedOverReportForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();		

		MailHandedOverFilterVO	mailHandedOverFilterVO =  new MailHandedOverFilterVO();
		mailHandedOverFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailHandedOverFilterVO.setScanPort(logonAttributes.getAirportCode());
		mailHandedOverFilterVO.setFlightNumber(mailHandedOverReportForm.getFlightNumber());
		mailHandedOverFilterVO.setCarrierCode(mailHandedOverReportForm.getCarrierCode());
		mailHandedOverFilterVO.setFlightCarrierCode(mailHandedOverReportForm.getFlightCarrierCode());
		
		if(mailHandedOverReportForm.getCarrierId()!=null && mailHandedOverReportForm.getCarrierId().length() > 0){
			mailHandedOverFilterVO.setCarrierId(Integer.parseInt(mailHandedOverReportForm.getCarrierId()));
		}
		
		if(mailHandedOverReportForm.getFlightCarrierId()!=null && mailHandedOverReportForm.getFlightCarrierId().length() > 0){
			mailHandedOverFilterVO.setFlightCarrierId(Integer.parseInt(mailHandedOverReportForm.getFlightCarrierId()));
		}
		
		if(mailHandedOverReportForm.getFromDate()!=null && mailHandedOverReportForm.getFromDate().length() > 0){
			mailHandedOverFilterVO.setFromDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailHandedOverReportForm.getFromDate()));
		}
		
		if(mailHandedOverReportForm.getToDate()!=null && mailHandedOverReportForm.getToDate().length() > 0){
			mailHandedOverFilterVO.setToDate(
							new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailHandedOverReportForm.getToDate()));
		}
		
		
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(mailHandedOverFilterVO);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);
		
		generateReport();
		invocationContext.target = getTargetPage();
	}

}
