/*
 * PrintBuildUpDetailsCommand.java Created on 28 JUL 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory.report;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2412
 *
 */
public class PrintBuildUpDetailsCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST270";

	private Log log = LogFactory.getLogger("PrintBuildUpDetailsCommand");

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private static final String ACTION = "printBuildUpDetails";

	private static final String BLANK = "";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
		ListULDMovementForm listULDMovementForm = (ListULDMovementForm) invocationContext.screenModel;
		uldMovementFilterVO = makeFilter(listULDMovementForm,
				uldMovementFilterVO);
		log.log(Log.INFO, "PrintBuildUpDetailsCommand ---- filter is ",
				uldMovementFilterVO);
		ReportSpec reportSpec = getReportSpec();
		log.log(Log.INFO, "report spec----------->", reportSpec);
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listULDMovementForm.getProduct());
		reportSpec.setSubProductCode(listULDMovementForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldMovementFilterVO);		
		reportSpec.setAction(ACTION);

		generateReport();

		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}		
		invocationContext.target = getTargetPage();

	}

	public ULDMovementFilterVO makeFilter(
			ListULDMovementForm listULDMovementForm,
			ULDMovementFilterVO uldMovementFilterVO) {
		LocalDate fromDate = new LocalDate(getApplicationSession().getLogonVO()
				.getAirportCode(), Location.ARP, false);
		LocalDate toDate = new LocalDate(getApplicationSession().getLogonVO()
				.getAirportCode(), Location.ARP, false);

		if (listULDMovementForm.getFromDate() != null
				&& !BLANK.equals(listULDMovementForm.getFromDate())) {
			uldMovementFilterVO.setFromDate(fromDate
					.setDate(listULDMovementForm.getFromDate()));
		}
		if (listULDMovementForm.getToDate() != null
				&& !BLANK.equals(listULDMovementForm.getToDate())) {
			uldMovementFilterVO.setToDate(toDate.setDate(listULDMovementForm
					.getToDate()));
		}
		uldMovementFilterVO.setUldNumber((listULDMovementForm.getUldNumber()
				.trim().toUpperCase()));
		
		log.log(Log.INFO, "listULDMovementForm.getDisplayPage()--------- ",
				listULDMovementForm.getDisplayPage());
		int pageNumber = Integer.parseInt(listULDMovementForm.getDisplayPage());
		uldMovementFilterVO.setPageNumber(pageNumber);
		uldMovementFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		return uldMovementFilterVO;
	}
}
