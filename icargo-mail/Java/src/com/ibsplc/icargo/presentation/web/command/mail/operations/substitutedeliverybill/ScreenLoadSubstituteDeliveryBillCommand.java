/*
 * ScreenLoadSubstituteDeliveryBillCommand.java Created on Jun 30 2009
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
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SubstituteDeliveryBillGenForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3217
 *
 */
public class ScreenLoadSubstituteDeliveryBillCommand extends
		BaseCommand {

	private Log log = LogFactory
			.getLogger("SUBSTITUTE DELIVERY BILL GENERATION");

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";

	private static final String SUBSTITUTEDELIVERYBILLGEN_SCREENLOAD_SUCCESS = "substitutescreenload_success";
	
	private static final String OUTBOUND = "O";

	/**
	 * @author A-3217
	 * @throws CommandInvocationException
	 * @param invocationContext
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("ScreenLoadSubstituteDeliveryBillGenerationCommand","execute");

	/*	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/

		SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm = (SubstituteDeliveryBillGenForm) invocationContext.screenModel;
		SubstituteDeliveryBillGenSession substituteDeliveryBillGenSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		substituteDeliveryBillGenForm.setRadioInboundOutbound(OUTBOUND);
		substituteDeliveryBillGenSession.removeAllAttributes();

		invocationContext.target = SUBSTITUTEDELIVERYBILLGEN_SCREENLOAD_SUCCESS;

		log.exiting("ScreenLoadSubstituteDeliveryBillGenerationCommand","execute");

	}

}
