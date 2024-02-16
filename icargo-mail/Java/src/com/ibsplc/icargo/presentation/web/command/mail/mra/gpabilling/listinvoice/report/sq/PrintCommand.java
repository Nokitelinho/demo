/*
 * PrintCommand.java Created on Oct 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report.sq;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-10383
 * 
 */
public class PrintCommand extends AbstractPrintCommand {

	private Log log = LogFactory.getLogger("MRA GPABILLING");


	private static final String CLASS_NAME = "PrintCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listgpabillinginvoice";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private static final String INVOICE_STATUS = "mra.gpabilling.invoicestatus";


	private static final String RESOURCE_BUNDLE_KEY = "listgpabillinginvoiceresources";

	private static final String ACTION = "generateCoverPageSQ";

	
	private static final String REPORT_ID = "RPRMRA001";
	/**
	 * Method to implement the List operation
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException 
		{

		log.entering("MAILTRACKING_GPAILLING","InvoiceDetailsPrintComand entered");
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm)invocationContext.screenModel;


	   	 	ListGPABillingInvoiceSession listGPABillingInvoiceSession =getScreenSession(MODULE_NAME, SCREENID);  	
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
				.getCN51SummaryVOs();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<>(
				cN51SummaryVOs);
		String counter = form.getSelectedRow();
		String[] selectedRows = null;
		if(counter!=null && counter.trim().length()>0){
			selectedRows=counter.split(",");
		}
		
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO (); 
		if(selectedRows!=null && selectedRows.length>0){
			for(int index=0;index<selectedRows.length;index++){
				CN51SummaryVO cn51SummaryVO=cN51SummaryVOArraylist.get(Integer.parseInt(selectedRows[index]));
				cN51CN66FilterVO.setCompanyCode(companyCode);
				cN51CN66FilterVO.setGpaCode(cn51SummaryVO.getGpaCode());
				cN51CN66FilterVO.setInvoiceNumber(cn51SummaryVO.getInvoiceNumber());
			}
	    		}
	    		ReportSpec reportSpec = getReportSpec();
	    		reportSpec.setReportId(REPORT_ID);
			reportSpec.setProductCode(form.getProduct());
			reportSpec.setSubProductCode(form.getSubProduct());
	    		reportSpec.setPreview(true);
	    		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
			reportSpec.addFilterValue(cN51CN66FilterVO);
	    		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
	    		reportSpec.setAction(ACTION);

	    		generateReport();

		if(getErrors() != null && getErrors().size() > 0)
		{
	    			invocationContext.addAllError(getErrors());
	    			invocationContext.target = PRINT_UNSUCCESSFUL;
	    			return;
	    		}

		log.exiting("MRA_GPABILLING","InvoiceDeatailsPrintCommand exit");
	    		invocationContext.target = getTargetPage();

	}
}
		
	    



	   





	    

