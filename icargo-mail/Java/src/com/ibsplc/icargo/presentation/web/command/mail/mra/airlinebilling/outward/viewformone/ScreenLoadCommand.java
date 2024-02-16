/*
 * ScreenLoadCommand.java Created on June 19, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformone;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormOneSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormOneForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3456
 * 
 */
public class ScreenLoadCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING OUTWARD");
	private static final String CLASS_NAME = "ScreenLoadCommand";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	/**
	 *SCREENID
	 * 
	 */
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform1";
	/**
	 * module name
	 * 
	 */
	private static final String MODULE = "mra.airlinebilling";
	
	/**
	 *
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		ViewMailFormOneForm form = (ViewMailFormOneForm)invocationContext.screenModel;
		ViewFormOneSession session = (ViewFormOneSession) getScreenSession(
				MODULE, SCREENID);
		session.removeFormOneVO();			
		session.removeInvoiceFormOneDetailsVOs();			
		invocationContext.target = SCREENLOAD_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
		
	}

}
