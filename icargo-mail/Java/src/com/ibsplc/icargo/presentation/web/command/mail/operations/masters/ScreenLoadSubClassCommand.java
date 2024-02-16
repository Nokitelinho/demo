/*
 * ScreenLoadSubClassCommand.java Created on June 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailSubClassMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ScreenLoadSubClassCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ScreenLoadSubClassCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.subclass";
	
	private static final String SBGRP = "mailtracking.defaults.mailsubclassgroup";
	private static final String SUCCESS = "screenload_success";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the screen load---------->\n\n");

    	MailSubClassForm mailSubClassForm = (MailSubClassForm)invocationContext.screenModel;
    	MailSubClassMasterSession subClassSession = getScreenSession(MODULE_NAME,SCREEN_ID);

    	Map oneTimeHashMap = null;
    	Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		oneTimeActiveStatusList.add(SBGRP);
		
    	try {
			oneTimeHashMap = new SharedDefaultsDelegate().findOneTimeValues(
						getApplicationSession().getLogonVO().getCompanyCode(),
						oneTimeActiveStatusList);
    	} catch (BusinessDelegateException e) {
    		e.getMessage();
			handleDelegateException( e );
		}
    	
    	subClassSession.setSubClassGroups((HashMap<String, Collection<OneTimeVO>>) oneTimeHashMap );
    	
    	subClassSession.setMailSubClassVOs(null);
        mailSubClassForm.setSubClassFilter("");
     	mailSubClassForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);

    	invocationContext.target = SUCCESS;

	}
}
