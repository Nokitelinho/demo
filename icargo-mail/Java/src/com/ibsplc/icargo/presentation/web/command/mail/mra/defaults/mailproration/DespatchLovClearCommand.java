/*
 * DespatchLovClearCommand.java Created on Apr 25, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.mailproration;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchLovForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2408
 *
 */
public class DespatchLovClearCommand extends BaseCommand {


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
    	DespatchLovForm form=
    		(DespatchLovForm)invocationContext.screenModel;
    	form.setCode("");
    	form.setGpaCodeFilter("");
    	form.setDespatchFilter("");

    	form.setLastPageNum("");
    	form.setDespatchLovPage(null);

    	invocationContext.target=CLEAR_SUCCESS;
		log.exiting("ClearCommand","execute");

    }

}