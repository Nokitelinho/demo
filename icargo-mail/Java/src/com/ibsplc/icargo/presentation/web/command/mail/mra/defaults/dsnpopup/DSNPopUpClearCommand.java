/*
 * DSNPopUpClearCommand.java Created on AUG 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dsnpopup;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNPopUpForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2391
 *
 */
public class DSNPopUpClearCommand extends BaseCommand {


	private static final String CLEAR_SUCCESS = "clear_success";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	private Log log = LogFactory.getLogger(
	"MRA DespatchLov ClearCommand");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ClearCommand","execute");
    	DSNPopUpForm form=
    		(DSNPopUpForm)invocationContext.screenModel;
    	DSNPopUpSession session=getScreenSession(MODULE_NAME,SCREEN_ID);
    	form.setCode("");
    	form.setDsnFilterDate("");
    	
    	form.setLastPageNum("");
    	session.removeDespatchDetails();
    	session.removeSelectedDespatchDetails();
    
    	invocationContext.target=CLEAR_SUCCESS;
		log.exiting("ClearCommand","execute");

    }

}