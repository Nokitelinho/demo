/*
 * CN51DetailsGpaWisePrintCommand.java Created on Mar 14, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsPrintFilterVO;
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
public class CN51DetailsGpaWisePrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	//private static final String MODULE = "mailtracking.mra.gpabilling";
	//private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillingreports";
	
	private static final String REPORT_ID = "RPTMRA036";
	private static final String RESOURCE_BUNDLE_KEY = "gpabillingreports";
	
	private static final String ACTION = "printcn51gpawise";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String GPACODE_MANDATORY="mailtracking.mra.gpabilling.msg.err.rpt.gpacodemandatory";
	private static final String FROM_GREATER="mailtracking.mra.gpabilling.msg.err.rpt.fromdategreatertodate";
	

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
		CN51DetailsPrintFilterVO filterVO=new CN51DetailsPrintFilterVO();
		ErrorVO errorVO=null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
		filterVO.setCompanyCode(companyCode);
		
		String fromDate=form.getFromDateGpa51();
		String toDate=form.getToDateGpa51();
		String gpaCode=form.getGpaCodeGpa51();
		String gpaName=form.getGpaNameGpa51();
		
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
		}
		
		if(fromDate!=null && fromDate.trim().length()>0){
			LocalDate frmDate=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			frmDate.setDate(fromDate.trim());
			filterVO.setFromDate(frmDate);
		}
		
		if(toDate!=null && toDate.trim().length()>0){
			LocalDate toDat=new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			toDat.setDate(toDate.trim());
			filterVO.setToDate(toDat);
		}
		
		if( filterVO.getFromDate() != null && filterVO.getToDate() != null){
			if(filterVO.getFromDate().isGreaterThan(filterVO.getToDate())){
				errorVO=new ErrorVO(FROM_GREATER);
				errors.add(errorVO);
			}
		}
		
		
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target=PRINT_UNSUCCESSFUL;
			return;
		}
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