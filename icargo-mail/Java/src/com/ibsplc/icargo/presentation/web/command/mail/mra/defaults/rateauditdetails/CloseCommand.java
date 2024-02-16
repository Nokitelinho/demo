/*
 * CloseCommand.java Created on July 17,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListRateAuditSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 *
 */
public class CloseCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";

	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_SUCCESS_TOLISTRATEAUDIT="listrateaudit";
	private static final String BLANK="";
	private static final String SCREENID_LISTRATEAUDIT =
		"mailtracking.mra.defaults.listrateaudit";	
	private static final String FROM_SCREEN="fromListRateAudit";
	/**
	 * Method  implementing closing of screen
	 * @author a-2391
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");

    	RateAuditDetailsSession session=getScreenSession(MODULE,SCREENID);
    	ListRateAuditSession listRateAuditSession = getScreenSession(
				 MODULE, SCREENID_LISTRATEAUDIT);
    	RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
    	session.setRateAuditVO(null);
    	//listRateAuditSession.removeAllAttributes();
    	//commented to avoid going to  ListRateAudit screen all the time From screen logic to be implemented
    	
    	log.log(Log.INFO, "from screen flag from session===1234>>",
				listRateAuditSession.getFromScreen());
		log.log(Log.INFO, "from screen flag===1234>>", rateAuditDetailsForm.getFromScreen());
		if( "LISTRA".equals(rateAuditDetailsForm.getFromScreen()) ){
					//listRateAuditSession.setRateAuditFilterVO(null);
					listRateAuditSession.setFromScreen(FROM_SCREEN);
					listRateAuditSession.setRateAuditVOs(null);
					rateAuditDetailsForm.setIsFromListRateAuditScreen(BLANK);
					invocationContext.target = CLOSE_SUCCESS_TOLISTRATEAUDIT;
					 
		 }else if(FROM_SCREEN.equals(listRateAuditSession.getFromScreen())){
			 
			 listRateAuditSession.setRateAuditVOs(null);
				rateAuditDetailsForm.setIsFromListRateAuditScreen(BLANK);
				invocationContext.target = CLOSE_SUCCESS_TOLISTRATEAUDIT;
		  }
		else{
		    invocationContext.target = CLOSE_SUCCESS;
			}
		 }
    
}


