/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite.ClearLOV.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.billingsite;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteLOVForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class ClearLOV.
 *
 * @author A-5219
 */
public class ClearLOV extends BaseCommand {


	/** The Constant CLEAR_SUCCESS. */
	private static final String CLEAR_SUCCESS = "clear_success";
	
	/** The log. */
	private Log log = LogFactory.getLogger(
	"BillingSiteLOV ClearCommand");
	
	/**
	 * Execute.
	 *
	 * @param invocationContext the invocation context
	 * @throws CommandInvocationException the command invocation exception
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ClearCommand","execute");
    	BillingSiteLOVForm form =
			(BillingSiteLOVForm)invocationContext.screenModel;
    	form.setBillingSiteCode("");
    	form.setSelectCheckBox(null);
    	form.setCode("");
    	form.setDescription("");
    	form.setLastPageNum("");
    	form.setDisplayPage("1");
    	    	invocationContext.target=CLEAR_SUCCESS;
		log.exiting("ClearCommand","execute");

    }

}