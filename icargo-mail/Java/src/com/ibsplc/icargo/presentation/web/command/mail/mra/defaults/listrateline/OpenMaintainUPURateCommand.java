/*
 * OpenMaintainUPURateCommand.java created on Feb 9, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listrateline;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2280
 *
 */
public class OpenMaintainUPURateCommand extends BaseCommand{

private  Log log = LogFactory.getLogger("MRA DEFAULTS");


   private static final String CLASS_NAME = "ChangeStatusCommand";

	//private static final String MODULE_NAME = "mailtracking.mra.defaults";

	//private static final String SCREENID = "mailtracking.mra.defaults.viewupurate";
	/**
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		  log.entering(CLASS_NAME,"execute");

	    	//ListUPURateLineForm listUPURateLineForm = (ListUPURateLineForm)invocationContext.screenModel;
	    	invocationContext.target="open_maintainupurate";
	    	log.exiting(CLASS_NAME,"execute");

	}

}
