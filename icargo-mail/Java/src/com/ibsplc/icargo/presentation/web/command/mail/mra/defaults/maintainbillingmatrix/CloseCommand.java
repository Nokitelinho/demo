/*
 * CloseCommand.java Created on Feb 28, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainbillingmatrix;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainBillingMatrixSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingMatrixForm;
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

	private static final String SCREENID = "mailtracking.mra.defaults.maintainbillingmatrix";

	private static final String CLEAR_SUCCESS = "close_success";
	private static final String PARENT_SUCCESS = "parent_load";
		


	/**
	 * 	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");    	
    	
    	BillingMatrixForm form = (BillingMatrixForm) invocationContext.screenModel;
		MaintainBillingMatrixSession session = (MaintainBillingMatrixSession) getScreenSession(
				MODULE_NAME, SCREENID);
		session.setBillingLineDetails(null);
		session.setBillingMatrixVO(null);
		session.setIndexMap(null);
		session.setBillingMatrixVO(null);
		form.setBlgMatrixID(null);
		form.setIdValue(null);
		form.setIsModified("");
		session.removeBillingLneDetails();
		session.removeBillingMatrixVO();
		session.removeIndexMap();
		session.removeOneTimeVOs();
		session.removeBillingMatrixFilterVO();
		if(SCREENID.equals(session.getParentScreenId())){
			invocationContext.target = PARENT_SUCCESS;	
		}else{
		invocationContext.target = CLEAR_SUCCESS;
		}
		log.exiting(CLASS_NAME,"execute"); 
    }

    }
