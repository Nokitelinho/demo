/*
 * ClearUnaccountedDispatchesCommand.java Created on Aug 21, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.unaccounteddispatches;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.UnaccountedDispatchesForm;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;

/**
 * @author A-2107
 *
 */
public class ClearUnaccountedDispatchesCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MRA");
	
   /**
    * TARGET
    */
   private static final String TARGET = "clear_success";
   
   private static final String MODULE_NAME = "mailtracking.defaults.mra";	
   
   private static final String SCREEN_ID = "mailtracking.mra.defaults.unaccounteddispatches";	
  
  /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ClearUnaccountedDispatchesCommand","execute");
    	  
    	UnaccountedDispatchesSession unaccountedDispatchesSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	UnaccountedDispatchesForm unaccountedDispatchesForm = 
			(UnaccountedDispatchesForm)invocationContext.screenModel;
       
		UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO = null;
		UnaccountedDispatchesVO unaccountedDispatchesVO = null;
				
		unaccountedDispatchesSession.setUnaccountedDispatchesVO(unaccountedDispatchesVO);
		unaccountedDispatchesSession.setUnaccountedDispatchesFilterVO(unaccountedDispatchesFilterVO);
		unaccountedDispatchesForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	
    	invocationContext.target = TARGET;
       	
    	log.exiting("ClearUnaccountedDispatchesCommand","execute");
    	
    }
       
}
