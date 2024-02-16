/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report.PrintAllCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	13-Jan-2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report;

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
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice.report.PrintAllCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	13-Jan-2014	:	Draft
 */
public class PrintAllCommand extends AbstractPrintCommand{
	
	private Log log = LogFactory.getLogger("MRA GPABILLING");
	
	private static final String REPORT_ID = "RPRMTK089";
	
	private static final String CLASS_NAME = "PrintAllCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
	
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private static final String RESOURCE_BUNDLE_KEY = "listcn51cn66";

	private static final String ACTION = "generateGPAInvoiceReport";

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-4809 on 13-Jan-2014
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "Execute");
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm) invocationContext.screenModel;

		ListGPABillingInvoiceSession listGPABillingInvoiceSession = (ListGPABillingInvoiceSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		//errors = validateForm(listGPABillingInvoiceForm, errors);
	/*	if (errors != null && errors.size() > 0) {

			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}*/
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
		reportSpec.setAction(ACTION); //Action to corrected as coding done

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			log.log(Log.INFO, "inside getERRORS", getErrors());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_LISTGPABILLINGINVOICE", "PrintAllCommand exit");
		invocationContext.target = getTargetPage();
	}
}
