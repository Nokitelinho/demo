/*
 * PrintCCACommand.java Created on Sep-15-2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca.report;

/**
 * @author A-3447
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/*
 * Command class for Printing CCA details of Maintain CCA 
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 15-Sep-2008 Muralee(a-3447) For CRQ_172
 */

/**
 * @author A-3447
 */
public class PrintCCACommand extends AbstractPrintCommand {




	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Report ID
	 */

	private static final String REPORT_ID = "RPTLST293";

	/**
	 * Onetime for cca type
	 */
	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";
	private static final String MALCATCOD_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String MALSUBCLS_ONETIME = "mailtracking.defaults.mailsubclassgroup";

	/**
	 * One time for issuing Party
	 */
	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";

	/**
	 * Resource Bundle Key
	 */

	private static final String RESOURCE_BUNDLE_KEY = "maintainCCA";

	/**
	 * Action
	 */

	private static final String ACTION = "printCCAReport";

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "PrintCCACommand";

	/**
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * Print UnsucessFul
	 */
	private static final String PRINT_UNSUCCESSFUL = "print_failure";

	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("PrintCCACommand");
	/**
	 * GPA
	 */
	private static final String GPA = "G";



	// -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * @author A-3447 execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *Excecute method for Printing CCA Details
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;		
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();

		if (maintainCCAForm.getCcaNum() != null	&& maintainCCAForm.getCcaNum().trim().length() > 0) {
			maintainCCAFilterVO.setCcaReferenceNumber(maintainCCAForm.getCcaNum());
		}

		if (maintainCCAForm.getDsnNumber() != null
				&& maintainCCAForm.getDsnNumber().trim().length() > 0) {
			maintainCCAFilterVO.setDsnNumber(maintainCCAForm.getDsnNumber());
		}
		if(maintainCCAForm.getIssueParty()!=null && 
				!"".equals(maintainCCAForm.getIssueParty())){
			maintainCCAFilterVO.setIssuedBy(maintainCCAForm.getIssueParty());

		}
		if(maintainCCAForm.getAirlineCode() !=null
				&& maintainCCAForm.getAirlineCode().trim().length()>0){			
			if(maintainCCAForm.getIssueParty()!=null && 
					!"".equals(maintainCCAForm.getIssueParty())){
				maintainCCAFilterVO.setIssuingParty(maintainCCAForm.getIssueParty());
				maintainCCAFilterVO.setIssuedBy(maintainCCAForm.getIssueParty());
			}
		}
		if(maintainCCAForm.getAirlineCode() !=null
				&& maintainCCAForm.getAirlineCode().trim().length()>0){
			maintainCCAFilterVO.setPartyCode(maintainCCAForm.getAirlineCode());	
		}
		if(maintainCCAForm.getAirlineLoc() !=null
				&& maintainCCAForm.getAirlineLoc().trim().length()>0){
			maintainCCAFilterVO.setPartyLocation(maintainCCAForm.getAirlineLoc());	
		}

		maintainCCAFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		maintainCCAFilterVO.setUsrCCANumFlg("N");
		log.log(Log.FINE, "-maintainCCAFilterVO---->", maintainCCAFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes-->", oneTimes);
		Collection<OneTimeVO> ccaType = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> ccaStatus = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> mailCategory = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> mailSubClass = new ArrayList<OneTimeVO>();

		Collection<OneTimeVO> issueParty = new ArrayList<OneTimeVO>();
		if("GPA".equals(maintainCCAFilterVO.getIssuingParty())){
			maintainCCAFilterVO.setIssuingParty(GPA);

		}

		if (oneTimes != null) {
			ccaType = oneTimes.get(CCATYPE_ONETIME);
			log.log(Log.INFO, "ccaType--->>", ccaType);
		}

		if (oneTimes != null) {
			ccaStatus = oneTimes.get(CCASTATUS_ONETIME);
			mailCategory=oneTimes.get(MALCATCOD_ONETIME);
			mailSubClass=oneTimes.get(MALSUBCLS_ONETIME);

		}
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(maintainCCAForm.getProduct());
		reportSpec.setSubProductCode(maintainCCAForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(maintainCCAFilterVO);
		reportSpec.addExtraInfo(ccaType);
		reportSpec.addExtraInfo(ccaStatus);
		reportSpec.addExtraInfo(mailCategory);
		reportSpec.addExtraInfo(mailSubClass);		
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

		log.exiting(CLASS_NAME, "PrintCommand exit");
		invocationContext.target = getTargetPage();
	}




	/**
	 * @author A-3447 
	 * This method is used to get the one time details
	 * @return Map
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(CCATYPE_ONETIME);
			fieldValues.add(CCASTATUS_ONETIME);
			fieldValues.add(MALCATCOD_ONETIME);
			fieldValues.add(MALSUBCLS_ONETIME);
			fieldValues.add(ISSUINGPARTY_ONETIME);
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
