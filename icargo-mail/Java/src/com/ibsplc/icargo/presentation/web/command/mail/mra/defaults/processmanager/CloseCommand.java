/*
 * CloseCommand.java Created on jan 8, 2007
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.processmanager;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProcessManagerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */

public class CloseCommand  extends BaseCommand {
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	//private static final String MODULE_NAME = "cra.defaults";
	
	//private static final String SCREEN_ID = "cra.defaults.processmanager";
	
	private static final String CLOSE_SUCCESS = "close_success";

	private static final String CLASS_NAME = "CloseCommand";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
//added by a7531 for icrd-132487
		MRAProcessManagerForm processManagerForm = (MRAProcessManagerForm) invocationContext.screenModel;
		processManagerForm.setCarrierCode(null);
		processManagerForm.setFlightDate(null);
		processManagerForm.setFlightNumber(null);
		processManagerForm.setDestination(null);
		processManagerForm.setDestinationforProrate(null);
		processManagerForm.setDestinationOfficeOfExchange(null);
		processManagerForm.setProrateException(null);
		processManagerForm.setFromAirlineCode(null);
		processManagerForm.setFromDate(null);
		processManagerForm.setFromDateforProrate(null);
		processManagerForm.setGpaCode(null);
		processManagerForm.setMailCategory(null);
		processManagerForm.setMailCategoryforProrate(null);
		processManagerForm.setMailSubclass(null);
		processManagerForm.setMailSubclassforProrate(null);
		processManagerForm.setOrigin(null);
		processManagerForm.setOriginforProrate(null);
		processManagerForm.setOriginOfficeOfExchange(null);
		processManagerForm.setProcessOneTime(null);
		processManagerForm.setToAirlineCode(null);
		processManagerForm.setToDate(null);
		processManagerForm.setToDateforProrate(null);

		invocationContext.target = CLOSE_SUCCESS;



	}
}