/*
 * ClearULDHistoryCommand.java Created on Oct 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldhistory;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDHistoryForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDHistorySession;

/**
 * 
 * @author A-2619
 * 
 */

public class ClearULDHistoryCommand extends BaseCommand {

	private Log log;

	private static final String SCREEN_ID = "uld.defaults.uldhistory";

	private static final String MODULE_NAME = "uld.defaults";

	private static final String CLEAR_SUCCESS = "uldhistory_clear_success";

	public ClearULDHistoryCommand() {
		log = LogFactory.getLogger("ClearULDHistoryCommand");
	}

	/**
	 * @author A-2619
	 * 
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearULDHistoryCommand", "execute");
		ULDHistoryForm uldHistoryForm = (ULDHistoryForm) invocationContext.screenModel;
		uldHistoryForm
		.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		ULDHistorySession uldHistorySession = getScreenSession(MODULE_NAME,
				SCREEN_ID);

		uldHistoryForm.setUldNumber("");
		uldHistoryForm.setFromDate("");
		uldHistoryForm.setToDate("");
		uldHistoryForm.setCarrierCode("");
		uldHistoryForm.setFlightNumber("");
		uldHistoryForm.setFlightDate("");
		uldHistoryForm.setFromStation("");
		uldHistoryForm.setStatus("Select");
		uldHistorySession.setUldHistoryVOs(null);
		log.log(log.FINE, "Session while Clear=====>>"
				+ uldHistorySession.getUldHistoryVOs());

		invocationContext.target = CLEAR_SUCCESS;
		return;
	}

}
