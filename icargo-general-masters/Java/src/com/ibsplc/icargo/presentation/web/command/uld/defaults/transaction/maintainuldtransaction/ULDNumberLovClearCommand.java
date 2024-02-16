/*
 * ULDNumberLovClearCommand.java
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.maintainuldtransaction;

//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.LoanBorrowULDSession;
import  com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ULDNumberLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2886
 * 
 */
public class ULDNumberLovClearCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("LOV clear command");

	private static final String ULDNUMBERLOV_SUCCESS="uldNumberLovClear_success";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = "uld.defaults.loanborrowuld";

	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ULDNumberLovForm uldNumberLovForm= (ULDNumberLovForm)invocationContext.screenModel;

		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		//		Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes =  applicationSessionImpl.getLogonVO();
		LoanBorrowULDSession loanBorrowULDSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);


		uldNumberLovForm.setUldNumbers(null);
		uldNumberLovForm.setCode(null);


		invocationContext.target =ULDNUMBERLOV_SUCCESS;
	}
}
