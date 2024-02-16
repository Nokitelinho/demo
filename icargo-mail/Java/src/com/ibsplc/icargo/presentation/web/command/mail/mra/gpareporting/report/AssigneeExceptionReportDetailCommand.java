/*
 * AssigneeExceptionReportDetailCommand.java Created on Mar 6, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.report;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.GPAReportForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-2245
 * 
 * 
 * Revision History
 * 
 * Version      Date           		Author              Description
 * 
 *  0.1         Mar 6, 2007  		A-2245				Initial draft
 *  
 */
public class AssigneeExceptionReportDetailCommand extends AbstractPrintCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("Mailtracking MRA GPAReports");
	private static final String CLASS_NAME = "AssigneeExceptionReportDetailCommand";
	
	private static final String REPORT_ID = "RPTMRA009";
	private static final String RESOURCE_BUNDLE_KEY = "gpareportResources";
	private static final String ACTION = "printExceptionsReportAssigneeDetails";
	/*
	 * Target mappings for failure
	 */
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	/*
	 * errorcodes
	 */
	private static final String ERR_MANDGASNUSR = "mailtracking.mra.gpareporting.report.msg.err.mandatoryassignee";
	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 * 	
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		/*
		 * getting form
		 */
		GPAReportForm gpaReportForm = (GPAReportForm)invocationContext.screenModel;
		/*
		 * companyCode defined
		 */
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		/*
		 * populating GPAReportingFilterVO
		 */
		GPAReportingFilterVO gpaReportingFilterVO = new GPAReportingFilterVO();
		gpaReportingFilterVO.setCompanyCode(companyCode);
		log.log(Log.INFO, "gpaReportForm.getAssignee()>>>", gpaReportForm.getAssigneeDetailsAssignee());
		if(gpaReportForm.getAssigneeDetailsAssignee()!=null && 
				gpaReportForm.getAssigneeDetailsAssignee().trim().length()>0){
			gpaReportingFilterVO.setAssignee(gpaReportForm.getAssigneeDetailsAssignee().trim().toUpperCase());
		}else{
			invocationContext.addError(new ErrorVO(ERR_MANDGASNUSR));
			log.log(Log.INFO,"**********madatory Assignee user*******list_failure");
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		gpaReportingFilterVO.setPoaCode(gpaReportForm.getAssigneeDetailsGPACode().trim().toUpperCase());
		gpaReportingFilterVO.setPoaName(gpaReportForm.getAssigneeDetailsGPAName().trim());
	   	boolean isTimeNeeded = false;
	   	if(gpaReportForm.getAssigneeDetailsFromDate()!=null && 
	   			gpaReportForm.getAssigneeDetailsFromDate().trim().length()>0) {
			gpaReportingFilterVO.setReportingPeriodFrom(new LocalDate(
				   			LocalDate.NO_STATION,Location.NONE, isTimeNeeded).
				   			setDate(gpaReportForm.getAssigneeDetailsFromDate().trim()));
		}
	   	if(gpaReportForm.getAssigneeDetailsToDate()!=null && 
	   			gpaReportForm.getAssigneeDetailsToDate().trim().length()>0) {
			gpaReportingFilterVO.setReportingPeriodTo(new LocalDate(
				   			LocalDate.NO_STATION,Location.NONE, isTimeNeeded).
				   			setDate(gpaReportForm.getAssigneeDetailsToDate().trim()));
		}
	   	gpaReportingFilterVO.setCountry(gpaReportForm.getAssigneeDetailsCountryCode().trim().toUpperCase());
	   	gpaReportingFilterVO.setExceptionCode(gpaReportForm.getAssigneeDetailsExceptionCode().trim().toUpperCase());
	   	
	   	log.log(Log.INFO, "gpaReportingFilterVO>>", gpaReportingFilterVO);
		/*
	   	 * populating reportSpec
	   	 */
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(gpaReportForm.getProduct());
		reportSpec.setSubProductCode(gpaReportForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(gpaReportingFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		/*
		 * generating report
		 */
		generateReport();

		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		invocationContext.target = getTargetPage();
		log.exiting(CLASS_NAME,"execute");
	}	
	
	
}
