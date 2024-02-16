/*
 * ClearCommand.java Created on Mar 8,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MailProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MailProrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Ruby Abraham
 * Command class for clearing mailproration screen  
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Mar 7,2007   Ruby Abraham		Initial draft
 */
public class ClearCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String CLASS_NAME = "ClearCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.mailproration";
	
	
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
		
		MailProrationSession  mailProrationSession = 
			(MailProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		MailProrationForm mailProrationForm=(MailProrationForm)invocationContext.screenModel;
		
		mailProrationSession.removeProrationFilterVO();
		mailProrationSession.removeProrationVOs();
		mailProrationForm.setDespatchSerialNo("");
		mailProrationForm.setConsigneeDocNo("");
		mailProrationForm.setFlightCarrierCode("");
		mailProrationForm.setFlightNumber("");
		mailProrationForm.setFlightDate("");
		mailProrationForm.setOrgExgOffice("");
		mailProrationForm.setDestExgOffice("");
		mailProrationForm.setPostalAuthorityCode("");
		mailProrationForm.setPostalAuthorityName("");
		mailProrationForm.setMailCategorycode("");
		mailProrationForm.setMailSubclass("");
		mailProrationForm.setDsn("");
		mailProrationForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		
		log.log(Log.FINE, "ProrationFilterVO-------", mailProrationSession.getProrationFilterVO());
		log.log(Log.FINE, "Proration VOs--------->", mailProrationSession.getProrationVOs());
		invocationContext.target = CLEAR_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
