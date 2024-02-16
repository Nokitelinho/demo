/*
 * ClearULDAgreementCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldagreement.listuldagreement;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListULDAgreementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDAgreementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * ClearULDAgreementCommand 
 * @author a-1870
 *
 */

public class ClearULDAgreementCommand extends BaseCommand{
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULE_NAME = "uld.defaults";
    private static final String SCREEN_ID = "uld.defaults.listuldagreement";
    private static final String BLANK = "";
    private static final String ALL = "ALL";
    
	/**
	 * execute method 
	 * @param invocationContext 
	 * @throws CommandInvocationException
	 */	
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    	log.entering("ClearULDAgreementCommand","ULD");
    	ListULDAgreementForm form = (ListULDAgreementForm)invocationContext.screenModel;
    	ListULDAgreementSession session = getScreenSession(MODULE_NAME,SCREEN_ID);
    	form.setAgreementNumber(BLANK);
    	form.setAgreementListDate(BLANK);
    	form.setAgreementFromDate(BLANK);
    	form.setAgreementToDate(BLANK);
    	form.setPartyCode(BLANK);
    	form.setFromPartyCode(BLANK);
    	form.setAgreementStatus(ALL);
    	form.setTransactionType(ALL);
    	form.setPartyType(ALL);
    	form.setFromPartyType(ALL);
    	form.setListStatus("");
    	session.setUldAgreements(null);
    	session.setULDAgreementFilterVO(null);
    	invocationContext.target=SCREENLOAD_SUCCESS;
}
}
