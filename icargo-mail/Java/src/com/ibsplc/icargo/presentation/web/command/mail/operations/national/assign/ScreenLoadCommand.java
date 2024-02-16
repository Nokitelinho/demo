/**
 * ScreenLoadCommand.java Created on January 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ScreenLoadCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mail.operations";
	private static final String CATEGORY = "mailtracking.defaults.mailcategory";
	private static final String FLIGHTSTATUS = "mailtracking.defaults.flightstatus";
	private static final String OPERATIONALSTATUS = "flight.operation.flightlegstatus";
	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadCommand", "execute");
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignMailBagForm assignMailBagForm = (AssignMailBagForm)invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = null;
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> fieldTypes = new ArrayList<String>();
		fieldTypes.add(FLIGHTSTATUS);
		fieldTypes.add(CATEGORY);
		fieldTypes.add(OPERATIONALSTATUS);
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		assignMailBagSession.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		assignMailBagForm.setDeparturePort(logonAttributes.getStationCode());
		assignMailBagForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
		/*MailManifestVO mailManifestVO = new MailManifestVO();
		assignMailBagSession.setMailManifestVO(mailManifestVO);
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		assignMailBagSession.setFlightValidationVO(flightValidationVO);*/
		// assignMailBagForm.setDisableStatus("Y");
		assignMailBagForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

		//assignMailBagSession.removeAssignMagVOS(ASSIGNMAILBAGVOS);
		assignMailBagSession.setFlightValidationVO(null);
		assignMailBagSession.setMailManifestVO(null);


		invocationContext.target = SCREENLOAD_SUCCESS;	
		log.exiting("ScreenLoadCommand", "execute");
	}

}
