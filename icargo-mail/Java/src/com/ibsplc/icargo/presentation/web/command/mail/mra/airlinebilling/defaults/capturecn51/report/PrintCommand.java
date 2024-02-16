/*
 * PrintCommand.java Created on Feb-03,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51.report;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2407
 * 
 */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTMRA016";

	private static final String RESOURCE_BUNDLE_KEY = "capturecn51resources";

	private static final String ACTION = "findCN51DetailsReport";

	private Log log = LogFactory
			.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");

	private static final String CLASS_NAME = "PrintCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String DISABLESTATUS = "DISABLE";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	/**
	 * 
	 * @param invocationContext
	 * @return void
	 * @exception CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CRA_AIRLINEBILLING",
						"cAPTURE CN51 Print Command ");

		CaptureCN51Form captureCN51Form = (CaptureCN51Form) invocationContext.screenModel;

		AirlineCN51FilterVO airlineCN51FilterVO = null;
		CaptureCN51Session captureCN51Session = (CaptureCN51Session) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		airlineCN51FilterVO=captureCN51Session.getFilterDetails();
		airlineCN51FilterVO.setInterlineBillingType(captureCN51Form.getBillingType().toUpperCase());
		
		
	/*	airlineCN51FilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		if ((captureCN51Form.getInvoiceRefNo() != null)
				&& (captureCN51Form.getInvoiceRefNo().trim().length() > 0)) {
			airlineCN51FilterVO.setInvoiceReferenceNumber(captureCN51Form
					.getInvoiceRefNo().toUpperCase());
		}
		if ((captureCN51Form.getClearancePeriod() != null)
				&& (captureCN51Form.getClearancePeriod().trim().length() > 0)) {
			airlineCN51FilterVO.setIataClearancePeriod(captureCN51Form
					.getClearancePeriod().toUpperCase());

		}
		if ((captureCN51Form.getAirlineCode() != null)
				&& (captureCN51Form.getAirlineCode().trim().length() > 0)) {
			airlineCN51FilterVO.setAirlineCode(captureCN51Form.getAirlineCode()
					.toUpperCase());

		}
		if ((captureCN51Form.getCategory() != null)
				&& (captureCN51Form.getCategory().trim().length() > 0)) {
			airlineCN51FilterVO.setCategoryCode(captureCN51Form.getCategory());

		}
		if ((captureCN51Form.getCarriageFrom() != null)
				&& (captureCN51Form.getCarriageFrom().trim().length() > 0)) {
			airlineCN51FilterVO.setCarriageStationFrom(captureCN51Form
					.getCarriageFrom().toUpperCase());

		}
		if ((captureCN51Form.getCarriageTo() != null)
				&& (captureCN51Form.getCarriageTo().trim().length() > 0)) {
			airlineCN51FilterVO.setCarriageStationTo(captureCN51Form
					.getCarriageTo().toUpperCase());

		}
		if ((captureCN51Form.getBillingType() != null)
				&& (captureCN51Form.getBillingType().trim().length() > 0)) {
			airlineCN51FilterVO.setInterlineBillingType(captureCN51Form
					.getBillingType().toUpperCase());
		}
*/
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(captureCN51Form.getProduct());
		getReportSpec().setSubProductCode(captureCN51Form.getSubProduct());
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(airlineCN51FilterVO);
		getReportSpec().setResourceBundle(RESOURCE_BUNDLE_KEY);
		getReportSpec().setAction(ACTION);
		log.log(Log.FINE,"\nBefore Calling generateReport");
		generateReport();
		log.log(Log.FINE,"\nAfter Calling generateReport");
		
		if(getErrors() != null && getErrors().size() > 0){
			log.log(Log.FINE,"\nnside getErrors()");
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			log.log(Log.FINE,"Errors are returned............for no data");
			return;
		}
		invocationContext.target = getTargetPage();
		log.exiting("CRA_AIRLINEBILLING","CAPTURE CN51 Print Command ");

		

	}

}
