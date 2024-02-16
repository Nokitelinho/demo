/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report.tk.InvoiceDetailsPrintComand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	27-Feb-2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report.tk;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report.tk.InvoiceDetailsPrintComand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	27-Feb-2014	:	Draft
 */
public class InvoiceDetailsPrintComand extends AbstractPrintCommand{
	
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	private static final String MODULE = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	
	private static final String REPORT_ID = "RPRMTK087";
	private static final String RESOURCE_BUNDLE_KEY = "listcn51cn66";
	
	private static final String ACTION = "generateGPABillingInvoiceReportTK";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-4809 on 27-Feb-2014
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("MAILTRACKING_GPAILLING","InvoiceDetailsPrintComand entered");
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(MODULE, SCREEN_ID);
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		String counter = form.getSelectedRow();
		String[] selectedRows = null;
		if(counter!=null && counter.trim().length()>0){
		selectedRows=counter.split(",");
		}
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession
		.getCN51SummaryVOs();
		ArrayList<CN51SummaryVO> cN51SummaryVOArraylist = new ArrayList<CN51SummaryVO>(
				cN51SummaryVOs);
		CN51CN66FilterVO cN51CN66FilterVO = null;
		Collection<CN51CN66FilterVO> cN51CN66FilterVOs = new ArrayList<CN51CN66FilterVO>();
		if(selectedRows!=null && selectedRows.length>0){
		for(int index=0;index<selectedRows.length;index++){
			CN51SummaryVO cn51SummaryVO=cN51SummaryVOArraylist.get(Integer.parseInt(selectedRows[index]));
			 cN51CN66FilterVO = new CN51CN66FilterVO (); 
			cN51CN66FilterVO.setCompanyCode(companyCode);
			cN51CN66FilterVO.setGpaCode(cn51SummaryVO.getGpaCode());
			cN51CN66FilterVO.setInvoiceNumber(cn51SummaryVO.getInvoiceNumber());
			cN51CN66FilterVOs.add(cN51CN66FilterVO);
		}
		}else{
			for(CN51SummaryVO cn51SummaryVO :cN51SummaryVOs){
				 cN51CN66FilterVO = new CN51CN66FilterVO (); 
					cN51CN66FilterVO.setCompanyCode(companyCode);
					cN51CN66FilterVO.setGpaCode(cn51SummaryVO.getGpaCode());
					cN51CN66FilterVO.setInvoiceNumber(cn51SummaryVO.getInvoiceNumber());
					cN51CN66FilterVOs.add(cN51CN66FilterVO);
			}
		}
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(false);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(cN51CN66FilterVOs);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_GPABILLING","InvoiceDetailsPrintComand exit");
		invocationContext.target = getTargetPage();
		
	}

}
