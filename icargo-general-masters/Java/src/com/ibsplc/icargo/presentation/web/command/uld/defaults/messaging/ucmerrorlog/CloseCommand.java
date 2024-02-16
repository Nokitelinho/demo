/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog.CloseCommand.java
 *
 *	Created by	:	A-7359
 *	Created on	:	06-Nov-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog;


import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.UCMErrorLogSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.UCMErrorLogForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.ucmerrorlog.CloseCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7359	:	06-Nov-2017	:	Draft
 */
public class CloseCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("UCM Error Log");

	private static final String SCREENID = "uld.defaults.ucmerrorlog";

	private static final String MODULE_NAME = "uld.defaults";
	
	private static final String CLOSE_SUCCESS= "close_success";
	
	private static final String SENDUCM_SUCCESS= "senducm_success";
   /**
    * 
    *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
    *	Added by 			: A-7359 on 06-Nov-2017
    * 	Used for 	:
    *	Parameters	:	@param invocationContext
    *	Parameters	:	@throws CommandInvocationException
    */
	 public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	 
		 
		 ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
	    	UCMErrorLogForm form = (UCMErrorLogForm) invocationContext.screenModel;
	    	UCMErrorLogSession ucmErrorLogSession = getScreenSession(MODULE_NAME, SCREENID);
	    	//Modifed by A-7359 for ICRD-225848
	    	if((("fromSendUCMInOut").equals(ucmErrorLogSession.getPageURL()) && ucmErrorLogSession
					.getFlightFilterMessageVOSession() != null)){
				ucmErrorLogSession.setPageURL("fromucmerrorlog");
				invocationContext.target = SENDUCM_SUCCESS;
				return;
	    	}
	    	ucmErrorLogSession.removeAllAttributes();
			invocationContext.target = CLOSE_SUCCESS;
	 }
}
