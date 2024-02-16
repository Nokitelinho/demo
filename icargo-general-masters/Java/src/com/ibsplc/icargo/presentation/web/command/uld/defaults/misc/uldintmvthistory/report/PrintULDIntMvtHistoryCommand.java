/*
 * PrintULDIntMvtHistoryCommand.java Created on Mar 31, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldintmvthistory.report;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDIntMvtHistoryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class PrintULDIntMvtHistoryCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTLST211";
	private Log log = LogFactory.getLogger("ULD Internal Movement History");
	private static final String BLANK = "";
	private static final String SELECT="ALL";
	private static final String RESOURCE_BUNDLE_KEY = "uldIntMvtResources";	
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String ACTION = "printULDIntMovementHistory";

    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
										throws	CommandInvocationException {
		log.log(log.INFO,"entering printcommand");
		ULDIntMvtHistoryFilterVO uldIntMvtFilterVO = new ULDIntMvtHistoryFilterVO();
		ULDIntMvtHistoryForm form = (ULDIntMvtHistoryForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		LocalDate fromDate = new LocalDate(logonAttributes.getAirportCode(),
							Location.ARP,false);
		LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(),
							Location.ARP,false);
		uldIntMvtFilterVO.setCompanyCode(companyCode);
		if(form.getUldNumber()!= null && !BLANK.equals
				(form.getUldNumber())&& !SELECT.equals
									(form.getUldNumber())){
			uldIntMvtFilterVO.setUldNumber((form.getUldNumber().trim()
				.toUpperCase()));
		}
		if(form.getFromDate()!= null && !BLANK.equals
				(form.getFromDate())&& !SELECT.equals
										(form.getFromDate())){
			uldIntMvtFilterVO.setFromDate(fromDate.setDate
										(form.getFromDate()));
		}
		if(form.getToDate()!= null && !BLANK.equals
				(form.getToDate())&& !SELECT.equals
										(form.getToDate())){
			uldIntMvtFilterVO.setToDate(toDate.setDate(form.getToDate()));
		}
		log.log(Log.FINE, "\n\n\n---Filter Vo sent to server----->",
				uldIntMvtFilterVO);
		ReportSpec reportSpec = getReportSpec();
		log.log(Log.INFO, "report spec----------->", reportSpec);
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());			
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldIntMvtFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.exiting("PrintULDIntMvtHistoryCommand","PrintCommand exit");
		invocationContext.target = getTargetPage();
}
}
