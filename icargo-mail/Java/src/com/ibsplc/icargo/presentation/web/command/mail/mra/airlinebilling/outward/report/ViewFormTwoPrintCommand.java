/*
 * ViewFormTwoPrintCommand.java Created on DEC09 , 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.report;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InterlineFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormTwoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormTwoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429
 * 
 */
public class ViewFormTwoPrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST320";

	private Log log = LogFactory.getLogger("print View Form2 details");

	private static final String BLANK = "";

	private static final String RESOURCE_BUNDLE_KEY = "viewForm2";

	private static final String ACTION = "findformtwoprint";

	private static final String MODULE_NAME = "mra.airlinebilling";

	private static final String CLASS_NAME = "ViewFormTwoPrintCommand";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform2";

	private static final String CLEARANCEPERIOD_MANDATORY = "mra.airlinebilling.reports.clrprdmandatory";

	private static final String CLEARANCEPERIOD_INVALID = "mailtracking.mra.airlinebilling.error.invalidClearancePeriod";

	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ViewMailFormTwoForm viewForm2Form = (ViewMailFormTwoForm) invocationContext.screenModel;
		ViewFormTwoSession viewForm2Session = (ViewFormTwoSession) getScreenSession(
				MODULE_NAME, SCREENID);
		String clearancePeriod = viewForm2Form.getClearancePeriod();
		log.log(Log.INFO, "Clearance period from FORM===>>>", clearancePeriod);
		ErrorVO errorVO = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if (("").equals(viewForm2Form.getClearancePeriod())
				&& (viewForm2Form.getClearancePeriod().trim().length() == 0)) {

			errorVO = new ErrorVO(CLEARANCEPERIOD_MANDATORY);
			errors.add(errorVO);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
		iatacalendarfiltervo.setCompanyCode(logonAttributes.getCompanyCode());
		iatacalendarfiltervo.setClearancePeriod(viewForm2Form
				.getClearancePeriod());
		IATACalendarVO iatacalendarvo = null;
		MailTrackingMRADelegate mailTrackingMRADelegate =new MailTrackingMRADelegate();		
		try {

			iatacalendarvo = mailTrackingMRADelegate
			.validateClearancePeriod(iatacalendarfiltervo);
			log.log(Log.INFO, "iatacalendarvo obtained print", iatacalendarvo);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}

		if (iatacalendarvo == null ) {
			log.log(log.INFO, "iatacalendarvo null print");

			errors.add(new ErrorVO(CLEARANCEPERIOD_INVALID));

		} else {
			log.log(log.INFO, "iatacalendarvo!=null");
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		InterlineFilterVO interlineFilterVO = new InterlineFilterVO();
		interlineFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
				.getCompanyCode());
		interlineFilterVO.setIsFormTwo(true);
		interlineFilterVO.setClearancePeriod(clearancePeriod);
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(viewForm2Form.getProduct());
		reportSpec.setSubProductCode(SUBPRODUCT);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(interlineFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			log.log(Log.INFO, "inside getERRORS print", getErrors());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_VIEWFORM TWO", "PrintCommand exit");
		invocationContext.target = getTargetPage();
	}

}
