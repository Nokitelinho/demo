/*
 * ClearCommand.java Created on Feb 8, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.copyrate;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyRateForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA DEFAULTS CLEARCOMMAND");
	private static final String CLASS_NAME = "ClearCommand";

	/*private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.copyrate";*/
	private static final String CLEAR_SUCCESS="clear_success";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		 CopyRateForm copyRateForm=(CopyRateForm)invocationContext.screenModel;
		 copyRateForm.setRateCardId("");
		 copyRateForm.setValidFrom("");
		 copyRateForm.setValidTo("");
		 copyRateForm.setScreenMode("");
		 copyRateForm.setScreenFlag("");
		 copyRateForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		 invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}


}
