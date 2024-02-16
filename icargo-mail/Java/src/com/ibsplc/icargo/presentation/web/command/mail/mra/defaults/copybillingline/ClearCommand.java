/*
 * ClearCommand.java Created on Mar 22, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.copybillingline;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.CopyBlgLineForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 * 
 */
public class ClearCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS COPYBLGLINE");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		CopyBlgLineForm copyBlgLineForm = (CopyBlgLineForm) invocationContext.screenModel;
		copyBlgLineForm.setBlgMatrixId("");
		copyBlgLineForm.setValidFrom("");
		copyBlgLineForm.setValidTo("");
		copyBlgLineForm.setScreenMode("");
		copyBlgLineForm.setScreenFlag("");
		copyBlgLineForm
				.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}
