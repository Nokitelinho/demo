/*
 * ScreenLoadSCMValidationCommand.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmvalidation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMValidationSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMValidationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ScreenLoadSCMValidationCommand extends BaseCommand{
	
	private Log log = LogFactory
	.getLogger("ScreenLoadDamageChecklistMasterCommand");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String SCREENID = "uld.defaults.scmvalidation";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String FACILITYTYPE_ONETIME = "uld.defaults.facilitytypes";
	private static final String ULD_SCMSTATUS = "uld.defaults.scmstatus";
/**
* execute method for handling the close action and closing the session
* 
* @param invocationContext
* @throws CommandInvocationException
*/

public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
	log.entering("ScreenLoadCommand", "execute");
	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	SCMValidationForm scmValidationForm = (SCMValidationForm)invocationContext.screenModel;
	SCMValidationSession scmValidationSession = (SCMValidationSession) getScreenSession(
			MODULE_NAME, SCREENID);
	Collection<String> oneTimeToModes = new ArrayList<String>();
	oneTimeToModes.add(FACILITYTYPE_ONETIME);
	oneTimeToModes.add(ULD_SCMSTATUS);
	String companyCode = logonAttributes.getCompanyCode();
	SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	Map<String, Collection<OneTimeVO>> oneTimeToModeMap = null;
	try {
		oneTimeToModeMap = sharedDefaultsDelegate.findOneTimeValues(
				companyCode, oneTimeToModes);
		
	} catch (BusinessDelegateException businessDelegateException) {
		handleDelegateException(businessDelegateException);
		businessDelegateException.getMessage();
	}
	Collection<OneTimeVO> facilitytype=(Collection<OneTimeVO>) oneTimeToModeMap.get(FACILITYTYPE_ONETIME);
	log.log(Log.FINE, "onetimes---->", facilitytype);
	Collection<OneTimeVO> scmStatus=(Collection<OneTimeVO>) oneTimeToModeMap.get(ULD_SCMSTATUS);
	scmValidationSession.setFacilityType(facilitytype);
	scmValidationSession.setScmStatus(scmStatus);
	scmValidationForm.setTotal("");
	scmValidationForm.setNotSighted("");
	scmValidationForm.setMissing("");
	scmValidationForm.setStatusFlag("screenload_success");
	scmValidationSession.removeSCMValidationVOs();
	scmValidationForm
			.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	
	invocationContext.target = SCREENLOAD_SUCCESS;
	
}
	

}
