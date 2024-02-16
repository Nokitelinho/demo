/* * ClearCommand.java Created on Nov 21, 2006 * * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. * * This software is the proprietary information of IBS Software Services (P) Ltd. * Use is subject to license terms. */package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.clearanceperiod;

import com.ibsplc.icargo.framework.web.command.BaseCommand;import com.ibsplc.icargo.framework.web.command.CommandInvocationException;import com.ibsplc.icargo.framework.web.command.InvocationContext;import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ClearancePeriodLovForm;import com.ibsplc.xibase.util.log.Log;import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * * * @author a-2521 * */
public class ClearCommand  extends BaseCommand {	/* For setting the Target action*/
    private static final String CLEAR_SUCCESS = "clear_success";
    private static final String CLASS_NAME = "ClearCommand";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)    			throws CommandInvocationException {
    		Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");    		log.entering(CLASS_NAME, "execute");
    		ClearancePeriodLovForm clearancePeriodLovForm 	=     				(ClearancePeriodLovForm)invocationContext.screenModel;    		     		clearancePeriodLovForm.setUpucalendarVOs(null);    		clearancePeriodLovForm.setCode(null);        	clearancePeriodLovForm.setFromDateLst(null);        	clearancePeriodLovForm.setToDateLst(null);        	log.exiting(CLASS_NAME,"execute"); 			//        	clearancePeriodLovForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = CLEAR_SUCCESS;
	}
}