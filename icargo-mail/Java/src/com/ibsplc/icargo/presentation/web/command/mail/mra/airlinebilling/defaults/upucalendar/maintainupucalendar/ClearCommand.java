/* * ClearCommand.java Created on Sep 28, 2006 * * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. * * This software is the proprietary information of IBS Software Services (P) Ltd. * Use is subject to license terms. */package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.upucalendar.maintainupucalendar;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;import com.ibsplc.icargo.framework.web.command.BaseCommand;import com.ibsplc.icargo.framework.web.command.CommandInvocationException;import com.ibsplc.icargo.framework.web.command.InvocationContext;import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm;import com.ibsplc.xibase.util.log.Log;import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * * * @author a-2521 * */
public class ClearCommand  extends BaseCommand {

	/*
	 * The module name
	 */
    private static final String MODULE_NAME = "mailtracking.mra";
    /*
	 * The screen id
	 */
    private static final String SCREENID =    	"mailtracking.mra.airlinebilling.defaults.upucalendar";
    /* For setting the Target action*/
    private static final String CLEAR_SUCCESS = "clear_success";
    private static final String CLASS_NAME = "ClearCommand";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)    			throws CommandInvocationException {
    		Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");    		log.entering(CLASS_NAME, "execute");
    		UPUCalendarForm upuCalendarForm = (UPUCalendarForm)invocationContext.screenModel;    		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession(    															MODULE_NAME, SCREENID);    		/** removing VO colection from session */    		upuCalendarSession.removeUPUCalendarVOs();    		upuCalendarForm.setClrHsCodeLst("");        	upuCalendarForm.setFromDateLst("");        	upuCalendarForm.setToDateLst("");        	log.exiting(CLASS_NAME,"execute"); 			upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = CLEAR_SUCCESS;
	}
}