/*
 * ClearSccLovCommand.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.common;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;

import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SccLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Command to clear SCC LOV
 * @author A-1754
 *
 */

public class ClearSccLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("ClearSccLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearSccLovCommand","execute");
		SccLovForm sccLovActionForm = (SccLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		sccLovActionForm.setSccCode("");
		sccLovActionForm.setSccDescription("");
		sccLovActionForm.setDisplayPage("1");
		session.removeSccLovVOs();
		session.removeALLSccLovVOs();
		session.setAllSccLovVOs(null);
		invocationContext.target = "screenload_success";
		log.exiting("ClearSccLovCommand","execute");
		
	}
	
	
}