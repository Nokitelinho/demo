/*
 * PrintInvoiceCommand.java Created on Mar 17,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66.report;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class PrintInvoiceCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_AIRLINEBILLING");
	private static final String REPORT_ID = "12MRA001";
	private static final String RESOURCE_BUNDLE_KEY = "capturecn66";
	private static final String ACTION = "generateCN66InvoiceReport";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("MRA_AIRLINEBILLING","PrintInvoiceCommand entered");
		CaptureCN66Form form=(CaptureCN66Form)invocationContext.screenModel;
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();	
		log
				.log(Log.INFO, "airlineCode>>>", form.getAirlineCode().toUpperCase());
		log.log(Log.INFO, "BillingType()>>>", form.getBillingType().toUpperCase());
		AirlineCN66DetailsFilterVO filterVo = new AirlineCN66DetailsFilterVO();
		filterVo.setCompanyCode(companyCode);
		filterVo.setAirlineCode(form.getAirlineCode().toUpperCase());
		filterVo.setInvoiceRefNumber(form.getInvoiceRefNo().toUpperCase());
		filterVo.setClearancePeriod(form.getClearancePeriod());
		filterVo.setInterlineBillingType(form.getBillingType());
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVo);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		generateReport();	
		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.exiting("MRA_AIRLINEBILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
		
	}
	

}
