/*
 * ScreenLoadCommand.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.damagechecklistmaster;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageChecklistMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageChecklistMasterForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 * 
 */
public class ScreenLoadCommand extends BaseCommand {
	private Log log = LogFactory
			.getLogger("ScreenLoadDamageChecklistMasterCommand");

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String SCREENID = "uld.defaults.damagechecklistmaster";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String ULD_SECTION = "uld.defaults.section";
	private static final String ULD_TOTALPOINTS = "uld.defaults.totalpointstomakeuldunserviceable";
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
		DamageChecklistMasterForm damageChecklistMasterForm = (DamageChecklistMasterForm)invocationContext.screenModel;
		DamageChecklistMasterSession damageChecklistMasterSession = (DamageChecklistMasterSession) getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<String> oneTimeToModes = new ArrayList<String>();
		oneTimeToModes.add(ULD_SECTION);

		String companyCode = logonAttributes.getCompanyCode();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeToModeMap = null;
		try {
			oneTimeToModeMap = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, oneTimeToModes);
			damageChecklistMasterSession
					.setSection((ArrayList<OneTimeVO>) oneTimeToModeMap
							.get(ULD_SECTION));

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}

		Map<String,String> systemParameters=null;
		Collection<String> parameterCodes=new ArrayList<String>();
		parameterCodes.add(ULD_TOTALPOINTS);
		
		try{
		
			systemParameters=new SharedDefaultsDelegate().findSystemParameterByCodes(parameterCodes);
			String totalPoints=systemParameters.get(ULD_TOTALPOINTS);
			damageChecklistMasterForm.setTotalPoints(totalPoints);
			log.log(Log.FINE, "damageChecklistMasterForm.getTotalPoints=",
					damageChecklistMasterForm.getTotalPoints());
			
		}

		catch(BusinessDelegateException businessDelegateException){
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}


		log.exiting("ScreenLoadCommand", "execute");
		damageChecklistMasterForm.setStatusFlag("screenload_success");
		damageChecklistMasterForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		damageChecklistMasterSession.removeULDDamageChecklistVO();
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

}
