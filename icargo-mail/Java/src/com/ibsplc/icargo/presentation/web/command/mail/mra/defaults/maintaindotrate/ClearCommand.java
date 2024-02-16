/*
 * ClearCommand.java Created on Aug 07, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaindotrate;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainDOTRateSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainDOTRateForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 *
 */
public class ClearCommand extends BaseCommand{
	
	private Log log = LogFactory.getLogger("MaintainDOTRate ScreenloadCommand");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaindotrate";
	
	private static final String BLANK = "";
	
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering(CLASS_NAME, "execute");
    	
    	MaintainDOTRateForm form=(MaintainDOTRateForm)invocationContext.screenModel;
    	MaintainDOTRateSession session=null;
   		session=(MaintainDOTRateSession) getScreenSession(MODULE_NAME,SCREEN_ID);
   		
   		form.setGreatCircleMiles(BLANK);
   		form.setRateCodeFilter(BLANK);
   		form.setScreenFlag("screenload");
   		form.setSectorOriginCode(BLANK);
   		form.setSectorDestinationCode(BLANK);
		session.setMailDOTRateVOs(null);
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }

}
