/*
 * AddCommand.java Created on Jan 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn66;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN66Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CN66DetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-2408
 *
 */
public class AddCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("AirlineBilling CloseCommand");

	private static final String CLASS_NAME = "AddCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn66";
	private static final String SCREEN_SUCCESS = "screenload_success";
	//private static final String EMPTY_STRING="";
	private static final String SCREENSTATUS_ADD="add";

	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	CaptureCN66Session session=(CaptureCN66Session)getScreenSession(MODULE_NAME, SCREEN_ID);
    	CN66DetailsForm form=(CN66DetailsForm)invocationContext.screenModel;
    	
    	session.removeCn66Details();
    	
    	form.setScreenStatus(SCREENSTATUS_ADD);
    	
    	
    	
   		invocationContext.target = SCREEN_SUCCESS;	// sets target
		log.exiting(CLASS_NAME,"execute");
    	
    	
    	
    	
    }
}