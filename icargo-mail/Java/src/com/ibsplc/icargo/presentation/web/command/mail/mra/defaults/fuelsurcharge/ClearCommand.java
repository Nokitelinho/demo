/*
 * ClearCommand.java Created on apr 23,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.fuelsurcharge;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.FuelSurchargeSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.FuelSurchargeForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Indu
 * Command class for clearing mailproration screen  
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1        Apr 23 			Indu		Initial draft
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA  DEFAULTS");
	
	private static final String CLASS_NAME = "ClearCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREENID = "mailtracking.mra.defaults.fuelsurcharge";

	private static final String CLEAR_SUCCESS = "clear_success";
	

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		FuelSurchargeSession session=getScreenSession(MODULE_NAME,SCREENID);
		
		FuelSurchargeForm form = (FuelSurchargeForm) invocationContext.screenModel;
		session.removeFuelSurchargeVOs();
		form.setGpaCode("");
		form.setGpaName("");
		form.setCountry("");
		
		form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		
		
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}