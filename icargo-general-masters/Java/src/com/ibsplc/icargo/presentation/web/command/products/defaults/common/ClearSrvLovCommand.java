/*
 * ClearSrvLovCommand.java Created on Jun 27, 2005
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
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ServiceLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1754
 *
 */

public class ClearSrvLovCommand extends BaseCommand {

	
	private Log log = LogFactory.getLogger("ClearSrvLovCommand");
	/**
	 * Overriding the execute method of BaseCommand
	 * @param invocationContext
	 * @author A-1754
	 * @throws CommandInvocationException
	 */	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ClearSrvLovCommand","execute");
		ServiceLovForm serviceLovForm = (ServiceLovForm)invocationContext.screenModel;
		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		serviceLovForm.setServiceCode("");
		serviceLovForm.setServiceDescription("");
		
		serviceLovForm.setDisplayPage("1");
		session.setServiceLovVOs(null);
		session.setAllServiceLovVOs(null);
		session.removeALLServiceLovVOs();
		invocationContext.target = "screenload_success";
		log.exiting("ClearSrvLovCommand","execute");
		
	}
	
	
}