/*
 * CloseCommand.java Created on apr 23,2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.fuelsurcharge;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.FuelSurchargeSession;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2391
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.fuelsurcharge";
	
	private static final String CLOSE_SUCCESS = "close_success";
	
	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    		FuelSurchargeSession session=getScreenSession(MODULE_NAME,SCREENID);
			session.removeAllAttributes();
			invocationContext.target = CLOSE_SUCCESS;
			log.exiting(CLASS_NAME,"execute"); 
    }

    }
