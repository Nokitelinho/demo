/*
 * ClearCopySLACommand.java Created on April 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 *
 */
public class ClearCopySLACommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "ClearCopySLACommand";
	private static final String ACTION_SUCCESS="action_success";	

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
			
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		maintainMailSLAForm.setSlaIdrForPopup(null);
		maintainMailSLAForm.setSlaDetailsVos(null);	
		invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");


	}
}
