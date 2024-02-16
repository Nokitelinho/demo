/*
 * CloseCommand.java created on Mar 1, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.   
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listbillingmatrix;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListBillingMatrixSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class CloseCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.listbillingmatrix";

	private static final String CLOSE_SUCCESS = "close_success";
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
		ListBillingMatrixSession listBillingMatrixSession=getScreenSession(MODULE_NAME,SCREENID);
		listBillingMatrixSession.removeAllAttributes();
		invocationContext.target=CLOSE_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
		
	}

}