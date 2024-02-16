/*
 * ClearCommand.java created on APR 2, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listmailcontracts;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListMailContractsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailContractsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1946
 *
 */
public class ClearCommand extends BaseCommand{

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID =
		"mailtracking.mra.defaults.listmailcontracts";

	private static final String SCREEN_SUCCESS = "clear_success";
	
	private static final String BLANK = "";

	
	
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
		ListMailContractsForm form=(ListMailContractsForm)invocationContext.screenModel;
		ListMailContractsSession  session=	(ListMailContractsSession)getScreenSession(
				MODULE_NAME, SCREENID);	
		
		session.removeMailContractVOs();
		form.setAgreementStatus(BLANK);
		form.setAgreementType(BLANK);
		form.setAirlineCode(BLANK);
		form.setContractDate(BLANK);
		form.setContractRefNo(BLANK);
		form.setFromDate(BLANK);
		form.setPaCode(BLANK);
		form.setToDate(BLANK);
		form.setVersionNumberFilter("LATEST");
		
		invocationContext.target=SCREEN_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}	
}