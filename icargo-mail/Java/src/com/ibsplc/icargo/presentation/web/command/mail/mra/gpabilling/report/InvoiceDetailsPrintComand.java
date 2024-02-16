/*
 * InvoiceDetailsPrintComand.java Created on Mar 02,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListCN51CN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListCN51CN66Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class InvoiceDetailsPrintComand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	private static final String MODULE = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.listcn51cn66";
	
	private static final String REPORT_ID = "RPTMRA003";
	private static final String RESOURCE_BUNDLE_KEY = "listcn51cn66";
	
	private static final String ACTION = "generateInvoiceReport";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("MAILTRACKING_GPAILLING","InvoiceDetailsPrintComand entered");
		ListCN51CN66Form form = (ListCN51CN66Form)invocationContext.screenModel;
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//ErrorVO errorVO = null;
		ListCN51CN66Session session = (ListCN51CN66Session)getScreenSession(
				MODULE, SCREENID);
		
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		
		log.log(Log.INFO, "gpaCode>>>", session.getCN51CN66FilterVO().getGpaCode().toUpperCase());
		log.log(Log.INFO, "inv num>>>", session.getCN51CN66FilterVO().getInvoiceNumber().toUpperCase());
		//		if(errors != null && errors.size() > 0){
//			invocationContext.addAllError(errors);
//			invocationContext.target=PRINT_UNSUCCESSFUL;
//			return;
//			
//		}
		CN51CN66FilterVO filterVo = new CN51CN66FilterVO();
		filterVo.setCompanyCode(companyCode);
		filterVo.setGpaCode(session.getCN51CN66FilterVO().getGpaCode().toUpperCase());
		filterVo.setInvoiceNumber(session.getCN51CN66FilterVO().getInvoiceNumber().toUpperCase());
		
		//interlineFilterVO.setInterlineBillingType(INTERLINE_BILLING_TYPE);
		
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

		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
		
	}
	

}
