/**
 * ScreenLoadCommand Created java on January 20, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.returnmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.ReturnDispatchSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.ReturnDispatchForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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
	private static final String SCREEN_ID = "mailtracking.defaults.national.return";
	private static final String RETURN_REASONCODE = "mailtracking.defaults.return.reasoncode";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ScreenLoadReturnCommand", "execute");

		ReturnDispatchSession returnSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		ReturnDispatchForm returnForm = (ReturnDispatchForm)invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<String> fieldTypes 		= new ArrayList<String>();
		fieldTypes.add(RETURN_REASONCODE);
		try {
			oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(logonAttributes.getCompanyCode(), fieldTypes);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
			businessDelegateException.getMessageVO().getErrors();
			handleDelegateException(businessDelegateException);
		}
		// assignMailBagForm.setDisableStatus("Y");

		Collection<DSNVO> dsnvos = returnSession.getSelectedDSNVO();
		for(DSNVO dsnvo : dsnvos){
			returnForm.setPieces(String.valueOf(dsnvo.getBags()));
			returnForm.setWeight((String.valueOf(dsnvo.getWeight())));			
			//returnForm.setReturnRemarks(dsnvo.getRemarks());
		}
		returnForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//	  	   assignMailBagSession.removeFilterVO(KEY_FILTER_ASSIGNMAILBAG);
		//	  	   assignMailBagSession.removeAssignMagVOS(ASSIGNMAILBAGVOS);

		returnSession.setFlightValidationVO(null);
		returnSession.setOneTimeVOs((HashMap<String, Collection<OneTimeVO>>) oneTimeValues);
		invocationContext.target = SCREENLOAD_SUCCESS;	
		log.exiting("ScreenLoadCommand", "execute");


	}
}
