/* ClearloadAgreementNoLovCommand.java Created on May 10,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.agreementnolov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.AgreementNoLovForm;

/**
 * 
 * @author A-1819
 * 
 */
public class ClearloadAgreementNoLovCommand extends BaseCommand {

	private static final String CLEAR_SUCCESS = "clear_success";

	private static final String MODULE = "uld.defaults";

	private static final String BLANK = "";

	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		AgreementNoLovForm agreementNoLovForm = (AgreementNoLovForm)invocationContext.screenModel;		
		agreementNoLovForm.setAgreementNo(BLANK);
		//added by a-3045 for CR QF1154 starts
		agreementNoLovForm.setPartyCode(BLANK);
		agreementNoLovForm.setPartyName(BLANK);
		//added by a-3045 for CR QF1154 ends
		agreementNoLovForm.setDisplayPage("1");
		agreementNoLovForm.setPageAgreementLov(null);
		invocationContext.target=CLEAR_SUCCESS;

	}
}
