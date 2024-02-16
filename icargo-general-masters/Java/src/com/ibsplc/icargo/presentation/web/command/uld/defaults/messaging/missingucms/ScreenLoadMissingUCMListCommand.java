/*
 * ScreenLoadMissingUCMListCommand.java Created on Jul 30, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.missingucms;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MissingUCMListSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MissingUCMListForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ScreenLoadMissingUCMListCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("ScreenLoadMissingUCMListCommand");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String SCREENID = "uld.defaults.missingucmlist";
    private static final String MODULE_NAME = "uld.defaults";
    /**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
	log.entering("ScreenLoadMissingUCMListCommand", "execute");
    MissingUCMListForm missingUCMListForm = (MissingUCMListForm)invocationContext.screenModel;
    MissingUCMListSession missingUCMListSession = (MissingUCMListSession)getScreenSession(MODULE_NAME, SCREENID);
    missingUCMListSession.removeULDFlightMessageReconcileDetailsVOs();
    missingUCMListSession.removeAllAttributes();
    invocationContext.target = SCREENLOAD_SUCCESS;

	}
}
