/*
 * PrintULDHistoryCommand.java Created on Oct 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldhistory.report;

import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDHistoryForm;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDHistoryVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 * 
 * @author A-2619
 * 
 */

public class PrintULDHistoryCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST078";

	private static final String ACTION = "printULDHistory";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private static final String LIST_SUCCESS = "uldhistory_print_success";

	private Log log = LogFactory.getLogger("PrintULDHistoryCommand");

	/**
	 * @author A-2619
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("PrintULDHistoryCommand", "execute");
		log.log(Log.FINE, "PrintULDHistoryCommand000000000000000000000000");
		ULDHistoryForm uldHistoryForm = (ULDHistoryForm) invocationContext.screenModel;
		log.log(Log.FINE, "PrintULDHistoryCommand111111111111111111111111");
		ULDHistoryVO uldHistoryVO = new ULDHistoryVO();
		log.log(Log.FINE, "PrintULDHistoryCommand222222222222222222222");
		uldHistoryVO = makeFilter(uldHistoryForm);
		log.log(Log.FINE, "PrintULDHistoryCommand333333333333333333333");

		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(uldHistoryForm.getProduct());
		reportSpec.setSubProductCode(uldHistoryForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldHistoryVO);
		reportSpec.setResourceBundle("uldHistory");
		reportSpec.setAction(ACTION);

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("ULDHistory", "PrintULDHistoryCommand");

		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);

		// invocationContext.target = LIST_SUCCESS;

	}

	private ULDHistoryVO makeFilter(ULDHistoryForm uldHistoryForm) {
		log.entering("PrintULDHistoryCommand", "makeFilter");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		ULDHistoryVO uldHistoryFilterVO = new ULDHistoryVO();

		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,
				false);
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);

		if (uldHistoryForm.getFromDate() != null
				&& uldHistoryForm.getFromDate().trim().length() != 0) {
			fromDate.setDate(uldHistoryForm.getFromDate());
			uldHistoryFilterVO.setFromDate(fromDate);
		}
		if (uldHistoryForm.getToDate() != null
				&& uldHistoryForm.getToDate().trim().length() != 0) {
			toDate.setDate(uldHistoryForm.getToDate());
			uldHistoryFilterVO.setToDate(toDate);
		}
		if (uldHistoryForm.getFlightDate() != null
				&& uldHistoryForm.getFlightDate().trim().length() != 0) {
			flightDate.setDate(uldHistoryForm.getFlightDate());
			uldHistoryFilterVO.setFlightDate(flightDate);
		}
		uldHistoryFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		uldHistoryFilterVO.setUldNumber(uldHistoryForm.getUldNumber());
		uldHistoryFilterVO.setUldStatus(uldHistoryForm.getStatus());
		uldHistoryFilterVO.setCarrierCode(uldHistoryForm.getCarrierCode());
		uldHistoryFilterVO.setFlightNumber(uldHistoryForm.getFlightNumber());
		uldHistoryFilterVO.setFromStation(uldHistoryForm.getFromStation());

		String toDisplayPage = uldHistoryForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		uldHistoryFilterVO.setPageNumber(displayPage);
		log.exiting("PrintULDHistoryCommand", "makeFilter");
		return uldHistoryFilterVO;
	}
}
