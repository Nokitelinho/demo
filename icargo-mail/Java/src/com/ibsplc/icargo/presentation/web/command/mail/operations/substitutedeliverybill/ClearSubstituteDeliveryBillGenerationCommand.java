/*
 * ClearSubstituteDeliveryBillGenerationCommand.java Created on Jun 30 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.substitutedeliverybill;

import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SubstituteDeliveryBillGenSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SubstituteDeliveryBillGenForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/*
 * @authorA-3217
 * ClearSubstituteDeliveryBillGenerationCommand
 * extends BaseCommand
 */
public class ClearSubstituteDeliveryBillGenerationCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("SUBSTITUTE DELIVERY BILL GENERATION");

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID = "mailtracking.defaults.substitutedeliverybill";

	private static final String CLEARSUBSTITUEDELIVERYBILLGEN_SUCCESS = "substitutelist_clear";

	private static final String BLANKSPACE = "";
	private static final String OUTBOUND = "O";

	/**
	 * @author A-3217
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {

		log.entering("ClearSubstituteDeliveryBillGenerationCommand", "execute");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		SubstituteDeliveryBillGenForm substituteDeliveryBillGenForm = (SubstituteDeliveryBillGenForm) invocationContext.screenModel;

		SubstituteDeliveryBillGenSession substituteDeliveryBillGenSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		substituteDeliveryBillGenSession.removeAllAttributes();

		substituteDeliveryBillGenForm.setCarrierCode(BLANKSPACE);
		substituteDeliveryBillGenForm.setFlightNumber(BLANKSPACE);
		substituteDeliveryBillGenForm.setDeparturePort(BLANKSPACE);
		substituteDeliveryBillGenForm.setArrivalPort(BLANKSPACE);
		substituteDeliveryBillGenForm.setDepartureDate(BLANKSPACE);
		substituteDeliveryBillGenForm.setArrivalDate(BLANKSPACE);
		substituteDeliveryBillGenForm.setRadioInboundOutbound(OUTBOUND);

		invocationContext.target = CLEARSUBSTITUEDELIVERYBILLGEN_SUCCESS;

		log.exiting("ClearSubstituteDeliveryBillGenerationCommand", "execute");
	}

}
