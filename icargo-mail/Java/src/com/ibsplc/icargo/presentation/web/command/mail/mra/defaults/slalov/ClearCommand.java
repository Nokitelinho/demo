/*
 * ClearCommand.java Created on Dec 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.slalov;


import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.SLALovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 *
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private final static String CLEAR_SUCCESS = "clear_success";
	
	private final static String BLANK = "";
		
	private static final String CLASS_NAME = "ClearCommand";
	
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		
		SLALovForm slaLovForm = (SLALovForm)invocationContext.screenModel;
		
		slaLovForm.setCode(BLANK);
		slaLovForm.setSlaDetailsVOs(null);
		
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target=CLEAR_SUCCESS;

	}

}
