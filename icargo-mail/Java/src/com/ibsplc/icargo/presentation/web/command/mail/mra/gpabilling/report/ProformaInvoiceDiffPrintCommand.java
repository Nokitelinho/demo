/*
 * ProformaInvoiceDiffPrintCommand.java Created on Aug 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ProformaMailInvoiceDiffReportForm ;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3271
 *
 */
public class ProformaInvoiceDiffPrintCommand extends AbstractPrintCommand{
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	private static final String REPORT_ID = "RPTLST279";
	
	private static final String ACTION = "generateProformaInvoiceDiffReport";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String FUNCTIONPOINT_MANDATORY = "mailtracking.mra.gpabilling.msg.err.rpt.functionptmandatory";
	private static final String FROMDATE_MANDATORY = "mailtracking.mra.gpabilling.msg.err.rpt.fromdatemandatory";
	private static final String TODATE_MANDATORY = "mailtracking.mra.gpabilling.msg.err.rpt.todatemandatory";
	private static final String FROMDATE_GREATER = "mailtracking.mra.gpabilling.msg.err.fromdatenotgreater";	
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("Mailtracking GPABilling","Print Command entered");
		ProformaMailInvoiceDiffReportForm proformaInvoiceDiffReportForm  = (ProformaMailInvoiceDiffReportForm )invocationContext.screenModel;
		//ProformaInvoiceDiffReportVO proformaInvoiceDiffReportVO = new ProformaInvoiceDiffReportVO();
		CN51SummaryVO cn51SummaryVO = new CN51SummaryVO();
				
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
		cn51SummaryVO.setCompanyCode(companyCode);
		
		errors = validateForm(proformaInvoiceDiffReportForm); 
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			log.log(Log.FINE, "Enter error", errors.size());
			invocationContext.target=PRINT_UNSUCCESSFUL;
			return;
		}
		if(proformaInvoiceDiffReportForm.getFromDate()!=null && proformaInvoiceDiffReportForm.getToDate()!=null){
			errors = validateDates(proformaInvoiceDiffReportForm);
			if(errors!=null && errors.size() > 0 ) {
				invocationContext.addAllError(errors);
				invocationContext.target = PRINT_UNSUCCESSFUL;
				return;
			}
		}
		
		String functionPoint = proformaInvoiceDiffReportForm.getFunctionPoint();
		String fromDate = proformaInvoiceDiffReportForm.getFromDate();
		log.log(Log.INFO, "Entered From date ", fromDate);
		String toDate = proformaInvoiceDiffReportForm.getToDate();
		String country = proformaInvoiceDiffReportForm.getCountry().toUpperCase();
		
			
		cn51SummaryVO.setFunctionPoint(functionPoint);
			
				LocalDate frmDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
				frmDate.setDate(fromDate.trim());
				cn51SummaryVO.setFromDate(frmDate);
			
				LocalDate toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		        toDat.setDate(toDate.trim());
		        cn51SummaryVO.setToDate(toDat);
		        
		        if(country != null){
		        	cn51SummaryVO.setCountryCode(country);
				}
					
			getReportSpec().setReportId(REPORT_ID);
			getReportSpec().setPreview(true);
		    getReportSpec().setProductCode(proformaInvoiceDiffReportForm.getProduct());
			getReportSpec().setSubProductCode(proformaInvoiceDiffReportForm.getSubProduct());
			getReportSpec().setResourceBundle(proformaInvoiceDiffReportForm.getBundle());
			getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
			getReportSpec().addFilterValue(cn51SummaryVO); 
			getReportSpec().setAction(ACTION);
			generateReport();
			
			if(getErrors() != null && getErrors().size() > 0){
				invocationContext.addAllError(getErrors());
				log.log(Log.FINE, "Enter error", getErrors().size());
				invocationContext.target=PRINT_UNSUCCESSFUL;
				return;
			}
					
			log.exiting("Mailtracking GPABilling", "execute");
			invocationContext.target = getTargetPage();						
		
		}
	/**
	 * 
	 * @param proformaInvoiceDiffReportForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ProformaMailInvoiceDiffReportForm proformaInvoiceDiffReportForm){
		log.entering("PrintCommand", "validateForm");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(proformaInvoiceDiffReportForm.getFunctionPoint().length() == 0){
			error = new ErrorVO(FUNCTIONPOINT_MANDATORY,new Object[] {"Function Point"});
			errors.add(error);
		}
		
		if(proformaInvoiceDiffReportForm.getFromDate().length() == 0){
			error = new ErrorVO(
					FROMDATE_MANDATORY, new Object[] {"From Date"});
			errors.add(error);
		}
		
		if(proformaInvoiceDiffReportForm.getToDate().length() == 0){
			error = new ErrorVO(TODATE_MANDATORY, new Object[] {"To Date"});
			errors.add(error);
		}
		log.exiting("PrintCommand", "validateForm");
		return errors;
	}
	
	/**
	 * 
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateDates(ProformaMailInvoiceDiffReportForm proformaInvoiceDiffReportForm){
		log.entering("PrintCommand", "validateDates");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LocalDate frmDat=new LocalDate(LocalDate
									.NO_STATION,Location.NONE, false)
												.setDate(proformaInvoiceDiffReportForm.getFromDate());
		LocalDate toDat=new LocalDate(LocalDate
									.NO_STATION,Location.NONE, false)
												.setDate(proformaInvoiceDiffReportForm.getToDate());
		if(frmDat.isGreaterThan(toDat)){
			log.log(Log.FINE,"validateDates");
			errors.add(new ErrorVO(FROMDATE_GREATER));
		}
		log.exiting("PrintCommand", "validateDates");
		return errors;
	}
}
