/*
 * PrintCommand.java Created on AUG , 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listcca.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-3429
 * 
 */
public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST283";

	private Log log = LogFactory.getLogger("print CCAs");

	private static final String BLANK = "";

	private static final String SELECT = "ALL";

	private static final String CCA_TYPE = "mra.defaults.ccatype";

	private static final String CCA_STATUS = "mra.defaults.ccastatus";

	private static final String ISSUE_PARTY = "mra.defaults.issueparty";

	private static final String CCABILSTA_ONETIME = "mra.airlinebilling.billingstatus";

	private static final String CCABILLSTA_ONETIME = "mra.gpabilling.billingstatus";

	private static final String RESOURCE_BUNDLE_KEY = "listCCA";

	private static final String ACTION = "listCCAReport";

	// private static final String PRINT_UNSUCCESSFUL =
	// "normal-report-error-jsp";

	private static final String PRINT_UNSUCCESSFUL = "print_failure";

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
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO = applicationSession.getLogonVO();
		ErrorVO error = null;
		ListCCAForm form = (ListCCAForm) invocationContext.screenModel;
		log.log(Log.FINE, "\n\n\n-inside print Command----->");
		ListCCAFilterVO listCCAFilterVO = new ListCCAFilterVO();
		listCCAFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
		listCCAFilterVO.setCcaRefNumber(form.getCcaNum());
		if (form.getCcaType() != null && !BLANK.equals(form.getCcaType())) {
			listCCAFilterVO.setCcaType(form.getCcaType());
		}
		listCCAFilterVO.setDsn(form.getDsn());
		if (form.getDsnDate() != null && form.getDsnDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getDsnDate(), "dd-MMM-yyyy")) {
				LocalDate dsnDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				dsnDate.setDate(form.getDsnDate());
				listCCAFilterVO.setDsnDate(dsnDate);
			}

		} else {
			listCCAFilterVO.setDsnDate(null);
		}
		if (form.getCcaStatus() != null && !BLANK.equals(form.getCcaStatus())) {
			listCCAFilterVO.setCcaStatus(upper(form.getCcaStatus()));
		}
		if (form.getIssueParty() != null && !BLANK.equals(form.getIssueParty())) {
			if("ARL".equals(form.getIssueParty())){
				listCCAFilterVO.setArlGpaIndicator("A");
			}else if("GPA".equals(form.getIssueParty())){
				listCCAFilterVO.setArlGpaIndicator("G");
			}
			listCCAFilterVO.setIssueParty(form.getIssueParty());
		}
		listCCAFilterVO.setAirlineCode(form.getAirlineCode());
		listCCAFilterVO.setGpaCode(form.getGpaCode());
		listCCAFilterVO.setGpaName(form.getGpaName());
		if (((!("").equals(form.getFrmDate())) && form.getFrmDate() != null)) {
			if (!form.getFrmDate().equals(form.getToDate())) {
				if (!DateUtilities.isLessThan(form.getFrmDate(), form
						.getToDate(), "dd-MMM-yyyy")) {
					error = new ErrorVO(
					"mailtracking.mra.defaults.fromdateearliertodate");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
				}
			}
		}
		if (form.getFrmDate() != null && form.getFrmDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFrmDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(form.getFrmDate());
				listCCAFilterVO.setFromDate(frmDate);
			}

		} else {
			listCCAFilterVO.setFromDate(null);
		}
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(form.getToDate());
				listCCAFilterVO.setToDate(toDate);

			}

		} else {
			listCCAFilterVO.setFromDate(null);
		}
		listCCAFilterVO.setPageNumber(Integer
				.parseInt(form.getDisplayPage()));
		if(form.getOrigin()!=null && form.getOrigin().trim().length()>0){
			listCCAFilterVO.setOrigin(form.getOrigin());  
		}
		if(form.getDestination()!=null && form.getDestination().trim().length()>0){
			listCCAFilterVO.setDestination(form.getDestination());
		}
		log.log(Log.FINE, "\n\n\n----------Filter Vo sent to server----->",
				listCCAFilterVO);  

		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++===>", oneTimes);
		Collection<OneTimeVO> ccaType = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> ccaArlBilStatus = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> ccaGpaBilStatus = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> issueParty = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			ccaType = oneTimes.get(CCA_TYPE);
			log.log(Log.INFO, "ccaType124++", ccaType);
		}
		if (oneTimes != null) {
			ccaArlBilStatus = oneTimes.get(CCA_STATUS);
			log.log(Log.INFO, "ccaArlBilStatus124++", ccaArlBilStatus);
		}
		if (oneTimes != null) {
			ccaGpaBilStatus = oneTimes.get(CCABILLSTA_ONETIME);
			log.log(Log.INFO, "ccaGpaBilStatus124++", ccaGpaBilStatus);
		}
		if (oneTimes != null) {
			issueParty = oneTimes.get(ISSUE_PARTY);
		}
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(listCCAFilterVO);
		reportSpec.addExtraInfo(ccaType);
		reportSpec.addExtraInfo(ccaArlBilStatus);
		reportSpec.addExtraInfo(ccaGpaBilStatus);
		reportSpec.addExtraInfo(issueParty);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);

		generateReport();

		if (getErrors() != null && getErrors().size() > 0) {
			log.log(Log.INFO, "inside getERRORS", getErrors());
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_LISTCCA", "PrintCommand exit");
		invocationContext.target = getTargetPage();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	private String upper(String input) {

		if (input != null) {
			return input.trim().toUpperCase();
		} else {
			return "";
		}

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

			fieldValues.add(CCA_TYPE);
			fieldValues.add(CCA_STATUS);			
			fieldValues.add(CCABILLSTA_ONETIME);
			fieldValues.add(ISSUE_PARTY);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}

}
