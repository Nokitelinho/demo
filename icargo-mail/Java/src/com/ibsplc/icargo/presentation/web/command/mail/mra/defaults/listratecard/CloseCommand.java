/*
 * CloseCommand.java Created on Jan 10, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listratecard;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListUPURateCardSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListUPURateCardForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * @author A-2521
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listupuratecard";

	private static final String CLASS_NAME = "CloseCommand";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String BLANK = "";


	/**
	 * Method that clears objects from sessioon and form
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	ListUPURateCardForm form = (ListUPURateCardForm)invocationContext.screenModel;

		ListUPURateCardSession session =
			(ListUPURateCardSession)getScreenSession(MODULE_NAME, SCREENID);

		// clearing vos in session
		session.removeAllAttributes();

		form.setRateCardID(BLANK);
		form.setRateCardStatus(BLANK);
		form.setStartDate(BLANK);
		form.setEndDate(BLANK);

		invocationContext.target = CLOSE_SUCCESS;

    }

}
