/*
 * CloseMaintainProductCommand.java Created on Oct 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.products.defaults.maintainproduct;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.MaintainProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.MaintainProductForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class CloseMaintainProductCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("CloseMaintainProductCommand");

	/**
	 * The Module Name
	 */


	private static final String MODULE = "product.defaults";

	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLOSEMAINTAINPRODUCT_SUCCESS = "close_maintainproduct_success";
	private static final String CLOSE_MAINTAINPRODUCT_SUCCESS = "close_maintainproduct_screen_success";

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("CloseMaintainProductCommand", "CloseCommand");
		MaintainProductForm maintainProductForm = (MaintainProductForm) invocationContext.screenModel;

		MaintainProductSessionInterface session = getScreenSession(
				"product.defaults", "products.defaults.maintainproduct");
		log.log(Log.FINE, " maintainProductForm.getFromListProduct()",
				maintainProductForm.getFromListProduct());
		log.log(Log.FINE, " maintainProductForm.getFromListProduct()", session.getButtonStatusFlag());
		if (("listproductmode").equals(session.getButtonStatusFlag())) {
			log.log(Log.FINE, "\n\n\n11111111111111111111111", session.getButtonStatusFlag());
			invocationContext.target = CLOSEMAINTAINPRODUCT_SUCCESS;
			return;
		}else if (("screenloadmode").equals(session.getButtonStatusFlag())) {
			log.log(Log.FINE, "\n\n\n11111111111111111111111", session.getButtonStatusFlag());
			invocationContext.target = CLOSE_MAINTAINPRODUCT_SUCCESS;
			return;
		}

		else{
			invocationContext.target = CLOSE_SUCCESS;
		    	return;
		}
	}
}
