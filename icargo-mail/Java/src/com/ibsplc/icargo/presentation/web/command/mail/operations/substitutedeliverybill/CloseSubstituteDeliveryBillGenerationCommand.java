/*
 * CloseSubstituteDeliveryBillGenerationCommand.java Created on Jul 01 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.substitutedeliverybill;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SubstituteDeliveryBillGenSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3217
 *
 */
public class CloseSubstituteDeliveryBillGenerationCommand extends BaseCommand{

private Log log = LogFactory.getLogger("SUBSTITUTE DELIVERY BILL GENERATION");

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";

	private static final String CLOSESUBSTITUEDELIVERYBILLGEN_SUCCESS = "substitutelist_close";


	/**
	 * @author A-3217
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {

		log.entering("CloseSubstituteDeliveryBillGenerationCommand", "execute");

		SubstituteDeliveryBillGenSession substituteDeliveryBillGenSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		substituteDeliveryBillGenSession.removeAllAttributes();

		invocationContext.target = CLOSESUBSTITUEDELIVERYBILLGEN_SUCCESS;

		log.exiting("CloseSubstituteDeliveryBillGenerationCommand", "execute");
	}

}
