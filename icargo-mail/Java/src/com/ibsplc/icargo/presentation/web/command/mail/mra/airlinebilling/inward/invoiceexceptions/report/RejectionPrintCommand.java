/*
 * RejectionPrintCommand.java Created on March 2, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.invoiceexceptions.report;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ExceptionInInvoiceFilterVO;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.InvoiceExceptionsForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2399
 *
 */
/**
 * 
 * Revision History
 * 
 * Version      Date           Author          		    Description
 * 
 *  0.1         Dec 15, 2006  A-2399    		
 *  
 *  
 *  
 *  		 	
 */
public class RejectionPrintCommand extends AbstractPrintCommand {
/**
 * 
 * report id
 */
	private static final String REPORT_ID = "RPTMRA022";
	/**
	 * 
	 * bundle
	 */
	private static final String RESOURCE_BUNDLE_KEY = "invoiceexceptionsresources";
	
	/**
	 * 
	 * For setting the Target action
	 * */
	private static final String PRINT_FAILURE= "normal-report-error-jsp";
	private static final boolean PREVIEW = true;
	private static final String ACTION = "printExceptionInInvoice";
	/**
	 * 
	 * class name
	 */
	private static final String CLASS_NAME = " MraAirlineBillingInwardRejectionPrintCommand";
	/**
	 * 
	 * logger
	 */
	
	private static final String BLANK = "";
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA");	
	
	/**

	 * @param invocationContext

	 * @throws CommandInvocationException

	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		InvoiceExceptionsForm form =
			(InvoiceExceptionsForm)invocationContext.screenModel;
		ExceptionInInvoiceFilterVO filterVO=new ExceptionInInvoiceFilterVO();
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				
		filterVO.setCompanyCode(companyCode);
		if(!BLANK.equals(form.getAirlineCode())){
			filterVO.setAirlineCode(form.getAirlineCode().toUpperCase());
		}
		if(!BLANK.equals(form.getClearencePeriod())){
			filterVO.setClearancePeriod(form.getClearencePeriod());
		}
		//filter for rejection
			filterVO.setExceptionStatus("R");
		
		if(!BLANK.equals(form.getInvoiceNumber())){
			filterVO.setInvoiceNumber(form.getInvoiceNumber());
		}
		if(!BLANK.equals(form.getRejectionMemoNumber())){
			filterVO.setMemoCode(form.getRejectionMemoNumber());
		}
		if(!BLANK.equals(form.getContractCurrency())){
			filterVO.setContractCurrency(form.getContractCurrency());
		}
		if(!BLANK.equals(form.getMemoStatus())){
			filterVO.setMemoStatus(form.getMemoStatus());
		}
		 if(errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);			
		       	invocationContext.target = PRINT_FAILURE;
		       	return;
		}   
		 log.log(Log.FINE, "getting data for report...");
		
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(form.getProduct());
		getReportSpec().setSubProductCode(form.getSubProduct());
		getReportSpec().setPreview(PREVIEW);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
		getReportSpec().addFilterValue(filterVO);
		getReportSpec().setAction(ACTION);
		generateReport();
		log.log(Log.FINE, "generating report...");
		if(getErrors()!= null && getErrors().size() > 0) {
			invocationContext.addAllError(getErrors());	
			log.log(Log.FINE, "error in cmd class...");
	       	invocationContext.target = PRINT_FAILURE;
	       	return;
	   		}
		invocationContext.target = getTargetPage();
		log.exiting(CLASS_NAME, "execute");
	}
	
}
