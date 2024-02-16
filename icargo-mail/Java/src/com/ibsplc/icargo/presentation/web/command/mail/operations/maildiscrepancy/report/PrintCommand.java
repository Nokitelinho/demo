/*
 * PrintCommand.java Created on Jul 1 2016
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.maildiscrepancy.report;


import com.ibsplc.icargo.business.mail.operations.vo.MailDiscrepancyFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailDiscrepancyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class PrintCommand extends AbstractPrintCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");	

	/**
	 * TARGET
	 */
	private static final String REPORT_ID = "RPTMIS223";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateMailDiscrepancyReport";
	private static final String BUNDLE = "mailDiscrepancyResources";
	private static final String PRINT_FAILURE="print_failure";
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering("PrintCommand","execute");    	  
		MailDiscrepancyForm form = (MailDiscrepancyForm)invocationContext.screenModel;  	
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		MailDiscrepancyFilterVO filterVO = new MailDiscrepancyFilterVO();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(form.getFlightNumber()!=null){
		filterVO.setFlightCarrierCode(form.getFlightCarrierCode());
		filterVO.setFlightNumber(form.getFlightNumber());
		}
		filterVO.setAirport(form.getAirport());
		
		if(form.getFlightCarrierIdr()!=null&& form.getFlightCarrierIdr().length()>0){
			filterVO.setFlightCarrierid(Integer.parseInt(form.getFlightCarrierIdr()));
		}		
		filterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(form.getFromDate()));
		filterVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(form.getToDate()));
		filterVO.setDiscType(form.getDiscType());
		     
		ReportSpec reportSpec = getReportSpec();				
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		if (("GENERATE").equals(form.getStatus())) {
			reportSpec.setPreview(true);
		} else {
			reportSpec.setPreview(false);
		}
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.setAction(ACTION);
			
			generateReport();
			if(getErrors()!= null && getErrors().size()>0 )
			{
				invocationContext.addAllError(getErrors());
				log.log(Log.INFO, "****************errors", getErrors().toString());
				invocationContext.target=PRINT_FAILURE;
				return;
			}
			invocationContext.target = getTargetPage();
	}
}
