/*
 * DSNSelectLovClearCommand.java Created on Jul 10, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchlov;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DSNSelectLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2391
 *
 */
public class DSNSelectLovClearCommand extends BaseCommand {


	private static final String CLEAR_SUCCESS = "clear_success";
	private Log log = LogFactory.getLogger(
	"MRA DespatchLov ClearCommand");
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ClearCommand","execute");
    	DSNSelectLovForm form=
    		(DSNSelectLovForm)invocationContext.screenModel;
    	form.setCode("");
    	form.setDsnFilterDate("");
    	
    	form.setLastPageNum("");
    	form.setDespatchLovPage(null);
    
    	invocationContext.target=CLEAR_SUCCESS;
		log.exiting("ClearCommand","execute");

    }

}