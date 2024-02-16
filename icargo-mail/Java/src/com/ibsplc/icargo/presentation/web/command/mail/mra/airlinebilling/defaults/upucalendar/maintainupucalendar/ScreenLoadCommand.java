/*
 * ScreenLoadCommand.java Created on Sep 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.upucalendar.maintainupucalendar;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 *
 */
public class ScreenLoadCommand extends BaseCommand {
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID =
		"mailtracking.mra.airlinebilling.defaults.upucalendar";
	
	private static final String SCREENLOADETAILS_SUCCESS = "screenload_success";
	
	private static final String CLASS_NAME = "ScreenLoadCommand";
	
	/**
	 * Method  implementing screen load of upu calendar details
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException{
		
		Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");
		log.entering(CLASS_NAME, "execute");
		
		Collection<UPUCalendarVO> upuCalendarVOs 	= null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		//UPUCalendarForm upuCalendarForm 	= (UPUCalendarForm)invocationContext.screenModel;
		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession( MODULE_NAME, SCREENID );
		
		upuCalendarVOs = new ArrayList<UPUCalendarVO>();
		upuCalendarSession.setUPUCalendarVOs(upuCalendarVOs);
		invocationContext.addAllError(errors);
		//upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOADETAILS_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
	}
}

