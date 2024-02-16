/*
 * ClearCommand.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "ClearCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String CLEAR_SUCCESS="clear_success";
	private static final String LINK_STATUS = "Y";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		maintainMailSLAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		
		maintainMailSLAForm.setSlaId(null);
		maintainMailSLAForm.setDescription(null);
		maintainMailSLAForm.setCurrency(null);
		maintainMailSLAForm.setMailCategory(null);
		maintainMailSLAForm.setServiceTime(null);
		maintainMailSLAForm.setAlertTime(null);
		maintainMailSLAForm.setChaserTime(null);
		maintainMailSLAForm.setChaserFrequency(null);
		maintainMailSLAForm.setMaxNumberOfChasers(null);
		maintainMailSLAForm.setClaimRate(null);
		maintainMailSLAForm.setNewSLAOptionFlag(null);
		maintainMailSLAForm.setCallPopup(null);
		maintainMailSLAForm.setLinkStatus(LINK_STATUS);
		
		
		maintainMailSLASession.removeMailSLAVo();
		invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");


	}

}
