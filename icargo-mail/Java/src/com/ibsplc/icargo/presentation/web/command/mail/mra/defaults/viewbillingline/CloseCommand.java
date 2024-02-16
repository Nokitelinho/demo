/*
 * CloseCommand.java Created on Mar 12, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewbillingline;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ViewBillingLineSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ViewBillingLineForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-2398
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "CloseCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.viewbillingline";

	private static final String CLEAR_SUCCESS = "close_success";
	
	private static final String PARENT_ID = "mailtracking.mra.defaults.listbillingmatrix";
	
	private static final String GOTO_PARENT = "load_parent";
		


	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");    	
    	
    	ViewBillingLineForm form = (ViewBillingLineForm) invocationContext.screenModel;
		ViewBillingLineSession session = (ViewBillingLineSession) getScreenSession(
				MODULE_NAME, SCREENID);
		form.setAirline("");
		form.setBilledSector("");
		form.setBillingClass("");
		form.setBillingMatrixID("");
		form.setCategory("");
		form.setValidTo("");
		form.setValidFrom("");
		form.setStatus("");
		form.setDestination("");
		form.setPostalAdmin("");
		form.setUldType("");
		form.setOrigin("");
		form.setSubClass("");
		form.setDestination("");
		form.setCatCode("");
		form.setClassCode("");
		session.removeIndexMap();
		session.removeOneTimeVOs();
			session.setIndexMap(null);
			session.setOneTimeVOs(null);
			session.setBillingLineDetails(null);
		if(PARENT_ID.equals(session.getParentScreenId())) {
			invocationContext.target = GOTO_PARENT;
		} else {
			invocationContext.target = CLEAR_SUCCESS;
		}
		session.removeAllAttributes();
		log.exiting(CLASS_NAME,"execute"); 
    }

    }
