/*
 * PrintCommand.java Created on Jan 5, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmvalidation.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMValidationSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMValidationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class PrintCommand extends AbstractPrintCommand  {
	private static final String REPORT_ID = "RPTLST319";
	private Log log = LogFactory.getLogger("List SCM Validation");
	private static final String PRINT_FAILURE = "print_failure";
	private static final String SCREENID = "uld.defaults.scmvalidation";
	 private static final String MODULE = "uld.defaults";
	 private static final String ULD_SCMSTATUS = "uld.defaults.scmstatus";
	 private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	 private static final String PRODUCT="uld";
	 private static final String SUBPRODUCT="defaults";
	 private static final String ACTION="printSCMValidationReport";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		SCMValidationForm scmValidationForm = (SCMValidationForm)invocationContext.screenModel;
		SCMValidationSession scmValidationSession = (SCMValidationSession) getScreenSession(
				MODULE, SCREENID);
		SCMValidationFilterVO scmValidationFilterVO = new SCMValidationFilterVO();
		scmValidationFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		//added by a-3278 for bug 34185 on 21Jan09
		scmValidationFilterVO.setAirlineIdentifier(logonAttributes.getOwnAirlineIdentifier());	
		//a-3278 ends
		if((scmValidationForm.getAirport() != null &&
				scmValidationForm.getAirport().trim().length() > 0)) {
			scmValidationFilterVO.setAirportCode(scmValidationForm.getAirport().toUpperCase());
		}
		if((scmValidationForm.getUldTypeCode() != null &&
				scmValidationForm.getUldTypeCode().trim().length() > 0)) {
			scmValidationFilterVO.setUldTypeCode(scmValidationForm.getUldTypeCode().toUpperCase());
		}
		if((scmValidationForm.getFacilityType()!= null &&
				scmValidationForm.getFacilityType().trim().length() > 0)) {
		scmValidationFilterVO.setFacilityType(scmValidationForm.getFacilityType().toUpperCase());
		}
		if((scmValidationForm.getLocation()!= null &&
				scmValidationForm.getLocation().trim().length() > 0)) {
			scmValidationFilterVO.setLocation(scmValidationForm.getLocation().toUpperCase());
		}
		if((scmValidationForm.getScmStatus()!= null &&
				scmValidationForm.getScmStatus().trim().length() > 0)) {
		scmValidationFilterVO.setScmStatus(scmValidationForm.getScmStatus().toUpperCase());
		}
		scmValidationFilterVO.setPageNumber(Integer.parseInt(scmValidationForm.getDisplayPage()));
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++===>", oneTimes);
		Collection<OneTimeVO> facilityType = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> scmStatus = new ArrayList<OneTimeVO>();
		
		if (oneTimes != null) {
			facilityType = oneTimes.get(FACILITYTYPE_ONETIME);
			log.log(Log.INFO, "facilityType++", facilityType);
		}
		if (oneTimes != null) {
			scmStatus = oneTimes.get(ULD_SCMSTATUS);
			log.log(Log.INFO, "scmStatus++", scmStatus);
		}
		log.log(Log.FINE, "Filter VO To Server------------->",
				scmValidationFilterVO);
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setProductCode(PRODUCT);
		getReportSpec().setSubProductCode(SUBPRODUCT);
		getReportSpec().setPreview(true);
		getReportSpec().setHttpServerBase(invocationContext.httpServerBase);
		getReportSpec().addFilterValue(scmValidationFilterVO);
		getReportSpec().addExtraInfo(facilityType);
		getReportSpec().addExtraInfo(scmStatus);
		getReportSpec().setResourceBundle("SCMValidationResources");
		getReportSpec().setAction(ACTION);
		log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
		generateReport();
		if (getErrors() != null && getErrors().size() > 0) {
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.log(Log.FINE,"----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "----------report----->", invocationContext.target);
	}
	
	/**
	 * This method is used to get the one time details
	 * 
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(FACILITYTYPE_ONETIME);
			fieldValues.add(ULD_SCMSTATUS);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);
		} catch (BusinessDelegateException businessDelegateException) {
		 handleDelegateException(businessDelegateException);
		 businessDelegateException.getMessage();
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}


}
