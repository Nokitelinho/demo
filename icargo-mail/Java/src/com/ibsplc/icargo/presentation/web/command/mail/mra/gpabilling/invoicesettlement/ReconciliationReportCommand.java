/*
 * ReconciliationReportCommand.java Created on Mar 26, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ReconciliationReportCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");
	
	private static final String ACTION = "printreconciliationreport";
	
	private static final String REPORT_ID="RPTMRA041";
	
	private static final String RESOURCE_BUNDLE_KEY="invoicesettlement";

	private static final String PRINT_UNSUCCESSFUL="normal-report-error-jsp";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("MAILTRACKING_GPABILLING","Print Command entered");
		InvoiceSettlementForm form = (InvoiceSettlementForm)invocationContext.screenModel;
		InvoiceSettlementFilterVO filterVO=new InvoiceSettlementFilterVO();
		
		//Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
		filterVO.setCompanyCode(companyCode);
	
		String gpaCode=form.getGpaCodeFilter();
    	String invNumber=form.getInvRefNumberFilter();
    	String fromDate=form.getFromDate();
    	String toDate=form.getToDate();
    	if(gpaCode!=null && gpaCode.trim().length()>0){
    		filterVO.setGpaCode(gpaCode.trim().toUpperCase());
    	}
    	if(invNumber!=null && invNumber.trim().length()>0){
    		filterVO.setInvoiceNumber(invNumber.trim().toUpperCase());
    	}
    	if(fromDate!=null && fromDate.trim().length()>0){
    		filterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(fromDate));
    	}
    	if(toDate!=null && toDate.trim().length()>0){
    		filterVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(toDate));
    	}
		//for pending settlements only
		filterVO.setSettlementStatus("P");
		
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();

	}
}