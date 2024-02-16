/*
 * ClearSCMValidationCommand.java Created on Jan 5, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmvalidation;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMValidationSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMValidationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class ClearSCMValidationCommand extends BaseCommand{
	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = "uld.defaults.scmvalidation";
	private static final String BLANK = "";
	private Log log = LogFactory.getLogger("ClearSCMValidationCommand");
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	
	public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
		log.entering("ScreenLoadMissingUCMList", "execute");
		
	    SCMValidationForm scmValidationForm = (SCMValidationForm)invocationContext.screenModel;
		SCMValidationSession scmValidationSession = (SCMValidationSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		scmValidationForm.setAirport(BLANK);
		scmValidationForm.setLocation(BLANK);
		scmValidationForm.setUldTypeCode(BLANK);
		scmValidationForm.setFacilityType(BLANK);
		scmValidationForm.setScmStatus(BLANK);
		scmValidationForm.setTotal(BLANK);
		scmValidationForm.setNotSighted(BLANK);
		scmValidationForm.setMissing(BLANK);
		scmValidationSession.removeSCMValidationVOs();
		scmValidationForm.setStatusFlag("screenload_success");
	    scmValidationForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    invocationContext.target = CLEAR_SUCCESS;
	    
	
	}

}
