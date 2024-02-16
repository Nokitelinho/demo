/*
 * GPABillableReportPrintCommand.java Created on Mar 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingReportsForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class GPABillableReportPrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	//private static final String MODULE = "mailtracking.mra.gpabilling";
	//private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillingreports";
	
	private static final String RESOURCE_BUNDLE_KEY = "gpabillingreports";
	
	private static final String ACTION1 = "printgpabillablereport";
	private static final String ACTION2 = "printgpabillablereporttk";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String GPACODE_MANDATORY="mailtracking.mra.gpabilling.msg.err.rpt.gpacodemandatory";
	private static final String FROMDATE_MANDATORY="mailtracking.mra.gpabilling.msg.err.rpt.fromdatemandatory";
	private static final String TODATE_MANDATORY="mailtracking.mra.gpabilling.msg.err.rpt.todatemandatory";
	

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("MAILTRACKING_GPABILLING","Print Command entered");
		GPABillingReportsForm form = (GPABillingReportsForm)invocationContext.screenModel;
		GPABillingEntriesFilterVO filterVO=new GPABillingEntriesFilterVO();
		String reportId = "RPTMRA039";
		ErrorVO errorVO=null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
		filterVO.setCompanyCode(companyCode);
		
		
		String gpaCode=form.getGpaCode();
		String gpaName=form.getGpaName();
		String billingStatus=form.getBillingStatus();
		
		if(gpaCode!=null && gpaCode.trim().length()>0){
			filterVO.setGpaCode(gpaCode.trim().toUpperCase());
		}
		else{
			errorVO=new ErrorVO(GPACODE_MANDATORY);
			errors.add(errorVO);
		}
//		gpaname is not required as filter
		if(gpaName!=null && gpaName.trim().length()>0){
			filterVO.setGpaName(gpaName.trim().toUpperCase());
			log.log(log.INFO,"inside not null");
		}else{
			filterVO.setGpaName("");
		}
		
		if(billingStatus!=null && billingStatus.trim().length()>0){
			filterVO.setBillingStatus(billingStatus.toUpperCase());
		}
		
		if(form.getFromDateBlbRpt()!=null && form.getFromDateBlbRpt().trim().length()>0){
			LocalDate fromDate =  new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			fromDate.setDate(form.getFromDateBlbRpt());
			filterVO.setFromDate(fromDate);
		}
		else{
			errorVO=new ErrorVO(FROMDATE_MANDATORY);
			errors.add(errorVO);
		}
		if(form.getToDateBlbRpt()!=null && form.getToDateBlbRpt().trim().length()>0){
			LocalDate toDate =  new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			toDate.setDate(form.getToDateBlbRpt());
			filterVO.setToDate(toDate);
		}
		else{
			errorVO=new ErrorVO(TODATE_MANDATORY);
			errors.add(errorVO);
		}
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=PRINT_UNSUCCESSFUL;
			return;
		}
		if("Y".equals(form.getSpecificFlag())){
			reportId="RPTMRA044";
		}
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(reportId);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		if("RPTMRA039".equals(reportId)){
			reportSpec.setAction(ACTION1);
		}else{
			reportSpec.setAction(ACTION2);
		}
		try{
		generateReport();
		}catch(Exception ex){
			log.entering("GPABillableReportPrintCommand",ex.getMessage());
		}
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
	}
}