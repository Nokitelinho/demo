/**
 * ClearCommand.java Created on January 13, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.AssignMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.AssignMailBagForm;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");	
	private static final String SCREENID = "mailtracking.defaults.national.assignmailbag";	
	private static final String MODULE_NAME = "mail.operations";
	private static final String CLEAR_SUCCESS = "clear_success";
	/**
	 This method is used to execute the command
	 * @param invocationContext - InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("ClearCommand", "execute"); 
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREENID);
		AssignMailBagForm assignMailBagForm =(AssignMailBagForm)invocationContext.screenModel;	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		assignMailBagForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
		assignMailBagForm.setFlightNo("");
		assignMailBagForm.setFlightDate("");   
		assignMailBagForm.setOperationalStatus("");
		//    	assignMailBagSession.removeFilterVO(KEY_FILTER_ASSIGNMAILBAG);
		//    	assignMailBagSession.removeAssignMagVOS(ASSIGNMAILBAGVOS);
		//    	assignMailBagSession.removePous(POUS);    	
		assignMailBagSession.setFlightValidationVO(null);
		assignMailBagSession.setAssignMailBagVOs(null);
		assignMailBagSession.setOperationalFlightVO(null);
		assignMailBagSession.setMailManifestVO(null);

		//assignMailBagForm.setDisableStatus("Y");

		assignMailBagForm.setScreenStatusFlag(ComponentAttributeConstants.
				SCREEN_STATUS_SCREENLOAD);

		invocationContext.target = CLEAR_SUCCESS;
		log.exiting("ClearCommand", "execute");


	}

}
