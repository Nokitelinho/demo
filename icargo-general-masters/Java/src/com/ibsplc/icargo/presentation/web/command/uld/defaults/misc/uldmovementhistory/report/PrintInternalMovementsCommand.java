/*
 * PrintInternalMovementsCommand.java Created on Jul 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory.report;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
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
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2408
 *
 */
public class PrintInternalMovementsCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST274";
	private Log log = LogFactory.getLogger("List ULD Movement");
	private static final String BLANK = "";
	private static final String RESOURCE_BUNDLE_KEY = "listuldmovementResources";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	private static final String ACTION = "printinternalmovements";



	    /**
	     * execute method
	     * @param invocationContext
	     * @throws CommandInvocationException
	    */
	public void execute(InvocationContext invocationContext)
											throws	CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		ListULDMovementForm form = (ListULDMovementForm) invocationContext.screenModel;
		ULDIntMvtHistoryFilterVO uldIntMvtFilterVO = new ULDIntMvtHistoryFilterVO();
		uldIntMvtFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		String uldNumber = form.getUldNumber().toUpperCase();
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			
			uldIntMvtFilterVO.setUldNumber(uldNumber.trim());
		} else {
			uldIntMvtFilterVO.setUldNumber(BLANK);
		}

		if (form.getFromDate() != null
				&& form.getFromDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFromDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(form.getFromDate().trim());
				uldIntMvtFilterVO.setFromDate(frmDate);
			}

		} else {
			uldIntMvtFilterVO.setFromDate(null);
		}
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(form.getToDate().trim());
				uldIntMvtFilterVO.setToDate(toDate);
			}

		} else {
			uldIntMvtFilterVO.setToDate(null);
		}
		int pageNumber=Integer.parseInt(form.getDisplayPage());
		uldIntMvtFilterVO.setPageNumber(pageNumber);
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

		log.exiting("ULD_DEFAULTS","PrintCommand exit");
		invocationContext.target = getTargetPage();
		
	}

}
