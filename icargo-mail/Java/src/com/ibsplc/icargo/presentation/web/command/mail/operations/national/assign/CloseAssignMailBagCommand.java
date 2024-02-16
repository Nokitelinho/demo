/**
 * 
 * CloseAssignMailBagCommand Created on January 12, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.national.assign;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
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
public class CloseAssignMailBagCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.national.assignmailbag";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("Close  Command", "execute");
		AssignMailBagSession assignMailBagSession  = getScreenSession(MODULE_NAME, SCREEN_ID);
		AssignMailBagForm assignMailBagForm =(AssignMailBagForm)invocationContext.screenModel;	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		assignMailBagForm.setFlightCarrierCode(logonAttributes.getOwnAirlineCode());
		assignMailBagForm.setFlightNo("");
		assignMailBagForm.setFlightDate("");   
		assignMailBagForm.setOperationalStatus("");
		assignMailBagSession.setFlightValidationVO(null);
		assignMailBagSession.setAssignMailBagVOs(null);
		assignMailBagSession.setOperationalFlightVO(null);
		assignMailBagSession.setMailManifestVO(null);    	
		assignMailBagSession.removeAllAttributes();
		invocationContext.target = CLOSE_SUCCESS;	
		log.exiting("Close  Command", "execute");

	}

}
