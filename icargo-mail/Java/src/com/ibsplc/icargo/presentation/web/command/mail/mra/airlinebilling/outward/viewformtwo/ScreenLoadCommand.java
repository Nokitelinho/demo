/*
 * ScreenLoadCommand.java Created on July 22, 2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.viewformtwo;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.outward.ViewFormTwoSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.ViewMailFormTwoForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3456
 * 
 */
public class ScreenLoadCommand  extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING OUTWARD");
	private static final String CLASS_NAME = "ScreenLoadCommand";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE_NAME = "mra.airlinebilling";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.viewform2";
	
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

		ViewMailFormTwoForm form = (ViewMailFormTwoForm)invocationContext.screenModel;
		ViewFormTwoSession viewForm2Session = (ViewFormTwoSession)getScreenSession(MODULE_NAME, SCREENID);
		viewForm2Session.removeAirlineForBillingVOs();
		form.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
		
		log.exiting(CLASS_NAME, "execute");
	}


}
