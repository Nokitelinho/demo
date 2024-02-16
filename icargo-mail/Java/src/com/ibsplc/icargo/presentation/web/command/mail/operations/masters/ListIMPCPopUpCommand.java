/*
 * ListIMPCCodeCommand.java Created on June 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

/**
 * @author A-3108
 *
 */
public class ListIMPCPopUpCommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";
	//private Log log = LogFactory.getLogger("ListIMPCPopUPCommand");

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID =
							"mailtracking.defaults.masters.officeofexchange";

	private static final String NO_MATCH =
							"mailtracking.defaults.oemaster.msg.err.noMatch";

	private static final String IMPCCODE_EMPTY =
		"mailtracking.defaults.oemaster.msg.err.impccodeEmpty";
	private static final String PA_EMPTY =
		"mailtracking.defaults.oemaster.msg.err.paEmpty";

	private static final String EMPTY_STRING = "";

	private static final String ADD_STATUS = "ADD";

	private static final String UPDATE_STATUS = "UPDATE";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	

	}
}
