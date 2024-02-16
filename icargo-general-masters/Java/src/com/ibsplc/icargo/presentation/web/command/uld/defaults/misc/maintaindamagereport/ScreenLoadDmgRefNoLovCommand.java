/*
 * ScreenLoadDmgRefNoLovCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.
														maintaindamagereport;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.DamageRefNoLovSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.DamageRefNoLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the 
 * ScreenLoadMaintainDamageReportCommand screen
 * 
 * @author A-2052
 */

public class ScreenLoadDmgRefNoLovCommand extends BaseCommand {
    
	/**
	 * Logger for Maintain Damage Report
	 */
	private Log log = LogFactory.getLogger("ScreenLoadDmgRefNoLovCommand");
	/**
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/**
	 * Screen Id of maintain damage report screen
	 */
	private static final String SCREENID =
		"uld.defaults.damagerefnolov";
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//Commented by Manaf for INT ULD510
		//String  compCode = logonAttributes.getCompanyCode();

		DamageRefNoLovForm form = 
			(DamageRefNoLovForm)invocationContext.screenModel;
		DamageRefNoLovSession session = getScreenSession(MODULE, SCREENID);
		form.setPageURL("listlov");
		session.setParentScreenId("listlov");
		form.setScreenStatusFlag(
				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SCREENLOAD_SUCCESS;
        
    }
    
}
