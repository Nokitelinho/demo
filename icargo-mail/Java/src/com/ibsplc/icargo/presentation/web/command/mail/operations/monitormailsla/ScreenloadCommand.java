/*
 * ScreenloadCommand.java Created on Mar 30, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.monitormailsla;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MonitorMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MonitorMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2122
 *
 */
public class ScreenloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String CLASS_NAME = "ScreenloadCommand";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.monitormailsla";
	private static final String SCREENLOAD_SUCCESS="screenload_success";
	
	private static final String CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String ACTIVITY_ONETIME = "mailtracking.mra.slaactivity";
	private static final String STATUS_ONETIME = "mailtracking.defaults.slastatus";
	
	
	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * Execute method
	 *
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");

		MonitorMailSLAForm monitorMailSLAForm=(MonitorMailSLAForm)invocationContext.screenModel;
		MonitorMailSLASession monitorMailSLASession = (MonitorMailSLASession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		SharedDefaultsDelegate sharedDefaultsDelegate = new
		  SharedDefaultsDelegate(); 
		ApplicationSessionImpl applicationSession = getApplicationSession();
		  LogonAttributes logonAttributes = applicationSession.getLogonVO();
		  Map<String, Collection<OneTimeVO>>
		  oneTimeValues = null; 
		  try {
		 
		  oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
		  logonAttributes.getCompanyCode(), getOneTimeParameterTypes());
		   } catch (BusinessDelegateException businessDelegateException) {
			  log.log(Log.FINE, "*****in the exception");
			  businessDelegateException.getMessageVO().getErrors(); 
			  handleDelegateException(businessDelegateException);
		  }
		   monitorMailSLASession.setCategories(
					(ArrayList<OneTimeVO>) oneTimeValues.get(CATEGORY_ONETIME));
		   monitorMailSLASession.setActivities(
					(ArrayList<OneTimeVO>) oneTimeValues.get(ACTIVITY_ONETIME));
		   monitorMailSLASession.setStatus(
					(ArrayList<OneTimeVO>) oneTimeValues.get(STATUS_ONETIME));
		   monitorMailSLASession.setFilterVO(null);
		   monitorMailSLASession.setMailSlaDetails(null);
		   
		monitorMailSLAForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}	
	/**
	 * 
	 * @return Collection<String>
	 */
	private Collection<String> getOneTimeParameterTypes() {

		ArrayList<String> parameterTypes = new ArrayList<String>();
		parameterTypes.add(CATEGORY_ONETIME);
		parameterTypes.add(ACTIVITY_ONETIME);
		parameterTypes.add(STATUS_ONETIME);
		return parameterTypes;
	}

}
