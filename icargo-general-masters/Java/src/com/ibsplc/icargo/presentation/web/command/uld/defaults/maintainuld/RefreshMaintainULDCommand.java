/*
 * RefreshMaintainULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainULDForm;

/**
 * This command class is invoked for refreshing the screen
 *	after saving the multiple ulds
 *
 * @author A-2001
 */
public class RefreshMaintainULDCommand extends BaseCommand {

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID =
		"uld.defaults.maintainuld";
	
	private static final String REFRESHULD_SUCCESS = "refresh_success";
   

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDForm maintainUldForm = 
    							(MaintainULDForm)invocationContext.screenModel; 
    	MaintainULDSession maintainULDSession = 
			(MaintainULDSession)getScreenSession(MODULE,SCREENID);
    	if(maintainULDSession.getUldNumbersSaved() != null &&
    			maintainULDSession.getUldNumbersSaved().size() > 0) {
	    	maintainUldForm.setTotalNoofUlds(
	    	  Integer.toString(maintainULDSession.getUldNumbersSaved().size()));
	    	
	    	maintainUldForm.setScreenStatusFlag(
	 				ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	maintainUldForm.setOperationalAirlineCode(maintainUldForm.getOwnerAirlineCode());
			maintainUldForm.setOperationalOwnerAirlineCode(maintainUldForm.getOwnerAirlineCode());
	    	maintainUldForm.setStatusFlag("refresh");
    	}
    	else {
    		maintainUldForm.setScreenStatusFlag(
	 				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	}
 		invocationContext.target = REFRESHULD_SUCCESS;
    }
}
