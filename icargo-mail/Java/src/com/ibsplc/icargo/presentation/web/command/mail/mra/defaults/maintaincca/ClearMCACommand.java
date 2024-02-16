/**
 *ClearMCACommand.java Created on May 25-2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MCALovForm;

/**
 * @author a-4823
 *
 */
public class ClearMCACommand extends BaseCommand{
	private static final String CLASS_NAME = "FindMCALovCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.mcalov";

	private static final String SCREEN_SUCCESS = "screenload_success";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MCALovForm mcaLovForm = (MCALovForm) invocationContext.screenModel;
		mcaLovForm.setMcaLovPage(null);
		mcaLovForm.setCategory(null);
		mcaLovForm.setCcaNum(null);
		mcaLovForm.setDestination(null);
		mcaLovForm.setDsnNumber(null);
	
		mcaLovForm.setOrigin(null);
		mcaLovForm.setSubclass(null);
		mcaLovForm.setYear(null);
		
		mcaLovForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target=SCREEN_SUCCESS;
	}
}
